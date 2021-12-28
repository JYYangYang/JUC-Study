package com.xu.Volitile;

import java.util.concurrent.atomic.AtomicInteger;

public class VolitileDemo {
    private static volatile AtomicInteger num=new AtomicInteger();

    private static void add(){
        num.getAndIncrement(); //AtomicInteger
    }

    public static void main(String[] args) {
        for(int i=0;i<=20;i++){
            new Thread(()->{
                for(int j=0;j<1000;j++){
                    add();
                }
            }).start();
        }
        while(Thread.activeCount()>2){
            Thread.yield();
        }
        System.out.println(Thread.currentThread().getName()+""+num);
    }
}
