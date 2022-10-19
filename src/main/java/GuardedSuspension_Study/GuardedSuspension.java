package GuardedSuspension_Study;

import lombok.extern.slf4j.Slf4j;

/**
 *这样设计的好处:较于使用join()方法实现这个案例
 * join()的使用只能是全局的
 * wait()的使用是局部的
 * join()方法必须等待另一个线程运行结束，整个程序才能继续运行，效率低下
 * wait()方法在等待的时候，程序可以去运行其他的事，效率高
 */
@Slf4j
public class GuardedSuspension {


    public static void main(String[] args) {

        //创建线程交互协作的中介
        GuardedObject guardedObject = new GuardedObject();

        //模拟线程处理后的结果
        String string = "您好先生，我是快递员，您的快递到了";

        /**
         * 模拟与courier线程互动
         */
        Thread second_Dog = new Thread(() -> {

            log.debug("等待快递.......");

            //获取courier线程处理后的结果
            String freight = (String) guardedObject.getResponse();

            //输出打印获取的结果
            log.debug("{}",freight);

            log.debug("好耶快递到了");

        },"二狗子");

        /**
         * 模拟与second_Dog线程互动
         */
        Thread courier = new Thread(() -> {

            log.debug("快递打包.....");

            log.debug("快递分拣.....");

            log.debug("快递运送中.....");

            //传递结果给second_Dog线程
            guardedObject.setResponse(string);

        },"快递员");

        second_Dog.start();

        courier.start();

    }

}
