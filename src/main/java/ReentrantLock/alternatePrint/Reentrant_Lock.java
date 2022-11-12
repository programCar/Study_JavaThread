package ReentrantLock.alternatePrint;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 多线程交替执行模式的学习--ReentrantLock
 */
public class Reentrant_Lock extends ReentrantLock {

    //各线程执行的次数
    private int loop_Number;

    /**
     * 初始化各线程执行的次数
     * @param loop_Number
     */
    public Reentrant_Lock(int loop_Number){
        this.loop_Number = loop_Number;
    }

    /**
     * 实现各线程交替输出
     * @param _this
     * @param _next
     * @param print
     */
    public void print(Condition _this, Condition _next, String print){

        //实现各线程loopNumber次交替输出，abc，abc，abc.....
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
