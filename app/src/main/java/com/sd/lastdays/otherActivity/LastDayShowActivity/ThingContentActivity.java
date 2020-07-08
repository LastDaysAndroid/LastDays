package com.sd.lastdays.otherActivity.LastDayShowActivity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.sd.lastdays.R;
import com.sd.lastdays.beans.booksBeans.Book;
import com.sd.lastdays.fragment.booksFragment.ThingContentFragment;
import com.sd.lastdays.fragment.countdownDayFragment.DateContentFragment;
import com.sd.lastdays.otherActivity.countdownDayActivity.AmendContentActivity;
import com.sd.lastdays.otherActivity.countdownDayActivity.DateContentActivity;
import com.sd.lastdays.util.ActivityCollector;

import org.litepal.crud.DataSupport;

import java.util.List;

public class ThingContentActivity extends AppCompatActivity {

    public static void actionStart(Context context, String newsTitle, String remainingTime, String targetDate, String account, String lastBook) {
        Intent intent = new Intent(context, ThingContentActivity.class);
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
        setContentView(R.layout.activity_thing_content);
        ActivityCollector.addActivity(this);
        String newsTitle = getIntent().getStringExtra("newsTitle");
        String remainingTime = getIntent().getStringExtra("remainingTime");
        String targetDate = getIntent().getStringExtra("targetDate");
        ThingContentFragment dateContentFragment = (ThingContentFragment) getSupportFragmentManager().findFragmentById(R.id.thing_fragment);
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
//                Intent intent = new Intent(this,BookContentActivity.class);
//                startActivity(intent);
                return true;
            case R.id.add_next_too:
                List<Book> bookList = DataSupport.findAll(Book.class);
                String[] strings = new String[bookList.size()];

                for (int i = 0; i < bookList.size(); i++) {
                    strings[i]=bookList.get(i).getBookTitle();
                    System.out.println(strings[i]);
                }
                new AmendThingActivity().setItems(strings);
                //Toast.makeText(this, "You clicked add_next_too", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(this, AmendThingActivity.class);
                intent2.putExtra("account", getIntent().getStringExtra("account"));
                intent2.putExtra("amend_title", getIntent().getStringExtra("newsTitle"));
                intent2.putExtra("remainingTime", getIntent().getStringExtra("remainingTime"));
                intent2.putExtra("lastBook", getIntent().getStringExtra("lastBook"));
                intent2.putExtra("titleList",strings);
                startActivity(intent2);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}