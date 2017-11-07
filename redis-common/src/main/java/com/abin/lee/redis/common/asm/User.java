package com.abin.lee.redis.common.asm;

import com.esotericsoftware.reflectasm.MethodAccess;

/**
 * Description:
 * Author: abin
 * Update: (2015-08-17 12:05)
 */
public class User {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static void main(String[] args) {
        User user = new User();
        //使用reflectasm生产User访问类
        MethodAccess access = MethodAccess.get(User.class);
        //invoke setName方法name值
        access.invoke(user, "setName", "张三");
        //invoke getName方法 获得值
        String name = (String)access.invoke(user, "getName", null);
        System.out.println(name);
    }
}