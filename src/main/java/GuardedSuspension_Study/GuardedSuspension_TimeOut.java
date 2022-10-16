package GuardedSuspension_Study;


import lombok.extern.slf4j.Slf4j;


/**
 * GuardedObject_TimeOut类的测试，测试证明，解决了采用GuardedObject类的courier线程因为某些原因导致结果迟迟没有反馈给
 * second_Dog线程，导致second_Dog陷入漫长甚至是永远的等待中这种不良现象
 *
 * 并且由于采用了(避免虚假唤醒而导致等待时间重置，造成实际等待时间大于设定等待时间)的设计，经过测试，确实解决了这一问题，详情请查看GuardedObject_TimeOut类
 */


@Slf4j
public class GuardedSuspension_TimeOut {


    public static void main(String[] args) {


        GuardedObject_TimeOut guardedObject_timeOut = new GuardedObject_TimeOut();                 //创建线程交互协作的中介


        String string = "您好先生，我是快递员，您的快递到了";                                             //模拟线程处理后的结果


        Thread second_Dog = new Thread(() -> {                                                     //模拟与courier线程互动


            log.debug("等待快递.......");


            String freight = (String) guardedObject_timeOut.getResponse(2000);             //获取courier线程处理后的结果


            if (freight == null){                                                                  //如果超过等待时间，木有得到courier线程的反馈。那就回去看动画片

                log.debug("不会吧!!!东西还没有来，我不等了，回屋看动画片....");

                return;
            }


            /**
             * 如果在等待时间内得到了courier线程的反馈则执行以下结果
             */


            log.debug("{}",freight);


            log.debug("好耶快递到了");

        },"二狗子");


//        new Thread(()->{
//
//               guardedObject_timeOut.setResponse(null);
//         },"虚假唤醒").start();


        Thread courier = new Thread(() -> {                               //模拟与second_Dog线程互动


            log.debug("快递打包.....");


            log.debug("快递分拣.....");


            log.debug("快递运送中.....");


            log.debug("啊~好困啊，疲劳驾驶不安全，我得去酒店睡觉");


            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


           guardedObject_timeOut.setResponse(string);                            //传递结果给second_Dog线程


       },"快递员");


        second_Dog.start();


        courier.start();
   }
}
