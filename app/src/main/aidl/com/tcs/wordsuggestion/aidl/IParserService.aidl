// IParserService.aidl
package com.tcs.wordsuggestion.aidl;

import java.util.List;
import com.tcs.wordsuggestion.aidl.ParseResult;

interface IParserService {
    List<ParseResult> parseCSV(String match);
}
