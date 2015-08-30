package com.tcs.wordsuggestion.parser;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Created by hans on 27-Aug-15.
 */
public class TCSWordParserTest {

    @Test
    public void testParseCSV() throws Exception {
        List<String> results = TCSWordParser.parseCSV("he", "hello, helium, height, horse, flower, florence, where, sheet");
        Assert.assertNotNull(results);

        results = TCSWordParser.parseCSV("he", "hello,helium, height, horse, flower, florence,where, sheet, heat,athen");
        Assert.assertNotNull(results);
    }
}