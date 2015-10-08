package com.akiniyalocts.system76.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.akiniyalocts.system76.NavIntentBuilder;
import com.akiniyalocts.system76.R;
import com.akiniyalocts.system76.System76App;
import com.akiniyalocts.system76.base.ToolbarActivity;
import com.akiniyalocts.system76.data.FetchEndpointService;
import com.akiniyalocts.system76.model.Item;
import com.akiniyalocts.system76.model.ItemsWrapper;
import com.squareup.otto.Subscribe;

import butterknife.Bind;

public class MainActivity extends ToolbarActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        SwipeRefreshLayout.OnRefreshListener,
        BaseAdapter.OnItemClickedListener{

    public static final String EXTRA_CUSTOM_TABS_TOOLBAR_COLOR = "android.support.customtabs.extra.TOOLBAR_COLOR";

    public static final String EXTRA_CUSTOM_TABS_SESSION = "android.support.customtabs.extra.SESSION";


    @Bind(R.id.item_list)
    RecyclerView mRecycler;

    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mRefresh;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    private String currentEndpoint = FetchEndpointService.ENDPOINT_LAPTOP;

    private BaseAdapter adapter;

    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    public Toolbar getToolBar() {
        return mToolbar;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setLayout();
        mRefresh.setOnRefreshListener(this);
        mRefresh.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary);

        setupHomeSupportActionBar(getString(R.string.app_name));

        initDrawer();

        if(savedInstanceState != null){
            currentEndpoint = savedInstanceState.getString(FetchEndpointService.KEY_ENDPOINT);
        }

        onRefresh();

    }

    private void setLayout(){
        if(System76App.isTablet()){
            mRecycler.setLayoutManager(new GridLayoutManager(this, 2));
        }
        else {
            mRecycler.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    private void initDrawer(){
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(FetchEndpointService.KEY_ENDPOINT, currentEndpoint);
    }


    @Subscribe
    public void onItemsLoaded(ItemsWrapper mItems){
        mRefresh.setRefreshing(false);
        adapter = new BaseAdapter(mItems.getItemList(), this);
        adapter.setListener(this);
        mRecycler.setAdapter(adapter);
    }

    @Override
    public void onItemClick(Item item, View parent) {

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getItemUrl()));

        Bundle extras = new Bundle();
        intent.putExtra(EXTRA_CUSTOM_TABS_TOOLBAR_COLOR, getResources().getColor(R.color.colorPrimary));

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            extras.putBinder(EXTRA_CUSTOM_TABS_SESSION, null);
        }

        intent.putExtras(extras);

        startActivity(intent);

    }

    @Override
    public void onRefresh() {
        mRefresh.setRefreshing(true);
        NavIntentBuilder.startFetchService(this, currentEndpoint);
    }

    @Override
    protected void onResume() {
        super.onResume();
        System76App.getBus().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        System76App.getBus().unregister(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        String title = null;

        if (id == R.id.nav_laptops) {
            mToolbar.setTitle(R.string.laptops);
            currentEndpoint = FetchEndpointService.ENDPOINT_LAPTOP;

        } else if (id == R.id.nav_desktops) {
            mToolbar.setTitle(R.string.desktops);
            currentEndpoint = FetchEndpointService.ENDPOINT_DESKTOPS;

        } else if (id == R.id.nav_servers) {
            mToolbar.setTitle(R.string.servers);
            currentEndpoint = FetchEndpointService.ENDPOINT_SERVERS;

        } else if (id == R.id.nav_swag) {
            mToolbar.setTitle(R.string.swag);
            currentEndpoint = FetchEndpointService.ENDPOINT_SWAG;

        } else if (id == R.id.nav_twitter) {
            NavIntentBuilder.startSocialIntent(this, "https://twitter.com/system76/");

        } else if (id == R.id.nav_fb) {
            NavIntentBuilder.startSocialIntent(this, "https://www.facebook.com/system76/");
        }
        else if(id == R.id.nav_google){
            NavIntentBuilder.startSocialIntent(this, "https://plus.google.com/u/0/104919222657565747428");
        }

        if(id == R.id.nav_desktops || id == R.id.nav_laptops || id == R.id.nav_servers || id == R.id.nav_swag){
            onRefresh();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
