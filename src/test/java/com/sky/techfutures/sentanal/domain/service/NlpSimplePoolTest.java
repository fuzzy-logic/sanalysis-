package com.sky.techfutures.sentanal.domain.service;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by gawain on 12/05/2014.
 */
public class NlpSimplePoolTest {

    Object o1;
    Object o2;
    Object o3;
    Object o4;
    Object o5;
    Object o6;

    boolean testFinished = false;

    AtomicInteger callbackCounter = new AtomicInteger(1);


    @Before
    public void setup() {
        System.out.println("resetting all test data!");
        callbackCounter.set(1);
        testFinished = false;

        Object o1 = null;
        Object o2 = null;
        Object o3 = null;
        Object o4 = null;
        Object o5 = null;
        Object o6 = null;
    }


    @Test
    public void testPoolsize1() throws Exception {


        SimplePool pipelinePool = new TestSimplePool(1);

        o1 = pipelinePool.get();
        Thread.sleep(100);
        o2 = pipelinePool.get();
        Thread.sleep(100);
        o3 = pipelinePool.get();
        Thread.sleep(100);
        o4 = pipelinePool.get();
        Thread.sleep(100);
        o5 = pipelinePool.get();
        Thread.sleep(100);
        o6 = pipelinePool.get();

        assertTest1();




    }

    @Test
    public void testPoolsize2() throws Exception {

        SimplePool pipelinePool = new TestSimplePool(2);

        o1 = pipelinePool.get();
        Thread.sleep(100);
        o2 = pipelinePool.get();
        Thread.sleep(100);
        o3 = pipelinePool.get();
        Thread.sleep(100);
        o4 = pipelinePool.get();
        Thread.sleep(100);
        o5 = pipelinePool.get();
        Thread.sleep(100);
        o6 = pipelinePool.get();

        assertTest2();




    }

    @Test
    public void testPoolsize3() throws Exception {


        SimplePool pipelinePool = new TestSimplePool(3);

        o1 = pipelinePool.get();
        Thread.sleep(100);
        o2 = pipelinePool.get();
        Thread.sleep(100);
        o3 = pipelinePool.get();
        Thread.sleep(100);
        o4 = pipelinePool.get();
        Thread.sleep(100);
        o5 = pipelinePool.get();
        Thread.sleep(100);
        o6 = pipelinePool.get();

        assertTest3();




    }

    private void assertTest1() {

        assert o1 != o2;
        assert o1 != o3;
        assert o1 != o4;
        assert o1 != o5;
        assert o1 != o6;

        assert o2 != o1;
        assert o2 != o3;
        assert o2 != o4;
        assert o2 != o5;
        assert o2 != o6;

        assert o3 != o1;
        assert o3 != o2;
        assert o3 != o4;
        assert o3 != o5;
        assert o3 != o6;

        assert o4 != o1;
        assert o4 != o2;
        assert o4 != o3;
        assert o4 != o5;
        assert o4 != o6;

        assert o5 != o1;
        assert o5 != o2;
        assert o5 != o3;
        assert o5 != o4;
        assert o5 != o6;


        System.out.println("passed all assertion for pool size = 1");
    }

    private void assertTest2() {

        assert o1 == o2;
        assert o1 != o3;
        assert o1 != o4;
        assert o1 != o5;
        assert o1 != o6;

        assert o2 == o1;
        assert o2 != o3;
        assert o2 != o4;
        assert o2 != o5;
        assert o2 != o6;

        assert o3 != o1;
        assert o3 != o2;
        assert o3 == o4;
        assert o3 != o5;
        assert o3 != o6;

        assert o4 != o1;
        assert o4 != o2;
        assert o4 == o3;
        assert o4 != o5;
        assert o4 != o6;

        assert o5 != o1;
        assert o5 != o2;
        assert o5 != o3;
        assert o5 != o4;
        assert o5 == o6;


        System.out.println("passed all assertion for pool size = 2");
    }

    private void assertTest3() {

        assert o1 == o2;
        assert o1 == o3;
        assert o1 != o4;
        assert o1 != o5;
        assert o1 != o6;

        assert o2 == o1;
        assert o2 == o3;
        assert o2 != o4;
        assert o2 != o5;
        assert o2 != o6;

        assert o3 == o1;
        assert o3 == o2;
        assert o3 != o4;
        assert o3 != o5;
        assert o3 != o6;

        assert o4 != o1;
        assert o4 != o2;
        assert o4 != o3;
        assert o4 == o5;
        assert o4 == o6;

        assert o5 != o1;
        assert o5 != o2;
        assert o5 != o3;
        assert o5 == o4;
        assert o5 == o6;

        System.out.println("passed all assertion for pool size = 3");
    }

    private class TestSimplePool extends SimplePool<Object> {
        TestSimplePool(int i) {
            super(i);
        }

        @Override
        protected Object createObject() {
            //System.out.println("createObject()");
            return new Object();
        }
    }
}
