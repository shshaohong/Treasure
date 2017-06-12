package com.feicuiedu.hunttreasure.user.register;

/**
 * Created by gqq on 2017/1/3.
 */

// 视图的接口
public interface RegisterView {

    void showProgress();

    void hideProgress();

    void showMessage(String msg);

    void navigationToHome();

}
