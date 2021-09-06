package com.example.xiaoyuanapp.entity;

import cn.bmob.v3.BmobObject;

public class Line extends BmobObject {

    private String talk_uid; //用户id
    private String talk_time;//话题发表时间
    private String talk_content;//话题内容
    private String talk_type;//话题分类

    public String getTalk_uid() {
        return talk_uid;
    }

    public void setTalk_uid(String talk_uid) {
        this.talk_uid = talk_uid;
    }

    public String getTalk_time() {
        return talk_time;
    }

    public void setTalk_time(String talk_time) {
        this.talk_time = talk_time;
    }

    public String getTalk_content() {
        return talk_content;
    }

    public void setTalk_content(String talk_content) {
        this.talk_content = talk_content;
    }

    public String getTalk_type() {
        return talk_type;
    }

    public void setTalk_type(String talk_type) {
        this.talk_type = talk_type;
    }


}
