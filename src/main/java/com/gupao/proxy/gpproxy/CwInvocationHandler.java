package com.gupao.proxy.gpproxy;

import java.lang.reflect.Method;

/**
 * * @Package com.gupao.proxy.gpproxy
 * * @Description: ${todo}
 * * @author caiwei
 * * @date 2019/3/20
 **/
public interface CwInvocationHandler {

    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable;
}
