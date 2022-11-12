package ReentrantLock.alternatePrint;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class Synchronized {

    private int print_Number;

    private int this_Number;

    public Synchronized(int print_Number, int this_Number){
        this.print_Number = print_Number;

        this.this_Number = this_Number;
    }

    public final void print(int this_Number,int next_Number,String print){

        for(int i = 0; i < print_Number; i++){

            synchronized (this){

                while(this.this_Number != this_Number){

                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print(print);
                this.this_Number = next_Number;
                this.notifyAll();
            }
        }

    }

}
