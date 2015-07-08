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
    private static final String ARG_PERSON = "person";
    View view;
    Person person;

    public static PerfilTab newInstance(int sectionNumber, Person person)
    {
        PerfilTab fragment = new PerfilTab();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        args.putSerializable(ARG_PERSON, person);
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
        view = inflater.inflate(R.layout.fragment_perfil_tab, container, false);
        this.loadBackdrop(view);
        this.setup();


        return view;
    }

    @Override
    public void onResume()
    {
        super.onResume();

    }



    private void setup()
    {
        FloatingActionButton fa = (FloatingActionButton) view.findViewById(R.id.fab_favorite);
        this.person = (Person) getArguments().get(ARG_PERSON);
        this.person = PersonManager.getInstance().getPerson(this.person);
        if(this.person.isFavorite())
        {
            fa.setImageResource(R.drawable.ic_favorite_white);
        }
        else
        {
            fa.setImageResource(R.drawable.ic_favorite_unchecked);
        }
    }

    private void loadBackdrop(View root)
    {
        final ImageView imageView = (ImageView) root.findViewById(R.id.backdrop);
        Glide.with(this).load(R.drawable.testeg).centerCrop().into(imageView);

    }



}


