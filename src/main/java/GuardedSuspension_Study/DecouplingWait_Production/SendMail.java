package GuardedSuspension_Study.DecouplingWait_Production;


import lombok.extern.slf4j.Slf4j;


/**
 * 模拟送信行为，以及送信途中所产生的意外
 */


@Slf4j
public class SendMail extends Thread{


    int SendMailId = -1;                                            //接受收信的Id，用于送信


    public SendMail(){

    }


    public SendMail(String ThreadName, int SendMailId){             //有参构造方法用于初始化线程名字便于辨别，初始化送信Id，用于确保多线程交互之间对接对象的准确性，唯一性

        this.setName(ThreadName);

        this.SendMailId =SendMailId;
    }


    @Override
    public void run() {


        if (SendMailId == -1){                                      //防止线程之间不能形成对应关系，不能达成交互作用

            log.debug("糟糕，今天没活干欸......");

            return;
        }


        log.debug("收到邮箱ID为:{}的寄送任务，准备出发........",SendMailId);

        log.debug("快马加鞭中........");

        log.debug("中途堵车啦.........");

        SendMail_Accident.Traffic_Jam();                            //模拟堵车中...

        log.debug("出发........");

        log.debug("吃饭时间到......");

        SendMail_Accident.EatKFC();                                 //模拟吃饭中....

        log.debug("出发.......");

        log.debug("好困。疲劳驾驶，不安全，得睡觉....");

        SendMail_Accident.Sleep();                                  //模拟睡觉中.....

        log.debug("出发.........");

        log.debug("啊~~~肚子疼......");

        SendMail_Accident.Toilet();                                 //模拟上厕所中....

        log.debug("到目的地喽:{}",SendMailId);


        /**
         * 传递送信结果，完成线程交互。
         */

        try {
            MailBoxes.getMailBoxes(SendMailId).setResponse("您好，今天是星期四，请微我50参加肯德基疯狂星期四，showTime....Surprise");
        }catch (NullPointerException e){

            log.debug("喂喂喂~我是{}号信件邮递员，有人在吗？？嘶~~咦~~难道原邮箱格子的主人搬家了？？？",SendMailId);
        }


    }
}
