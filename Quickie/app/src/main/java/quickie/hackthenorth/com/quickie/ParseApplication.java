package quickie.hackthenorth.com.quickie;

import android.app.Application;
import android.location.Location;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseACL;

import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import quickie.hackthenorth.com.quickie.Requests.FoodRequest;

/**
 * Created by eedcoro on 9/18/2015.
 */
public class ParseApplication extends Application {
    @Override
    public void onCreate(){
        super.onCreate();
        // Initialize Crash Reporting.

        Parse.enableLocalDatastore(this);
        // Add your initialization code here
        // Keys from parse representing our app
        Parse.initialize(this, "RnMrKcZDQB2FzHQxvxYjgAsp9HwlEQNUDKAquSC7",
                "i2YXWYcBrSs7FCN7RmFMMwbwW7LAcY472UpCbXF3");
        ParseACL defaultACL = new ParseACL();
        // If you would like all objects to be private by default, remove this line.
        defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);

    }

    public void pushFoodRequestToDB(FoodRequest request){
        ParseGeoPoint geoPoint = new ParseGeoPoint();
        geoPoint.setLatitude(request.getLocationFood().getLatitude());
        geoPoint.setLongitude(request.getLocationFood().getLongitude());

        ParseObject foodRequest = new ParseObject("FoodRequests");
        foodRequest.put("Location", geoPoint);
        foodRequest.put("LatUser", request.getLocationUser().getLatitude());
        foodRequest.put("LngUser", request.getLocationUser().getLongitude());
        foodRequest.put("Description", request.getDescription());
        foodRequest.put("FacebookId", request.getFacebookId());
        foodRequest.put("Price", request.getPrice());
        foodRequest.put("Name", request.getName());
//        foodRequest.saveInBackground();
    }

    public void matchedFoodRequest(FoodRequest request){
        ParseQuery query = new ParseQuery("FoodRequests");
        query.whereEqualTo("Location", request.getLocationFood());
        query.whereEqualTo("LatUser", request.getLocationUser().getLatitude());
        query.whereEqualTo("LngUser", request.getLocationUser().getLongitude());
        query.findInBackground(new FindCallback() {
            @Override
            public void done(List objects, ParseException e) {
                for(int i = 0; i < objects.size(); i++){
                    ParseObject obj = (ParseObject) objects.get(i);
                    Log.d("Quickie", obj.getObjectId());
                    obj.deleteInBackground();
                }
            }

            @Override
            public void done(Object o, Throwable throwable) {

            }
        });
    }
    public ArrayList<FoodRequest> fetchFoodRequestToDB(){
        ParseQuery query = new ParseQuery("FoodRequests");
        try {
            List requests = query.find();
            ArrayList <FoodRequest> foodRequests = new ArrayList<>();
            for(int i = 0; i < requests.size(); i++){
                ParseObject object = (ParseObject) requests.get(i);
                ParseGeoPoint geoPoint = object.getParseGeoPoint("Location");
                Location locationFood = new Location("");
                locationFood.setLatitude(geoPoint.getLatitude());
                locationFood.setLongitude(geoPoint.getLongitude());
                Location locationUser = new Location("");
                locationUser.setLatitude(object.getNumber("LatUser").doubleValue());
                locationUser.setLongitude(object.getNumber("LngUser").doubleValue());
                FoodRequest parsedRequest = new FoodRequest("Richard", locationFood, locationUser,
                        object.getString("Description"), object.getNumber("Price").intValue());
                foodRequests.add(parsedRequest);
            }
            return foodRequests;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
