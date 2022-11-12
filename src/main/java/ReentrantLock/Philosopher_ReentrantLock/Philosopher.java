package ReentrantLock.Philosopher_ReentrantLock;

import ReentrantLock.Philosopher_ReentrantLock.Chopsticks;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Philosopher extends Thread{

    private Chopsticks left_Chopstick;

    private Chopsticks right_Chopstick;

    public Philosopher(){}

    public Philosopher(String name,Chopsticks left_Chopstick, Chopsticks right_Chopstick){

        super(name);

        this.left_Chopstick = left_Chopstick;

        this.right_Chopstick = right_Chopstick;

    }

    @Override
    public void run() {

        while(true) {

            if (left_Chopstick.tryLock()){

                try{

                    if (right_Chopstick.tryLock()){

                        try {
                            eat();
                        }finally {
                            right_Chopstick.unlock();
                        }
                    }

                }finally {
                    left_Chopstick.unlock();
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
