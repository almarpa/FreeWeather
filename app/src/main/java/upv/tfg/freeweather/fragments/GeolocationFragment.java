/*
 * Copyright (c) 2016. David de Andrés and Juan Carlos Ruiz, DISCA - UPV, Development of apps for mobile devices.
 */

package upv.tfg.freeweather.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import upv.tfg.freeweather.R;

/**
 * Description.....
 */
public class GeolocationFragment extends Fragment {

    /**
     * Required empty public constructor.
     */
    public GeolocationFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // The Fragment can now add actions to the ActionBar and react when they are clicked
        setHasOptionsMenu(true);
    }

    /**
     * Creates the View associated to this Fragment from a Layout resource.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_geolocation, null);
    }

    /**
     * This method is executed when the fragment is created to populate the ActionBar with actions.
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Generate the Menu object from the XML resource file
        inflater.inflate(R.menu.menu_geolocation, menu);
    }

    /**
     * This method is executed when any action from the ActionBar is selected.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Determine the action to take place according to the Id of the action selected
        if (item.getItemId() == R.id.mLocalizame) {
            // Notify the user that this action has been selected
            Toast.makeText(getContext(), R.string.menu_fragment_localizame, Toast.LENGTH_SHORT).show();
            return true;
        }
        // There was no custom behaviour for that action, so let the system take care of it
        return super.onOptionsItemSelected(item);
    }
}
