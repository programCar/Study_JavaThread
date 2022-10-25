package More_Lock;

/**
 * 工具类，模拟各线程进入房间活动的类，为One_Lock类服务
 */
public final class One_Room_Lock {


    /**
     * 锻炼身体的方法，给二狗子线程用
     */
    public void Take_Exercise(){

        synchronized (this){

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }


    /**
     * 模拟学习的方法，给老王线程用
     */
    public void Study(){

        synchronized(this){

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }


    /**
     * 模拟看电视剧的方法，给喵大怪线程用
     */
    public void Watch_TV(){

        synchronized (this){

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
