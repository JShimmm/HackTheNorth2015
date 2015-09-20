package quickie.hackthenorth.com.quickie;

import android.os.Parcel;
import org.json.JSONException;
import org.json.JSONObject;
import quickie.hackthenorth.com.quickie.JsonParserJShim.DualCreator;


public class Location extends _Location {

	public static final DualCreator<Location> CREATOR = new DualCreator<Location>() {

		public Location[] newArray(int size) {
			return new Location[size];
		}

		public Location createFromParcel(Parcel source) {
			Location object = new Location();
			object.readFromParcel(source);
			return object;
		}

		@Override
		public Location parse(JSONObject obj) throws JSONException {
			Location newInstance = new Location();
			newInstance.readFromJson(obj);
			return newInstance;
		}
	};

}
