package ReentrantLock.Synchronized_OrderGo;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

@Slf4j
public class Park_Unpark_Demo {

    public static void main(String[] args) {

        Thread big_Cat = new Thread(() -> {

            LockSupport.park();

            log.debug("1");

        },"大猫咪");

        Thread second_Dog = new Thread(() -> {

            log.debug("2");

            LockSupport.unpark(big_Cat);

        },"二狗子");

        big_Cat.start();
        second_Dog.start();
    }
}
