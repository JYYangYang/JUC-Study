package com.xu.FuZhu;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch  countDownLatch=new CountDownLatch(6);
        for(int i=1;i<=6;i++){
            new Thread(()->{
                System.out.println(Thread.currentThread().getName());
                countDownLatch.countDown();
            },String.valueOf(i)).start();


        }
        countDownLatch.await();//等待计数器归零，然后再向下执行

        System.out.println("close Door");
    }
}
