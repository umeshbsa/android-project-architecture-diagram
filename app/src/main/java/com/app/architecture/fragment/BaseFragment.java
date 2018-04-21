package com.app.architecture.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.app.architecture.activity.BaseActivity;

public abstract class BaseFragment extends Fragment {

    protected BaseActivity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (BaseActivity) context;
    }
}
