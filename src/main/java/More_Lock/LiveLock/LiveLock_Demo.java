package More_Lock.LiveLock;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LiveLock_Demo {

    static volatile int count = 50;

    public static void main(String[] args) {

        Thread second_Dog = new Thread(() -> {

            while (count > 0){

                count--;

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                log.debug("count:{}",count);
            }

        },"二狗子");

        Thread big_Cat = new Thread(() -> {

            while (count < 60){
                count++;

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                log.debug("count:{}",count);
            }

        },"大猫咪");

        big_Cat.start();


        second_Dog.start();


    }
}
