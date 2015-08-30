package com.tcs.wordsuggestion.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.tcs.wordsuggestion.aidl.IParserService;
import com.tcs.wordsuggestion.aidl.ParseResult;
import com.tcs.wordsuggestion.common.TCSApplication;
import com.tcs.wordsuggestion.parser.TCSWordParser;
import com.tcs.wordsuggestion.parser.entity.WordParseResult;

import java.util.ArrayList;
import java.util.List;

/**
 * AIDL service to be run in the background
 *
 */
public class ParserService extends Service {
    private ParserBinder mBinder;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        mBinder = new ParserBinder();
        return mBinder;
    }

    class ParserBinder extends IParserService.Stub {
        @Override
        public List<ParseResult> parseCSV(String match) throws RemoteException {
            String source = TCSApplication.getSource();
            List<WordParseResult> results = TCSWordParser.parseCSV(match, source);
            List<ParseResult> data = new ArrayList<>(results.size());

            for (WordParseResult result : results) {
                data.add(new ParseResult(result.getResult()));
            }

            return data;
        }
    }
}
