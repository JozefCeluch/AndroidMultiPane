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

import java.util.List;

public class MasterFragment extends Fragment implements RecyclerAdapter.OnListItemClickListener {

    public interface MasterFragmentCallback {
        void onListItemClick(View v, int itemId);

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
        return inflater.inflate(R.layout.fragment_master, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(new RecyclerAdapter(this, mCallback != null ? mCallback.getListData() : null));
    }

    @Override
    public boolean onItemClick(View v, int position) {
        mCallback.onListItemClick(v, position);
        return true;
    }

    @Override
    public void onItemLongClick(int position) {

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

}
