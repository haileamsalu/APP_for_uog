package com.example.uselogin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toolbar;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class Reading_Activity extends AppCompatActivity {
RecyclerView recyclerView;
ListViewAdapter listViewAdapter;
LinearLayout norelativeLayout;
RelativeLayout homeLayout;
ShimmerFrameLayout shimmerFrameLayout;
private ArrayList<uploadfilemodel> moredetails = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        homeLayout = findViewById(R.id.homeLayout);
        norelativeLayout = findViewById(R.id.homenoConnection);
        recyclerView = findViewById(R.id.productlist);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        LoaderManager.getInstance(this).initLoader(0,null,documentlist);
        shimmerFrameLayout = findViewById(R.id.shimmer_view_container);
        shimmerFrameLayout.startShimmer();
    }
    private LoaderManager.LoaderCallbacks<ArrayList<uploadfilemodel>> documentlist = new LoaderManager.LoaderCallbacks<ArrayList<uploadfilemodel>>() {
        @NonNull
        @Override
        public Loader<ArrayList<uploadfilemodel>> onCreateLoader(int id, @Nullable Bundle args) {
            return new fetchtableasyncktask(getApplicationContext());
        }

        @Override
        public void onLoadFinished(@NonNull Loader<ArrayList<uploadfilemodel>> loader, ArrayList<uploadfilemodel> data) {
            homeLayout.setVisibility(View.VISIBLE);
            shimmerFrameLayout.stopShimmer();
            shimmerFrameLayout.setVisibility(View.GONE);
            if (data != null) {
                moredetails = data;
               listViewAdapter = new ListViewAdapter(getApplicationContext(),moredetails);
                recyclerView.setAdapter(listViewAdapter);
            } else {
                norelativeLayout.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onLoaderReset(@NonNull Loader<ArrayList<uploadfilemodel>> loader) {

        }
    };
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            startActivity(new Intent(Reading_Activity.this, MainActivity.class));
            overridePendingTransition(0, R.anim.toptobottom_exit);
            finish();
        }
        return true;
    }
}
