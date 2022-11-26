package HappyLock.Atomic_Reference.Atomic___Reference;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicReference;

public class AccountSafe implements Account{

    private AtomicReference<BigDecimal> balance;

    public AccountSafe(BigDecimal balance){

        this.balance = new AtomicReference<>(balance);


    }

    @Override
    public BigDecimal getBalance() {
        return balance.get();
    }


    @Override
    public void withdraw(BigDecimal amount) {

        while (true){

            BigDecimal prev = balance.get();

            BigDecimal next = prev.subtract(amount);

            if (balance.compareAndSet(prev,next)){

                break;
            }
        }

    }
}
