package com.xu.ProductConsumer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Demo02 {
    public static void main(String[] args) {
        Data1 date=new Data1();
        new Thread(()->{
            for(int i=0;i<10;i++){
                try {
                    date.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        },"A").start();
        new Thread(()->{
            for(int i=0;i<10;i++){
                try {
                    date.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        },"B").start();


        new Thread(()->{
            for(int i=0;i<10;i++){
                try {
                    date.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        },"C").start();



        new Thread(()->{
            for(int i=0;i<10;i++){
                try {
                    date.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        },"D").start();
    }
}

class Data1{
    private int number=0;
    Lock lock=new ReentrantLock();
    Condition condition=lock.newCondition();
    public  void increment() throws InterruptedException {
        lock.lock();
        try{

            while(number!=0){
                //等待
                condition.await();
            }
            number++;
            System.out.println(Thread.currentThread().getName()+"=>"+number);
            condition.signalAll();
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            lock.unlock();
        }
    }


    public    void decrement() throws InterruptedException {
        lock.lock();

        try{

            while(number==0){
                condition.await();
            }
            number--;
            condition.signalAll();
            System.out.println(Thread.currentThread().getName()+"=>"+number);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            lock.unlock();
        }

    }
}


