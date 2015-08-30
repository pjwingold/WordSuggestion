package com.tcs.wordsuggestion.reader;

import java.io.IOException;
import java.io.InputStream;

/**
 *
 */
public abstract class SourceReader {
    protected String uri;
    protected String fileName;

    public SourceReader() {
    }

    public SourceReader(String uri) {
        this.uri = uri;
    }

    public abstract InputStream readSource() throws IOException;

    public String getUri() {
        return uri;
    }
}
