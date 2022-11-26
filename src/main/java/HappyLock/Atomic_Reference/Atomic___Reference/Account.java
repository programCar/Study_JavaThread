package HappyLock.Atomic_Reference.Atomic___Reference;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public interface Account {

    public BigDecimal getBalance();

    public void withdraw(BigDecimal amount);

    public static void demo(Account account){

        List<Thread> ts = new ArrayList<>();

        for (int i = 0; i < 1000; i++){

            ts.add(new Thread(() -> {

                account.withdraw(BigDecimal.valueOf(10));
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
