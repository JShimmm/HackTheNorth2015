package quickie.hackthenorth.com.quickie.Requests;

import android.location.Location;

import com.parse.ParseUser;

/**
 * Created by eedcoro on 9/19/2015.
 */
public class FoodRequest {

    private ParseUser user;
    private Location locationFood;
    private Location locationUser;
    private String description;
    private int price;
    private String name;
    private String FacebookId;

    public String getFacebookId() {
        return FacebookId;
    }

    public void setFacebookId(String facebookId) {
        FacebookId = facebookId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public FoodRequest(Location locationFood, Location locationUser,
                       String description, int price, String name, String FacebookId){
        this.locationFood = locationFood;
        this.locationUser = locationUser;
        this.description = description;
        this.price = price;
        this.name = name;
        this.FacebookId = FacebookId;
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
