package AsynchronousMode_ProducerAndConsumer;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;

/**
 * 异步模式之消费者与生产者的学习，异步模式中的生产者和消费者不需要相互产生结果的一一对应关系
 *
 * 由于异步模式之消费者与生产者不需要强制性的一一对应关系，所以可以用来平衡生产与消费的线程资源
 * (比如:采用同步模式之消费者与生产者，那么1000个收信者就需要1000个送信者，这显然不太合理，对线程资源消耗太大，因为他们是一一对应关系,而
 * 异步模式之生产者与消费者不需要这种强制性的一对一关系，它可以是多对一的关系，或者多对多的关系，可以大大减小对线程资源的消耗)
 *
 * 采用异步模式的消费者与生产者之间，生产者不需要考虑结果如何处理，只需要考虑数据的生产，消费者则只需要考虑如何处理生产的数据
 *
 * 由于异步模式的消费者和生产者是采用消息队列来解耦的，考虑到资源的合理分配与利用，采用的消息队列必须有容量的限制，防止产生太多的数据造成过度堆积。
 *
 * 当消息队列满时，将限制其不能再假如数据，空之不会再读取数据
 *
 * 由于消息队列被两个线程所共用，所以必须采用到synchronized进行一个共享资源保护，而且生产者与消费者之间是一存一取，所以采用双向队列更符合情景，
 * 效率更高
 *
 * 如果不采用双向队列的话，只用单向，就会造成添加和取出时，需要移动大量的数据，很明显，这样的效率太慢，而采用双向队列，就可以标记取出的位置和添加的位置，
 * 只需要考虑添加的位置，和取出的位置，无需为了添加和删除移动数据，节省了大量的时间。
 *
 * Java中双向队列的对象类型是LinkedList
 *
 * 温馨提示:JDK中各种阻塞队列，采用的就是这种模式
 *
 * 此类是为了模拟这个消息队列的大概过程和原理而生，这是一个多线程交互消息队列的模拟类,是多线程数据交互的解耦缓冲区
 *
 **/
@Slf4j
public final class MessageQueue {

    //创建一个双向队列类型的对象，提高效率(较于单向队列)，用于生产者线程在尾巴那储存数据和消费者线程在头部取出数据，多线程交互的解耦缓存区
    private static LinkedList<Message> list = new LinkedList<>();

    //限定消息队列的长度，防止储存数据过多而造成数据过多堆积
    private int max_ListLength;


    /**
     * 设定消息队列长度的方法
     * @param max_ListLength
     */
    public void setMax_ListLength(int max_ListLength){
        this.max_ListLength = max_ListLength;
    }


    /**
     * 返回消息队列中最前面的数据的方法，此方法归消费者所调用
     * @return
     */
    public Message Take_Message(){

        //保护共享资源--消息队列list
        synchronized (list){

            //如果消息队列的长度为0，则等待生产者线程生产数据储存进消息队列
            while(list.isEmpty()){

                try {
                    //提示消费者线程
                    log.debug("对不起，此消息队列为空,暂时没有新消息哦!!");
                    list.wait();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }

            }

            //如果消息队列不为空，则返回最前面的数据，并且删除，双向队列的removeFirst()方法可以达到这个效果
            Message message = list.removeFirst();

            //提示消费者线程
            log.debug("信息已到达，请读取信息");

            //唤醒因为消息队列储存已满而等待的生产者线程，让他们醒来去竞争储存数据进消息队列的权限
            list.notifyAll();

            //返回取出的数据
            return message;
        }

    }


    /**
     * 储存数据进消息队列的方法，此方法归生产者所调用
     * @param message
     */
    public void Send_Message(Message message){

        //保护共享资源--消息队列list
        synchronized (list){

            //当消息队列的储存已满，则等待消费者线程把消息队列中的数据消费掉空出空位
            while (list.size() == max_ListLength){

                try {
                    //提示生产者线程
                    log.debug("对不起，此消息队列内存已满，请稍后存入");
                    list.wait();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }

            //提示生产者线程
            log.debug("消息队列有位，请存入");

            //如果消息队列的储存不满则存入数据
            list.addLast(message);

            //提示生产者线程
            log.debug("已存入信息");

            //唤醒因为消息队列为空而无法读取数据的消费者线程，让消费者线程醒来读取线程
            list.notifyAll();

        }

    }


}
