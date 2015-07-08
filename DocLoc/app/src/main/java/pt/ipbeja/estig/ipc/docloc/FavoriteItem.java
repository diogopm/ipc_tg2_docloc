package pt.ipbeja.estig.ipc.docloc;

/**
 * Created by Diogo on 07/07/2015.
 */
public class FavoriteItem
{
    String name;
    String status;
    int statusIcon;
    int profileImage;

    public FavoriteItem(Person p)
    {
        this.name = p.fullName();
        this.status = p.getStatusDescriptive();
        this.statusIcon = p.getStatus() == Person.AVAILABLE ? android.R.drawable.presence_online : p.getStatus() == Person.BUSY ? android.R.drawable.presence_busy : android.R.drawable.presence_offline;
        this.profileImage = R.drawable.ic_face_black;

    }


}
