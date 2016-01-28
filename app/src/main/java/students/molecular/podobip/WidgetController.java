package students.molecular.podobip;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CheckBox;

public class WidgetController extends AppCompatActivity {

    private CheckBox steps;
    private CheckBox calories;
    private CheckBox distance;
    private CheckBox position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget_controller);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        steps = (CheckBox) findViewById(R.id.stepsCheckBox);
        calories = (CheckBox) findViewById(R.id.caloriesCheckbox);
        distance = (CheckBox) findViewById(R.id.distanceCheckBox);
        position = (CheckBox) findViewById(R.id.positionCheckBox);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent data = new Intent();
                data.putExtra("stepsWidget", steps.isChecked());
                data.putExtra("caloriesWidget", calories.isChecked());
                data.putExtra("distanceWidget", distance.isChecked());
                data.putExtra("positionWidget", position.isChecked());

                setResult(RESULT_OK, data);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
