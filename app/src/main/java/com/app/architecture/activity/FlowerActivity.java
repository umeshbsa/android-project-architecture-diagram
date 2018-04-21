package com.app.architecture.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.app.architecture.R;
import com.app.architecture.adapter.FlowerImageAdapter;
import com.app.architecture.di.component.AppComponent;
import com.app.architecture.listner.IApiService;
import com.app.architecture.model.Flower;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
 * FlowerActivity : Used to show all flower list with adapter
 * */
public class FlowerActivity extends BaseActivity {

    /*
     * Inject to get instance of IApiService from NetModule
     * */
    @Inject
    IApiService request;
    private RecyclerView recycleViewFlower;
    private Toolbar mTopToolbar;

    /*
     * Set to this activity with AppComponent
     * */
    @Override
    public void injectAppComponent(AppComponent appComponent) {
        appComponent.plus(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flower);
        setToolbarHeader("Flower", false);
        recycleViewFlower = (RecyclerView) findViewById(R.id.recycle_view_flower);
        recycleViewFlower.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recycleViewFlower.setLayoutManager(mLayoutManager);

        // call flower api list
        showProgressBar();
        Call<List<Flower>> call = request.getFlowersAPI();
        call.enqueue(new Callback<List<Flower>>() {
            @Override
            public void onResponse(Call<List<Flower>> call, Response<List<Flower>> response) {
                if (response != null) {
                    List<Flower> flowers = response.body();
                    if (flowers != null && flowers.size() > 0) {
                        FlowerImageAdapter adapter = new FlowerImageAdapter(FlowerActivity.this, flowers);
                        recycleViewFlower.setAdapter(adapter);
                    }
                    hideProgressBar();
                }
            }

            @Override
            public void onFailure(Call<List<Flower>> call, Throwable t) {
                hideProgressBar();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_flower, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_favorite) {
            showToast("Action clicked");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
