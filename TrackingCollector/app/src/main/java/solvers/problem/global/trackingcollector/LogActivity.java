package solvers.problem.global.trackingcollector;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.HashMap;

public class LogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setLogText();
    }

    private void setLogText() {
        String s = "";
        int i = 1;
        for (LocationDataPoint ldp : MainActivity.t.getLocationsLog()) {
            s += i + "   " + ldp.latitude + " " + ldp.longitude + "   "
                    + ldp.date + "   " + ldp.mode + "\n";
        }
        ((TextView)findViewById(R.id.log_textview)).setText(s);


    }

    private void addLocations(){

        new AsyncTask<Void,Void,String>() {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(LogActivity.this, "Adding...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                MainActivity.t.clearLocationsLog();
                setLogText();
                Toast.makeText(LogActivity.this, s, Toast.LENGTH_LONG).show();

                // If the device has no DEVICE_ID, then put the returned ID as DEVICE_ID
                String prefString = getString(R.string.DEVICE_ID_PREF);
                SharedPreferences sharedPref = LogActivity.this.getPreferences(Context.MODE_PRIVATE);
                int DEVICE_ID = sharedPref.getInt(prefString, -1);

                if (DEVICE_ID == -1) {
                    sharedPref.edit().putInt(prefString, Integer.parseInt(s)).commit();
                }


            }

            @Override
            protected String doInBackground(Void...v) {
                String res = "";

                String prefString = getString(R.string.DEVICE_ID_PREF);
                SharedPreferences sharedPref = LogActivity.this.getPreferences(Context.MODE_PRIVATE);
                int DEVICE_ID = sharedPref.getInt(prefString, -1);

                for (LocationDataPoint ldp : MainActivity.t.getLocationsLog()) {
                    HashMap<String,String> params = new HashMap<>();
                    params.put("mode", ldp.mode.toString());
                    params.put("lat", ldp.latitude+"");
                    params.put("lon", ldp.longitude+"");
                    params.put("time", ldp.date);
                    params.put("id", DEVICE_ID+"");

                    DBRequestHandler rh = new DBRequestHandler();
                    res = rh.sendPostRequest("http://nazsakiba.info/Track/add.php", params);
                    System.out.println(res);
                }
                return res;
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);

    }

    public void sendToDBonClick(View view) {

        // Add all the datapoints in the list
        addLocations();
    }
}
