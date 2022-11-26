package HappyLock.Atomic_Reference.AtomicMarkable__Reference;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicMarkableReference;

@Slf4j
public class AtomicMarkable_Reference {

    public static void main(String[] args) {

        Trash_Can trash_Can = new Trash_Can("有垃圾",true);

        AtomicMarkableReference<Trash_Can> mark = new AtomicMarkableReference<>(trash_Can,true);

        new Thread(() -> {

            log.debug("清洁工");

            log.debug("有没有垃圾,{}",mark.isMarked());

            log.debug("{}",mark.compareAndSet(trash_Can,new Trash_Can("没有垃圾",false),true,false));
        },"清洁工").start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.debug("有没有垃圾,{}",mark.isMarked());

        log.debug("{}",mark.compareAndSet(trash_Can,new Trash_Can("没有垃圾",false),true,false));

    }


}
