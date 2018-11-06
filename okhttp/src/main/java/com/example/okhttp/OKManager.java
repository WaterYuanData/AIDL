package com.example.okhttp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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

    /**
     * 同步请求，在Android开发中不常用，因为会阻塞UI线程
     *
     * @param url
     * @return
     */
    public String syncGetURl(String url) {
        // 构建一个request请求
        Request request = new Request.Builder().url(url).build();
        Response response = null;
        try {
            response = mOkHttpClient.newCall(request).execute();// 同步请求数据
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 请求指定的url，返回的结果是json
     *
     * @param url
     * @param callBack
     */
    public void asyncJsonStringByURl(String url, final Func1 callBack) {
        // 构建一个request请求
        Request request = new Request.Builder().url(url).build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response != null && response.isSuccessful()) {
                    onSuccessJsonStringMethod(response.body().string(), callBack);
                }
            }
        });
    }

    public void asyncGetByteByURl(String url, final Func2 callBack) {
        // 构建一个request请求
        Request request = new Request.Builder().url(url).build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response != null && response.isSuccessful()) {
                    onSuccessByteMethod(response.body().bytes(), callBack);
                }
            }
        });
    }

    public void asyncDownLoadImageByURl(String url, final Func3 callBack) {
        // 构建一个request请求
        Request request = new Request.Builder().url(url).build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response != null && response.isSuccessful()) {
                    byte[] data = response.body().bytes();
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                    bitmap = new CropSquareTrans().transform(bitmap);
                    callBack.onResponse(bitmap);
                }
            }
        });
    }

    public void asyncJsonObjectByURl(String url, final Func4 callBack) {
        // 构建一个request请求
        Request request = new Request.Builder().url(url).build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response != null && response.isSuccessful()) {
                    onSuccessJsonObjectMethod(response.body().string(), callBack);
                }
            }
        });
    }

    /**
     * 模拟表单提交
     *
     * @param url
     * @param params
     * @param callBack
     */
    public void sendComplexFrom(String url, Map<String, String> params, final Func4 callBack) {
        // 表单对象，包含以input开始的对象，以html表单为主
        FormBody.Builder formBody_builder = new FormBody.Builder();
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                formBody_builder.add(entry.getKey(), entry.getValue());
            }
        }
        RequestBody requestBody = formBody_builder.build();
        // 采用post方式提交
        final Request request = new Request.Builder().url(url).post(requestBody).build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response != null && response.isSuccessful()) {
                    onSuccessJsonObjectMethod(response.body().string(), callBack);
                }
            }
        });


    }

    /**
     * 请求返回的结果是json字符串
     *
     * @param jsonValue
     * @param callBack
     */
    private void onSuccessJsonStringMethod(final String jsonValue, final Func1 callBack) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    callBack.onResponse(jsonValue);
                }
            }
        });
    }

    /**
     * 请求返回的结果是byte数组
     *
     * @param data
     * @param callBack
     */
    private void onSuccessByteMethod(final byte[] data, final Func2 callBack) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    callBack.onResponse(data);
                }
            }
        });
    }

    /**
     * 请求返回的结果是byte数组
     *
     * @param jsonValue
     * @param callBack
     */
    private void onSuccessJsonObjectMethod(final String jsonValue, final Func4 callBack) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    try {
                        callBack.onResponse(new JSONObject(jsonValue));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    interface Func1 {
        void onResponse(String string);
    }

    interface Func2 {
        void onResponse(byte[] bytes);
    }

    interface Func3 {
        void onResponse(Bitmap bitmap);
    }

    interface Func4 {
        void onResponse(JSONObject jsonObject);
    }

}
