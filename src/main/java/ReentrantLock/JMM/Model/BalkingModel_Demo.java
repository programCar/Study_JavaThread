package ReentrantLock.JMM.Model;

public class BalkingModel_Demo {

    public static void main(String[] args) {

        Balking_Monitor_simulation monitor = new Balking_Monitor_simulation();

        new Thread(() -> {

            monitor.start_Monitor();

        },"一号").start();

        new Thread(() -> {

            monitor.start_Monitor();

        },"二号").start();

        new Thread(() -> {

            monitor.start_Monitor();

        },"三号").start();

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        monitor.stop_Monitor();
    }
}
