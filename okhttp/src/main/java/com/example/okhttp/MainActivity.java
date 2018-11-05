package com.example.okhttp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ImageView mImageView;
    private Button mButton;
    private Button mButton2;
    public static final int SUCCESS = 1;
    public static final int FAIL = 0;
    private OkHttpClient mOkHttpClient;
    //    private String path = "https://www.baidu.com/img/superlogo_c4d7df0a003d3db9b65e9ef0fe6da1ec.png?where=super";
    private String image_path = "http://i.guancha.cn/news/2018/03/25/20180325095919875.jpg";
    private String json_path = "http://gank.io/api/data/福利/10/1";
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SUCCESS:
                    byte[] objs = (byte[]) msg.obj;
                    Bitmap bitmap = BitmapFactory.decodeByteArray(objs, 0, objs.length);
                    bitmap = new CropSquareTrans().transform(bitmap);// 裁剪，可省略
                    mImageView.setImageBitmap(bitmap);
                    break;
                case FAIL:
                    break;
            }
        }
    };
    private OKManager mOkManager;// 工具类

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mOkHttpClient = new OkHttpClient();
        mImageView = findViewById(R.id.imageView);
        mButton = findViewById(R.id.button);
        mButton2 = findViewById(R.id.button2);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Request request = new Request.Builder().url(path).build();
                // 使用get请求
                Request request = new Request.Builder().get().url(image_path).build();
                Call call = mOkHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            Message message = Message.obtain();
                            message.what = SUCCESS;
                            message.obj = response.body().bytes();
                            mHandler.sendMessage(message);
                        } else {
                            mHandler.sendEmptyMessage(FAIL);
                        }
                    }
                });
            }
        });
        ///////////////////////////////////////////////////////////////////////
        mOkManager = OKManager.getInstance();
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOkManager.asyncJsonStringByURl(json_path, new OKManager.Func1() {
                    @Override
                    public void onResponse(String string) {
                        Log.d(TAG, "onResponse: " + string);
                    }
                });
            }
        });
    }
}
