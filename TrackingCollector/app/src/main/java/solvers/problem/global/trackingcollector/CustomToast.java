package solvers.problem.global.trackingcollector;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Johannes on 02.03.2016.
 */
public class CustomToast {

    public static int length = Toast.LENGTH_SHORT;
    public static Toast previousToast = null;

    public static void ShowMessage(int message, Context context) {
        if (previousToast != null) {
            previousToast.cancel();
        }

        previousToast = Toast.makeText(context, message, length);
        previousToast.show();
    }
}
