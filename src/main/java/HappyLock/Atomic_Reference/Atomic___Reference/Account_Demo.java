package HappyLock.Atomic_Reference.Atomic___Reference;

import java.math.BigDecimal;

public class Account_Demo {

    public static void main(String[] args) {

        Account account = new AccountSafe(BigDecimal.valueOf(10000));

        Account.demo(account);

    }



}
