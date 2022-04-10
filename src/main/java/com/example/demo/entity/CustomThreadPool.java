package com.example.demo.entity;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CustomThreadPool extends ThreadPoolExecutor {

/**
 *
 * @param corePoolSize 核心线程池
 * @param maximumPoolSize 线程池最大数量
 * @param keepAliveTime 线程存活时间
 * @param unit TimeUnit
 * @param workQueue 工作队列,自定义大小
 * @param poolName 线程工厂自定义线程名称
 */
    public CustomThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }
}
