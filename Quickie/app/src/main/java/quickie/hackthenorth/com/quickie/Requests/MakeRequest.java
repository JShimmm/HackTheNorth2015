package quickie.hackthenorth.com.quickie.Requests;

import android.app.Activity;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import quickie.hackthenorth.com.quickie.R;


public class MakeRequest extends Fragment {

    private String name;
    private Location requestLocation;

    public MakeRequest() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if(args != null) {
            name = args.getString("Name");
            requestLocation = new Location("");
            requestLocation.setLatitude(args.getDouble("Latitude"));
            requestLocation.setLongitude(args.getDouble("Longitude"));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedState) {
        super.onSaveInstanceState(savedState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_make_request, container, false);
        TextView name = (TextView) root.findViewById(R.id.make_request_name);
        name.setText(name + " wants ");
        return root;
    }
}
