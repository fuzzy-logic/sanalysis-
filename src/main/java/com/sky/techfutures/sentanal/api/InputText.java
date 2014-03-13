package com.sky.techfutures.sentanal.api;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by gawain on 13/03/2014.
 */
public class InputText {

    @JsonProperty
    private String text;



    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "InputText{" + "text='" + text + '\'' + '}';
    }
}
