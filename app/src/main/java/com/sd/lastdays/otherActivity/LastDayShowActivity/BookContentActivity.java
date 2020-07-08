package com.sd.lastdays.otherActivity.LastDayShowActivity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.sd.lastdays.MainActivity;
import com.sd.lastdays.R;
import com.sd.lastdays.beans.booksBeans.Book;
import com.sd.lastdays.beans.countdownDayBeans.DateContent;
import com.sd.lastdays.fragment.booksFragment.BookContentFragment;
import com.sd.lastdays.fragment.countdownDayFragment.DateContentFragment;
import com.sd.lastdays.otherActivity.countdownDayActivity.AddContentActivity;
import com.sd.lastdays.util.ActivityCollector;
import com.sd.lastdays.util.DateContentTitleData;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.List;

public class BookContentActivity extends AppCompatActivity {

    private static String dateAccount = "admin";

    public String getDateAccount() {
        return dateAccount;
    }

    public void setDateAccount(String dateAccount) {
        BookContentActivity.dateAccount = dateAccount;
    }

    private List<DateContent> list ;

    public List<DateContent> getList() {
        return list;
    }

    public void setList(List<DateContent> list) {
        this.list = list;
    }

    public static void actionStart(Context context, String bookname, String account){
        Intent intent = new Intent(context, BookContentActivity.class);
        intent.putExtra("bookTitle",bookname);
        intent.putExtra("account",account);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);

        BookContentFragment bookContentFragment = new BookContentFragment();
        Bundle bundle = new Bundle();
        System.out.println("第三");
        String booktitle = getIntent().getStringExtra("bookTitle");//BookListFragment传过来的
        String bookname = getIntent().getStringExtra("bookname");//AddThingActivity传过来的
        String bookname1 = getIntent().getStringExtra("bookname2");//AmendThingActivity传过来的
        String bookname2 = getIntent().getStringExtra("bookname3");//AmendThingActivity传过来的

        bundle.putString("name",booktitle);
        bundle.putString("name1",bookname);
        bundle.putString("name2",bookname1);
        bundle.putString("name3",bookname2);

        System.out.println("第四");
        bookContentFragment.setArguments(bundle);

        System.out.println("booktitle"+booktitle);
        setContentView(R.layout.book_content_fragment);
        //list = new DateContentTitleData().getList(dateAccount,booktitle);
       // System.out.println("list:"+list.size());
        String title = "Days Matter ."+booktitle;
        setTitle(title);
        if (bookname != null){
            String title1 = "Days Matter ."+bookname;
            setTitle(title1);
        }
        if (bookname1 != null){
            String title2 = "Days Matter ."+bookname1;
            setTitle(title2);
        }
        if (bookname2 != null){
            String title3 = "Days Matter ."+bookname2;
            setTitle(title3);
        }
        //bookContentFragment.setList(list);
//        Intent intent = new Intent(BookContentActivity.this,BookContentFragment.class);
//        intent.putExtra("bookname",booktitle);
//        intent.putExtra("booklist",(Serializable)list);

        System.out.println("第一次调用BookContentFragment");

        //startActivity(intent);


        FragmentManager fragmentManager = getSupportFragmentManager();
//        BookContentFragment bookContentFragment = new BookContentFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.book_fragment,bookContentFragment);
        fragmentTransaction.commit();
        //BookContentFragment bookContentFragment = (BookContentFragment)getSupportFragmentManager().findFragmentById(R.id.book_content_fragment);
        //bookContentFragment.setBookTitle(booktitle);
        System.out.println("fragment的值已获取");
        int id = getIntent().getIntExtra("fragmentid",-1);
        if (id>0){
            if (id == 1){
                fragmentTransaction.replace(R.id.book_fragment,bookContentFragment);
                fragmentTransaction.commit();
            }
        }
        //加载BookContentFragment
        //将倒数本对应的事件显示出来

//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//
////        bookContentFragment.setBookTitle(booktitle);
//
//        transaction.add(R.id.book_content_fragment,bookContentFragment,BookContentFragment.class.getName());
//        transaction.commit();


        //添加左上角返回按钮
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    //右上角编辑按钮
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.book_content,menu);
        return true;
    }
    //返回按钮的点击事件、右上角icon点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                Intent intent = new Intent(BookContentActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.edit_book:
                Toast.makeText(this, "You clicked edit_book", Toast.LENGTH_SHORT).show();

                Intent intent3 = new Intent(BookContentActivity.this, EditBookActivity.class);
                System.out.println("bookTitle1"+getIntent().getStringExtra("bookTitle"));
                System.out.println("account"+getIntent().getStringExtra("account"));
                intent3.putExtra("account", getIntent().getStringExtra("account"));
                intent3.putExtra("bookTitle", getIntent().getStringExtra("bookTitle"));
                intent3.putExtra("bookname",getIntent().getStringExtra("bookname"));//AddThingActivity传过来的
                //intent2.putExtra("bookIcon", getIntent().getStringExtra("bookIcon"));
                startActivity(intent3);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}