package com.example.demo.entity;



import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j(topic = "c.BlockQueue")
public class BlockQueue<T> {

    private int capacity;
    private Deque<T> deque = new ArrayDeque<>();

    private ReentrantLock lock = new ReentrantLock();
    private Condition emptyWaitSet = lock.newCondition();
    private Condition fullWaitSet = lock.newCondition();

    public BlockQueue(int capacity){
        this.capacity = capacity;
    }


    /**
     * 从队列中获取数据，带超时时间
     * @param timeout
     * @param timeUnit
     * @return
     */
    public T poll(long timeout, TimeUnit timeUnit){
        lock.lock();
        try {
            long nanos = timeUnit.toNanos(timeout);
            while (deque.isEmpty()){
                try {
                    if(nanos<=0) return null;
                    nanos = emptyWaitSet.awaitNanos(nanos); //返回剩余需要等待的时间
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            T t = deque.removeFirst();
            log.info("poll 返回{}",t);
            fullWaitSet.signal();
            return t;
        }
        finally {
            lock.unlock();
        }
    }
    /**
     * 从队列中获取数据，不带超时时间，如果队列中一直没有数据，会死等[一直等]下去
     * @return
     */
    public T take(){
        lock.lock();
        try {
            while (deque.isEmpty()){
                try{
                    emptyWaitSet.await();//队列数据为空，需要等待其他线程向队列中存放数据
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            T t = deque.removeFirst();
            log.info("take 返回 {}",t);
            fullWaitSet.signal();
            return t;
        }finally {
            lock.unlock();
        }
    }
    /**
     * 存放数据，如果队列满了会一直死等下去
     * @param t
     */
    public void put(T t){
        lock.lock();
        try{
            while (deque.size()==capacity){
                try {
                    log.info("put 等待加入 {}",t);
                    fullWaitSet.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.info("put {}",t);
            deque.addLast(t);
            emptyWaitSet.signal();
        }finally {
            lock.unlock();
        }
    }
    /**
     * 存放数据，如果超过了一定时间则进入下一次循环
     * @param t
     */
    public boolean offer(T t,long timeout,TimeUnit timeUnit){
        lock.lock();
        try{
            long nanos = timeUnit.toNanos(timeout);
            while (deque.size()==capacity){
                try {
                    if(nanos<=0) return false;
                    log.info("offer 等待加入 {}",t);
                    nanos = fullWaitSet.awaitNanos(nanos);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            deque.addLast(t);
            log.info("offer {}",t);
            emptyWaitSet.signal();
            return true;
        }finally {
            lock.unlock();
        }
    }

    /**
     * 队列满了后怎么做，让用户自己选择
     * @param rejectPolicy
     * @param t
     */
    public void tryPut(RejectPolicy<T> rejectPolicy,T t){
        lock.lock();
        try {
            if(deque.size()==capacity){
                rejectPolicy.reject(this,t);
            }else{
                log.info("put {}", t);
                deque.addLast(t);
                emptyWaitSet.signal();
            }
        }finally {
            lock.unlock();
        }
    }
}
