package com.cricketta.league;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class create_user_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user_activity);
        GridView gridView = (GridView)findViewById(R.id.gridView);
        gridView.setAdapter(new ImageAdapter(this));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id ){
                Toast.makeText(create_user_activity.this, " " + position, Toast.LENGTH_SHORT).show();
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
     private Integer[] mThumbIds= {
            R.drawable.prof_ico_1,
            R.drawable.prof_ico_2,
            R.drawable.prof_ico_3,
            R.drawable.prof_ico_4,
            R.drawable.prof_ico_5,
            R.drawable.prof_ico_6 };

  }

}
