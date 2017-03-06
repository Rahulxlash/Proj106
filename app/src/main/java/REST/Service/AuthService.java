package REST.Service;

import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by rahul.sharma01 on 3/6/2017.
 */

public interface AuthService {

    @GET("/user/getByFBId/{value}")
    public void getUserByFBId(@Path("id") Integer FbId);

}
