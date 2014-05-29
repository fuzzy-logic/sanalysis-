package com.sky.techfutures.sentanal.async;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by gawain on 29/05/2014.
 */
public class AsyncMasterRequestHandlerTest {

    @Test
    public void testSentimentRequestHandlerAsync() {

        Command<String, String> command = new Command<String, String>() {
            public String execute(String input) {
                //System.out.println("Command processing " + input);
                return input + "_processed_by_command";
            }
        };

        final AsyncMasterRequestHandler handler = new AsyncMasterRequestHandler(command);


        int total = 1000;
        final List responses = Collections.synchronizedList(new ArrayList<Thread>());
        for (int i = 0 ; i < total ; i++) {

            final int count = i;
            Runnable r = new Runnable() {
                public void run() {
                    Object res1 = handler.processRequest("hello" + count + "!");
                    responses.add(res1);
                }
            };
            new Thread(r).start();
        }


        boolean waiting = true;
        while(waiting) {
            if (responses.size() >= total ) {
                waiting = false;
            }
        }


       //sleep();

       for (Object response : responses) {
           System.out.println("asserting response: " + response);
           assertTrue(response.toString().startsWith("hello"));
           assertTrue(response.toString().endsWith("_processed_by_command"));
       }



    }

    @Test
    public void testSentimentRequestHandlerSync() {

        Command<String, String> command = new Command<String, String>() {
            public String execute(String input) {
                //System.out.println("Command processing " + input);
                return input + "_processed_by_command";
            }
        };

        final AsyncMasterRequestHandler handler = new AsyncMasterRequestHandler(command);



        Object res1 = handler.processRequest("hello1!");
        Object res2 = handler.processRequest("hello2!");
        Object res3 = handler.processRequest("hello3!");


        assertTrue(res1.toString().startsWith("hello1"));
        assertTrue(res1.toString().endsWith("_processed_by_command"));

        assertTrue(res2.toString().startsWith("hello2"));
        assertTrue(res2.toString().endsWith("_processed_by_command"));

        assertTrue(res3.toString().startsWith("hello3"));
        assertTrue(res3.toString().endsWith("_processed_by_command"));




    }




}
