package students.molecular.podobip.services.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.util.Log;

/**
 * Created by meradi on 28/01/16.
 */
public class LightNotifier implements INotifyer {

    private static String TAG = LightNotifier.class.getSimpleName();
    private final Context mContext;
    private final Notification notif;
    private final NotificationManager nm;
    private static int LED_NOTIFICATION_ID = 1;

    public LightNotifier(Context context) {
        mContext = context;
        nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notif = new Notification();
        notif.ledARGB = 0xFFff0000;
        notif.flags = Notification.FLAG_SHOW_LIGHTS;
        notif.ledOnMS = 100;
        notif.ledOffMS = 100;
    }

    @Override
    public void showNotification() {
        Log.d(TAG, "notify");
        nm.notify(LED_NOTIFICATION_ID, notif);
    }
}
