package com.sd.lastdays.fragment.manageBookFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sd.lastdays.R;
import com.sd.lastdays.beans.booksBeans.Book;
import com.sd.lastdays.beans.booksBeans.BookType;
import com.sd.lastdays.fragment.countdownDayFragment.DateContentTitleFragment;
import com.sd.lastdays.util.PenultimateContent;

import java.util.ArrayList;
import java.util.List;


public class ManagerBookFragment extends Fragment {
    private List<BookType> bookTypeList = new ArrayList<>();
    private BookTypeAdapter adapter;
    private static String userAccount = "admin";;

    public String getDateAccount() {
        return userAccount;
    }

    //从广播那里获取到用户账号
    public void setAccount(String account) {
        ManagerBookFragment.userAccount = account;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //view为碎片要显示的布局内容
        View view = inflater.inflate(R.layout.book, container, false);

        initBookTypes();
        //book_recyclerView是显示列表的区域的id
        RecyclerView newsTitleRecyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        newsTitleRecyclerView.setLayoutManager(layoutManager);
        adapter = new BookTypeAdapter(bookTypeList);

        SlipReAdapter.Builder builder = new SlipReAdapter.Builder()
                .setAdapter(adapter)
                .setISlipClickAction(new SlipReAdapter.ISlipClickAction() {
                    @Override
                    public void onAction(int position) {
                        PenultimateContent dBook;
                        dBook = new PenultimateContent();
                        //删除数据库里的数据
                        if(userAccount.equals("admin")) {
                            Toast.makeText(getActivity(),"默认账户不能删除!",Toast.LENGTH_SHORT).show();

                        } else{
                            if(dBook.deleteBook(userAccount,bookTypeList.get(position).getName())) {

                                Toast.makeText(getActivity(),"删除成功",Toast.LENGTH_SHORT).show();
                                bookTypeList.remove(position);
                            } else{
                                Toast.makeText(getActivity(),"删除失败",Toast.LENGTH_SHORT).show();
                            }
                        }


                    }
                })
                .setMode(SlipReAdapter.MODE_DELETE)
                .setSlipViewId(R.layout.item_remove);
        newsTitleRecyclerView.setAdapter(builder.build());


        return view;
    }


    private void initBookTypes() {
        //根据用户账号得到对应的倒数本信息
        PenultimateContent qBook;
        qBook = new PenultimateContent();
        List<Book> lastBooks =  qBook.getBooks(userAccount);

        for(int i = 0; i < lastBooks.size(); i++) {
            String bookName = lastBooks.get(i).getBookTitle();
            int bookId = lastBooks.get(i).getBookIcon();
            BookType book = new BookType(bookName,bookId);
            bookTypeList.add(book);
        }

    }




}