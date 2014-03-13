package com.sky.techfutures.sentanal.domain.service;

import com.sky.techfutures.sentanal.api.SentimentReport;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

public class SentimentComputeDomainServiceTest {



    static final Logger LOG = LoggerFactory.getLogger(SentimentComputeDomainServiceTest.class);

    SentimentComputeDomainService  sentimentComputeDomainService;

    @Before
    public void setup() throws Exception {
        LOG.info("setup() creating new SentimentComputeDomainService()");
        sentimentComputeDomainService = new SentimentComputeDomainService();
        LOG.info("setup() SentimentComputeDomainService created.");
    }

    @Test
    public void testComputeSentiment() throws Exception {
        LOG.info("running test testComputeSentiment()");
        SentimentReport report = sentimentComputeDomainService.analyse("The film was neither witty not funny. In fact I believe it was the worst film I've ever seen. The acting was terrible, the plot was dull and the photography was uninspiring.");
        LOG.info("overall sentiment: " + report.getAggregateSentiment());
        LOG.info("per sentence sentiment: " + report.getSentenceSentiment());
        LOG.info("nouns: " + report.getNouns());
        LOG.info("adjectives: " + report.getAdjectives());
        LOG.info("verbs: " + report.getVerbs());
        LOG.info("Named entities: " + report.getNamedEntities());
        assertEquals(new Integer(2), report.getAggregateSentiment() );
    }

}
