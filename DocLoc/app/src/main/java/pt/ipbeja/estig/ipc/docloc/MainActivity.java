package pt.ipbeja.estig.ipc.docloc;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.Window;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.Map;

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

        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new Adapter(getSupportFragmentManager(), MainActivity.this));
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(android.R.drawable.ic_dialog_map);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_favorite_white);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_message);



        tabLayout.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                TabLayout tab = (TabLayout) v;

                switch (viewPager.getCurrentItem())
                {
                    case 0:
                        Snackbar.make(viewPager, "Mapa", Snackbar.LENGTH_SHORT).show();
                        return true;
                    case 1:
                        Snackbar.make(viewPager, "Favoritos", Snackbar.LENGTH_SHORT).show();
                        return true;
                    case 2:
                        Snackbar.make(viewPager, "Chat", Snackbar.LENGTH_SHORT).show();
                        return true;
                }
                return false;
            }
        });


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);


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
                //pwindo.dismiss();
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
                ((ImageView) findViewById(R.id.imageView_profile_status)).setImageResource(android.R.drawable.presence_online);
                break;
            case R.id.radioButton_busy:
                ((ImageView) findViewById(R.id.imageView_profile_status)).setImageResource(android.R.drawable.presence_busy);
                break;
            case R.id.radioButton_offline:
                ((ImageView) findViewById(R.id.imageView_profile_status)).setImageResource(android.R.drawable.presence_offline);
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


    class Adapter extends FragmentPagerAdapter
    {
        final int PAGE_COUNT = 3;
        private Context context;
        private String tabTitles[] = new String[]{"Mapa", "Favoritos", "Chat"};

        public Adapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
        }


        @Override
        public Fragment getItem(int position)
        {

            switch (position)
            {
                case 0:
                    return MapTab.newInstance(position + 1);
                case 1:
                    return FavoritesTab.newInstance(position + 1);
                case 2:
                    return ChatTab.newInstance(position + 1);
                default:
                    return ChatTab.newInstance(position + 1);
            }
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            //return this.tabTitles[position];
            return "";
        }
    }
}
