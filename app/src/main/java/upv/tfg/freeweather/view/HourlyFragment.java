package upv.tfg.freeweather.view;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import upv.tfg.freeweather.R;
import upv.tfg.freeweather.adapters.RecyclerViewAdapter;

public class HourlyFragment extends Fragment {

    private View view;

    //vars
    private ArrayList<String> mHours = new ArrayList<>();
    private ArrayList<String> mTemperatures = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();

    public HourlyFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_tab_hourly, container, false);

        //INITIALIZATION
        mHours.add("1");
        mHours.add("2");
        mHours.add("3");
        mHours.add("4");
        mHours.add("5");

        mTemperatures.add("10");
        mTemperatures.add("11");
        mTemperatures.add("12");
        mTemperatures.add("10");
        mTemperatures.add("11");

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        //Instantiate adapter
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getContext(), mHours, mImageUrls, mTemperatures);
        //Handle listview and assign adapter
        recyclerView.setAdapter(adapter);
        /*
        //Assign listener to the items
        lView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                                    long arg3)
            {
                String location = lView.getAdapter().getItem(position).toString();

                NavigationView navigationView = getActivity().findViewById(R.id.navView);
                navigationView.getMenu().getItem(0).setChecked(true);

                //Create a new HomeFragment to search the prediction of the location.
                HomeFragment homeFragment = new HomeFragment();

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameLayout, homeFragment, "home")
                        .commit();
            }
        });
        */
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public interface OnFragmentInteractionListener {
    }
}
