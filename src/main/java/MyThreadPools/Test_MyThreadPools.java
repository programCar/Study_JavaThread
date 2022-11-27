package MyThreadPools;

import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.util.concurrent.TimeUnit;

/**
 * 测试MyThreadPools
 */
@Slf4j
public class Test_MyThreadPools {

    public static void main(String[] args) {

        MyThreadPools threadPools = new MyThreadPools(3,10,3000, TimeUnit.SECONDS,(waitRoom,runnable) -> {

                //以下是各种为了应对WaitRoom满载时采取的机制

                //waitRoom.placeRunnable(runnable);

                //waitRoom.placeRunnable_Time(runnable,5000,TimeUnit.SECONDS);

                //throw new RuntimeException("{}添加失败..." + runnable);

                //runnable.run();
        });

        for (int i = 0; i < 10; i++){

                int j = i;

                threadPools.goExecute(() -> {
                    log.debug("{}",j);
                });
        }

    }

}
