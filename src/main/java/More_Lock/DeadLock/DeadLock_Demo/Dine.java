package More_Lock.DeadLock.DeadLock_Demo;

public class Dine {

    public static void main(String[] args) {

        Chopsticks c1 = new Chopsticks("1");

        Chopsticks c2 = new Chopsticks("2");

        Chopsticks c3 = new Chopsticks("3");

        Chopsticks c4 = new Chopsticks("4");

        Chopsticks c5 = new Chopsticks("5");

        Philosopher first_Philosopher = new Philosopher("鸡哥",c1,c2);

        Philosopher second_Philosopher = new Philosopher("小黑子",c2,c3);

        Philosopher third_Philosopher = new Philosopher("一眼丁真",c3,c4);

        Philosopher fourth_Philosopher = new Philosopher("麻辣毛蛋",c4,c5);

        Philosopher fifth_Philosopher = new Philosopher("冲冲冲",c5,c1);

        first_Philosopher.start();

        second_Philosopher.start();

        third_Philosopher.start();

        fourth_Philosopher.start();

        fifth_Philosopher.start();


    }

}
