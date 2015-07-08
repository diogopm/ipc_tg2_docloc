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
        setUpMapIfNeeded();

        return view;
    }

    @Override
    public void onCreateOptionsMenu (Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        //inflater.inflate(R.menu.dropdown, menu);
    }

    public void setUpMapIfNeeded() {

        if (this.mMap == null)
        {
            SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
            this.mMap = mapFragment.getMap();

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
        final LatLngBounds bounds = new LatLngBounds(new LatLng(38.015560, -7.876150), new LatLng(38.016578, -7.874715));
        GroundOverlayOptions overlay = new GroundOverlayOptions().image(BitmapDescriptorFactory.fromResource(R.drawable.estig2)).positionFromBounds(bounds);
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
                tv = (TextView) v.findViewById(R.id.textView_info_room);
                tv.setText(p.getRoom());

                return v;

            }
        });

        this.mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(39.346070, -8.034906), 6f));

        this.markerMap = new HashMap<>();

        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback()
        {
            @Override
            public void onMapLoaded()
            {

                //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(38.016125, -7.875400), 18.2f));
                mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 5));
            }
        });

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener()
        {
            @Override
            public void onInfoWindowClick(Marker marker)
            {
                Intent a = new Intent(getActivity(), PersonView.class);
                Person p = MapTab.this.markerMap.get(marker);
                a.putExtra("person", p);

                startActivity(a);
            }
        });

        PersonManager pm = PersonManager.getInstance();
        View marker = getLayoutInflater(null).inflate(R.layout.custom_marker, null);

        for(Person p: pm.getPersonList())
        {
            if(p.getStatus() == Person.OFFLINE) continue;
            ImageView iv = (ImageView) marker.findViewById(R.id.imageView_marker_status);
            iv.setImageResource(p.getStatusDrawableID());
            iv = (ImageView) marker.findViewById(R.id.imageView_marker_avatar);
            iv.setImageResource(p.getImage());

            Marker m = this.mMap.addMarker(new MarkerOptions().position(p.getPosition()).
                    title(p.getFullName()).snippet(p.getDepartment()).icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(getActivity(), marker))));
            this.markerMap.put(m, p);

        }
        pm.setMapMarker(this.markerMap);

    }


    @Override
    public void onPause(){
        super.onPause();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

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

}
