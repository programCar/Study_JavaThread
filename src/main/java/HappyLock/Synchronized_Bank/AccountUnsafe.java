package HappyLock.Synchronized_Bank;

public class AccountUnsafe implements Account{

    private Integer balance;

    public AccountUnsafe(Integer balance){
        this.balance = balance;
    }

    @Override
    public Integer getBalance() {

        return balance;

    }

    @Override
    public void withdraw(Integer amount) {

        balance -= amount;

    }
}
