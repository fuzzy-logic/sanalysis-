package com.sky.techfutures.sentanal;


import com.sky.techfutures.sentanal.health.SentimentResourceHealthCheck;
import com.sky.techfutures.sentanal.resources.SentimentResource;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.config.HttpConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SentimentService extends Service<SentimentConfiguration>   {

    static final Logger LOG = LoggerFactory.getLogger(SentimentService.class);

    public static void main(String[] args) throws Exception {

        new SentimentService().run(args);
    }

    @Override
    public void initialize(Bootstrap<SentimentConfiguration> bootstrap) {
        LOG.info("initialize()");
        bootstrap.setName("sentanal");
    }

    @Override
    public void run(SentimentConfiguration configuration, Environment environment) {
        final String serviceName = configuration.getServiceName();

        HttpConfiguration httpConfig = new HttpConfiguration();
        httpConfig.setPort(8080);
        httpConfig.setAdminPort(8081);
        configuration.setHttpConfiguration(httpConfig);

        int port = configuration.getHttpConfiguration().getPort();
        int adminPort = configuration.getHttpConfiguration().getAdminPort();

        LOG.info("run() serviceName: " + serviceName);
        LOG.info("run() http port: " + port);
        LOG.info("run() http admin port: " + adminPort);
        LOG.info("run() adding SentimentResource and healthcheck");
        SentimentResource sentimentResource = new SentimentResource();
        environment.addResource(sentimentResource);

        environment.addHealthCheck(new SentimentResourceHealthCheck(sentimentResource));
    }

}
