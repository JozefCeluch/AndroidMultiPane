package com.plgrnd.android.multipane;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MasterFragment extends Fragment {

    public static final String TAG = MasterFragment.class.getSimpleName();

    public interface MasterClickListener {
        void onMasterClick(View v);
    }

    private MasterClickListener mClickListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof MasterClickListener) {
            mClickListener = (MasterClickListener) activity;
        } else {
            Log.d(TAG, "activity does not implement the interface");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mClickListener = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_master, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button button = (Button) view.findViewById(R.id.master_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickListener != null) {
                    mClickListener.onMasterClick(v);
                } else {
                    Log.d(TAG, "no click listener");
                }
            }
        });
    }
}
