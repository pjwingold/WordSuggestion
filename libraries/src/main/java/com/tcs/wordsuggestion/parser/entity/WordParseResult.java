package com.tcs.wordsuggestion.parser.entity;

/**
 * Future proof, in case more fields need to be returned
 *
 * Created by hans on 28-Aug-15.
 */
public class WordParseResult {
    private String result;

    public WordParseResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
