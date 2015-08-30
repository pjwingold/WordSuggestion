package com.tcs.aidlclient;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tcs.wordsuggestion.aidl.IParserService;
import com.tcs.wordsuggestion.aidl.ParseResult;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * A client that test the AIDL service of the main app
 */
public class MainActivity extends AppCompatActivity {
    private ParserServiceConnection mConnection;

    private TextView inputTxt;
    private Button searchBtn;
    private InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputTxt = (EditText) findViewById(R.id.inputTxt);
        searchBtn = (Button) findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });
        imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        initService();
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

    private void initService() {
        Intent intent = new Intent(ParseResult.class.getPackage().getName());
        intent.setPackage("com.tcs.wordsuggestion");//need the remote aidl app package name to work on android 5+
        mConnection = new ParserServiceConnection();
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        if (mConnection != null) {
            unbindService(mConnection);
        }
        super.onDestroy();
    }

    private void search() {
        View focus = getCurrentFocus();
        if (focus != null) {
            imm.hideSoftInputFromWindow(focus.getWindowToken(), 0);//hide soft keyboard
        }

        List<ParseResult> results = mConnection.retrieveData(inputTxt.getText().toString());
        if (results == null) {
            initService();//attempt to reconnect nevertheless
            Toast.makeText(this, "Unable to retrieve data from remote service, the service is not ready or has stopped, you may try again later.", Toast.LENGTH_LONG).show();
            return;
        }

        if (results.size() > 0) {
            List<String> tmp = new ArrayList<>(results.size());
            for (ParseResult result : results) {
                tmp.add(result.getResult());
            }

            //display the results as a comma separated string
            Toast.makeText(this, StringUtils.join(tmp.toArray(new String[tmp.size()]), ","), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Use this to communicate with the remote service
     */
    private static class ParserServiceConnection implements ServiceConnection {
        private IParserService parserService;

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            parserService = IParserService.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }

        public List<ParseResult> retrieveData(String match) {
            List<ParseResult> results = null;
            try {
                results = parserService.parseCSV(match);
            } catch (RemoteException e) {
            } catch (Exception e) {
            }
            return results;
        }
    }
}
