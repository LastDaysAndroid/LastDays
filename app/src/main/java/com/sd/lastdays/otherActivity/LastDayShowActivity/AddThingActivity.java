package com.sd.lastdays.otherActivity.LastDayShowActivity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.sd.lastdays.MainActivity;
import com.sd.lastdays.R;
import com.sd.lastdays.beans.booksBeans.Book;
import com.sd.lastdays.beans.countdownDayBeans.DateContent;
import com.sd.lastdays.otherActivity.countdownDayActivity.AddContentActivity;
import com.sd.lastdays.util.ActivityCollector;
import com.sd.lastdays.util.JudgmentDay;

import org.litepal.crud.DataSupport;

import java.util.Calendar;
import java.util.List;

public class AddThingActivity extends AppCompatActivity {

    private String bookName;//从BookContentFragment获取
    private  static String[] items;
    int y = 0, m = 0, d = 0;//选择的时间
    String data_time, addTitle2;
    private static String account_add = "admin";//接收的账户信息

    public String getAccount_add() {
        return account_add;
    }

    public void setAccount_add(String account_add) {
        AddThingActivity.account_add = account_add;
    }

    public String[] getItems() {
        return items;
    }
    public void setItems(String[] items) {
        AddThingActivity.items = items;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_thing);
        setTitle("添加事件");
        ActivityCollector.addActivity(this);

        bookName = getIntent().getStringExtra("bookname");
        System.out.println("得到bookname："+bookName);
        EditText addTitle = (EditText) findViewById(R.id.add_title);
        //时间
        DatePicker dataPicker = (DatePicker) findViewById(R.id.dataPicker);
        //获取当前时间
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);//年
        int month = calendar.get(Calendar.MONTH) + 1;//月
        int day = calendar.get(Calendar.DAY_OF_MONTH);//日
        /*天数差*/
        dataPicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                //获取选中的年月日
                AddThingActivity.this.y = year;
                //月份是从0开始的
                AddThingActivity.this.m = (monthOfYear + 1);
                AddThingActivity.this.d = dayOfMonth;
                data_time = y + "-" + m + "-" + d;
                System.out.println("data_time:"+data_time);
                //弹窗显示
                //Toast.makeText(AddContentActivity.this,AddContentActivity.this.y+"年"+AddContentActivity.this.m+"月"+AddContentActivity.this.d+"日",Toast.LENGTH_SHORT).show();
            }
        });

        Button saveButton = (Button) findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTitle2 = addTitle.getText().toString();
                int days = (y * 365 + m * (new JudgmentDay().judgmentDay(y, m)) + d) - (year * 365 + month * (new JudgmentDay().judgmentDay(year, month)) + day);
                DateContent content = new DateContent();
                List<DateContent> dateContents1 = DataSupport.where("title=?", addTitle2).find(DateContent.class);
                if ("".equals(addTitle2) || "".equals(data_time)) {
                    Toast.makeText(AddThingActivity.this, "内容未填写完整", Toast.LENGTH_SHORT).show();
                } else if (!dateContents1.isEmpty()) {
                    Toast.makeText(AddThingActivity.this, "已存在相同标题的事件", Toast.LENGTH_SHORT).show();
                } else if ("admin".equals(account_add)) {
                    Toast.makeText(AddThingActivity.this, "未登录，添加失败", Toast.LENGTH_SHORT).show();
                } else {
                    content.setTitle(addTitle2 + "——>还有");
                    content.setTargetDate("目标日期：" + data_time);
                    content.setLastBook(bookName);
                    content.setRemainingTime(String.valueOf(days));
                    content.setAccount(account_add);
                    content.save();
                    Toast.makeText(AddThingActivity.this, "事件添加成功", Toast.LENGTH_SHORT).show();
                    finish();
                    back(bookName);
                }
            }
        });

        //添加左上角返回按钮
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
    private void back(String name) {
        Intent intent = new Intent(this,BookContentActivity.class);
        intent.putExtra("bookname",name);
        startActivity(intent);
    }

    //返回按钮的点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
//                Intent intent = new Intent(this, MainActivity.class);
//                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}