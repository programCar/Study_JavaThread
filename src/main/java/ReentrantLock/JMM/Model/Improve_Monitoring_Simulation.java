package ReentrantLock.JMM.Model;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Improve_Monitoring_Simulation {

    private Thread monitor_Thread ;

    private volatile boolean stop = false;

    public void start_Monitor(){

        monitor_Thread = new Thread(() -> {

            log.debug("监控已开启");

            while (true){

                if (stop){

                    log.debug("停止监控.....");

                    break;
                }

                try {

                    Thread.sleep(1000);
                    log.debug("监控进行中......");

                } catch (InterruptedException e) {
                    e.printStackTrace();

                    log.debug("监控意外中断......");
                }


            }

        },"监控线程");

        monitor_Thread.start();

    }

    public void stop_Monitor(){

        stop = true;

        monitor_Thread.interrupt();

    }
}
