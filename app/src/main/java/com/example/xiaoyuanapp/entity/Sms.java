package com.example.xiaoyuanapp.entity;

import cn.bmob.v3.BmobObject;

public class Sms extends BmobObject {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    private String phone;

    public Sms() {

    }

    public Sms(String sms_id, String sms_phone) {
        this.id = sms_id;
        this.phone = sms_phone;
    }

}
