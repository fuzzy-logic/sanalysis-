package com.sky.techfutures.sentanal.domain.service;

import com.sky.techfutures.sentanal.api.SentimentReport;
import com.sky.techfutures.sentanal.domain.model.PosTags;
import com.sky.techfutures.sentanal.domain.model.Sentiment;
import com.sky.techfutures.sentanal.utils.StringUtils;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.trees.Tree;

import edu.stanford.nlp.ling.CoreAnnotations;

import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/*

CC Coordinating conjunction
CD Cardinal number
DT Determiner
EX Existential there
FW Foreign word
IN Preposition or subordinating conjunction
JJ Adjective
JJR Adjective, comparative
JJS Adjective, superlative
LS List item marker
MD Modal
NN Noun, singular or mass
NNS Noun, plural
NNP Proper noun, singular
NNPS Proper noun, plural
PDT Predeterminer
POS Possessive ending
PRP Personal pronoun
PRP$ Possessive pronoun
RB Adverb
RBR Adverb, comparative
RBS Adverb, superlative
RP Particle
SYM Symbol
TO to
UH Interjection
VB Verb, base form
VBD Verb, past tense
VBG Verb, gerund or present participle
VBN Verb, past participle
VBP Verb, non­3rd person singular present
VBZ Verb, 3rd person singular present
WDT Wh­determiner
WP Wh­pronoun
WP$ Possessive wh­pronoun
WRB Wh­adverb


 */


public class SentimentComputeDomainService {


    static final Logger LOG = LoggerFactory.getLogger(SentimentComputeDomainService.class);


    NlpSimplePool pipelinePool = new NlpSimplePool();


    public SentimentReport analyse(String text) {

        text = StringUtils.stripForAnalysis(text);

        Annotation document = new Annotation(text);

        //LOG.info("sharedPipeline.annotate(document)");
        // run all Annotators on this text
        pipelinePool.get().annotate(document);

        // these are all the sentences in this document
        // a CoreMap is essentially a Map that uses class objects as keys and has values with custom types
        //LOG.info("Creating sentence tree object...");
        List<CoreMap> sentenceTree = document.get(CoreAnnotations.SentencesAnnotation.class);

        //LOG.info("generating sentence tree...");
        Collection<String> standfordNlpSentiments = new ArrayList<String>();
        for (CoreMap sentence : sentenceTree) {
            //Tree tree = sentence.get(SentimentCoreAnnotations.AnnotatedTree.class);
            String result = sentence.get(SentimentCoreAnnotations.ClassName.class);
            standfordNlpSentiments.add(result);
        }
        //LOG.info("generating sentence sentiment...");
        List<Sentiment> results  = convertSentiment(standfordNlpSentiments);
        List<Integer> sentenceSentiment = new ArrayList<Integer>();
        Integer totalSentiment = 0;
        for (Sentiment sentiment : results) {
            totalSentiment += sentiment.getVal();
            sentenceSentiment.add(sentiment.getVal());
        }


        Collection<String> namedEntities = getNamedEntities(sentenceTree);
        Collection<String>  nouns = getNouns(sentenceTree);
        Collection<String>  verbs = getVerbs(sentenceTree);
        Collection<String>  adjectives = getAdjectives(sentenceTree);

        //LOG.info("analyse() totalSentiment: " + totalSentiment );
        //LOG.info("analyse() namedEntities: " + namedEntities );
        //LOG.info("analyse() nouns: " + nouns );
        //LOG.info("analyse() verbs: " + verbs);
        //LOG.info("analyse() adjectives: " + adjectives);


        SentimentReport computedSentiment = new SentimentReport();
        if (text.length() > 100) computedSentiment.setText(text.substring(0, 100)+  ".....");
        computedSentiment.setSentenceSentiment(sentenceSentiment);
        computedSentiment.setAggregateSentiment(totalSentiment);
        if (! namedEntities.isEmpty()) computedSentiment.setNamedEntities(namedEntities);
        if (! nouns.isEmpty()) computedSentiment.setNouns(nouns);
        if (! verbs.isEmpty()) computedSentiment.setVerbs(verbs);
        if (! adjectives.isEmpty()) computedSentiment.setAdjectives(adjectives);

        return computedSentiment;
    }










    public static List<Sentiment> convertSentiment(Collection<String> computedSentiments) {
        List<Sentiment> results = new ArrayList<Sentiment>();

        for (String it : computedSentiments) {
            Sentiment sentiment = Sentiment.valueOf(Sentiment.class, it.replace(" ", "_").toUpperCase());
            results.add( sentiment );
        }

        return results;
    }



    Collection<String> getNamedEntities(List<CoreMap> sentenceTree) {

        Collection<String>  returnWords = new HashSet<String>();
        for(CoreMap sentence: sentenceTree) {
            //println("sentence='$sentence'");
            // traversing the words in the current sentence
            // a CoreLabel is a CoreMap with additional token-specific methods
            for (CoreLabel token: sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                // this is the text of the token
                String word = token.get(CoreAnnotations.TextAnnotation.class);
                // this is the POS tag of the token
                String ne = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);
                //def nne = token.get(CoreAnnotations.NormalizedNamedEntityTagAnnotation.class)
                if (! ne.equals("O")) returnWords.add(word);

            }
        }
        return returnWords;
    }

    Collection<String> getNouns(List<CoreMap> sentenceTree) {
        Collection<String>  returnWords = new HashSet<String>();
        for(CoreMap sentence: sentenceTree) {
            // traversing the words in the current sentence
            // a CoreLabel is a CoreMap with additional token-specific methods
            for (CoreLabel token: sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                // this is the text of the token
                String word = token.get(CoreAnnotations.TextAnnotation.class);
                // this is the POS tag of the token
                String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                if (PosTags.NOUNS.matchesOneOf(pos)) returnWords.add(word);
            }

        }
        return returnWords;
    }


    /*
     VB Verb, base form
    VBD Verb, past tense
    VBG Verb, gerund or present participle
    VBN Verb, past participle
    VBP Verb, non­3rd person singular present
    VBZ Verb, 3rd person singular present
     */
    Collection<String> getVerbs(List<CoreMap> sentenceTree) {
        Collection<String>  returnWords = new HashSet<String>();
        for(CoreMap sentence: sentenceTree) {
            // traversing the words in the current sentence
            // a CoreLabel is a CoreMap with additional token-specific methods
            for (CoreLabel token: sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                // this is the text of the token
                String word = token.get(CoreAnnotations.TextAnnotation.class);
                // this is the POS tag of the token
                String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                if (PosTags.VERBS.matchesOneOf(pos)) returnWords.add(word);
            }

        }
        return returnWords;
    }

    Collection<String> getAdjectives(List<CoreMap> sentenceTree) {
        Collection<String>  returnWords = new HashSet<String>();
        for(CoreMap sentence: sentenceTree) {
            // traversing the words in the current sentence
            // a CoreLabel is a CoreMap with additional token-specific methods
            for (CoreLabel token: sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                // this is the text of the token
                String word = token.get(CoreAnnotations.TextAnnotation.class);
                // this is the POS tag of the token
                String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                if (PosTags.ADJECTIVES.matchesOneOf(pos)) returnWords.add(word);
            }

        }
        return returnWords;
    }








}