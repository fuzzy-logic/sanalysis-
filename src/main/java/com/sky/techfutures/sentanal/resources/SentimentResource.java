package com.sky.techfutures.sentanal.resources;

import com.sky.techfutures.sentanal.api.InputText;
import com.sky.techfutures.sentanal.api.SentimentReport;
import com.sky.techfutures.sentanal.domain.service.SentimentComputeDomainService;
import com.yammer.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;


@Path("/api/sentiment")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SentimentResource {

    static final Logger LOG = LoggerFactory.getLogger(SentimentResource.class);
    SentimentComputeDomainService sentimentComputeDomainService = new SentimentComputeDomainService();

    @POST
    @Timed
    public SentimentReport computeSentiment( @Valid InputText inputText) {
        LOG.info("computeSentiment() inputText: " + inputText);
        SentimentReport sentimentReport = sentimentComputeDomainService.analyse(inputText.getText());
        return sentimentReport;
    }


}