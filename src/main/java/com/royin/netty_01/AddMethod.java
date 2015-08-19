package com.royin.netty_01;

/**
 * 加法
 * User: Roy.yin
 * Date: 2015/4/24.
 * Time: 11:57
 */
public class AddMethod {

    private int a;

    private int b;

    public AddMethod(int a,int b){
        this.a = a;
        this.b = b;
    }

    public int add(){
        return a+b;
    }
}
