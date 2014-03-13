package com.sky.techfutures.sentanal.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by gawain on 13/03/2014.
 */
public class SentimentReport {

    @JsonProperty String text;
    @JsonProperty Integer aggregateSentiment;
    @JsonProperty List<Integer> sentenceSentiment;
    @JsonProperty Collection<String> nouns = new ArrayList<String>();
    @JsonProperty Collection<String> adjectives = new ArrayList<String>();
    @JsonProperty Collection<String> verbs = new ArrayList<String>();
    @JsonProperty Collection<String> namedEntities = new ArrayList<String>();

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getAggregateSentiment() {
        return aggregateSentiment;
    }

    public void setAggregateSentiment(Integer aggregateSentiment) {
        this.aggregateSentiment = aggregateSentiment;
    }

    public List<Integer> getSentenceSentiment() {
        return sentenceSentiment;
    }

    public void setSentenceSentiment(List<Integer> sentenceSentiment) {
        this.sentenceSentiment = sentenceSentiment;
    }

    public Collection<String> getNouns() {
        return nouns;
    }

    public void setNouns(Collection<String> nouns) {
        this.nouns = nouns;
    }

    public Collection<String> getAdjectives() {
        return adjectives;
    }

    public void setAdjectives(Collection<String> adjectives) {
        this.adjectives = adjectives;
    }

    public Collection<String> getVerbs() {
        return verbs;
    }

    public void setVerbs(Collection<String> verbs) {
        this.verbs = verbs;
    }

    public Collection<String> getNamedEntities() {
        return namedEntities;
    }

    public void setNamedEntities(Collection<String> namedEntities) {
        this.namedEntities = namedEntities;
    }

    @Override
    public String toString() {
        return "SentimentReport{" +
                "aggregateSentiment=" + aggregateSentiment +
                '}';
    }
}
