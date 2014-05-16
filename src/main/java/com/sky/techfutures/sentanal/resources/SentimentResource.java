package com.sky.techfutures.sentanal.resources;

import com.google.common.base.Stopwatch;
import com.sky.techfutures.sentanal.api.InputText;
import com.sky.techfutures.sentanal.api.SentimentReport;
import com.sky.techfutures.sentanal.domain.service.SentimentComputeDomainService;
import com.yammer.metrics.annotation.Timed;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;


@Path("/api/sentiment")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SentimentResource {

    static final Logger LOG = LoggerFactory.getLogger(SentimentResource.class);
    SentimentComputeDomainService sentimentComputeDomainService = new SentimentComputeDomainService();

    @POST
    @Timed
    public SentimentReport computeSentiment( @Valid InputText inputText) {
        String logFriendlyString = getLogFriendlyString(inputText);
        Stopwatch stopwatch = new Stopwatch();
        stopwatch.start();
        try {
            //long startTime = System.nanoTime();
            SentimentReport sentimentReport = sentimentComputeDomainService.analyse(inputText.getText());
            //long endTime = System.nanoTime();
            //long totalTime = endTime - startTime;
            //long printTime = new TimeUnit().convert(totalTime, TimeUnit.MILLISECONDS);
            stopwatch.stop();
            LOG.info("Computed aggregate sentiment of " + sentimentReport.getAggregateSentiment() + " in " + stopwatch.elapsedMillis() + "ms for input text '" + logFriendlyString + "'");
            return sentimentReport;

        } catch (Exception e) {
            LOG.error("Exception while calculating sentiment for input text '" + logFriendlyString + "'", e);
            return null;
        }

    }

    private String getLogFriendlyString(InputText inputText) {
        String logString = inputText.getText();
        logString = logString.replaceAll("\n", "");
        int logLength = 100;
        if (logString.length() <= 100) logLength = logString.length();
        return logString.substring(0, logLength);
    }


}