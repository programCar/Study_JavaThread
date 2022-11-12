package ReentrantLock.alternatePrint;

import java.util.concurrent.locks.LockSupport;

/**
 * 多线程交替执行模式的学习--park和unpark
 */
public class ParkUnpark {

    //各线程执行的次数
    private int loopNumber;

    /**
     *  初始化各线程执行的次数
     */
    public ParkUnpark(int loopNumber){
        this.loopNumber = loopNumber;
    }

    /**
     * 实现各线程交替输出
     * @param next_Thread
     * @param print
     */
    public void print(Thread next_Thread, String print){

        //实现各线程loopNumber次交替输出，abc，abc，abc.....
        for (int i = 0; i < loopNumber; i++) {

            LockSupport.park();

            System.out.print(print);

            LockSupport.unpark(next_Thread);
        }

    }


}
