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

import java.util.Date;

public class MainActivity extends AppCompatActivity implements MasterFragment.MasterClickListener {

    private static final String STATE_SHOWING_DETAIL = "state_showing_detail";
    private boolean mDualPane;
    private boolean mShowingDetail;

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
            RetainedFragment retainedFragment = new RetainedFragment();
            retainedFragment.setRetainedString(new Date().toString());
            fm.beginTransaction().add(retainedFragment, RetainedFragment.TAG).commit();
            if (!mDualPane) {
                fm.beginTransaction()
                        .replace(R.id.single_pane_container, new MasterFragment(), MasterFragment.TAG)
                        .commit();
            }
        } else if (savedInstanceState.getBoolean(STATE_SHOWING_DETAIL)) {
            removeDetailFragment();
            addDetailFragment();
        }
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

    @Override
    public void onMasterClick(View v) {
        removeDetailFragment();
        addDetailFragment();
        Log.d("activity", "AFTER CLICK BACKSTACK COUNT: " + getFragmentManager().getBackStackEntryCount());
    }

    private void addDetailFragment() {
        mShowingDetail = true;

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (mDualPane) {
            ft.replace(R.id.detail_container, new DetailFragment(), DetailFragment.TAG);
        } else {
            ft.replace(R.id.single_pane_container, new DetailFragment(), DetailFragment.TAG);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }
        ft.addToBackStack(null).commit();
    }

    private void removeDetailFragment() {
        mShowingDetail = false;

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
    }
}
