package Thread_Synchronized;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Thread_synchronized {

    static int counter_test = 0;                                    //未改良程序的共享资源

    static int counter = 0;                                         //改良后的程序共享资源

    static Object Lock = new Object();                              //创建synchronized锁所使用的对象

    public static void main(String[] args) {

        //演示代码，未改良的程序
        {
            Thread thread_testAdd = new Thread(() -> {

                for (int i = 0; i < 5000; i++) {

                    counter_test++;
                }
            }, "test_add");

            Thread thread_testSub = new Thread(() -> {

                for (int i = 0; i < 5000; i++) {

                    counter_test--;
                }
            }, "test_sub");

            thread_testAdd.start();

            thread_testSub.start();

            try {
                thread_testAdd.join();
                thread_testSub.join();
            }catch (InterruptedException e){
                e.printStackTrace();
            }

            log.debug("counter_test的值为 : {}", counter_test);
        }


        //演示代码，改良后的程序
        {
            Thread thread_Add = new Thread(() -> {

                for (int i = 0; i < 5000; i++) {
                synchronized(Lock) {

                    counter++;
                    }
                }
            }, "add");

            Thread thread_Sub = new Thread(() -> {

                for (int i = 0; i < 5000; i++) {
                    synchronized(Lock) {
                        counter--;
                    }
                }
            }, "sub");

            thread_Add.start();

            thread_Sub.start();

            try {
                thread_Add.join();
                thread_Sub.join();
            }catch (InterruptedException e){
                e.printStackTrace();
            }

            log.debug("counter的值为 : {}", counter);
        }
    }
}
