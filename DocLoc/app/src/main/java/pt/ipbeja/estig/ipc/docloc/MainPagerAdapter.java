package pt.ipbeja.estig.ipc.docloc;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.Locale;
import java.util.Map;

/**
 * Created by Diogo on 03/07/2015.
 */
public class MainPagerAdapter extends FragmentPagerAdapter
{

    private String[] tabs;

    public MainPagerAdapter(FragmentManager fm, Context context)
    {
        super(fm);
        this.tabs = context.getResources().getStringArray(R.array.main_tabs);
    }

    @Override
    public Fragment getItem(int position)
    {
        switch (position)
        {
            case 0:
                return MapTab.newInstance(position);
            case 1:
                return FavoritesTab.newInstance(position);
            default:
                return ChatTab.newInstance(position);
        }
    }

    @Override
    public int getCount()
    {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return tabs[position];
//        Locale l = Locale.getDefault();
//        switch (position) {
//            case 0:
//                return context.getString(R.string.title_section1).toUpperCase(l);
//            case 1:
//                return context.getString(R.string.title_section2).toUpperCase(l);
//            case 2:
//                return context.getString(R.string.title_section3).toUpperCase(l);
//        }
//        return null;
    }
}
