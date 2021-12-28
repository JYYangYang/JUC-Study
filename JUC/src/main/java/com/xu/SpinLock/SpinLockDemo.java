package com.xu.SpinLock;

import java.util.concurrent.atomic.AtomicReference;

public class SpinLockDemo {
    AtomicReference<Thread> atomicReference=new AtomicReference<>();

    public void myLock() {
        Thread thread = Thread.currentThread();
        System.out.println(Thread.currentThread().getName() + "=>mylock");
        //自旋锁
        while (!atomicReference.compareAndSet(null, thread)) {

        }
    }
        public void myUnlock(){
            Thread thread=Thread.currentThread();
            System.out.println(Thread.currentThread().getName()+"=>myunlock");
           while(!atomicReference.compareAndSet(thread,null)){

           }
    }

}
