import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author mycclee
 * @createTime 2019/11/25 9:16
 */

/**
 * 基于ReentrantLock实现的ManagedBlocker
 */
public class TestManagedBlocker implements ForkJoinPool.ManagedBlocker {

    final ReentrantLock lock;
    boolean hasLock = false;

    TestManagedBlocker(ReentrantLock lock) {
        this.lock = lock;
    }

    @Override
    public boolean block() throws InterruptedException {
        if (!hasLock){
            lock.lock();
        }
        return true;
    }

    @Override
    public boolean isReleasable() {
        return hasLock || (hasLock = lock.tryLock());
    }
}

/**
 * 这个类在等待给定队列的条目时，可能会被阻塞
 * @param <E>
 */
class QueueTaker<E> implements ForkJoinPool.ManagedBlocker {

    final BlockingQueue<E> queue;
    volatile E item = null;

    public QueueTaker(BlockingQueue queue) {
        this.queue = queue;
    }

    @Override
    public boolean block() throws InterruptedException {
        if (item == null) {
            queue.take();
        }
        return true;
    }

    @Override
    public boolean isReleasable() {
        return item != null || (item = queue.poll()) != null;
    }
}
