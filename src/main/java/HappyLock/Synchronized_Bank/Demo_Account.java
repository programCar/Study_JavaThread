package HappyLock.Synchronized_Bank;

public class Demo_Account {

    /**
     * 不安全的接口测试
     * @param args
     */
//    public static void main(String[] args) {
//
//        Account account = new AccountUnsafe(10000);
//
//        Account.demo(account);
//    }
    public static void main(String[] args) {

        Account account = new AccountSafe(10000);

        Account.demo(account);

    }
}
