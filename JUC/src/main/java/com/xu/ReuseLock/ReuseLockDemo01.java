package com.xu.ReuseLock;

import com.xu.Lock8.Phone;

public class ReuseLockDemo01 {

    public static void main(String[] args) {
        phone phone=new phone();
        new Thread(()->{
            phone.sms();
        },"a").start();
        new Thread(()->{
            phone.sms();
        },"b").start();
    }
}


class phone{
    public synchronized void sms(){
        System.out.println(Thread.currentThread().getName()+"sms");
    }
    public synchronized void call(){
        System.out.println(Thread.currentThread().getName()+"call");
    }
}

