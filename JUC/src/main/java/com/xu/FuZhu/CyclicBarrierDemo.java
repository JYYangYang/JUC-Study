package com.xu.FuZhu;

import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo {
    public static void main(String[] args) {


        CyclicBarrier cyclicBarrier=new CyclicBarrier(7,()->{
            System.out.println("成功");
        });
        for(int i=0;i<=7;i++){
            final  int temp=i;
            new Thread(()->{
                System.out.println(temp);
                try{
                    cyclicBarrier.await();
                }
                catch (Exception e){
                    e.printStackTrace();
                }


            }).start();
        }

    }
}
