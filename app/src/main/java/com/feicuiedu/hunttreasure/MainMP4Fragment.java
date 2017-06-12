package com.feicuiedu.hunttreasure;


import android.content.res.AssetFileDescriptor;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;

import com.feicuiedu.hunttreasure.commons.ActivityUtils;

import java.io.FileDescriptor;
import java.io.IOException;

// 这个里面我们主要是进行视频的播放
public class MainMP4Fragment extends Fragment implements TextureView.SurfaceTextureListener {

    private TextureView mTextureView;

    private ActivityUtils mActivityUtils;
    private MediaPlayer mMediaPlayer;

    /**
     * 1. 使用MediaPlayer来进行播放视频
     * 2. 展示视频播放：SurfaceView,我们在这里使用的是TextureView
     * 3. 使用TextureView：需要使用的是SurfaceTexture：使用这个来渲染、呈现播放的内容
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mActivityUtils = new ActivityUtils(this);
        // fragment全屏显示播放视频的控件
        mTextureView = new TextureView(getContext());
        return mTextureView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // 设置监听：因为播放显示内容需要SurfaceTexture，所以设置一个监听，看SurfaceTexture有没有准备好或有没有变化等
        mTextureView.setSurfaceTextureListener(this);
    }

    // 确实准备好了，可以展示内容了
    @Override
    public void onSurfaceTextureAvailable(final SurfaceTexture surface, int width, int height) {
        /**
         * 1. 播放什么呢？找到我们播放的资源
         * 2. 可以播放了：MediaPlayer来进行播放
         *      创建、设置播放的资源、设置播放的同异步等。。
         *      MediaPlayer有没有准备好：好了，就直接开始播放吧
         * 3. 页面销毁了：MediaPlayer资源释放掉。。。
         */

        try {
            // 打开播放的资源文件




            AssetFileDescriptor openFd = getContext().getAssets().openFd("welcome.mp4");
            // 拿到MediaPlayer需要的资源类型
            FileDescriptor fileDescriptor = openFd.getFileDescriptor();
            mMediaPlayer = new MediaPlayer();
            // 设置播放的资源给MediaPlayer
            mMediaPlayer.setDataSource(fileDescriptor, openFd.getStartOffset(), openFd.getLength());
            mMediaPlayer.prepareAsync();// 异步
            // 设置监听：看有没有准备好，好了，开始播放
            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                // 确实准备好了，开始播放吧
                @Override
                public void onPrepared(MediaPlayer mp) {

                    Surface mySurface = new Surface(surface);
                    mMediaPlayer.setSurface(mySurface);
                    mMediaPlayer.setLooping(true);// 循环播放
                    mMediaPlayer.seekTo(0);
                    mMediaPlayer.start();
                }
            });
        } catch (IOException e) {
            mActivityUtils.showToast("媒体文件播放失败了");
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }

    }
}
