package com.kborid.setting.messenger;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import androidx.annotation.Nullable;

public class TestMessengerServer extends Service {

    private Messenger serviceMessenger = new Messenger(new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            System.out.println("From Client");
            String mm = (String) msg.obj;
            System.out.println(mm);

            Message m = Message.obtain();
            m.obj = "yes, i received client's msg";
            try {
                msg.replyTo.send(m);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    });

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return serviceMessenger.getBinder();
    }
}
