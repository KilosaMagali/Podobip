package students.molecular.podobip.services.notification;

import android.content.Context;

import students.molecular.podobip.ViewController;
import students.molecular.podobip.services.INotificationType;

/**
 * Created by meradi on 28/01/16.
 */
public class NotificationType implements INotificationType {

    private final Context mContext;

    public NotificationType(Context context) {
        mContext = context;
    }


    @Override
    public INotifyer getNotifyer() {
        switch (ViewController.getNotificationMode()) {
            case AUTO:
            case SOUND:
                return new SoundNotifyer(mContext);
            case LIGHT:
                return new LightNotifier(mContext);
            case VIBRATE:
            default:
                return new ViberNotifyer(mContext);
        }
    }
}
