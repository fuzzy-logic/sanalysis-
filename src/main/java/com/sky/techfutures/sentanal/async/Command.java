package com.sky.techfutures.sentanal.async;

/**
 * Created by gawain on 29/05/2014.
 */
public interface Command<I, O> {

    public O execute(I input);
}
