package students.molecular.podobip.services.notification;

import android.util.Log;

/**
 * Created by meradi on 28/01/16.
 */
public class ViberNotifiyer implements  INotifiyer {

    private static String TAG = ViberNotifiyer.class.getSimpleName();

    @Override
    public void notifiy() {
        Log.d(TAG, "notify");
    }
}
