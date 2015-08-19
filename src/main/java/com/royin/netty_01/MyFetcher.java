package com.royin.netty_01;

/**
 * User: Roy.yin
 * Date: 2015/4/24.
 * Time: 12:01
 */
public class MyFetcher implements Fetcher{

    private AddMethod method;

    public MyFetcher(AddMethod method){
        this.method = method;
    }

    @Override
    public void fetcherData(FetcherCallback callback) {

        try {
            callback.onAdd(method);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
