package com.sky.techfutures.sentanal.domain.model;

/**
 * enums and calculations for sentiment
 */
public  enum Sentiment {
    VERY_NEGATIVE(-3),
    NEGATIVE(-1),
    NEUTRAL(0),
    POSITIVE(1),
    VERY_POSITIVE(3);

    final int val;

    Sentiment(int i) {
        val = i;
    }

    static Sentiment forInt(int i) {
        if (i > 3) return Sentiment.VERY_POSITIVE;
        if (i > 0) return Sentiment.POSITIVE;
        if (i < -3) return Sentiment.VERY_NEGATIVE;
        if (i < 0) return Sentiment.NEGATIVE;
        return Sentiment.NEUTRAL;
    }
}