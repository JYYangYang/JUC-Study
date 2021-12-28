package com.xu.FunctionInterface;

import java.util.function.Function;

public class Demo01 {

    //function函数形接口，有一个输入参数，有一个输出
    //只要是函数型接口，可以用Lambda表达式简化
    public static void main(String[] args) {
//
//        Function<String, String> function = new Function<String, String>() {
//
//            @Override
//            public String apply(String s) {
//                return s;
//            }
//        };
       //用lambda简化
        Function<String, String> function=(str)->{return str;};
        System.out.println(function.apply("asdas"));
    }
}
