package com.plgrnd.android.multipane;

import android.app.Fragment;
import android.os.Bundle;

import java.util.Arrays;
import java.util.List;

public class RetainedFragment extends Fragment {

    public static final String TAG = RetainedFragment.class.getSimpleName();

    private List<String> mRetainedArray;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        String[] array = {"item0", "item1", "item2", "item3", "item4", "item5", "item6", "item7"};
        mRetainedArray = Arrays.asList(array);
    }

    public List<String> getRetainedArray() {
        return mRetainedArray;
    }

    public String getRetainedItem(int itemId) {
        return mRetainedArray != null ? mRetainedArray.get(itemId) : null;
    }
}
