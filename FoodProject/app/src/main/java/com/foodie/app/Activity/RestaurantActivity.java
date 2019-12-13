package com.foodie.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.etsy.android.grid.StaggeredGridView;
import com.foodie.app.R;
import com.foodie.app.adapter.DishListAdapter;
import com.foodie.app.code.Code;
import com.foodie.app.model.DishInfo;
import com.foodie.app.util.Constant;
import com.foodie.app.util.HttpRequest;
import com.foodie.app.view.Kanner;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RestaurantActivity extends AppCompatActivity {
    private Toolbar mToolbar;

    private StaggeredGridView dishListView;
    private Kanner kanner;

    private DishListAdapter dishListAdapter;

    private List<DishInfo> dishes=null;

    private SwipeRefreshLayout sr;
// private variables
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
// connect with activity_restaurant interface
        initToolbar();

        LayoutInflater inflater=new LayoutInflater(RestaurantActivity.this) {
            @Override
            public LayoutInflater cloneInContext(Context context) {
                return null;
            }
        };

        dishListView= (StaggeredGridView)findViewById(R.id.channel_dish_listmenu);
        sr = (SwipeRefreshLayout)findViewById(R.id.srmenu);
        View header =inflater.inflate(R.layout.kanner, dishListView,false);
        kanner = (Kanner) header.findViewById(R.id.kanner);
        dishListView.addHeaderView(header);

        Intent intent=getIntent();
        String what=intent.getStringExtra("what");
        if(what.equals("south")){
            setTitle("SouthCampus");
            goPost("3");
            // south campus as type 3
            int[] imagesRes = {R.drawable.mei, R.drawable.shi};
            kanner.setImagesRes(imagesRes);
        }else if(what.equals("north")){
            setTitle("NorthCampus");
            goPost("1");
            int[] imagesRes = {R.drawable.cheese_1, R.drawable.cheese_2};
            kanner.setImagesRes(imagesRes);
            // north campus as number 1
        }else if(what.equals("center")){
            setTitle("CentralCampus");
            goPost("2");
            int[] imagesRes = {R.drawable.cheese_5, R.drawable.cheese_4};
            kanner.setImagesRes(imagesRes);
            //Center campus as number 2
        }

        sr.setColorSchemeResources(
                R.color.colorPrimary,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
//set color method
        sr.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                sr.setRefreshing(false);
            }
        });


        HttpRequest.handler=new Handler(){
            @Override
            public void handleMessage(Message msg){
                if(msg.obj.equals(Code.fail)){
                    Toast.makeText(RestaurantActivity.this,"request failed",Toast.LENGTH_SHORT).show();
                }else if(msg.obj.equals(Code.noRespond)){
                    Toast.makeText(RestaurantActivity.this,"Request no response",Toast.LENGTH_SHORT).show();
                }else{
                    updateDishes(msg.obj.toString());
                }
                super.handleMessage(msg);
            }
        };
    }
    // headler method
    private void updateDishes(String dis) {
        dishes=new ArrayList<DishInfo>();

        try{
            JSONArray jsonArray=new JSONArray(dis);
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject= (JSONObject) jsonArray.get(i);
                DishInfo dishInfo=new DishInfo();
                dishInfo.setDishName(jsonObject.getString("foodname"));
                dishInfo.setPrice(jsonObject.getString("foodprice"));
                if(jsonObject.getString("foodname").equals("RoastBeef"))
                {
                    dishInfo.setPicture("https://dinnerthendessert.com/wp-content/uploads/2017/04/Slow-Cooker-Roast-Beef-Sliceable-5-680x453.jpg");
                }
                if(jsonObject.getString("foodname").equals("Pizza")) {
                    dishInfo.setPicture("http://www.palermospizza.com/Media/Default/Our%20Pizzas/Pizzeria/slice_P_PEPPERONI-SLICE.png");
                }
                if(jsonObject.getString("foodname").equals("FrenchFries") ){
                    dishInfo.setPicture("https://cms.splendidtable.org/sites/default/files/styles/900x500/public/french-fries.jpg?itok=2wcnbFAY");
                }
                if(jsonObject.getString("foodname").equals("Burger")) {
                    dishInfo.setPicture("http://food.fnr.sndimg.com/content/dam/images/food/fullset/2012/5/4/2/FNM_060112-Grilled-Burger-Recipe_s4x3.jpg.rend.hgtvcom.406.305.suffix/1371606262739.jpeg");
                }
                if(jsonObject.getString("foodname").equals("Pasta")){
                    dishInfo.setPicture("https://assets.epicurious.com/photos/55f72d733c346243461d496e/master/pass/09112015_15minute_pastasauce_tomato.jpg");

                }
                if(jsonObject.getString("foodname").equals("KungPaoChicken"))
                {
                    dishInfo.setPicture("http://cook.fnr.sndimg.com/content/dam/images/cook/fullset/2012/6/28/1/CCXTR510_kung-pao-chicken-recipe_s4x3.jpg.rend.hgtvcom.616.462.suffix/1389721001123.jpeg");
                }
                if(jsonObject.getString("foodname").equals("ChickenWing"))
                {
                    dishInfo.setPicture("https://www.yellowblissroad.com/wp-content/uploads/2015/02/Baked-Chicken-Wings.jpg");
                }
                if(jsonObject.getString("foodname").equals("Taco"))
                {
                    dishInfo.setPicture("https://upload.wikimedia.org/wikipedia/commons/thumb/7/73/001_Tacos_de_carnitas%2C_carne_asada_y_al_pastor.jpg/1200px-001_Tacos_de_carnitas%2C_carne_asada_y_al_pastor.jpg");
                }


                dishInfo.setId(jsonObject.getString("id"));
                dishInfo.setFoodtype(jsonObject.getString("foodtype"));
                dishInfo.setFoodcalorie(jsonObject.getString("foodcalorie"));
                dishes.add(dishInfo);
                // update menu method foe different campus
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        dishListAdapter = new DishListAdapter(RestaurantActivity.this,dishes);
        dishListAdapter.setOnItemClickListener(new DishListAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                Intent intent = new Intent(RestaurantActivity.this, DishInfoActivity.class);
                intent.putExtra("id", dishListAdapter.getDish(postion).getId());
                intent.putExtra("foodtype", dishListAdapter.getDish(postion).getFoodtype());
                intent.putExtra("foodname", dishListAdapter.getDish(postion).getDishName());
                startActivity(intent);

                //get information from anthod class
            }
        });

        dishListView.setAdapter(dishListAdapter);
    }

    private void goPost(String foodtype){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        NameValuePair pair1 = new BasicNameValuePair("foodtype", foodtype);
        params.add(pair1);
        HttpRequest.goPost(params, Constant.foodmenu,true);
    }
    // food type as differnet campus and post method in different campus

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        final ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        // initToolbar method
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return false;
        //onOptionsItemSelected method
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.setting_menu, menu);
        return true;
        //onCreateOptionsMenu method
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(new Intent(RestaurantActivity.this,MainActivity.class));
            RestaurantActivity.this.finish();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
