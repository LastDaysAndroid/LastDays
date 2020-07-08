package com.sd.lastdays.otherActivity.countdownDayActivity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.sd.lastdays.R;
import com.sd.lastdays.fragment.countdownDayFragment.DateContentFragment;
import com.sd.lastdays.util.ActivityCollector;

public class DateContentActivity extends AppCompatActivity {

    public static void actionStart(Context context, String newsTitle, String remainingTime, String targetDate, String account, String lastBook) {
        Intent intent = new Intent(context, DateContentActivity.class);
        intent.putExtra("newsTitle", newsTitle);
        intent.putExtra("remainingTime", remainingTime);
        intent.putExtra("targetDate", targetDate);
        intent.putExtra("account", account);
        intent.putExtra("lastBook", lastBook);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_date_content);
        ActivityCollector.addActivity(this);
        String newsTitle = getIntent().getStringExtra("newsTitle");
        String remainingTime = getIntent().getStringExtra("remainingTime");
        String targetDate = getIntent().getStringExtra("targetDate");
        DateContentFragment dateContentFragment = (DateContentFragment) getSupportFragmentManager().findFragmentById(R.id.news_dateContent_layout);
        dateContentFragment.refresh(newsTitle, remainingTime, targetDate); // dateContentFragment
        //添加左上角返回按钮
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    //=======右上角菜单栏
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.icon_datecontent, menu);
        return true;
    }

    //返回按钮的点击事件、右上角icon点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                return true;
            case R.id.add_next_too:
                //Toast.makeText(this, "You clicked add_next_too", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(this, AmendContentActivity.class);
                intent2.putExtra("account", getIntent().getStringExtra("account"));
                intent2.putExtra("amend_title", getIntent().getStringExtra("newsTitle"));
                intent2.putExtra("remainingTime", getIntent().getStringExtra("remainingTime"));
                intent2.putExtra("lastBook", getIntent().getStringExtra("lastBook"));
                startActivity(intent2);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}