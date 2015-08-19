package com.royin.netty_01;

/**
 * User: Roy.yin
 * Date: 2015/4/24.
 * Time: 12:53
 */
public class Worker {

    public void doWork() {
        Fetcher fetcher = new MyFetcher(new AddMethod(3,6));

        fetcher.fetcherData(new FetcherCallback() {
            @Override
            public void onAdd(AddMethod method) throws Exception {
                System.out.println("add return ="+method.add());
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("An error accour: " + e.getMessage());
            }
        });

    }

    public static void main(String[] args) {
        Worker w = new Worker();
        w.doWork();
    }
}
