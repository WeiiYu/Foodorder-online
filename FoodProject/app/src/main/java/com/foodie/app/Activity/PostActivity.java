package com.foodie.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.foodie.app.R;
import com.foodie.app.code.Code;
import com.foodie.app.util.Constant;
import com.foodie.app.util.HttpRequest;
import com.foodie.app.util.HttpUtils;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

public class PostActivity extends AppCompatActivity {
    private static int REQUEST_CODE = 1;
    private EditText mIntroductionView;
    private ImageButton mImageView;
    private EditText mDishNameView,foodca;
    private File file;
    //private String picPath;

    List<String> paths;

    MenuItem sendItem;

    ProgressBar progressBar;

    int po=0;

    String foodid;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        mIntroductionView = (EditText) findViewById(R.id.et_introduction);
        mImageView = (ImageButton) findViewById(R.id.ib_plus);
        mDishNameView = (EditText) findViewById(R.id.et_dish_name);
        mImageView = (ImageButton) findViewById(R.id.ib_plus);
        foodca= (EditText) findViewById(R.id.numofFood);

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paths = null;
                MultiImageSelector.create(PostActivity.this)
                        .showCamera(true) // show camera or not. true by default
                        .single() // single mode
                        .start(PostActivity.this, REQUEST_CODE);

            }
        });
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        setTitle("Upload the food");

        Intent intent=getIntent();
        String what= intent.getStringExtra("what");

        if(what.equals("1")){
            po=1;
            mDishNameView.setText(intent.getStringExtra("foodname"));
            mIntroductionView.setText(intent.getStringExtra("foodprice"));
            foodca.setText(intent.getStringExtra("foodcalorie"));
            foodid=intent.getStringExtra("id");

        }else{
            po=0;
        }

        HttpRequest.handler=new Handler(){
            @Override
            public void handleMessage(Message msg){
                if(msg.obj.equals(Code.fail)){
                    Toast.makeText(PostActivity.this,"request failed",Toast.LENGTH_SHORT).show();
                }else if(msg.obj.equals(Code.noRespond)){
                    Toast.makeText(PostActivity.this,"Request no response",Toast.LENGTH_SHORT).show();
                }else if(msg.obj.equals("1")){
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(PostActivity.this,"Save Success",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(PostActivity.this,UploadActivity.class));
                    PostActivity.this.finish();
                }else if(msg.obj.equals("0")){
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(PostActivity.this,"Save Fail",Toast.LENGTH_SHORT).show();
                }
                super.handleMessage(msg);
            }
        };
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Get the result list of select image paths
                paths = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                Bitmap bmImg = BitmapFactory.decodeFile(paths.get(0));
                mImageView.setImageBitmap(bmImg);
                sendItem.setEnabled(true);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(PostActivity.this,UploadActivity.class));
                PostActivity.this.finish();
                break;
            case R.id.action_done:
               if(mIntroductionView.getText().toString().equals("")|mDishNameView.getText().toString().equals("")|
                        foodca.getText().toString().equals("")){
                    Toast.makeText(PostActivity.this,"Content cannot be empty",Toast.LENGTH_SHORT).show();
                }else{
                    progressBar.setVisibility(View.VISIBLE);
                    sendWorks();
                }
                break;
        }
        return true;
    }

    private void sendWorks() {
        sendItem.setEnabled(false);
        if (paths == null) {
            //Toast.makeText(PostActivity.this, "The picture cannot be empty", Toast.LENGTH_SHORT).show();
            //return;
        }
        //File file = new File(paths.get(0));
        if (HttpUtils.isNetworkConnected(this)) {
            SharedPreferences sharedPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
            String account= sharedPreferences.getString("account","null");
            String address= sharedPreferences.getString("address","null");
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            NameValuePair pair1 = new BasicNameValuePair("useremail",account);
            params.add(pair1);
            NameValuePair pair2 = new BasicNameValuePair("foodtype",address);
            params.add(pair2);
            NameValuePair pair3 = new BasicNameValuePair("foodname",mDishNameView.getText().toString());
            params.add(pair3);
            NameValuePair pair4 = new BasicNameValuePair("foodprice",mIntroductionView.getText().toString());
            params.add(pair4);
            NameValuePair pair5 = new BasicNameValuePair("foodcalorie",foodca.getText().toString());
            params.add(pair5);
            if(po==0){
                HttpRequest.goPost(params, Constant.insertMenu,true);
                // insert menu method
            }else if(po==1){
                NameValuePair pair6 = new BasicNameValuePair("id",foodid);
                params.add(pair6);
                HttpRequest.goPost(params, Constant.updateMenu,true);
                //update method
            }

        } else {
            Toast.makeText(this, "There is no network connection!", Toast.LENGTH_LONG).show();
            //check if has internet
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(new Intent(PostActivity.this,UploadActivity.class));
            PostActivity.this.finish();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.post_menu, menu);
        sendItem = menu.findItem(R.id.action_done);
        sendItem.setEnabled(true);
        return true;
    }
}

