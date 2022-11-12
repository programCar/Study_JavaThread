package ReentrantLock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class Reentrant_Demo {

    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {

        lock.lock();
        try{
            log.debug("进入主函数......");
            m1();
        }finally {
            lock.unlock();
        }
    }

    public static void m1(){

        lock.lock();
        try{
            log.debug("进入m1方法");
            m2();
        }finally {
            lock.unlock();
        }
    }

    public static void m2(){
        lock.lock();
        try{
            log.debug("进入m2方法");
        }finally {
            lock.unlock();
        }
    }
}
