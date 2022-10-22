package AsynchronousMode_ProducerAndConsumer;

/**
 * 此类专门用于(储存生产者线程生产的数据和数据Id)与(消费者线程读取生产者线程生产出来的数据和数据Id)
 * 注意:本类没有创建set方法去修改id变量的值，而是采取，是为了防止其他线程去调用set()修改它的值,造成线程安全
 * 只在创建本类对象时初始化赋值，进行一个读写操作，之后的操作是只读，所以不会发生多线程的安全问题
 * 使用final关键字修饰，final是最终的意思，是不可修改，不可变化的意思，防止其他线程创建他的子类改写造成一些多线程安全问题
 */
public final class Message {

    //数据Id,用于不同数据的唯一识别
    private int message_Id;

    //储存生产者线程生产的数据的地方
    private Object message_Content;


    public Message(){}


    /**
     * 用于初始化数据的Id和数据
     * @param message_Id
     * @param message_Content
     */
    public Message(int message_Id, Object message_Content){

        this.message_Id = message_Id;

        this.message_Content = message_Content;

    }


    /**
     * 返回数据的Id,用于消费者线程查找数据所需数据使用
     * @return int
     */
    public int getMessage_Id(){

        return message_Id;
    }


    /**
     * 返回数据，给予消费者线程所使用
     * @return Object
     */
    public Object getMessage_Content(){

            return message_Content;
    }


    /**
     * 重写toString方法，用于消费者线程直接读取数据和数据Id使用
     * @return String
     */
    @Override
    public String toString() {
        return "Message{" +
                "message_Id=" + message_Id +
                ", message_Content=" + message_Content +
                '}';
    }
}
