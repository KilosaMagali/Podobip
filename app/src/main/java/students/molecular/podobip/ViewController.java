package students.molecular.podobip;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.text.DecimalFormat;

import students.molecular.podobip.listener.StepListener;

public class ViewController extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, StepListener {

    public static final int WIDGET_ACTIVITY = 1;
    public static final int SETTINGS_ACTIVITY = 2;

    //For a mean human
    public static final double METERS_PER_STEP = 0.75;
    public static final double CALORIES_PER_STEP = 0.03;
    public static final double TIME_PER_STEP = 0.5;

    public static NOTIF_MODE mode = NOTIF_MODE.VIBRATE;
    private static int nbSteps = 0;
    private static double duration = 0;
    private static double nbCalories = 0;
    private static double distance = 0;
    private boolean stepsWidget = true,caloriesWidget = true,distanceWidget = true,positionWidget = true, durationWidget = true ;
    private String[] items = new String[] {};
    private String[] content = new String[] {};
    private TextView totalDuration, totalDistance, largeNbSteps;


    ServiceConnection connection;
    StepAndroidService stepService;

    public ViewController() {
        connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                stepService = ((StepAndroidService.StepBinder)service).getService();

                stepService.registerCallback(ViewController.this);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        totalDuration = (TextView)findViewById(R.id.duree);
        totalDistance = (TextView)findViewById(R.id.distance);
        largeNbSteps = (TextView)findViewById(R.id.largeSteps);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setupItemsAdapter();

        //Intent intent = new Intent(ViewController.this, AppService.class);
        //startService(intent);

        Intent intent = new Intent(ViewController.this, StepAndroidService.class);
        startService(intent);
        bindStepService();

        //stopService(intent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //Toast.makeText(ViewController.this, "Item", Toast.LENGTH_LONG);
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            displayWidgetView();
            return true;
        }
        

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_manage) {
            displaySettingsView();
        } else if(id == R.id.about) {
            displayAboutView();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void displayWidgetView() {
        Intent widgetIntent = new Intent(this, WidgetController.class);
        startActivityForResult(widgetIntent, WIDGET_ACTIVITY);
    }


    private void displayAboutView() {
        Intent widgetIntent = new Intent(this, AboutController.class);
        startActivity(widgetIntent);
    }

    private void displaySettingsView() {
        Intent settingsIntent = new Intent(this, SettingsController.class);
        startActivityForResult(settingsIntent, SETTINGS_ACTIVITY);
    }

    public void setupItemsAdapter() {
        GridView gridView = (GridView)findViewById(R.id.grid_view);
        totalDistance.setText(distance +" Km");
        totalDuration.setText("00h"+duration);
        largeNbSteps.setText(nbSteps+"");
        //String[] items = new String[] {"Calories", "Distance", "Steps", "Walking time"};
        //String[] content = new String[] {nbCalories +" Cal.", distance+" m", nbSteps +" Steps", duration+" min" };
        ArrayList<String> tempoItems = new ArrayList<>();
        ArrayList<String> tempoContent = new ArrayList<>();

        if(stepsWidget) {
            tempoItems.add("Steps");
            tempoContent.add(nbSteps +" Steps");
        }
        if(caloriesWidget) {
            tempoItems.add("Calories");
            tempoContent.add(nbCalories +" Cal.");
        }
        if(distanceWidget) {
            tempoItems.add("Distance");
            tempoContent.add(distance+" m");
        }
        if(positionWidget) {

        }
        if(durationWidget) {
            tempoItems.add("Walking time");
            tempoContent.add(duration+" min");
        }
        items = tempoItems.toArray(new String[tempoItems.size()]);
        content = tempoContent.toArray(new String[tempoContent.size()]);
        ItemAdapter itemAdapter = new ItemAdapter(getApplicationContext(),items,content);
        gridView.setAdapter(itemAdapter);
    }

    private void bindStepService() {
        bindService(new Intent(ViewController.this, StepAndroidService.class), connection, Context.BIND_AUTO_CREATE + Context.BIND_DEBUG_UNBIND);
    }

    @Override
    public void onStepEvent() {
        nbSteps++;

        nbCalories = Math.round((nbCalories+CALORIES_PER_STEP) * 100.0)/100.0;
        distance = Math.round((distance+METERS_PER_STEP) * 100.0)/100.0;
        duration = Math.round((duration+TIME_PER_STEP/60) * 100.0)/100.0;
        setupItemsAdapter();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            switch (requestCode) {
                case WIDGET_ACTIVITY:
                    handleWidgetViewResult(data);
                    break;
                case SETTINGS_ACTIVITY:
                    handleSettingsViewResult(data);
                    break;
            }
        }
    }

    private void handleWidgetViewResult(Intent data) {
        stepsWidget = data.getExtras().getBoolean("stepsWidget");
        caloriesWidget = data.getExtras().getBoolean("caloriesWidget");
        distanceWidget = data.getExtras().getBoolean("distanceWidget");
        positionWidget = data.getExtras().getBoolean("positionWidget");
        setupItemsAdapter();

    }

    private void handleSettingsViewResult(Intent data) {
        int stepsValue = data.getExtras().getInt("stepsValue");
        boolean automatic = data.getExtras().getBoolean("auto");
        boolean sound = data.getExtras().getBoolean("sound");
        boolean vibrate = data.getExtras().getBoolean("vibrate");
        boolean light = data.getExtras().getBoolean("light");

        NOTIF_MODE mode;

        if(automatic){
            mode = NOTIF_MODE.AUTO;
        }
        else if(sound){
            mode = NOTIF_MODE.SOUND;
        }
        else if(vibrate){
            mode = NOTIF_MODE.VIBRATE;
        }
        else{
            mode = NOTIF_MODE.LIGHT;
        }
    }

    public static NOTIF_MODE getNotificationMode(){
        return mode;
    }
}
