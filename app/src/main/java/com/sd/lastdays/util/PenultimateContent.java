package com.sd.lastdays.util;

import android.provider.ContactsContract;
import android.util.Log;

import com.sd.lastdays.R;
import com.sd.lastdays.beans.booksBeans.Book;
import com.sd.lastdays.beans.countdownDayBeans.DateContent;
import com.sd.lastdays.beans.mineBeans.Person;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;
/*
 * 提供倒数本的内容
 * */
public class PenultimateContent {
    public List<Book> getBooks(String account) {
        List<Book> booksList = new ArrayList<>();
        Book books = new Book();
        List<Book> bookContentList;

//        DataSupport.deleteAll(Book.class);//删除book表的所有数据
        if ("admin".equals(account)){
            Log.d("tag","admin判断正确");
            if (DataSupport.where("account=?", "admin").find(Book.class).isEmpty()){
                //无数据时默认演示
                books.setBookTitle("倒数本A");
                books.setBookIcon(R.drawable.icon1);
                books.setBookCover(2131165379);
                books.setAccount("admin");
                books.save();
            }
            bookContentList = DataSupport.where("account=?", "admin").find(Book.class);
        }else{
            bookContentList = DataSupport.where("account=?", account).find(Book.class);
        }
        booksList = bookContentList;
        return booksList;
    }
    public boolean saveBook(String account, String bookName, int bookIcon, int bookCover) {
        Book book = new Book();
        Log.d("tag","saveBook里的account");
        Log.d("tag",account);
        book.setAccount(account);
        book.setBookTitle(bookName);
        book.setBookIcon(bookIcon);
        book.setBookCover(bookCover);
        book.save();
        return true;
    }
    public boolean editBookInfo(String originaltitle,String account, String bookName, int bookIcon, int bookCover){
        Book book = new Book();
        DateContent dateContent = new DateContent();
        System.out.println("originaltitle:"+originaltitle);
        System.out.println("更改前的icon："+bookIcon);
        System.out.println("更改前的icon："+bookCover);
        book.setAccount(account);
        book.setBookTitle(bookName);
        book.setBookIcon(bookIcon);
        book.setBookCover(bookCover);

        book.updateAll("bookTitle = ?",originaltitle);
        dateContent.setLastBook(bookName);
        dateContent.updateAll("lastbook = ?",originaltitle);
        System.out.println("更改后的书名："+book.getBookTitle());
        System.out.println("更改后的icon："+book.getBookIcon());
        System.out.println("更改后的photo："+book.getBookCover());
        return true;
    }

    public boolean deleteBook(String account,String bookName) {
        if(DataSupport.deleteAll(Book.class,"bookTitle=? and account = ?",bookName,account)!=0) {
           DataSupport.deleteAll(DateContent.class,"lastBook=? and account = ?",bookName,account);
           return true;
        }
        return false;
    }

}
