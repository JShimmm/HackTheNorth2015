package quickie.hackthenorth.com.quickie.Requests;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.parse.ParseGeoPoint;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import quickie.hackthenorth.com.quickie.R;

/**
 * Created by eedcoro on 9/19/2015.
 */
public class DeliveryListAdapter extends ArrayAdapter<FoodRequest> implements AdapterView.OnItemClickListener{

    ArrayList<FoodRequest> foodRequests = new ArrayList<>();
    Context context;
    int layoutResourceId;
    public DeliveryListAdapter(Context context, int resource) {
        super(context, resource);
    }

    public DeliveryListAdapter(Context context, int resource, ArrayList<FoodRequest> foodRequests,
                               double lat, double lng){
        super(context, resource, foodRequests);
        this.context = context;
        this.layoutResourceId = resource;
        this.foodRequests = foodRequests;
        latUser = lat;
        lngUser = lng;

    }
    private double latUser = 0.0, lngUser = 0.0;
    @Override
    public View getView(int position, View requestView, ViewGroup parent) {
        if (requestView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            requestView = inflater.inflate(layoutResourceId, parent, false);
        }
        FoodRequest request = getItem(position);
        TextView username = (TextView) requestView.findViewById(R.id.person_name);
        // request.getUser().getUSername()
        username.setText(request.getName());
        TextView distanceFromPlace = (TextView) requestView.findViewById(R.id.distance_from_place);
        ParseGeoPoint userPosition = new ParseGeoPoint(latUser,lngUser);
        ParseGeoPoint foodPosition = new ParseGeoPoint(request.getLocationFood().getLatitude(),
                request.getLocationFood().getLongitude());
        distanceFromPlace.setText(round((userPosition.distanceInKilometersTo(foodPosition)* 1000),2) + "m");
        TextView distanceFromBuyer = (TextView) requestView.findViewById(R.id.distance_from_user);
        ParseGeoPoint destinationPosition = new ParseGeoPoint(request.getLocationUser().getLatitude(),
                request.getLocationUser().getLongitude());
        distanceFromBuyer.setText(round((foodPosition.distanceInKilometersTo(destinationPosition) * 1000), 2) + "m");
        return requestView;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
