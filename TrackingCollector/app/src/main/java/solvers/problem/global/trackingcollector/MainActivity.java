package solvers.problem.global.trackingcollector;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static Tracker t;
    public static TransportationMode currentMode = TransportationMode.CYCLING;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
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
        if (id == R.id.log_settings) {
            this.startActivity(new Intent(this, LogActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showSwitchToast(Switch switchView) {
        initializeTracking();
        if (!switchView.isChecked())
            CustomToast.ShowMessage(R.string.TRACKING_OFF, this);
        else {
            switch (switchView.getId()) {
                case R.id.switchCar:
                    currentMode = TransportationMode.DRIVING;
                    CustomToast.ShowMessage(R.string.DRIVING_TOAST, this);
                    break;
                case R.id.switchBicycle:
                    currentMode = TransportationMode.CYCLING;
                    CustomToast.ShowMessage(R.string.CYCLING_TOAST, this);
                    break;
                case R.id.switchPedestrian:
                    currentMode = TransportationMode.PEDESTRIAN;
                    CustomToast.ShowMessage(R.string.PEDESTRIAN_TOAST, this);
                    break;
                case R.id.switchBus:
                    currentMode = TransportationMode.COMMUTE;
                    CustomToast.ShowMessage(R.string.COMMUTING_TOAST, this);
                    break;
            }
        }
    }

    public ArrayList<Switch> getSwitchButtonList() {
        ArrayList<Switch> switchButtons = new ArrayList<>();
        switchButtons.add((Switch) (findViewById(R.id.switchCar)));
        switchButtons.add((Switch) (findViewById(R.id.switchBicycle)));
        switchButtons.add((Switch) (findViewById(R.id.switchBus)));
        switchButtons.add((Switch) (findViewById(R.id.switchPedestrian)));

        return switchButtons;
    }


    public void switchOnClick(View view) {

        //Get all switch buttons
        ArrayList<Switch> switchButtons = getSwitchButtonList();
        switchButtons.remove(view);

        for (int i = 0; i < switchButtons.size(); i++) {
            switchButtons.get(i).setChecked(false);
        }

        Switch switchView = ((Switch) view);

        showSwitchToast(switchView);
    }

    public void initializeTracking() {
        if (t != null)
            return;
        if (!checkPermissions()) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},42);
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},42);
        } else {
            t = new Tracker((this));
            t.startLocationTracking();
        }
    }

    private boolean checkPermissions() {
        return !(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (42) {
            case R.integer.PERMISSION_CALLBACK_ID: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    t = new Tracker((this));
                    t.startLocationTracking();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    //public void showLog(View view) {
      //  ((TextView)findViewById(R.id.editText)).setText(t.log);
    //}
}
