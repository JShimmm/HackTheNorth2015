package quickie.hackthenorth.com.quickie;

import android.app.Application;
import android.content.Context;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

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
        ParseUser.enableAutomaticUser();
        // Add your initialization code here
        // Keys from parse representing our app
        Parse.initialize(this, "RnMrKcZDQB2FzHQxvxYjgAsp9HwlEQNUDKAquSC7",
                "i2YXWYcBrSs7FCN7RmFMMwbwW7LAcY472UpCbXF3");
        ParseACL defaultACL = new ParseACL();
        // If you would like all objects to be private by default, remove this line.
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);

    }

    public ParseApplication(){

    }

    public static void signUp(String username, String password, String email){
        ParseUser user = new ParseUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null){
                    Log.d("Quickie", "Signed up succesfully");
                } else {
                    Log.d("Quickie", e.getMessage());
                }
            }
        });
    }

    public static void LogIn(String username, String password, final Context context) {
        if (ParseUser.getCurrentUser() == null) {
            ParseUser.logInInBackground(username, password, new LogInCallback() {
                @Override
                public void done(ParseUser parseUser, com.parse.ParseException e) {
                    if (parseUser != null) {
                    } else {
                        Toast.makeText(context, "Messed up login", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
    public void pushFoodRequestToDB(FoodRequest request){
        Log.d("Quickie","Hello2");
        ParseObject foodRequest = new ParseObject("FoodRequests");

        ParseGeoPoint geoPoint = new ParseGeoPoint();
        geoPoint.setLatitude(request.getLocationFood().getLatitude());
        geoPoint.setLongitude(request.getLocationFood().getLongitude());

        foodRequest.put("Location", geoPoint);
        foodRequest.put("LatUser", request.getLocationUser().getLatitude());
        foodRequest.put("LngUser", request.getLocationUser().getLongitude());
        foodRequest.put("Description", request.getDescription());
        foodRequest.put("Price", request.getPrice());
        foodRequest.put("Name", request.getName());
        foodRequest.put("FacebookId", request.getFacebookId());
        foodRequest.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e != null) e.printStackTrace();
            }
        });
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
            Log.d("Quickie", "List size : "+requests.size());
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
                FoodRequest parsedRequest = new FoodRequest(locationFood, locationUser,
                        object.getString("Description"), object.getNumber("Price").intValue(),
                        "Richard", object.getString("FacebookId"));
                foodRequests.add(parsedRequest);
            }
            return foodRequests;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
