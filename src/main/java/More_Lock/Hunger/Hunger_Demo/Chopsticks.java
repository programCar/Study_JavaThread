package More_Lock.Hunger.Hunger_Demo;

/**
 * 筷子类,为Philosopher类服务
 */
public class Chopsticks {

    //筷子名字
    String chopsticks_Name;

    //初始化筷子名字
    public Chopsticks(String chopsticks_Name){

        this.chopsticks_Name = chopsticks_Name;
    }

    /**
     * 返回筷子名字，用于表示哲学家当前拿筷子的现状
     * @return
     */
    @Override
    public String toString() {
        return "筷子:" + chopsticks_Name;
    }
}
