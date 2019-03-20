package com.gupao.proxy.gpproxy;

/**
 * * @Package com.gupao.proxy.gpproxy
 * * @Description: ${todo}
 * * @author caiwei
 * * @date 2019/3/19
 **/
public class ProxyTest {

    public static void main(String[] args) {
        Object instance = new TeacherProxy(new Student()).getInstance();
        ((Person)instance).writeHomework();;
    }
}
