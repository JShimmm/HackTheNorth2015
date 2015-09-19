package quickie.hackthenorth.com.quickie.Requests;

import android.location.Location;

import com.parse.ParseUser;

/**
 * Created by eedcoro on 9/19/2015.
 */
public class FoodRequest {

    public ParseUser user;
    public Location locationFood;
    public Location locationUser;
    public String description;

    public FoodRequest(){

    }

    public Location getLocationFood() {
        return locationFood;
    }

    public void setLocationFood(Location locationFood) {
        this.locationFood = locationFood;
    }

    public Location getLocationUser() {
        return locationUser;
    }

    public void setLocationUser(Location locationUser) {
        this.locationUser = locationUser;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ParseUser getUser() {
        return user;
    }

    public void setUser(ParseUser user) {
        this.user = user;
    }
}
