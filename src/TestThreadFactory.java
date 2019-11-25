import java.util.concurrent.ThreadFactory;

/**
 * @author mycclee
 * @createTime 2019/11/25 11:38
 */
public class TestThreadFactory implements ThreadFactory {

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r);
    }
}
