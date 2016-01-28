package students.molecular.podobip;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import students.molecular.podobip.services.notification.StepService;

/**
 * Created by meradi on 28/01/16.
 */
public class AppService extends Service {

    private static final String TAG = AppService.class.getSimpleName();
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private StepService mStepService;

    private int mSteps;
    private int mPace;
    private float mDistance;
    private float mSpeed;
    private float mCalories;


    /**
     * Class for clients to access.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with
     * IPC.
     */
    public class StepBinder extends Binder {
        AppService getService() {
            return AppService.this;
        }
    }

    @Override
    public void onCreate() {
        Log.i(TAG, "[SERVICE] onCreate");
        super.onCreate();
        // Start detecting
        mStepService = new StepService();
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        registerDetector();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Log.i(TAG, "[SERVICE] onStart");
        super.onStart(intent, startId);
        mStepService = new StepService();
    }

    @Override
    public void onDestroy() {
    }

    private void registerDetector() {
        mSensor = mSensorManager.getDefaultSensor(
                Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(mStepService,
                mSensor,
                SensorManager.SENSOR_DELAY_FASTEST);
    }

    private void unregisterDetector() {
        mSensorManager.unregisterListener(mStepService);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "[SERVICE] onBind");
        return mBinder;
    }

    /**
     * Receives messages from activity.
     */
    private final IBinder mBinder = new StepBinder();
}
