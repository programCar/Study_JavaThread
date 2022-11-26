package HappyLock.Synchronized_Bank;

import java.util.ArrayList;
import java.util.List;

public interface Account {

    public Integer getBalance();

    public void withdraw(Integer amount);

    public static void demo(Account account){

        List<Thread> ts = new ArrayList<>();

        for (int i = 0; i < 1000; i++){

            ts.add(new Thread(() -> {

                account.withdraw(10);
            }));
        }

        long start = System.nanoTime();

        ts.forEach(Thread::start);

        ts.forEach(t ->{

            try{
                t.join();
            }catch (InterruptedException e){
                e.printStackTrace();
            }

        });

        long end = System.nanoTime();

        System.out.println(account.getBalance() + "cost:" + (end - start)/1000_000 + "ms");



    }
}
