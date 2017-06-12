package com.feicuiedu.hunttreasure.treasure.hide;

/**
 * Created by gqq on 2017/1/12.
 */

public interface HideTreasureView {

    // 宝藏上传中视图的交互

    void showProgress();

    void hideProgress();

    void showMessage(String msg);

    void navigationToHome();

}
