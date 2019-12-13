package com.foodie.app.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.foodie.app.R;
import com.foodie.app.code.Code;
import com.foodie.app.util.Constant;
import com.foodie.app.util.HttpRequest;
import com.github.clans.fab.FloatingActionButton;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UploadActivity extends AppCompatActivity{
    private Toolbar mToolbar;
    private ListView mListView;
    ArrayList<Map<String, Object>> array;
    private ProgressBar mProgressBar;
    private FloatingActionButton fab;
    MyAdapter adapter;
    int possion=0;
    // private variables
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
// connect with activity_upload interface
        mListView = (ListView) findViewById(R.id.upList);
        mProgressBar=(ProgressBar)findViewById(R.id.progress_bar);
        fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(UploadActivity.this,PostActivity.class);
                intent.putExtra("what","0");
                startActivity(intent);
                UploadActivity.this.finish();
            }
        });

        array = new ArrayList<Map<String, Object>>();
        initToolbar();

        goPost();
        HttpRequest.handler=new Handler(){
            @Override
            public void handleMessage(Message msg){
                if(msg.obj.equals(Code.fail)){
                    Toast.makeText(UploadActivity.this,"request failed",Toast.LENGTH_SHORT).show();
                }else if(msg.obj.equals(Code.noRespond)){
                    Toast.makeText(UploadActivity.this,"Request no response",Toast.LENGTH_SHORT).show();
                }else if(msg.obj.equals(Code.noRespond)){
                    Toast.makeText(UploadActivity.this,"Request no response",Toast.LENGTH_SHORT).show();
                }else if(msg.obj.equals("1")){
                    Toast.makeText(UploadActivity.this,"Delete Success",Toast.LENGTH_SHORT).show();
                }else if(msg.obj.equals("0")){
                    Toast.makeText(UploadActivity.this,"Delete Fail",Toast.LENGTH_SHORT).show();
                }else{
                    mProgressBar.setVisibility(View.INVISIBLE);
                    try{
                        MyListAdapter(msg.obj.toString());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                super.handleMessage(msg);
            }
        };
    }
// handler method
    private void goPost(){
        SharedPreferences sharedPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
        String account= sharedPreferences.getString("account","null");
        if(account.length()==0|account.equals("")|account.equals("null")){
            startActivity(new Intent(UploadActivity.this,LoginActivity.class));
            UploadActivity.this.finish();
            Toast.makeText(UploadActivity.this,"Login has expired, please login again",Toast.LENGTH_SHORT).show();
            // gopost method
        }else{
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            NameValuePair pair1 = new BasicNameValuePair("useremail", account);
            params.add(pair1);
            HttpRequest.goPost(params, Constant.GetListByUser,true);
        }
    }

    private void initToolbar() {
        mToolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        final ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        setTitle("Upload Menu History");
        //initToolbar method d
    }

    public void MyListAdapter(String string) {

        Map<String, Object> map1 = new HashMap<String, Object>();
        try {
            JSONArray jsonArray = new JSONArray(string);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                map1  = new HashMap<String, Object>();
                map1.put("p2", jsonObject.getString("foodname"));
                map1.put("p3", jsonObject.getString("uptime"));
                map1.put("p4", jsonObject.getString("foodtype"));
                map1.put("id", jsonObject.getString("id"));
                map1.put("p5", jsonObject.getString("foodprice"));
                map1.put("p6", jsonObject.getString("foodcalorie"));
                array.add(map1);
                // get string
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        adapter = new MyAdapter(UploadActivity.this);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent=new Intent(UploadActivity.this, PostActivity.class);
                intent.putExtra("id", (String)array.get(position).get("id"));
                intent.putExtra("what","1");
                intent.putExtra("foodname",(String)array.get(position).get("p2"));
                intent.putExtra("foodprice",(String)array.get(position).get("p5"));
                intent.putExtra("foodcalorie",(String)array.get(position).get("p6"));
                startActivity(intent);
                UploadActivity.this.finish();
                //setOnItemClickListener method
            }
        });

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                possion=i;
                new AlertDialog.Builder(UploadActivity.this)
                        .setTitle("Remind")
                        .setMessage("Delete?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                goDelete((String) array.get(possion).get("id"));
                                array.remove(possion);
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        })
                        .show();
                return false;
            }
        });
    }
    private void goDelete(String id){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        NameValuePair pair2 = new BasicNameValuePair("id", id);
        params.add(pair2);
        HttpRequest.goPost(params, Constant.delMenu,true);
        //delete method
    }
    public class MyAdapter extends BaseAdapter {
        private LayoutInflater inflater;

        public MyAdapter(Context c) {
            this.inflater = LayoutInflater.from(c);
        }

        @Override
        public int getCount() {
            return array.size();
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return 0;
        }
        @Override
        public View getView(int arg0, View arg1, ViewGroup arg2) {
            View myView = inflater.inflate(R.layout.list_adapter4, null);

            TextView textView2 = (TextView) myView.findViewById(R.id.feedWhoAd);
            TextView textView3 = (TextView) myView.findViewById(R.id.feedTimeAd);
            TextView textView4 = (TextView) myView.findViewById(R.id.sugestAD);
            TextView textTitle = (TextView) myView.findViewById(R.id.textTitle);

            TextView foodprice = (TextView) myView.findViewById(R.id.foodpriceAd);
            TextView foodcalorie = (TextView) myView.findViewById(R.id.foodcalorieAd);

            textTitle.setText(Integer.toString(arg0+1));
            textView2.setText("FoodName:"+(String) array.get(arg0).get("p2"));
            textView3.setText("Time:"+(String) array.get(arg0).get("p3"));
            if(array.get(arg0).get("p4").equals("1")){
                textView4.setText("Restaurant:NorthCampus ");
            }else if(array.get(arg0).get("p4").equals("2")){
                textView4.setText("Restaurant:CentralCampus ");
            }else if(array.get(arg0).get("p4").equals("3")){
                textView4.setText("Restaurant:SouthCampus ");
            }
            foodcalorie.setText("FoodCalorie:"+array.get(arg0).get("p6"));
            foodprice.setText("FoodPrice:"+array.get(arg0).get("p5"));
            return myView;
            // view method
        }
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            UploadActivity.this.finish();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
