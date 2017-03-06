package com.cricketta.league;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import REST.Model.User;
import REST.RestClient;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.cricketta.league.R.id.editText;

public class create_user_activity extends AppCompatActivity {
    private Button btnCreateUser;
    private EditText txtUserName;
    private int selectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user_activity);
        btnCreateUser = (Button) findViewById(R.id.btn_create_profile);
        txtUserName = (EditText) findViewById(editText);
        selectedImage = -1;
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
                    Toast toast = Toast.makeText(create_user_activity.this, msg, Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                RestClient client = new RestClient();
                client.AuthService().getUserByFBId(123456, new Callback<User>() {
                    @Override
                    public void success(User user, Response response) {
                        Log.d("Retro", response.getBody().toString());
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d("Retro", error.getMessage());
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

}
