package ReentrantLock.alternatePrint;

/**
 * 对Synchronized类的测试
 */
public class Synchronized_Demo {

    public static void main(String[] args) {

        Synchronized sap = new Synchronized(5,0);

        Thread a = new Thread(() -> {

            sap.print(0,1,"a");

        },"a");

        Thread b = new Thread(() -> {

            sap.print(1,2,"b");

        },"b");

        Thread c = new Thread(() -> {

            sap.print(2,0,"c");

        },"c");

        a.start();
        b.start();
        c.start();
    }
}
