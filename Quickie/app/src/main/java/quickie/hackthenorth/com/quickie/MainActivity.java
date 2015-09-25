package quickie.hackthenorth.com.quickie;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import android.location.Location;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import org.json.JSONException;

import quickie.hackthenorth.com.quickie.Requests.DeliveryRequests;
import quickie.hackthenorth.com.quickie.Requests.FoodRequest;


public class MainActivity extends ActionBarActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    private TextView info;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private String fbUserId;

    private Context context;
    ParseApplication parseApp;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        context = this;
        info = (TextView)findViewById(R.id.info);
        if(AccessToken.getCurrentAccessToken() == null) {
            callbackManager = CallbackManager.Factory.create();
            loginButton = (LoginButton) findViewById(R.id.login_button);
            loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    fbUserId = loginResult.getAccessToken().getUserId();

                    info.setText(
                            "User ID: "
                                    + loginResult.getAccessToken().getUserId()
                                    + "\n" +
                                    "Auth Token: "
                                    + loginResult.getAccessToken().getToken()
                    );
                    mGoogleApiClient.connect();
//                    Intent myIntent = new Intent(MainActivity.this, messengerActivity.class);
//                    myIntent.putExtra("key", value); //Optional parameters
//                    MainActivity.this.startActivity(myIntent);

//                    Intent intent = new Intent(context, quickie.hackthenorth.com.quickie.TabPlatform.class);

//                    Intent myIntent = new Intent(MainActivity.this, SearchActivity.class);
//                    myIntent.putExtra("LL", ((Double) mLastLocation.getLatitude()).toString() + "," + ((Double) mLastLocation.getLongitude()).toString()); //Optional parameters
//                    startActivity(myIntent);
                }

                @Override
                public void onCancel() {
                    info.setText("Login attempt canceled.");
                }

                @Override
                public void onError(FacebookException e) {
                    info.setText("Login attempt failed.");
                }
            });
        } else {
            mGoogleApiClient.connect();
        }
        parseApp = new ParseApplication();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        buildGoogleApiClient();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        if(AccessToken.getCurrentAccessToken() != null){
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onPause(){
        super.onPause();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnectionSuspended(int cause) {
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        // Provides a simple way of getting a device's location and is well suited for
        // applications that do not require a fine-grained location and that do not need location
        // updates. Gets the best and most recent location currently available, which may be null
        // in rare cases when a location is not available.
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            Toast.makeText(this, String.valueOf(mLastLocation.getLatitude()) + "" +
                    String.valueOf(mLastLocation.getLongitude()), Toast.LENGTH_LONG).show();
            GraphRequest graphRequest = GraphRequest.newGraphPathRequest(
                    AccessToken.getCurrentAccessToken(),
                    "me",
                    new GraphRequest.Callback() {
                        @Override
                        public void onCompleted(GraphResponse graphResponse) {
                            Log.d("Quickie", graphResponse.toString());
                            try{
                                String name = graphResponse.getJSONObject().getString("name");
                                Intent intent = new Intent(context, quickie.hackthenorth.com.quickie.HomeActivity.class);
                                intent.putExtra("LatitudeCurrentUser", mLastLocation.getLatitude());
                                intent.putExtra("LongitudeCurrentUser", mLastLocation.getLongitude());
                                intent.putExtra("Name", name);
                                Log.d("abcd","MainActivity: "+fbUserId);
                                intent.putExtra("FacebookId", fbUserId);
                                context.startActivity(intent);
                            } catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                    }
            );
            Bundle args = new Bundle();
            args.putString("fields", "name,id");
            graphRequest.setParameters(args);
            graphRequest.executeAsync();
        } else {
            Toast.makeText(this, "No location", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
        // onConnectionFailed.
        Log.i("Quickie", "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }
}
