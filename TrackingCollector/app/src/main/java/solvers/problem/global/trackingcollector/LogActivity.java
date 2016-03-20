package solvers.problem.global.trackingcollector;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class LogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String log = getIntent().getExtras().getString(getResources().getString(R.string.log_intent));
        ((TextView)findViewById(R.id.log_textview)).setText(log);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
