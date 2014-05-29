package com.sky.techfutures.sentanal.async;

import com.sky.techfutures.sentanal.api.SentimentReport;
import com.sky.techfutures.sentanal.domain.service.SentimentAnalysis;

import java.util.Deque;
import java.util.Map;

/**
 * Created by gawain on 29/05/2014.
 */
public class AsyncWorker implements Runnable {

    Command command;
    Deque<Request> workQueue;
    Map<String, Response> finishedQueue;
    int myId;
    boolean running = true;

    public AsyncWorker(int id, Command command, Deque<Request> workQueue, Map<String, Response> finishedQueue) {
        this.command = command;
        this.workQueue = workQueue;
        this.myId = id;
        this.finishedQueue = finishedQueue;
    }

    private Request getNext() {
        Request o =  workQueue.pollFirst();
        if (o == null) {
            return null;
        }
        //System.out.println("Worker" + myId + " getting next item in work queue: " + o.getRequest());
        return o;
    }

    public void run() {
        while(running) {
            Request request = getNext();
            if (request == null) {
                continue;
            }
            Object input = request.getRequest();
            Object output =  command.execute(input);
            Response response = new Response(request, output);
            finishedQueue.put(request.id, response);
            sleep();
        }
    }

    public void shutdown() {
        running = false;
    }

    private void sleep() {

        try {
            Thread.sleep(10);
        } catch (Exception e) {

        }

    }

}
