package More_Lock;

import lombok.extern.slf4j.Slf4j;

/**
 * 测试Mary_Room_Lock类的方法，通过测试会发现，通过创建多个房间，让他们各自去不同的房间里弄自己的东西，可以大大提高效率，同时提高了并发度
 */
@Slf4j
public class Mary_lock {

    //用于计算除主线程外所有线程的开始时间
    static long thread_Began;

    //用于计算second_Dog线程做完任务的时间
    static long secondDog_End;

    //用于计算big_Cat线程做完任务的时间
    static long bigCat_End;

    //用于计算old_Wang线程做完任务的时间
    static long oldWang_End;


    public static void main(String[] args) {

        //创建用于各个线程做任务的工具类
        Mary_Room_Lock Room = new Mary_Room_Lock();

        //二狗子进房间锻炼身体
        Thread second_Dog = new Thread(() -> {

            log.debug("准备进房间锻炼身体");

            //调用模拟锻炼身体的方法
            Room.Take_Exercise();

            //记录二狗子完成锻炼的时间
            secondDog_End = System.currentTimeMillis();

            //告知用户，二狗子锻炼离开房间距离房间开放花了多少时间
            log.debug("二狗子在{}，时候锻炼身体完毕", (secondDog_End - thread_Began));

            }, "二狗子");

        //喵大怪进房间看电视剧
        Thread big_Cat = new Thread(() -> {

            log.debug("准备进房间看电视剧");

            //调用模拟看电视的方法
            Room.Watch_TV();

            //记录喵大怪看完电视剧的时间
            bigCat_End = System.currentTimeMillis();

            //告知用户，喵大怪看电视剧离开房间距离房间开放花了多少时间
            log.debug("喵大怪在{}，时候看完电视剧", (bigCat_End - thread_Began));

            }, "喵大怪");

        //
        Thread old_Wang = new Thread(() -> {

            log.debug("准备进入房间进行深入学习");

            //调用模拟学习的方法
            Room.Study();

            //记录老王学习完成的时间
            oldWang_End = System.currentTimeMillis();

            //告知用户，老王学习完离开房间距离房间开放花了多少时间
            log.debug("老王在{}，时候完成深入学习", (oldWang_End - thread_Began));

            }, "老王");

        //记录房间开放时间(线程开启时间)
        thread_Began = System.currentTimeMillis();

        second_Dog.start();

        big_Cat.start();

        old_Wang.start();

    }

}
