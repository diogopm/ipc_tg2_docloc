package pt.ipbeja.estig.ipc.docloc;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Diogo on 03/07/2015.
 */
public class SectionPagerAdapter extends FragmentPagerAdapter
{

    public SectionPagerAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int position)
    {
        return null;
    }

    @Override
    public int getCount()
    {
        return 3;
    }
}
