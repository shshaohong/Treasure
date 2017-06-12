package com.feicuiedu.hunttreasure.treasure.map;

import com.feicuiedu.hunttreasure.treasure.Treasure;

import java.util.List;

/**
 * Created by gqq on 2017/1/10.
 */

public interface MapMvpView {

    void showMessage(String msg);// 弹吐司
    void setData(List<Treasure> list);// 设置数据
}
