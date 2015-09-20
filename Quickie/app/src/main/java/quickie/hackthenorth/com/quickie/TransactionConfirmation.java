package quickie.hackthenorth.com.quickie;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by eedcoro on 9/20/2015.
 */
public class TransactionConfirmation extends Activity {


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_confirmation);
        Bundle args = getIntent().getExtras();
        TextView name = (TextView) findViewById(R.id.transaction_fullName);
        name.setText(args.getString("Name"));
        TextView address = (TextView)findViewById(R.id.transaction_address);
        address.setText(args.getDouble("LatFood") + ", " + args.getDouble("LngFood"));
        TextView destination = (TextView) findViewById(R.id.transaction_destination);
        destination.setText(args.getDouble("LatUser") + ", " + args.getDouble("LngFood"));
        //TextView distributor = (TextView) findViewById(R.id.transaction_distributer);
        //distributor.setText(args.getString("Distributor"));
        TextView price = (TextView) findViewById(R.id.transaction_price);
        price.setText(args.getString("Price"));
        TextView description = (TextView) findViewById(R.id.transaction_description);
        description.setText(args.getString("Description"));
    }
}
