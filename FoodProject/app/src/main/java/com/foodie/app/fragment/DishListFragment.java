package com.foodie.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.etsy.android.grid.StaggeredGridView;
import com.foodie.app.Activity.DishInfoActivity;
import com.foodie.app.Json.JsonListResult;
import com.foodie.app.Json.Result;
import com.foodie.app.R;
import com.foodie.app.adapter.DishListAdapter;
import com.foodie.app.model.DishInfo;
import com.foodie.app.util.HttpUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.lang.reflect.Type;
import java.util.List;

import cz.msebera.android.httpclient.Header;


public class DishListFragment extends Fragment {
    protected static final String TAG = "HomeFragment";

    private static final String QUERY = "";

    private StaggeredGridView dishListView;

    private DishListAdapter dishListAdapter;

    private List<DishInfo> dishes = null;

    private String query;

    private SwipeRefreshLayout sr;

    private ProgressBar progressBar;

    public static DishListFragment newInstance(String query) {
        DishListFragment fragment = new DishListFragment();
        Bundle args = new Bundle();
        args.putString(QUERY, query);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.fragment_home, container, false);
        dishListView = (StaggeredGridView) view.findViewById(R.id.channel_dish_list);
        sr = (SwipeRefreshLayout) view.findViewById(R.id.sr);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
        if (HttpUtils.isNetworkConnected(getActivity())) {
            HttpUtils.getWithAuth(getContext().getApplicationContext(), "dish/search/" + query + "/", new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Gson gson = new Gson();
                    //Type type = new TypeToken<JsonResult<User>>(){}.getType();
                    Type type = new TypeToken<JsonListResult<DishInfo>>() {
                    }.getType();
                    JsonListResult<DishInfo> jsonListResult = gson.fromJson(new String(responseBody), type);
                    String status = jsonListResult.getStatus();
                    dishes = jsonListResult.getData();
                    //Log.v(TAG,data);
                    if (TextUtils.equals(status, Result.SUCCESS) && dishes.size() > 0) {
                        Log.i(TAG, new String(responseBody));
                        progressBar.setVisibility(View.GONE);
                        dishListAdapter = new DishListAdapter(getActivity(), dishes);
                        dishListAdapter.setOnItemClickListener(new DishListAdapter.MyItemClickListener() {
                            @Override
                            public void onItemClick(View view, int postion) {
                                Intent intent = new Intent(getActivity(), DishInfoActivity.class);
                                intent.putExtra("dishInfo", dishListAdapter.getDish(postion));
                                getActivity().startActivity(intent);
                            }
                        });
                        dishListView.setAdapter(dishListAdapter);
                        return;
                    } else if (TextUtils.equals(status, Result.SUCCESS) && dishes.size() == 0) {
                    } else {
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Toast.makeText(getActivity(), "Please check the network", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            Toast.makeText(getActivity(), "There is no network connection!", Toast.LENGTH_LONG).show();
        }
    }

}
