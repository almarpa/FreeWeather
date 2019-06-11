package upv.tfg.freeweather.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import upv.tfg.freeweather.R;

/**
 * A view with some photos explaining how to use the application
 */
public class HelpFragment extends Fragment {

    private String mParam1 = "PRUEBA";

    public HelpFragment() {
    }

    public static HelpFragment newInstance(String param1, String param2) {
        HelpFragment fragment = new HelpFragment();
        Bundle args = new Bundle();
        args.putString("HELLO", param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString("HELLO");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_help, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
