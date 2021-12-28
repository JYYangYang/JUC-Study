## 1.概述

- 三个包
  - java.util.concurrent
  - java.util.concurrent.atomic
  - java.util.concurrent.locks

- Java Util工具包



## 2.线程和进程

- 进程：一个程序，

- java默认两个线程。Mian、Gc
- 对于java而言：Thread、Runable、Callable

- java调用底层的c++去开线程



- 查看电脑的cpu数

```java
  public static void main(String[] args) {
        System.out.println(Runtime.getRuntime().availableProcessors());
    }
```



## 3.多线程 

- 线程的状态（Thread.state）
  - New新生
  - RunNable: 运行
  - Blocked:阻塞 
  - Waitting: 等待
  - timed_watting: 超时等待
  - Terminated: 终止

### 1.wait和sleep（Thread.sleep）的区别

#### 1.来自不同的类

- wait 来自Object
- Sleep来自 Thread

```java
 Thread.sleep(30000);
```



#### 2.关于锁的释放

- wait会释放锁，sleep不会释放。

#### 3.使用的范围是不同的

- sleep可以在任何地方

- wait：在同步代码块中

#### 4.是否捕获异常

- wait不需要
- sleep需要



## 4.Lock锁

- 真正的多线程开发，线程就是一个单独的资源类，没有任何附属的操作

- 属性和方法

```java
lambda表达式：（针对接口的）
    - (参数)->{代码}



- 例子
    
        new Runnable(){
            @Override
            public int run(a,b) {
                
            }
        };



改写为
    - (a,b)->{
          return  a+b
            }
```

- 创建一个进程

  - 用Thread

  - Thread有好几个构造函数

    ```java
      public Thread(Runnable target, String name) {
            init(null, target, name, 0);
        }
    ```

    

  - 基本上都会用到Runnable

  ```java
  @FunctionalInterface
  public interface Runnable {
      /**
       * When an object implementing interface <code>Runnable</code> is used
       * to create a thread, starting the thread causes the object's
       * <code>run</code> method to be called in that separately executing
       * thread.
       * <p>
       * The general contract of the method <code>run</code> is that it may
       * take any action whatsoever.
       *
       * @see     java.lang.Thread#run()
       */
      public abstract void run();
  }
  ```

  



- 创建进程

```java
new Thread(()->{
    业务逻辑
},线程名字)
```





### 1.卖票例子(传统的Synchronized锁)

- 首先有一个资源类

```java
class  Ticket{
    private int number=50;

    public synchronized void sale(){
        if(number>0){
            System.out.println(Thread.currentThread().getName()+"卖出了"+(number--)+"票,剩余"+number);
        }
    }
}
```

- 然后再有一个业务类

```java
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
```

- 此时多线程出问题了，一般的做法就是在方法上加上synchronized锁。
  - 其实就是排队和锁

```java

    public synchronized void sale(){
        if(number>0){
            System.out.println(Thread.currentThread().getName()+"卖出了"+(number--)+"票,剩余"+number);
        }
    }
```

### 2.Lock锁

![image-20211213091342203](D:\typora Data\image-20211213091342203.png)

![image-20211213091426925](D:\typora Data\image-20211213091426925.png)





>公平锁：十分公平，先来后到
>
>**非公平锁：可以插队（默认）**

#### 1.实现步骤

##### 1.现在资源类中抢夺的资源类中创建锁

- 一般就是ReentrantLock可重用锁

```java
    Lock lock=new ReentrantLock();
```

##### 2.加锁

```java
 lock.lock();
```

##### 3.要在trycatch中写业务并在finally中解锁

```java

        try{
            if(number>0){
                System.out.println(Thread.currentThread().getName()+"卖出了"+(number--)+"票,剩余"+number);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            lock.unlock();
        }
```









```java
class  Ticket2{
    private int number=50;

    Lock lock=new ReentrantLock();

    public void sale(){
        lock.lock();

        try{
            if(number>0){
                System.out.println(Thread.currentThread().getName()+"卖出了"+(number--)+"票,剩余"+number);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            lock.unlock();
        }
    }
```









### 3.Synchronized和Lock锁的区别

1. Synchronized是内置的Java关键子，Lock是一个java类
2. Synchronized无法判断锁的庄涛，Lock可以判断是否获取到了锁
3. Synchronized会自动释放锁，Lock必须要手动释放锁。
4. Synchronized线程1（获得锁、阻塞）、线程2（等待）；Lock锁就不一定会等待下去
5. Synchronized可重入锁，不可以终端，非公平。Lock，可重入锁，可以判断锁，非公平
6. Synchronized适合锁少量的代码同步问题，Lock适合锁大量的同步代码。











## 5.生产者消费者

### 1.Synchronize

- notify
- wait
- synchronize

#### 1.资源类

```java
class Data{
    private int number=0;

    public synchronized  void increment() throws InterruptedException {
        if(number!=0){
            //等待
         this.wait();
        }
        number++;
        System.out.println(Thread.currentThread().getName()+"=>"+number);
        this.notifyAll();
    }


    public  synchronized  void decrement() throws InterruptedException {
        if(number==0){
            this.wait();
        }
        number--;
        this.notifyAll();
        System.out.println(Thread.currentThread().getName()+"=>"+number);
    }
}

```



#### 2.主类

```java
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
```



#### 3.问题

- 当再更加几个现成的时候，这时就会出现问题

![image-20211214090316589](D:\typora Data\image-20211214090316589.png)





```java
wait方法
    - 使得当前线程立刻停止运行，处于等待状态（WAIT），并将当前线程置入锁对象的等待队列中，直到被通知（notify）或被中断为止。wait方法只能在同步方法或同步代码块中使用，而且必须是内建锁。wait方法调用后立刻释放对象锁
    
    
    

notify()方法：
    唤醒处于等待状态的线程用来唤醒等待该对象的其他线程。如果有多个线程在等待，随机挑选一个线程唤醒（唤醒哪个线程由JDK版本决定）。notify方法调用后，当前线程不会立刻释放对象锁，要等到当前线程执行完毕后再释放锁。
```





>虚假唤醒：
>
>其实就是if语句只会执行一次，也就是拿线程A来说，当进入if语句的时候，this.wait会释放锁，当再次拿到资源之后，不会去执行if而是直接跳到下边的语句执行。
>
>所以，一般要用while



```java
 public synchronized  void increment() throws InterruptedException {
        while(number!=0){
            //等待
         this.wait();
        }
        number++;
        System.out.println(Thread.currentThread().getName()+"=>"+number);
        this.notifyAll();
    }

```

### 2.JUC版的生产者消费者

- Lock
- Condition

#### 1.资源类



```java
class Data1{
    private int number=0;
    Lock lock=new ReentrantLock();// 首先先创建出锁
    Condition condition=lock.newCondition();  //然后再创建出类
    public  void increment() throws InterruptedException {
        lock.lock();//首先现在方法中加锁
        try{

            while(number!=0){
                //等待
                condition.await();   //等待使用condition的await
            }
            number++;
            System.out.println(Thread.currentThread().getName()+"=>"+number);
            condition.signalAll();   //通知其他线程用condition的signalAll
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            lock.unlock();  //解锁
        }
    }


    public    void decrement() throws InterruptedException {
        lock.lock();

        try{

            while(number==0){
                condition.await();
            }
            number--;
            condition.signalAll();
            System.out.println(Thread.currentThread().getName()+"=>"+number);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            lock.unlock();
        }

    }
}
```





- 基本的路线
  - 在类中创建出Lock和Condition类
  - 首先在拿到资源的方法中加锁
  - 然后在try中写业务，在fianlly中解锁







### 3.Condition精准的去控制唤醒

- 创建多个condition，每个condition代表着一个线程
- 每次执行完一个线程之后就能去精准的去唤醒下一个指定的线程





资源类

```java
class Date2{
    Lock lock=new ReentrantLock();
    Condition condition1=lock.newCondition();
    Condition condation2=lock.newCondition();
    Condition condition3=lock.newCondition();

    private int number=1;
    public void printA(){
        lock.lock();
        try{
            while(number!=1){
                condition1.await();
            }
            number=2;
            System.out.println(Thread.currentThread().getName());
            condation2.signal();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            lock.unlock();
        }
    }
    public void printB(){
        lock.lock();
        try{
     while(number!=2){
         condation2.await();
     }
     number=3;
            System.out.println(Thread.currentThread().getName());
     condition3.signal();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            lock.unlock();
        }
    }
    public void printC(){
        lock.lock();
        try{
      while(number!=3){
          condition3.await();
      }
      number=1;
            System.out.println(Thread.currentThread().getName());
      condition1.signal();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            lock.unlock();
        }
    }

}
```





主类

```java
public class Demo03 {

    public static void main(String[] args) {

        Date2 data=new Date2();
        new Thread(()->{for(int i=0;i<10;i++){data.printA();}},"A").start();
        new Thread(()->{for(int i=0;i<10;i++){data.printB();}},"B").start();
        new Thread(()->{for(int i=0;i<10;i++){data.printC();}},"C").start();
    }
}
```

## 5. 8锁现象

1. 有锁的情况下，对于同一个资源，谁先拿到锁谁先执行
2. 如果没有锁的方法，就会先去执行
3. synchronized锁的是对象的方法调用者
4. 如果方法中加上static，就是锁的是class







## 6.集合类不安全



### 1.ArrayList

- 多线程下使用ArrayList会出异常

java.util.ConcurrentModificationException： 并发修改异常

```java
public class ListTest {
    public static void main(String[] args) {
        List<String> list= new ArrayList<>();
        new Thread(()->{
            list.add(UUID.randomUUID().toString().substring(0,5));
            System.out.println(list);
        },"a").start();
    }}
```





#### 1.解决方案



1. ```
   List<String> list= Collections.synchronizedList(new ArrayList<>());
   ```

2. ```
   List<String> list= new CopyOnWriteArrayList<>();
   ```

- CopyOnWrite 写入时复制  是计算机程序设计领域的一个优化策略。多个线程的调用的时候，List，list读取的时候，在写入的时候避免覆盖，造成数据问题。
- 只要有synchronized的方法效率会比较低

```java
系统调用 fork() 创建了父进程的一个复制，以作为子进程。传统上，fork() 为子进程创建一个父进程地址空间的副本，复制属于父进程的页面。然而，考虑到许多子进程在创建之后立即调用系统调用 exec()，父进程地址空间的复制可能没有必要。
```



### 2.Set集合



```java
public class SetTest {
    public static void main(String[] args) {
        Set<String> set=new HashSet<>();

        for(int i=0;i<=30;i++){
            new Thread(()->{
                set.add(UUID.randomUUID().toString().substring(0,5));
                System.out.println(set);
            },String.valueOf(i)).start();
        }
    }
}
```

#### 1.解决方案

1. ```java
   Set<String> set= Collections.synchronizedSet(new HashSet<>());
   ```

2. 

   ```java
   Set<String> set=new  CopyOnWriteArraySet<>();
   ```





### 3.HashMap

```java
public class MapTest {

    public static void main(String[] args) {
        Map<String,String> map=new HashMap<>();
        for(int i=0;i<30;i++){


        new Thread(()->{
            map.put(Thread.currentThread().getName(), UUID.randomUUID().toString().substring(0,5));
            System.out.println(map);

        },String.valueOf(i)).start();
        }
    }
}
```



#### 1.解决方法

1. 

```
Map<String,String> map=new ConcurrentHashMap<>();
```





## 7. Callable

- 在thread中不需要用Runnable了，改用Callable
- 首先要在资源类上继承Callable
- 然后在线程类上用FutureTask

- 也可以拿到返回值



- 资源类

```java
class MyThread implements Callable<Integer> {
    public Integer  call(){
        System.out.println("call()");
        return 1024;
    }
```

- 线程类

```java

    public static void main(String[] args) {

        MyThread myThread=new MyThread();
        FutureTask futureTask=new FutureTask(myThread);

        new Thread(futureTask,"A").start();
        new Thread(futureTask,"B").start();
    }

```



- ```java
  String o =(String)futureTask.get();
  ```

  - 可以拿到返回值



## 8.三个辅助类



### 1.CountDownLatch

```java
public class CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch  countDownLatch=new CountDownLatch(6);
        for(int i=1;i<=6;i++){
            new Thread(()->{
                System.out.println(Thread.currentThread().getName());
                countDownLatch.countDown();
            },String.valueOf(i)).start();


        }
        countDownLatch.await();//等待计数器归零，然后再向下执行

        System.out.println("close Door");
    }
}
```



- 每次有线程调用countDown()数量减一，假设计数器变为0 ，countDownLatch.await()就会被唤醒，继续执行

### 2.CyclicBarrier

- 计数增加

```java
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
```



- 增加到7个线程的时候就会输出

### 3.Semaphore

- 维持一组许可证，如果有必要，每个acquire（）都会阻塞，知道许可证可用，然后才能使用它。
- Semaphore: 信号量



```java
 public static void main(String[] args) {
        Semaphore semaphore =new Semaphore(3);//线程数量
        for(int i=1;i<6;i++){
            //acquire 得到

            new Thread(()->{
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName()+"抢到车位");
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println(Thread.currentThread().getName()+"离开车位");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    semaphore.release();//释放
                }
            },String.valueOf(i)).start();

        }
    }
```

- semaphore.acquire(): 获得，假设如果已经满了。等待，等待直到被释放为止
- semaphore.release(): 释放，会将当前信号量释放+1，然后唤醒等待的线程
- 作用：多个共享资源互斥的使用！并发限流，控制最大的线程数。







## 9.读写锁

- 操纵读的时候就读，写的时候就写
- 写的时候就只有一个线程写，读的时候所有都可用读



```java
package com.xu.ReadWriteLock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockDemo {
    public static void main(String[] args) {

        MycacheLock mycache=new MycacheLock();

        for(int i=0;i<8;i++){
            final int temp=i;
            new Thread(()->{
                mycache.Write(temp+"",temp+"");
            },String.valueOf(i)).start();
        }

        for(int i=0;i<8;i++){
            final int temp=i;
            new Thread(()->{
                mycache.Read(temp+"");
            },String.valueOf(i)).start();
        }
    }



}

class MycacheLock{
    private  volatile Map<String,Object> map=new HashMap<>();

   ReadWriteLock lock= new ReentrantReadWriteLock();  //读写锁
    //写入的时候希望有一个线程写
    public void Write(String key,Object Value){
        lock.writeLock().lock();
        try{
            System.out.println(Thread.currentThread().getName()+"写入内存");
            map.put(key,Value);
            System.out.println(Thread.currentThread().getName()+"写入ok");
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            lock.writeLock().unlock();
        }



    }


    //所有人都可以读
    public void Read(String key){
        lock.readLock().lock();
        try{
            System.out.println(Thread.currentThread().getName()+"读取"+key);
            Object o=map.get(key+"");
            System.out.println(Thread.currentThread().getName()+"读取ok");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            lock.readLock().unlock();
        }


    }



}

class Mycache{
    private  volatile Map<String,Object> map=new HashMap<>();

    public void Write(String key,Object Value){
        System.out.println(Thread.currentThread().getName()+"写入内存");
        map.put(key,Value);
        System.out.println(Thread.currentThread().getName()+"写入ok");

    }


    public void Read(String key){
        System.out.println(Thread.currentThread().getName()+"读取"+key);
        Object o=map.get(key+"");
        System.out.println(Thread.currentThread().getName()+"读取ok");
    }



}
```

- 独占锁：写锁：一次只能被一个线程占用
- 共享锁：读锁： 多个线程同时占用





## 10.阻塞队列

- BlockingQueue
- 多线程、线程池用的多



![image-20211219091340922](D:\typora Data\image-20211219091340922.png)



### 1.四组API

| 方式       | 抛出异常 | 有返回值,不抛出异常 | 阻塞等待 | 超时等待 |
| ---------- | -------- | ------------------- | -------- | -------- |
| 添加       | add      | offer()             | put      | offer    |
| 移除       | remove   | poll()              | take     | pull     |
| 判断队列首 | element  | peek                |          |          |











#### 1.有返回值，抛出异常



```java
public class Test {

    public static void main(String[] args) {
        test1();
    }


    public static void test1(){
        ArrayBlockingQueue blockingQueue=new ArrayBlockingQueue<>(3);
        System.out.println(blockingQueue.add("a"));
        System.out.println(blockingQueue.add("b"));
        System.out.println(blockingQueue.add("c"));
        //java.lang.IllegalStateException: Queue full
        System.out.println(blockingQueue.add("d"));
      System.out.println(blockingQueue.element());

        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        //java.util.NoSuchElementException
        System.out.println(blockingQueue.remove());
       

    }
}

```



- 查看队首： blockingQueue.element()

#### 2.有返回值，不抛出异常



```java
/*

有返回值无异常
 */
    public static void test2(){
        ArrayBlockingQueue blockingQueue=new ArrayBlockingQueue<>(3);
        System.out.println(blockingQueue.offer("a"));
        System.out.println(blockingQueue.offer("b"));
        System.out.println(blockingQueue.offer("c"));
        System.out.println(blockingQueue.offer("d"));
        System.out.println(blockingQueue.peek());
  
    }
}
```

- 查看队首元素blockingQueue.peek()



#### 3.阻塞

```java
    public static void test3() throws InterruptedException {
        ArrayBlockingQueue blockingQueue=new ArrayBlockingQueue<>(3);
        blockingQueue.put("a");
        blockingQueue.put("b");
        blockingQueue.put("c");
        //blockingQueue.put("d");  队列没有位置了，会一致阻塞
        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take());
//        System.out.println(blockingQueue.take()); 没有这个元素，会一致阻塞
    }
```



#### 4.超时等待

```java
public static void test4() throws InterruptedException {
    ArrayBlockingQueue blockingQueue=new ArrayBlockingQueue<>(3);
    blockingQueue.offer("A");
    blockingQueue.offer("B");
    blockingQueue.offer("C");
    blockingQueue.offer("A",2, TimeUnit.SECONDS); //等待两秒，如果拿不到就退出
    System.out.println(blockingQueue.poll());
    System.out.println(blockingQueue.poll());
    System.out.println(blockingQueue.poll());
    blockingQueue.poll(2,TimeUnit.SECONDS);//等到两秒，如果没有就退出
}
```







### 2.SynchronousQueue同步队列

- 进去一个元素，必须等待取出来之后，才能再往里面放一个元素

```java
public class SynchronousQueue {
    public static void main(String[] args) {
        BlockingQueue<String> squeue = new SynchronousQueue<>();


        new Thread(()->{
            try{
                System.out.println(Thread.currentThread().getName()+"put1");
                squeue.put("1");
                System.out.println(Thread.currentThread().getName()+"put1");
                squeue.put("2");
                System.out.println(Thread.currentThread().getName()+"put1");
                squeue.put("3");
            }catch (Exception e){
                e.printStackTrace();
            }
        },"T1").start();

        new Thread(()->{
            try{
                System.out.println(Thread.currentThread().getName()+squeue.take());
                System.out.println(Thread.currentThread().getName()+squeue.take());
                System.out.println(Thread.currentThread().getName()+squeue.take());
            }catch (Exception e){
                e.printStackTrace();
            }
        },"T2").start();
    }
```



- SynchronousQueue不存储元素，put了一个元素





