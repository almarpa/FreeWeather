package upv.tfg.freeweather.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import upv.tfg.freeweather.R;
import upv.tfg.freeweather.adapters.RadarAdapter;

/**
 * Description.....
 */
public class RadarFragment extends Fragment {

    RadarAdapter adapter;

    public RadarFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        GridView grid = (GridView) inflater.inflate(R.layout.fragment_radar, null);
        adapter = new RadarAdapter(getContext());
        grid.setAdapter(adapter);

        return grid;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        adapter.recycle();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_radar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mRadar) {
            Toast.makeText(getContext(), R.string.menu_fragment_radar, Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}