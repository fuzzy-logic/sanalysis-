package com.sky.techfutures.sentanal.async;

import com.sky.techfutures.sentanal.api.SentimentReport;
import com.sky.techfutures.sentanal.domain.service.SentimentAnalysis;
import com.sky.techfutures.sentanal.domain.service.SentimentComputeDomainService;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Created by gawain on 29/05/2014.
 */
public class AsyncMasterRequestHandler {


    Deque<Request> workq = new ConcurrentLinkedDeque<Request>();
    Queue<Response> finishq = new ConcurrentLinkedDeque<Response>();
    Map<String, Response> responseMap = Collections.synchronizedMap(new HashMap());
    Collection<Thread> workers =  Collections.synchronizedList(new ArrayList<Thread>());

    Command command;

    public AsyncMasterRequestHandler(Command command) {
        this.command = command;
        createWorkers();
    }

    private void createWorkers() {
        for (int i = 0 ; i < 4 ; i++) {
            Runnable worker = new AsyncWorker(i, command, workq, responseMap);
            Thread thread = new Thread(worker);
            thread.start();
            workers.add(thread);
        }
    }

    public Object processRequest(Object in) {
        Request request = new Request(in);
        workq.add(request);
        waitForResponse(request);
        Response response =  responseMap.remove(request.id);
        if (response == null) {
               return null;
        } else {
            //System.out.println("AsyncMasterRequestHandler returning response");
               return response.response;
        }
    }

    private void waitForResponse(Request request) {
        boolean waiting = true;
        int counter = 0;
        while(waiting) {
            Response response = responseMap.get(request.id);
            if (response != null || counter == 10000) {
                //System.out.println("waitForResponse() finished counter=" + counter + " response=" + response);
                waiting = false;
            }
            sleep();
            counter++;
        }
    }

    private void sleep() {
        try {
            Thread.sleep(10);
        } catch (Exception e) {

        }
    }

    public void shutdown() {
            for (Thread t : workers) {
                t.stop();
            }
    }

}
