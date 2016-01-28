package students.molecular.podobip;

import android.content.Context;

import students.molecular.podobip.listener.StepListener;
import students.molecular.podobip.services.notification.NotificationType;

/**
 * Created by meradi on 28/01/16.
 */
public class NotificationService implements StepListener {

    private final Context mContext;
    private final NotificationType notifType;

    public NotificationService(Context context) {
        mContext = context;
        notifType = new NotificationType(context);
    }
    @Override
    public void onStepEvent() {
        notifType.getNotifyer().showNotification();
    }
}
