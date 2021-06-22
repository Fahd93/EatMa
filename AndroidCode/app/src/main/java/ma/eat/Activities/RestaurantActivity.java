package ma.eat.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ma.eat.Model.RestaurantModel;
import ma.eat.R;

import static ma.eat.EatMaApp.SERVERURL;

public class RestaurantActivity extends AppCompatActivity implements View.OnClickListener {


    private ImageView blurMainPicture;
    private ImageView back;
    private TextView restaurantName;
    private RatingBar ratingBarRestaurant;
    private TextView restaurantReview;
    private TextView restaurantReviewCount;
    private TextView restaurantTags;
    private TextView restaurantServices;
    private TextView restaurantLocation;
    private TextView restaurantCity;
    private TextView restaurantPhoneNumber;
    private ImageView menuPictures;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        findViewsById();
        initValues();
        setClickListener();

    }

    private void findViewsById(){
        blurMainPicture = findViewById(R.id.iv_blur_main_picture);
        back = findViewById(R.id.iv_back);
        restaurantName = findViewById(R.id.tv_restaurant_name);
        ratingBarRestaurant = findViewById(R.id.rb_restaurant_rate);
        restaurantReview = findViewById(R.id.tv_restaurant_review);
        restaurantReviewCount = findViewById(R.id.tv_restaurant_review_count);
        restaurantTags = findViewById(R.id.tv_restaurant_tags);
        restaurantServices = findViewById(R.id.tv_restaurant_services);
        restaurantLocation = findViewById(R.id.tv_restaurant_location);
        restaurantCity = findViewById(R.id.tv_restaurant_city);
        restaurantPhoneNumber = findViewById(R.id.tv_restaurant_phone_number);
        menuPictures = findViewById(R.id.iv_menu_pictures);

    }

    private void initValues(){

        RestaurantModel restaurantModel = (RestaurantModel) getIntent().getSerializableExtra("RestaurantModel");
        loadImage(SERVERURL+restaurantModel.getMainPicture(), blurMainPicture);
        restaurantName.setText(restaurantModel.getName());
        //todo fix review issue json file
        ratingBarRestaurant.setRating(4.1f);
        restaurantReview.setText(""+4.1);
        restaurantReviewCount.setText(""+33);
        restaurantTags.setText(restaurantModel.getTags());
        restaurantServices.setText(restaurantModel.getServices());
        restaurantLocation.setText(restaurantModel.getLocation());
        restaurantCity.setText(restaurantModel.getCity());
        restaurantPhoneNumber.setText(restaurantModel.getPhoneNumber());



        loadImage(SERVERURL+restaurantModel.getMenuPictures()[0], menuPictures);

    }

    private void setClickListener(){
        back.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
        }

    }

    private void loadImage(String url, ImageView imageView) {
        Picasso.get()
                .load(url)

                .into(imageView);


    }
}