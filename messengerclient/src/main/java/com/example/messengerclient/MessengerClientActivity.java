package com.example.messengerclient;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MessengerClientActivity extends AppCompatActivity {
    private static final String TAG = "MessengerClientActivity";
    private static final int MSG_SUM = 1;
    private TextView mTextView;
    private int a = 0;
    Messenger mMessengerInService;
    Messenger mMessenger = new Messenger(new Handler() {
        @Override
        public void handleMessage(Message msgFromService) {
            switch (msgFromService.what) {
                case MSG_SUM:
                    mTextView.setText(mTextView.getText() + ": " + msgFromService.arg2);
                    break;
            }
            super.handleMessage(msgFromService);
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger_client);

        mTextView = findViewById(R.id.tv3);

        findViewById(R.id.tv1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                /**
                 * java.lang.IllegalArgumentException: Service Intent must be explicit: Intent
                 * Intent intent = new Intent(this,MessengerService.class);
                 * 如何绑定另一个进程的Service：todo
                 * Android5.0中service的intent一定要显性声明,但是服务端的MessengerService不存在于客户端，
                 * 所以通过intent.setPackage("com.example.messengerservice");
                 * */
                intent.setPackage("com.example.messengerservice");
                intent.setAction("MessengerService的action");
                bindService(intent, mServiceConnection, Service.BIND_AUTO_CREATE);
                mTextView.setText(mTextView.getText() + ": 已连接");
            }
        });

        findViewById(R.id.tv2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    a++;
                    int b = 10;
                    // 获得一个消息并由服务端的mMessengerInService发送 todo
                    Message msgFromClient = Message.obtain(null, MSG_SUM, a, b);
                    msgFromClient.replyTo = mMessenger;
                    mMessengerInService.send(msgFromClient);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected: 客户端接受服务端发送的IBinder对象");
            mMessengerInService = new Messenger(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
}
