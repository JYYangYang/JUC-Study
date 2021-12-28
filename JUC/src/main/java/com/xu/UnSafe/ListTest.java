package com.xu.UnSafe;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class ListTest {
    public static void main(String[] args) {
        List<String> list= new ArrayList<>();
        new Thread(()->{
            list.add(UUID.randomUUID().toString().substring(0,5));
            System.out.println(list);
        },"a").start();
    }}

