package quickie.hackthenorth.com.quickie;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link VersionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link VersionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VersionFragment extends Fragment {

    public VersionFragment() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_version, container,
                false);
        // Get position of argument
        String name = getArguments().getString("name");

        TextView textView = (TextView) rootView.findViewById(R.id.textView);
        textView.setText("Android version " + name + " is selected");
        getActivity().setTitle(name);
        return rootView;
    }
}
