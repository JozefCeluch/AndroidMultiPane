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
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class MasterFragment extends Fragment {

    public static final String TAG = MasterFragment.class.getSimpleName();

    public interface MasterFragmentCallback {
        void onMasterClick(View v, int itemId);
        List<String> getData();
    }

    private MasterFragmentCallback mCallback;

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
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new RecyclerAdapter(mCallback != null ? mCallback.getData() : null ));
    }

    private class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.Holder> {

        private List<String> mItems;

        public RecyclerAdapter(List<String> mItems) {
            this.mItems = mItems;
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new Holder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_item, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(Holder holder, final int i) {
            holder.title.setText(mItems.get(i));
            holder.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCallback != null) {
                        mCallback.onMasterClick(v, i);
                    } else {
                        Log.d(TAG, "no click listener");
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mItems == null ? 0 : mItems.size();
        }

        public class Holder extends RecyclerView.ViewHolder {
            View item;
            TextView title;

            public Holder(View itemView) {
                super(itemView);
                item = itemView;
                title = (TextView) itemView.findViewById(R.id.item_title);
            }
        }
    }
 }
