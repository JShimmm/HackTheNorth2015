package quickie.hackthenorth.com.quickie.Requests;

import quickie.hackthenorth.com.quickie.Business;
import quickie.hackthenorth.com.quickie.BusinessesActivity;
import quickie.hackthenorth.com.quickie.JsonUtil;
import quickie.hackthenorth.com.quickie.ParseApplication;
import quickie.hackthenorth.com.quickie.R;
import quickie.hackthenorth.com.quickie.Requests.DeliveryListAdapter;
import quickie.hackthenorth.com.quickie.Requests.FoodRequest;
import quickie.hackthenorth.com.quickie.YelpApis.Yelp;

import android.content.Context;
import android.location.Location;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.*;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.locks.LockSupport;

public class MakeRequest extends Fragment implements View.OnClickListener{

    public static final int DIALOG_PROGRESS = 42;
    private static final int REQUEST_CODE_FOR_BUSINESS = 1;

    private Yelp mYelp;
    private String LL;
    Location location;
    Location locationFood;
    private String name;
    private String distributor;
    public ParseApplication parseApplication;
    public EditText pickupPlace;
    public EditText food;
    public EditText distributorView;
    public EditText price;
    public EditText description;
    public Context context;
    public Activity activity;
    String FacebookId;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parseApplication = new ParseApplication();
        mYelp = new Yelp(getString(R.string.consumer_key), getString(R.string.consumer_secret),
                getString(R.string.api_token), getString(R.string.api_token_secret));

        context = activity = this.getActivity();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_make_request, container, false);
        Button searchButton = (Button) view.findViewById(R.id.searchButton);
        Button searchSurroundingButton = (Button) view.findViewById(R.id.searchByCurrentLocationButton);
        Button submitRequestButton = (Button) view.findViewById(R.id.submitRequesButton);
        searchButton.setOnClickListener(this);
        searchSurroundingButton.setOnClickListener(this);
        submitRequestButton.setOnClickListener(this);

        if(getArguments().containsKey("Latitude")){
            location = new Location("");
            location.setLatitude(getArguments().getDouble("Latitude"));
            location.setLongitude(getArguments().getDouble("Longitude"));
            name = getArguments().getString("Name");
            FacebookId = getArguments().getString("FacebookId");
            //Log.d("Quickie", FacebookId);
            LL = location.getLatitude() + "," + location.getLongitude();

            TextView nameView = (TextView) view.findViewById(R.id.make_request_name);
            nameView.setText(name + " wants");
            description = (EditText) view.findViewById(R.id.make_request_description);
            price = (EditText) view.findViewById(R.id.make_request_price);
        }
        if(getArguments().containsKey("Food")){
            pickupPlace = (EditText) view.findViewById(R.id.make_request_location);
            pickupPlace.setText(getArguments().getString("Address"));
            food = (EditText) view.findViewById(R.id.make_request_food);
            food.setText(getArguments().getString("Food"));
            distributorView = (EditText) view.findViewById(R.id.make_request_distributor);
            distributorView.setText(getArguments().getString("Distributor"));
            locationFood = new Location("");
            locationFood.setLatitude(getArguments().getDouble("LatitudeFood"));
            locationFood.setLongitude(getArguments().getDouble("LongitudeFood"));
        }

        return view;
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.searchButton:
                search(v);
                break;
            case R.id.searchByCurrentLocationButton:
                searchByCurrentLocation(v);
                break;
            case R.id.submitRequesButton:
                submitRequest(v);
                break;
        }
    }

    public void submitRequest(View v){
        if(FacebookId == null){
            FacebookId = "123456789";
        }
        FoodRequest request = new FoodRequest(locationFood,location, description.getText().toString(),
                Integer.parseInt(price.getText().toString()), name, FacebookId);
        parseApplication.pushFoodRequestToDB(request);
    }
    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onPause(){
        super.onPause();
    }

    public void search(View v) {
        String terms = ((EditText)getView().findViewById(R.id.make_request_food)).getText().toString();
        String location = ((EditText)getView().findViewById(R.id.make_request_location)).getText().toString();
        //getActivity().showDialog(DIALOG_PROGRESS);
        new AsyncTask<String, Void, ArrayList<Business>>() {

            @Override
            protected ArrayList<Business> doInBackground(String... params) {
                String result = mYelp.search(params[0], params[1]);
                try {
                    JSONObject response = new JSONObject(result);
                    if (response.has("businesses")) {
                        return JsonUtil.parseJsonList(
                                response.getJSONArray("businesses"), Business.CREATOR);
                    }
                } catch (JSONException e) {
                    return null;
                }
                return null;
            }

            @Override
            protected void onPostExecute(ArrayList<Business> businesses) {
                onSuccess(businesses);
            }

        }.execute(terms, location);
    }

    public void searchByCurrentLocation(View v) {
        String terms = ((EditText)getView().findViewById(R.id.make_request_food)).getText().toString();
        //getActivity().showDialog(DIALOG_PROGRESS);
        new AsyncTask<String, Void, ArrayList<Business>>() {

            @Override
            protected ArrayList<Business> doInBackground(String... params) {
                String result = mYelp.searchByCurrentLocation(params[0], LL);
                try {
                    JSONObject response = new JSONObject(result);
                    if (response.has("businesses")) {
                        return JsonUtil.parseJsonList(
                                response.getJSONArray("businesses"), Business.CREATOR);
                    }
                } catch (JSONException e) {
                    return null;
                }
                return null;
            }

            @Override
            protected void onPostExecute(ArrayList<Business> businesses) {
                onSuccess(businesses);
            }

        }.execute(terms, LL);
    }

    public void onSuccess(ArrayList<Business> businesses) {
        // Launch BusinessesActivity with an intent that includes the received businesses
        //getActivity().dismissDialog(DIALOG_PROGRESS);
        if (businesses != null) {
            Intent intent = new Intent(context, BusinessesActivity.class);
            intent.putParcelableArrayListExtra(BusinessesActivity.EXTRA_BUSINESSES, businesses);
            intent.putExtra("Latitude", location.getLatitude());
            intent.putExtra("Longitude", location.getLongitude());
            intent.putExtra("Name", name);
            intent.putExtra("FacebookId", FacebookId);
            food = (EditText) getView().findViewById(R.id.make_request_food);
            intent.putExtra("Food", food.getText().toString());
            startActivityForResult(intent, REQUEST_CODE_FOR_BUSINESS);
        } else {
            Toast.makeText(context, "An error occured during search", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        //Check which request we're responding to
        if (requestCode == REQUEST_CODE_FOR_BUSINESS){
            //Make sure the request was successful
            Log.d("abcd","gate 1");
            if (resultCode == Activity.RESULT_OK){
                Log.d("abcd","gate 2");
                Log.d("abcd", "in SearchActivity: Address: " + data.getStringExtra("Address"));
                pickupPlace = (EditText) getView().findViewById(R.id.make_request_location);
                pickupPlace.setText(data.getStringExtra("Address"));
                food = (EditText) getView().findViewById(R.id.make_request_food);
                Log.d("abcd","in SearchActivity: food: "+ data.getStringExtra("Food"));
                food.setText(data.getStringExtra("Food"));
                distributorView = (EditText) getView().findViewById(R.id.make_request_distributor);
                distributorView.setText(data.getStringExtra("Distributor"));
                locationFood = new Location("");
                locationFood.setLatitude(data.getDoubleExtra("LatitudeFood", 0));
                locationFood.setLongitude(data.getDoubleExtra("LongitudeFood", 0));
            }
        }
    }

    static String RESTAURANT = "Restaurant";
    static String LOCATION = "Location";

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);

        // Save the user's current game state
        savedInstanceState.putString(RESTAURANT, ((EditText) getView().findViewById(R.id.make_request_food)).getText().toString());
        savedInstanceState.putString(LOCATION, ((EditText) getView().findViewById(R.id.make_request_location)).getText().toString());

    }

    public Dialog onCreateDialog(int id) {
        if (id == DIALOG_PROGRESS) {
            ProgressDialog dialog = new ProgressDialog(context);
            dialog.setMessage("Loading...");
            return dialog;
        } else {
            return null;
        }
    }
}