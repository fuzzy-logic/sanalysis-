package com.sky.techfutures.sentanal.domain.service;

import com.sky.techfutures.sentanal.api.SentimentReport;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

import static org.junit.Assert.*;

public class SentimentComputeDomainServiceTest {



    static final Logger LOG = LoggerFactory.getLogger(SentimentComputeDomainServiceTest.class);

    final String testText = "A green baby caterpillar hatches from an egg, and from birth he experiences a perpetual craving for food. He eats through fruits on five days, one piece on the first, two on the second, and so on up to five, then experiments with a wider variety of foods. Soon enough he overdoes it and nauseates himself. After recovering he spins a cocoon in which he remains for the following two weeks. Later, the caterpillar emerges as a bright, colorful butterfly with large, gorgeous, multi-colored wings.";

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
        SentimentReport report = sentimentComputeDomainService.analyse(testText);
        LOG.info("overall sentiment: " + report.getAggregateSentiment());
        LOG.info("per sentence sentiment: " + report.getSentenceSentiment());
        LOG.info("nouns: " + report.getNouns());
        LOG.info("adjectives: " + report.getAdjectives());
        LOG.info("verbs: " + report.getVerbs());
        LOG.info("Named entities: " + report.getNamedEntities());

        Integer sentence1sentiment = -1;
        Integer sentence2sentiment = 1;
        Integer sentence3sentiment = 1;
        Integer sentence4sentiment = -1;
        Integer sentence5sentiment = 1;
        Integer expectedTotalSentiment = 1;

        assertEquals(expectedTotalSentiment, report.getAggregateSentiment() );
        assertEquals(Arrays.asList(sentence1sentiment, sentence2sentiment, sentence3sentiment, sentence4sentiment, sentence5sentiment), report.getSentenceSentiment() );

    }

}
