package com.example.okhttp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    public static final int SUCCESS = 1;
    public static final int FAIL = 0;
    private OkHttpClient mOkHttpClient;
    //    private String path = "https://www.baidu.com/img/superlogo_c4d7df0a003d3db9b65e9ef0fe6da1ec.png?where=super";
    private String path = "http://i.guancha.cn/news/2018/03/25/20180325095919875.jpg";
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mOkHttpClient = new OkHttpClient();
        mImageView = findViewById(R.id.imageView);
        mButton = findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Request request = new Request.Builder().url(path).build();
                // 使用get请求
                Request request = new Request.Builder().get().url(path).build();
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
    }
}
