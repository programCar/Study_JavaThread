package ReentrantLock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock可超时特性的学习
 */
@Slf4j
public class TimeOutLock_Demo {

    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {

        Thread thread = new Thread(() -> {

            try {
                if (!lock.tryLock(2, TimeUnit.SECONDS)) {
                    log.debug("锁没了.....");
                    return;
                }
            }catch (InterruptedException e){
                e.printStackTrace();
                log.debug("锁被偷了........");
                return;
            }

            try{
                log.debug("成功锁住了狗.....");
            }finally {
                lock.unlock();
            }


        },"锁");

        lock.lock();

        log.debug("偷...");

        thread.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        lock.unlock();

        log.debug("好像没用......丢了");
    }
}
