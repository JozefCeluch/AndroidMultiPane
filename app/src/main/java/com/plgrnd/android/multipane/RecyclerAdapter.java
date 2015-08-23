package com.plgrnd.android.multipane;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.Holder> {

    public interface OnListItemClickListener {
        //TODO TRUE for multi select, false for single select
        boolean onItemClick(View v, int position);

        void onItemLongClick(int position);
    }

    private static final int NONE_SELECTED = -1;

    private ArrayList<Integer> mSelectedItems;
    private int mSelectedSingleItem = NONE_SELECTED;

    private List<String> mItems;
    private OnListItemClickListener mClickListener;

    public RecyclerAdapter(OnListItemClickListener onListItemClickListener, List<String> items) {
        mSelectedItems = new ArrayList<>();
        mClickListener = onListItemClickListener;
        mItems = items;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new Holder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_item, viewGroup, false), this);
    }

    @Override
    public void onBindViewHolder(Holder holder, final int i) {
        holder.title.setText(mItems.get(i));
        holder.itemView.setActivated(isSelected(i));
    }

    public boolean isSelected(int position) {
        return position == mSelectedSingleItem; // || mSelectedItems.contains(Integer.valueOf(position));
    }

    public void switchSelectionState(int position) {
        if (position < 0) {
            position = NONE_SELECTED;
        }
        if (mSelectedItems.isEmpty()) {
            int previousSelectedItem = mSelectedSingleItem;
            mSelectedSingleItem = position;
            notifyItemChanged(previousSelectedItem);
        } else {
            int index = mSelectedItems.indexOf(Integer.valueOf(position));
            if (index != -1) {
                mSelectedItems.remove(index);
            } else if (position != NONE_SELECTED) {
                mSelectedItems.add(position);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.size();
    }

    public static class Holder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView title;
        private RecyclerAdapter mAdapter;

        public Holder(View itemView, RecyclerAdapter adapter) {
            super(itemView);
            mAdapter = adapter;
            title = (TextView) itemView.findViewById(R.id.item_title);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Log.d("HOLDER", "ON CLICK: " + position);

            mAdapter.mClickListener.onItemClick(v, position);
            switchSelectedState(position);
        }

        private void switchSelectedState(int position) {
            mAdapter.switchSelectionState(position);
            mAdapter.notifyItemChanged(position);
        }

        @Override
        public boolean onLongClick(View v) {
            int position = getAdapterPosition();
            Log.d("HOLDER", "ON LONG CLICK: " + position);
//            mAdapter.mClickListener.onItemLongClick(position);
//            switchSelectedState(position);
            return true;
        }
    }
}
