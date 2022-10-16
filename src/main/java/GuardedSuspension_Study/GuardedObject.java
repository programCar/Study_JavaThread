package GuardedSuspension_Study;


/**
 *创建一个类，给予GuardedSuspension中的second_Dog线程和courier线程作为数据处理结果的传递和接受的中介，实现两个线程之间的数据交互协作
 */


public class GuardedObject {


    private static Object response;                             //接收线程所传递来的结果


    public Object getResponse(){                                //获取其他线程所传递来的结果

        synchronized (this){                                    //对共享资源response进行保护

           while (response == null){                            //判断结果是否传递到达，不是则一直等待

               try {
                   this.wait();
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }

           return response;                                     //是则返回结果
        }
    }


    public void setResponse(Object response){                   //用于传递线程结果


        synchronized (this){                                    //对共享资源进行保护

            this.response = response;                           //传递线程结果

            this.notify();                                      //唤醒线程继续运行
        }
    }
}
