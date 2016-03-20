package solvers.problem.global.trackingcollector;

import android.app.ProgressDialog;
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

    private void addLocation(final LocationDataPoint ldp){


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
                Toast.makeText(LogActivity.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void...v) {
                HashMap<String,String> params = new HashMap<>();
                params.put("mode", ldp.mode.toString());
                params.put("lat", ldp.latitude+"");
                params.put("lon", ldp.longitude+"");

                DBRequestHandler rh = new DBRequestHandler();
                String res = rh.sendPostRequest("http://nazsakiba.info/Track/add.php", params);
                return res;
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);

    }

    public void sendToDBonClick(View view) {
        // Add all the datapoints in the list
        for (LocationDataPoint ldp : MainActivity.t.getLocationsLog()) {
            addLocation(ldp);
        }

        // Clear the list
        MainActivity.t.clearLocationsLog();
        setLogText();
    }
}
