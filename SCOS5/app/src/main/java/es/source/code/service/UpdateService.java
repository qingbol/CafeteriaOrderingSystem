package es.source.code.service;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import es.source.code.activity.MainScreen;
import es.source.code.activity.R;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */



public class UpdateService extends IntentService {
    public UpdateService() {
        super("UpdateService");
        System.out.println( "开机自动服务自动启动.....");
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        /*实例化NotificationManager以获取系统服务*/
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Uri ringUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        //点击的意图ACTION是跳转到Intent
        Intent resultIntent = new Intent(this, MainScreen.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.image1);
        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(this)
            /*设置large icon*/
                .setLargeIcon(bitmap)
             /*设置small icon*/
                .setSmallIcon(R.drawable.img2)
            /*设置title*/
                .setContentTitle("通知")
            /*设置详细文本*/
                .setContentText("新品上架：糖拌西红柿，¥5，凉菜")
             /*设置发出通知的时间为发出通知时的系统时间*/
                .setWhen(System.currentTimeMillis())
            /* 可用于添加常驻通知，必须调用cancle方法来清除*/
                .setOngoing(true)
             /*设置点击后通知消失*/
                .setAutoCancel(true)
             /*设置通知数量的显示类似于QQ那种，用于同志的合并*/
                .setNumber(2)
                .setSound(ringUri)
             /*点击跳转到MainActivity*/
                .setContentIntent(pendingIntent);

        manager.notify(121, notifyBuilder.build());
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d("Demo","onDestroy 已执行");
    }
}






