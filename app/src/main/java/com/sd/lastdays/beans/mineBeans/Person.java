package com.sd.lastdays.beans.mineBeans;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * 个人数据实体类
 */
public class Person extends DataSupport {

    private String account;

    private String passWord;

    private String imageUri;//默认default

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}
