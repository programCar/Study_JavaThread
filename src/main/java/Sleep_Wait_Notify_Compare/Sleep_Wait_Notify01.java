package Sleep_Wait_Notify_Compare;

import lombok.extern.slf4j.Slf4j;

/**
 * 用sleep()实现吃了烤鸭腿才干活的场景。
 *
 * 用sleep()实现，效率很低，因为当二狗子线程拿到ROOM的使用权后就把ROOM锁住了，但发现ROOM里面没有烤鸭腿吃，他就不去干活除非它自己走了
 * 其他人的线程到ROOM前面发现门被锁，他们就进不去，不能经过房间去干活，只能在ROOM的entryList中等，等外卖员线程把烤鸭腿送到ROOM，
 * 二狗子吃了烤鸭腿才去干活，ROOM的锁才会被解开，其他人的线程才能通过房间去干活。
 *
 * 由于使用sleep()实现，外卖员就不能通过ROOM的门进去，只能挖墙进去，这样对共享资源ROOM的安全没有保障，存在很大的隐患
 */
@Slf4j
public class Sleep_Wait_Notify01 {

    //房子，将来安装对象锁锁住住，给其他线程用
    static final Object ROOM = new Object();

    //烤鸭退，二狗子没有烤鸭腿不干活
    static boolean roasted_DuckLeg = false;


    public static void main(String[] args) {

        /**
         * 二狗子线程,没有烤鸭腿不干活
         */
         new Thread(() -> {

             //二狗子锁住ROOM，没有烤鸭腿不干活，不开门,除非它走了
            synchronized(ROOM){

                log.debug("哟哟哟，到我喽，有烤鸭腿吃吗？");

                log.debug("您好，现在的房间里的烤鸭腿为:{}",roasted_DuckLeg);

                //二狗子看看有没有烤鸭腿，没有就不干活
                if (roasted_DuckLeg != true ){

                    log.debug("什么？？？没烤鸭腿？？没烤鸭腿不干活，hetui...");

                    //二狗子等待烤鸭腿送到ROOM
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

                log.debug("有烤鸭腿没??");

                //外卖员烤鸭腿送到吃了就干活，随后开锁
                if (roasted_DuckLeg == true){

                    log.debug("您好，现在房间里的烤鸭腿为:{}",roasted_DuckLeg);

                    log.debug("WOWOWO!!有烤鸭腿吃，干活喽....");

                    return;

                }

                //那么久还没送到，不干了，罢工，走
                log.debug("烤鸭腿还没到....我不干了");

            }

        },"二狗子").start();

        //创建十个其他干活的人
        for (int i = 0; i < 10; i++) {

            /**
             * 其他干活的人
             */
             new Thread(() -> {

                synchronized (ROOM) {

                    log.debug("哟哟哟，到我喽，干活干活");

                }

            }, "其他人").start();

        }

        //正在做烤鸭腿
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /**
         * 外卖员送烤鸭腿到ROOM
         */
         new Thread(() -> {

               roasted_DuckLeg = true;

               log.debug("烤鸭腿来喽.....");

            },"外卖员").start();

    }

}
