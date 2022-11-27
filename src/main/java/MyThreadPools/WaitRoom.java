package MyThreadPools;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 此类用于任务量超过线程池数时，超过的任务进行进行等待的房间
 */
@Slf4j
public final class WaitRoom <T>{

    //等待房间的大小
    private int roomNumber;

    //等待的房间
    private ArrayDeque<T> waitRoom;

    //锁，由于房间共用,防止线程问题
    private ReentrantLock roomLock = new ReentrantLock();

    //房间满时，给予生产任务的线程采取等待,
    private Condition fullWaitRoom = roomLock.newCondition();

    //房间空时，给予消费任务的线程采取等待
    private Condition emptyWaitRoom = roomLock.newCondition();

    public WaitRoom(int roomNumber){

        this.roomNumber = roomNumber;

        waitRoom = new ArrayDeque<>(roomNumber);

    }


    /**
     * 当等待房间不为空时，给与消费者任务的方法
     * @return T
     */
    private final T getRunnable(){

        log.debug("进入等待房间，此方法是无防超时方法，开始给予任务........");

        roomLock.lock();
        try{

            //如果房间为空，则进入等待传送任务的线程传送任务
            while(waitRoom.isEmpty()){

                log.debug("警告!任务等待房间为空，没有任务....");

                try {
                    log.debug("进入等待.....");
                    emptyWaitRoom.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            //如果房间不为空则获取等待房间的第一个元素
            T runnable = waitRoom.removeFirst();

            log.debug("获取等待房间任务成功,任务为{}",runnable);

            //唤醒正在等待的传送任务线程
            fullWaitRoom.signal();

            log.debug("{},进行对等待线程的唤醒......",runnable);

            //给予消费者线程任务
            return runnable;

        }finally {
            roomLock.unlock();
        }
    }


    /**
     * 当等待房间不为空时，给与消费者任务的方法，带超时保护
     * @param timeOut
     * @param timeUnit
     * @return T
     */
    private final T getRunnable_Time(long timeOut, TimeUnit timeUnit){

        log.debug("进入任务等待房间,此方法为防超时方法,开始给予任务.....");

        roomLock.lock();
        try{

            //获取等待的时间
            long time = timeUnit.toNanos(timeOut);

            //如果房间为空，则进入等待传送任务的线程传送任务
            while(waitRoom.isEmpty()){

                log.debug("警告!任务等待房间为空，没有任务.....");

                try {
                    //如果超过等待时间则返回null
                    if (time <= 0){
                        log.debug("警告!超时.....");
                        return null;
                    }

                    log.debug("进入等待.........");

                    //只等待time纳秒,超过则返回null
                    time = emptyWaitRoom.awaitNanos(time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            //如果房间不为空则获取等待房间的第一个元素
            T runnable = waitRoom.removeFirst();

            log.debug("任务已获取，此任务为{}",runnable);

            //唤醒正在等待的传送任务线程
            fullWaitRoom.signal();

            log.debug("{},对等待线程进行唤醒.....",runnable);

            //给予消费者线程任务
            return runnable;

        }finally {
            roomLock.unlock();
        }
    }


    /**
     * 当等待房间里的元素不为满时，传送任务的方法
     * @param runnable
     */
    private final void setRunnable(T runnable){

        log.debug("进入任务等待房间，此方法无防超时，开始传送任务");

        roomLock.lock();
        try{

            //如果等待房间为满时，则进入等待消费任务的线程消费任务
            while(waitRoom.size() == roomNumber){

                log.debug("警告，房间已满......");

                try {

                    log.debug("进入等待......");
                    fullWaitRoom.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

            //如果等待房间不满则往房间添加元素
            waitRoom.addLast(runnable);

            log.debug("添加任务完毕，任务为{}",runnable);

            //唤醒正在等待消费任务的线程
            emptyWaitRoom.signal();

            log.debug("{},唤醒等待的线程",runnable);
        }finally {
            roomLock.unlock();
        }
    }


    /**
     * 当等待房间里的元素不为满时，传送任务的方法,此方法提供了防超时机制
     * @param runnable
     * @param timeOut
     * @param timeUnit
     * @return boolean
     */
    private final boolean setRunnable_Time(T runnable, long timeOut, TimeUnit timeUnit){

        log.debug("进入任务等待房间，此方法设置了防超时，开始传送任务.....");

        roomLock.lock();
        try{
            //获取等待的时间
            long time = timeUnit.toNanos(timeOut);

            //如果等待房间为满时，则进入等待消费任务的线程消费任务
            while(waitRoom.size() == roomNumber){

                log.debug("警告!房间已满.....");

                try {
                    //如果超过等待的时间，则返回false
                    if(time <= 0){
                        log.debug("警告!超时......");
                        return false;
                    }

                    log.debug("进入等待，任务为{}",runnable);

                    //只等待time纳秒
                    time = fullWaitRoom.awaitNanos(time);
                } catch (InterruptedException e) {
                    e.printStackTrace();

                }

            }

            //如果等待房间不满则往房间添加元素
            waitRoom.addLast(runnable);

            log.debug("添加任务成功，任务为{}",runnable);

            //唤醒正在等待消费任务的线程
            emptyWaitRoom.signal();

            log.debug("{}，唤醒等待的线程...",runnable);

            return true;
        }finally {
            roomLock.unlock();
        }
    }


    /**
     * 对WaitRoom满载时的采用处理机制下放给Main
     * @param select
     * @param runnable
     */
    private final void tryPut(Reject_Select select, T runnable){

        roomLock.lock();
        try{

            if (waitRoom.size() == roomNumber){

                select.reject(this,runnable);
            }else {

                log.debug("{}任务，进入等待房间",runnable);

                placeRunnable(runnable);

                emptyWaitRoom.signal();
            }

        }finally {
            roomLock.unlock();
        }
    }

    public void try_Put(Reject_Select select, T runnable){
                    tryPut(select,runnable);
    }

    public T takeRunnable(){

        return getRunnable();
    }

    public void placeRunnable(T runnable){
        setRunnable(runnable);
    }

    public T takeRunnable_Time(long timeOut, TimeUnit timeUnit){
        return getRunnable_Time(timeOut, timeUnit);
    }

    public boolean placeRunnable_Time(T runnable, long timeOut, TimeUnit timeUnit){
       return setRunnable_Time(runnable, timeOut, timeUnit);
    }
}
