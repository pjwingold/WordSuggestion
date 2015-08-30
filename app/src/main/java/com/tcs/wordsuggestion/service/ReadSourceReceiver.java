package com.tcs.wordsuggestion.service;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

/**
 * Deliver the result from ReadSourceService to calling the class
 *
 */
public class ReadSourceReceiver extends ResultReceiver {
    private OnSourceReceiveListener mCallback;

    public ReadSourceReceiver(Handler handler, OnSourceReceiveListener listener) {
        super(handler);
        try {
            mCallback = listener;
        } catch (ClassCastException e) {
            throw new ClassCastException(listener.toString() + " must implement OnSourceReceiveListener");
        }
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        super.onReceiveResult(resultCode, resultData);
        if (mCallback != null) {
            mCallback.onSourceReceived(resultCode, resultData);
        }
    }

    public interface OnSourceReceiveListener {
        void onSourceReceived(int resultCode, Bundle resultData);
    }
}
