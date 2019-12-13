/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.foodie.app.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.foodie.app.Activity.DishInfoActivity;
import com.foodie.app.Activity.LoginActivity;
import com.foodie.app.Activity.PostActivity;
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

public class Collect extends Fragment {
    protected static final String TAG="MessageFragment";
    private FloatingActionButton fab;

    private ListView mListView;
    ArrayList<Map<String, Object>> array;
    int possion=0;
    MyAdapter adapter;
    private String account;

    private ProgressBar mProgressBar;
    // private variables

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = (View) inflater.inflate(
                R.layout.fragment_message, container, false);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setVisibility(View.INVISIBLE);

        mListView=(ListView)view.findViewById(R.id.collectList);
        mProgressBar=(ProgressBar)view.findViewById(R.id.progress_bar);

        array = new ArrayList<Map<String, Object>>();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        account= sharedPreferences.getString("account","null");
        if(account.length()==0|account.equals("")|account.equals("null")){
            startActivity(new Intent(getActivity(),LoginActivity.class));
            getActivity().finish();
            Toast.makeText(getActivity(),"Login has expired, please login again",Toast.LENGTH_SHORT).show();
        }
        goPost(account);

        HttpRequest.handler=new Handler(){
            @Override
            public void handleMessage(Message msg){
                if(msg.obj.equals(Code.fail)){
                    Toast.makeText(getActivity(),"request failed",Toast.LENGTH_SHORT).show();
                }else if(msg.obj.equals(Code.noRespond)){
                    Toast.makeText(getActivity(),"Request no response",Toast.LENGTH_SHORT).show();
                }else if(msg.obj.equals("delSuccess")){
                    Toast.makeText(getActivity(),"Delete Success",Toast.LENGTH_SHORT).show();
                }else if(msg.obj.equals("delFail")){
                    Toast.makeText(getActivity(),"Delete failed",Toast.LENGTH_SHORT).show();
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
        return view;
    }
    // hndler method
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PostActivity.class));
            }
        });

    }
    private void goDelete(String id){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        NameValuePair pair2 = new BasicNameValuePair("id", id);
        params.add(pair2);
        HttpRequest.goPost(params, Constant.delCollect,true);
        //goDelete method by Id
    }
    private void goPost(String account){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        NameValuePair pair2 = new BasicNameValuePair("useremail", account);
        params.add(pair2);
        HttpRequest.goPost(params, Constant.collectList,true);
    }
    public void MyListAdapter(String string) {

        Map<String, Object> map1 = new HashMap<String, Object>();
        try {
            JSONArray jsonArray = new JSONArray(string);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                map1  = new HashMap<String, Object>();
                map1.put("p2", jsonObject.getString("foodname"));
                map1.put("p3", jsonObject.getString("collectTime"));
                map1.put("p4", jsonObject.getString("foodtype"));
                map1.put("foodId", jsonObject.getString("menuid"));
                map1.put("id", jsonObject.getString("id"));
                array.add(map1);
                // get the information by Jason
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        adapter = new MyAdapter(getActivity());
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent=new Intent(getActivity(), DishInfoActivity.class);
                intent.putExtra("id", (String)array.get(position).get("foodId"));
                intent.putExtra("foodtype",(String)array.get(position).get("p4"));
                intent.putExtra("foodname",(String)array.get(position).get("p2"));
                startActivity(intent);
                // get the information from another class
            }
        });

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                    possion=i;
                new AlertDialog.Builder(getActivity())
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
            View myView = inflater.inflate(R.layout.list_adapter2, null);

            TextView textView2 = (TextView) myView.findViewById(R.id.feedWho);
            TextView textView3 = (TextView) myView.findViewById(R.id.feedTime);
            TextView textView4 = (TextView) myView.findViewById(R.id.sugest);
            TextView textTitle = (TextView) myView.findViewById(R.id.textTitle);

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

            return myView;
        }
    }

    }
