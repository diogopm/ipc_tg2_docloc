package pt.ipbeja.estig.ipc.docloc;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

import java.util.ArrayList;
import java.util.HashMap;

public class MapTab extends Fragment
{

    private static final String ARG_SECTION_NUMBER = "section_number";
    private GoogleMap mMap;
    private HashMap<Marker, Person> markerMap;

    private PopupWindow pwindo;

    public static MapTab newInstance(int sectionNumber) {
        MapTab fragment = new MapTab();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public MapTab(){

    }

    private static View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        if (container == null) {
            return null;
        }


        if(view != null)
        {
            ViewGroup parent = (ViewGroup) view.getParent();
            if(parent != null) parent.removeView(view);
        }

        try
        {
            view = inflater.inflate(R.layout.fragment_maptab, container, false);

        }
        catch (InflateException e)
        {

        }
        /*
        if (container == null) {
            return null;
        }*/

        //setHasOptionsMenu(true);
        //View view = inflater.inflate(R.layout.fragment_maptab, container, false);
        setUpMapIfNeeded();

        return view;
    }
    /*
    public PopupWindow getPwindo()
    {
        return pwindo;
    }*/

    @Override
    public void onCreateOptionsMenu (Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        //inflater.inflate(R.menu.dropdown, menu);
    }

    public void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (this.mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
            this.mMap = mapFragment.getMap();

            // Check if we were successful in obtaining the map.
            if (this.mMap != null)
                this.setUpMap();
        }
    }




    private void setUpMap() {
        this.mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setMyLocationEnabled(false);
        mMap.getUiSettings().setRotateGesturesEnabled(false);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.getUiSettings().setCompassEnabled(false);
        mMap.getUiSettings().setZoomGesturesEnabled(false);
        mMap.getUiSettings().setScrollGesturesEnabled(false);


        //this.mMap.setMyLocationEnabled(true); //38.015748, -7.874964
        this.mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(39.346070, -8.034906), 6f));

        this.markerMap = new HashMap<>();

        //LatLngBounds.Builder builder = new LatLngBounds.Builder();

        //final int padding = 100; // offset
        //final LatLngBounds bounds = builder.build();

        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback()
        {
            @Override
            public void onMapLoaded()
            {

                //mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(38.016125, -7.875400), 18f));
            }
        });

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener()
        {
            @Override
            public boolean onMarkerClick(final Marker marker)
            {
                new Handler().postDelayed(new Runnable()
                {

                    @Override
                    public void run()
                    {
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 18f));
                    }
                }, 300);

                return false;
            }
        });

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener()
        {
            @Override
            public void onInfoWindowClick(Marker marker)
            {
                Intent a = new Intent(getActivity(), PersonView.class);
                a.putExtra("person", MapTab.this.markerMap.get(marker));
                startActivity(a);
            }
        });
        /*
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener()
        {
            @Override
            public boolean onMarkerClick(final Marker marker)
            {
                new Handler().postDelayed(new Runnable()
                {

                    @Override
                    public void run()
                    {
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 13f));
                    }
                }, 300);

                return false;
            }
        });

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener()
        {
            @Override
            public void onInfoWindowClick(Marker marker)
            {
                Intent a = new Intent(getActivity(), PersonView.class);
                a.putExtra("person", MapTab.this.markerMap.get(marker));
                startActivity(a);
            }
        });*/

        //addOverlay();

        ArrayList<Person> personList = (ArrayList<Person>) new PersonManager().getPersonList();

        for(Person p: personList)
        {
            if(p.getPosition() == null) continue;

            Marker m = this.mMap.addMarker(new MarkerOptions().position(p.getPosition()).
                    title(p.getFirstName() + " "  + p.getLastName()).snippet(p.getDepartment()));
            this.markerMap.put(m, p);
        }

    }

    /**** The mapfragment's id must be removed from the FragmentManager
     **** or else if the same it is passed on the next time then
     **** app will crash ****/
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        /*
        if (mMap != null) {
            try {
                getChildFragmentManager().beginTransaction().remove(getChildFragmentManager().findFragmentById(R.id.map)).commit();
                //MainActivity.fragmentManager.beginTransaction()
                 //       .remove(MainActivity.fragmentManager.findFragmentById(R.id.map)).commit();
            }
            catch (IllegalStateException ignored)
            {
                Log.e("destroy", "falhou");
            }

            mMap = null;
        }*/

    }



    @Override
    public void onPause(){
        super.onPause();
        /*
        if(pwindo != null && pwindo.isShowing())
        {
            pwindo.dismiss();
            pwindo = null;
        }*/
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }
/*
    private void initiatePopupWindow(View root) {

        LayoutInflater inflater = (LayoutInflater) getActivity().getBaseContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View layout = inflater.inflate(R.layout.marker_settings, null);

        pwindo = new PopupWindow(layout,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, false);

        pwindo.setBackgroundDrawable(new BitmapDrawable());
        //pwindo.setOutsideTouchable(true);

        pwindo.showAsDropDown((View) root);


    }*/
}
