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
