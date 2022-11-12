package ReentrantLock.Synchronized_OrderGo;

import lombok.extern.slf4j.Slf4j;

/**
 * 多线程可选择性执行模式的学习--Synchronized
 */
@Slf4j
public class Synchronized_OrderGo {

    private static Object object = new Object();

    private static boolean two = false;

    public static void main(String[] args) {

        Thread big_Cat = new Thread(() -> {

            synchronized (object){

                while(!two){

                    try {
                        object.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                log.debug("1");
            }

        },"大猫咪");

        Thread second_Dog = new Thread(() -> {

            synchronized (object){
                log.debug("2");

                two = true;

                object.notify();
            }

        },"二狗子");

        big_Cat.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        second_Dog.start();

    }


}
