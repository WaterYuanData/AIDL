package com.example.okhttp;

import android.os.Handler;
import android.os.Looper;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;

public class OKManager {

    private OkHttpClient mOkHttpClient;
    private volatile static OKManager mOkManager;
    private static final String TAG = OKManager.class.getSimpleName();
    private Handler mHandler;
    // 提价json数据
    private static final MediaType JSON = MediaType.parse("application/json;charset=utf-8");
    // 提交字符串
    private static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown;charset=utf-8");

    private OKManager() {
        mOkHttpClient = new OkHttpClient();
        mHandler = new Handler(Looper.getMainLooper());
    }

    // 采取单例模式获取对象
    public static OKManager getInstance() {
        synchronized (OKManager.class) {
            if (mOkManager == null) {
                mOkManager = new OKManager();
            }
        }
        return mOkManager;
    }

}
