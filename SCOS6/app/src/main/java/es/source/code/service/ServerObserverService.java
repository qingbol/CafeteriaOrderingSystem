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
import android.util.Log;

import java.util.List;

import es.source.code.data.HotFoodData;

public class ServerObserverService extends Service {

    private Messenger FoodViewMessenger;
    private Messenger ServiceMessenger;

    private Handler cMessageHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    getserverdatathread.stop();
                    break;
                case 1:
                    getserverdatathread.start();
                    break;
            }
            super.handleMessage(msg);
            ServiceMessenger = new Messenger(cMessageHandler);
        }
    };

    @Override
    public IBinder onBind(final Intent intent) {
        Log.v("ServerObserverService","service bind");
        return ServiceMessenger.getBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.print("Service Create!");

    }

    Thread getserverdatathread = new Thread(new Runnable() {
        @Override
        public void run() {
            HotFoodData hotFoodData = new HotFoodData();
            Message msg = cMessageHandler.obtainMessage();
            Bundle serverdata = new Bundle();
            for (int i=0;i<hotFoodData.getHotfoodname().length;i++){
                serverdata.putString("foodname",hotFoodData.getHotfoodname()[i]);
                serverdata.putInt("foodnumber",hotFoodData.getHotfoodnum()[i]);
                msg.setData(serverdata);
                if (getAppSates(getApplicationContext(),"es.source.code")){
                msg.what = 10;
                try {
                    ServiceMessenger.send(msg);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                }
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    });

    /**
     * 返回app运行状态
     * 1:程序在前台运行
     * 2:程序在后台运行
     * 3:程序未启动
     * 注意：需要配置权限<uses-permission android:name="android.permission.GET_TASKS" />
     */
    public boolean getAppSates(Context context, String pageName) {

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(20);

        //判断程序是否在栈顶
        if (list.get(0).topActivity.getPackageName().equals(pageName)) {
            return true;
        } else {
            //判断程序是否在栈里
            for (ActivityManager.RunningTaskInfo info : list) {
                if (info.topActivity.getPackageName().equals(pageName)) {
                    return true;
                }
            }
            return false;//栈里找不到，返回false
        }
    }
}
