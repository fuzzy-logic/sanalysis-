package com.sky.techfutures.sentanal.domain.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by gawain on 12/05/2014.
 */
public abstract class SimplePool<R> {

    static final Logger LOG = LoggerFactory.getLogger(SimplePool.class);


    public SimplePool() {
        resource = createResource();
    }

    public SimplePool(int maxsize) {
        this();
        this.maxsize = maxsize;
    }

    public SimplePool(int maxsize, Callback callback) {
        this(maxsize);
       this.callback = callback;
    }



    private Callback callback;
    private R resource;
    AtomicInteger counter = new AtomicInteger(0);
    AtomicBoolean performingReInit = new AtomicBoolean(true);
    int maxsize = 100;

    public R get() {
        //LOG.debug("get() counter/maxsize:" + counter.get() + "/" + maxsize);
        reInit();
        return getOrSet(null);
    }

    private synchronized R getOrSet(R newresource) {
        if (newresource != null) {
            resource = newresource;
            counter.set(1);
            //LOG.debug("getOrSet() setting resource=" + resource);
        } else {
            //LOG.debug("getOrSet() returning resource=" + resource);
        }

        return resource;
    }


    public void reInit() {
        if (counter.get() < maxsize) {
            //LOG.debug("counter not at maxsize, carry on");
            counter.incrementAndGet();
            return;
        } else if (! performingReInit.get()) {
            //LOG.debug("Creating aysnc task...");
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    LOG.debug("Aysnc task replacing resource with new object ...");
                    R newResource = createResource();
                    getOrSet(newResource);
                }
            };
            new Thread(task).run();
        } else {
            //LOG.debug("Counter at max but reinit flag = " + performingReInit.get());
        }



    }

    private R createResource() {
        R object = createObject();
        performingReInit.set(false);
        LOG.debug("Pool created new object: " + object);
        if (callback != null) callback.call();
        return object;
    }

    protected abstract R createObject();

    public interface Callback {
        public void call();
    }
}
