package com.feicuiedu.hunttreasure.net;

import com.feicuiedu.hunttreasure.treasure.Area;
import com.feicuiedu.hunttreasure.treasure.Treasure;
import com.feicuiedu.hunttreasure.treasure.detail.TreasureDetail;
import com.feicuiedu.hunttreasure.treasure.detail.TreasureDetailResult;
import com.feicuiedu.hunttreasure.treasure.hide.HideTreasure;
import com.feicuiedu.hunttreasure.treasure.hide.HideTreasureResult;
import com.feicuiedu.hunttreasure.user.User;
import com.feicuiedu.hunttreasure.user.account.UploadResult;
import com.feicuiedu.hunttreasure.user.login.LoginResult;
import com.feicuiedu.hunttreasure.user.register.RegisterResult;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by gqq on 2017/1/9.
 */

// 请求构建的接口
public interface TreasureApi {

    // 登录的请求
    @POST("/Handler/UserHandler.ashx?action=login")
    Call<LoginResult> login(@Body User user);

    // 注册的请求
    @POST("/Handler/UserHandler.ashx?action=register")
    Call<RegisterResult> register(@Body User user);

    // 获取区域内的宝藏数据请求
    @POST("/Handler/TreasureHandler.ashx?action=show")
    Call<List<Treasure>> getTreasureInArea(@Body Area area);

    // 宝藏详情的请求
    @POST("/Handler/TreasureHandler.ashx?action=tdetails")
    Call<List<TreasureDetailResult>> getTreasureDetail(@Body TreasureDetail treasureDetail);

    // 埋藏宝藏的请求
    @POST("/Handler/TreasureHandler.ashx?action=hide")
    Call<HideTreasureResult> hideTreasure(@Body HideTreasure hideTreasure);

    /**
     * 关于头像上传的：文件
     */
    // 两种方式
//    @Multipart
//    @POST("/Handler/UserLoadPicHandler1.ashx")
//    Call<UploadResult> upload(@Part("file\";filename=\"image.png\"") RequestBody body);

    @POST("/Handler/UserLoadPicHandler1.ashx")
    Call<UploadResult> upload(@Part MultipartBody.Part part);

}
