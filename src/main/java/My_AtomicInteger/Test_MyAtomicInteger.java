package My_AtomicInteger;

import HappyLock.Atomic_Reference.Atomic___Reference.Account;


/**
 * 测试MyAtomicInteger类
 */
public class Test_MyAtomicInteger {


    public static void main(String[] args) {

        for (int i = 0; i < 300; i++) {
            Account.demo(new MyAtomicInteger(10000));
        }

    }
}
