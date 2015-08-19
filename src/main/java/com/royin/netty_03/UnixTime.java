package com.royin.netty_03;

import java.util.Date;

/**
 * User: Roy.Yin
 * Date: 2015/6/4
 * Time: 14:08
 */
public class UnixTime {

    private long value;

    public UnixTime(){
        value = System.currentTimeMillis();
    }

    public UnixTime(long value){
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    @Override
    public String toString() {
        return new Date((value-2208988800L)/1000L).toString();
    }
}
