package com.tcs.wordsuggestion.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * Receive broadcast from AlarmManager, then starts the intent service {@link ReadSourceService} to
 * update sourceData in TCSApplication. No long operation should be done in onReceive to release the
 * wake lock, therefore the use of intent service
 *
 */
public class RefreshSourceAlarm extends BroadcastReceiver implements ReadSourceReceiver.OnSourceReceiveListener {
    @Override
    public void onReceive(Context context, Intent intent) {
        //L.log(context.toString());
        Intent sourceIntent = new Intent(context, ReadSourceService.class);
        sourceIntent.putExtra(ReadSourceService.SERVICE, new ReadSourceReceiver(new Handler(), this));
        context.startService(sourceIntent);
    }

    @Override
    public void onSourceReceived(int resultCode, Bundle resultData) {

    }
}
