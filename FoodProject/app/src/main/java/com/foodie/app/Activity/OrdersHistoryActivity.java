package com.foodie.app.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.EditText;
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

public class OrdersHistoryActivity extends AppCompatActivity{

    private Toolbar mToolbar;
    private String id;
    private ListView mListView;
    ArrayList<Map<String, Object>> array;
    int possion=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aactivity_orderhistory);

        mListView=(ListView)findViewById(R.id.ordersList);

        initToolbar();
        goPost();

        HttpRequest.handler=new Handler(){
            @Override
            public void handleMessage(Message msg){
                if(msg.obj.equals(Code.fail)){
                    Toast.makeText(OrdersHistoryActivity.this,"request failed",Toast.LENGTH_SHORT).show();
                }else if(msg.obj.equals(Code.noRespond)){
                    Toast.makeText(OrdersHistoryActivity.this,"Request no response",Toast.LENGTH_SHORT).show();
                }else if(msg.obj.equals("1")){
                    Toast.makeText(OrdersHistoryActivity.this,"Submit Success",Toast.LENGTH_SHORT).show();
                }else if(msg.obj.equals("0")){
                    Toast.makeText(OrdersHistoryActivity.this,"Submit fail",Toast.LENGTH_SHORT).show();
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

        array = new ArrayList<Map<String, Object>>();
        Map<String, Object> map1 = new HashMap<String, Object>();
        try {
            JSONArray jsonArray = new JSONArray(string);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                map1  = new HashMap<String, Object>();
                map1.put("p2", jsonObject.getString("foodname"));
                map1.put("p3", jsonObject.getString("foodtype"));
                map1.put("p4", jsonObject.getString("menuid"));
                map1.put("p5", jsonObject.getString("orderquantity"));
                map1.put("p6", jsonObject.getString("orderpayment"));
                map1.put("p7", jsonObject.getString("orderTime"));
                // get the drder information
                array.add(map1);
            }
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
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                possion=i;
                final EditText inputServer = new EditText(OrdersHistoryActivity.this);
                inputServer.setFocusable(true);
                new AlertDialog.Builder(OrdersHistoryActivity.this)
                        .setTitle("FeedBack")
                        // title
                        .setMessage("What do you want to say?")
                        // content of your feedback
                        .setView(inputServer)
                        .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                String inputName = inputServer.getText().toString();
                                String foodid=array.get(possion).get("p4").toString();
                                String foodname=array.get(possion).get("p2").toString();
                                //name foodid
                                if(inputName.equals("")){
                                    Toast.makeText(OrdersHistoryActivity.this,"Can not Null",Toast.LENGTH_SHORT).show();
                                    // can not be noon
                                }else{
                                    goFeedBack(foodid,inputName,foodname);
                                }
                            }
                        })
                        .setNegativeButton("Cancle", new DialogInterface.OnClickListener()
                            //cancle button
                        {
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
            View myView = inflater.inflate(R.layout.list_adapter3, null);

            TextView textView2 = (TextView) myView.findViewById(R.id.textTitle);
            TextView textView3 = (TextView) myView.findViewById(R.id.foodname);
            TextView textView4 = (TextView) myView.findViewById(R.id.feedAddress);
            TextView textView5 = (TextView) myView.findViewById(R.id.orderquantity);
            TextView textView6 = (TextView) myView.findViewById(R.id.orderpayment);

            textView2.setText("Time:"+(String) array.get(arg0).get("p7"));
            textView3.setText("FoodName:"+(String) array.get(arg0).get("p2"));
            textView5.setText("OrderQuantity:"+(String) array.get(arg0).get("p5"));
            textView6.setText("OrderPayment:"+(String) array.get(arg0).get("p6"));
            if(array.get(arg0).get("p3").equals("1")){
                textView4.setText("Adress:NorthCampus");
            }else if(array.get(arg0).get("p3").equals("2")){
                textView4.setText("Adress:CentralCampus");
            }else if(array.get(arg0).get("p3").equals("3")){
                textView4.setText("Adress:SouthCampus");
            }
 // get the view of order information
            return myView;
        }
    }

    private void goPost(){
        SharedPreferences sharedPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
        String  account= sharedPreferences.getString("account","null");

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        NameValuePair pair1 = new BasicNameValuePair("useremail", account);
        params.add(pair1);
        HttpRequest.goPost(params, Constant.orderList,true);
        // post method and get the order list
    }

    private void goFeedBack(String id,String said,String foodname){
        SharedPreferences sharedPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
        String  account= sharedPreferences.getString("account","null");
        String  name= sharedPreferences.getString("nickname","null");
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        NameValuePair pair1 = new BasicNameValuePair("useremail", account);
        params.add(pair1);
        NameValuePair pair2 = new BasicNameValuePair("username", name);
        params.add(pair2);
        NameValuePair pair3 = new BasicNameValuePair("foodid", id);
        params.add(pair3);
        NameValuePair pair4 = new BasicNameValuePair("foodname", foodname);
        params.add(pair4);
        NameValuePair pair5 = new BasicNameValuePair("suggestion", said);
        params.add(pair5);
        HttpRequest.goPost(params, Constant.insertFeedBack,true);
        // feedback method
    }
    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbarOfFeedBack);
        setSupportActionBar(mToolbar);
        final ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        setTitle("Purchase history");
        // purchase history toolbar
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
            onBackPressed();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
