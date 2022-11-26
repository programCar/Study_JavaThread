package My_ConnectionPool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicIntegerArray;

@Slf4j
public class ConnectionPools {

    //连接池子的大小
    private final int poolSize;

    //连接池
    private final MyConnection[] Pools;

    //数据库状态
    private AtomicIntegerArray states;


    /**
     * 初始化连接池子的大小，创建池子，初始化池子的状态，并且给池子赋值相应的元素,
     * @param poolSize
     */
    public ConnectionPools(int poolSize){

        //定义池子的大小
        this.poolSize = poolSize;

        //生成连接池子
        Pools = new MyConnection[poolSize];

        //定义连接池中所储存元素的状态
        states = new AtomicIntegerArray(poolSize);

        //为连接池子里赋予相应的元素
        for (int i = 0; i < poolSize; i++){

            Pools[i] = new MyConnection(i+"");
        }
    }


    /**
     * 实现连接池元素的获取
     * @return
     */
    private final MyConnection borrowConnection(){

        while (true){

            log.debug("gogogo.....");
            //返回连接池中不繁忙的元素
            for (int i = 0; i < poolSize; i++){

                if (states.get(i) == 0){

                    //将获取到的元素置为繁忙状态
                    if (states.compareAndSet(i,0,1)) {

                        log.debug("pool{}",Pools[i].toString());
                        //返回已获取的元素
                        return Pools[i];
                    }
                }
            }

            //如果元素全都是繁忙状态，则进行休息，等待元素释放
            synchronized (this){

                try {
                    log.debug("wait.......");
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }

    }


    /**
     * 释放已获取的元素
     * @param connection
     */
    private final void freeConnection(MyConnection connection){

        //查找连接池中自己所处的为位置
        for (int i = 0; i < poolSize; i++){

            if (Pools[i] == connection){

                log.debug("states{}",connection.toString());
                //将状态改为0，表示空闲，释放元素
                states.set(i,0);

                //唤醒等待的线程
                synchronized (this){
                    log.debug("notify......");
                    this.notifyAll();
                }

                break;
            }
        }

    }

    public MyConnection getBorrowConnection(){
        return borrowConnection();
    }

    public void setFreeConnection(MyConnection connection){

        freeConnection(connection);
    }

}
