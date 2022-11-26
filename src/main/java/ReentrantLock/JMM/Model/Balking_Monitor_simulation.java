package ReentrantLock.JMM.Model;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Balking_Monitor_simulation {

    private Thread monitor_Thread;

    private volatile boolean stop;

    private boolean creation = false;

    public void start_Monitor(){

        synchronized (this){
            if (creation){
                return;
            }
        }

        creation = true;

        monitor_Thread = new Thread(() -> {

            log.debug("监控已开启....");

            while(true){

                if (stop){
                    log.debug("监控已停止......");
                    break;
                }

                try {
                    Thread.sleep(1000);
                    log.debug("监控进行中......");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    log.debug("监控被意外中断....");
                }
            }

        },"监控");

        monitor_Thread.start();

    }

    public void stop_Monitor(){

        stop = true;

        monitor_Thread.interrupt();
    }
}
