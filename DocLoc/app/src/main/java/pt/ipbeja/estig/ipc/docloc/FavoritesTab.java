package pt.ipbeja.estig.ipc.docloc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;


public class FavoritesTab extends Fragment
{
    private static final String ARG_SECTION_NUMBER = "section_number";

    public static FavoritesTab newInstance(int sectionNumber)
    {
        FavoritesTab fragment = new FavoritesTab();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }


    public FavoritesTab()
    {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorites_tab, container, false);
    }

}


