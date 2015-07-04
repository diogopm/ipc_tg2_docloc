package pt.ipbeja.estig.ipc.docloc;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.TooManyListenersException;


public class PersonView extends AppCompatActivity
{

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_view);

        toolbar =  (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);


        Intent intent = getIntent();
        Person person = intent.getParcelableExtra("person");

        TextView tv = (TextView)findViewById(R.id.textview_person);
        tv.setText(person.getFirstName() + " " + person.getLastName());
        tv = (TextView) findViewById(R.id.textview_department);
        tv.setText(person.getDepartment());
        tv = (TextView) findViewById(R.id.textview_status);
        tv.setText(person.getStatus() == Person.AVAILABLE ? "Available" : person.getStatus() == Person.BUSY ? "Busy" : "Offline");
        tv = (TextView) findViewById(R.id.textview_position);
        tv.setText(person.getPosition().toString());


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_person_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
