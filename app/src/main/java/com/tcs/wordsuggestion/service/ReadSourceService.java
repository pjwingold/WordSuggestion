package com.tcs.wordsuggestion.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

import com.tcs.wordsuggestion.common.TCSApplication;
import com.tcs.wordsuggestion.reader.ReaderFactory;
import com.tcs.wordsuggestion.reader.SourceReader;
import com.tcs.wordsuggestion.reader.SourceReaderException;
import com.tcs.wordsuggestion.reader.SourceType;
import com.tcs.wordsuggestion.utils.Constants;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * An intent service that loads data from source, this also enables a switch to network source in future
 *
 */
public class ReadSourceService extends IntentService {
    public static final String SERVICE = "readsource_service";
    public static final String SERVICE_RESULT = "service_result";
    public static final int SUCCESS = 0;
    public static final int FAIL = -1;

    public ReadSourceService() {
        super(SERVICE);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        ResultReceiver receiver = intent.getParcelableExtra(SERVICE);
        Bundle bundle = new Bundle();
        int resultCode = SUCCESS;

        try {
            refreshSource();
        } catch (IOException e) {
            bundle.putString(SERVICE_RESULT, "Unable to read data from source");
            resultCode = FAIL;
        } catch (SourceReaderException e) {
            bundle.putString(SERVICE_RESULT, e.getLocalizedMessage());
            resultCode = FAIL;
        }

        receiver.send(resultCode, bundle);
    }

    private void refreshSource() throws IOException, SourceReaderException {
        SourceReader reader = ReaderFactory.getReader(SourceType.SD_CARD);
        InputStream stream = reader.readSource();
        String source = IOUtils.toString(stream, Constants.ENCODE);//apache common library, convert from stream to string
        TCSApplication.setSource(source);
    }
}
