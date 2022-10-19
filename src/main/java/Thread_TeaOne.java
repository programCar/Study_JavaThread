import lombok.extern.slf4j.Slf4j;

/**
 *              阅读华罗庚《统筹方法》，给出烧水泡茶的多线程解决方案
 * （一）具备条件：开水没有，水壶要洗，茶壶，茶杯要洗，火已生成，茶叶有了。
 * （二）情景模拟：
 *  甲：洗好水壶，灌上凉水，放在火上；等待水开的时间里，洗茶壶，洗茶杯，拿茶叶；等水开后，泡茶喝
 *  乙：先做好准备工作，洗水壶，洗茶壶茶杯，拿茶叶，一切准备就绪后，灌水烧水；等待水开后，泡茶喝
 *  丙：洗净水壶，灌上凉水，放在火上，等待水开，水开后，急急忙忙找茶叶，洗茶壶茶杯，泡茶喝。
 * （三）情景实现分析：
 *  甲的情景既有并行又有串行，其中（洗水壶和灌入凉水）的行为与（烧水），（洗茶壶，洗茶杯，拿茶叶，泡茶）这些行为是串行关系
 *  因为必须等洗好水壶，然后把水加入水壶，才能进行（烧水），（洗茶壶，洗茶杯，拿茶叶，泡茶）这些行为。
 *  而（烧水）行为与（洗茶壶，洗茶杯，拿茶叶）行为又是并行关系
 *  因为烧水的时间可以干（洗茶壶，洗茶杯，拿茶叶）这些行为
 *  但（泡茶）与（洗水壶和灌入凉水），（烧水），（洗茶壶，洗茶杯，拿茶叶，泡茶）又是串行关系
 *  因为只有这些做完了才可以进行泡茶。
 *  乙的情景的所有行为都是串行行为
 *  丙的情景的所以行为都是串行行为
 *  设置sleep（）方法来模拟各个行为的消耗时间。
 *  设置join（）方法来实现线程之间的等待行为。
 *  方案（1）：甲和乙丙三个人由于是同时进行行为的，所以甲和乙和丙三者之间是并行关系
 *  方案（2）：甲和乙丙三人是串行的，必须等前面一个人做完了全部行为，后面一个人再继续。
 *  方案（3）：同方案（2）逻辑一样，布局不一样
 *  方案（4）：甲和乙丙三人，其中两个人是并行的，另外一个人必须等这两个人结束后才能进行行为。
 * （四）情景实现思想
 *       甲乙丙的个体实现思想：
 *      （甲）可以创建四个线程线程，其中主线程用来处理泡茶行为或者最终结果
 *      其他三个线程分别用来处理（洗水壶和灌入凉水），（烧水），（洗茶壶，洗茶杯，拿茶叶）这些行为
 *      其中（洗水壶和灌入凉水）线程内嵌（烧水），（洗茶壶，洗茶杯，拿茶叶，泡茶）两个线程，必须等
 *      （洗水壶和灌入凉水）行为完成后才能开启（烧水），（洗茶壶，洗茶杯，拿茶叶，泡茶）两个线程
 *      这样（洗水壶和灌入凉水）线程与（烧水），（洗茶壶，洗茶杯，拿茶叶，泡茶）就形成了串行逻辑状态，
 *      随后在（洗水壶和灌入凉水）线程内同时开启（烧水），（洗茶壶，洗茶杯，拿茶叶，泡茶）两个线程
 *      这样（烧水），（洗茶壶，洗茶杯，拿茶叶，泡茶）两个线程就实现了并行逻辑状态，这三个线程之间的逻辑关系实现完毕
 *      在主线程内设置开启（洗水壶和灌入凉水），随后使用join（）方法等待（洗水壶和灌入凉水），（
 *      烧水），（洗茶壶，洗茶杯，拿茶叶，泡茶）线程完成后再进行泡茶行为，从而实现（泡茶）与这三个线程
 *      串行的逻辑状态，环环相扣。
 *      （乙）由于乙的所以行为都是串行行为，但与甲丙二人可以是并行行为，也可以是串行行为，
 *      当乙与甲丙是并行行为时，可以另外创建一个线程用来进行乙的全部行为
 *      当乙与甲丙时串行行为时，可以在同一个类下另外创建一个方法，此方法以主线程作为载体，用这个方法储存乙的全部行为，
 *      （丙）由于丙的所以行为都是串行行为，但与甲乙二人可以是并行行为，也可以是串行行为，
 *      当丙与甲乙是并行行为时，可以另外创建一个线程用来进行丙的全部行为
 *      当丙与甲乙时串行行为时，可以在同一个类下另外创建一个方法，此方法以主线程作为载体，用这个方法储存丙的全部行为，
 * 使用Slf4j注解来调用其中的log.debug（）方法来打印日志
 * 创建模拟甲乙丙都是并行行为的类，同时开始制作茶水活动
 */
@Slf4j
public class Thread_TeaOne {

    //创建类全局线程变量，用于存放甲同学洗茶壶，加水的线程
    static Thread thread_Jia_kettle_AddWater;

    //创建类全局线程变量，用于存放甲同学烧水的线程
    static Thread thread_Jia_Tea;

    //创建类全局线程变量，用于存放甲同学洗茶壶茶杯，拿茶叶的线程
    static Thread thread_Jia_Water;

    //创建类全局线程变量，用于存放乙同学制作茶水全过程的线程
    static Thread thread_Yi_Water_Tea;

    //创建类全局线程变量，用于存放丙同学制作茶水全过程的线程
    static Thread thread_Bing_Water_Tea;

    //用来储存各位同学同时制作茶水的开始时间
    static long time_Start;

    //用来储存甲同学制作茶水结束的时间
    static long time_Jia_End;

    //用来储存乙同学制作茶水结束的时间
    static long time_Yi_End;

    //用来储存丙同学制作茶水结束的时间
    static long time_Bing_End;


    /**
     * 创建主线程，用来模拟甲同学泡茶行为和结果，实现甲同学四个线程间的逻辑关系
     * 实现甲乙丙三人并行的逻辑关系，开启甲（洗水壶，加水）线程，开启乙丙线程
     * @param args
     */
    public static void main(String[] args) {

        //调用方法，创建甲同学（洗水壶，放水），（烧水），（洗茶壶茶杯拿茶叶）的三个线程，实现这三个线程间的逻辑关系
        jia_Thread();

        //调用方法，创建乙同学制作茶水全过程的线程
        yi_Thread();

        //调用方法，创建丙同学制作茶水全过程的线程
        bing_Thread();

        //记录各位同学同时制作茶水的开始时间
        time_Start = System.currentTimeMillis();

        //启动线程，让CPU给予时间片，获取被任务调度器调度的资格
        thread_Jia_kettle_AddWater.start();

        //启动线程，让CPU给予时间片，获取被任务调度器调度的资格
        thread_Yi_Water_Tea.start();

        //启动线程，让CPU给予时间片，获取被任务调度器调度的资格
        thread_Bing_Water_Tea.start();

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

        //甲同学制作茶水工作模拟已完毕
        log.debug("甲同学表示自己泡的茶很好喝,赞......");

        time_Jia_End = System.currentTimeMillis();

        log.debug("甲同学制作茶水一共耗时为 :"+((time_Jia_End-time_Start)/1000)+"秒钟");

    }


    /**
     * 此方法用于创建囊括甲同学的（洗水壶，放水），（烧水），（洗茶壶茶杯拿茶叶）的三个线程，并且实现了这三个线程间的逻辑关系
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
             * 创建线程模拟甲同学烧开水的过程
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
     * 此方法用来创建囊括乙同学全部行为的线程
     */
    public static void yi_Thread(){

        /**
         * 创建线程，模拟乙同学制作茶水工作全过程
         */
        thread_Yi_Water_Tea = new Thread(()->{

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

            //乙同学的制作茶水工作模拟已完毕
            log.debug("乙同学表示自己泡的茶还不错,赞......");

            time_Yi_End = System.currentTimeMillis();

            log.debug("乙同学制作茶水一共耗时为 :"+((time_Yi_End-time_Start)/1000)+"秒钟");

        },"乙同学制作茶水的全过程");

    }


    /**
     * 此方法用来创建囊括丙同学全部行为的线程
     */
    public static void bing_Thread(){

        /**
         * 创建线程，模拟丙同学的制作茶水工作全过程
         */
        thread_Bing_Water_Tea = new Thread(()->{

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

            //丙同学制作茶水工作模拟已完毕
            log.debug("丙同学表示自己泡的茶很很狠很好喝,赞赞赞......");

            time_Bing_End = System.currentTimeMillis();

            log.debug("丙同学制作茶水一共耗时为 :"+((time_Bing_End-time_Start)/1000)+"秒钟");

            },"丙制作茶水的全过程");

    }

}


/**
 * 创建一个制作茶水的工具类，存放制作茶水各项工作的耗时方法,为各位同学的线程服务
 */
class Make_Work {


    /**
     * 烧水损耗时间
     */
    public static void clean_Kettle(){

        //洗水壶耗时10秒钟
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    /**
     * 烧开水损耗时间
     */
    public static void heat_Up_Water(){

        //烧开水耗时30秒钟
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    /**
     * 洗茶壶损耗时间
     */
    public static void clean_Teapot(){

        //洗茶壶耗时5秒钟
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    /**
     * 洗茶杯损耗时间
     */
    public static void clean_Teacup(){

        //洗茶杯耗时10秒钟
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    /**
     * 拿茶叶损耗时间
     */
    public static void take_Tea(){

        //拿茶叶耗时5秒钟
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    /**
     * 泡茶损耗时间
     */
    public static void make_Tea(){

        //泡茶损耗20秒钟
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    /**
     * 加水损耗时间
     */
    public static void add_Water(){

        //加水损耗5秒钟
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}

