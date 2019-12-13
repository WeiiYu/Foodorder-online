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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static com.foodie.app.R.id.Determine;
import static com.foodie.app.R.id.orderpayment;

public class Orders  extends AppCompatActivity {

    private Toolbar mToolbar;
    private String foodname,foodprice,foodnum,foodtype,foodid;
    TextView houmuch;
    EditText number;
    String allPay=null;
    // private objects
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_layout);
// connect with activity_orders_layout interface
        initToolbar();
        Intent intent=getIntent();
        foodname=intent.getStringExtra("foodname");
        foodnum=intent.getStringExtra("foodnum");
        foodprice=intent.getStringExtra("foodprice");
        foodtype=intent.getStringExtra("foodtype");
        foodid=intent.getStringExtra("foodid");
// get the foodnane,foodnum,foodprice,foodtype and foodid from antoher class
        TextView foodnameView=(TextView)findViewById(R.id.foodnameOr);
        TextView foodnumView=(TextView)findViewById(R.id.foodnum);
        TextView foodpriceView=(TextView)findViewById(R.id.foodprice);
        TextView foodtypeView=(TextView)findViewById(R.id.foodtype);
        houmuch=(TextView)findViewById(R.id.houmuch);
        number=(EditText)findViewById(R.id.Number);
        //view information of food

        foodnameView.setText("FoodName:"+foodname);
        foodnumView.setText("FoodCalorie:"+foodnum);
        foodpriceView.setText("FoodPrice:"+foodprice);

        // set text when you upload the food

         if(foodtype.equals("1")){
             foodtypeView.setText("Adress:NorthCampus");
         }else if(foodtype.equals("2")){
             foodtypeView.setText("Adress:CentralCampus");
         }else if(foodtype.equals("3")){
             foodtypeView.setText("Adress:SouthCampus");
             // different foodtype have unique number to represent
         }

        number.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                String s1=s.toString();
                if(s1.length()>0) {
                    try{
                        Double  num1 = Double.valueOf(s1);
                        Double  num2 = Double.valueOf(foodprice);
                        DecimalFormat format = new DecimalFormat("#.00");
                        allPay = format.format(num1*num2);
                        houmuch.setText("Payment:" + allPay+ "$");
                        // food price format
                    }catch (Exception e){
                        Toast.makeText(Orders.this,"An unknown error occurred",Toast.LENGTH_SHORT).show();
                        // exception as unknown error occurred
                    }
                  }
                if(s1.length()==0){
                    houmuch.setText("Payment:0$");
                    // when the price is  0
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub
            }
        });

        ImageView imageOrder=(ImageView)findViewById(R.id.imageOrder);
        // image of order

        if(foodname.equals("Pizza"))
        {
            setImage("http://www.palermospizza.com/Media/Default/Our%20Pizzas/Pizzeria/slice_P_PEPPERONI-SLICE.png", imageOrder);

        }
        else if(foodname.equals("RoastBeef"))
        {
            setImage("https://dinnerthendessert.com/wp-content/uploads/2017/04/Slow-Cooker-Roast-Beef-Sliceable-5-680x453.jpg",imageOrder);
        }
        else if(foodname.equals("FrenchFries"))
        {
            setImage("https://cms.splendidtable.org/sites/default/files/styles/900x500/public/french-fries.jpg?itok=2wcnbFAY",imageOrder);;
        }
        else   if(foodname.equals("Burger"))
        {
            setImage("http://food.fnr.sndimg.com/content/dam/images/food/fullset/2012/5/4/2/FNM_060112-Grilled-Burger-Recipe_s4x3.jpg.rend.hgtvcom.406.305.suffix/1371606262739.jpeg",imageOrder);
        }
        else   if(foodname.equals("Pasta"))
        {
            setImage("https://assets.epicurious.com/photos/55f72d733c346243461d496e/master/pass/09112015_15minute_pastasauce_tomato.jpgm ", imageOrder);
        }
        else  if(foodname.equals("KungPaoChicken"))
        {
            setImage("http://cook.fnr.sndimg.com/content/dam/images/cook/fullset/2012/6/28/1/CCXTR510_kung-pao-chicken-recipe_s4x3.jpg.rend.hgtvcom.616.462.suffix/1389721001123.jpeg",imageOrder);
        }
        else  if(foodname.equals("ChickenWing"))
        {
            setImage("https://www.yellowblissroad.com/wp-content/uploads/2015/02/Baked-Chicken-Wings.jpg",imageOrder);
        }

        else  if(foodname.equals("Taco"))
        {
            setImage("https://upload.wikimedia.org/wikipedia/commons/thumb/7/73/001_Tacos_de_carnitas%2C_carne_asada_y_al_pastor.jpg/1200px-001_Tacos_de_carnitas%2C_carne_asada_y_al_pastor.jpg",imageOrder);
        }
        else
        {
            setImage("https://upload.wikimedia.org/wikipedia/commons/thumb/6/6c/No_image_3x4.svg/1024px-No_image_3x4.svg.png", imageOrder);
        }
// set the image by string and get the image from oiline URL





        Button determine=(Button)findViewById(Determine);
        determine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(number.getText().toString().length()==0){
                    Toast.makeText(Orders.this,"Please fill out the purchase quantity",Toast.LENGTH_SHORT).show();
                    //quantity method
                }else{
                    goPost();
                }

            }
        });

        HttpRequest.handler=new Handler(){
            @Override
            public void handleMessage(Message msg){
                if(msg.obj.equals(Code.fail)){
                    Toast.makeText(Orders.this,"request failed",Toast.LENGTH_SHORT).show();
                }else if(msg.obj.equals(Code.noRespond)){
                    Toast.makeText(Orders.this,"Request no response",Toast.LENGTH_SHORT).show();
                }else if(msg.obj.equals("saveOrderSuccess")){
                    startActivity(new Intent(Orders.this,OrdersHistoryActivity.class));
                }else if(msg.obj.equals("saveOrderFail")){
                    Toast.makeText(Orders.this,"Purchase failed",Toast.LENGTH_SHORT).show();
                }else if(msg.obj.equals("nonum")){
                    Toast.makeText(Orders.this,"Quantity not sufficient",Toast.LENGTH_SHORT).show();
                }
                super.handleMessage(msg);
            }
        };
    }
    // handler method
    private void setImage(String path,ImageView imageView){
        DisplayImageOptions dishOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.loading_large)
                //loading
                .showImageOnFail(R.drawable.recipe)
                //fail
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

// setImage method
        ImageLoader.getInstance().displayImage(path,imageView,dishOptions);
    }


    private void goPost(){
// gopost method
        SharedPreferences sharedPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
 // share reference with login class
        final String  account= sharedPreferences.getString("account","null");

        if(account.length()==0|account.equals("")|account.equals("null")){
            // the length od email
            startActivity(new Intent(Orders.this,LoginActivity.class));
            Orders.this.finish();
            Toast.makeText(Orders.this,"Login has expired, please login again",Toast.LENGTH_SHORT).show();
        }else{
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            NameValuePair pair1 = new BasicNameValuePair("foodtype", foodtype);
            params.add(pair1);
            NameValuePair pair2 = new BasicNameValuePair("useremail", account);
            params.add(pair2);
            NameValuePair pair3 = new BasicNameValuePair("orderquantity",number.getText().toString());
            params.add(pair3);
            NameValuePair pair4 = new BasicNameValuePair("orderpayment", allPay);
            params.add(pair4);
            NameValuePair pair5 = new BasicNameValuePair("menuid", foodid);
            params.add(pair5);
            NameValuePair pair6 = new BasicNameValuePair("foodname", foodname);
            params.add(pair6);
            HttpRequest.goPost(params, Constant.insertorder,true);
            // insert order method

            new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        GMailSender sender = new GMailSender("yw4dhr@gmail.com","yuwei4dhr");

                            sender.sendMail("order is Successful", "Thank you for Ordering food " +
                                            "   Campus: " +foodtype + "     " + "Name:" + account + "     " + "FoodName:" + foodname + "     " + "Quantity:" + number.getText().toString() + "     " +
                                            "Payment:" + allPay,
                                    "yw4dhr@gmail.com", account);


                            sender.sendMail("New Order", "Campus:"+foodtype+"     "+"Name:"+account+"     "+"FoodName:"+foodname+"     "+"Quantity:"+number.getText().toString()+"     "+
                                            "Payment"+allPay,
                                    "yw4dhr@gmail.com","yyww4dhr@gmail.com");



                    } catch (Exception e) {
                        Log.e("SendMail", e.getMessage(), e);
                    }
                }

            }).start();

// this is sendmail method same as login in class
        }

    }
    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        final ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        setTitle("place an order");
        // place an order title
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
                //onOptionsItemSelected method
        }
        return false;
    }
}
