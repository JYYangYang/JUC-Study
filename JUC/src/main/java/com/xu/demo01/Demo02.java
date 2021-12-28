package com.xu.demo01;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Demo02{
    public static void main(String[] args) throws InterruptedException {
      //并发：多个线程操作同一个资源类
        Ticket2 ticket=new Ticket2();
        //函数式编程 jdk1.8 lambda表达式（参数）->{代码}
        new Thread(()->{
            for(int i=1;i<60;i++){
                ticket.sale();
            }

        },"A"
        ).start();

        new Thread(()->{
            for(int i=1;i<60;i++){
                ticket.sale();
            }

        },"B"
        ).start();

        new Thread(()->{
            for(int i=1;i<60;i++){
                ticket.sale();
            }

        },"C"
        ).start();



    }
}

//资源类
//1.创建Lock锁，基本上是用ReentrantLock可重用锁
//2.Lock.lock()加锁
//3. Finally中要解锁
class  Ticket2{
    private int number=50;

    Lock lock=new ReentrantLock();

    public void sale(){
        lock.lock();

        try{
            if(number>0){
                System.out.println(Thread.currentThread().getName()+"卖出了"+(number--)+"票,剩余"+number);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            lock.unlock();
        }
    }

}
