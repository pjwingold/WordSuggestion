package com.tcs.wordsuggestion.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.tcs.wordsuggestion.R;
import com.tcs.wordsuggestion.adapter.SearchResultAdapter;
import com.tcs.wordsuggestion.service.ReadSourceReceiver;
import com.tcs.wordsuggestion.service.ReadSourceService;
import com.tcs.wordsuggestion.service.RefreshSourceAlarm;
import com.tcs.wordsuggestion.utils.Constants;
import com.tcs.wordsuggestion.utils.ReflectionUtil;

public class MainActivity extends AppCompatActivity implements ReadSourceReceiver.OnSourceReceiveListener {
    private AutoCompleteTextView inputTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ReflectionUtil.initViews(this, null);
        initSource();
        initAlarm();
        inputTxt.setAdapter(new SearchResultAdapter(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initSource() {
        Intent intent = new Intent(this, ReadSourceService.class);
        intent.putExtra(ReadSourceService.SERVICE, new ReadSourceReceiver(new Handler(), this));
        startService(intent);
    }

    /**
     * Use AlarmManager here because it needs to be alive even the UI is hidden from user
     */
    private void initAlarm() {
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, RefreshSourceAlarm.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), Constants.REFRESH_INTERVAL, pendingIntent);
    }

    @Override
    public void onSourceReceived(int resultCode, Bundle resultData) {
        if (resultCode != ReadSourceService.SUCCESS) {
            Toast.makeText(getApplicationContext(), resultData.getString(ReadSourceService.SERVICE_RESULT), Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(getApplicationContext(), getString(R.string.source_data_loaded), Toast.LENGTH_LONG).show();
        }
    }
}
