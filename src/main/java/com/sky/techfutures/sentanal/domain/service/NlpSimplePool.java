package com.sky.techfutures.sentanal.domain.service;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.util.Properties;

/**
 * Created by gawain on 12/05/2014.
 */
public class NlpSimplePool extends SimplePool<StanfordCoreNLP> {


    NlpSimplePool() {
        super(100);
    }
    @Override
    protected StanfordCoreNLP createObject() {
        return new StanfordCoreNLP(getProps());
    }

    static Properties getProps() {
        java.util.Properties p = new Properties();
        p.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref, sentiment");
        return p;
    }
}
