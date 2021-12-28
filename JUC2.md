## 11.线程池

- 池化技术

  - 程序的运行，本质：占用系统的资源！利用池化技术去优化资源的使用

- 线程池、连接池、内存池、对象池

- 池化技术：事先准备好一些资源，有人要用，就来拿，用完之后归还

- 好处

  1. 降低资源的消耗
  2. 提高响应的速度
  3. 方便管理

  **线程复用，可以控制最大并发数，管理线程**





- 线程池不允许Executors去创建，而是通过ThreadpoolExecutor



### 1.三大方法

#### 1.单线程

```java
Executors.newSingleThreadExecutor();
```





```java
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
```

#### 2. 创建一个固定的线程池的大小

```java
public static ExecutorService newFixedThreadPool(int nThreads, ThreadFactory threadFactory) {
    return new ThreadPoolExecutor(nThreads, nThreads,
                                  0L, TimeUnit.MILLISECONDS,
                                  new LinkedBlockingQueue<Runnable>(),
                                  threadFactory);
}
```



#### 3.可伸缩的，遇强则强，遇弱则弱

```java
public static ExecutorService newCachedThreadPool() {
    return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                                  60L, TimeUnit.SECONDS,
                                  new SynchronousQueue<Runnable>());
}
```





### 2. 7大参数

```java
    public static ExecutorService newSingleThreadExecutor() {
        return new FinalizableDelegatedExecutorService
            (new ThreadPoolExecutor(1, 1,
                                    0L, TimeUnit.MILLISECONDS,
                                    new LinkedBlockingQueue<Runnable>()));
    }



    public static ExecutorService newCachedThreadPool() {
        return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                                      60L, TimeUnit.SECONDS,
                                      new SynchronousQueue<Runnable>());
    }

    public static ExecutorService newFixedThreadPool(int nThreads, ThreadFactory threadFactory) {
        return new ThreadPoolExecutor(nThreads, nThreads,
                                      0L, TimeUnit.MILLISECONDS,
                                      new LinkedBlockingQueue<Runnable>(),
                                      threadFactory);
    }
```

- 本质都是ThreadPoolExecutor



```java
 public ThreadPoolExecutor(int corePoolSize,//核心线程大小
                              int maximumPoolSize,//最大核心线程池大小
                              long keepAliveTime, //超时了没有人调用就会释放
                              TimeUnit unit,//超时单位
                              BlockingQueue<Runnable> workQueue,//阻塞队列
                              ThreadFactory threadFactory, //线程工厂，创建线程的，一般不用动
                              RejectedExecutionHandler handler //拒绝策略
                          ) {
        if (corePoolSize < 0 ||
            maximumPoolSize <= 0 ||
            maximumPoolSize < corePoolSize ||
            keepAliveTime < 0)
            throw new IllegalArgumentException();
        if (workQueue == null || threadFactory == null || handler == null)
            throw new NullPointerException();
        this.corePoolSize = corePoolSize;
        this.maximumPoolSize = maximumPoolSize;
        this.workQueue = workQueue;
        this.keepAliveTime = unit.toNanos(keepAliveTime);
        this.threadFactory = threadFactory;
        this.handler = handler;
    }

   public ThreadPoolExecutor(int corePoolSize,
                              int maximumPoolSize, 
                              long keepAliveTime,
                              TimeUnit unit,  
                              BlockingQueue<Runnable> workQueue, 
                              RejectedExecutionHandler handler
                            ) {
        this(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
             Executors.defaultThreadFactory(), handler);
    }
```





### 3.四种拒绝策略

![image-20211221092223044](D:\typora Data\image-20211221092223044.png)

```java
new ThreadPoolExecutor.AbortPolicy()//银行满了，还有人进来不处理这个人的，抛出异常
new ThreadPoolExecutor.CallerRunsPolicy() //哪来的去哪里
new ThreadPoolExecutor.DiscardPolicy()//队列满了，就会丢弃业务不会抛出异常
new ThreadPoolExecutor.DiscardOldestPolicy()//队列满了，尝试和最早的竞争，不会抛出异常
```





#### 1.最大线程如何定义

##### 1.cpu密集型

- 几核，就是几个可以保持cpu的效率最高



##### 2.io密集型

- io十分占用资源，判断你的程序中时分耗io的线程









## 12.四大函数式接口

- Lambda表达式、链式编程、函数式接口、Stream流式计算



### 1.函数式接口

```java
@FunctionalInterface
public interface Runnable {

    public abstract void run();
}

//简化编程模型，在新版本的框架底层大量应用

```



```java
public class Demo01 {

    //function函数形接口，有一个输入参数，有一个输出
    //只要是函数型接口，可以用Lambda表达式简化
    public static void main(String[] args) {

        Function<String, String> function = new Function<String, String>() {

            @Override
            public String apply(String s) {
                return s;
            }
        };
       //用lambda简化
        Function<String, String> function=(str)->{return str};
        System.out.println(function.apply("asdas"));
    }
}
```



### 2.断定型接口

- 有一个输入参数，返回值只能是布尔值！

```java
public static void main(String[] args) {


    Predicate<String> predicate=new Predicate<String>(){

        @Override
        public boolean test(String s) {
            return false;
        };
    };
    System.out.println(predicate.test("sasd"));
    //简化Lambda表达式
    Predicate<String> predicate1=(str)->{ return str.isEmpty(); };
}
```













### 3.消费型接口

- 只有输入，没有返回值

```java
public class Demo03 {
    public static void main(String[] args) {
        Consumer<String> consumer= new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        };
        Consumer<String> consumer1=(s)->{
            System.out.println(s);
        };
        consumer1.accept("sdasd");
    }
}
```



### 4.供给型接口

- 没有参数，只有返回值

```java
public class Demo04 {
    public static void main(String[] args) {
        Supplier<String> supplier=new Supplier<String>() {
            @Override
            public String get() {

                return "123";
            }
        };

        Supplier<String> supplier1=()->{
            return "123123";
        };

        System.out.println(supplier1.get());
    }
}
```





## 13. ForkJoin

- ForkJoin在JDK1.7，并行执行任务！提高效率，大数据量
- 大数据：Map Reduce(把大人物拆分成小任务)

![image-20211222094210107](D:\typora Data\image-20211222094210107.png)



### 1.工作窃取

- 

![image-20211222094414149](D:\typora Data\image-20211222094414149.png)



- B任务执行完之后，就去A中把A中的任务拿过来执行
- 这里面维护的是双端队列





### 2.实例

1. 先要继承RecursiveTask
2. 重写compute()方法

```java
package com.xu.ForkJoin;

import java.util.concurrent.RecursiveTask;

public class ForkJoinDemo extends RecursiveTask<Long> {

    private Long start;  //1
    private Long end;    //1990900000

    //临界值
    private Long temp=10000L;

    public ForkJoinDemo(Long start,Long end){
        this.start=start;
        this.end=end;
    }
    //计算方法
    @Override
    protected Long compute() {
        if((end-start)<temp){
            Long  sum=0L;
            for(Long i=start;i<=end;i++){
                sum+=i;
            }
            return sum;
        }else{
           Long middle=(end+start)/2;  //中间值
           ForkJoinDemo task1=new ForkJoinDemo(start,middle);
           task1.fork();  //拆分任务，把任务压入线程队列
            ForkJoinDemo task2=new ForkJoinDemo(middle+1,end);
            task2.fork();
          return   task1.join()+task2.join();
        }
    }



}
```

### 3.比较几种方法和执行forkjoin

- 执行ForkJoin就得用ForkJoinPool

```java
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
            ForkJoinPool forkJoinPool = new ForkJoinPool(); //执行ForkJoin就得用ForkJoinPool
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
```





## 14.异步回调

>Future 设计的初衷：对将来的某个时间的结果进行建模



- 就是客户端请求服务器后，客户端去做其他事情，等到服务器端执行完返回后，服务端再去处理





### 1.没有返回值的异步回调

```java
public static void main(String[] args) throws ExecutionException, InterruptedException {
    //没有返回值的异步回调
    CompletableFuture<Void> completableFuture=CompletableFuture.runAsync(()->{
        try{
            TimeUnit.SECONDS.sleep(2);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+"runAsync=》void");

    });
    System.out.println("1111");
 completableFuture.get();//获取阻塞执行结果
}
```





### 2.有返回值的异步回调



```java
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
```





## 15.JMM

>Volatile

- 是Java虚拟机提供的轻量级同步机制
  1. 保证可见性
  2. 不保证原子性
  3. 进制指令重排

>JMM

- java内存模型
- 不存在的东西
- 就是一个约定

**关于JMM的约定**

1. 线程解锁前，必须把共享变量立刻刷回内存
2. 线程加锁前，必须读取内存中的最新值到工作内存中
3. 必须保证加锁和解锁是同一把锁



```java
内存分为工作内存和主内存
    每一个线程有一个工作内存
    
```



- 八个操作

  ![image-20211223100540009](D:\typora Data\image-20211223100540009.png)



- 问题： 线程B修改了值，但是线程A不能及时可见



## 16.Volatile

### 1.保证可见性

```java
package com.xu.Volitile;

import java.util.concurrent.TimeUnit;

public class JMMDemo {
    private volatile static int num=0;
    public static void main(String[] args) {
        //线程1
        new Thread(()->{
            while(num==0){

            }
        }).start();


        //主线程
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        num=1;

        System.out.println(num);
    }
}

```

- 加上Volitile之后，线程1在主线成修改num之后立刻会同步

### 2.不保证原子性

- 原子性:不可分割
- 线程A在执行任务的时候,不能被打扰的,也不能被分割的,要么同时成功,要么同时失败.



```java
package com.xu.Volitile;

public class VolitileDemo {
    private volatile static  int num=0;

    private static void add(){
        num++;
    }

    public static void main(String[] args) {
        for(int i=0;i<=20;i++){
            new Thread(()->{
                for(int j=0;j<1000;j++){
                    add();
                }
            }).start();
        }
        while(Thread.activeCount()>2){
            Thread.yield();
        }
        System.out.println(Thread.currentThread().getName()+""+num);
    }
}

```



- 如何在不加Synchronized和Lock的时候保证原子性
- 使用原子类

![image-20211224092040624](D:\typora Data\image-20211224092040624.png)

```java
package com.xu.Volitile;

import java.util.concurrent.atomic.AtomicInteger;

public class VolitileDemo {
    private static volatile AtomicInteger num=new AtomicInteger();

    private static void add(){
        num.getAndIncrement(); //AtomicInteger
    }

    public static void main(String[] args) {
        for(int i=0;i<=20;i++){
            new Thread(()->{
                for(int j=0;j<1000;j++){
                    add();
                }
            }).start();
        }
        while(Thread.activeCount()>2){
            Thread.yield();
        }
        System.out.println(Thread.currentThread().getName()+""+num);
    }
}
```



### 3.指令重排

- 你写的程序,计算机按照你写的那样去执行
- 源代码-> 编译器优化的重排->指令并行也可能会重排->内存系统也会重排->执行

- 处理器在执行指令重排的时候,会考虑道数据之间的依赖性

```java
int x=1;//1
int y=2; //2
x=x+5; //3
y=x*x;//4

我们所期望的是: 1234 但是可能执行的时候变回 2134 1324
但不可能是4123
```

![image-20211224094120249](D:\typora Data\image-20211224094120249.png)





## 17. CAS

```java
java 无法操作内存
java可以调用C++  用Native
C++可以操作内存
Unsafe内相当于java的后门,通过这个类可以操作内存
```





```java
package com.xu.CAS;

import java.util.concurrent.atomic.AtomicInteger;

//compareAndSet():比较并交换
public class CASDemo {
    public static void main(String[] args) {
        AtomicInteger ato = new AtomicInteger(2020);
        // public final boolean compareAndSet(int expect, int update)
        //如果期望的值达到了，那么就更新，否则，就不更新，CAS是CPU的并发原语。
        boolean b = ato.compareAndSet(2020, 2021);
        System.out.println(b);
    }

}
```





**CAS:** 比较工作内存中的值和主内存的值,如果这个值是期望的饿,那么执行操作,如果不是就一直循环.

CPU指令级的操作



**缺点:**

- 循环耗时
- 一次性只能保证一个共享变量的原子性
- ABA问题



### 1.CAS问题--ABA

![image-20211225092238386](D:\typora Data\image-20211225092238386.png)



- 也就是线程1去拿A，期望是1，就更新为2.
- 但是线程2 也拿到了A期望是1 就更新为3。但是又进行了一次CAS更新为了1.
- 此时线程1不知道。
- 对于我们平时的SQL就用乐观锁





>所有相同类型的包装类对象之间值的比较，全部使用equals方法比较。
>
>对于Integer var =? 在-128-127之间的赋值，Integer对象是在IntegerCache.Cache产生,会复用已有的对象，这个区间内的Integer值可以直接使用==进行判断，但是这个区间之外的所有数据，都会在堆上产生，并不会复用已有的对象。







## 18.原子引用

- 对于CAS的ABA问题可以使用原子引用

- 也就是加上版本号，如果一个线程修改了值，就会将版本号也修改，如果另一个线程查到版本号也修改了，那就更新失败，重新去拿版本号。

- 就是乐观锁

- 需要用到AtomicStampedReference这个类。参数为初始值和版本的初始值。

```java
package com.xu.CAS;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

//compareAndSet():比较并交换
public class CASDemo {
    public static void main(String[] args) {
        AtomicStampedReference<Integer> ato=new AtomicStampedReference<>(2,1);
        new Thread(()->{
            int stamp=ato.getStamp();
            System.out.println("a=>"+stamp);

            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            ato.compareAndSet(2,3,
            ato.getStamp(),ato.getStamp()+1
            );

            System.out.println("a=>"+ato.getStamp());

            ato.compareAndSet(3,2,
                    ato.getStamp(),ato.getStamp()+1
            );

            System.out.println("a=>"+ato.getStamp());
        }).start();

        new Thread(()->{
            int stamp=ato.getStamp();
            System.out.println("b=>"+stamp);

            ato.compareAndSet(2,6,
                    ato.getStamp(),ato.getStamp()+1
                    );
            System.out.println("b=>"+ato.getStamp());
        }).start();
    }

}
```







>乐观锁：
>
>乐观锁的实现方式主要有两种：CAS机制和版本号机制





## 19.各种锁的理解

### 1.公平锁和非公平锁

- 公平锁： 非常公平，不能够插队，必须先来后到
- 非公平锁。非常不公平，可以插队（默认都是非公平）

```java
public ReentrantLock() {
        sync = new NonfairSync();
    }
public ReentrantLock(boolean fair) {
    sync = fair ? new FairSync() : new NonfairSync();
}
```

### 2.可重用锁



![image-20211227093326871](D:\typora Data\image-20211227093326871.png)



### 3.自旋锁

- spinlock

  

![image-20211227094545427](D:\typora Data\image-20211227094545427.png)



```java
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

```

```java
package com.xu.SpinLock;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

public class SpinLockTest {
    public static void main(String[] args) throws InterruptedException {
        SpinLockDemo lock=new SpinLockDemo();
        new Thread(()->{
            lock.myLock();
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.myUnlock();
            }

        },"T1").start();

        TimeUnit.SECONDS.sleep(1);
        new Thread(()->{
            lock.myLock();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.myUnlock();
            }

        },"T2").start();
    }



}
```









## 20.死锁

```java
package com.xu.DeadLock;

import java.util.concurrent.TimeUnit;

public class DeadLockDemo {
    public static void main(String[] args) {
        String lockA="lockA";
        String lockB="lockB";
        new Thread(new MyThread(lockA,lockB),"T1").start();
        new Thread(new MyThread(lockB,lockA),"T2").start();
    }


}

class MyThread implements Runnable{
    private String lockA;
    private String lockB;

    public MyThread(String lockA,String lockB){
        this.lockA=lockA;
        this.lockB=lockB;
    }

    public void run(){
        synchronized (lockA){
            System.out.println(Thread.currentThread().getName()+"lock:"+lockA+"=>get"+lockB);
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lockB){
                System.out.println(Thread.currentThread().getName()+"lock:"+lockB+"=>get"+lockA);
            }
        }
    }
}
```





### 1.解决问题

#### 1.使用jps定位进程号

- 使用``jsp -l ``查看进程号
- 使用``jstack 进程号``查看进程



面视