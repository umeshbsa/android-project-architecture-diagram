package com.app.architecture.fragment;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.app.architecture.R;
import com.app.architecture.adapter.FlavorAdapter;
import com.app.architecture.db.FlavorDbManager;
import com.app.architecture.db.FlavorsContract;
import com.app.architecture.model.Flavor;


/**
 * A placeholder fragment containing a simple view.
 */
public class FlavorFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private FlavorAdapter mFlavorAdapter;
    private GridView mGridView;

    private static final int CURSOR_LOADER_ID = 0;
    Flavor[] flavors = {
            new Flavor("Cupcake", "The first release of Android", R.drawable.back_forgot),
            new Flavor("Donut", "The world's information is at your fingertips – " +
                    "search the web, get driving directions... or just watch cat videos.",
                    R.drawable.back_forgot),

    };

    public FlavorFragment() {
    }


    Handler handler = new Handler() {

    };


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Cursor c =
                getActivity().getContentResolver().query(FlavorsContract.FlavorEntry.CONTENT_URI,
                        new String[]{FlavorsContract.FlavorEntry._ID},
                        null,
                        null,
                        null);
        if (c.getCount() == 0) {
            FlavorDbManager.insertFlavorData(activity, flavors, handler);
        }
        // initialize loader
        getLoaderManager().initLoader(CURSOR_LOADER_ID, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflate fragment_main layout
        final View rootView = inflater.inflate(R.layout.fragment_flavor, container, false);


        // initialize our FlavorAdapter
        mFlavorAdapter = new FlavorAdapter(getActivity(), null, 0, CURSOR_LOADER_ID);
        // initialize mGridView to the GridView in fragment_main.xml
        mGridView = (GridView) rootView.findViewById(R.id.flavors_grid);
        // set mGridView adapter to our CursorAdapter
        mGridView.setAdapter(mFlavorAdapter);


        return rootView;
    }

    // insert data into database
    public void insertData() {
        ContentValues[] flavorValuesArr = new ContentValues[flavors.length];
        // Loop through static array of Flavors, add each to an instance of ContentValues
        // in the array of ContentValues
        for (int i = 0; i < flavors.length; i++) {
            flavorValuesArr[i] = new ContentValues();
            flavorValuesArr[i].put(FlavorsContract.FlavorEntry.COLUMN_ICON, flavors[i].image);
            flavorValuesArr[i].put(FlavorsContract.FlavorEntry.COLUMN_VERSION_NAME,
                    flavors[i].name);
            flavorValuesArr[i].put(FlavorsContract.FlavorEntry.COLUMN_DESCRIPTION,
                    flavors[i].description);
        }

        // bulkInsert our ContentValues array
        activity.getContentResolver().bulkInsert(FlavorsContract.FlavorEntry.CONTENT_URI,
                flavorValuesArr);
    }

    // Attach loader to our flavors database query
    // run when loader is initialized
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(),
                FlavorsContract.FlavorEntry.CONTENT_URI,
                null,
                null,
                null,
                null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    // Set the cursor in our CursorAdapter once the Cursor is loaded
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        mFlavorAdapter.swapCursor(data);


    }

    // reset CursorAdapter on Loader Reset
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mFlavorAdapter.swapCursor(null);
    }
}
