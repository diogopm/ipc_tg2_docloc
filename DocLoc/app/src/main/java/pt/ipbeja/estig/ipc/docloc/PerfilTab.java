package pt.ipbeja.estig.ipc.docloc;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;


public class PerfilTab extends Fragment
{
    private static final String ARG_SECTION_NUMBER = "section_number";

    public static PerfilTab newInstance(int sectionNumber)
    {
        PerfilTab fragment = new PerfilTab();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }


    public PerfilTab()
    {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_perfil_tab, container, false);
        this.loadBackdrop(view);


        return view;
    }

    private void loadBackdrop(View root)
    {
        final ImageView imageView = (ImageView) root.findViewById(R.id.backdrop);
        Glide.with(this).load(R.drawable.testeg).centerCrop().into(imageView);

    }



}


