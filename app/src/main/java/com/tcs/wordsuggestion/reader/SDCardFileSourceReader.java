package com.tcs.wordsuggestion.reader;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Read file from sd card
 */
public class SDCardFileSourceReader extends SourceReader {
    public SDCardFileSourceReader() {
    }

    public SDCardFileSourceReader(String uri) {
        super(uri);
    }

    @Override
    public InputStream readSource() throws IOException {
        if (uri == null) {
            uri = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "source.csv";
        }
        File file = new File(uri);

        return new FileInputStream(file);
    }
}
