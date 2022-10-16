package GuardedSuspension_Study;


import lombok.extern.slf4j.Slf4j;


/**
 *由于GuardedObject的wait()采用的是无参方法，假如程序运行时由于某些特殊原因导致courier线程迟迟没有返回结果与second_Dog线程互动
 * 那么second_Dog线程就会一直等下去，这样不利于程序的运行
 *
 * 所以本次采用限制超时机制的设计思想对GuardedObject中的getResponse()方法进行改进,避免以上不良现象
 */


@Slf4j
public class GuardedObject_TimeOut {


    private static Object response;                                        //接收线程所传递来的结果


    public Object getResponse(long timeOut) {                              //获取其他线程所传递来的结果,并且获取等待时间，超过则不等待


        if (timeOut <= 0){

            log.debug("等待的时间必须大于0");
        }


        synchronized (this) {                                              //对共享资源response进行保护


            long begin = System.currentTimeMillis();                       //记录getResponse()方法被调用的开始时间


            long passTime = 0;


            while (response == null) {                                     //判断结果是否传递到达，不是则一直等待


                /**
                 * 为了避免采用if（timeOut）设计后被虚假唤醒再此进入重置等待时间，造成实际等待时间大于设定等待时间
                 * 所以采用waitTime变量记录还需等待多少时间
                 */


                long waitTime = timeOut - passTime;


                if(waitTime<=0){

                    break;
                }


                try {

                    this.wait(waitTime);

                } catch (InterruptedException e) {

                    e.printStackTrace();

                }


                passTime = System.currentTimeMillis() - begin;        //记录使用getResponse()方法被调用后的持续运行时间


            }


            return response;                                     //是则返回结果


        }


    }



    public void setResponse(Object response){                   //用于传递线程结果


        synchronized (this){                                    //对共享资源进行保护

            this.response = response;                           //传递线程结果

            this.notify();                                      //唤醒线程继续运行
        }
    }
}
