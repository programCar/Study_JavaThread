package Thread_Synchronized;

import lombok.extern.slf4j.Slf4j;

/**
 * 对Synchronized保护的资源采用面向对象的思想进行一个改进
 * 把公共资源放入Room类内，通过Room内的Synchronized进行一个加锁，而Synchronized的对象为Room自己，更符合Java的逻辑思想
 * 什么是面向对象:面向对象就是当你使用一个东西时，只需要知道它的作用和功能，不需要知道它的实现过程，是对一个物体的具体抽象。
 */
@Slf4j
public class Thread_Improve_Synchronized {


    public static void main(String[] args) {

        Room room = new Room();

        Thread thread_increment = new Thread(()->{

            for (int i=0; i<=5000; i++){
                room.increment();
            }

        },"thread_increment");

        Thread thread_decrement = new Thread(()->{

            for (int i=0; i<=5000; i++){
                room.decrement();
            }

            },"thread_decrement");

        log.debug("count的值为 :",room.getCount());

    }

}


class Room{

    private int count = 0;


    public void increment(){

        synchronized (this){
            count++;
        }

    }


    public void decrement(){

        synchronized (this){
            count--;
        }

    }


    public int getCount(){

        synchronized (this){
            return count;
        }

    }

}
