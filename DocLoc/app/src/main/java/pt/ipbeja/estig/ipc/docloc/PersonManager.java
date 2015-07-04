package pt.ipbeja.estig.ipc.docloc;

import android.widget.ArrayAdapter;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Diogo on 04/07/2015.
 */
public class PersonManager
{
    private List<Person> personList;

    public PersonManager()
    {
        this.personList = new ArrayList<>();
        Person a = new Person("Joao", "Silva", "Fisica");
        Person b = new Person("Pedro", "Martins", "Matematica");
        Person c = new Person("Rita", "Fernandes", "Gestao");
        Person d = new Person("Manuela", "Pereira", "Informatica");

        a.setStatus(Person.AVAILABLE);
        a.setPosition(new LatLng(38.016125, -7.875782));

        b.setStatus(Person.BUSY);
        b.setPosition(new LatLng(38.016216, -7.875590));

        c.setStatus(Person.AVAILABLE);
        c.setPosition(new LatLng(38.016316, -7.875395));

        d.setStatus(Person.OFFLINE);
        d.setPosition(new LatLng(38.015953, -7.875611));


        personList.add(a);
        personList.add(b);
        personList.add(c);
        personList.add(d);

    }

    public List<Person> getPersonList()
    {
        return this.personList;
    }
}
