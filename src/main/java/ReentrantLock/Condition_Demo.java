package ReentrantLock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Condition学习，改进模拟送外卖类的运行
 */
@Slf4j
public class Condition_Demo {

    private static ReentrantLock lock = new ReentrantLock();

    public static Condition chicken_takeout = lock.newCondition();

    public static Condition duck_takeout = lock.newCondition();

    private static boolean chicken_go = false;

    private static boolean duck_go = false;

    public static void main(String[] args) {

        Thread second_Dog = new Thread(() -> {

            lock.lock();

            try{

                log.debug("鸡腿到了没？？？");

                while(!chicken_go){
                    log.debug("竟然还没到.......");
                    try {
                        chicken_takeout.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                log.debug("有鸡腿吃喽......");

            }finally {
                lock.unlock();
            }

        },"二狗子");

        Thread big_Cat = new Thread(() -> {

            lock.lock();
            try{

                log.debug("鸭腿到了没？？？？");
                while(!duck_go){

                    log.debug("鸭腿竟然还没到.......不会吧");

                    try {
                        duck_takeout.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                log.debug("有鸭腿吃喽........");

            }finally {
                lock.unlock();
            }

        },"喵大怪");


        Thread takeOur = new Thread(() -> {

           lock.lock();
           try {
               log.debug("吼吼，鸡腿来喽!!");

               chicken_go = true;

               chicken_takeout.signal();
           }finally {
               lock.unlock();
           }

        },"外卖员");

        Thread takeOur2 = new Thread(() -> {

            lock.lock();
            try{
                log.debug("吼吼，鸭腿来喽!!");

                duck_go = true;

                duck_takeout.signal();
            }finally {
                lock.unlock();
            }

        },"外卖员");

        second_Dog.start();
        big_Cat.start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        takeOur.start();
        takeOur2.start();

    }



}
