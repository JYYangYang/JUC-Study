package com.xu.SpinLock;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

public class SpinLockTest {
    public static void main(String[] args) throws InterruptedException {
        SpinLockDemo lock=new SpinLockDemo();
        new Thread(()->{
            lock.myLock();
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.myUnlock();
            }

        },"T1").start();

        TimeUnit.SECONDS.sleep(1);
        new Thread(()->{
            lock.myLock();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.myUnlock();
            }

        },"T2").start();
    }



}
