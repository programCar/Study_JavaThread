package ReentrantLock.alternatePrint;

import java.util.concurrent.locks.Condition;

/**
 * 对Reentrant_Lock类的测试
 */
public class ReentrantLock_Demo {

    public static void main(String[] args) {

        Reentrant_Lock print = new Reentrant_Lock(5);

        Condition a = print.newCondition();

        Condition b = print.newCondition();

        Condition c = print.newCondition();

        Thread _a = new Thread(() -> {

            print.print(a,b,"a");

        },"a");

        Thread _b = new Thread(() -> {

            print.print(b,c,"b");

        },"b");

        Thread _c = new Thread(() -> {

            print.print(c,a,"c");

        },"c");

        _a.start();

        _b.start();

        _c.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        print.lock();
        try{
            a.signal();
        }finally {
            print.unlock();
        }

    }


}
