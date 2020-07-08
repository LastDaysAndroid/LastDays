package com.sd.lastdays.beans.booksBeans;

import org.litepal.crud.DataSupport;

/*
* 倒数本实体类
*/
public class Book extends DataSupport {
    private String bookTitle;//倒数本名称
    private int bookIcon;//倒数本图标
    private int bookCover;//倒数本封面
    private String account;//账户

    public Book(){

    }
    public Book(String bookTitle, int bookCover, int bookIcon, String account){
        this.bookTitle = bookTitle;
        this.bookCover = bookCover;
        this.bookIcon = bookIcon;
        this.account = account;
    }
    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public int getBookIcon() {
        return bookIcon;
    }

    public void setBookIcon(int bookIcon) {
        this.bookIcon = bookIcon;
    }

    public int getBookCover() {
        return bookCover;
    }

    public void setBookCover(int bookCover) {
        this.bookCover = bookCover;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
