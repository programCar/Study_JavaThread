package My_AtomicInteger;

import sun.misc.Unsafe;

import java.lang.reflect.Field;


/**
 * 此类用于获取Unsafe对象，通过反射获取,MyAtomicInteger类服务
 */
public class UnSafeAccessor {

    private static final Unsafe unsafe;

    static {

        try {
            //通过反射获取Unsafe类中theUnsafe变量形成的Field对象
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");

            //开启，反射的对象在使用时取消 Java 语言访问检查，允许对私有成员变量进行操作的权限
            theUnsafe.setAccessible(true);

            //获取Unsafe对象，并且初始值为null
            unsafe = (Unsafe) theUnsafe.get(null);
        }catch (NoSuchFieldException | IllegalAccessException e){
            throw new RuntimeException();
        }

    }


    /**
     * 返回已获取的Unsafe对象
     * @return
     */
    public static Unsafe getUnsafe(){

        return unsafe;
    }
}
