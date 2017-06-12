package com.feicuiedu.hunttreasure.treasure.map;

import android.util.Log;

import com.feicuiedu.hunttreasure.net.NetClient;
import com.feicuiedu.hunttreasure.treasure.Area;
import com.feicuiedu.hunttreasure.treasure.Treasure;
import com.feicuiedu.hunttreasure.treasure.TreasureRepo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gqq on 2017/1/10.
 */

// 获取宝藏数据的业务类
public class MapPresenter {

    private MapMvpView mMvpView;
    private Area mArea;

    public MapPresenter(MapMvpView mvpView) {
        mMvpView = mvpView;
    }

    public void getTreasure(Area area){

        if (TreasureRepo.getInstance().isCached(area)){
            return;
        }

        this.mArea = area;
        Call<List<Treasure>> listCall = NetClient.getInstances().getTreasureApi().getTreasureInArea(area);
        listCall.enqueue(mListCallback);
    }

    private Callback<List<Treasure>> mListCallback = new Callback<List<Treasure>>() {

        // 请求成功
        @Override
        public void onResponse(Call<List<Treasure>> call, Response<List<Treasure>> response) {

            if (response.isSuccessful()){
                List<Treasure> treasureList = response.body();
                if (treasureList==null){
                    // 友好的提示一下：弹个吐司
                    mMvpView.showMessage("未知的错误");
                    return;
                }

                // 做缓存
                TreasureRepo.getInstance().addTreasure(treasureList);
                TreasureRepo.getInstance().cache(mArea);

                // 拿到数据了：给MapFragment，在地图展示
                mMvpView.setData(treasureList);
            }
        }

        // 请求失败
        @Override
        public void onFailure(Call<List<Treasure>> call, Throwable t) {
            // 弹个吐司
            mMvpView.showMessage("请求失败"+t.getMessage());
        }
    };
}
