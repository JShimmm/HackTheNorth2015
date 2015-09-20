package quickie.hackthenorth.com.quickie;

import android.os.Bundle;
import android.location.Location;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import java.util.ArrayList;

import quickie.hackthenorth.com.quickie.Requests.DeliveryRequests;
import quickie.hackthenorth.com.quickie.Requests.FoodRequest;
import quickie.hackthenorth.com.quickie.Requests.MakeRequest;


public class TabPlatform extends FragmentActivity {

    ArrayList<FoodRequest> requests = new ArrayList<>();
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        double latitude = 0.0, longitude = 0.0;
        String name = null;
        if(savedInstanceState == null) {
            latitude = getIntent().getExtras().getDouble("LatitudeCurrentUser");
            longitude = getIntent().getExtras().getDouble("LongitudeCurrentUser");
            name = getIntent().getExtras().getString("Name");

        }
        setContentView(R.layout.activity_tab_platform);
        Fragment fragment = new DeliveryRequests();
        Bundle bundle = new Bundle();
        bundle.putDouble("Latitude",latitude);
        bundle.putDouble("Longitude", longitude);
        bundle.putString("Name", name);
        fragment.setArguments(bundle);
        setFragment(fragment);

    }

    public int iterator = 0;

    protected void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.replace(android.R.id.content, fragment);
        fragmentTransaction.commit();
    }
}
