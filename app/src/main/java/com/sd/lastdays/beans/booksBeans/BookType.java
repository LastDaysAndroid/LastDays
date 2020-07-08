package com.sd.lastdays.beans.booksBeans;

public class BookType {
    private String name;
    private int imageId;

    public BookType(String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }
}
