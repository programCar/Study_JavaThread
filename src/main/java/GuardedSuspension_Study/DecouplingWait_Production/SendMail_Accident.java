package GuardedSuspension_Study.DecouplingWait_Production;

/**
 * 模拟送信途中发生的意外，此类是工具类，服务于SendMail类
 */
public class SendMail_Accident {

    /**
     * 模拟堵车事件
     */
    public static void Traffic_Jam(){

        try {
            Thread.sleep(60);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    /**
     * 模拟吃饭事件
     */
    public static void EatKFC(){

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    /**
     * 模拟睡觉事件
     */
    public static void Sleep(){

        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    /**
     * 模拟上厕所事件
     */
    public static void Toilet(){

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
