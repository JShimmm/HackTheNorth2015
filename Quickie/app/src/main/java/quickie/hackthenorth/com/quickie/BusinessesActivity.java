package quickie.hackthenorth.com.quickie;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class BusinessesActivity extends ListActivity {
	
	public static final String EXTRA_BUSINESSES = "businesses";
	
	ArrayList<Business> mBusinesses;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Search results");
        mBusinesses = getIntent().getParcelableArrayListExtra(EXTRA_BUSINESSES);

        ArrayAdapter<Business> adapter = new ArrayAdapter<Business>(this, android.R.layout.simple_list_item_1, mBusinesses);
        ArrayList<String> fullLocation = new ArrayList<String>();

        for( int i=0 ; i<adapter.getCount() ; i++) {
            fullLocation.add(adapter.getItem(i).toString() + "\n" + adapter.getItem(i).getLocation().getDisplayAddress());
        }

        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, fullLocation));
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	Intent intent = new Intent(this, SearchActivity.class);
    	intent.putExtra(SearchActivity.FULLLOCATION, ((TextView) l.getChildAt(position)).getText());
        startActivity(intent);

    }
}
