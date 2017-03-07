package REST.Service;

import REST.Model.User;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by rahul.sharma01 on 3/6/2017.
 */

public interface AuthService {

    @GET("/user/getByFBId/{value}")
    public void getUserByFBId(@Path("value") Integer FbId, Callback<User> callback);

    @GET("/user/getByUserName/{value}")
    public void getUserByUserName(@Path("value") String UserName, Callback<User> callback);
}
