package com.app.architecture.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.architecture.App;
import com.app.architecture.R;
import com.app.architecture.di.component.AppComponent;
import com.app.architecture.fragment.BaseFragment;
import com.app.architecture.fragmentfactory.FragmentFactory;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;


/*
 * BaseActivity : used all child activity
 * abstraction - to used inject child class from here
 * */
public abstract class BaseActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    protected Toolbar mTopToolbar;

    abstract public void injectAppComponent(AppComponent appComponent);

    /*
     *  Inject to All Activity
     * */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectAppComponent(((App) getApplication()).getAppComponent());
    }

    /**
     * @param containerId
     * @param baseFragment
     * @param addToStackName
     * @param isStack
     */
    public void replaceFragment(int containerId, BaseFragment baseFragment, String addToStackName, boolean isStack) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(containerId, baseFragment);
        if (isStack) {
            fragmentTransaction.addToBackStack(addToStackName);
        }
        fragmentTransaction.commit();
    }

    public void launchActivity(Class<? extends BaseActivity> activityClass) {
        launchActivity(activityClass, null);
    }

    public void launchActivity(Class<? extends BaseActivity> classType, Bundle bundle) {
        Intent intent = new Intent(this, classType);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public void launchActivityMain(Class<? extends BaseActivity> activityClass) {
        Intent intent = new Intent(this, activityClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


    public int setColor(int color) {
        return ContextCompat.getColor(this, color);
    }


    public void showToast(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            Toast.makeText(BaseActivity.this, msg, Toast.LENGTH_LONG).show();
        }
    }

    public void showProgressBar() {
        if (BaseActivity.this != null && !BaseActivity.this.isFinishing()) {
            if (progressDialog == null) {
                progressDialog = ProgressDialog.show(BaseActivity.this, "", "", true);
            }
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setContentView(R.layout.progress_layout);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        }
    }

    public void hideProgressBar() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    public void loadImageGlideProfile(String url, final ImageView imageView) {
        Glide.with(getApplicationContext())
                .load(url)
                .placeholder(R.color.color_255_92_92)
                .error(R.color.color_255_92_92)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .into(imageView);

    }

    public void hideKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    InputMethodManager.RESULT_UNCHANGED_SHOWN);
        }
    }

    public void setToolbarHeader(String title) {
        setToolbarHeader(title, true);
    }

    public void setToolbarHeader(String title, boolean isBack) {
        mTopToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mTopToolbar != null) {
            setSupportActionBar(mTopToolbar);
            getSupportActionBar().setDisplayShowHomeEnabled(isBack);
            getSupportActionBar().setDisplayHomeAsUpEnabled(isBack);
            if (isBack)
                mTopToolbar.setNavigationIcon(R.drawable.back_forgot);
            getSupportActionBar().setTitle(title);

            mTopToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }

}
