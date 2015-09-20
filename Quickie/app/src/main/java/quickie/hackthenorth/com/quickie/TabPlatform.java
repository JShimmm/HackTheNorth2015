package quickie.hackthenorth.com.quickie;

import android.os.Bundle;
import android.location.Location;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

import quickie.hackthenorth.com.quickie.Requests.DeliveryRequests;
import quickie.hackthenorth.com.quickie.Requests.MakeRequest;

public class TabPlatform extends FragmentActivity {

    ImageView deliver, makeRequest;
    double latitude = 0.0, longitude = 0.0;
    String name = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null) {
            latitude = getIntent().getExtras().getDouble("LatitudeCurrentUser");
            longitude = getIntent().getExtras().getDouble("LongitudeCurrentUser");
            name = getIntent().getExtras().getString("Name");
        }
        setContentView(R.layout.activity_tab_platform);
        deliver = (ImageView) findViewById(R.id.deliver_image);
        makeRequest = (ImageView) findViewById(R.id.make_request_image);

        deliver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new DeliveryRequests();
                Bundle bundle = new Bundle();
                bundle.putDouble("Latitude", latitude);
                bundle.putDouble("Longitude", longitude);
                bundle.putString("Name", name);
                fragment.setArguments(bundle);
                setFragment(fragment);
            }
        });
        makeRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deliver.setVisibility(View.GONE);
                makeRequest.setVisibility(View.GONE);
                Fragment fragment = new MakeRequest();
                Bundle bundle = new Bundle();
                bundle.putDouble("Latitude", latitude);
                bundle.putDouble("Longitude", longitude);
                bundle.putString("Name", name);
                fragment.setArguments(bundle);
                setFragment(fragment);
            }
        });
    }

    protected void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.replace(android.R.id.content, fragment);
        fragmentTransaction.commit();
    }
}
