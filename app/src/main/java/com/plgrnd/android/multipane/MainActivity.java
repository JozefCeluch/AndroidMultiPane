package com.plgrnd.android.multipane;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MasterFragment.MasterFragmentCallback {

    private static final String STATE_SHOWING_DETAIL = "state_showing_detail";
    private static final String STATE_SELECTED_ITEM = "state_selected_item";
    private static final int NONE = -1;
    private boolean mDualPane;
    private boolean mShowingDetail;
    private RetainedFragment mRetainedFragment;
    private int mSelectedItem = NONE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDualPane = findViewById(R.id.dual_pane_container) != null;

        FragmentManager fm = getFragmentManager();
        Log.d("activity", "BACKSTACK COUNT: " + fm.getBackStackEntryCount());
        if (savedInstanceState == null) {
            mRetainedFragment = new RetainedFragment();
            fm.beginTransaction().add(mRetainedFragment, RetainedFragment.TAG).commit();
            if (!mDualPane) {
                fm.beginTransaction()
                        .replace(R.id.single_pane_container, new MasterFragment(), MasterFragment.TAG)
                        .commit();
            }
        } else {
            if (savedInstanceState.getBoolean(STATE_SHOWING_DETAIL)) {
                removeDetailFragment();
                addDetailFragment(savedInstanceState.getInt(STATE_SELECTED_ITEM, NONE));
            }
        }
        mRetainedFragment = (RetainedFragment) fm.findFragmentByTag(RetainedFragment.TAG);
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

        if (id == android.R.id.home) {
            if (!mDualPane) {
                removeDetailFragment();
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void addDetailFragment(int itemId) {
        mShowingDetail = true;
        mSelectedItem = itemId;

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (mDualPane) {
            ft.replace(R.id.detail_container, DetailFragment.newInstance(itemId), DetailFragment.TAG);
        } else {
            ft.replace(R.id.single_pane_container, DetailFragment.newInstance(itemId), DetailFragment.TAG);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }
        ft.addToBackStack(null).commit();
    }

    private void removeDetailFragment() {
        mShowingDetail = false;
        mSelectedItem = NONE;

        FragmentManager fm = getFragmentManager();
        Fragment detailFragment = fm.findFragmentByTag(DetailFragment.TAG);

        if (detailFragment != null) {
            fm.beginTransaction().remove(detailFragment).commit();
            fm.popBackStack();
        }
        fm.executePendingTransactions();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    @Override
    public void onBackPressed() {
        if (mShowingDetail) {
            removeDetailFragment();
        } else {
            super.onBackPressed();
        }
        Log.d("activity", "AFTER BACK BACKSTACK COUNT: " + getFragmentManager().getBackStackEntryCount());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATE_SHOWING_DETAIL, mShowingDetail);
        outState.putInt(STATE_SELECTED_ITEM, mSelectedItem);
    }

    @Override
    public void onMasterClick(View v, int itemId) {
        removeDetailFragment();
        addDetailFragment(itemId);
        Log.d("activity", "AFTER CLICK BACKSTACK COUNT: " + getFragmentManager().getBackStackEntryCount());
    }

    @Override
    public List<String> getData() {
        if (mRetainedFragment == null) {
            mRetainedFragment = (RetainedFragment) getFragmentManager().findFragmentByTag(RetainedFragment.TAG);
        }
        return mRetainedFragment != null ? mRetainedFragment.getRetainedArray() : null;
    }
}
