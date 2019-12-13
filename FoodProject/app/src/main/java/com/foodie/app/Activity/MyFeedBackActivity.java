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
import android.widget.TextView;
import android.widget.Toast;

import com.foodie.app.R;
import com.foodie.app.code.Code;
import com.foodie.app.util.Constant;
import com.foodie.app.util.HttpRequest;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyFeedBackActivity extends AppCompatActivity{

    private Toolbar mToolbar;
    private String id;
    private ListView mListView;
    ArrayList<Map<String, Object>> array;
    int possion=0;
    MyAdapter adapter;
    // private variables , arraylist and adapter
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aactivity_feedback);
        // connect with aactivity_feedback interface

        mListView=(ListView)findViewById(R.id.feedList);
        array = new ArrayList<Map<String, Object>>();
        // feed list

        initToolbar();
        goPost();

        HttpRequest.handler=new Handler(){
            @Override
            public void handleMessage(Message msg){
                if(msg.obj.equals(Code.fail)){
                    Toast.makeText(MyFeedBackActivity.this,"request failed",Toast.LENGTH_SHORT).show();
                }else if(msg.obj.equals(Code.noRespond)){
                    Toast.makeText(MyFeedBackActivity.this,"Request no response",Toast.LENGTH_SHORT).show();
                }else if(msg.obj.equals("delFeedSuccess")){
                    Toast.makeText(MyFeedBackActivity.this,"Delete Success",Toast.LENGTH_SHORT).show();
                }else if(msg.obj.equals("delFeedFail")){
                    Toast.makeText(MyFeedBackActivity.this,"Delete failed",Toast.LENGTH_SHORT).show();
                }else{
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
    public void MyListAdapter(String string) {


        Map<String, Object> map1 = new HashMap<String, Object>();
        try {
            JSONArray jsonArray = new JSONArray(string);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                map1  = new HashMap<String, Object>();
                map1.put("p2", jsonObject.getString("username"));
                map1.put("p3", jsonObject.getString("feedbackDate"));
                map1.put("p4", jsonObject.getString("suggestion"));
                map1.put("id", jsonObject.getString("id"));
                map1.put("foodname", jsonObject.getString("foodname"));
                array.add(map1);
                // JSONArray get ther information from database
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        adapter= new MyAdapter(this);
        mListView.setAdapter(adapter);
       /* listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
            }
        });*/

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                possion=i;
                new AlertDialog.Builder(MyFeedBackActivity.this)
                        .setTitle("Remind")
                        .setMessage("Delete?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                goDelete((String) array.get(possion).get("id"));
                                array.remove(possion);
                                adapter.notifyDataSetChanged();
                            }
                        })
                        // delete feedback , if selete yes the feedbacl will delete by ID
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        })
                        .show();
                return false;
            }
        });
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
            View myView = inflater.inflate(R.layout.list_adapter, null);

            TextView textView2 = (TextView) myView.findViewById(R.id.feedWho);
            TextView textView3 = (TextView) myView.findViewById(R.id.feedTime);
            TextView textView4 = (TextView) myView.findViewById(R.id.sugest);
            TextView foodnameSug = (TextView) myView.findViewById(R.id.foodnameSug);

            textView2.setText("UserName:"+(String) array.get(arg0).get("p2"));
            textView3.setText("Time:"+(String) array.get(arg0).get("p3"));
            textView4.setText("Suggest:"+(String) array.get(arg0).get("p4"));
            foodnameSug.setText("FoodName:"+(String) array.get(arg0).get("foodname"));
            return myView;
            //view the information of feedback like who post, the time, the content and others
        }
    }
    private void goDelete(String id){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        NameValuePair pair2 = new BasicNameValuePair("id", id);
        params.add(pair2);
        HttpRequest.goPost(params, Constant.delfeedBack,true);
        // delete by id
    }
    private void goPost(){
        SharedPreferences sharedPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
        String  account= sharedPreferences.getString("account","null");
        if(account.length()==0|account.equals("")|account.equals("null")){
            startActivity(new Intent(MyFeedBackActivity.this,LoginActivity.class));
            MyFeedBackActivity.this.finish();
            Toast.makeText(MyFeedBackActivity.this,"Login has expired, please login again",Toast.LENGTH_SHORT).show();
            // make sure if the account login expired
        }else{
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            NameValuePair pair1 = new BasicNameValuePair("useremail", account);
            params.add(pair1);
            HttpRequest.goPost(params, Constant.feedBackByEmail,true);
            // add the information in the databse
        }
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbarOfFeedBack);
        setSupportActionBar(mToolbar);
        final ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        setTitle("My Feedback History");
        // toobar ffedback history
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
