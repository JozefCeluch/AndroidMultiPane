package com.plgrnd.android.multipane;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class MasterFragment extends Fragment implements RecyclerAdapter.OnListItemClickListener {

    public interface MasterFragmentCallback {
        void onListItemClick(View v, int itemId);

        void onListItemClickInMultiMode(View v, int itemId, ArrayList<Integer> selectedItems);

        void onListItemLongClick(View v, int itemId, ArrayList<Integer> selectedItems);

        List<String> getListData();
    }

    public static final String TAG = MasterFragment.class.getSimpleName();

    private MasterFragmentCallback mCallback;
    private RecyclerView mRecyclerView;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof MasterFragmentCallback) {
            mCallback = (MasterFragmentCallback) activity;
        } else {
            Log.d(TAG, "activity does not implement the interface");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_master, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(new RecyclerAdapter(this, mCallback != null ? mCallback.getListData() : null));
        return view;
    }

    @Override
    public void onItemClick(View v, int position) {
        mCallback.onListItemClick(v, position);
    }

    @Override
    public void onItemClickInMultiMode(View v, int position, ArrayList<Integer> selectedItems) {
        mCallback.onListItemClickInMultiMode(v, position, selectedItems);
    }

    @Override
    public void onItemLongClick(View v, int position, ArrayList<Integer> selectedItems) {
        mCallback.onListItemLongClick(v, position, selectedItems);
    }

    public void selectItem(int position) {
        if (isAdded()) {
            ((RecyclerAdapter) mRecyclerView.getAdapter()).switchSelectionState(position);
        }
    }

    public void deselectItem() {
        if (isAdded()) {
            ((RecyclerAdapter) mRecyclerView.getAdapter()).switchSelectionState(-1);
        }
    }

    public void selectMultiModeItems(ArrayList<Integer> multiModeItems) {
        if (isAdded() && mRecyclerView != null) {
            ((RecyclerAdapter) mRecyclerView.getAdapter()).setSelectedItems(multiModeItems);
        }
    }

}
