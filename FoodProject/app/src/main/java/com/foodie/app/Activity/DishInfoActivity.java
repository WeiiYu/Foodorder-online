package com.foodie.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.foodie.app.R;
import com.foodie.app.code.Code;
import com.foodie.app.util.Constant;
import com.foodie.app.util.HttpRequest;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class DishInfoActivity extends AppCompatActivity {
    private static String TAG = "DishInfoActivity";
// Private string TAG
    private Toolbar mToolbar;

    private ImageView mImageView;

    private String id, account,foodType,foodname;
    // food ID, quantity, which campus and name

    private Button collect,buy;

    //two button one is collect and another is buy the food
    private TextView foodnameV,foodpriceV,foodcalorieV,foodTypeV;
    // the information of food

    private Boolean hasCollect=false;

    // button of the collection
    private String foodprice,foodnum;
// the food price and number
    // These are all  private variable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_info);
        // connect with activity_dish_information res

        mImageView=(ImageView)findViewById(R.id.imageDetails);
        // Imageview in the interface
        foodnameV=(TextView)findViewById(R.id.foodnameV);
        //food name review in the interface
        foodpriceV=(TextView)findViewById(R.id.foodpriceV);
        //Food price view in the interface
        foodcalorieV=(TextView)findViewById(R.id.foodcalorieV);
        // foodcalorie view in the interface
        foodTypeV=(TextView)findViewById(R.id.foodTypeV);
        //food campous view
        collect=(Button)findViewById(R.id.like);
        buy=(Button)findViewById(R.id.byFood);
        // two button connect with the res

        SharedPreferences sharedPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
        account= sharedPreferences.getString("account","null");
// use the perference which come from login class
        initToolbar();
        Intent intent=getIntent();
        id=intent.getStringExtra("id");
        foodType=intent.getStringExtra("foodtype");
        foodname=intent.getStringExtra("foodname");
//pass id, food Type and foodname from another class
        goPost(id);

        Button seeFeed=(Button)findViewById(R.id.seefeed);
        //button set
        seeFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DishInfoActivity.this,FeedBackActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });
        //onClick method
        collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   if(hasCollect==false){
                       collect();
                   }else{
                       Toast.makeText(DishInfoActivity.this,"You have already collected it",Toast.LENGTH_SHORT).show();
                }
            }
        });
        collectHistory();
// setonclicklistener method to collect the food make sureif already collected
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent itt=new Intent(DishInfoActivity.this,Orders.class);
                itt.putExtra("foodname",foodname);
                itt.putExtra("foodnum",foodnum);
                itt.putExtra("foodprice",foodprice);
                itt.putExtra("foodtype",foodType);
                itt.putExtra("foodid",id);
                startActivity(itt);
//Oncclick method to view
            }
        });

        HttpRequest.handler=new Handler(){
            @Override
            public void handleMessage(Message msg){
                if(msg.obj.equals(Code.fail)){
                    Toast.makeText(DishInfoActivity.this,"request failed",Toast.LENGTH_SHORT).show();
                }else if(msg.obj.equals(Code.noRespond)){
                    Toast.makeText(DishInfoActivity.this,"Request no response",Toast.LENGTH_SHORT).show();
                }else if(msg.obj.equals("10")){
                    hasCollect=true;
                }else if(msg.obj.equals("11")){
                    hasCollect=false;
                }else if(msg.obj.equals("saveSuccess")){
                    Toast.makeText(DishInfoActivity.this,"Save successfully",Toast.LENGTH_SHORT).show();
                }else if(msg.obj.equals("saveFail")){
                    Toast.makeText(DishInfoActivity.this,"Save failure",Toast.LENGTH_SHORT).show();
                }else if(msg.obj.equals("hasSaved")){
                    Toast.makeText(DishInfoActivity.this,"You have already collected it",Toast.LENGTH_SHORT).show();
                }else{
                    // all headler method
                     try{
                         JSONObject jsonObject= new JSONObject(msg.obj.toString());
                         foodnameV.setText("foodname:"+jsonObject.getString("foodname"));
                         foodpriceV.setText("foodprice:"+jsonObject.getString("foodprice")+"$");
                         foodprice=jsonObject.getString("foodprice");
                         foodcalorieV.setText("foodcalorie:"+jsonObject.getString("foodcalorie"));
                         foodnum=jsonObject.getString("foodcalorie");
                         // JSON get the data from database
                         if(foodname.equals("Pizza")) {

                             setImage("http://www.palermospizza.com/Media/Default/Our%20Pizzas/Pizzeria/slice_P_PEPPERONI-SLICE.png");
                         }
                        else if(foodname.equals("RoastBeef"))
                         {
                             setImage("https://dinnerthendessert.com/wp-content/uploads/2017/04/Slow-Cooker-Roast-Beef-Sliceable-5-680x453.jpg");
                         }
                        else if(foodname.equals("FrenchFries"))
                         {
                             setImage("https://cms.splendidtable.org/sites/default/files/styles/900x500/public/french-fries.jpg?itok=2wcnbFAY");;
                         }
                      else   if(foodname.equals("Burger"))
                         {
                             setImage("http://food.fnr.sndimg.com/content/dam/images/food/fullset/2012/5/4/2/FNM_060112-Grilled-Burger-Recipe_s4x3.jpg.rend.hgtvcom.406.305.suffix/1371606262739.jpeg");
                         }
                      else   if(foodname.equals("Pasta"))
                         {
                             setImage("https://assets.epicurious.com/photos/55f72d733c346243461d496e/master/pass/09112015_15minute_pastasauce_tomato.jpgm ");
                         }
                       else  if(foodname.equals("KungPaoChicken"))
                         {
                             setImage("http://cook.fnr.sndimg.com/content/dam/images/cook/fullset/2012/6/28/1/CCXTR510_kung-pao-chicken-recipe_s4x3.jpg.rend.hgtvcom.616.462.suffix/1389721001123.jpeg");
                         }
                       else  if(foodname.equals("ChickenWing"))
                         {
                             setImage("https://www.yellowblissroad.com/wp-content/uploads/2015/02/Baked-Chicken-Wings.jpg");
                         }

                       else  if(foodname.equals("Taco"))
                         {
                             setImage("https://upload.wikimedia.org/wikipedia/commons/thumb/7/73/001_Tacos_de_carnitas%2C_carne_asada_y_al_pastor.jpg/1200px-001_Tacos_de_carnitas%2C_carne_asada_y_al_pastor.jpg");
                         }
                         else
                         {
                             setImage("https://upload.wikimedia.org/wikipedia/commons/thumb/6/6c/No_image_3x4.svg/1024px-No_image_3x4.svg.png");
                         }

                       // all the images from online


                         if(foodType.equals("1")){
                             foodTypeV.setText("Adress:NorthCampus");
                         }else if(foodType.equals("2")){
                             foodTypeV.setText("Adress:CentralCampus");
                         }else if(foodType.equals("3")){
                             foodTypeV.setText("Adress:SouthCampus");
                         }
                         // three type sympolize three campus

                     }catch (Exception e){
                         e.printStackTrace();
                     }
                }
                super.handleMessage(msg);
            }
        };

    }
    private void collect(){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        NameValuePair pair1 = new BasicNameValuePair("menuid", id);
        params.add(pair1);
        NameValuePair pair2 = new BasicNameValuePair("useremail", account);
        params.add(pair2);
        NameValuePair pair3 = new BasicNameValuePair("foodtype", foodType);
        params.add(pair3);
        NameValuePair pair4 = new BasicNameValuePair("foodname", foodname);
        params.add(pair4);
        HttpRequest.goPost(params, Constant.insertCollect,true);
        // collect method to collect favorite food
    }

    private void collectHistory(){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        NameValuePair pair1 = new BasicNameValuePair("menuid", id);
        params.add(pair1);
        NameValuePair pair2 = new BasicNameValuePair("useremail", account);
        params.add(pair2);
        HttpRequest.goPost(params, Constant.collectGetById,true);
        // get the collection list
    }


    private void goPost(String id){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        NameValuePair pair1 = new BasicNameValuePair("id", id);
        params.add(pair1);
        HttpRequest.goPost(params, Constant.foodmenuById,true);
        //gopost method to get the meun
    }

    private void setImage(String path){
        DisplayImageOptions dishOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.loading_large)
                .showImageOnFail(R.drawable.recipe)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        ImageLoader.getInstance().displayImage(path,mImageView,dishOptions);
        //set image method to make if the image upload successfully
    }


    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        final ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        setTitle("The menu for details");
        //the detail of dish

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
                //select items
        }
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
