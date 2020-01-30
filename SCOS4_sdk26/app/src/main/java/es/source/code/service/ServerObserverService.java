package es.source.code.service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import java.util.List;

import static android.os.SystemClock.sleep;

public class ServerObserverService extends Service {
    private Messenger mMessenger = new Messenger(new cMessageHandler());
    class cMessageHandler extends Handler {//建 Handler 对象 cMessageHandler，
        @Override
        public void handleMessage(Message msgfromClient) {
            final Message msgToClient = Message.obtain(msgfromClient);
            switch (msgfromClient.what) {
                //msg 客户端传来的消息
                case 1://当传入的 Message 属性 what 值为 1 时：启动多线程模拟接收服务器传回菜品库存信息（菜名称，库存量），
                    msgToClient.what = 10;//如果为运行状态则向SCOS app 进程发送 Message，其 what 值为 10，
                    try {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                sleep(300);
                                ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);/////判断 SCOS app 进程是否在运行状态
                                List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
                                boolean isAppRunning = false;
                                String MY_PKG_NAME = "es.source.code.activity";
                                for (ActivityManager.RunningTaskInfo info : list) {
                                    if (info.topActivity.getPackageName().equals(MY_PKG_NAME) || info.baseActivity.getPackageName().equals(MY_PKG_NAME)) {
                                        isAppRunning = true;
                                        break;
                                    }
                                }
                                if (isAppRunning == true) {
                                    msgToClient.what = 10;
                                }
                            }
                        }).start();
                        Bundle bundle = new Bundle();///////////////从服务器获取到的菜名称和库存量
                        bundle.putStringArray("FoodName", new String[]{"凉拌萝卜丝", "果仁菠菜墩", "糖拌西红柿", "冰糖苦瓜", "香辣卤猪心", "蒜茄子"});
                        bundle.putIntArray("Inventory", new int[]{1, 2, 3, 4, 5, 6});
                        msgToClient.setData(bundle);
                        msgfromClient.replyTo.send(msgToClient);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                case 0://法收到 Message属性 what 值为 0 时，则关闭模拟接收服务器传回菜品库存信息的多线程
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            sleep(300);
                            ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);//判断 SCOS app 进程是否在运行状态
                            List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
                            boolean isAppRunning = false;
                            String MY_PKG_NAME = "es.source.code.activity";
                            for (ActivityManager.RunningTaskInfo info : list) {
                                if (info.topActivity.getPackageName().equals(MY_PKG_NAME) || info.baseActivity.getPackageName().equals(MY_PKG_NAME)) {
                                    isAppRunning = true;
                                    break;
                                }
                            }
                            if (isAppRunning == true) {
                                msgToClient.what = 10;
                            }
                        }
                    }).interrupt();
                    break;
            }
            super.handleMessage(msgfromClient);
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }
}









