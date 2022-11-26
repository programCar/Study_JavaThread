package HappyLock.Synchronized_Bank;

public class AccountSafe implements Account{

    private Integer balance;

    public AccountSafe(Integer balance){

        this.balance = balance;
    }

    @Override
    public Integer getBalance() {
        return balance;
    }

    @Override
    public void withdraw(Integer amount) {

       synchronized (this){

           balance -= amount;
       }

    }
}
