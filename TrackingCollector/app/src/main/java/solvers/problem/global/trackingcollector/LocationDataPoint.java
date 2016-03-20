package solvers.problem.global.trackingcollector;

;import java.sql.Date;
import java.text.Format;
import java.text.SimpleDateFormat;

/**
 * Created by Johannes on 20.03.2016.
 */
public class LocationDataPoint {

    public double latitude, longitude;
    public String date;
    public TransportationMode mode;
    public int deviceID;

    public LocationDataPoint(double latitude, double longitude, long time, TransportationMode mode, int deviceID) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = convertTime(time);
        this.mode = mode;
        this.deviceID = deviceID;
    }


    public String convertTime(long time){
        Date date = new Date(time);
        Format format = new SimpleDateFormat("yyyy MM dd HH:mm:ss");
        return format.format(date);
    }

}
