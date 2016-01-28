package students.molecular.podobip.services;

import android.util.Log;

import students.molecular.podobip.listener.StepListener;

/**
 * Created by meradi on 28/01/16.
 */
public class NotifierService implements StepListener {

    private static String TAG = NotifierService.class.getSimpleName();

    public NotifierService(IStepService stepService) {
        stepService.addStepListener(10, this);
    }

    @Override
    public void onStepEvent() {
        Log.d(TAG, "step event");
    }
}
