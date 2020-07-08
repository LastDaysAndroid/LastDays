package com.sd.lastdays;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.next.easynavigation.view.EasyNavigationBar;
import com.sd.lastdays.beans.booksBeans.Book;
import com.sd.lastdays.beans.mineBeans.Person;
import com.sd.lastdays.fragment.booksFragment.BookListFragment;
import com.sd.lastdays.fragment.countdownDayFragment.DateContentTitleFragment;
import com.sd.lastdays.fragment.historyDayFragment.HistoryDayFragment;
import com.sd.lastdays.fragment.mineFragment.MineFragment;
import com.sd.lastdays.otherActivity.countdownDayActivity.AddContentActivity;
import com.sd.lastdays.otherActivity.manageBookActivity.ManageBookActivity;
import com.sd.lastdays.service.UpdateTimeService;
import com.sd.lastdays.util.ActivityCollector;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    //右上角的图标菜单
    int j = 0, k = 0;
    Menu menu1;
    //=========底部导航栏
    private EasyNavigationBar navigationBar;
    //导航栏标题（未添加）
    private String[] tabText = {"1", "2", "3", "4"};
    //未选中icon
    private int[] normalIcon = {R.mipmap.index, R.mipmap.find, R.mipmap.message, R.mipmap.me};
    //选中时icon
    private int[] selectIcon = {R.mipmap.index1, R.mipmap.find1, R.mipmap.message1, R.mipmap.me1};
    private List<Fragment> fragments = new ArrayList<>();
    //======
    //=========左上角菜单项
    private DrawerLayout drawerLayout;
    //======菜单显示的图片
    private CircleImageView circleImageView;
    //接收账户信息
    private static String account_add = "";//接收的账户信息
    public String getAccount_add() {
        return account_add;
    }
    public void setAccount_add(String account_add) {
        MainActivity.account_add = account_add;
    }

    //活动的7个回调方法
    /*活动第一次创建的时候被调用，完成活动的初始化操作，加载布局、绑定事件*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //加载活动的布局，传入布局文件的id，项目中任何一个资源都会在R文件中生成相应的id
        setContentView(R.layout.activity_main);
        ActivityCollector.addActivity(this);
        //============底部导航栏设置
        navigationBar = findViewById(R.id.navigationBar);
        fragments.add(new DateContentTitleFragment());
        fragments.add(new BookListFragment());//导航栏第二个按钮点击进入对应碎片
        fragments.add(new HistoryDayFragment());//导航栏第三个按钮点击进入对应碎片
        fragments.add(new MineFragment());
        navigationBar
                //.titleItems(tabText) //设置导航栏下方标题
                .normalIconItems(normalIcon)
                .selectIconItems(selectIcon)
                .fragmentList(fragments)
                .fragmentManager(getSupportFragmentManager())
                //.navigationBackground(Color.parseColor("#000"))//导航栏背景色
                //.iconSize(50)     //Tab图标大小
                .canScroll(true)
                .build();
        navigationBar.setOnTabClickListener(new EasyNavigationBar.OnTabClickListener() {
            @Override
            public boolean onTabSelectEvent(View view, int position) {
                if (position == 0) {
                    menu1.clear();
                    getMenuInflater().inflate(R.menu.toolbar, menu1);
                    setTitle("Last Days · 倒数日");
                }
                if (position == 1) {
                    menu1.clear();
                    getMenuInflater().inflate(R.menu.toolbar2, menu1);
                    setTitle("倒数本");
                }
                if (position == 2) {
                    menu1.clear();
                    getMenuInflater().inflate(R.menu.toolbar3, menu1);
                    setTitle("历史上的今天");
                }
                if (position == 3) {
                    menu1.clear();
                    setTitle("我的倒数日");
                    //getMenuInflater().inflate(R.menu.toolbar4, menu1);
                }
                return false;
            }

            @Override
            public boolean onTabReSelectEvent(View view, int position) {
                return false;
            }
        });
        //============
        //=========左上角菜单项
        drawerLayout = (DrawerLayout) findViewById(R.id.layout_bottom);
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        //navView.setItemIconTintList(null);设置图标以原来的颜色显示
        ActionBar actionBar = getSupportActionBar();//得到actionBar的实例
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);//显示菜单按钮
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);//修改默认样式
        }
        navView.setCheckedItem(R.id.nav_call);//设置Call为默认选中
        //菜单子项点击事件监听器
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_call:
                        drawerLayout.closeDrawers();
                        setTitle("Last Days · 倒数日");
                        break;
                    case R.id.nav_friends:
                        drawerLayout.closeDrawers();
                        setTitle("Last Days · 倒数日");
                        break;
                    case R.id.nav_mail:
                        //跳转到对应的页面
                        navigationBar.selectTab(1, false);
                        drawerLayout.closeDrawers();
                        setTitle("倒数本");
                        break;
                    case R.id.nav_location:
                        //跳转到对应的页面
                        navigationBar.selectTab(2, false);
                        drawerLayout.closeDrawers();
                        setTitle("历史上的今天");
                        break;
                    case R.id.nav_task:
                        //跳转到对应的页面
                        navigationBar.selectTab(3, false);
                        drawerLayout.closeDrawers();
                        setTitle("我的倒数日");
                        break;
                    case R.id.select_image:
                        selectImage();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        //======
        //===========自定义更新数据库剩余时间服务激活
        //Context context = getApplicationContext();
         Intent intent = new Intent(this, UpdateTimeService.class);
         startService(intent);
        // ============
        //==========设置头像
    }


    //活动由不可见变为可见时调用
    @Override
    protected void onStart() {
        super.onStart();
    }

    //===========与用户交互可见
    //活动准备好和用户进行交互时调用，此时活动位于栈顶
    @Override
    protected void onResume() {
        super.onResume();
    }

    //系统去启动或恢复另一个活动时调用 释放资源、保存数据
    @Override
    protected void onPause() {
        super.onPause();
    }

    //==========
    //活动完全不可见时调用，活动是对话框时不会调用
    @Override
    protected void onStop() {
        super.onStop();
    }

    //活动被重新启动时调用
    @Override
    protected void onRestart() {
        super.onRestart();
    }

    //活动销毁之前调用
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //当活动被回收之前肯定会调用此方法，用来保存临时数据，类似Intent
    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        /*String name = "123";
        bundle.putString("data_key",name);
        在oncreate中取出
        if(savedInstanceState!=null){
            String name = savedInstanceState.getStirng("data_key);
            Log.d(TAG,name);
        }
        */

    }

    //=========对右上角菜单项的点击事件进行处理
    int i = 1,q=0;
private static int card_id=1;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.card:
                if (card_id==1){
                    Intent card = new Intent("CARD");
                    card.putExtra("card", "card");
                    sendBroadcast(card);
                    card_id--;
                }else if (card_id==0){
                    Intent card = new Intent("CARD");
                    card.putExtra("card", "nocard");
                    sendBroadcast(card);
                    card_id++;
                }
                Intent intent1 = new Intent(this, MainActivity.class);
                startActivity(intent1);
                break;
            case R.id.add:
                //Toast.makeText(this, "You clicked add", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, AddContentActivity.class);
                startActivity(intent);
                break;
            case R.id.manage:
                Intent intent2 = new Intent(this, ManageBookActivity.class);
                startActivity(intent2);
                break;
            case android.R.id.home:
                if (i == 1) {
                    drawerLayout.openDrawer(GravityCompat.START);
                    if (q==0){//q设置只能运行一次，即在第一次打开菜单时
                        //判断有没有权限，没有权限获取，有看看数据库里面有没有设置，没有默认
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!=
                                PackageManager.PERMISSION_GRANTED){
                            ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE }, 1);
                        }else{
                            if (!"".equals(account_add)) {
                                List<Person> people = DataSupport.where("account=?", account_add).find(Person.class);
                                if (!people.isEmpty()){
                                    for (Person person:people) {
                                        String imageUri = person.getImageUri();
                                        if (!"default".equals(imageUri)){
                                            circleImageView=(CircleImageView) findViewById(R.id.icon_image);
                                            circleImageView.setImageURI(Uri.parse(imageUri));
                                        }
                                    }
                                }
                            }
                        }
                        q++;
                    }
                    i--;
                } else {
                    drawerLayout.closeDrawers();
                    i++;
                }
                break;
            default:
        }
        return true;
    }

    //======
    //=======右上角菜单栏
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu1 = menu;
        getMenuInflater().inflate(R.menu.toolbar, menu1);
        return true;
    }

//==========点击图片从图库中选择更换
    public void selectImage() {
        // 打开系统的图库 获取一个图片
        /*
         * <intent-filter> <action android:name="android.intent.action.PICK" />
         * <category android:name="android.intent.category.DEFAULT" /> <data
         * android:mimeType="image/*" /> <data android:mimeType="video/*" />
         * </intent-filter>
         */
        Intent intent = new Intent();
        intent.setAction("android.intent.action.PICK");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setType("image/*");
        startActivityForResult(intent, 0);
    }
    //从图库返回，回调数据的处理
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        circleImageView=(CircleImageView) findViewById(R.id.icon_image);
        Person person = new Person();
        if ("".equals(account_add)){
            Toast.makeText(MainActivity.this,"请先进行登录操作！",Toast.LENGTH_SHORT).show();
        }else{
            if (data != null) {
                // 得到点击图片的uri
                Uri uri = data.getData();
                person.setImageUri(uri.toString());
                person.updateAll("account=?",account_add);
                circleImageView.setImageURI(uri);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}