package ReentrantLock.alternatePrint;

import java.util.concurrent.locks.LockSupport;

public class ParkUnpark {

    private int loopNumber;

    public ParkUnpark(int loopNumber){
        this.loopNumber = loopNumber;
    }

    public void print(Thread next_Thread, String print){

        for (int i = 0; i < loopNumber; i++) {

            LockSupport.park();

            System.out.print(print);

            LockSupport.unpark(next_Thread);
        }

    }


}
