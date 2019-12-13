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

package com.foodie.app.Activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.foodie.app.R;
import com.foodie.app.fragment.HomeFragment;
import com.foodie.app.fragment.MeFragment;
import com.foodie.app.fragment.Collect;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private static String TAG = "MainActivity";
    private BottomBar mBottomBar;
    private ViewPager viewPager;

    private Toolbar mToolbar;

    private int currentTab;

    private long firstTime;

    private Adapter mAdapter;

// private variables

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
// connect with activity_main interface
        if (savedInstanceState!=null){
            currentTab=savedInstanceState.getInt("currentTab");
        }else{
            currentTab=0;
        }
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setCollapsible(false);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }

        mBottomBar = BottomBar.attach(this, savedInstanceState);
        mBottomBar.useFixedMode();
        mBottomBar.setItems(R.menu.bottombar_menu);
        mBottomBar.setOnMenuTabClickListener(new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {

                switch (menuItemId) {
                    case R.id.bottomBarHome:
                        viewPager.setCurrentItem(0);
                        currentTab = 0;
                        invalidateOptionsMenu();
                        break;
                   /* case R.id.bottomBarLocation:
                        viewPager.setCurrentItem(1);
                        currentTab = 1;
                        invalidateOptionsMenu();
                        break;*/
                    case R.id.bottomBarMessage:
                        viewPager.setCurrentItem(1);
                        currentTab = 1;
                        invalidateOptionsMenu();
                        break;
                    case R.id.bottomBarMe:
                        viewPager.setCurrentItem(2);
                        currentTab = 2;
                        invalidateOptionsMenu();
                        break;

                }
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {
                if (menuItemId == R.id.bottomBarHome) {
                    // The user reselected item number one, scroll your content to top.
                }
            }
        });

        mBottomBar.mapColorForTab(0, 0xFF5D4037);
        mBottomBar.mapColorForTab(1, "#FF5252");
        mBottomBar.mapColorForTab(2, "#FF9800");
        currentTab=getIntent().getIntExtra("currentTab",currentTab);
        viewPager.setCurrentItem(currentTab);
        mBottomBar.selectTabAtPosition(currentTab,false);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_actions, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchMenuItem = menu.findItem(R.id.id_action_search);
        SearchView searchView = null;
        if (searchMenuItem != null) {
            searchView = (SearchView) searchMenuItem.getActionView();
            // search method
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(MainActivity.this.getComponentName()));
        }
        //search view method
        SearchView.SearchAutoComplete searchAutoComplete = (SearchView.SearchAutoComplete) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchAutoComplete.setHintTextColor(Color.WHITE);
        searchAutoComplete.setTextColor(Color.WHITE);
        searchAutoComplete.setHint("Search for food");
// search food by letter or name then will show you the food which you like
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(query.equals("")){
                    Toast.makeText(MainActivity.this, "The query content must not be empty", Toast.LENGTH_SHORT).show();

                }else{
                    Intent intent =new Intent(MainActivity.this,SearchFoodActivity.class);
                    intent.putExtra("query",query);
                    startActivity(intent);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        ImageView searchCloseIcon = (ImageView) searchView.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
        searchCloseIcon.setImageResource(R.drawable.ic_close_white_24dp);

        ImageView voiceIcon = (ImageView) searchView.findViewById(android.support.v7.appcompat.R.id.search_voice_btn);
        voiceIcon.setImageResource(R.drawable.ic_settings_voice_white_24dp);

        //searchView.setSubmitButtonEnabled(true); // to enable submit button

        ImageView b = (ImageView) searchView.findViewById(android.support.v7.appcompat.R.id.search_button);
        b.setImageResource(R.drawable.ic_search_white_24dp); //to change submit button icon

        ImageView searchIcon = (ImageView) searchView.findViewById(android.support.v7.appcompat.R.id.search_mag_icon);
        searchIcon.setImageResource(R.drawable.ic_search_white_24dp);

        switch (currentTab) {
            case 0:
                MenuItem miMap = menu.findItem(R.id.id_action_map);
                miMap.setVisible(false);
                break;
             case 1:
                 MenuItem map = menu.findItem(R.id.id_action_map);
                 map.setVisible(false);
                MenuItem miSearch = menu.findItem(R.id.id_action_search);
                miSearch.setVisible(false);
                break;
             case 2:
                MenuItem mi1 = menu.findItem(R.id.id_action_map);
                mi1.setVisible(false);
                MenuItem mi2 = menu.findItem(R.id.id_action_search);
                mi2.setVisible(false);
            case 3:
                MenuItem mi3 = menu.findItem(R.id.id_action_map);
                mi3.setVisible(false);
                MenuItem mi4 = menu.findItem(R.id.id_action_search);
                mi4.setVisible(false);
                break;
            default:
                break;
        }
// switch burrenttab method
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                return true;

            case R.id.action_settings:
                startActivity(new Intent(this, SettingActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager){

        mAdapter = new Adapter(getSupportFragmentManager());
        mAdapter.addFragment(new HomeFragment());
        mAdapter.addFragment(new Collect());
        mAdapter.addFragment(new MeFragment());
        viewPager.setAdapter(mAdapter);
        // three button in the bottom
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment) {
            mFragments.add(fragment);
        }
        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
        @Override
        public int getCount() {
            return mFragments.size();
        }
        // adpater method
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        mBottomBar.onSaveInstanceState(outState);
        outState.putInt("currentTab",this.currentTab);
        //save onSaveInstanceState method
    }

    @Override
    public void onBackPressed() {
        long secondTime = System.currentTimeMillis();
        if (secondTime - firstTime > 2000) {
            Toast.makeText(this, "Press the exit again", Toast.LENGTH_LONG).show();
            firstTime = secondTime;
        } else {
            finish();
        }
    }
}
