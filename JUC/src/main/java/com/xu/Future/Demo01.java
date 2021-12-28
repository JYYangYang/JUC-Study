package com.xu.Future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
  Ajax
 异步调用
 **/
public class Demo01 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        //没有返回值的异步回调
//        CompletableFuture<Void> completableFuture=CompletableFuture.runAsync(()->{
//            try{
//                TimeUnit.SECONDS.sleep(2);
//            }
//            catch (Exception e){
//                e.printStackTrace();
//            }
//            System.out.println(Thread.currentThread().getName()+"runAsync=》void");
//
//        });
//        System.out.println("1111");
//     completableFuture.get();//获取阻塞执行结果


        CompletableFuture<Integer> completableFuture=CompletableFuture.supplyAsync(()->{
            System.out.println(Thread.currentThread().getName()+"runAsync=》void");
            return 1024;
        });
        completableFuture.whenComplete((t,u)->{
            System.out.println("t=>"+t);//成功时的返回值
            System.out.println("u=>"+u);//错误时的返回值
        }).exceptionally((e)->{
            System.out.println(e.getMessage()); //可以获取到错误的返回结果
            return 233;
        }).get();
    }
}
