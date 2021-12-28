package com.xu.FunctionInterface;

import java.util.function.Predicate;

public class Demo02 {
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
}
