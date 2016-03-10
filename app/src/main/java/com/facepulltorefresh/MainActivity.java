package com.facepulltorefresh;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.pulltorefreshlibrary.FacePullToRefreshLayout;

import in.srain.cube.views.ptr.PtrFrameLayout;

public class MainActivity extends AppCompatActivity {
    private final static String Tag = MainActivity.class.getSimpleName();
    private FacePullToRefreshLayout facePullToRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        facePullToRefreshLayout = (FacePullToRefreshLayout) findViewById(R.id.face_pull_to_refresh);

        facePullToRefreshLayout.setLastUpdateTimeRelateObject(this);
        facePullToRefreshLayout.setRefreshListener(new FacePullToRefreshLayout.RefreshListener() {
            @Override
            public void onRefresh(PtrFrameLayout frame) {
                facePullToRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.e(Tag, "刷新...");
                        facePullToRefreshLayout.refreshComplete();
                    }
                }, 3000);
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
