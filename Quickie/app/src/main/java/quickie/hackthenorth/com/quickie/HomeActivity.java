package quickie.hackthenorth.com.quickie;

import android.app.Activity;
import android.app.ActionBar;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import quickie.hackthenorth.com.quickie.Requests.DeliveryRequests;
import quickie.hackthenorth.com.quickie.Requests.MakeRequest;

public class HomeActivity extends ActionBarActivity {
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;

    private CharSequence drawerTitle;
    private CharSequence title;

    String[] andoridVeriosnArray;

    double latitude = 0.0, longitude = 0.0;
    String name = null;
    public Context context;
    public String FacebookId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null) {
            latitude = getIntent().getExtras().getDouble("LatitudeCurrentUser");
            longitude = getIntent().getExtras().getDouble("LongitudeCurrentUser");
            name = getIntent().getExtras().getString("Name");
            FacebookId = getIntent().getExtras().getString("FacebookId");
        }
        Log.d("abcd", "HomeActivity: " + FacebookId);
        context = this;
        setContentView(R.layout.activity_home);

        title = drawerTitle = "Quickie";

        andoridVeriosnArray = new String[] { "Make Request", "Delivery Requests" };

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawerList = (ListView) findViewById(R.id.drawerList);

        // Set shadow to drawer
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
                GravityCompat.START);

        // Set adapter to drawer
        drawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, andoridVeriosnArray));

        // set up click listener on drawer
        drawerList.setOnItemClickListener(new DrawerItemClickListener());

        // set app icon to behave as action to toggle navigation drawer
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // ActionBarDrawerToggle ties together the the interactions the sliding
        // drawer and the app icon
        drawerToggle = new ActionBarDrawerToggle(this, // Host Activity
                drawerLayout, // layout container for navigation drawer
                R.drawable.ic_drawer, // Application Icon
                R.string.drawer_open, // Open Drawer Description
                R.string.drawer_close) // Close Drawer Description
        {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(title);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(drawerTitle);
                invalidateOptionsMenu();
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Listen Toggle state of navigation Drawer
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Click Event of navigation drawer item click
    private class DrawerItemClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {

        Fragment fragment;

        // Set Fragment page accordingly
        switch(position){
            case 0:
                fragment = new MakeRequest();
                Bundle makeRequestBundle = new Bundle();
                makeRequestBundle.putDouble("Latitude", latitude);
                makeRequestBundle.putDouble("Longitude", longitude);
                makeRequestBundle.putString("Name", name);
                makeRequestBundle.putString("FacebookId", FacebookId);
                fragment.setArguments(makeRequestBundle);
                break;
            case 1:
                fragment = new DeliveryRequests();
                Bundle deliveryRequestBundle = new Bundle();
                deliveryRequestBundle.putDouble("Latitude", latitude);
                deliveryRequestBundle.putDouble("Longitude", longitude);
                deliveryRequestBundle.putString("Name", name);
                deliveryRequestBundle.putString("FacebookId", FacebookId);
                fragment.setArguments(deliveryRequestBundle);
                break;
            default:
                fragment = new MakeRequest();
                Bundle defaultBundle = new Bundle();
                defaultBundle.putDouble("Latitude", latitude);
                defaultBundle.putDouble("Longitude", longitude);
                defaultBundle.putString("Name", name);
                defaultBundle.putString("FacebookId", FacebookId);
                fragment.setArguments(defaultBundle);
                break;
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frameContainer, fragment).commit();

        // Update Title on action bar
        drawerList.setItemChecked(position, true);
        setTitle(andoridVeriosnArray[position]);
        drawerLayout.closeDrawer(drawerList);
    }

    @Override
    public void setTitle(CharSequence _title) {
        title = _title;
        getSupportActionBar().setTitle(title);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        drawerToggle.onConfigurationChanged(newConfig);
    }
}
