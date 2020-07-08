package com.sd.lastdays.fragment.booksFragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sd.lastdays.R;
import com.sd.lastdays.beans.booksBeans.Book;
import com.sd.lastdays.beans.countdownDayBeans.DateContent;
import com.sd.lastdays.otherActivity.LastDayShowActivity.AddThingActivity;
import com.sd.lastdays.otherActivity.LastDayShowActivity.ThingContentActivity;
import com.sd.lastdays.otherActivity.countdownDayActivity.AddContentActivity;
import com.sd.lastdays.otherActivity.countdownDayActivity.DateContentActivity;
import com.sd.lastdays.util.DateContentTitleData;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.List;

/*
* 点击倒数本后进入，显示具体的倒数日
* */
public class BookContentFragment extends Fragment {

    private View view;
    private  String bookTitle;
    String name = null;//获得从BookContentActivity中传来的值
    String name1 = null;//获得从BookContentActivity中传来的值
    String name2 = null;//获得从AmendThingActivity中传来的值
    String name3 = null;//获得从AmendThingActivity中传来的值
    private IntentFilter intentFilter;
    private com.sd.lastdays.fragment.booksFragment.BookContentFragment.LocalReceiver localReceiver;    //本地广播接收者BroadcastReceiver
    private LocalBroadcastManager localBroadcastManager;
    private static String dateAccount = "admin";
    public String getDateAccount() {
        return dateAccount;
    }

    public void setDateAccount(String dateAccount) {
        BookContentFragment.dateAccount = dateAccount;
    }

   public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        System.out.println("给booktitle赋了值");
        this.bookTitle = bookTitle;
    }

    //private List<DateContent> list = new DateContentTitleData().getList(dateAccount,bookTitle);
    private List<DateContent> mNewsList;
    private List<DateContent> list;

    public void setList(List<DateContent> list) {
        System.out.println("给list赋了值");
        this.list = list;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //System.out.println("dateAccount" + dateAccount);
        System.out.println("第一");
        View view = inflater.inflate(R.layout.book_content, container, false);
        //1.获取RecyclerView实例  2.指定为线性布局 3.创建适配器实例传入数据  4.setAdapter调用完成配置，建立关联
        RecyclerView newsTitleRecyclerView = (RecyclerView) view.findViewById(R.id.book_list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        newsTitleRecyclerView.setLayoutManager(layoutManager);
        System.out.println("第二");
        //获取LocalBroadcastManager   本地广播管理者实例
        localBroadcastManager = LocalBroadcastManager.getInstance(getContext());
        localReceiver = new com.sd.lastdays.fragment.booksFragment.BookContentFragment.LocalReceiver();

        intentFilter = new IntentFilter();
        intentFilter.addAction("com.sd.lastdays.BOOK_TITLE");


        localBroadcastManager.registerReceiver(localReceiver,intentFilter);   //注册本地广播
        //list = new DateContentTitleData().getList(dateAccount,bookTitle);
        Bundle bundle = this.getArguments();
        System.out.println("第五");

        //name = getActivity().getIntent().getStringExtra("bookname");
        if (bundle!=null){
            System.out.println("第六");
            name = bundle.getString("name");
            name1 = bundle.getString("name1");
            name2 = bundle.getString("name2");
            name3 = bundle.getString("name3");
        }
       // String name = getBookTitle();
        System.out.println("BookContentFragmentbookTitle:"+name);
        //getTransitiveData();
//        if(name.equals("倒数本A")){
//
//        }
        System.out.println("account:"+dateAccount);
        //list = new DateContentTitleData().getList(dateAccount,"倒数本A");
        if (name!=null){
            list = new DateContentTitleData().getList(dateAccount,name);
            System.out.println("倒数本name："+name);
            System.out.println("name不为空时的list："+list.size());
        }
        if (name1!=null){
            list = new DateContentTitleData().getList(dateAccount,name1);
            System.out.println("倒数本name1："+name1);
            System.out.println("name1不为空时的list："+list.size());
        }
        if (name2!=null){
            list = new DateContentTitleData().getList(dateAccount,name2);
            System.out.println("name2："+name2);
            System.out.println("name2不为空时的list："+list.size());
        }
        if (name3!=null){
            list = new DateContentTitleData().getList(dateAccount,name3);
            System.out.println("倒数本name3："+name3);
            System.out.println("name3不为空时的list："+list.size());
        }
        //list = new DateContentTitleData().getList(dateAccount,bookTitle);
        //list = (List<DateContent>) getActivity().getIntent().getSerializableExtra("booklist");
        System.out.println("list内容1："+list.size());

        NewsAdapter adapter = new NewsAdapter(list);
        newsTitleRecyclerView.setAdapter(adapter);



        return view;
    }

    //内部RecyclerView的适配器
     class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

          class ViewHolder extends RecyclerView.ViewHolder {
            TextView newsTitleText;
            TextView lastDaays;

            //最外层布局
            public ViewHolder(View view) {
                super(view);
                newsTitleText = (TextView) view.findViewById(R.id.news_title);
                lastDaays = (TextView) view.findViewById(R.id.last_day);
            }
        }

        //数据传入
        public NewsAdapter(List<DateContent> newsList) {
            mNewsList = newsList;
        }

        //以下三个重写方法
        //onCreateViewHolder ： 加载date_content_list_item布局 再传入ViewHolder的构造方法中
        //监听点击事件
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_day, parent, false);

            final BookContentFragment.NewsAdapter.ViewHolder holder = new BookContentFragment.NewsAdapter.ViewHolder(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DateContent news = mNewsList.get(holder.getAdapterPosition());
                    ThingContentActivity.actionStart(getActivity(), news.getTitle(), news.getRemainingTime(), news.getTargetDate(), news.getAccount(), news.getLastBook());
                }
            });
            return holder;
        }

        //会在每个子项滚动到屏幕时执行，进行赋值操作
        @Override
        public void onBindViewHolder(BookContentFragment.NewsAdapter.ViewHolder holder, int position) {
            DateContent news = mNewsList.get(position);
            holder.newsTitleText.setText(news.getTitle());
            holder.lastDaays.setText(news.getRemainingTime());
        }

        //返回RecyclerView一共多少子项（长度）
        @Override
        public int getItemCount() {
            return mNewsList.size();
        }

    }

    private class LocalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals("com.sd.lastdays.BOOK_TITLE")){
                //int photo = intent.getIntExtra("photo",1);
                String bookname = intent.getStringExtra("bookname");
                bookTitle = bookname;
                Log.d("tag","执行了NameReceiver");
            }
            Log.d("tag","执行了receive");


        }
    }

    private void getTransitiveData(){
        bookTitle = getArguments().getString("bookname");
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        Button add_btn = (Button) getActivity().findViewById(R.id.add_book_content);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddThingActivity.class);
                System.out.println("传给AddThingActivity的书名："+name);
                intent.putExtra("bookname",name);

                startActivity(intent);
            }
        });
    }
}