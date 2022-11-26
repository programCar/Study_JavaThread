package HappyLock.Atomic_Reference.AtomicStamped__Reference;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicStampedReference;


@Slf4j
public class AtomicStamped_Reference {

    public static void main(String[] args) {

        AtomicStampedReference<String> stamped = new AtomicStampedReference<>("A",0);

        log.debug("Main开始");

        String reference = stamped.getReference();

        int stamp = stamped.getStamp();

        log.debug("{}",stamp);

        new Thread(() -> {

            log.debug("a1开始.....");

            int stamped1 = stamped.getStamp();

            log.debug("{}",stamped.compareAndSet("A","B",stamped1,stamped1+1));

            log.debug("{}",stamped1);

        },"a1").start();

        new Thread(() -> {

            log.debug("a2开始.....");

            int stamped1 = stamped.getStamp();

            log.debug("{}",stamped.compareAndSet("B","A",stamped1,stamped1+1));

            log.debug("{}",stamped1);

        },"a2").start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        log.debug("{}", stamped.compareAndSet("A","C",stamp,stamp+1));

        log.debug("{}",stamp);


    }
}
