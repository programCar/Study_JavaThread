package GuardedSuspension_Study.DecouplingWait_Production;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * 创建两个线程交互的保护性暂停（内设超时机制）类
 * 为ReceiveMail(模拟收信)线程类和SendMail(模拟送信)线程类的交互所构建的MailBoxes类服务
 * 此类使用了Synchronized()方法实现了两个线程之间交互对接的安全性。
 * 此类使用ID变量作为标记，保证多线程同时使用时，SendMail线程类实例与ReceiveMail线程类之间交互的准确唯一性
 * 此类设计了防止虚假唤醒产生错误结果的机制，解决了被虚假唤醒产生错误结果的问题.
 *
 */
@Slf4j
public class GuardedObject_TimeOut {

    //用于确保SendMail线程类实例与ReceiveMail线程类之间交互的准确唯一性
    private int ID;

    //录入Id信息
    public void setID(int ID){

        this.ID = ID;
    }

    //返回本类ID
    public int getID(){                                         //传递Id信息
        return ID;
    }

    //用于接收线程交互所产生的结果
    private static Object response;


    /**
     * 用于返回线程交互结果的方法
     * @param timeOut
     * @return
     */
    public Object getResponse(long timeOut){

        //先判断等待是否大于等于0，增加程序的健壮性
        if (timeOut <= 0 ){

            log.debug("等待时间不可以小于等于零");

            return -1;

        }

        //锁住本类作为线程之间交互的房间
        synchronized(this){

            //记录调用本方法的开始时间
            long beginTime = System.currentTimeMillis();

            //记录调用本方法后等待了多长时间
            long passTime = 0;

            //防止虚假唤醒产生错误结果机制，看看叫的那个是不是自己
            while (response == null){

                //记录调用本方法后还需等待多长时间
                long waitTime = timeOut - passTime;

                //如果还需等待的时间等于小于0，说明不必等待，结束循环
                if (waitTime <= 0){
                    break;
                }

                //等待机制
                try {
                    this.wait(waitTime);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }

                //计算调用本方法后等待了多少时间
                passTime = System.currentTimeMillis() - beginTime;
            }

            //返回线程交互的结果
            return response;


        }


    }


    /**
     * 传送此线程产生的结果，用于线程交互
     * @param response
     */
    public void setResponse(Object response){

        //进入本对象锁房间
        synchronized (this){

            //传送调用本方法的线程产生的结果
            this.response = response;

            //唤醒所以在waitSet等待的线程，谁的谁拿
            this.notifyAll();

        }

    }

}
