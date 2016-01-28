package students.molecular.podobip.services;

import android.util.Log;

import students.molecular.podobip.listener.StepListener;

/**
 * Created by meradi on 28/01/16.
 */
public class GPSTracker implements StepListener {

    private static String TAG = GPSTracker.class.getSimpleName();

    public GPSTracker(IStepService stepService) {
        stepService.addStepListener(20, this);
    }

    @Override
    public void onStepEvent() {
        Log.d(TAG, "step event");

    }
}
