package com.sky.techfutures.sentanal.async;

import java.util.UUID;

/**
 * Created by gawain on 29/05/2014.
 */
public class Request<T> {



    String id;
    private T request;

    public Request(T request) {
        this.request = request;
        this.id = UUID.randomUUID().toString();
    }

    public T getRequest() {
        return request;
    }
}
