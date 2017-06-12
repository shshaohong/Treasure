package com.feicuiedu.hunttreasure.user.register;

import android.os.AsyncTask;

import com.feicuiedu.hunttreasure.net.NetClient;
import com.feicuiedu.hunttreasure.user.User;
import com.feicuiedu.hunttreasure.user.UserPrefs;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gqq on 2017/1/3.
 */

// 注册的业务类
public class RegisterPresenter {

    /**
     * 视图的交互怎么处理？
     * 1. RegisterActivity
     * 2. 接口回调
     * 接口的实例化和接口方法的具体实现
     * 让Activity实现视图接口
     */

    private RegisterView mRegisterView;

    public RegisterPresenter(RegisterView registerView) {
        mRegisterView = registerView;
    }

    public void register(User user){
        mRegisterView.showProgress();
        Call<RegisterResult> resultCall = NetClient.getInstances().getTreasureApi().register(user);
        resultCall.enqueue(mResultCallback);
    }

    private Callback<RegisterResult> mResultCallback = new Callback<RegisterResult>() {

        // 请求成功
        @Override
        public void onResponse(Call<RegisterResult> call, Response<RegisterResult> response) {

            mRegisterView.hideProgress();

            // 响应成功
            if (response.isSuccessful()){
                RegisterResult result = response.body();

                // 响应体是不是为null
                if (result==null){
                    mRegisterView.showMessage("发生了未知的错误");
                    return;
                }
                // 不为空
                if (result.getCode()==1){
                    // 真正的注册成功
                    // 保存用户token
                    UserPrefs.getInstance().setTokenid(result.getTokenId());
                    mRegisterView.navigationToHome();
                }
                mRegisterView.showMessage(result.getMsg());
            }
        }

        // 请求失败
        @Override
        public void onFailure(Call<RegisterResult> call, Throwable t) {
            mRegisterView.hideProgress();
            mRegisterView.showMessage("请求失败"+t.getMessage());
        }
    };

}
