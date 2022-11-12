package More_Lock.DeadLock.DeadLock_Demo;

public class Chopsticks {

    String chopsticks_Name;

    public Chopsticks(String chopsticks_Name){

        this.chopsticks_Name = chopsticks_Name;
    }

    @Override
    public String toString() {
        return "筷子:" + chopsticks_Name;
    }
}
