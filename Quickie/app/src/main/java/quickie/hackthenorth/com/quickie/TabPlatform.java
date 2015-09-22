package quickie.hackthenorth.com.quickie;

import android.content.Context;
import android.content.Intent;
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
    public Context context;
    public String FacebookId;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null) {
            latitude = getIntent().getExtras().getDouble("LatitudeCurrentUser");
            longitude = getIntent().getExtras().getDouble("LongitudeCurrentUser");
            name = getIntent().getExtras().getString("Name");
            FacebookId = getIntent().getExtras().getString("FacebookId");
        }
        context = this;
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
                bundle.putString("FacebookId", FacebookId);
                fragment.setArguments(bundle);
                setFragment(fragment);
            }
        });
        makeRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, quickie.hackthenorth.com.quickie.SearchActivity.class);
                intent.putExtra("Latitude", latitude);
                intent.putExtra("Longitude", longitude);
                intent.putExtra("Name", name);
                intent.putExtra("FacebookId", FacebookId);
                context.startActivity(intent);
//                setFragment(fragment);
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
