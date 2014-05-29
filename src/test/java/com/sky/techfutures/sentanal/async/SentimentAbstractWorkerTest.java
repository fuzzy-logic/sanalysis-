package com.sky.techfutures.sentanal.async;

import com.sky.techfutures.sentanal.api.SentimentReport;
import com.sky.techfutures.sentanal.async.AsyncWorker;
import com.sky.techfutures.sentanal.domain.service.SentimentComputeDomainService;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;

import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

/**
 * Created by gawain on 29/05/2014.
 */
public class SentimentAbstractWorkerTest {

    @Test
    public void testSentimentWorker() {

        Deque<Request> workq = new ConcurrentLinkedDeque<Request>();
        Map<String, Response> finishq = Collections.synchronizedMap(new HashMap());
        final SentimentComputeDomainService compute = mock(SentimentComputeDomainService.class);

        when(compute.analyse(anyString())).thenReturn(new SentimentReport());
        when(compute.analyse(anyString())).thenReturn(new SentimentReport());
        Command command = new Command() {
            public Object execute(Object input) {
                return compute.analyse((String) input);
            }
        };

        AsyncWorker sentimentWorker1 = new AsyncWorker(1, command, workq, finishq);

        Thread thread = new Thread(sentimentWorker1);

        thread.start();


        Request req1 = new Request("hello!");
        Request req2 = new Request("goodbye!");
        Request req3 = new Request("goodnight!");

        workq.add(req1);
        workq.add(req2);
        workq.add(req3);

        boolean waiting = true;

        while (waiting) {
            if (finishq.size() == 3) {
                waiting = false;
            }
        }

        Object result1 = finishq.get(req1.id).response;
        Object result2 = finishq.get(req2.id).response;
        Object result3 = finishq.get(req3.id).response;

        sentimentWorker1.shutdown();

        assertEquals(SentimentReport.class, result1.getClass());
        assertEquals(SentimentReport.class, result2.getClass());
        assertEquals(SentimentReport.class, result3.getClass());


    }
}
