package com.foodie.app.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.etsy.android.grid.StaggeredGridView;
import com.foodie.app.Activity.LoginActivity;
import com.foodie.app.Activity.RestaurantActivity;
import com.foodie.app.R;
import com.foodie.app.adapter.DishListAdapter;
import com.foodie.app.model.DishInfo;
import com.foodie.app.view.Kanner;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment{
    protected static final String TAG="HomeFragment";

    private StaggeredGridView dishListView;
    private Kanner kanner;

    private DishListAdapter dishListAdapter;

    private List<DishInfo> dishes=null;

    private SwipeRefreshLayout sr;

    private ProgressBar progressBar;
    // private variables
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(
                R.layout.fragment_home, container, false);
        dishListView= (StaggeredGridView) view.findViewById(R.id.channel_dish_list);
        sr = (SwipeRefreshLayout) view.findViewById(R.id.sr);
        View header = inflater.inflate(R.layout.kanner, dishListView, false);
        kanner = (Kanner) header.findViewById(R.id.kanner);
        dishListView.addHeaderView(header);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);

        CardView south=(CardView)view.findViewById(R.id.South);
        CardView north=(CardView)view.findViewById(R.id.North);
        CardView center=(CardView)view.findViewById(R.id.Central);

        south.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start("south");
            }
        });
        north.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start("north");
            }
        });
        center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start("center");
            }
        });
        // three campus

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        String account= sharedPreferences.getString("account","null");
        Log.i("account",account);
        // get the email from login class
        if(account.length()==0|account.equals("")|account.equals("null")){
            startActivity(new Intent(getActivity(),LoginActivity.class));
            getActivity().finish();
            Toast.makeText(getActivity(),"Login has expired, please login again",Toast.LENGTH_SHORT).show();
            //login in ecpired
        }

        return view;
    }

    private void  start(String what){
        Intent intent=new Intent(getActivity(), RestaurantActivity.class);
        intent.putExtra("what",what);
        startActivity(intent);
        getActivity().finish();
        // start method
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        int[] imagesRes = {R.drawable.ice, R.drawable.ping, R.drawable.cei, R.drawable.niangao};
        kanner.setImagesRes(imagesRes);
        updateDishes();
        sr.setColorSchemeResources(
                R.color.colorPrimary,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        sr.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateDishes();
                sr.setRefreshing(false);
            }
        });
    }

    private void updateDishes() {
        dishes=new ArrayList<DishInfo>();
        progressBar.setVisibility(View.GONE);
        dishListAdapter = new DishListAdapter(getActivity(),dishes);
        dishListAdapter.setOnItemClickListener(new DishListAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
            }
        });
        dishListView.setAdapter(dishListAdapter);
    }
}
