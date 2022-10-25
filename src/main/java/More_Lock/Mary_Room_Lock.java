package More_Lock;

/**
 * 对One_Room_Lock工具类类改进,为Mary_Lock类服务
 * 改进方法:房间多个房间
 */
public class Mary_Room_Lock {

    //创建学习的房间，保护共享资源
    private Object study_Room = new Object();

    //创建锻炼的房间，保护共享资源
    private Object exercise_Room = new Object();

    //创建看电视剧的房间，保护共享资源
    private Object watchTV_Room = new Object();


    /**
     * 锻炼身体的方法，给二狗子线程用
     */
    public void Take_Exercise(){

        synchronized (exercise_Room){

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

        synchronized(study_Room){

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

        synchronized (watchTV_Room){

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
