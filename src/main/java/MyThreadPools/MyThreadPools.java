package MyThreadPools;

import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.concurrent.TimeUnit;

@Slf4j
public class MyThreadPools {

    //线程池的大小
    private int poolNumber;

    //线程池
    private HashSet<Worker> threadPools = new HashSet<>();

    //任务等待房间
    private WaitRoom<Runnable> blockingRoom;

    //超时时间
    private long timeOut;

    //超时时间单位
    private TimeUnit timeUnit;

    private Reject_Select<Runnable> select;

    public MyThreadPools(int poolNumber, int blockingRoomNumber, long timeOut, TimeUnit timeUnit,Reject_Select<Runnable> select){

        this.poolNumber = poolNumber;

        this.blockingRoom = new WaitRoom<Runnable>(blockingRoomNumber);

        this.timeOut = timeOut;

        this.timeUnit = timeUnit;

        this.select = select;

    }


    /**
     * 线程池工作的方法
     * @param runnable
     */
    private final void execute(Runnable runnable){

        synchronized (threadPools){

            //如果线程池的现大小小于预定大小，则生成一个线程给线程池并执行操作
            if(threadPools.size() < poolNumber){

                //创建任务线程
                Worker worker = new Worker(runnable);

                log.debug("任务{},已创建任务线程",runnable);

                //往线程池中添加任务线程
                threadPools.add(worker);

                log.debug("已将将任务{}线程添加进线程池",runnable);

                log.debug("任务开启....");

                //开启线程工作，消费任务
                worker.start();
            }else {
                //如果线程池大小已达到预定大小，则将多余的任务存进任务等待房间，为后续线程池线程提取任务做准备
                //blockingRoom.placeRunnable(runnable);
                //调用应对WaitRoom满载时，措施机制下放的方法，把选择丢给Main线程
                    blockingRoom.try_Put(select,runnable);
            }

        }

    }


    /**
     * 内部类，用于模拟线程池中线程工作的类
     */
    class Worker extends Thread{

        //创建一个任务变量来储存生产线程给予的任务
        private Runnable runnable;

        //初始化任务变量
        public Worker(Runnable runnable){
            this.runnable = runnable;
        }

        /**
         * 线程池中线程工作的方法
         */
        @Override
        public void run() {

            //如果此任务存在或者任务等待房间里有任务，则进行操作
           while(runnable != null || (runnable = blockingRoom.takeRunnable() )!= null){

               try{
                   log.debug("正在执行{}任务",runnable);
                   runnable.run();
               }catch (RuntimeException e){
                   e.printStackTrace();
               }finally {
                   runnable = null;
               }
           }

           //如果没有任务，则将线程池中的此线程移除，释放空间
           synchronized (threadPools) {
               threadPools.remove(this);

               log.debug("任务已被移除{}",this);
           }
        }


    }


    public void goExecute(Runnable runnable){
        execute(runnable);
    }


}
