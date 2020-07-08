package com.sd.lastdays.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.util.Calendar;

/**
 * 对当前时间的获取设置定时任务
 */
public class UpdateTimeService extends Service {
    public UpdateTimeService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //获取当前时间
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);//24小时制
        int minute = calendar.get(Calendar.MINUTE);//分钟
        Intent intent2 = new Intent(getApplicationContext(), UpdateTimeService.class);
        if ((hour==0)&&(minute>31||minute<60)){//0-23
            startService(intent2);
        }else{
            stopService(intent2);
        }
        //31分钟执行一次
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int anHour =1 * 31 * 60 * 1000;
        long triggerAtTime = System.currentTimeMillis() + anHour;
        Intent i = new Intent(this, AutoUpdateTimeService.class);
        PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
        manager.setExact(AlarmManager.RTC_WAKEUP, triggerAtTime, pi);
        return super.onStartCommand(intent, flags, startId);
    }
}
