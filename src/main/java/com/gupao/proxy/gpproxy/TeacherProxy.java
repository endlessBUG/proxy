package com.gupao.proxy.gpproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * * @Package com.gupao.proxy.gpproxy
 * * @Description: ${todo}
 * * @author caiwei
 * * @date 2019/3/19
 **/
public class TeacherProxy implements CwInvocationHandler{

    private Object object;

    public TeacherProxy(Object object) {
        this.object = object;
    }

    public Object getInstance(){
        return CwProxy.newProxyInstance(new CwClassLoader(),object.getClass().getInterfaces(),this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("老师提交作业");
        Object o =  method.invoke(this.object,args);
        System.out.println("老师批改作业");
        return o;
    }
}
