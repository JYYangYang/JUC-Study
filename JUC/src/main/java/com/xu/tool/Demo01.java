package com.xu.tool;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
//线程池
public class Demo01 {

    public static void main(String[] args) {
        ExecutorService e= Executors.newSingleThreadExecutor(); //单个线程
        try {
            for (int i = 0; i < 10; i++) {
                e.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "ok");
                });
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        finally {
            e.shutdown();//用完之后一定要关线程池
        }
    }

}
