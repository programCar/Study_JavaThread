package HappyLock.HappyLock_Bank;

import java.util.concurrent.atomic.AtomicInteger;

public class AccountSafe implements Account{

    private AtomicInteger balance;

    public AccountSafe(Integer balance){

        this.balance = new AtomicInteger(balance);
    }

    @Override
    public Integer getBalance() {
        return balance.get();
    }

    int prev;

    int next;

    @Override
    public void withdraw(Integer amount) {

        while (true){

            prev = balance.get();

            next = prev - amount;

            if (balance.compareAndSet(prev,next)){

                break;
            }
        }

    }
}
