package Sleep_Wait_Notify_Compare;


import lombok.extern.slf4j.Slf4j;


/**
 * 较于Sleep_Wait_Notify_Improve01,Sleep_Wait_Notify02增加了大猫咪线程
 * 大猫咪和二狗子一个性格，只不过大猫咪是没有烤鸡腿它就不干活，要等烤鸡腿才干活。
 * 但THIRD_ROOM的房东并没有点烤鸡腿，只准备点了烤鸭腿
 * 当烤鸭腿做好后，送到THIRD_ROOM，用notify()唤醒，谁知道叫错了，没有叫到二狗子，而是叫到了大猫咪
 * 结果大猫咪看到是烤鸡腿没到，烤鸡腿没到，生气的罢工不干了，只留下二狗子线程一直在苦等,造成了虚假唤醒
 * notify()的方法:随机叫醒正在Monitor的waitset中等待的某个线程
 */


@Slf4j
public class Sleep_Wait_Notify02 {


    static final Object THIRD_ROOM = new Object();                    //房子，将来安装对象锁锁住住，给其他线程用


    static boolean roasted_DuckLeg = false;                           //烤鸭退，二狗子没有烤鸭腿不干活


    static boolean roasted_ChickenLeg = false;                        //烤鸭退，大猫咪没有烤鸭腿不干活


    public static void main(String[] args) {


        new Thread(() -> {                                            //大猫咪线程,没有烤鸡腿不干活


            synchronized(THIRD_ROOM){                                 //大猫咪锁住THIRD_ROOM，没有烤鸡腿不干活，进入waitset休息室等待,放开锁


                log.debug("哟哟哟，到我喽，有烤鸡腿吃吗？");


                log.debug("您好，现在的房间里的烤鸡腿为:{}",roasted_ChickenLeg);


                if (roasted_ChickenLeg != true ){                     //大猫咪看看有没有烤鸡腿，没有就不干活

                    log.debug("什么？？？没烤鸡腿？？没烤鸡腿不干活，哼...");

                    try {                                             //大猫咪等待烤鸡腿送到THIRD_ROOM

                        THIRD_ROOM.wait();

                    } catch (InterruptedException e) {

                        e.printStackTrace();

                    }

                }


                log.debug("有烤鸡腿没??");


                if (roasted_ChickenLeg == true){                      //外卖员烤鸡腿送到吃了就干活，随后开锁

                    log.debug("您好，现在房间里的烤鸡腿为:{}",roasted_ChickenLeg);

                    log.debug("WOWOWO!!有烤鸡腿吃，干活喽....");

                    return;

                }


                log.debug("烤鸡腿还没到....我不干了");                    //那么久还没送到，不干了，罢工，走


            }


        },"大猫咪").start();


        new Thread(() -> {                                             //二狗子线程,没有烤鸭腿不干活


            synchronized(THIRD_ROOM){                                 //二狗子锁住THIRD_ROOM，没有烤鸭腿不干活，进入waitset休息室等待,放开锁


                log.debug("哟哟哟，到我喽，有烤鸭腿吃吗？");


                log.debug("您好，现在的房间里的烤鸭腿为:{}",roasted_DuckLeg);


                if (roasted_DuckLeg != true ){                         //二狗子看看有没有烤鸭腿，没有就不干活

                    log.debug("什么？？？没烤鸭腿？？没烤鸭腿不干活，hetui...");

                    try {                                              //二狗子等待烤鸭腿送到THIRD_ROOM

                        THIRD_ROOM.wait();

                    } catch (InterruptedException e) {

                        e.printStackTrace();

                    }

                }


                log.debug("有烤鸭腿没??");


                if (roasted_DuckLeg == true){                         //外卖员烤鸭腿送到吃了就干活，随后开锁

                    log.debug("您好，现在房间里的烤鸭腿为:{}",roasted_DuckLeg);

                    log.debug("WOWOWO!!有烤鸭腿吃，干活喽....");

                    return;

                }


                log.debug("鸭腿还没到....我不干了");                     //那么久还没送到，不干了，罢工，走


            }


        },"二狗子").start();


        for (int i = 0; i < 10; i++) {                               //其他干活的人

            new Thread(() -> {


                synchronized (THIRD_ROOM) {


                    log.debug("哟哟哟，到我喽，干活干活");


                }


            }, "其他人").start();

        }


        try {                                                       //正在做烤鸭腿

            Thread.sleep(1000);

        } catch (InterruptedException e) {

            e.printStackTrace();

        }


        new Thread(() -> {                                         //外卖员送烤鸭腿到ROOM


            synchronized (THIRD_ROOM) {


                roasted_DuckLeg = true;


                log.debug("烤鸭腿来喽.....");


                THIRD_ROOM.notify();


            }


        },"外卖员").start();


    }


}

