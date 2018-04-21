package com.app.architecture.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.app.architecture.R;
import com.app.architecture.di.component.AppComponent;
import com.app.architecture.fragment.BaseFragment;
import com.app.architecture.fragmentfactory.FragmentFactory;

import java.util.Observable;
import java.util.Observer;

public class FlavorActivity extends BaseActivity {

    private BaseFragment fragment;

    @Override
    public void injectAppComponent(AppComponent appComponent) {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flavor);

        fragment = FragmentFactory.newInstance().getFragment(FragmentFactory.FLAVOR_FRAGMENT_TAG);
        replaceFragment(R.id.fragment_container, fragment, FragmentFactory.FLAVOR_FRAGMENT_TAG, false);
    }

    @Override
    public void onBackPressed() {



    }
}
