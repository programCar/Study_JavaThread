package More_Lock.Hunger.Hunger_Demo;


import lombok.extern.slf4j.Slf4j;

/**
 * 哲学家类，为Dine类服务，模拟哲学家拿筷子吃饭场景
 */
@Slf4j
public class Philosopher extends Thread{

    //定义左手筷子变量
    private Chopsticks left_Chopstick;

    //定义右手筷子变量
    private Chopsticks right_Chopstick;

    public Philosopher(){}

    /**
     * 初始化左右手筷子和线程名字
     * @param name
     * @param left_Chopstick
     * @param right_Chopstick
     */
    public Philosopher(String name, Chopsticks left_Chopstick, Chopsticks right_Chopstick){

        super(name);

        this.left_Chopstick = left_Chopstick;

        this.right_Chopstick = right_Chopstick;

    }


    /**
     * 锁住左右手筷子吃饭，必须左手右手同时拿筷子吃饭
     */
    @Override
    public void run() {

        while(true){

            synchronized (left_Chopstick){

                synchronized(right_Chopstick){

                    eat();
                }

            }
        }
    }

    /**
     * 吃饭方法，模拟吃饭
     */
    private void eat(){

        log.debug("吃吃吃，快吃快吃，这不得狠狠干饭.......");

        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){

            e.printStackTrace();
        }
    }
}
