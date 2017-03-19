package REST.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cricketta.league.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

/**
 * Created by rahul.sharma01 on 3/19/2017.
 */

public class FBUserAdapter extends RecyclerView.Adapter<FBUserAdapter.FBUserViewHolder> {
    LayoutInflater inflater;
    Context context;
    private JSONArray array;

    public FBUserAdapter(JSONArray data) {
        this.array = data;
    }


    @Override
    public FBUserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.card_facebook_user, parent, false);
        FBUserViewHolder holder = new FBUserViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(FBUserViewHolder holder, int position) {
        try {
            holder.Name.setText(array.getJSONObject(position).getString("name"));
            String imgUrl = array.getJSONObject(position).getJSONObject("picture").getJSONObject("data").getString("url");
            Picasso.with(context).load(imgUrl).transform(new CropCircleTransformation()).into(holder.img);
            //holder.PhoneNumber.setText(arrayList.get(position));
        } catch (JSONException e) {
        }
    }

    @Override
    public int getItemCount() {

        return array.length();
    }

    class FBUserViewHolder extends RecyclerView.ViewHolder {

        TextView Name;
        Button btn_Challange;
        ImageView img;

        public FBUserViewHolder(View itemView) {
            super(itemView);

            img = (ImageView) itemView.findViewById(R.id.img_user);
            Name = (TextView) itemView.findViewById(R.id.txt_FBUserName);
            btn_Challange = (Button) itemView.findViewById(R.id.btn_Challange);

            btn_Challange.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //OnClick
                }
            });

        }

    }
}