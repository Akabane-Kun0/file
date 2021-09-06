package com.example.xiaoyuanapp.entity;

import cn.bmob.v3.BmobObject;

public class Shop extends BmobObject {

    private String demand_name; //商品名称
    private String demand_description;//商品描述
    private String demand_price;//商品价格
    private String demand_time;//商品推出时间
    private String demand_pic;//商品图片

    public String getDemand_name() {
        return demand_name;
    }

    public void setDemand_name(String demand_name) {
        this.demand_name = demand_name;
    }

    public String getDemand_description() {
        return demand_description;
    }

    public void setDemand_description(String demand_description) {
        this.demand_description = demand_description;
    }

    public String getDemand_price() {
        return demand_price;
    }

    public void setDemand_price(String demand_price) {
        this.demand_price = demand_price;
    }

    public String getDemand_time() {
        return demand_time;
    }

    public void setDemand_time(String demand_time) {
        this.demand_time = demand_time;
    }

    public String getDemand_pic() {
        return demand_pic;
    }

    public void setDemand_pic(String demand_pic) {
        this.demand_pic = demand_pic;
    }

    public String getDemand_type() {
        return demand_type;
    }

    public void setDemand_type(String demand_type) {
        this.demand_type = demand_type;
    }

    private String demand_type;//商品所属

}
