package com.feicuiedu.hunttreasure.user.account;

import com.feicuiedu.hunttreasure.net.NetClient;

import java.io.File;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017-1-13.
 */

public class AccountPresenter {

    public void uploadPhoto(File file) {
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", "photo.png", RequestBody.create(null, file));
        Call<UploadResult> uploadResultCall = NetClient.getInstances().getTreasureApi().upload(part);

        uploadResultCall.enqueue(mUploadResultCallback);
    }
    
    private Callback<UploadResult> mUploadResultCallback = new Callback<UploadResult>() {
        @Override
        public void onResponse(Call<UploadResult> call, Response<UploadResult> response) {
            UploadResult body = response.body();
            if (body == null) {
                return;
            }
            if (body.getCount() != 1) {
                return;
            }
            String photoUrl = body.getUrl();
            // TODO: 2017-1-13  更新信息！！重新在个人信息上加载、保存到用户信息里面等
            

        }

        @Override
        public void onFailure(Call<UploadResult> call, Throwable t) {

            //提示
        }
    };
}
