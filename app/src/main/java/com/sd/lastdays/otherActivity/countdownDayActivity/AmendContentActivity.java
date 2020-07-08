package com.sd.lastdays.otherActivity.countdownDayActivity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
import com.sd.lastdays.util.ActivityCollector;
import com.sd.lastdays.util.JudgmentDay;

import org.litepal.crud.DataSupport;

import java.util.Calendar;
import java.util.List;

/**
 * 倒数日内容修改/删除
 */
public class AmendContentActivity extends AppCompatActivity {
    private AlertDialog alertDialog2; //单选框
    private String singleSelect;
    private String[] items;
    int y = 0, m = 0, d = 0;//选择的时间
    String data_time;
    int lastBook_k = 0;
    String account = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.amend_content_frag);
        ActivityCollector.addActivity(this);
        Intent intent = getIntent();
        account = intent.getStringExtra("account");

        if (!"admin".equals(account)){
            int i=0;
            List<Book> books = DataSupport.where("account=?", account).find(Book.class);
            items = new String[books.size()];
            for (Book book:books) {
                items[i]=book.getBookTitle();
                i++;
            }
        }else{
            items= new String[]{"倒数本A"};
        }

        String amend_title = intent.getStringExtra("amend_title");
        String remainingTime = intent.getStringExtra("remainingTime");
        String lastBook = intent.getStringExtra("lastBook");
        for (int i = 0; i < items.length; i++) {
            if (items[i].equals(lastBook)) {
                lastBook_k = i;
            }
        }
        EditText amend_title2 = (EditText) findViewById(R.id.amend_title);
        amend_title2.setText(amend_title);
        //时间
        DatePicker dataPicker = (DatePicker) findViewById(R.id.dataPicker2);
        //获取当前时间
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);//年
        int month = calendar.get(Calendar.MONTH) + 1;//月
        int day = calendar.get(Calendar.DAY_OF_MONTH);//日
        /*天数差*/
        final int[] k = {0};
        dataPicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                //获取选中的年月日
                AmendContentActivity.this.y = year;
                //月份是从0开始的
                AmendContentActivity.this.m = (monthOfYear + 1);
                AmendContentActivity.this.d = dayOfMonth;
                data_time = y + "-" + m + "-" + d;
                k[0]++;
                //弹窗显示
                //Toast.makeText(AmendContentActivity.this,AmendContentActivity.this.y+"年"+AmendContentActivity.this.m+"月"+AmendContentActivity.this.d+"日",Toast.LENGTH_SHORT).show();
            }
        });
        //修改按钮
        Button amendButton = (Button) findViewById(R.id.amend_button);
        amendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amend_title3 = amend_title2.getText().toString();
                int days = (y * 365 + m * (new JudgmentDay().judgmentDay(y, m)) + d) - (year * 365 + month * (new JudgmentDay().judgmentDay(year, month)) + day);
                DateContent content = new DateContent();
                if ("".equals(amend_title3) || "".equals(data_time) || "".equals(singleSelect)) {
                    Toast.makeText(AmendContentActivity.this, "内容未填写完整", Toast.LENGTH_SHORT).show();
                } else {
                    content.setTitle(amend_title3);
                    content.setTargetDate(data_time);
                    content.setLastBook(singleSelect);
                    if (k[0] > 0) {
                        content.setRemainingTime(String.valueOf(days));
                    } else {
                        content.setRemainingTime(remainingTime);
                    }
                    content.setAccount(account);
                    content.updateAll("title =?", amend_title);
                    /*content.updateAll("title=? and remainingTime=? and targetDate=? and LastBook=? and account=?",
                            amend_title3,String.valueOf(days),data_time,singleSelect,account);*/
                    Toast.makeText(AmendContentActivity.this, "事件修改成功", Toast.LENGTH_SHORT).show();
                    finish();
                    Intent intent = new Intent(AmendContentActivity.this, MainActivity.class);
                    startActivity(intent);
                }

            }
        });
        Button delectButton = (Button) findViewById(R.id.delect_button);
        delectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("admin".equals(account)) {
                   Toast.makeText(AmendContentActivity.this, "测试账户无法删除，只可修改", Toast.LENGTH_SHORT).show();
                } else {
                    DataSupport.deleteAll(DateContent.class, "title=?", amend_title);
                    Toast.makeText(AmendContentActivity.this, "事件删除成功", Toast.LENGTH_SHORT).show();
                    finish();
                    Intent intent = new Intent(AmendContentActivity.this, MainActivity.class);
                    startActivity(intent);
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

    int j;

    //单选框按钮——选择倒数本
    public void showSingleAlertDialog(View view) {

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("这是单选框");
        alertBuilder.setSingleChoiceItems(items, lastBook_k, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                j = i;
                //Toast.makeText(AmendContentActivity.this, items[i], Toast.LENGTH_SHORT).show();
            }
        });

        alertBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                singleSelect = items[j];
                alertDialog2.dismiss();
            }
        });
        alertBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog2.dismiss();
            }
        });
        alertDialog2 = alertBuilder.create();
        alertDialog2.show();
    }

    //返回按钮的点击事件、右上角icon点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
               /* Intent intent = new Intent(this, DateContentActivity.class);
                startActivity(intent);*/
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}