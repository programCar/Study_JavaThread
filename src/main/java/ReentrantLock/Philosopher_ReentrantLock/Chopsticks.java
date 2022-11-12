package ReentrantLock.Philosopher_ReentrantLock;

import java.util.concurrent.locks.ReentrantLock;

public class Chopsticks extends ReentrantLock {

        String chopsticks_Name;

        public Chopsticks(String chopsticks_Name){

            this.chopsticks_Name = chopsticks_Name;
        }

        @Override
        public String toString() {
            return "筷子:" + chopsticks_Name;
        }
    }


