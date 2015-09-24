package quickie.hackthenorth.com.quickie.adapter;

/**
 * Created by Jon on 20/09/2015.
 */

import quickie.hackthenorth.com.quickie.Requests.DeliveryRequests;
import quickie.hackthenorth.com.quickie.Requests.MakeRequest;
import quickie.hackthenorth.com.quickie.SearchActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter{

    public TabsPagerAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int index){
        switch (index){
            case 0:
                //MakeRequest fragment activity
                return new SearchActivity();
            case 1:
                //DeliveryRequest fragment activity
                return new DeliveryRequests();
        }

        return null;
    }

    @Override
    public int getCount(){
        //get item count - equal to number of tabs
        return 2;
    }
}
