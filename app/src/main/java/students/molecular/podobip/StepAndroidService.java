package students.molecular.podobip;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;

import students.molecular.podobip.listener.StepListener;
import students.molecular.podobip.services.GPSTracker;
import students.molecular.podobip.services.StepService;

public class StepAndroidService extends Service {

    Sensor sensor;
    SensorManager sensorManager;
    StepService stepService;
    Context mContext;

    @Override
    public void onCreate() {
        stepService = new StepService();
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mContext = getApplicationContext();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        registerDetector();

        return Service.START_STICKY;
    }

    private void registerDetector() {
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(stepService, sensor, SensorManager.SENSOR_DELAY_FASTEST);
        stepService.addStepListener(30, new NotificationService(mContext));
        stepService.addStepListener(30, new GPSTracker(mContext));
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return new StepBinder();
    }

    @Override
    public void onDestroy() {
    }

    public void registerCallback(StepListener l) {
        stepService.addStepListener(10, l);
    }

    public class StepBinder extends Binder {
        StepAndroidService getService() {
            return StepAndroidService.this;
        }
    }
}