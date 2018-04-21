package com.app.architecture.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.architecture.R;
import com.app.architecture.activity.FlowerDetailActivity;
import com.app.architecture.model.Flower;
import com.bumptech.glide.Glide;

import java.util.List;

/*
 * FlowerImageAdapter : Show all flower in list
 * */
public class FlowerImageAdapter extends RecyclerView.Adapter<FlowerImageAdapter.FlowerHolder> {

    private final LayoutInflater inflater;
    private final Context context;
    private List<Flower> flowers;

    public FlowerImageAdapter(Context context, List<Flower> flowers) {
        this.inflater = LayoutInflater.from(context);
        this.flowers = flowers;
        this.context = context;
    }

    @Override
    public FlowerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.adapter_flower, parent, false);
        FlowerHolder holder = new FlowerHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(FlowerHolder holder, int position) {
        holder.setFlowerUI(flowers.get(position));
    }

    @Override
    public int getItemCount() {
        return flowers.size();
    }

    public class FlowerHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvId;
        private ImageView ivPhoto;
        private TextView tvPhoto;
        private TextView tvCategory;
        private TextView tvDesc;
        private TextView tvPrice;
        private LinearLayout linearRoot;

        public FlowerHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvId = (TextView) itemView.findViewById(R.id.tv_id);
            ivPhoto = (ImageView) itemView.findViewById(R.id.iv_photo);
            tvPhoto = (TextView) itemView.findViewById(R.id.tv_photo);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_price);
            tvCategory = (TextView) itemView.findViewById(R.id.tv_category);
            tvDesc = (TextView) itemView.findViewById(R.id.tv_desc);
            linearRoot = (LinearLayout) itemView.findViewById(R.id.linear_root);
        }

        public void setFlowerUI(final Flower flower) {
            tvName.setText(flower.getName());
            tvId.setText(String.valueOf(flower.getProductId()));
            tvPrice.setText(String.valueOf(flower.getPrice()));
            tvPhoto.setText(flower.getPhoto());
            tvCategory.setText(flower.getCategory());
            tvDesc.setText(flower.getInstructions());
            Glide.with(context).load("http://services.hanselandpetal.com/photos/" + flower.getPhoto()).into(ivPhoto);
            linearRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("DATA", flower);
                    Intent intent = new Intent(context, FlowerDetailActivity.class);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        }
    }
}


