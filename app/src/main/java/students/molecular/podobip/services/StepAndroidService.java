package students.molecular.podobip.services;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.util.Log;

import students.molecular.podobip.services.notification.StepService;

public class StepAndroidService extends Service {

    Sensor sensor;
    SensorManager sensorManager;
    StepService stepService;

    @Override
    public void onCreate() {
        stepService = new StepService();
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        registerDetector();

        return Service.START_NOT_STICKY;
    }

    private void registerDetector() {
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(stepService, sensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    public IBinder onBind(Intent arg0) {
        System.out.println("Service binded");
        return null;
    }

    @Override
    public void onDestroy() {
        System.out.println("Service destroyed");
    }
}