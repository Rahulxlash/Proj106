package REST.Service;

import REST.Model.User;
import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by rahul.sharma01 on 3/6/2017.
 */

public interface AuthService {

    @GET("/user/{value}/getByFBId")
    public void getUserByFBId(@Path("value") String FbId, Callback<User> callback);

    @GET("/user/{value}/getByUserName")
    public void getUserByUserName(@Path("value") String UserName, Callback<User> callback);

    @FormUrlEncoded
    @POST("/user/")
    public void createUser(@Field("UserName") String UserName, @Field("FacebookId") String FacebookId, @Field("ProfileImage") int ProfileImage, Callback<User> callback);

    @FormUrlEncoded
    @POST("/user/{Id}/RegisterDevice")
    public void RegisterDevice(@Field("UserId") int Id, @Field("DeviceToken") String token, Callback<User> callback);

    @FormUrlEncoded
    @POST("/user/{Id}/UnRegisterDevice/")
    public void UnRegisterDevice(@Field("UserId") int Id, @Field("DeviceToken") String token, Callback<User> callback);

}
