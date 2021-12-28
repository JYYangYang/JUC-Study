package com.xu.ProductConsumer;

public class Demo01 {
    public static void main(String[] args) {
        Data date=new Data();
        new Thread(()->{
            for(int i=0;i<10;i++){
                try {
                    date.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        },"A").start();
        new Thread(()->{
           for(int i=0;i<10;i++){
               try {
                   date.decrement();
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }

        },"B").start();


        new Thread(()->{
            for(int i=0;i<10;i++){
                try {
                    date.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        },"C").start();



        new Thread(()->{
            for(int i=0;i<10;i++){
                try {
                    date.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        },"D").start();
    }


}


class Data{
    private int number=0;

    public synchronized  void increment() throws InterruptedException {
        while(number!=0){
            //等待
         this.wait();
        }
        number++;
        System.out.println(Thread.currentThread().getName()+"=>"+number);
        this.notifyAll();
    }


    public  synchronized  void decrement() throws InterruptedException {
        while(number==0){
            this.wait();
        }
        number--;
        this.notifyAll();
        System.out.println(Thread.currentThread().getName()+"=>"+number);
    }
}
