package com.app.architecture.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.architecture.R;
import com.app.architecture.di.component.AppComponent;
import com.app.architecture.model.Flower;
import com.bumptech.glide.Glide;

/*
 * FlowerDetailActivity : Used to show flower detail
 * */
public class FlowerDetailActivity extends BaseActivity {

    // Inject to get instance of IApiService from NetModule

    private TextView tvName;
    private TextView tvId;
    private ImageView ivPhoto;
    private TextView tvPhoto;
    private TextView tvCategory;
    private TextView tvDesc;
    private TextView tvPrice;

    /*
     * @param appComponent
     *        This is used inject this activity with dagger2
     *
     * Set to this activity with AppComponent
     * */
    @Override
    public void injectAppComponent(AppComponent appComponent) {
        appComponent.plus(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flower_detail);
        setToolbarHeader("Flower Details");
        tvName = (TextView) findViewById(R.id.tv_name);
        tvId = (TextView) findViewById(R.id.tv_id);
        ivPhoto = (ImageView) findViewById(R.id.iv_photo);
        tvPhoto = (TextView) findViewById(R.id.tv_photo);
        tvPrice = (TextView) findViewById(R.id.tv_price);
        tvCategory = (TextView) findViewById(R.id.tv_category);
        tvDesc = (TextView) findViewById(R.id.tv_desc);
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Flower flower = (Flower) bundle.getSerializable("DATA");
                tvName.setText(flower.getName());
                tvId.setText(String.valueOf(flower.getProductId()));
                tvPrice.setText(String.valueOf(flower.getPrice()));
                tvPhoto.setText(flower.getPhoto());
                tvCategory.setText(flower.getCategory());
                tvDesc.setText(flower.getInstructions());
                Glide.with(this).load("http://services.hanselandpetal.com/photos/" + flower.getPhoto()).into(ivPhoto);
            }
        }
    }
}
