package quickie.hackthenorth.com.quickie.Requests;

import android.app.Activity;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import quickie.hackthenorth.com.quickie.ParseApplication;
import quickie.hackthenorth.com.quickie.R;

public class DeliveryRequests extends Fragment {

    ArrayList<FoodRequest>requests = new ArrayList<>();
    ParseApplication parseApplication;
    Location mLastLocation = new Location("");
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_delivery_requests, container, false);
        ListView list = (ListView) root.findViewById(R.id.list_deliveries);

        requests = parseApplication.fetchFoodRequestToDB();

        list.setAdapter(new DeliveryListAdapter(this.getActivity(), R.layout.list_item_deliveries
                , requests, mLastLocation.getLatitude(), mLastLocation.getLongitude() ));
        // Inflate the layout for this fragment
        return root;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
