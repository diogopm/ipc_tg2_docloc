package pt.ipbeja.estig.ipc.docloc;

import java.io.Serializable;

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


}
