package com.example.demo.entity;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomThreadFactory implements ThreadFactory {

    //线程前缀,采用AtomicInteger实现线程编号线程安全自增
    private final AtomicInteger atomicInteger = new AtomicInteger(1);
    private final String namePrefix;
    //是否是守护线程
    private final boolean isDaemon;
    public CustomThreadFactory(String namePrefix, boolean isDaemon) {
        this.namePrefix = namePrefix;
        this.isDaemon = isDaemon;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r,namePrefix+'-'+atomicInteger.getAndIncrement());
        thread.setDaemon(isDaemon);
        //设置线程优先级
        if(thread.getPriority()!=Thread.NORM_PRIORITY){
            thread.setPriority(Thread.NORM_PRIORITY);
        }
        return thread;
    }
}
