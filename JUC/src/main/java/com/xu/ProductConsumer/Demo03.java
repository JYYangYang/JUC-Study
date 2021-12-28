package com.xu.ProductConsumer;

import java.awt.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Demo03 {

    public static void main(String[] args) {

        Date2 data=new Date2();
        new Thread(()->{for(int i=0;i<10;i++){data.printA();}},"A").start();
        new Thread(()->{for(int i=0;i<10;i++){data.printB();}},"B").start();
        new Thread(()->{for(int i=0;i<10;i++){data.printC();}},"C").start();
    }
}

class Date2{
    Lock lock=new ReentrantLock();
    Condition condition1=lock.newCondition();
    Condition condation2=lock.newCondition();
    Condition condition3=lock.newCondition();

    private int number=1;
    public void printA(){
        lock.lock();
        try{
            while(number!=1){
                condition1.await();
            }
            number=2;
            System.out.println(Thread.currentThread().getName());
            condation2.signal();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            lock.unlock();
        }
    }
    public void printB(){
        lock.lock();
        try{
     while(number!=2){
         condation2.await();
     }
     number=3;
            System.out.println(Thread.currentThread().getName());
     condition3.signal();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            lock.unlock();
        }
    }
    public void printC(){
        lock.lock();
        try{
      while(number!=3){
          condition3.await();
      }
      number=1;
            System.out.println(Thread.currentThread().getName());
      condition1.signal();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            lock.unlock();
        }
    }

}
