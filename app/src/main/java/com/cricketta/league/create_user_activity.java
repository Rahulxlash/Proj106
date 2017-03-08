package com.cricketta.league;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Profile;

import REST.Model.User;
import REST.RestClient;
import pl.droidsonroids.gif.GifTextView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.cricketta.league.R.id.activity_create_user_activity;
import static com.cricketta.league.R.id.editText;
import static com.cricketta.league.R.id.minlength;

public class create_user_activity extends BaseActivity {
    private Button btnCreateUser;
    private EditText txtUserName;
    private int selectedImage;
    private GifTextView gifView;
    private TextView minLength;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user_activity);
        btnCreateUser = (Button) findViewById(R.id.btn_create_profile);
        txtUserName = (EditText) findViewById(editText);
        gifView = (GifTextView) findViewById(R.id.spinnerIco);
        minLength = (TextView)findViewById(R.id.minlength);
        mContext = this;
        selectedImage = -1;

        txtUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 5) {
                    minLength.setVisibility(View.INVISIBLE);
                    gifView.setPadding(0,0,0,0);
                    gifView.setBackgroundResource(R.drawable.loading);
                    gifView.setVisibility(View.VISIBLE);
                    RestClient client = new RestClient();
                    Log.d("retro", s.toString());
                    client.AuthService().getUserByUserName(s.toString(), new Callback<User>() {
                        @Override
                        public void success(User user, Response response) {
                            if (user == null) {
                                gifView.setPadding(0,0,0,0);
                                gifView.setBackgroundResource(R.drawable.tick);
                            } else {
                                minLength.setVisibility(View.VISIBLE);
                                minLength.setText(R.string.user_name_duplicate);
                                gifView.setPadding(10,10,10,10);
                                gifView.setBackgroundResource(R.drawable.cross);
                            }
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            showToast("Error verifying user name.");
                        }
                    });
                }else{
                    minLength.setVisibility(View.VISIBLE);
                    gifView.setBackgroundResource(R.drawable.cross);
                    gifView.setPadding(10,10,10,10);
                    gifView.setVisibility(View.VISIBLE);
                }
            }
        });


        btnCreateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = "";

                if (txtUserName.getText().toString().equals("")) {
                    msg = "Please enter Username";
                }
                if (selectedImage == -1) {
                    msg = msg + (msg.equals("") ? "Please select profile icon" : ", Please select profile icon");
                }
                if (msg != "") {
                    showToast(msg);
                    return;
                }

                RestClient client = new RestClient();
                client.AuthService().createUser(txtUserName.getText().toString(), Profile.getCurrentProfile().getId(), selectedImage, new Callback<User>() {
                    @Override
                    public void success(User user, Response response) {
                        Log.d("retro", "User Registered" + Profile.getCurrentProfile().getId());
                        Intent intent = new Intent(mContext, Main_Activity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d("Retro", error.getMessage());
                        showToast("Error Registering User.");
                    }
                });
            }
        });

        GridView gridView = (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(new ImageAdapter(this));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedImage = position;

            }
        });
    }

    public class ImageAdapter extends BaseAdapter {
        private Context mcontext;

        public ImageAdapter(Context c) {
            mcontext = c;
        }

        public int getCount() {
            return mThumbIds.length;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView = new ImageView(mcontext);
            imageView.setImageResource(mThumbIds[position]);
            return imageView;
        }

        private Integer[] mThumbIds = {
                R.drawable.prof_ico_1,
                R.drawable.prof_ico_2,
                R.drawable.prof_ico_3,
                R.drawable.prof_ico_4,
                R.drawable.prof_ico_5,
                R.drawable.prof_ico_6};


    }
    public void setBtnCreateUser(View View)
    {



    }
}
