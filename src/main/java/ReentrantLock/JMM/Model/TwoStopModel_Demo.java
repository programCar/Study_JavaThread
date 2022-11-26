package ReentrantLock.JMM.Model;

public class TwoStopModel_Demo {

    public static void main(String[] args) {

        Improve_Monitoring_Simulation monitor = new Improve_Monitoring_Simulation();

        monitor.start_Monitor();

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        monitor.stop_Monitor();

    }
}
