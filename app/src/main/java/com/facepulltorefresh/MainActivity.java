package com.facepulltorefresh;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.facepulltorefresh.widget.TitleBar;
import com.pulltorefreshlibrary.FacePullToRefreshLayout;

import in.srain.cube.views.ptr.PtrFrameLayout;

public class MainActivity extends BaseTitleBarActivity {
    private final static String Tag = MainActivity.class.getSimpleName();
    private FacePullToRefreshLayout facePullToRefreshLayout;
    private RecyclerView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentViewTitleBarHaveBack(R.layout.activity_main);
        listView = (RecyclerView) findViewById(R.id.list_view);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listView.setLayoutManager(linearLayoutManager);
        listView.setAdapter(new TestAdapter());
        linearLayoutManager.scrollToPosition(49);
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
                linearLayoutManager.scrollToPosition(49);
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

    @Override
    public void onCreateCustomTitleBar(@NonNull TitleBar titleHeaderBar) {
        setTitleText("一句代码搞定TitleBar");
        setRightText("设置").setRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "点击了设置", Toast.LENGTH_LONG).show();
            }
        });
    }
}
