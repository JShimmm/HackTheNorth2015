package quickie.hackthenorth.com.quickie;

/*
 * Copyright 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

        import android.app.ActionBar;
        import android.app.FragmentTransaction;
        import android.content.Intent;
        import android.os.Bundle;
        import android.support.v4.app.Fragment;
        import android.support.v4.app.FragmentActivity;
        import android.support.v4.app.FragmentManager;
        import android.support.v4.app.FragmentPagerAdapter;
        import android.support.v4.view.ViewPager;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.view.Window;
        import android.widget.TextView;

        import quickie.hackthenorth.com.quickie.Requests.DeliveryRequests;
        import quickie.hackthenorth.com.quickie.Requests.MakeRequest;

public class TabPlatform extends FragmentActivity implements ActionBar.TabListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide fragments for each of the
     * three primary sections of the app. We use a {@link android.support.v4.app.FragmentPagerAdapter}
     * derivative, which will keep every loaded fragment in memory. If this becomes too memory
     * intensive, it may be best to switch to a {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    AppSectionsPagerAdapter mAppSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will display the three primary sections of the app, one at a
     * time.
     */
    ViewPager mViewPager;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_tab_platform);

        // Create the adapter that will return a fragment for each of the three primary sections
        // of the app.
        mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager());

        // Set up the action bar.
        final ActionBar actionBar = getActionBar();

        // Specify that the Home/Up button should not be enabled, since there is no hierarchical
        // parent.
        actionBar.setHomeButtonEnabled(false);

        // Specify that we will be displaying tabs in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Set up the ViewPager, attaching the adapter and setting up a listener for when the
        // user swipes between sections.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mAppSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // When swiping between different app sections, select the corresponding tab.
                // We can also use ActionBar.Tab#select() to do this if we have a reference to the
                // Tab.
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mAppSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by the adapter.
            // Also specify this Activity object, which implements the TabListener interface, as the
            // listener for when this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mAppSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one of the primary
     * sections of the app.
     */
    public static class AppSectionsPagerAdapter extends FragmentPagerAdapter {

        public AppSectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case 0:
                    // The first section of the app is the most interesting -- it offers
                    // a launchpad into the other demonstrations in this example application.
                    return new DeliveryRequests();

                default:
                    // The other sections of the app are dummy placeholders.
                    Fragment fragment = new MakeRequest();
                    return fragment;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Section " + (position + 1);
        }
    }

}


//import java.util.Locale;
//
//import android.support.v4.app.FragmentActivity;
//import android.support.v4.app.FragmentStatePagerAdapter;
//import android.support.v7.app.ActionBarActivity;
//import android.support.v7.app.ActionBar;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentTransaction;
//import android.support.v4.app.FragmentPagerAdapter;
//import android.os.Bundle;
//import android.support.v4.view.ViewPager;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import quickie.hackthenorth.com.quickie.Requests.DeliveryRequests;
//import quickie.hackthenorth.com.quickie.Requests.MakeRequest;
//
//
//public class TabPlatform extends FragmentActivity implements android.app.ActionBar.TabListener{
//
//    SectionsPagerAdapter mSectionsPagerAdapter;
//    ViewPager mViewPager;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_tab_platform);
//
//        final ActionBar actionBar = getActionBar();
//        // Create the adapter that will return a fragment for each of the three
//        // primary sections of the activity.
//        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
//        // Set up the ViewPager with the sections adapter.
//        mViewPager = (ViewPager) findViewById(R.id.pager);
//        mViewPager.setAdapter(mSectionsPagerAdapter);
//
//
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_tab_platform, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    public void onTabSelected(android.app.ActionBar.Tab tab, android.app.FragmentTransaction ft) {
//
//    }
//
//    @Override
//    public void onTabUnselected(android.app.ActionBar.Tab tab, android.app.FragmentTransaction ft) {
//
//    }
//
//    @Override
//    public void onTabReselected(android.app.ActionBar.Tab tab, android.app.FragmentTransaction ft) {
//
//    }
//
//    public class SectionsPagerAdapter extends FragmentStatePagerAdapter{
//        private DeliveryRequests delivery;
//        private MakeRequest request;
//        public SectionsPagerAdapter(FragmentManager fm) {
//            super(fm);
//            delivery = new DeliveryRequests();
//            request = new MakeRequest();
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            if(position == 0){
//                return request;
//            } else {
//                return delivery;
//            }
//        }
//
//        @Override
//        public int getCount() {
//            // Show 3 total pages.
//            return 2;
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            Locale l = Locale.getDefault();
//            switch (position) {
//                case 0:
//                    return "Make Request".toUpperCase(l);
//                case 1:
//                    return "Deliveries".toUpperCase(l);
//            }
//            return null;
//        }
//    }
//
//    @Override
//    public void onSaveInstanceState(Bundle hello){
//        super.onSaveInstanceState(hello);
//    }
//
//}
