package com.feicuiedu.hunttreasure.treasure.detail;

import com.feicuiedu.hunttreasure.net.NetClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gqq on 2017/1/11.
 */

// 宝藏详情的业务类
public class TreasureDetailPresenter {

    private TreasureDetailView mDetailView;

    public TreasureDetailPresenter(TreasureDetailView detailView) {
        mDetailView = detailView;
    }

    public void getTreasureDetail(TreasureDetail treasureDetail){
        Call<List<TreasureDetailResult>> detailCall = NetClient.getInstances().getTreasureApi().getTreasureDetail(treasureDetail);
        detailCall.enqueue(mListCallback);
    }

    // 回调的Callback
    private Callback<List<TreasureDetailResult>> mListCallback = new Callback<List<TreasureDetailResult>>() {

        // 请求成功
        @Override
        public void onResponse(Call<List<TreasureDetailResult>> call, Response<List<TreasureDetailResult>> response) {
            if (response.isSuccessful()){
                List<TreasureDetailResult> resultList = response.body();
                if (resultList==null){
                    // 弹个吐司说明一下
                    mDetailView.showMessage("未知的错误");
                    return;
                }
                // 数据获取到了，要给视图设置上(TextView上展示)
                mDetailView.setData(resultList);
            }
        }

        // 请求失败
        @Override
        public void onFailure(Call<List<TreasureDetailResult>> call, Throwable t) {
            // 提示信息：请求失败
            mDetailView.showMessage("请求失败了"+t.getMessage());
        }
    };
}
