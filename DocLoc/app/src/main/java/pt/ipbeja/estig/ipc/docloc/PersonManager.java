package pt.ipbeja.estig.ipc.docloc;

import android.widget.ArrayAdapter;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by Diogo on 04/07/2015.
 */
public class PersonManager
{
    private ArrayList<Person> personList;



    private ArrayList<Person> favoriteList;
    private HashMap<Marker, Person> mapMarker;
    private static PersonManager instance;

    private PersonManager()
    {

        this.personList = new ArrayList<>();
        Person a = new Person("Joao", "Silva", "Fisica", R.drawable.man1);
        Person b = new Person("Pedro", "Martins", "Matematica", R.drawable.man2);
        Person c = new Person("Rita", "Fernandes", "Gestao", R.drawable.woman2);
        Person d = new Person("Manuela", "Pereira", "Informatica", R.drawable.woman1);

        a.setStatus(Person.AVAILABLE);
        a.setPosition(38.016125f, -7.875782f);
        a.toggleFavorite();

        b.setStatus(Person.BUSY);
        b.setPosition(38.016216f, -7.875590f);
        b.toggleFavorite();

        c.setStatus(Person.AVAILABLE);
        c.setPosition(38.016316f, -7.875395f);
        c.toggleFavorite();

        d.setStatus(Person.OFFLINE);
        d.setPosition(38.015953f, -7.875611f);
        d.toggleFavorite();

        personList.add(a);
        personList.add(b);
        personList.add(c);
        personList.add(d);
        this.favoriteList = new ArrayList<>(personList.size());

        for(Person p : personList)
        {
            favoriteList.add(p);
        }


    }

    public Person getPerson(Person person)
    {
        for(Person p : this.personList)
        {
            if(p.equals(person)) return p;
        }
        return null;
    }

    public ArrayList<Person> getFavoriteList()
    {
        return favoriteList;
    }

    public void setFavoriteList(ArrayList<Person> favoriteList)
    {
        this.favoriteList = favoriteList;
    }

    public static PersonManager getInstance()
    {
        if(instance == null)
        {
            instance = new PersonManager();
        }
        return instance;
    }

    public ArrayList<Person> getPersonList()
    {
        return this.personList;
    }

    public void setPersonList(ArrayList<Person> personList)
    {
        this.personList = personList;
    }

    public HashMap<Marker, Person> getMapMarker()
    {
        return mapMarker;
    }

    public void setMapMarker(HashMap<Marker, Person> mapMarker)
    {
        this.mapMarker = mapMarker;
    }
}
