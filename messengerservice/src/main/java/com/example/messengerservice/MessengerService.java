package com.example.messengerservice;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

public class MessengerService extends Service {
    private static final String TAG = "MessengerService";
    private static final int MSG_SUM = 1;

    public MessengerService() {
    }

    /**
     * new一个实现handleMessage()方法的Handler去实现Messenger todo
     * 最好用HandlerThread的形式
     */
    Messenger mMessenger = new Messenger(new Handler() {
        @Override
        public void handleMessage(Message msgFromClint) {
            Log.d(TAG, "handleMessage: 接收到来自客户端的消息");
            // 用来自客户端的消息生成一个要返回给客户端的消息
            Message megToClient = Message.obtain(msgFromClint);
            switch (msgFromClint.what) {
                case MSG_SUM:
                    megToClient.what = MSG_SUM;
                    try {
                        // 模拟耗时操作
                        Thread.sleep(1000);
                        megToClient.arg2 = msgFromClint.arg1 + msgFromClint.arg2;
                        msgFromClint.replyTo.send(megToClient);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
            }
            super.handleMessage(msgFromClint);
        }
    });

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
        Log.d(TAG, "onBind: 服务器发送IBinder对象给客户端");
        return mMessenger.getBinder();
    }
}
