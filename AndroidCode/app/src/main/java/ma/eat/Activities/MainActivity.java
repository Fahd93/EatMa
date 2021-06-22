package ma.eat.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ma.eat.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private TextView marrakechRestaurants;
    private TextView casablancaRestaurants;
    private TextView rabatRestaurants;
    private TextView tousLesVilles;
    private ImageView addRestaurant;
    private ImageView settings;
    private ImageView header;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewsById();
        initValues();
        setClickListener();

    }

    private void findViewsById(){

        marrakechRestaurants = findViewById(R.id.tv_marrakech);
        casablancaRestaurants = findViewById(R.id.tv_casablanca);
        rabatRestaurants = findViewById(R.id.tv_rabat);
        tousLesVilles = findViewById(R.id.tv_tous_les_villes);
        addRestaurant = findViewById(R.id.add_restaurant);
        settings = findViewById(R.id.iv_settings);
        header = findViewById(R.id.iv_header);

    }

    private void initValues(){


    }

    private void setClickListener(){
        marrakechRestaurants.setOnClickListener(this);
        casablancaRestaurants.setOnClickListener(this);
        rabatRestaurants.setOnClickListener(this);
        tousLesVilles.setOnClickListener(this);
        addRestaurant.setOnClickListener(this);
        settings.setOnClickListener(this);
        header.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.tv_marrakech:
                startActivity(new Intent(MainActivity.this, CityActivity.class)
                        .putExtra("city", "marrakech"));
                break;

            case R.id.tv_casablanca:
                startActivity(new Intent(MainActivity.this, CityActivity.class)
                        .putExtra("city", "casablanca"));
                break;

            case R.id.tv_rabat:
                startActivity(new Intent(MainActivity.this, CityActivity.class)
                        .putExtra("city", "rabat"));
                break;

            case R.id.tv_tous_les_villes:

                break;

            case R.id.add_restaurant:
                break;

            case R.id.iv_settings:
                break;

            case R.id.iv_header:
                break;
        }

    }
}