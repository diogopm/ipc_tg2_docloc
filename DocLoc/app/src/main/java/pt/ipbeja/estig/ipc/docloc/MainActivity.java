package pt.ipbeja.estig.ipc.docloc;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import pt.ipbeja.estig.ipc.docloc.tabs.SlidingTabLayout;


public class MainActivity extends AppCompatActivity
{

    private Toolbar toolbar;
    public static FragmentManager fragmentManager;

    private ViewPager mPager;
    private SlidingTabLayout mTabs;

    private PopupWindow pwindo;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();

        toolbar = (Toolbar) findViewById(R.id.app_bar);

        //TabLayout tabLayout = (TabLayout) findViewById(R.id.main_tabs);
        mTabs = (SlidingTabLayout) findViewById(R.id.main_tabs);
        mTabs.setDistributeEvenly(true);
        mTabs.setSelectedIndicatorColors(getResources().getColor(R.color.accentColor));

        mPager = (ViewPager) findViewById(R.id.main_pager);

        mPager.setAdapter(new MainPagerAdapter(fragmentManager, this));
        mTabs.setViewPager(mPager);
        //mPager.setCurrentItem(1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setIcon(getResources().getDrawable(R.mipmap.ic_launcher));

        //viewPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager(), this));
        //tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_main, menu);
        final View cenaitas = menu.findItem(R.id.action_notifications).getActionView();
        cenaitas.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                MainActivity.this.cenas(cenaitas);
            }
        });

        final View cenas = menu.findItem(R.id.action_profile).getActionView();
        cenas.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                MainActivity.this.onClickProfile(cenaitas);
            }
        });

        //return super.onCreateOptionsMenu(menu);
        return true;
    }

    private int notificationCount = 0;

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings)
        {
            return true;
        } else*/ if (id == R.id.action_profile)
        {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initiatePopupWindow(View root)
    {

        /*
        getLayoutInflater().inflate()
        LayoutInflater inflater = (LayoutInflater) getBaseContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);*/

        View layout = getLayoutInflater().inflate(R.layout.profile_dropdown, null);
        pwindo = new PopupWindow(layout,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, false);

        //pwindo.setBackgroundDrawable(new BitmapDrawable());
        pwindo.setBackgroundDrawable(new BitmapDrawable(getResources(),""));
        pwindo.setOutsideTouchable(true);
        pwindo.showAsDropDown(root);

    }

    @Override
    public void onBackPressed()
    {

        if (pwindo != null && pwindo.isShowing())
        {
            pwindo.dismiss();
        } else
        {
            super.onBackPressed();
        }

    }

    public void onClickProfile(View item)
    {
        if (null != pwindo)
        {
            if (pwindo.isShowing())
            {
                pwindo.dismiss();
            } else
            {
                pwindo.showAsDropDown(findViewById(R.id.action_profile));
            }
        } else
        {
            View v = findViewById(R.id.action_profile);
            initiatePopupWindow(v);
        }
    }

    public void onClickProfileStatus(View item)
    {
        switch (item.getId())
        {
            case R.id.radioButton_available:
                ((ImageView) findViewById(R.id.imageView_profile_status)).setImageDrawable(getResources().getDrawable(android.R.drawable.presence_online));
                break;
            case R.id.radioButton_busy:
                ((ImageView) findViewById(R.id.imageView_profile_status)).setImageDrawable(getResources().getDrawable(android.R.drawable.presence_busy));
                break;
            case R.id.radioButton_offline:
                ((ImageView) findViewById(R.id.imageView_profile_status)).setImageDrawable(getResources().getDrawable(android.R.drawable.presence_offline));
                break;
        }
    }

    private void cenas(View item)
    {
        notificationCount++;
        Log.i("cenas", notificationCount + "");
        TextView tv = (TextView) item.findViewById(R.id.hotlist_hot);
        tv.setText(notificationCount + "");
        //item.setVisibility(View.VISIBLE);
        tv.setVisibility(TextView.VISIBLE);


    }
}
