package com.sd.lastdays.otherActivity.LastDayShowActivity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;

import com.sd.lastdays.R;
import com.sd.lastdays.fragment.booksFragment.EditBookFragment;
import com.sd.lastdays.util.ActivityCollector;

public class EditBookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);
        setTitle("编辑倒数本");
        ActivityCollector.addActivity(this);
        String account = getIntent().getStringExtra("account");
        String bookName = getIntent().getStringExtra("bookTitle");
        String bookName1 = getIntent().getStringExtra("bookname");

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        EditBookFragment editBookFragment = new EditBookFragment();
        editBookFragment.recieveInfo(account,bookName,bookName1);
        transaction.add(R.id.fragment_edit,editBookFragment,EditBookFragment.class.getName());
        transaction.commit();



        //设置编辑倒数本界面中原倒数本的名称
        System.out.println("倒数本名称："+bookName);
        EditText bookTitle = (EditText) findViewById(R.id.book_name);
        bookTitle.setText(bookName);
        //添加左上角返回按钮
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    //返回按钮的点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}