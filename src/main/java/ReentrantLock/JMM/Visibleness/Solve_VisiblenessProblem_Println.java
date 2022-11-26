package ReentrantLock.JMM.Visibleness;

/**
 * 使用System.out.println()来解决线程交互间的可见性问题
 */
public class Solve_VisiblenessProblem_Println {

    private static boolean run = true;

    public static void main(String[] args) {

        Thread second_Dog = new Thread(() -> {

            while(run){

                System.out.println("GOGOGO.........");

            }

        },"二狗子");


        second_Dog.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        run = false;
        System.out.println("停止.......");
    }
}
