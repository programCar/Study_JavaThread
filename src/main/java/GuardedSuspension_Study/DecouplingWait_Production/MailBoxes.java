package GuardedSuspension_Study.DecouplingWait_Production;

import GuardedSuspension_Study.GuardedObject;

import java.util.Map;

import java.util.Hashtable;

import java.util.Set;

/**
 * 耦合:指两个或两个以上的体系或两种运动形式间通过相互作用而彼此影响以至联合起来的现象
 * 解耦: 解耦就是用数学方法将两种运动分离开来处理问题
 *
 *     设计缘由:两个线程交互的耦合性太强，收信线程和送信线程之间的相互影响太激烈，如果送信的人一旦超过一定时间，收信人将会放弃接收，或者一直等待。
 * 同时这也要求送信的性能和效率必须极高，才能让收信的人接收到想要的结果，这种情况在现实之中是不合理的，不符合现实的场景。
 *
 * 解决思路，可以创建一个缓存区，用作收信的线程和送信的线程一个中介，送信的只需要找到收信人的Id就可以送信，没有则不送，收信的人只需要有空去看看缓存区
 * 就知道自己的信到没，而不用一直等待，这样将大大降低收信与送信之间的耦合性
 *
 * MailBoxes(模拟交互中介邮箱)，实现多线程交互的解耦机制，大大降低了了（收信与送信）耦合性太强的问题
 *
 * 以下注释中的（信息载体）:是指本包中的GuardedObject_TimeOut类
 *
 */
public class MailBoxes {

    //模拟邮箱，作为线程交互的缓存区，实现解耦
    static final Map<Integer,GuardedObject_TimeOut> mailBoxes = new Hashtable<Integer,GuardedObject_TimeOut>();

    //信件Id，确保多线程交互时，线程交互对接对象的唯一性，正确性
    private static int mail_ID = -1;


    /**
     * 产生信件Id，并且返还，用作创建模拟邮箱格子，给予收信人自己的ID
     */
    private static synchronized int getMail_ID(){

            return ++mail_ID;

    }


    /**
     * 用作传递存储收信与送信之间交互信息的载体
     * @param ID
     * @return
     */
    public static GuardedObject_TimeOut getMailBoxes(int ID){

        return mailBoxes.get(ID);

    }


    /**
     * 用于清理邮箱内载体，防止产生太多垃圾
     * @param ID
     */
    public static void RemoveMail(int ID){

        mailBoxes.remove(ID);

    }


    /**
     * 创建邮箱格子，并且返回这个邮箱格子的Id,此Id也是信息载体的Id
     * 此方法产生邮箱格子所使用的Id强制采用本方法自主配置，保证使用到的Id唯一性，防止用户自主输入Id而发生多线程交互临界区的竞态条件
     * 从而霸占了别人的邮箱格子，使原主无法使用本Id创建邮箱，从而导致多线程安全危机，所以必须保证本方法的原子性，一步到位.
     */
    public static int createMailBox(){

        GuardedObject_TimeOut guardedObject = new GuardedObject_TimeOut();

        int Id = getMail_ID();

        guardedObject.setID(Id);

        mailBoxes.put(Id,guardedObject);

        return Id;

    }

}
