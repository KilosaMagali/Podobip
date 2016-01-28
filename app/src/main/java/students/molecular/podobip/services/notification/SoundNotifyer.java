package students.molecular.podobip.services.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

/**
 * Created by meradi on 28/01/16.
 */
public class SoundNotifyer implements INotifyer {

    private static String TAG = SoundNotifyer.class.getSimpleName();
    private final NotificationManager manager;
    private static int SOUND_NOTIFICATION_ID = 1;
    private final Notification note;


    public SoundNotifyer(Context context) {
        Context mContext = context;
        manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        note = new Notification();
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        note.sound = alarmSound;
        note.defaults |= Notification.DEFAULT_VIBRATE;
        note.flags |= Notification.FLAG_AUTO_CANCEL;
    }

    @Override
    public void showNotification() {
        Log.d(TAG, "notify");
        manager.notify(SOUND_NOTIFICATION_ID, note);
    }
}
