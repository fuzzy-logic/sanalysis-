package com.sky.techfutures.sentanal.utils;

import junit.framework.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by gawain on 16/05/2014.
 */
public class StringUtilsTest {

    @Test
    public void testRemoveSpecialChars() {



        String text = "0123456789abcdefghijklmnopqrstuvwxyz,.?\"': _<>\\{}[]=-_+)(*&^%$£@!";


        String response = StringUtils.stripForAnalysis(text);
        System.out.println("response: '" + response + "'");
        assertEquals(response, "0123456789abcdefghijklmnopqrstuvwxyz,.?\"': ");
    }
}
