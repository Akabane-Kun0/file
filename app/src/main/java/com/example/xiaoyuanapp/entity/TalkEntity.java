package com.example.xiaoyuanapp.entity;

import cn.bmob.v3.BmobObject;

public class TalkEntity extends BmobObject {
    private String id;
    private String time;
    private String talk;
    private String Type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTalk() {
        return talk;
    }

    public void setTalk(String talk) {
        this.talk = talk;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
