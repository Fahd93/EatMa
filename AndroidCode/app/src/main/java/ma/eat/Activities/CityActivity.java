package ma.eat.Activities;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import ma.eat.Adapters.RestaurantAdapter;
import ma.eat.Model.RestaurantModel;
import ma.eat.R;

public class CityActivity extends AppCompatActivity implements View.OnClickListener {


    private ImageView back;
    private ArrayList<RestaurantModel> restaurantModels;

    private RecyclerView restaurantsList;
    private RestaurantAdapter restaurantAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        findViewsById();
        initValues();
        setClickListener();
    }

    private void findViewsById() {
        restaurantsList = findViewById(R.id.rv_restaurant_list);
        back = findViewById(R.id.iv_back);


    }

    private void initValues() {
        String city = getIntent().getExtras().getString("city");
        restaurantModels = new ArrayList<>();

        JSONObject jObject = null;
        try {
            jObject = new JSONObject(getJsonFromAssets(getApplicationContext(), "eat_ma.json"));
            JSONArray jArray = jObject.getJSONArray("Restaurants");
            for (int i = 0; i < jArray.length(); i++) {


                JSONObject oneObject = jArray.getJSONObject(i);
                String oneObjectsCity = oneObject.getString("city").toLowerCase();
                if (oneObjectsCity.equals(city)) {


                    JSONArray jAMP = oneObject.getJSONArray("menu pictures");
                    String[] sMP = new String[100];
                    if (jAMP.length() > 0) {
                        for (int j = 0; j < jAMP.length(); j++) {
                            sMP[j] = jAMP.getString(j);

                        }
                    }


                    JSONArray jTags = oneObject.getJSONArray("tags");
                    StringBuilder sTags = new StringBuilder();
                    if (jTags.length() > 0) {
                        for (int j = 0; j < jTags.length(); j++) {
                            if (j == 0)
                                sTags = new StringBuilder(jTags.getString(j));
                            else
                                sTags.append(", ").append(jTags.getString(j));

                        }
                    }

                    JSONArray jServices = oneObject.getJSONArray("services");
                    StringBuilder sServices = new StringBuilder();
                    if (jServices.length() > 0) {
                        for (int j = 0; j < jServices.length(); j++) {
                            if (j == 0)
                                sServices = new StringBuilder(jServices.getString(j));
                            else
                                sServices.append(", ").append(jServices.getString(j));

                        }
                    }

                    restaurantModels.add(new RestaurantModel(
                            oneObject.getString("name"),
                            sTags.toString(),
                            sServices.toString(),
                            !oneObject.getString("verified").equals("No"),
                            oneObject.getString("location"),
                            oneObject.getString("city"),
                            oneObject.getString("phone number"),
                            oneObject.getString("main picture"),
                            sMP,
                            2.1f,
                            0

                    ));
                }


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        restaurantsList = findViewById(R.id.rv_restaurant_list);
        restaurantAdapter = new RestaurantAdapter(getApplicationContext(), restaurantModels);
        restaurantsList.setHasFixedSize(true);
        restaurantsList.setLayoutManager(new LinearLayoutManager(this));
        restaurantsList.setAdapter(restaurantAdapter);


    }

    private void setClickListener() {
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

    private String getJsonFromAssets(Context context, String fileName) {
        String jsonString;
        try {
            InputStream is = context.getAssets().open(fileName);

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            jsonString = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return jsonString;
    }
}