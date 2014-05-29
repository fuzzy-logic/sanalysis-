package com.sky.techfutures.sentanal.domain.service;

import com.sky.techfutures.sentanal.api.SentimentReport;

/**
 * Created by gawain on 29/05/2014.
 */
public interface SentimentAnalysis {

    public SentimentReport analyse(String text);
}
