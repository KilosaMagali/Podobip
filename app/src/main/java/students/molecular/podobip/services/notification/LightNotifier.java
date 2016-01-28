package students.molecular.podobip.services.notification;

import android.util.Log;

/**
 * Created by meradi on 28/01/16.
 */
public class LightNotifier implements INotifiyer {

    private static String TAG = LightNotifier.class.getSimpleName();

    @Override
    public void notifiy() {
        Log.d(TAG, "notify");
    }
}
