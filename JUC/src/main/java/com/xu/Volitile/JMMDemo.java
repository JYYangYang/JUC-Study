package com.xu.Volitile;

import java.util.concurrent.TimeUnit;

public class JMMDemo {
    private volatile static int num=0;
    public static void main(String[] args) {
        //线程1
        new Thread(()->{
            while(num==0){

            }
        }).start();


        //主线程
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        num=1;

        System.out.println(num);
    }
}
