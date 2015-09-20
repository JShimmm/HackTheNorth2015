package quickie.hackthenorth.com.quickie.Requests;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import quickie.hackthenorth.com.quickie.ParseApplication;
import quickie.hackthenorth.com.quickie.R;

public class DeliveryRequests extends Fragment {

    ArrayList<FoodRequest>requests = new ArrayList<>();
    ParseApplication parseApplication;
    Location mLastLocation = new Location("");
    public Context context;

    public Activity activity;
    public DeliveryRequests() {
        parseApplication = new ParseApplication();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            mLastLocation.setLatitude(getArguments().getDouble("Latitude"));
            mLastLocation.setLongitude(getArguments().getDouble("Longitude"));
        }
        context = activity = this.getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_delivery_requests, container, false);
        ListView list = (ListView) root.findViewById(R.id.list_deliveries);
        requests = parseApplication.fetchFoodRequestToDB();
        list.setAdapter(new DeliveryListAdapter(this.getActivity(), R.layout.list_item_deliveries
                , requests, mLastLocation.getLatitude(), mLastLocation.getLongitude()));

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DeliveryListAdapter adapter = (DeliveryListAdapter) parent.getAdapter();
                FoodRequest request = adapter.getItem(position);

                Intent intent = new Intent(context, quickie.hackthenorth.com.quickie.TransactionConfirmation.class);
                Geocoder geocoder = new Geocoder(context, Locale.ENGLISH);
                try{
                    List<Address> addressList = geocoder.getFromLocationName(request.getLocationFood().toString(),1);
                    intent.putExtra("LatFood",addressList.get(0).getLatitude());
                    intent.putExtra("LngFood",addressList.get(0).getLongitude());
                } catch (Exception e){
                    e.printStackTrace();
                } finally {
                    intent.putExtra("LatUser", request.getLocationUser().getLatitude());
                    intent.putExtra("LngUser", request.getLocationUser().getLongitude());
                    intent.putExtra("Description", request.getDescription());
                    intent.putExtra("Price", request.getPrice());
                    intent.putExtra("Name", request.getName());
                    intent.putExtra("FacebookId", request.getFacebookId());
                    activity.startActivity(intent);
                }
            }
        });
        return root;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
