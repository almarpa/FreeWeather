package upv.tfg.freeweather.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import upv.tfg.freeweather.R;
import upv.tfg.freeweather.adapters.RecyclerViewAdapter;
import upv.tfg.freeweather.data.model.HourlyPrediction;

public class HourlyFragment extends Fragment implements RecyclerViewAdapter.OnRecyclerItemListener{

    private View view;
    private Context context;

    private HourlyPrediction[] hp;

    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private LinearLayoutManager llm;
    private ArrayList<String> mDays = new ArrayList<>();
    private ArrayList<String> mHours = new ArrayList<>();
    private ArrayList<String> mTemperatures = new ArrayList<>();
    private ArrayList<Integer> mStateImages = new ArrayList<>();
    private ArrayList<Integer> mTempImages = new ArrayList<>();

    public HourlyFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_tab_hourly, container, false);
        context = view.getContext();

        recyclerView = view.findViewById(R.id.recyclerView);
        llm = new LinearLayoutManager(context);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        hp = (HourlyPrediction[]) getArguments().getSerializable("HOURLY");

        mDays = hp[0].getDays();
        mHours = hp[0].getHours();
        mTemperatures = hp[0].getTemperatures();
        mStateImages = hp[0].getImages();
        mTempImages = hp[0].getTempImages();

        adapter = new RecyclerViewAdapter(context, this, mDays, mHours, mTemperatures, mStateImages, mTempImages);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onAdapterItemClick(int position) {
        Intent intent = new Intent(context, HourlyInfoActivity.class);
        intent.putExtra("HourlyPrediction",hp);
        intent.putExtra("Item_Position",position);
        startActivity(intent);
    }
}
