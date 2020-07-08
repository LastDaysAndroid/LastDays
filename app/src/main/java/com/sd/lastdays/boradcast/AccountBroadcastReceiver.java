package com.sd.lastdays.boradcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.sd.lastdays.MainActivity;
import com.sd.lastdays.fragment.addBookFragment.AddBookFragment;
import com.sd.lastdays.fragment.booksFragment.BookContentFragment;
import com.sd.lastdays.fragment.booksFragment.BookListFragment;
import com.sd.lastdays.fragment.countdownDayFragment.DateContentTitleFragment;
import com.sd.lastdays.fragment.manageBookFragment.ManagerBookFragment;
import com.sd.lastdays.otherActivity.LastDayShowActivity.AddThingActivity;
import com.sd.lastdays.otherActivity.countdownDayActivity.AddContentActivity;
import com.sd.lastdays.otherActivity.manageBookActivity.ManageBookActivity;

/**
 * 登陆后账号的标准广播接收器
 */
public class AccountBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String account = intent.getStringExtra("account");
        //Toast.makeText(context, "account广播接收成功"+account, Toast.LENGTH_SHORT).show();
        new AddContentActivity().setAccount_add(account);
        new DateContentTitleFragment().setDateAccount(account);
        new MainActivity().setAccount_add(account);
        new BookListFragment().setDateAccount(account);
        new BookContentFragment().setDateAccount(account);
        new BookContentFragment().setDateAccount(account);
        new ManagerBookFragment().setAccount(account);
        new AddBookFragment().setAccount(account);
        new AddThingActivity().setAccount_add(account);
        new ManageBookActivity().setAccount(account);
    }
}