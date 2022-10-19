package Sleep_Wait_Notify_Compare;

import lombok.extern.slf4j.Slf4j;

/**
 * 较于Sleep_Wait_Notify02,Sleep_Wait_Notify_Improve02使用notifyAll()方法进行改进，同时叫醒所有在waitset中等待的人
 * 解决了二狗子一直苦等的问题（虚假唤醒），但是没有解决大猫咪吃醋的样子（看到烤鸭腿来了，自己的烤鸡腿没来罢工不干了），这样做不是很礼貌，
 * 对多线程交互安全问题有很大影响,不能真正执行到正确的逻辑
 */
@Slf4j
public class Sleep_Wait_Notify_Improve02 {

    //房子，将来安装对象锁锁住住，给其他线程用
    static final Object FOURTH_ROOM = new Object();

    //烤鸭退，二狗子没有烤鸭腿不干活
    static boolean roasted_DuckLeg = false;

    //烤鸭退，大猫咪没有烤鸭腿不干活
    static boolean roasted_ChickenLeg = false;


    public static void main(String[] args) {

        /**
         * 大猫咪线程,没有烤鸡腿不干活
         */
        new Thread(() -> {

            //大猫咪锁住FOURTH_ROOM，没有烤鸡腿不干活，进入waitset休息室等待,放开锁
            synchronized(FOURTH_ROOM){

                log.debug("哟哟哟，到我喽，有烤鸡腿吃吗？");

                log.debug("您好，现在的房间里的烤鸡腿为:{}",roasted_ChickenLeg);

                //大猫咪看看有没有烤鸡腿，没有就不干活
                if (roasted_ChickenLeg != true ){

                    log.debug("什么？？？没烤鸡腿？？没烤鸡腿不干活，哼...");

                    //大猫咪等待烤鸡腿送到FOURTH_ROOM
                    try {
                        FOURTH_ROOM.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

                log.debug("有烤鸡腿没??");

                //外卖员烤鸡腿送到吃了就干活，随后开锁
                if (roasted_ChickenLeg == true){

                    log.debug("您好，现在房间里的烤鸡腿为:{}",roasted_ChickenLeg);

                    log.debug("WOWOWO!!有烤鸡腿吃，干活喽....");

                    return;

                }

                //那么久还没送到，不干了，罢工，走
                log.debug("烤鸡腿还没到....我不干了");

            }

        },"大猫咪").start();

        /**
         * 二狗子线程,没有烤鸭腿不干活
         */
        new Thread(() -> {

            //二狗子锁住FOURTH_ROOM，没有烤鸭腿不干活，进入waitset休息室等待,放开锁
            synchronized(FOURTH_ROOM){

                log.debug("哟哟哟，到我喽，有烤鸭腿吃吗？");

                log.debug("您好，现在的房间里的烤鸭腿为:{}",roasted_DuckLeg);

                //二狗子看看有没有烤鸭腿，没有就不干活
                if (roasted_DuckLeg != true ){

                    log.debug("什么？？？没烤鸭腿？？没烤鸭腿不干活，hetui...");

                    //二狗子等待烤鸭腿送到FOURTH_ROOM
                    try {
                        FOURTH_ROOM.wait();
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
                log.debug("鸭腿还没到....我不干了");

            }

        },"二狗子").start();

        //创建十个其他干活的人
        for (int i = 0; i < 10; i++) {

            /**
             * 其他干活的人
             */
            new Thread(() -> {

                synchronized (FOURTH_ROOM) {

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
         * 外卖员送烤鸭腿到FOURTH_ROOM
         */
        new Thread(() -> {

            synchronized (FOURTH_ROOM) {

                roasted_DuckLeg = true;

                log.debug("烤鸭腿来喽.....");

                FOURTH_ROOM.notifyAll();

            }

        },"外卖员").start();

    }

}
