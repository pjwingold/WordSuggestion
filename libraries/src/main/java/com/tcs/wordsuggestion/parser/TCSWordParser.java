package com.tcs.wordsuggestion.parser;

import com.tcs.wordsuggestion.parser.entity.WordParseResult;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 1. load source string such as hello, helium, height, horse, flower, florence, where, sheet
 * 2. do a like query (similar to sql like) with {@link StringTokenizer}
 * 3. reg exp match? add the word to a of {@link WordParseResult}
 */
public class TCSWordParser {
    private static final String ENCODE = "UTF-8";
    /**
     * Assume it is case in sensitive
     * @param match The search string
     * @param source The source string to be searched again
     * @param delim The delimit ie comma, space character
     * @return A list of {@link WordParseResult}
     */
    public static List<WordParseResult> parse(String match, String source, String delim) {
        //remove all space, newline, tab before setting up the tokenizer
        StringTokenizer tokenizer = new StringTokenizer(source.replaceAll("[\\n\\r]", ",").replaceAll("[\\s\\t]", ""), delim.replaceAll("\\s", ""));
        List<WordParseResult> results = new ArrayList<>(tokenizer.countTokens());

        while (tokenizer.hasMoreElements()) {
            String token = tokenizer.nextToken();
            if (token.toLowerCase().contains(match.toLowerCase())) {
                results.add(new WordParseResult(token));
            }
        }

        return results;//results.toArray(new String[results.size()]);
    }

    public static List<WordParseResult> parseCSV(String match, String source) {
        return parse(match, source, ",");
    }

    public static List<WordParseResult> parseCSV(String match, InputStream streamSource) {
        String source = "";

        try {
            source = IOUtils.toString(streamSource, ENCODE);//apache common library, convert from stream to string
        } catch (IOException e) {
            e.printStackTrace();
        }

        return parse(match, source, ",");
    }
}