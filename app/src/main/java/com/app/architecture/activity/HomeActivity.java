package com.app.architecture.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;

import com.app.architecture.R;
import com.app.architecture.di.component.AppComponent;
import com.app.architecture.fragment.BaseFragment;
import com.app.architecture.fragmentfactory.FragmentFactory;

public class HomeActivity extends BaseActivity {

    private BaseFragment fragment;

    @Override
    public void injectAppComponent(AppComponent appComponent) {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        fragment = FragmentFactory.newInstance().getFragment(FragmentFactory.HOME_FRAGMENT_TAG);
        replaceFragment(R.id.fragment_container, fragment, FragmentFactory.HOME_FRAGMENT_TAG, false);
    }

    @Override
    public void onBackPressed() {

    }
}
