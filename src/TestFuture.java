import java.util.concurrent.*;

/**
 * @author mycclee
 * @createTime 2019/11/25 9:43
 */
public class TestFuture {

    ExecutorService executorService = Executors.newFixedThreadPool(100);
    ArchiveSearch search = new ArchiveSearchImpl();

    void showSearch(final String target) throws InterruptedException{
        Future<String> future = executorService.submit(() ->{return search.search(target);});
        displayOtherThing();

        try {
            displayText(future.get());
        } catch (ExecutionException e) {
            cleanUp();
            return;
        }


        /**
         * FutureTask是RunnableFuture的实现类，而RunnableFuture是Future的子接口
         * 上面的执行过程可以使用下面的FutureTask来代替
         */
        FutureTask<String> futureTask = new FutureTask<String>(() -> {return search.search(target);});
        executorService.execute(futureTask);
    }


    void displayOtherThing(){}
    void displayText(String text) {
        System.out.println(text);
    }
    void cleanUp() {}
}

interface ArchiveSearch {
    String search(String target);
}

class ArchiveSearchImpl implements ArchiveSearch {

    @Override
    public String search(String target) {
        return target;
    }
}

