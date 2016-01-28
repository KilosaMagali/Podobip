package students.molecular.podobip;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import students.molecular.podobip.listener.StepListener;
import students.molecular.podobip.services.notification.StepService;

public class SettingsController extends AppCompatActivity {

    static final int DEFAULTS_STEPS = 10;

    private ToggleButton autoToggle;
    private ToggleButton soundToggle;
    private ToggleButton vibrationToggle;
    private ToggleButton lightToggle;

    private TextView stepsLabel;
    private Button more;
    private Button less;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_controller);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        autoToggle = (ToggleButton) findViewById(R.id.toggleAuto);
        soundToggle = (ToggleButton) findViewById(R.id.toggleSound);
        vibrationToggle = (ToggleButton) findViewById(R.id.toggleVibration);
        lightToggle = (ToggleButton) findViewById(R.id.toggleLight);

        stepsLabel = (TextView) findViewById(R.id.maxStepsValue);
        more = (Button) findViewById(R.id.buttonMore);
        less = (Button) findViewById(R.id.buttonLess);

        stepsLabel.setText(String.valueOf(DEFAULTS_STEPS));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent data = new Intent();
                data.putExtra("stepsValue", Integer.parseInt(stepsLabel.getText().toString()));
                data.putExtra("auto", autoToggle.isChecked());
                data.putExtra("sound", soundToggle.isChecked());
                data.putExtra("vibrate", vibrationToggle.isChecked());
                data.putExtra("light", lightToggle.isChecked());

                setResult(RESULT_OK, data);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onAutoTouched(View view) {
        ToggleButton button = (ToggleButton) view;

        if(button.isChecked()){
            soundToggle.setChecked(false);
            soundToggle.setEnabled(false);

            vibrationToggle.setChecked(false);
            vibrationToggle.setEnabled(false);

            lightToggle.setChecked(false);
            lightToggle.setEnabled(false);
        }
        else {
            soundToggle.setEnabled(true);

            vibrationToggle.setEnabled(true);

            lightToggle.setEnabled(true);
        }
    }

    public void onSoundTouched(View view) {
        ToggleButton button = (ToggleButton) view;

        if(button.isChecked()){
            vibrationToggle.setChecked(false);
            lightToggle.setChecked(false);
        }
    }

    public void onVibrationTouched(View view) {
        ToggleButton button = (ToggleButton) view;

        if(button.isChecked()){
            soundToggle.setChecked(false);
            lightToggle.setChecked(false);
        }
    }

    public void onLightTouched(View view) {
        ToggleButton button = (ToggleButton) view;

        if(button.isChecked()){
            soundToggle.setChecked(false);
            vibrationToggle.setChecked(false);
        }
    }

    public void onStepIncremented(View view){
        Button button = (Button) view;
        int currentValue = Integer.parseInt(stepsLabel.getText().toString());
        currentValue++;

        stepsLabel.setText(String.valueOf(currentValue));
    }

    public void onStepDecremented(View view){
        Button button = (Button) view;

        int currentValue = Integer.parseInt(stepsLabel.getText().toString());
        if(currentValue > 1){
            currentValue--;
            stepsLabel.setText(String.valueOf(currentValue));
        }
    }
}
