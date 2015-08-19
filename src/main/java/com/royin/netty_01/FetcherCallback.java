package com.royin.netty_01;

/**
 * User: Roy.yin
 * Date: 2015/4/24.
 * Time: 12:00
 */
public interface FetcherCallback {

    void onAdd(AddMethod method) throws Exception;

    void onError(Throwable e);
}
