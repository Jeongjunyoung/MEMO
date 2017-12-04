package com.obg.memo;


import java.util.Date;

public class MemoItem {
    private int _id;
    private String date;
    private String content;
    private int resId;
    public MemoItem(){}

    public MemoItem(int _id, String date, String content, int resId) {
        this._id = _id;
        this.date = date;
        this.content = content;
        this.resId = resId;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }
}
