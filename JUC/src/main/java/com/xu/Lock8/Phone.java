package com.xu.Lock8;

public class Phone {
//谁先拿到锁谁先执行
    public static void main(String[] args) {
        telephone telephone=new telephone();

        new Thread(()->{telephone.call();},"A").start();
        new Thread(()->{telephone.sendsms();},"B").start();


    }

}


class telephone{

    public synchronized void sendsms(){
        System.out.println("发短信");
    }


    public synchronized void  call(){
        System.out.println("打电话");
    }
}
