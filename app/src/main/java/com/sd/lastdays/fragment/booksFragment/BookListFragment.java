package com.sd.lastdays.fragment.booksFragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sd.lastdays.R;
import com.sd.lastdays.beans.booksBeans.Book;
import com.sd.lastdays.beans.booksBeans.Photos;
import com.sd.lastdays.beans.countdownDayBeans.DateContent;

import com.sd.lastdays.otherActivity.LastDayShowActivity.BookContentActivity;
import com.sd.lastdays.util.PenultimateContent;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 查询倒数本，显示在第二个按钮上
 */
public class BookListFragment extends Fragment {

    private static String dateAccount = "admin";

    public String getDateAccount() {
        return dateAccount;
    }

    //从广播那里获取到用户账号
    public void setDateAccount(String dateAccount) {
        BookListFragment.dateAccount = dateAccount;
    }
    private List<Book> bookList = new ArrayList<>();
    private List<Photos> photosList = new ArrayList<>();
    private LocalBroadcastManager localBroadcastManager;//本地广播管理器

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_b_main,container,false);
        initList();
        //得到所有倒数本包含事件的个数
//        int number = ;
//        String num = String.valueOf(number);
//        TextView allBook = (TextView) view.findViewById(R.id.book_number);
//        allBook.setText(num);

        RecyclerView bookPhoneRecycler1 = (RecyclerView)view.findViewById(R.id.recycler_view_photo);
        RecyclerView bookImageRecycler = (RecyclerView)view.findViewById(R.id.recycler_view_list);

        GridLayoutManager layoutManager1 = new GridLayoutManager(getActivity(),3);
        bookPhoneRecycler1.setLayoutManager(layoutManager1);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        bookImageRecycler.setLayoutManager(layoutManager);

        BookPhotoAdapter adapter1 = new BookPhotoAdapter(bookList);
        bookPhoneRecycler1.setAdapter(adapter1);
        BookAdapter adapter = new BookAdapter(bookList);
        System.out.println("书的本数："+bookList.size());
        bookImageRecycler.setAdapter(adapter);
        return view;
    }

    private void initList(){
        PenultimateContent books;
        books = new PenultimateContent();
        bookList = books.getBooks(dateAccount);
    }
    //得到关于封面的内容
    private void initPhoto(){
        for (int i = 0; i<bookList.size(); i++){

        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(Objects.requireNonNull(getActivity()));
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.CART_BROADCAST");
        BroadcastReceiver mItemViewListClickReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String msg = intent.getStringExtra("data");
                if("refresh".equals(msg)){
                    initList();//实现操作数据刷新的方法
                }
            }
        };
        broadcastManager.registerReceiver(mItemViewListClickReceiver, intentFilter);
    }

    //转到BookContentFragment

    class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {

        private List<Book> mbookList;
         class ViewHolder extends RecyclerView.ViewHolder{
             View bookview;
            ImageView bookIcon;
            TextView bookName;
            TextView thingNum;
            public ViewHolder(View itemView) {
                super(itemView);
                bookview = itemView;
                bookIcon = (ImageView) itemView.findViewById(R.id.book_image);
                bookName = (TextView) itemView.findViewById(R.id.bookname);
                thingNum = (TextView) itemView.findViewById(R.id.book_number);
            }
        }
        public BookAdapter(List<Book> bookList){
            mbookList = bookList;
        }

        @Override
        public BookAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_b_2,parent,false);
            final BookAdapter.ViewHolder holder = new BookAdapter.ViewHolder(view);
//            //广播book的数据
//            localBroadcastManager = LocalBroadcastManager.getInstance(parent.getContext());
//            final Intent intent = new Intent("com.sd.lastdays.BOOK_TITLE");

            holder.bookview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Book book = mbookList.get(holder.getAdapterPosition());
                    System.out.println("倒数本主人："+book.getAccount());
                   // new SetBookTitle().setBookTitle(book.getBookTitle());
//                    intent.putExtra("bookname",book.getBookTitle());
//                    localBroadcastManager.sendBroadcast(intent);

                    BookContentActivity.actionStart(getActivity(),book.getBookTitle(),book.getAccount());
//                    BookContentFragment bookContentFragment = new BookContentFragment();
//                    Bundle bundle = new Bundle();
//                    bundle.putString("bookname",book.getBookTitle());
//                    bookContentFragment.setArguments(bundle);
//                    showFragment(BookListFragment.this,bookContentFragment);
                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(BookAdapter.ViewHolder holder, int position) {
            Book book = mbookList.get(position);
            //去DateContent表里面查询对应倒数本里面的事件个数
            List<DateContent> list = DataSupport.where("LastBook = ?",""+book.getBookTitle()).find(DateContent.class);
            int number = list.size();
            String p = String.valueOf(number);
            System.out.println("bookIcon"+book.getBookIcon());
            holder.bookIcon.setImageResource(book.getBookIcon());
            holder.bookName.setText(book.getBookTitle());
            System.out.println("thingnumber"+number);
            holder.thingNum.setText(p);
        }

        @Override
        public int getItemCount() {
            return mbookList.size();
        }
    }

    class BookPhotoAdapter extends RecyclerView.Adapter<BookPhotoAdapter.ViewHolder> {
        private List<Book> mbookList;

         class ViewHolder extends RecyclerView.ViewHolder {
            View bookView;
            ImageView bookImage;
            TextView bookName;

            public ViewHolder(View view) {
                super(view);
                bookView = view;
                bookImage = (ImageView) view.findViewById(R.id.book_photo);
                bookName = (TextView) view.findViewById(R.id.book_name);
            }
        }

        public BookPhotoAdapter(List<Book> bookList) {
            mbookList = bookList;
        }

        @Override
        public BookPhotoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_b_1, parent, false);
            final BookPhotoAdapter.ViewHolder holder = new BookPhotoAdapter.ViewHolder(view);
            holder.bookView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    Book book = mbookList.get(position);
                    Toast.makeText(v.getContext(), "you clicked view " + book.getBookTitle(), Toast.LENGTH_SHORT).show();
                }
            });
            holder.bookImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    Book book = mbookList.get(position);
                    //Toast.makeText(v.getContext(), "you clicked image " + book.getBookTitle(), Toast.LENGTH_SHORT).show();
                   // Log.d("bookTitle",book.getBookTitle());
                    BookContentActivity.actionStart(getActivity(),book.getBookTitle(),book.getAccount());
                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(BookPhotoAdapter.ViewHolder holder, int position) {
            Book book = mbookList.get(position);
            holder.bookImage.setImageResource(book.getBookCover());
            holder.bookName.setText(book.getBookTitle());
        }

        @Override
        public int getItemCount() {
            return mbookList.size();
        }
    }

    private void showFragment(Fragment fragment1,Fragment fragment2){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (!fragment2.isAdded()){
            transaction.add(R.id.recycler_view_list,fragment2)
                    .addToBackStack(null)
                    .commitAllowingStateLoss();
        }else{
            transaction.hide(fragment1)
                    .show(fragment2)
                    .addToBackStack(null)
                    .commitAllowingStateLoss();
        }
    }

}