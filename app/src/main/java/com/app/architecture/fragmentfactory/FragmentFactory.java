package com.app.architecture.fragmentfactory;

import com.app.architecture.fragment.BaseFragment;
import com.app.architecture.fragment.FlavorFragment;
import com.app.architecture.fragment.HomeFragment;
import com.app.architecture.fragment.LoginFragment;


public class FragmentFactory {
    public static String HOME_FRAGMENT_TAG = "homeFragmentTag";
    public static String LOGIN_FRAGMENT_TAG = "loginFragmentTag";
    public static String FLAVOR_FRAGMENT_TAG = "flavorFragmentTag";

    private static FragmentFactory INSTANCE;
    private BaseFragment baseFragment;

    private FragmentFactory() {
    }

    public static FragmentFactory newInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FragmentFactory();
        }
        return INSTANCE;
    }

    public BaseFragment getFragment(String TAG) {
        switch (TAG) {
            case "homeFragmentTag":
                baseFragment = new HomeFragment();
                break;

            case "loginFragmentTag":
                baseFragment = new LoginFragment();
                break;

            case "flavorFragmentTag":
                baseFragment = new FlavorFragment();
                break;
        }
        return baseFragment;
    }
}
