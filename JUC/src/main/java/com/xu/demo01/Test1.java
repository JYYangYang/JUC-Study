package com.xu.demo01;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;

public class Test1 {
    public static void main(String[] args) throws InterruptedException {
      //并发：多个线程操作同一个资源类
        Ticket ticket=new Ticket();
        //函数式编程 jdk1.8 lambda表达式（参数）->{代码}
        new Thread(()->{
            for(int i=1;i<60;i++){
                ticket.sale();
            }

        },"A"
        ).start();

        new Thread(()->{
            for(int i=1;i<60;i++){
                ticket.sale();
            }

        },"B"
        ).start();

        new Thread(()->{
            for(int i=1;i<60;i++){
                ticket.sale();
            }

        },"C"
        ).start();



    }
}

//资源类
class  Ticket{
    private int number=50;

    public synchronized void sale(){
        if(number>0){
            System.out.println(Thread.currentThread().getName()+"卖出了"+(number--)+"票,剩余"+number);
        }
    }
}
