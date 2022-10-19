package GuardedSuspension_Study;

/**
 *创建一个类，给予GuardedSuspension中的second_Dog线程和courier线程作为数据处理结果的传递和接受的中介，实现两个线程之间的数据交互协作
 */
public class GuardedObject {

    //接收线程所传递来的结果
    private static Object response;


    /**
     * 获取其他线程所传递来的结果
     * @return
     */
    public Object getResponse(){

        //对共享资源response进行保护
        synchronized (this){

            //判断结果是否传递到达，不是则一直等待
           while (response == null){

               try {
                   this.wait();
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }

           }

            //是则返回结果
           return response;

        }

    }


    /**
     * 用于传递线程结果
     * @param response
     */
    public void setResponse(Object response){

        //对共享资源进行保护
        synchronized (this){

            //传递线程结果
            this.response = response;

            //唤醒线程继续运行
            this.notify();

        }

    }

}
