package es.source.code.br;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import es.source.code.service.UpdateService;

public class DeviceStartedListener extends BroadcastReceiver {
    public DeviceStartedListener() {
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("TAG", "开机自动服务自动启动.....");
        //后边的XXX.class就是要启动的服务
        Intent serviceIntent = new Intent(context, UpdateService.class);
        context.startService(serviceIntent);
        System.out.println( "开机自动服务自动启动.....");
    }
}
