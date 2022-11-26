package My_AtomicInteger;

import HappyLock.Atomic_Reference.Atomic___Reference.Account;
import sun.misc.Unsafe;

import java.math.BigDecimal;


/**
 * 模拟cas操作实现原理
 */
public class MyAtomicInteger implements Account {

    //总数
    private volatile int value;

    //用于实现cas操作
    private static final Unsafe unsafe;

    //储存偏移量，用于cas操作
    private static final long valueOffset;


    /**
     * 初始化总数
     * @param value
     */
    public MyAtomicInteger(int value){

        this.value = value;
    }

    static {
        unsafe = UnSafeAccessor.getUnsafe();
        try {
          valueOffset = unsafe.objectFieldOffset(MyAtomicInteger.class.getDeclaredField("value"));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }


    /**
     * 返回现总数
     * @return
     */
    private final int getValue(){
        return this.value;
    }


    /**
     * 用于多线程的线程安全减法操作，模拟cas
     * @param count
     */
    private final void decrement(int count){

        while (true) {

            int prev = this.value;

            int next = prev - count;

            if (unsafe.compareAndSwapInt(this, valueOffset, prev, next)) {
                break;
            }

        }

    }


    /**
     * 用于返回现有值
     * @return
     */
    @Override
    public BigDecimal getBalance() {
        return BigDecimal.valueOf(getValue());
    }


    /**
     * 进行线程安全的减法操作
     * @param amount
     */
    @Override
    public void withdraw(BigDecimal amount) {

        decrement(amount.intValue());

    }
}
