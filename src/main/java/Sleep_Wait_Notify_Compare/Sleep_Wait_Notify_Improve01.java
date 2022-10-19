package Sleep_Wait_Notify_Compare;

import lombok.extern.slf4j.Slf4j;

/**
 * 用wait()和notify()来实现吃了烤鸭腿才干活
 *
 * 这两个方法来实现解决了较于sleep()实现的效率低的问题，当二狗子发现没有烤鸭腿不干活时，可以到SECOND_ROOM的waitset那等待，顺便把owner让出来，
 * 把使用权让出来，把锁解开，唤醒SECOND_ROOM中的entryList中等待的线程，让他们竞争，争夺房间的使用权，通过房间去干活，而不用等到二狗子线程
 * 吃到烤鸭腿后去干活了，或者等它自己离开，其他人的线程才能有使用这个SECOND_ROOM.
 *
 * 这两个方法来实现解决了较于sleep()实现的共享资源安全性问题，可以光明正大的通过SECOND_ROOM门进去，而不用挖墙。
 */
@Slf4j
public class Sleep_Wait_Notify_Improve01 {

    //房子，将来安装对象锁锁住住，给其他线程用
    static final Object SECOND_ROOM = new Object();

    //烤鸭退，二狗子没有烤鸭腿不干活
    static boolean roasted_DuckLeg = false;


    public static void main(String[] args) {

        /**
         * 二狗子线程,没有烤鸭腿不干活
         */
        new Thread(() -> {

            //二狗子锁住SECOND_ROOM，没有烤鸭腿不干活，进入waitset休息室等待,放开锁
            synchronized(SECOND_ROOM){

                log.debug("哟哟哟，到我喽，有烤鸭腿吃吗？");

                log.debug("您好，现在的房间里的烤鸭腿为:{}",roasted_DuckLeg);

                //二狗子看看有没有烤鸭腿，没有就不干活
                if (roasted_DuckLeg != true ){

                    log.debug("什么？？？没烤鸭腿？？没烤鸭腿不干活，hetui...");

                    //二狗子等待烤鸭腿送到SECOND_ROOM
                    try {
                        SECOND_ROOM.wait();
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

                synchronized (SECOND_ROOM) {

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
         * 外卖员送烤鸭腿到SECOND_ROOM
         */
        new Thread(() -> {

            synchronized (SECOND_ROOM) {

                roasted_DuckLeg = true;

                log.debug("烤鸭腿来喽.....");

                SECOND_ROOM.notify();

            }

        },"外卖员").start();

    }

}
