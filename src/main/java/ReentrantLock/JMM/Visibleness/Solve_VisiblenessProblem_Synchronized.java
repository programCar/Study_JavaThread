package ReentrantLock.JMM.Visibleness;

import lombok.extern.slf4j.Slf4j;

/**
 * 使用Synchronized来解决线程交互间的可见性问题
 */
@Slf4j
public class Solve_VisiblenessProblem_Synchronized {

    private static boolean run = true;

    private static  Object object = new Object();

    public static void main(String[] args) {

        Thread second_Dog = new Thread(() -> {

            while (true){

                synchronized (object){

                    if (!run){

                        break;
                    }
                }
            }

        },"二狗子");


        second_Dog.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.debug("停止....");

        synchronized (object){

            run = false;
        }
    }
}
