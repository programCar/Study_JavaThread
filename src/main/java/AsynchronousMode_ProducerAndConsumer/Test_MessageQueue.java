package AsynchronousMode_ProducerAndConsumer;

import lombok.extern.slf4j.Slf4j;

/**
 * 此类用于测试模拟异步模式之生产者与消费者的运行结果(MessageQueue类和Message类的联动)
 */
@Slf4j
public class Test_MessageQueue {


    public static void main(String[] args) {

        //创建消息队列对象(MessageQueue对象),实现异步模式之生产者与消费者的运行过程，同时作为多线程交互的解耦缓存区
        MessageQueue messageQueue = new MessageQueue();

        //设置消息队列的大小
        messageQueue.setMax_ListLength(3);


        //创建6个生产者线程
        for (int i = 0; i < 6; i++){

            /**
             * 注意，此操作并不是多此一举，之所以这样是因为Java8所诞生的lambda语法中变量的取值必须为静态的，不能是动态的，i是动态的
             * 因为i++，i的值一直在变
             * 解决方法:定义声明一个局部变量去接收这个i值
             * 特别注意:虽然这个变量的值也在变，但它是初始化的时候变的值,不是初始化后变得值，是每一次初始化的值都不同，值在内存的地点也不一样，
             * 不是同一个地点的值在变，所以它是静态的。在底层原理是这样一个过程。
             * 注意:假如在for循环外面定义声明一个变量，然后在for循环里面赋值接受它，这样是不可以的，是有问题的，如果你碰到这个问题，恭喜你，
             * 你很幸运地看到了我的文章，原理就是我标注在这的特别注意的地方。你只需要把接收值的变量定义声明写在循环体内即可。
             */
            int id = i;

            //创建生产者线程，并且初始化数据id和数据，将生产的东西放在消息队列中
            new Thread(() -> {

                messageQueue.Send_Message(new Message(id,"您好，我是第" + id +"位送信者"));

            },"生产者" + i).start();

        }

        //创建消费者线程，读取消息队列中的数据和数据id
        new Thread(() -> {

            while (true){
                log.debug("{}",messageQueue.Take_Message().toString());
            }

        },"消费者").start();
    }
}
