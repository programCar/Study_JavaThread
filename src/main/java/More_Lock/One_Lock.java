package More_Lock;

import lombok.extern.slf4j.Slf4j;

/**
 * 场景:
 * 当一件大房子里有三个功能，一个是学习，一个是看电视剧，一个是锻炼身体，他们三者之间是互不相关的
 * 现在二狗子要锻炼身体，喵大怪要看电视剧，老王要学习，可他们只能使用一间房子，一间房子只有一个对象锁，他们只能等进去的完成后，自己才能进去，
 * 这样的话显然效率很低(并发度很低)
 *
 * 由此提出改进，学习多把锁(一个大房间里有多个小房间)
 */
@Slf4j
public class One_Lock {

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
        One_Room_Lock Room = new One_Room_Lock();

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
