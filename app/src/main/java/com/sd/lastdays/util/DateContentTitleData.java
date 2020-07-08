package com.sd.lastdays.util;

import com.sd.lastdays.beans.countdownDayBeans.DateContent;
import com.sd.lastdays.fragment.booksFragment.BookContentFragment;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * 提供倒数日内容数据
 */
public class DateContentTitleData {

    public List<DateContent> getNews(String account) {
        List<DateContent> newsList = new ArrayList<>();
        DateContent news = new DateContent();
        List<DateContent> dateContentList;
        //List<DateContent> dateContentList = DataSupport.where("account=?", account).find(DateContent.class);
        if ("admin".equals(account)){
            if (DataSupport.where("account=?", "admin").find(DateContent.class).isEmpty()){
                //无数据时默认演示
                news.setTitle("测试演示"+"——>还有");
                news.setRemainingTime(String.valueOf(10));
                news.setTargetDate("目标日期：2020-01-02 ");
                news.setLastBook("倒数本A");
                news.setAccount("admin");
                news.save();
            }
            dateContentList = DataSupport.where("account=?", "admin").find(DateContent.class);
        }else{
            dateContentList = DataSupport.where("account=?", account).find(DateContent.class);
        }
        newsList = dateContentList;
        return newsList;
    }

    public List<DateContent> getNews1(String account) {
        List<DateContent> newsList = new ArrayList<>();
        DateContent news = new DateContent();
        List<DateContent> dateContentList;
        //List<DateContent> dateContentList = DataSupport.where("account=?", account).find(DateContent.class);
        if ("admin".equals(account)){
            if (DataSupport.where("account=?", "admin").find(DateContent.class).isEmpty()){
                //无数据时默认演示

                news.setTitle("鲁琳君加油");
                news.setRemainingTime(String.valueOf(1));
                news.setTargetDate("目标日期：2020-01-09 ");
                news.setLastBook("倒数本B");
                news.setAccount("admin");
                news.save();
            }
            dateContentList = DataSupport.where("account=?", "admin").find(DateContent.class);
        }else{
            dateContentList = DataSupport.where("account=?", account).find(DateContent.class);
        }
        newsList = dateContentList;
        return newsList;
    }

    public List<DateContent> getList(String account,String bookTitle) {
        List<DateContent> newsList = new ArrayList<>();
        DateContent news = new DateContent();
        List<DateContent> dateContentList;
        //List<DateContent> dateContentList = DataSupport.where("account=?", account).find(DateContent.class);
        if ("admin".equals(account)){
            if (DataSupport.where("account=?", "admin").find(DateContent.class).isEmpty()){
                //无数据时默认演示
                news.setTitle("测试演示"+"——>还有");
                news.setRemainingTime(String.valueOf(10));
                news.setTargetDate("目标日期：2020-01-02 ");
                news.setLastBook("我");
                news.setAccount("admin");
                news.save();
            }
            dateContentList = DataSupport.where("account=? and lastbook=?", "admin",bookTitle).find(DateContent.class);
        }else{
            dateContentList = DataSupport.where("account=? and lastbook=?", account,bookTitle).find(DateContent.class);
        }

        newsList = dateContentList;
        return newsList;
    }
}
