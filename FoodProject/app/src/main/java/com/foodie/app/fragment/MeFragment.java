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
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.foodie.app.Activity.LoginActivity;
import com.foodie.app.Activity.ModifyUserActivity;
import com.foodie.app.Activity.MyFeedBackActivity;
import com.foodie.app.Activity.OrdersHistoryActivity;
import com.foodie.app.Activity.SettingActivity;
import com.foodie.app.Activity.TabActivity;
import com.foodie.app.Activity.UploadActivity;
import com.foodie.app.Json.JsonResult;
import com.foodie.app.R;
import com.foodie.app.model.User;
import com.foodie.app.util.HttpUtils;
import com.foodie.app.util.PrefUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.lang.reflect.Type;

import cz.msebera.android.httpclient.Header;


public class MeFragment extends Fragment {

    private User user=null;
    private Button mLoginButton;
    private TextView mNicknameView;
    private TextView mPhoneView;
    private Boolean isUserLogin=false;
    private RoundedImageView mUserAvatorView;
    private RoundedImageView mUserDefaultView;
    private RelativeLayout mSettingView;

    private TextView mFansView;
    private TextView mFollowView;

    private RelativeLayout mUserLoginView;
    private RelativeLayout mUserNotLoginView;

    private RelativeLayout mWorksView;
    private RelativeLayout mCollecttionView,insertMenu,outView;
    // private variables

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.fragment_me, container, false);
        mLoginButton = (Button) view.findViewById(R.id.btn_login_register);
        mNicknameView=(TextView)view.findViewById(R.id.me_nickname);
        mUserLoginView= (RelativeLayout) view.findViewById(R.id.user_login);
        insertMenu=(RelativeLayout) view.findViewById(R.id.insertMenu);
        outView=(RelativeLayout) view.findViewById(R.id.outView);
        mUserNotLoginView= (RelativeLayout) view.findViewById(R.id.user_not_login);
        mPhoneView= (TextView) view.findViewById(R.id.me_phone);
        mUserAvatorView= (RoundedImageView) view.findViewById(R.id.user_image);
        mUserDefaultView= (RoundedImageView) view.findViewById(R.id.default_user_image);
        mSettingView = (RelativeLayout) view.findViewById(R.id.view_setting);
        mFansView = (TextView) view.findViewById(R.id.me_fans_count);
        mFollowView = (TextView) view.findViewById(R.id.me_follow_count);
        mWorksView = (RelativeLayout) view.findViewById(R.id.view_my_work);
        mCollecttionView = (RelativeLayout) view.findViewById(R.id.view_favorite);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        String  account= sharedPreferences.getString("account","null");
        mLoginButton.setText("User:"+account);
        String userType= sharedPreferences.getString("userType","null");
        if(userType.equals("0")){
            insertMenu.setVisibility(View.GONE);
        }else if(userType.equals("1")){
            insertMenu.setVisibility(View.VISIBLE);
        }

        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                /*Intent loginIntent=new Intent(getActivity(), LoginActivity.class);
                startActivityForResult(loginIntent,1);*/
            }
        });
        mUserDefaultView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent loginIntent=new Intent(getActivity(), LoginActivity.class);
                startActivityForResult(loginIntent,1);*/
            }
        });
        insertMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(getActivity(), UploadActivity.class));
            }
        });
        outView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Remind")
                        .setMessage("Sign out?")
                        // sign out
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("account", "null");
                                editor.putString("nickname", "null");
                                editor.putString("userType", "null");
                                editor.putString("address", "null");
                                editor.commit();
                                startActivity(new Intent(getActivity(),LoginActivity
                                .class));
                                getActivity().finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        })
                        .show();
            }
        });
        mFollowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TabActivity.class);
                intent.putExtra("currentTab", 2);
                startActivity(intent);
            }
        });
        mWorksView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyFeedBackActivity.class);
                //intent.putExtra("currentTab", 1);
                startActivity(intent);
            }
        });
        mCollecttionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OrdersHistoryActivity.class);
                //intent.putExtra("currentTab", 0);
                startActivity(intent);
            }
        });
        String userId=PrefUtils.get("user","userId",getActivity());
        if(userId!=null){
            isUserLogin=true;
            updateUserInfo(userId);
        }
        mSettingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch(requestCode){
            case 1:
                boolean result = intent.getExtras().getBoolean("result");
                if (result==true){
                    String userId=PrefUtils.get("user","userId",getActivity());
                    if(userId!=null){
                        isUserLogin=true;
                        updateUserInfo(userId);
                    }
                }
                break;
            case 2:
        }

    }

    private void updateUserInfo(String userId){
        if (HttpUtils.isNetworkConnected(getActivity())) {
            if(userId!=null&&!TextUtils.equals(userId,"")){
                HttpUtils.get("user/"+userId, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        Gson gson=new Gson();
                        Type type = new TypeToken<JsonResult<User>>(){}.getType();
                        JsonResult<User> jsonResult=gson.fromJson(new String(responseBody),type);
                        //String status=jsonResult.getStatus();
                        user=jsonResult.getData();
                        isUserLogin=true;
                        mUserNotLoginView.setVisibility(View.GONE);
                        mUserLoginView.setVisibility(View.VISIBLE);
                        mNicknameView.setText(user.getNickname());
                        mPhoneView.setText(user.getPhone());
                        DisplayImageOptions userImageOptions = new DisplayImageOptions.Builder()
                                .showImageOnLoading(R.drawable.loading_small)
                                .showImageOnFail(R.drawable.user)
                                .cacheInMemory(true)
                                .cacheOnDisk(true)
                                .bitmapConfig(Bitmap.Config.RGB_565)
                                .build();

                        ImageLoader.getInstance().displayImage(user.getAvator(), mUserAvatorView, userImageOptions);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Toast.makeText(getActivity(),"The server is busy"+new String(responseBody),Toast.LENGTH_LONG).show();
                    }
                });
                HttpUtils.getWithAuth(getActivity().getApplicationContext(), "user/fanscount/", new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        Gson gson = new Gson();
                        Type type = new TypeToken<JsonResult<Integer>>() {
                        }.getType();
                        JsonResult<Integer> jsonResult = gson.fromJson(new String(responseBody), type);
                        String status = jsonResult.getStatus();
                        int fansCount = 0;
                        if (jsonResult.getData() != null) {
                            fansCount = jsonResult.getData();
                        }
                        mFansView.setText(fansCount + "");
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Toast.makeText(getActivity(), "Please check the network" + new String(responseBody), Toast.LENGTH_LONG).show();
                    }
                });
                HttpUtils.getWithAuth(getActivity().getApplicationContext(), "user/followcount/", new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        Gson gson = new Gson();
                        Type type = new TypeToken<JsonResult<Integer>>() {
                        }.getType();
                        JsonResult<Integer> jsonResult = gson.fromJson(new String(responseBody), type);
                        String status = jsonResult.getStatus();
                        int followCount = 0;
                        if (jsonResult.getData() != null) {
                            followCount = jsonResult.getData();
                        }
                        mFollowView.setText(followCount + "");
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Toast.makeText(getActivity(), "Please check the network" + new String(responseBody), Toast.LENGTH_LONG).show();
                    }
                });
            }else{
                Toast.makeText(getActivity(),"Please login or register",Toast.LENGTH_LONG).show();
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }

        }else{
            Toast.makeText(getActivity(), "There is no network connection!", Toast.LENGTH_LONG).show();
        }
    }
}
