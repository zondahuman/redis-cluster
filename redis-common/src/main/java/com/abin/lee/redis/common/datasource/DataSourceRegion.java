package com.abin.lee.redis.common.datasource;

import com.google.common.collect.Lists;

import java.util.List;

public enum DataSourceRegion {
    all("all"),
    house("house"),
    customer("customer");
//    business("business");

    private String text;

    private DataSourceRegion(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public static List<String> getRegion(){
        List<String> params = Lists.newArrayList();
         for(DataSourceRegion region:DataSourceRegion.values()){
             params.add(region.getText());
         }
        return params;
    }
}

