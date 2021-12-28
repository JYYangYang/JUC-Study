package com.xu.FunctionInterface;

import java.util.function.Consumer;

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
