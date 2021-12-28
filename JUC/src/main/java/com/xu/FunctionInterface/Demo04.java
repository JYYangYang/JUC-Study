package com.xu.FunctionInterface;

import java.util.function.Supplier;

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
