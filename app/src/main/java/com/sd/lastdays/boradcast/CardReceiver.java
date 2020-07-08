package com.sd.lastdays.boradcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.sd.lastdays.fragment.countdownDayFragment.DateContentTitleFragment;
/**
 * 切换显示列表为图片的标准广播接收器
 */
public class CardReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String card = intent.getStringExtra("card");
        new DateContentTitleFragment().setCard(card);
    }
}
