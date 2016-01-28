package students.molecular.podobip.services.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by meradi on 28/01/16.
 */
public class LightNotifier implements INotifyer {

    private static String TAG = LightNotifier.class.getSimpleName();
    private final Context mContext;
    private final Notification notif;
    private final NotificationManager nm;
    private static int LED_NOTIFICATION_ID = 1;
    private static boolean on = false;
    Timer timer ;
    private static Camera cam = null;
    private long start;

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
        timer = new Timer();
        start = System.currentTimeMillis();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                synchronized (this) {
                    if (!on)
                        onFlash();
                    else
                        offFlash();
                    testStop();
                }
            }
        };
        timer.scheduleAtFixedRate(task, 0, 500);
    }

    private synchronized void onFlash() {
        on = !on;
        try {
            if (mContext.getPackageManager().hasSystemFeature(
                    PackageManager.FEATURE_CAMERA_FLASH)) {
                cam = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
                Camera.Parameters p = cam.getParameters();
                p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                cam.setParameters(p);
                cam.startPreview();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void offFlash() {
        Log.d(TAG, "offflash");
        on = !on;
        if (cam != null) {
            cam.stopPreview();
            cam.release();
            cam = null;
        }
    }

    private void testStop() {
        Long current = System.currentTimeMillis();
        if (current - start > 3000)
            timer.cancel();
        offFlash();
    }


}
