package com.xu.ForkJoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.LongStream;

public class Test {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        test1();//10795
//        test2();//5343
        test3();//392
    }
    //普通写任务
    public static void test1(){
        Long sum=0L;
        Long start=System.currentTimeMillis();
        for(Long i=1L;i<=10_0000_0000;i++){
            sum+=i;
        }
        long end=System.currentTimeMillis();
        System.out.println("sum"+sum+"时间"+(end-start));
    }
    //ForkJoin
        public static void test2() throws ExecutionException, InterruptedException {
            long start=System.currentTimeMillis();
            ForkJoinPool forkJoinPool = new ForkJoinPool();
            ForkJoinTask forkJoinDemo = new ForkJoinDemo(0L, 10_0000_0000L);
            ForkJoinTask submit = forkJoinPool.submit(forkJoinDemo);
            Object o = submit.get();
            long end=System.currentTimeMillis();
            System.out.println("sum"+o+"时间"+(end-start));
    }
    //Stream并行流
    public static void test3(){
        long start=System.currentTimeMillis();
        long sum = LongStream.rangeClosed(0, 10_0000_0000).parallel().reduce(0, Long::sum);
        long end=System.currentTimeMillis();
        System.out.println("sum"+sum+"时间"+(end-start));
    }

}
