package com.gupao.proxy.gpproxy;

/**
 * * @Package com.gupao.proxy.gpproxy
 * * @Description: ${todo}
 * * @author caiwei
 * * @date 2019/3/19
 **/
public class Student implements Person {

    @Override
    public void writeHomework(){
        System.out.println("学生正在写作业!");
    }
}
