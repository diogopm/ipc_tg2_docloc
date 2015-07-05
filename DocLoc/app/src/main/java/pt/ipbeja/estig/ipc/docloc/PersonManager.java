package pt.ipbeja.estig.ipc.docloc;

import android.widget.ArrayAdapter;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by Diogo on 04/07/2015.
 */
public class PersonManager
{
    private HashSet<Person> personList;
    private HashMap<Marker, Person> mapMarker;
    private static PersonManager instance;

    private PersonManager()
    {

        this.personList = new HashSet<>();
        Person a = new Person("Joao", "Silva", "Fisica");
        Person b = new Person("Pedro", "Martins", "Matematica");
        Person c = new Person("Rita", "Fernandes", "Gestao");
        Person d = new Person("Manuela", "Pereira", "Informatica");

        a.setStatus(Person.AVAILABLE);
        a.setPosition(38.016125f, -7.875782f);

        b.setStatus(Person.BUSY);
        b.setPosition(38.016216f, -7.875590f);
        b.toggleFavorite();

        c.setStatus(Person.AVAILABLE);
        c.setPosition(38.016316f, -7.875395f);

        d.setStatus(Person.OFFLINE);
        d.setPosition(38.015953f, -7.875611f);



        personList.add(a);
        personList.add(b);
        personList.add(c);
        personList.add(d);

    }



    public static PersonManager getInstance()
    {
        if(instance == null)
        {
            instance = new PersonManager();
        }
        return instance;
    }

    public HashSet<Person> getPersonList()
    {
        return this.personList;
    }

    public void setPersonList(HashSet<Person> personList)
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
