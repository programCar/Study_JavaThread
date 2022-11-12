package ReentrantLock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock的可打断特性的学习
 */
@Slf4j
public class LockInterruptibly_Demo {

   private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {


        Thread thread = new Thread(() -> {

            try {
                log.debug("尝试锁住狗....");

                //如果没有竞争，则获得lock对象锁，如果有，则进入entryList中等待，等待途中可以被打断
                lock.lockInterruptibly();
            }catch (InterruptedException e){
                e.printStackTrace();
                log.debug("糟糕，没有防住嘎子偷狗....");
                return;
            }

            try{
                log.debug("来啊，come baby...");
            }finally {
                log.debug("就这啊，，嘎子");
                log.debug("嘎子偷狗失败，最终释放锁....");
                lock.unlock();
            }

        },"防嘎子偷狗锁");

        lock.lock();

        thread.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        thread.interrupt();
    }
}
