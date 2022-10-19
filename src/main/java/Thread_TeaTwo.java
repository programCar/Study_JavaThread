import lombok.extern.slf4j.Slf4j;

/**
 * 使用Slf4j注解来调用其中的log.debug（）方法来打印日志
 * 创建模拟甲乙丙都是串行行为的类，按队列方式开始制作茶水活动
 */

@Slf4j
class Thread_TeaTwo {

    //创建类全局线程变量，用于存放甲同学洗茶壶，加水的线程
    static Thread thread_Jia_kettle_AddWater;

    //创建类全局线程变量，用于存放甲同学烧水的线程
    static Thread thread_Jia_Tea;

    //创建类全局线程变量，用于存放甲同学洗茶壶茶杯，拿茶叶的线程
    static Thread thread_Jia_Water;

    //用来储存各位同学分别制作茶水的开始时间
    static long time_Start;

    //用来储存甲同学制作茶水结束的时间
    static long time_Jia_End;

    //用来储存乙同学制作茶水结束的时间
    static long time_Yi_End;

    //用来储存丙同学制作茶水结束的时间
    static long time_Bing_End;


    /**
     * 创建主线程，用来模拟甲同学泡茶行为和结果，实现甲同学四个线程间的逻辑关系
     * 实现甲乙丙三人串行的逻辑关系，开启甲（洗水壶，加水）线程，在主线程内按队列方式进行甲乙丙三人的行为
     * @param args
     */
    public static void main(String[] args) {

        /**
         * 调用方法，创建甲同学（洗水壶，放水），（烧水），（洗茶壶茶杯拿茶叶）的三个线程，实现这三个线程间的逻辑关系
         */
        jia_Thread();

        //记录甲同学制作茶水的开始时间
        time_Start = System.currentTimeMillis();

        //启动线程，让CPU给予时间片，获取被任务调度器调度的资格
        thread_Jia_kettle_AddWater.start();

        //等待甲同学的三个泡茶准备工作（三个线程）结束后可以泡茶喝
        try {
            thread_Jia_kettle_AddWater.join();
            thread_Jia_Tea.join();
            thread_Jia_Water.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.debug("甲同学的泡茶准备工作已完成，可以泡茶喝.....");

        //泡茶
        Make_Work.make_Tea();

        //甲同学的制作茶水工作模拟已完毕
        log.debug("甲同学表示自己泡的茶很好喝,赞......");

        time_Jia_End = System.currentTimeMillis();

        log.debug("甲同学制作茶水一共耗时为 :"+((time_Jia_End-time_Start)/1000)+"秒钟");

        //调用方法，实现乙同学制作茶水全过程
        yi_Make_Tea();

        //调用方法，实现丙同学制作茶水全过程
        bing_Make_Tea();

    }


    /**
     * 此方法用于创建囊括甲同学的（洗水壶，放水）,（烧水），（洗茶壶茶杯拿茶叶）的三个线程，并且实现了这三个线程间的逻辑关系
     */
    public static void jia_Thread(){

        /**
         * 创建线程，模拟甲同学的（洗水壶，放水）过程,用jdk8新语法特性（）->{}lambda简化代码
         */
        thread_Jia_kettle_AddWater = new Thread(()->{

            log.debug("甲同学正在刷洗水壶中....");

            //洗水壶
            Make_Work.clean_Kettle();

            log.debug("甲同学水壶已洗干净，准备往水壶里加水...");

            log.debug("甲同学正在努力加水中........");

            //往水壶里加水
            Make_Work.add_Water();

            log.debug("甲同学已把水加入干净的水壶，准备把水壶放在猛火里烧水......");

            log.debug("甲同学已把洗干净的水壶放在猛火上烧开水....");

            /**
             * 创建线程模拟甲同学的洗茶壶，洗茶杯，拿茶叶的过程
             */
            thread_Jia_Tea = new Thread(()->{

                log.debug("甲同学正在努力刷洗茶壶中.....");

                //洗茶壶
                Make_Work.clean_Teapot();

                log.debug("甲同学茶壶已洗干净,准备洗茶杯.......");

                log.debug("甲同学正在努力刷洗茶杯中........");

                //洗茶杯
                Make_Work.clean_Teacup();

                log.debug("甲同学茶杯已洗干净，准备去拿茶叶....");

                log.debug("甲同学正在拿茶叶.....");

                //拿茶叶
                Make_Work.take_Tea();

                log.debug("甲同学茶叶已拿到，等待开水烧开泡茶喝.......");

            },"甲同学，洗茶壶茶杯，拿茶叶");

            /**
             * 创建线程模拟甲同学的（烧开水）过程
             */
            thread_Jia_Water = new Thread(()->{

                //烧水
                Make_Work.heat_Up_Water();

                log.debug("甲同学的水壶水已烧开，准备泡茶....");

            },"甲同学，烧水");

            //启动线程，让CPU给予时间片，获取被任务调度器调度的资格
            thread_Jia_Tea.start();

            //启动线程，让CPU给予时间片，获取被任务调度器调度的资格
            thread_Jia_Water.start();

        },"甲同学，洗水壶，加水");

    }


    /**
     * 此方法用来创建囊括乙同学全部行为
     */
    public static void yi_Make_Tea(){

        //记录乙同学制作茶水的开始时间
        time_Start = System.currentTimeMillis();

        log.debug("乙同学正在努力刷洗水壶中.......");

        //洗水壶
        Make_Work.clean_Kettle();

        log.debug("乙同学水壶已洗干净，准备洗茶壶.......");

        log.debug("乙同学正在努力刷洗茶壶中........");

        //洗茶壶
        Make_Work.clean_Teapot();

        log.debug("乙同学的茶壶已洗干净，准备洗茶杯.....");

        log.debug("乙同学正在努力刷洗茶杯中........");

        //洗茶杯
        Make_Work.clean_Teacup();

        log.debug("乙同学的茶杯已洗干净，准备拿茶叶.....");

        log.debug("乙同学正在拿茶叶........");

        //拿茶叶
        Make_Work.take_Tea();

        log.debug("乙同学茶叶已拿到，准备往水壶里加水....");

        log.debug("乙同学正在努力加水中........");

        //往水壶里加水
        Make_Work.add_Water();

        log.debug("乙同学已把干净的水灌入水壶中，准备放在猛火上烧开水......");

        //烧开水
        Make_Work.heat_Up_Water();

        log.debug("乙同学的水已烧开，准备泡茶喝....");

        log.debug("乙同学的泡茶准备工作已完成，可以泡茶喝.....");

        //泡茶
        Make_Work.make_Tea();

        //乙同学制作茶水工作模拟已完毕
        log.debug("乙同学表示自己泡的茶还不错,赞......");

        time_Yi_End = System.currentTimeMillis();

        log.debug("乙同学制作茶水一共耗时为 :"+((time_Yi_End-time_Start)/1000)+"秒钟");

    }


    /**
     * 此方法用来创建囊括丙同学全部行为的线程
     */
    public static void bing_Make_Tea(){

        //记录丙同学制作茶水的开始时间
        time_Start = System.currentTimeMillis();

        log.debug("丙同学正在努力刷洗水壶中.......");

        //洗水壶
        Make_Work.clean_Kettle();

        log.debug("丙同学的水壶已洗干净，准备往水壶里加水....");

        log.debug("丙同学正在努力加水中........");

        //往水壶里加水
        Make_Work.add_Water();

        log.debug("丙同学已把干净的水灌入水壶中，准备放在猛火上烧开水....");

        //烧开水
        Make_Work.heat_Up_Water();

        log.debug("丙同学的水已烧开，完蛋！！忘记其他准备工作了，得赶紧去做...");

        log.debug("丙同学正在急急忙忙的找茶叶........");

        //找茶叶
        Make_Work.take_Tea();

        log.debug("好险茶叶放的够显眼，丙同学茶叶已找到，准备洗茶壶.....");

        log.debug("丙同学正在努力刷洗茶壶中........");

        //洗茶壶
        Make_Work.clean_Teapot();

        log.debug("丙同学茶壶已洗干净，准备洗茶杯.....");

        log.debug("丙同学正在努力刷洗茶杯中......");

        //洗茶杯
        Make_Work.clean_Teacup();

        log.debug("丙同学茶杯已洗干净，准备泡茶喝.....");

        log.debug("丙同学的泡茶准备工作已完成，可以泡茶喝.....");

        //泡茶
        Make_Work.make_Tea();

        //丙同学的制作茶水工作模拟已完毕
        log.debug("丙同学表示自己泡的茶很很狠很好喝,赞赞赞......");

        time_Bing_End = System.currentTimeMillis();

        log.debug("丙同学制作茶水一共耗时为 :"+((time_Bing_End-time_Start)/1000)+"秒钟");

    }

}
