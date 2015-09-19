package quickie.hackthenorth.com.quickie;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseCrashReporting;
import com.parse.ParseFacebookUtils;

/**
 * Created by eedcoro on 9/18/2015.
 */
public class ParseApplication extends Application {
    @Override
    public void onCreate(){
        super.onCreate();
        // Initialize Crash Reporting.
        ParseCrashReporting.enable(this);
        Parse.enableLocalDatastore(this);
        // Add your initialization code here
        // Keys from parse representing our app
        Parse.initialize(this, "RnMrKcZDQB2FzHQxvxYjgAsp9HwlEQNUDKAquSC7",
                "i2YXWYcBrSs7FCN7RmFMMwbwW7LAcY472UpCbXF3");
        ParseACL defaultACL = new ParseACL();
        // If you would like all objects to be private by default, remove this line.
        defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
        ParseFacebookUtils.initialize(getApplicationContext());
    }
}
