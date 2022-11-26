package My_ConnectionPool;

import java.util.Random;

public class Test_MyConnectionPools {

    public static void main(String[] args) {

        ConnectionPools pools = new ConnectionPools(2);

        for (int i = 0; i < 5; i++){

            new Thread(() -> {

                MyConnection borrowConnection = pools.getBorrowConnection();

                try {
                    Thread.sleep(new Random().nextInt(3000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                pools.setFreeConnection(borrowConnection);
            },i+"").start();
        }
    }
}
