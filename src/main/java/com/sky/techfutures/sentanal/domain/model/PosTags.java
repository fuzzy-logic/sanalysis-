package com.sky.techfutures.sentanal.domain.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum PosTags {
    VERBS("VB:VBG:VBN"),
    ADJECTIVES("JJ:JJR:JJS"),
    NOUNS("NN:NNS:NNP:NNPS");


    List<String> posTags = new ArrayList<String>();

    PosTags(String tags) {
        posTags.addAll(Arrays.asList(tags.split(":")));
    }


    public boolean matchesOneOf(String ... tags) {
        for (String tag : tags) {
           if (posTags.contains(tag) ) {
               //println("$this contains $tag")
               return  true;
           }
        }
        return false;
    }



}