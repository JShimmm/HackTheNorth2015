package quickie.hackthenorth.com.quickie;

import android.app.ListActivity;
import android.content.Intent;
import android.location.*;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BusinessesActivity extends ListActivity {
	
	public static final String EXTRA_BUSINESSES = "businesses";
	
	ArrayList<Business> mBusinesses;

    android.location.Location location;
    private String name, FacebookId;
    private String food;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Search results");
        mBusinesses = getIntent().getParcelableArrayListExtra(EXTRA_BUSINESSES);

        Bundle bundle = getIntent().getExtras();
        name = bundle.getString("Name");
        location = new Location("");
        location.setLatitude(bundle.getDouble("Latitude"));
        location.setLongitude(bundle.getDouble("Longitude"));
        FacebookId = bundle.getString("FacebookId");
        food = bundle.getString("Food");
        ArrayAdapter<Business> adapter = new ArrayAdapter<Business>(this, android.R.layout.simple_list_item_1, mBusinesses);
        ArrayList<String> fullLocation = new ArrayList<String>();

        for( int i=0 ; i<adapter.getCount() ; i++) {
            fullLocation.add(adapter.getItem(i).toString() + "\n" + adapter.getItem(i).getLocation().getDisplayAddress());
        }

        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, fullLocation));
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        String hello = (String) l.getAdapter().getItem(position);
        Intent intent = new Intent(this, SearchActivity.class);
        Geocoder geocoder = new Geocoder(BusinessesActivity.this, Locale.ENGLISH);
        try{
            List<Address> addressList = geocoder.getFromLocationName(hello.split("\\[")[1].split("\\]")[0],1);
            intent.putExtra("LatitudeFood", addressList.get(0).getLatitude());
            intent.putExtra("LongitudeFood", addressList.get(0).getLongitude());
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            intent.putExtra("Latitude", location.getLatitude());
            intent.putExtra("Longitude", location.getLongitude());
            intent.putExtra("Name", name);
            intent.putExtra("Distributor", hello.split("\\[")[0]);
            intent.putExtra("Address", hello.split("\\[")[1].split("\\]")[0]);
            intent.putExtra("Food", food);
            intent.putExtra("FacebookId", FacebookId);
            startActivity(intent);
        }
    }
}
