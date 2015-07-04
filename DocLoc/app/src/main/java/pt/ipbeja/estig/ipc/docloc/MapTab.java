package pt.ipbeja.estig.ipc.docloc;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
        mMap.getUiSettings().setZoomControlsEnabled(true);
        LatLngBounds bounds = new LatLngBounds(new LatLng(38.015674, -7.876074), new LatLng(38.016573, -7.874719));
        GroundOverlayOptions overlay = new GroundOverlayOptions().image(BitmapDescriptorFactory.fromResource(R.drawable.estig)).positionFromBounds(bounds);
        mMap.addGroundOverlay(overlay);

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter()
        {
            @Override
            public View getInfoWindow(Marker marker)
            {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker)
            {
                View v = getLayoutInflater(null).inflate(R.layout.custom_infowindow, null);
                Person p = markerMap.get(marker);
                TextView tv = (TextView) v.findViewById(R.id.textView_info_name);
                tv.setText(p.getFullName());
                tv = (TextView) v.findViewById(R.id.textView_info_status);
                tv.setText(p.getStatusDescriptive());
                ImageView iv = (ImageView) v.findViewById(R.id.imageView_info_status);
                iv.setImageDrawable(p.getStatus() == Person.AVAILABLE ? getResources().getDrawable(android.R.drawable.presence_online) : getResources().getDrawable(android.R.drawable.presence_busy));
                //iv.setBackground(p.getStatus() == Person.AVAILABLE ? getResources().getDrawable(android.R.drawable.presence_online) : getResources().getDrawable(android.R.drawable.presence_busy));



                return v;

            }
        });




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
        View marker = getLayoutInflater(null).inflate(R.layout.custom_marker, null);

        for(Person p: personList)
        {
            if(p.getPosition() == null) continue;
            ((ImageView)marker.findViewById(R.id.imageView_marker_status)).setImageDrawable(p.getStatus() == Person.AVAILABLE ? getResources().getDrawable(android.R.drawable.presence_online) : getResources().getDrawable(android.R.drawable.presence_busy));
            Marker m = this.mMap.addMarker(new MarkerOptions().position(p.getPosition()).
                    title(p.getFirstName() + " "  + p.getLastName()).snippet(p.getDepartment()).icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(getActivity(), marker))));
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

    public static Bitmap createDrawableFromView(Context context, View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }


    private void onClickProfile(View item)
    {
        switch (item.getId())
        {
            case R.id.radioButton_available:
                Log.i("radio", "available");
                break;
            case R.id.radioButton_busy:
                Log.i("radio", "busy");
                break;
            case R.id.radioButton_offline:
                Log.i("radio", "offline");
                break;
        }
    }
}
