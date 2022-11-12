package ReentrantLock.alternatePrint;

import java.util.concurrent.locks.LockSupport;

public class ParkUnpark_Demo {

    private static Thread a;

    private static Thread b;

    private static Thread c;

    public static void main(String[] args) {

        ParkUnpark park = new ParkUnpark(5);

        a = new Thread(() -> {
            park.print(b,"a");
        },"a");



        b = new Thread(() -> {
            park.print(c,"b");
        },"a");

        c = new Thread(() -> {
            park.print(a,"c");
        },"a");

        a.start();

        b.start();

        c.start();

        LockSupport.unpark(a);

    }
}
