package ReentrantLock.alternatePrint;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Reentrant_Lock extends java.util.concurrent.locks.ReentrantLock {

    private int loop_Number;

    public Reentrant_Lock(int loop_Number){
        this.loop_Number = loop_Number;
    }

    public void print(Condition _this, Condition _next, String print){

        for (int i = 0; i < loop_Number; i++){

            this.lock();
            try{
                try {
                    _this.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.print(print);

                _next.signal();
            }finally {
                this.unlock();
            }

        }
    }
}
