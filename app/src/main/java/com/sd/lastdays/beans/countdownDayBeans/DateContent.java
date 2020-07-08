package com.sd.lastdays.beans.countdownDayBeans;

import org.litepal.crud.DataSupport;

/**
 * 倒数日列表数据实体类
 */
public class DateContent extends DataSupport {
    //文本标题
    private String title;
    //剩余时间
    private String remainingTime;
    //目标日期
    private String targetDate;
    //倒数本
    private String lastBook;
    //所属人账户
    private String account;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(String remainingTime) {
        this.remainingTime = remainingTime;
    }

    public String getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(String targetDate) {
        this.targetDate = targetDate;
    }

    public String getLastBook() {
        return lastBook;
    }

    public void setLastBook(String book) {
        lastBook = book;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
