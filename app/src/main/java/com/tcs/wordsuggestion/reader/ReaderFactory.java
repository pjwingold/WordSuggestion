package com.tcs.wordsuggestion.reader;

/**
 * Returns the appropriate Reader based on {@link SourceType}
 */
public class ReaderFactory {
    public static SourceReader getReader(SourceType sourceType) throws SourceReaderException {
        switch (sourceType) {
            case SD_CARD:
                return new SDCardFileSourceReader();
            default:
                throw new SourceReaderException("Undefined source reader");
        }
    }

    public static SourceReader getReader(SourceType sourceType, String uri) throws SourceReaderException {
        switch (sourceType) {
            case SD_CARD:
                return new SDCardFileSourceReader(uri);
            default:
                throw new SourceReaderException("Undefined source reader");
        }
    }
}
