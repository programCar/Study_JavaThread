package MyThreadPools;

/**
 * 当WaitRoom满载时，为实现更加人性化的处理机制所使用
 * @param <T>
 */
public interface Reject_Select<T> {

    public void reject(WaitRoom<T> waitRoom, T runnable);
}
