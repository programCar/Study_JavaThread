package Sleep_Wait_Notify_Compare;


import lombok.extern.slf4j.Slf4j;


/**
 * 较于Sleep_Wait_Notify_Improve02,Sleep_Wait_Notify_ImproveEnd采用while（）代替if（）让线程多几次判断
 * 解决了多线程交互间因为notifyAll（）同时唤醒所有在waitset等待的线程造成线程不能接收正确的数据，形成错误的结果
 * 让二狗子和大猫咪耐心多点，不能因为一次不行就罢工不干。
 */


@Slf4j
public class Sleep_Wait_Notify_ImproveEnd {


    static final Object FIFTH_ROOM = new Object();                       //房子，将来安装对象锁锁住住，给其他线程用


    static boolean roasted_DuckLeg = false;                              //烤鸭退，二狗子没有烤鸭腿不干活


    static boolean roasted_ChickenLeg = false;                           //烤鸭退，大猫咪没有烤鸭腿不干活


    public static void main(String[] args) {


        new Thread(() -> {                                               //大猫咪线程,没有烤鸡腿不干活


            synchronized(FIFTH_ROOM){                                    //大猫咪锁住FIFTH_ROOM，没有烤鸡腿不干活,进入waitset休息室等待,放开锁


                log.debug("哟哟哟，到我喽，有烤鸡腿吃吗？");


                log.debug("您好，现在的房间里的烤鸡腿为:{}",roasted_ChickenLeg);


                log.debug("什么？？？没烤鸡腿？？没烤鸡腿不干活，哼...");


                while (roasted_ChickenLeg != true ){                     //大猫咪看看有没有烤鸡腿，没有就不干活，继续等待


                    try {                                                //大猫咪等待烤鸡腿送到FIFTH_ROOM

                        FIFTH_ROOM.wait();

                    } catch (InterruptedException e) {

                        e.printStackTrace();

                    }


                    if (roasted_ChickenLeg == true){                      //外卖员烤鸡腿送到吃了就干活

                        log.debug("您好，现在房间里的烤鸡腿为:{}",roasted_ChickenLeg);

                        log.debug("WOWOWO!!有烤鸡腿吃，干活喽....");

                        return;

                    }


                    log.debug("有烤鸡腿没??");


                    log.debug("您好，现在房间里的烤鸡腿为:{}",roasted_ChickenLeg);


                    log.debug("唉，那我继续等喽.........");


                }


                log.debug("WOWOWO!!有烤鸡腿吃，干活喽....");                //如果一开始就有，那么直接吃完干活，不等


            }


        },"大猫咪").start();


        new Thread(() -> {                                             //二狗子线程,没有烤鸭腿不干活


            synchronized(FIFTH_ROOM){                                  //二狗子锁住FIFTH_ROOM，没有烤鸭腿不干活，进入waitset休息室等待,放开锁


                log.debug("哟哟哟，到我喽，有烤鸭腿吃吗？");


                log.debug("您好，现在的房间里的烤鸭腿为:{}",roasted_DuckLeg);


                log.debug("什么？？？没烤鸭腿？？没烤鸭腿不干活，hetui...");


                while (roasted_DuckLeg != true ){                       //二狗子看看有没有烤鸭腿，没有就不干活


                    try {                                               //二狗子等待烤鸭腿送到FIFTH_ROOM

                        FIFTH_ROOM.wait();

                    } catch (InterruptedException e) {

                        e.printStackTrace();

                    }

                    if (roasted_DuckLeg == true){                         //外卖员烤鸭腿送到吃了就干活，随后开锁

                        log.debug("您好，现在房间里的烤鸭腿为:{}",roasted_DuckLeg);

                        log.debug("WOWOWO!!有烤鸭腿吃，干活喽....");

                        return;

                    }

                    log.debug("有烤鸭腿没??");

                    log.debug("您好，现在的房间里的烤鸭腿为:{}",roasted_DuckLeg);

                    log.debug("唉，那我继续等喽.........");



                }


                log.debug("WOWOWO!!有烤鸭腿吃，干活喽....");             //如果一开始就有，那么直接吃完干活，不等


            }


        },"二狗子").start();


        for (int i = 0; i < 10; i++) {                               //其他干活的人

            new Thread(() -> {


                synchronized (FIFTH_ROOM) {


                    log.debug("哟哟哟，到我喽，干活干活");


                }


            }, "其他人").start();

        }


        try {                                                       //正在做烤鸭腿

            Thread.sleep(1000);

        } catch (InterruptedException e) {

            e.printStackTrace();

        }


        new Thread(() -> {                                         //外卖员送烤鸭腿到FIFTH_ROOM


            synchronized (FIFTH_ROOM) {


                roasted_DuckLeg = true;


                log.debug("烤鸭腿来喽.....");


               FIFTH_ROOM.notifyAll();


            }


        },"外卖员").start();


    }


}
