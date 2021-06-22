package ma.eat.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import ma.eat.Activities.RestaurantActivity;
import ma.eat.EatMaApp;
import ma.eat.Model.RestaurantModel;
import ma.eat.R;

import static ma.eat.EatMaApp.SERVERURL;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder>{

    private final ArrayList<RestaurantModel> restaurantModels;
    private Context context;


    public RestaurantAdapter(Context context, ArrayList<RestaurantModel> restaurantModels) {
        this.context = context;
        this.restaurantModels = restaurantModels;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.item_restaurant, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final RestaurantModel restaurantModel = restaurantModels.get(position);
        loadImage(SERVERURL+restaurantModel.getMainPicture(), holder.mainPicture);
        //holder.mainPicture.setImageDrawable(LoadImageFromWebOperations(SERVERURL+restaurantModel.getMainPicture()));
        holder.restaurantName.setText(restaurantModel.getName());
        holder.restaurantTags.setText(restaurantModel.getTags());
        holder.restaurantLocation.setText(restaurantModel.getLocation()+", "+restaurantModel.getCity());
        holder.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context.getApplicationContext(), RestaurantActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("RestaurantModel", restaurantModel);
                context.startActivity(i);

            }
        });

    }


    @Override
    public int getItemCount() {
        return restaurantModels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mainPicture;
        public TextView restaurantName;
        public TextView restaurantTags;
        public TextView restaurantLocation;
        public LinearLayout menu;

        public ViewHolder(View itemView) {
            super(itemView);
            this.mainPicture = itemView.findViewById(R.id.iv_main_picture);
            this.restaurantName = itemView.findViewById(R.id.tv_restaurant_name);
            this.restaurantTags = itemView.findViewById(R.id.tv_restaurant_tags);
            this.restaurantLocation = itemView.findViewById(R.id.tv_restaurant_location);
            this.menu = itemView.findViewById(R.id.ll_restaurant_menu);

        }
    }

    private void loadImage(String url, ImageView imageView) {
        Picasso.get()
                .load(url)

                .into(imageView);


    }
}
