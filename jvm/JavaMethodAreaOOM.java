package com.example.demo.test;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 1.8之前：VM Args：-XX:PermSize=10M -XX:MaxPermSize=10M
 * 1.8以后：VM Args：-XX:MetaspaceSize=10m -XX:MaxMetaspaceSize=10m
 */
public class JavaMethodAreaOOM {
    public static void main(String[] args) {
        List<Object> objects=new ArrayList<>();
        while (true) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(OOMObject.class);
            enhancer.setUseCache(false);
            enhancer.setCallback((MethodInterceptor) (obj, method, args1, proxy) -> proxy.invokeSuper(obj, args1));
            Object o = enhancer.create();
            objects.add(o);
            System.out.println(objects.size());
        }
    }

    static class OOMObject {
    }
}
