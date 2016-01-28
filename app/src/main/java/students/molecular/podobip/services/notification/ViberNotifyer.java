package students.molecular.podobip.services.notification;

import android.content.Context;
import android.os.Vibrator;
import android.util.Log;

/**
 * Created by meradi on 28/01/16.
 */
public class ViberNotifyer implements INotifyer {

    private static String TAG = ViberNotifyer.class.getSimpleName();
    private Vibrator vibrator;
    private static int NB_MILLIS_VIBRATE = 3000;

    public ViberNotifyer(Context context) {
        Context mContext = context;
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }

    @Override
    public void showNotification() {
        Log.d(TAG, "notify");
        vibrator.vibrate(NB_MILLIS_VIBRATE);
    }
}
