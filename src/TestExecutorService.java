import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author mycclee
 * @createTime 2019/11/24 18:03
 */
public class TestExecutorService implements Runnable{

    private final ServerSocket serverSocket;
    private final ExecutorService pool;

    public TestExecutorService(int port, int poolSize) throws IOException {
        serverSocket = new ServerSocket(port);
        pool = Executors.newFixedThreadPool(poolSize);
    }

    @Override
    public void run() {
        try {
            for (;;) {
                pool.execute(new Handler(serverSocket.accept()));
            }
        } catch (IOException e) {
            pool.shutdown();
        }
    }

    //这个方法分两个步骤停止ExecutorService，首先使用shutdown()方法拒绝新来的任务，然后如果有必要的话调用shutdownNow()来取消所有遗留的任务
    public void shutdownAndAwaitTermination(ExecutorService pool) {
        pool.shutdown();
        try {
            if (pool.awaitTermination(60, TimeUnit.SECONDS)) {
                pool.shutdown();
                if (pool.awaitTermination(60, TimeUnit.SECONDS)) {
                    System.out.println("pool is not terminate");
                }
            }
        } catch (InterruptedException e) {
            pool.shutdown();
            Thread.currentThread().interrupt();
        }
    }
}

class Handler implements Runnable {

    private final Socket socket;

    public Handler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        //read and service request on socket
    }
}
