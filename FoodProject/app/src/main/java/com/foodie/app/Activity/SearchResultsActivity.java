package com.foodie.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.foodie.app.R;


public class SearchResultsActivity extends AppCompatActivity {
    private String query;
    FloatingSearchView mFloatingSearchView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        query = getIntent().getStringExtra("query");
        mFloatingSearchView = (FloatingSearchView) findViewById(R.id.floating_search_view);
        mFloatingSearchView.setOnHomeActionClickListener(new FloatingSearchView.OnHomeActionClickListener() {
            @Override
            public void onHomeClicked() {
                onBackPressed();
            }
        });
        mFloatingSearchView.setOnMenuItemClickListener(new FloatingSearchView.OnMenuItemClickListener() {
            @Override
            public void onActionMenuItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.id_action_search:
                        doSearch(mFloatingSearchView.getQuery().toString());
                        break;
                }
            }
        });
    }

    private void doSearch(String query) {
        if(query.equals("")){
            Toast.makeText(SearchResultsActivity.this, "The query content must not be empty", Toast.LENGTH_SHORT).show();

        }else{
            Intent intent =new Intent(SearchResultsActivity.this,SearchFoodActivity.class);
            intent.putExtra("query",query);
            startActivity(intent);
        }

    }

}
