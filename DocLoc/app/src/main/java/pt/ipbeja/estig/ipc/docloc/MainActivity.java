package pt.ipbeja.estig.ipc.docloc;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;


public class MainActivity extends ActionBarActivity {

    private Toolbar toolbar;
    private SensorManager sensorManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        this.sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensors = this.sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        final View cenaitas = menu.findItem(R.id.action_notifications).getActionView();
        cenaitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.cenas(cenaitas);
            }
        });
        //return super.onCreateOptionsMenu(menu);
        return true;
    }

    private int notificationCount = 0;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void cenas(View item) {
        notificationCount++;
        Log.i("cenas", notificationCount + "");
        TextView tv = (TextView) item.findViewById(R.id.hotlist_hot);
        tv.setText(notificationCount + "");
        //item.setVisibility(View.VISIBLE);
        tv.setVisibility(TextView.VISIBLE);


    }
}
