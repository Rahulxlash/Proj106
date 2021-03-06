package REST.ViewModel;

import org.parceler.Parcel;

import java.io.Serializable;

/**
 * Created by Anuj on 4/4/2017.
 */
@Parcel
public class Player implements Serializable {
    public int playerId;
    public String name;
    public boolean bat;
    public boolean bowl;
    public boolean keeper;
    public boolean captain;
    public int age;
    public String photo;
}
