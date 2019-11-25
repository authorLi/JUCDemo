import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @author mycclee
 * @createTime 2019/11/25 11:06
 */
public class TestScheduledExecutorService {

    private final ScheduledExecutorService service = Executors.newScheduledThreadPool(1);

    private void beepForAnHour() {
        final Runnable beeper = () -> System.out.println("beeper");
        final ScheduledFuture<?> beeperFuture = service.scheduleAtFixedRate(beeper,10,10,SECONDS);
        service.schedule(() -> {beeperFuture.cancel(true);},60 * 60,SECONDS);
    }
}
