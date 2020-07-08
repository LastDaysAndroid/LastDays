package com.sd.lastdays.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.sd.lastdays.beans.countdownDayBeans.DateContent;
import com.sd.lastdays.fragment.countdownDayFragment.DateContentTitleFragment;
import com.sd.lastdays.util.DateContentTitleData;

import org.litepal.crud.DataSupport;

import java.util.Calendar;
import java.util.List;

/**
 * 定时服务将数据库里的时间数据减少一天
 */
public class AutoUpdateTimeService extends Service {
    public AutoUpdateTimeService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final String[] account = {""};
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<DateContent> dateContents = DataSupport.findAll(DateContent.class);
                for (DateContent dateContent : dateContents) {
                    int i = Integer.parseInt(dateContent.getRemainingTime()) - 1;
                    dateContent.setRemainingTime(String.valueOf(i));
                    dateContent.save();
                    // System.out.println("data" + dateContent.getRemainingTime());
                }
                DateContentTitleFragment fragment = new DateContentTitleFragment();
                List<DateContent> list = fragment.getList();
                for (DateContent date : list
                ) {
                    account[0] = date.getAccount();
                }
                fragment.setList(new DateContentTitleData().getNews(account[0]));
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }
}
