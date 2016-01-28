package students.molecular.podobip.services.notification;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import students.molecular.podobip.listener.StepListener;
import students.molecular.podobip.services.IStepService;

/**
 * Created by meradi on 28/01/16.
 */
public class StepService implements IStepService {

    private static String TAG = StepService.class.getSimpleName();
    private List<StepListener> listeners = new ArrayList<>();

    @Override
    public void addStepListener(int nbStep, StepListener l) {
        Log.d(TAG, "add listener " + l.getClass().getSimpleName());
    }
}
