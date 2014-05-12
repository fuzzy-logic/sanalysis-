package com.sky.techfutures.sentanal.domain.service;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public abstract class ResourcePool<R> {
    private final BlockingQueue pool;
    private final ReentrantLock lock = new ReentrantLock();
    private int createdObjects = 0;
    private int size;
 
    protected ResourcePool(int size) {
        this(size, false);
    }
 
    protected ResourcePool(int size, Boolean dynamicCreation) {
        // Enable the fairness; otherwise, some threads
        // may wait forever.
        pool = new ArrayBlockingQueue(size, true);
        this.size = size;
        if (!dynamicCreation) {
            lock.lock();
        }
    }
 
    public R acquire() throws Exception {
        if (!lock.isLocked()) {
            if (lock.tryLock()) {
                try {
                    ++createdObjects;
                    return createObject();
                } finally {
                    if (createdObjects < size) lock.unlock();
                }
            }
        }
        return (R)  pool.take();
    }
 
    public void recycle(R resource) throws Exception {
        // Will throws Exception when the queue is full,
        // but it should never happen.
        int maxCounter = 100;
        int counter = 0;
        while (pool.size() >= size && counter < maxCounter) {
            Thread.sleep(100);
            counter++;
        }
        pool.add(resource);
    }
 
    public void createPool() {
        if (lock.isLocked()) {
            for (int i = 0; i < size; ++i) {
                pool.add(createObject());
                createdObjects++;
            }
        }
    }
 
    protected abstract R createObject();
}