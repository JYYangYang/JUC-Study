package com.xu.CAS;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//compareAndSet():比较并交换
public class CASDemo {
    public static void main(String[] args) {
        AtomicStampedReference<Integer> ato=new AtomicStampedReference<>(2,1);
        new Thread(()->{
            int stamp=ato.getStamp();
            System.out.println("a=>"+stamp);

            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            ato.compareAndSet(2,3,
            ato.getStamp(),ato.getStamp()+1
            );

            System.out.println("a=>"+ato.getStamp());

            ato.compareAndSet(3,2,
                    ato.getStamp(),ato.getStamp()+1
            );

            System.out.println("a=>"+ato.getStamp());
        }).start();

        new Thread(()->{
            int stamp=ato.getStamp();
            System.out.println("b=>"+stamp);

            ato.compareAndSet(2,6,
                    ato.getStamp(),ato.getStamp()+1
                    );
            System.out.println("b=>"+ato.getStamp());
        }).start();
    }

}
