package com.divyeshbc.testslidingscrollbar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.divyeshbc.testslidingscrollbar.tabs.SlidingTabLayout;


public class MainActivity extends BaseActivity {

    private ViewPager mPager;
    private SlidingTabLayout mTabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Calling Activate Toolbar method
        activateToolBar();

        mPager = (ViewPager) findViewById(R.id.pager);
        //Setting the Adapter on the view pager first. Passing the fragment manager through as an argument
        mPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mTabs = (SlidingTabLayout) findViewById(R.id.tabs);
        //Setting the ViewPager as the tabs
        mTabs.setViewPager(mPager);

        mTabs.setDistributeEvenly(true);

        mTabs.setBackgroundColor(getResources().getColor(R.color.basePrimaryBackgroundColour));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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

    class MyPagerAdapter extends FragmentPagerAdapter {


        String[] tabs;

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            //Initialising the strings array of tabs
            tabs = getResources().getStringArray(R.array.tabs);
        }

        @Override
        public Fragment getItem(int position) {

            //Initialising Fragment
            //Passing in the position so that position of the fragment is returned
            MyFragment myFragment = MyFragment.getInstance(position);

            return myFragment;
        }

        @Override
        public CharSequence getPageTitle(int position){
            //Return the text of the position clicked and display this as the title for the tab
            return tabs[position];
        }

        @Override
        public int getCount() {
            return 5;
        }
    }

    public static class MyFragment extends Fragment {

        private TextView textView;

        //Method to return instance of the fragment. Passing in position to show which position is currently being shown in the fragment
        public static MyFragment getInstance(int position){
            //Construct the fragment
            MyFragment myFragment = new MyFragment();

            //New bundle instance
            Bundle args = new Bundle();

            //Passing in the Integer position of the fragment into the argument
            args.putInt("position", position);

            //Setting the argument of the fragment to be the position
            myFragment.setArguments(args);

            //Return the fragment
            return myFragment;
        }

        @Override
        //This will handle how the fragment will display content
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstanceState) {
            //Inflate the fragment_main layout
            View layout = inflater.inflate(R.layout.fragment_main, container, false);

            //Initialising the text view
            textView = (TextView) layout.findViewById(R.id.position);

            //Getting a reference to the TextView (as defined in fragment_main) and set it to a value
            Bundle bundle = getArguments();

            //Safety Check
            if(bundle != null) {
                textView.setText("The page currently selected is " +bundle.getInt("position"));
            }

            return layout;
        }
    }
}