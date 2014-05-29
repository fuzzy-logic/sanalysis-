package com.sky.techfutures.sentanal.async;

/**
 * Created by gawain on 29/05/2014.
 */
public class Response {

    String id;
    Object response;

    public Response(Request request, Object response) {
        this.id = request.id;
        this.response = response;
    }
}
