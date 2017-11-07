package com.ps.androidlib.widget.nicespinner;

import android.content.Context;
import android.widget.ListAdapter;

/**
 * @author angelo.marchesin
 */

public class NiceSpinnerAdapterWrapper extends NiceSpinnerBaseAdapter {

    private final ListAdapter mBaseAdapter;

    public NiceSpinnerAdapterWrapper(Context context, ListAdapter toWrap, int textColor, int backgroundSelector) {
        super(context, textColor, backgroundSelector);
        mBaseAdapter = toWrap;
    }

    @Override
    public int getCount() {
        return mBaseAdapter.getCount() - 1;
    }

    @Override
    public SpinnerModel getItem(int position) {
        if (position >= mSelectedIndex) {
            return (SpinnerModel) mBaseAdapter.getItem(position + 1);
        } else {
            return (SpinnerModel) mBaseAdapter.getItem(position);
        }
    }

    @Override
    public SpinnerModel getItemInDataset(int position) {
        return (SpinnerModel) mBaseAdapter.getItem(position);
    }
}