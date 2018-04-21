package com.app.architecture.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.architecture.R;
import com.app.architecture.db.FlavorsContract;


public class FlavorAdapter extends CursorAdapter {

    private static final String LOG_TAG = FlavorAdapter.class.getSimpleName();

    public static class ViewHolder {
        public final ImageView imageView;
        public final TextView textView;

        public ViewHolder(View view) {
            imageView = (ImageView) view.findViewById(R.id.flavor_image);
            textView = (TextView) view.findViewById(R.id.flavor_text);
        }
    }

    public FlavorAdapter(Context context, Cursor c, int flags, int loaderID) {
        super(context, c, flags);
        mContext = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        int layoutId = R.layout.adapter_flavor;
        View view = LayoutInflater.from(context).inflate(layoutId, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        int versionIndex = cursor.getColumnIndex(FlavorsContract.FlavorEntry.COLUMN_VERSION_NAME);
        final String versionName = cursor.getString(versionIndex);
        viewHolder.textView.setText(versionName);
        int imageIndex = cursor.getColumnIndex(FlavorsContract.FlavorEntry.COLUMN_ICON);
        int image = cursor.getInt(imageIndex);
        viewHolder.imageView.setImageResource(image);
    }
}
