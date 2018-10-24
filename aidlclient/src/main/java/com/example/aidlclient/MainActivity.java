package com.example.aidlclient;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

// 特别注意客户端中aidl要与服务端中aidl的包名完全一致 todo
import com.example.aidlservice.IDataInterface;

public class MainActivity extends AppCompatActivity {

    private IDataInterface iDataInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.bt1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                // 在服务端的清单文件中配置
                intent.setAction("com.aidlservice.AIDL_SERVICE");
                // 设置服务端的包名
                intent.setPackage("com.example.aidlservice");
                bindService(intent, new AidlServiceConnection(), Service.BIND_AUTO_CREATE);
            }
        });

        findViewById(R.id.bt2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int roomNum = iDataInterface.getRoomNum();
                    Toast.makeText(getApplicationContext(), "roomNum = " + roomNum, Toast.LENGTH_SHORT).show();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private class AidlServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // 用asInterface的形式将iBinder还原成接口，再调用其接口中的方法实现通信
            iDataInterface = IDataInterface.Stub.asInterface(service);
            Toast.makeText(getApplicationContext(), "绑定成功", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }
}
