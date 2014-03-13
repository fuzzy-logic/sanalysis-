package com.sky.techfutures.sentanal;

import com.yammer.dropwizard.config.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

public class SentimentConfiguration extends Configuration {

    @NotEmpty
    @JsonProperty
    private String serviceName;

    public SentimentConfiguration(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceName() {
        return serviceName;
    }
}
