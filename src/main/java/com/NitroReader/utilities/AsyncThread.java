package com.NitroReader.utilities;


import java.util.concurrent.*;

public class AsyncThread {
    //creating and configuring the Thread Pool
    static ExecutorService mes = new ThreadPoolExecutor(1, 25, 800, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());

    //method to execute every thread that is pushed into  threadPool
    public static void executeThread(Runnable run){
    mes.submit(run);
    }
}
