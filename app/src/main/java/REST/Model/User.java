package REST.Model;

import org.parceler.Parcel;

/**
 * Created by rahul.sharma01 on 3/6/2017.
 */
@Parcel
public class User {
    private Integer userId;
    private String facebookId;
    private String userName;
    private Integer profileImage;

    public String getUserName()
    {
        return userName;
    }

    public String getFacebookId()
    {
        return facebookId;
    }

    public int getProfileImage()
    {
        return profileImage;
    }

    public int getUserId()
    {
        return  userId;
    }

}
