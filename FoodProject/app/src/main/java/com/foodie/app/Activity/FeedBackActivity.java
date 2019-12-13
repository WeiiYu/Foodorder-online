package com.foodie.app.Activity;

import android.content.Context;
import android.content.Intent;
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

public class FeedBackActivity extends AppCompatActivity{

    private Toolbar mToolbar;
    private String id;
    private ListView mListView;
    ArrayList<Map<String, Object>> array;
    // three private variables and one array eith json
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aactivity_feedback);
        // connext with activity_feedback interface

        mListView=(ListView)findViewById(R.id.feedList);
// view by ID
        initToolbar();
        Intent intent=getIntent();
        id=intent.getStringExtra("id");
        goPost(id);
        // intent id from another class

        HttpRequest.handler=new Handler(){
            @Override
            public void handleMessage(Message msg){
                if(msg.obj.equals(Code.fail)){
                    Toast.makeText(FeedBackActivity.this,"request failed",Toast.LENGTH_SHORT).show();
                    // if request fail, you can not do the action
                }else if(msg.obj.equals(Code.noRespond)){
                    Toast.makeText(FeedBackActivity.this,"Request no response",Toast.LENGTH_SHORT).show();
                    // no respond
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
    //httprequest handler method

    public void MyListAdapter(String string) {

        array = new ArrayList<Map<String, Object>>();
        Map<String, Object> map1 = new HashMap<String, Object>();
        try {
            JSONArray jsonArray = new JSONArray(string);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                map1  = new HashMap<String, Object>();

                map1.put("p2", jsonObject.getString("username"));
                map1.put("p3", jsonObject.getString("feedbackDate"));
                map1.put("p4", jsonObject.getString("suggestion"));
                map1.put("foodname", jsonObject.getString("foodname"));
                array.add(map1);
            }
            //use the jason get the data from database
        }catch (Exception e){
            e.printStackTrace();
        }

        MyAdapter adapter = new MyAdapter(this);
        mListView.setAdapter(adapter);
       /* listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
            }
        });*/
    }

    public class MyAdapter extends BaseAdapter {
        private LayoutInflater inflater;

        public MyAdapter(Context c) {
            this.inflater = LayoutInflater.from(c);
        }
        //MyAdapter method

        @Override
        public int getCount() {
            return array.size();
        }
        //get method

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return null;
        }//get item method

        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return 0;
        }
        //get item id method

        @Override

        public View getView(int arg0, View arg1, ViewGroup arg2) {
            View myView = inflater.inflate(R.layout.list_adapter, null);

            TextView textView2 = (TextView) myView.findViewById(R.id.feedWho);
            TextView textView3 = (TextView) myView.findViewById(R.id.feedTime);
            TextView textView4 = (TextView) myView.findViewById(R.id.sugest);
            TextView foodnameSug = (TextView) myView.findViewById(R.id.foodnameSug);
// view the information of feedback
            textView2.setText("UserName:"+(String) array.get(arg0).get("p2"));
            textView3.setText("Time:"+(String) array.get(arg0).get("p3"));
            textView4.setText("Suggest:"+(String) array.get(arg0).get("p4"));
            foodnameSug.setText("FoodName:"+(String) array.get(arg0).get("foodname"));
            //get the information of feedback from array
            return myView;
        }
    }

    private void goPost(String id){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        NameValuePair pair1 = new BasicNameValuePair("foodid", id);
        params.add(pair1);
        HttpRequest.goPost(params, Constant.feedBackByMenuId,true);
        //gopost method
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbarOfFeedBack);
        setSupportActionBar(mToolbar);
        final ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        setTitle("Feedback History");
        //view the feednack history
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return false;

    }
//onOptionsItemSelected method
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
    //onKeyDown Method
}
