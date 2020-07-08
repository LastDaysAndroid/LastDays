package com.sd.lastdays.otherActivity.mineActivity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.sd.lastdays.MainActivity;
import com.sd.lastdays.R;
import com.sd.lastdays.beans.mineBeans.Person;
import com.sd.lastdays.fragment.mineFragment.MineFragment;
import com.sd.lastdays.util.ActivityCollector;

import org.litepal.crud.DataSupport;

import java.util.List;

public class MineActivity extends AppCompatActivity {
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, MineActivity.class);
        context.startActivity(intent);
    }

    Person person = new Person();
    String username1 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);
        ActivityCollector.addActivity(this);
        Button login = (Button) findViewById(R.id.login);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.loading);
        MineFragment mineFragment = new MineFragment();
        EditText username = (EditText) findViewById(R.id.username);
        EditText password = (EditText) findViewById(R.id.password);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (progressBar.getVisibility() == View.GONE) {
                    progressBar.setVisibility(View.VISIBLE);
                }
                username1 = username.getText().toString();
                String password1 = password.getText().toString();
                List<Person> people = DataSupport.where("account=?", username1).find(Person.class);
                if ("".equals(username1) || "".equals(password1)) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(MineActivity.this, "输入的信息不完整", Toast.LENGTH_SHORT).show();
                } else if("admin".equals(username1)){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(MineActivity.this, "默认账户无法注册", Toast.LENGTH_SHORT).show();
                }else {
                    if (people.isEmpty()) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(MineActivity.this);
                        alert.setTitle("账号不存在是否进行注册");
                        alert.setMessage("点击确认即可完成注册");
                        alert.setCancelable(false);
                        alert.setPositiveButton("注册", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                person.setAccount(username1);
                                person.setPassWord(password1);
                                person.setImageUri("default");
                                person.save();
                                Toast.makeText(MineActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                Intent my_broadcast = new Intent("ACCOUNNT_BROADCAST");
                                my_broadcast.putExtra("account", person.getAccount());
                                sendBroadcast(my_broadcast);
                                Intent intent2 = new Intent(MineActivity.this, MainActivity.class);
                                startActivity(intent2);
                            }
                        });
                        alert.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MineActivity.this, "取消成功", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        });
                        alert.show();
                    } else {
                        Toast.makeText(MineActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        //mineFragment.refresh_view(username1);
                        Intent my_broadcast = new Intent("ACCOUNNT_BROADCAST");
                        my_broadcast.putExtra("account", username1);
                        sendBroadcast(my_broadcast);
                        Intent intent2 = new Intent(MineActivity.this, MainActivity.class);
                        startActivity(intent2);
                    }
                }
            }
        });
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                return true;
            /*case R.id.add_next_too:
                //Toast.makeText(this, "You clicked add_next_too", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(this, AmendContentActivity.class);
                intent2.putExtra("account",getIntent().getStringExtra("account"));
                intent2.putExtra("amend_title",getIntent().getStringExtra("newsTitle"));
                intent2.putExtra("remainingTime",getIntent().getStringExtra("remainingTime"));
                intent2.putExtra("lastBook",getIntent().getStringExtra("lastBook"));
                startActivity(intent2);
                break;*/
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}