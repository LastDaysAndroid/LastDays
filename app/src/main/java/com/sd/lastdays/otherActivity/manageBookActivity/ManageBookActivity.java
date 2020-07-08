package com.sd.lastdays.otherActivity.manageBookActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.sd.lastdays.MainActivity;
import com.sd.lastdays.R;
import com.sd.lastdays.fragment.addBookFragment.AddBookFragment;
import com.sd.lastdays.otherActivity.addBookActivity.AddBookActivity;
import com.sd.lastdays.util.ActivityCollector;

public class ManageBookActivity extends AppCompatActivity {

    private static String userAccount = "admin";;

    public String getDateAccount() {
        return userAccount;
    }

    //从广播那里获取到用户账号
    public void setAccount(String account) {
        ManageBookActivity.userAccount = account;
        Log.d("tag",account);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_book);
        ActivityCollector.addActivity(this);
        //添加左上角返回按钮
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Button addBook = (Button)findViewById(R.id.add_new_book);
        addBook.setOnClickListener(new View.OnClickListener() {
            @Override
            //点击添加新倒数本按钮,将fragment进行切换
            public void onClick(View view) {
                if(userAccount.equals("admin")) {
                    Toast.makeText(ManageBookActivity.this,"默认用户不能添加新倒数本",Toast.LENGTH_SHORT).show();
                } else{
                    Intent intent = new Intent(ManageBookActivity.this, AddBookActivity.class);
                    startActivity(intent);
                }

            }
        });


    }



    //返回按钮的点击事件、右上角icon点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}