package ReentrantLock.JMM.Visibleness;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VisiblenessProblem_Demo {

    private static boolean run = true;

    public static void main(String[] args) {

        Thread second_Dog = new Thread(() -> {

            while(run){


            }

        },"二狗子");


        second_Dog.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

       log.debug("停止....");
        run = false;
    }
}
