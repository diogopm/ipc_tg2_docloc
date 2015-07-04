package pt.ipbeja.estig.ipc.docloc;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

/**
 * Created by Diogo on 03/07/2015.
 */
public class Person implements Parcelable {
    private String firstName;
    private String lastName;
    private boolean isFavorite;
    private String department;
    private int status;
    private LatLng position;

    public static final int AVAILABLE = 2;
    public static final int BUSY = 1;
    public static final int OFFLINE = 0;

    public Person(String firstName, String lastName, String department)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
        this.status = OFFLINE;
    }

    public void setPosition(LatLng position)
    {
        this.position = position;
    }

    public LatLng getPosition()
    {
        if(status == OFFLINE) return null;
        return position;
    }

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

    public String getStatusDescriptive()
    {
        switch (status)
        {
            case AVAILABLE:
                return "Available";
            case BUSY:
                return "Busy";
            case OFFLINE:
                return "Offline";
        }
        return null;
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


    protected Person(Parcel in) {
        firstName = in.readString();
        lastName = in.readString();
        isFavorite = in.readByte() != 0x00;
        department = in.readString();
        status = in.readInt();
        position = (LatLng) in.readValue(LatLng.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeByte((byte) (isFavorite ? 0x01 : 0x00));
        dest.writeString(department);
        dest.writeInt(status);
        dest.writeValue(position);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Person> CREATOR = new Parcelable.Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };
}