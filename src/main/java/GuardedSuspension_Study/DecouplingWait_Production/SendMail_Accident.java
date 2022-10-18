package GuardedSuspension_Study.DecouplingWait_Production;


/**
 * 模拟送信途中发生的意外，此类是工具类，服务于SendMail类
 */



public class SendMail_Accident {


    public static void Traffic_Jam(){                       //模拟堵车事件

        try {
            Thread.sleep(60);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    public static void EatKFC(){                            //模拟吃饭事件

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    public static void Sleep(){                             //模拟睡觉事件

        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    public static void Toilet(){                            //模拟上厕所事件

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
