package ReentrantLock.alternatePrint;

import lombok.extern.slf4j.Slf4j;

/**
 * 多线程交替执行模式的学习--Synchronized
 */
@Slf4j
public final class Synchronized {

    //各线程执行的次数
    private int print_Number;

    //当前可执行线程的编号
    private int this_Number;

    /**
     * 初始化各线程执行的次数和当前可执行线程的编号
     * @param print_Number
     * @param this_Number
     */
    public Synchronized(int print_Number, int this_Number){
        this.print_Number = print_Number;

        this.this_Number = this_Number;
    }

    /**
     * 实现各线程交替输出
     * @param this_Number
     * @param next_Number
     * @param print
     */
    public final void print(int this_Number,int next_Number,String print){

        ////实现各线程loopNumber次交替输出，abc，abc，abc.....
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
