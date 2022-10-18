package GuardedSuspension_Study.DecouplingWait_Production;


import lombok.extern.slf4j.Slf4j;


/**
 * 此类用作模拟收信,收信状态
 */


@Slf4j
public class ReceiveMail extends Thread{


    private int waitTime;                                       //用作设定邮箱格子等待时间


    public ReceiveMail(){

    }


    public ReceiveMail(String ThreadName, int waitTime){        //有参构造方法，初始化线程名字，便于辨别，初始化等待时间

        this.setName(ThreadName);

        this.waitTime = waitTime;
    }


    private int myId = MailBoxes.createMailBox();                           //收信Id,用于打印效果和给予送信线程用，确保多线程交互之间的对接对象的准确性和唯一性


    public int getMyId(){                                                   //给予送信线程送信的Id

            return myId;

    }


    /**
     *
     * private int myId = MailBoxes.createMailBox();                （1）
     *
     * public int getMyId(){
     *          return myId;
     *}
     * 这样设计的巧妙之处在于:由于myId这个变量是两个线程在使用，所以是共享资源
     *
     * 如果不这样设计，将private int myId = MailBoxes.createMailBox();分开设计为：
     *
     * private int myId;                                            （2）
     *
     * public int getMyId(){
     *         return myId;
     *      }
     *
     *myId = MailBoxes.createMailBox();
     *
     *就会发生竞态条件，造成一个线程的myId = MailBoxes.createMailBox();的还没有赋值完成就被另一个线程调用getMyId()方法
     *将MyId的值取出，造成数据错误，多线程的安全问题，这样是不允许的。
     *
     * 但....假如采取以往所学知识添加Synchronized锁住共享资源的设计，如:
     *
     * private int myId;                                            （3）
     *
     *synchronized(this){
     *
     *     myId = MailBoxes.createMailBox();
     *
     *}
     *
     * public synchronized int getMyId(){
     *
     *     return myId;
     * }
     *
     * 设计运行之后会发现，依然没有解决问题
     *
     * 原因在于synchronized虽然确实锁住了共享资源MyId这个变量，避免发生了线程交互之间的竞态条件,使线程对这个变量的取值变为同步模式
     * 但由于Synchronized锁只能多对线程临界区的读写操作交互之间构成效果，两个都必须是读写操作，
     * 它的原理是防止多线程对共享资源读写操作时，某线程因为时间片被用完，或者被意外打断等等情况而造成JVM读写操作在对变量的值写完后，无法上传赋值被更改的数值
     * 造成多线程对共享资源数值的读写操作交互结果错误。
     *
     * 但对于线程之间不完全是读写操作时无法起作用，比如此情况就是这种，它是多线程之间（读写-——读）的操作
     * 之所以synchronized无法起作用，主要是因为synchronized虽然锁住了共享资源，但无法决定哪个线程先拥有synchronized所锁住资源的使用权
     * 它实现的情景确实是同步模式，没有发生竞态条件，但一旦是只读的线程先拿到使用权，就会读到共享资源的初始化值0，从而造成数据不准确。无法得到正确的结果
     *
     * 解决方法:
     *根据所学以往知识总结
     *一个变量在定义和初始化时，在Jvm上编译情况，可以确定变量的定义和初始化是一个原子性操作。无需担心多线程读写操作发生竞态条件。
     *一个变量被使用时，必须先（定义-声明）才能被使用，这也确保了private int myId = MailBoxes.createMailBox();语句完成后
     * 调用getMyId()方法的线程才能对MyId的值进行读。从整个运行情况逻辑来看，它们两之间整体上是一个原子性，是有执行顺序性。
     *
     * 解决了（2）（3）设计情况所造成的缺陷，维护增加了了多线程交互之间的安全性
     */



    @Override
    public void run() {


        log.debug("哇~~~我终于有了自己的邮箱 :{}",myId);


        log.debug("听说新手注册邮箱就送50，好期待啊......");


        Object freight = MailBoxes.getMailBoxes(myId).getResponse(waitTime);//接收线程交互信息载体中的信息


        if (freight != null){                                               //如果信到了，则通知收信人

            log.debug("叮咚~~~您好，您的邮箱已更新，邮箱信息为 :{}",freight);

            log.debug("哇哇哇~~~邮箱收到了好开心");

            log.debug("看完了，真是一个surprise......(生气)");

            MailBoxes.RemoveMail(myId);                                     //删掉已读邮箱格子，防止产生太多垃圾

            return;

        }

        /**
         * 请注意，此逻辑并不是收信人一直在等送信人的信，而是邮箱格子在等收信人的信，设定的等待时间不作用于收信人，而是作用于邮箱格子
         * 因为采用的是缓存区（MailBoxes类）储存信，所以只要在设定的等待时间内，哪怕收信人不等待信，一旦信送到，信也会储存在邮箱中
         * 到时候再来看，信依然还在.
         */

        log.debug("有没有搞错，那么慢，我不等了，回去喝可乐看动画片去.........");     //如果收信人发现邮箱格子过了等待时间还没到，就继续去干其他事


        MailBoxes.RemoveMail(myId);                                         //删掉过期的邮箱格子，防止产生太多垃圾
    }
}
