package com.feicuiedu.hunttreasure.treasure.hide;

import com.feicuiedu.hunttreasure.net.NetClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gqq on 2017/1/12.
 */

// 埋藏宝藏的业务类
public class HideTreasurePresenter {

    // 过程中与视图的交互？？
    private HideTreasureView mHideView;

    public HideTreasurePresenter(HideTreasureView hideView) {
        mHideView = hideView;
    }

    public void hideTreasure(HideTreasure hideTreasure){

        // 显示进度
        mHideView.showProgress();

        Call<HideTreasureResult> resultCall = NetClient.getInstances().getTreasureApi().hideTreasure(hideTreasure);
        resultCall.enqueue(mResultCallback);
    }

    // 请求的回调
    private Callback<HideTreasureResult> mResultCallback = new Callback<HideTreasureResult>() {

        // 请求成功
        @Override
        public void onResponse(Call<HideTreasureResult> call, Response<HideTreasureResult> response) {
            // 进度隐藏
            mHideView.hideProgress();

            if (response.isSuccessful()){
                HideTreasureResult treasureResult = response.body();
                if (treasureResult==null){
                    // 提示：
                    mHideView.showMessage("未知的错误");
                    return;
                }
                // 真正的上传成功了
                if (treasureResult.getCode()==1){
                    // 跳转回首页
                    mHideView.navigationToHome();
                }
                // 提示：
                mHideView.showMessage(treasureResult.getMsg());
            }
        }

        // 请求失败
        @Override
        public void onFailure(Call<HideTreasureResult> call, Throwable t) {
            // 隐藏进度
            mHideView.hideProgress();
            // 提示：
            mHideView.showMessage("请求失败"+t.getMessage());
        }
    };
}
