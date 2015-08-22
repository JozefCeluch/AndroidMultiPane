package com.plgrnd.android.multipane;

import android.app.Fragment;
import android.os.Bundle;

public class RetainedFragment extends Fragment {

    public static final String TAG = RetainedFragment.class.getSimpleName();

    private String mRetainedString;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }

    public String getRetainedString() {
        return mRetainedString;
    }

    public void setRetainedString(String mRetainedString) {
        this.mRetainedString = mRetainedString;
    }
}
