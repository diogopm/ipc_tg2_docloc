package pt.ipbeja.estig.ipc.docloc;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.ContactsContract;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.Random;

/**
 * Created by Diogo on 03/07/2015.
 */
public class Person implements Serializable
{

    private String firstName;
    private String lastName;
    private boolean isFavorite;
    private String department;
    private int status;
    private float latitude;
    private float longitude;
    private String room;
    private String email;
    private String office;
    private int imageID;

    public static final int AVAILABLE = 2;
    public static final int BUSY = 1;
    public static final int OFFLINE = 0;

    public Person(String firstName, String lastName, String department, int imageID)
    {
        this.imageID = imageID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
        this.status = OFFLINE;
        this.office = "G" + new Random().nextInt(30);
        this.room = "S" + new Random().nextInt(50);
        this.email = firstName.charAt(0) + "." + lastName + "@ipbeja.pt";
    }

    public int getImage()
    {
        return imageID;
    }

    public String getEmail()
    {
        return email;
    }

    public String getOffice()
    {
        return office;
    }

    public String getRoom()
    {
        return this.room;
    }

    public float getLatitude()
    {
        return latitude;
    }

    public void setLatitude(float latitude)
    {
        this.latitude = latitude;
    }

    public float getLongitude()
    {
        return longitude;
    }

    public void setLongitude(float longitude)
    {
        this.longitude = longitude;
    }

    public LatLng getPosition()
    {
        return new LatLng(this.latitude, this.longitude);
    }

    /*
        public void setPosition(LatLng position)
        {
            this.position = position;
        }

        public LatLng getPosition()
        {
            if(status == OFFLINE) return null;
            return position;
        }
    */
    public void setStatus(int status)
    {
        this.status = status;
    }

    public String fullName()
    {
        return firstName + " " + lastName;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public String getFullName()
    {
        return firstName + " " + lastName;
    }

    public int getStatusDescriptive()
    {
        switch (status)
        {
            case AVAILABLE:
                return R.string.status_available;
            case BUSY:
                return R.string.status_busy;
            case OFFLINE:
                return R.string.status_offline;
        }
        return -1;
    }

    public boolean isFavorite()
    {
        return isFavorite;
    }

    public void toggleFavorite()
    {
        isFavorite = !isFavorite;
    }

    public String getDepartment()
    {
        return department;
    }

    public int getStatus()
    {
        return status;
    }

    public int getStatusDrawableID()
    {
        switch (status)
        {
            case AVAILABLE:
                return android.R.drawable.presence_online;
            case BUSY:
                return android.R.drawable.presence_busy;
            default:
                return android.R.drawable.presence_offline;
        }
    }

    public void setPosition(float latitude, float longitude)
    {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;
        if(person.fullName().equals(this.fullName())) return true;

        return false;

    }

    @Override
    public int hashCode()
    {
        int result = firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + (isFavorite ? 1 : 0);
        result = 31 * result + department.hashCode();
        result = 31 * result + status;
        result = 31 * result + (latitude != +0.0f ? Float.floatToIntBits(latitude) : 0);
        result = 31 * result + (longitude != +0.0f ? Float.floatToIntBits(longitude) : 0);
        return result;
    }
}