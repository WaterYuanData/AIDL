package com.example.aidlservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class RoomService extends Service {
    public RoomService() {
    }

    // 对客户端提供进程间通信的接口的实现
    public IDataInterface.Stub mBinder = new IDataInterface.Stub() {
        @Override
        public int getRoomNum() throws RemoteException {
            return 3008;
        }
    };

    // 作为binder对象，提供给客户端调用
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
        return mBinder;
    }
}
