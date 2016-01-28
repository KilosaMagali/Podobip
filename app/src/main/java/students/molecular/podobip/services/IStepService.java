package students.molecular.podobip.services;

import students.molecular.podobip.listener.StepListener;

/**
 * Created by meradi on 28/01/16.
 */
public interface IStepService {


    public void addStepListener(int nbStep, StepListener l);
}
