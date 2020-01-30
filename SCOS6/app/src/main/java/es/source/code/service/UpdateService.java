package es.source.code.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import es.source.code.R;
import es.source.code.Utils.HttpURLUtils;
import es.source.code.activity.FoodDetailed;
import es.source.code.activity.MainScreen;
import es.source.code.br.NotificationBroadcastReceiver;

/**
 * Created by SamChen on 2017/10/31.
 */

public class UpdateService extends IntentService {
    private static final String urlPath = "http://153.34.235.56:8080/SCOSServer/FoodUpdateService";

    private String foodname;
    private int foodnumber;
    private double foodprice;
    private String foodtype;
    private String foodmessage;
    int jsonArraylength;

    /*Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            newfoodmessage = msg.obj.toString();
            Log.v("NEW FOOD MESSAGE:",newfoodmessage);
            super.handleMessage(msg);
        }
    };*/

    public UpdateService() {
        super(null);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //LayoutInflater inflater = LayoutInflater.from(this);
        //View notification_view = inflater.inflate(R.layout.notification_layout,null);
        //tv_foodupdate = (TextView) notification_view.findViewById(R.id.tv_foodupdate);

        try {
            URL url = new URL(urlPath);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                String result = HttpURLUtils.getJsonData(inputStream);
                try {
                    JSONArray jsonArray = new JSONArray(result);
                    jsonArraylength = jsonArray.length();
                    System.out.println("json数组长度:" + jsonArraylength);
                    for (int i = 0; i < jsonArraylength; i++) {
                        foodmessage = JSONAnalysis(jsonArray, i);
                        Log.v("NEW FOOD MESSAGE:", foodmessage);

                        RemoteViews notificationView = new RemoteViews(this.getPackageName(), R.layout.notification_layout);
                        notificationView.setTextViewText(R.id.tv_foodupdate, foodmessage);


                        String ns = Context.NOTIFICATION_SERVICE;
                        NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);

                        Notification.Builder notification = new Notification.Builder(this);
                        notification.setContent(notificationView);
                        //添加图标
                        notification.setSmallIcon(R.mipmap.hongshaorou);
                        //添加头目
                        notification.setTicker("新品通知");
                        //添加标题
                        notification.setContentTitle("开机新通知");
                        //添加内容
                        notification.setContentText("新品上架：红烧牛肉，￥55，热菜");

                        //消息发送时间
                        notification.setWhen(System.currentTimeMillis());
                        //设置默认的提示音，振动方式，灯光
                        notification.setDefaults(Notification.DEFAULT_ALL);

                        Intent cancelintent = new Intent(this, NotificationBroadcastReceiver.class);
                        cancelintent.setAction("notification_cancel");
                        PendingIntent pendingIntentClick = PendingIntent.getBroadcast(this, 0, cancelintent, PendingIntent.FLAG_ONE_SHOT);
                        notificationView.setOnClickPendingIntent(R.id.bt_notificationButton, pendingIntentClick);

                        //设置通知的事件消息
                        Intent notificationIntent = new Intent(this, MainScreen.class);//点击后通知要跳转的的页面

                        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
                        notification.setContentIntent(contentIntent);

                        Notification notification1 = notification.build();
                        //通过通知管理器发送通知
                        mNotificationManager.notify(0, notification1);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        /*RemoteViews notificationView = new RemoteViews(this.getPackageName(), R.layout.notification_layout);
        notificationView.setTextViewText(R.id.tv_foodupdate, foodmessage);


        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);

        Notification.Builder notification = new Notification.Builder(this);
        notification.setContent(notificationView);
        //添加图标
        notification.setSmallIcon(R.mipmap.hongshaorou);
        //添加头目
        notification.setTicker("新品通知");
        //添加标题
        notification.setContentTitle("开机新通知");
        //添加内容
        notification.setContentText("新品上架：红烧牛肉，￥55，热菜");

        //消息发送时间
        notification.setWhen(System.currentTimeMillis());
        //设置默认的提示音，振动方式，灯光
        notification.setDefaults(Notification.DEFAULT_ALL);

        Intent cancelintent = new Intent(this, NotificationBroadcastReceiver.class);
        cancelintent.setAction("notification_cancel");
        PendingIntent pendingIntentClick = PendingIntent.getBroadcast(this, 0, cancelintent, PendingIntent.FLAG_ONE_SHOT);
        notificationView.setOnClickPendingIntent(R.id.bt_notificationButton, pendingIntentClick);

        //设置通知的事件消息
        Intent notificationIntent = new Intent(this, MainScreen.class);//点击后通知要跳转的的页面

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        notification.setContentIntent(contentIntent);

        Notification notification1 = notification.build();
        //通过通知管理器发送通知
        mNotificationManager.notify(0, notification1);*/
    }

    //JSON解析
    public String JSONAnalysis(JSONArray jsonArray, int index) {
        /*try {
            JSONObject foodJson = new JSONObject(string);
            foodname = foodJson.getString("foodname");
            foodnumber = foodJson.getInt("foodnumber");
            foodprice = foodJson.getDouble("foodprice");
            foodtype = foodJson.getString("foodtype");
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        String content = null;
        try {
            JSONObject foodJson = jsonArray.getJSONObject(index);
            System.out.println("--------------------------");
            foodname = foodJson.getString("foodname");
            System.out.println("菜名:" + foodname);
            foodnumber = foodJson.getInt("foodnumber");
            System.out.println("数量:" + foodnumber);
            foodprice = foodJson.getDouble("foodprice");
            System.out.println("价格:" + foodprice);
            foodtype = foodJson.getString("foodtype");
            System.out.println("种类:" + foodtype);
            content = "新品上架:" + foodname + "," + foodprice + "元," + foodtype + "," + foodnumber + "份";
            System.out.println(content);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return content;
    }

}
