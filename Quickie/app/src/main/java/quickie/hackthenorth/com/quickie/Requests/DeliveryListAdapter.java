package quickie.hackthenorth.com.quickie.Requests;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import quickie.hackthenorth.com.quickie.R;

/**
 * Created by eedcoro on 9/19/2015.
 */
public class DeliveryListAdapter extends ArrayAdapter<FoodRequest> {

    ArrayList<FoodRequest> foodRequests = new ArrayList<>();
    Context context;
    int layoutResourceId;
    public DeliveryListAdapter(Context context, int resource) {
        super(context, resource);
    }

    public DeliveryListAdapter(Context context, int resource, ArrayList<FoodRequest> foodRequests){
        super(context, resource, foodRequests);
        this.context = context;
        this.layoutResourceId = resource;
        this.foodRequests = foodRequests;
    }

    @Override
    public View getView(int position, View requestView, ViewGroup parent) {
        if (requestView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            requestView = inflater.inflate(layoutResourceId, parent, false);
        }
        FoodRequest request = getItem(position);
        TextView username = (TextView) requestView.findViewById(R.id.person_name);
        // request.getUser().getUSername()
        username.setText("Hello omg");
        TextView distanceFromPlace = (TextView) requestView.findViewById(R.id.distance_from_place);
        distanceFromPlace.setText("16km");
        TextView distanceFromBuyer = (TextView) requestView.findViewById(R.id.distance_from_user);
        distanceFromBuyer.setText("30km");
        return requestView;
    }
}
