package More_Lock.DeadLock.DeadLock_Demo;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "D.Philosopher")
public class Philosopher extends Thread{

    private Chopsticks left_Chopstick;

    private Chopsticks right_Chopstick;

    public Philosopher(){}

    public Philosopher(String name, Chopsticks left_Chopstick, Chopsticks right_Chopstick){

        super(name);

        this.left_Chopstick = left_Chopstick;

        this.right_Chopstick = right_Chopstick;

    }

    @Override
    public void run() {

        while(true){

            synchronized (left_Chopstick){

                synchronized(right_Chopstick){

                    eat();
                }

            }
        }
    }

    private void eat(){

        log.debug("吃吃吃，快吃快吃，这不得狠狠干饭.......");

        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){

            e.printStackTrace();
        }
    }
}
