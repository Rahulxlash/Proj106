package REST.Model;

import android.content.Context;
import android.content.SharedPreferences;

import com.cricketta.league.CricApplication;
import com.cricketta.league.ModelCallback;
import com.facebook.Profile;
import com.facebook.login.LoginManager;

import javax.inject.Inject;

import REST.Service.AuthService;
import REST.ViewModel.User;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by rahul.sharma01 on 4/4/2017.
 */

public class AuthModel {

    public static final String USER_NAME = "user_name";
    public static final String USER_ID = "user_id";
    public static final String FACEBOOK_ID = "facebook_id";
    public static final String PHOTO_URL = "photo_url";
    public static final String FIREBASE_TOKEN = "FirebaseToken";
    public static int UserId;
    public static String UserName, ThirdPartyId, PhotoUrl, FirebaseToken;
    public User CurrentUser;
    @Inject
    AuthService authService;
    @Inject
    SharedPreferences sharedPreferences;
    @Inject
    Context context;

    public AuthModel() {
        CricApplication.getAppComponent().inject(this);
        getUserData();
    }


    public boolean isUserLogin() {
        if (Profile.getCurrentProfile() != null && UserId != 0) {
            return true;
        }
        return false;
    }

    public Subscription LoginUser(final String FacebookId, final String UserName, final String PhotoUrl, final ModelCallback<User> callback) {
        return authService.getUserByFBId(FacebookId)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .flatMap(new Func1<User, Observable<User>>() {
                    @Override
                    public Observable<User> call(User user) {
                        if (user == null)

                            return authService.createUser(UserName, FacebookId)
                                    .observeOn(Schedulers.io())
                                    .subscribeOn(Schedulers.io());
                        else
                            return Observable.just(user);

                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<User>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(User user) {
                        saveUserData(FacebookId, UserName, user.userId, PhotoUrl);
                        RegisterDevicetoUser(callback);
                    }
                });
    }

    public void Logout(final ModelCallback<User> callback) {
        LoginManager.getInstance().logOut();
        UnRegisterDevicetoUser(callback);
    }

    private Subscription UnRegisterDevicetoUser(final ModelCallback<User> callback) {
        return authService.UnRegisterDevice(UserId, FirebaseToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onNext(User user) {
                        callback.onSuccess(user);
                    }

                    @Override
                    public void onError(Throwable t) {
                        callback.onError(t.getMessage());
                    }

                    @Override
                    public void onCompleted() {

                    }
                });
    }

    private Subscription RegisterDevicetoUser(final ModelCallback<User> callback) {
        return authService.RegisterDevice(UserId, FirebaseToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onNext(User user) {
                        callback.onSuccess(user);
                    }

                    @Override
                    public void onError(Throwable t) {
                        callback.onError(t.getMessage());
                    }

                    @Override
                    public void onCompleted() {

                    }
                });
    }

    private void saveUserData(String ThirdPartyId, String UserName, int UserId, String photoUrl) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        this.UserId = UserId;
        this.ThirdPartyId = ThirdPartyId;
        this.UserName = UserName;
        this.PhotoUrl = photoUrl;

        editor.putString(FACEBOOK_ID, ThirdPartyId);
        editor.putInt(USER_ID, UserId);
        editor.putString(USER_NAME, UserName);
        editor.putString(PHOTO_URL, photoUrl);
        editor.apply();
    }

    private void getUserData() {
        ThirdPartyId = sharedPreferences.getString(FACEBOOK_ID, "");
        UserId = sharedPreferences.getInt(USER_ID, 0);
        UserName = sharedPreferences.getString(USER_NAME, "");
        PhotoUrl = sharedPreferences.getString(PHOTO_URL, "");
        FirebaseToken = sharedPreferences.getString(FIREBASE_TOKEN, "");
    }


    public Subscription getUserByFBId(String FBId, final ModelCallback<User> callback) {
        return authService.getUserByFBId(FBId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(User user) {
                        callback.onSuccess(user);
                    }
                });
    }

    public Subscription CreateNewUser(String userName, String FBId, final ModelCallback<User> callback) {
        return authService.createUser(userName, FBId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(User user) {
                        callback.onSuccess(user);
                    }
                });

    }
}
