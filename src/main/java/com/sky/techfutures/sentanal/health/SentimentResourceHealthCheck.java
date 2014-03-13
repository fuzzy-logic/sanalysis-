package com.sky.techfutures.sentanal.health;

import com.sky.techfutures.sentanal.resources.SentimentResource;
import com.yammer.metrics.core.HealthCheck;

/**
 * User: gawain
 * Date: 19/02/2014
 * Time: 10:39
 */
public class SentimentResourceHealthCheck extends HealthCheck {

    final SentimentResource sentimentResource;

    public SentimentResourceHealthCheck(SentimentResource sentimentResource) {
        super("/api/sentiment");
        this.sentimentResource = sentimentResource;
    }

    @Override
    protected Result check() throws Exception {
        //WordRange wordRange =  sentimentResource.getWordRange("skygo", Optional.of(5));
        //if (! wordRange.getWord().equals("skygo")) {
        //    return Result.unhealthy("word range not being generated");
       // }
       // return Result.healthy();
        return null;
    }
}