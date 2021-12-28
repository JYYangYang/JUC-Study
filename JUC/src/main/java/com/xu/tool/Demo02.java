package com.xu.tool;

import java.util.concurrent.*;

public class Demo02 {
    public static void main(String[] args) {
    ExecutorService tool=new ThreadPoolExecutor(2,
            5,
            3,
            TimeUnit.SECONDS,
            new LinkedBlockingDeque<>(3),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.DiscardOldestPolicy()//队列满了，尝试和最早的竞争，不会抛出异常

    ); //单个线程

        try {
        for (int i = 0; i <=9; i++) {
            tool.execute(() -> {
                System.out.println(Thread.currentThread().getName() + "ok");
            });
        }
    }catch (Exception ex){
        ex.printStackTrace();
    }
        finally {
        tool.shutdown();//用完之后一定要关线程池
    }

    }
}

