package com.plgrnd.android.multipane;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class DetailFragment extends Fragment {

    public static final String TAG = DetailFragment.class.getSimpleName();

    private static final String ARG_ITEM_ID = "arg_item_it";
    private int mItemId;

    public static DetailFragment newInstance(int itemId) {
        DetailFragment frag = new DetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ITEM_ID, itemId);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            mItemId = getArguments().getInt(ARG_ITEM_ID);
        }
        TextView id = (TextView) view.findViewById(R.id.detail_id_text);
        RetainedFragment retainedFragment = (RetainedFragment) getFragmentManager().findFragmentByTag(RetainedFragment.TAG);
        if (retainedFragment != null) {
            id.setText("RETAINED TEXT: " + retainedFragment.getRetainedItem(mItemId));
        } else {
            id.setText("NO RETAINED FRAGMENT");
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_detail, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_detail:
                Toast.makeText(getActivity(), "DETAIL ACTION CLICKED", Toast.LENGTH_SHORT).show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
