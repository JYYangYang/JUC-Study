package com.xu.BlockingQueue;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

public class SynchronousQueue {
    public static void main(String[] args) {

    }
//        BlockingQueue<String> squeue = new SynchronousQueue<>();
//
//
//        new Thread(()->{
//            try{
//                System.out.println(Thread.currentThread().getName()+"put1");
//                squeue.put("1");
//                System.out.println(Thread.currentThread().getName()+"put1");
//                squeue.put("2");
//                System.out.println(Thread.currentThread().getName()+"put1");
//                squeue.put("3");
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        },"T1").start();
//
//        new Thread(()->{
//            try{
//                System.out.println(Thread.currentThread().getName()+squeue.take());
//                System.out.println(Thread.currentThread().getName()+squeue.take());
//                System.out.println(Thread.currentThread().getName()+squeue.take());
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        },"T2").start();
//    }

}
