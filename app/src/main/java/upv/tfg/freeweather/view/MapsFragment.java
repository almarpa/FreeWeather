package upv.tfg.freeweather.view;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import upv.tfg.freeweather.R;
import upv.tfg.freeweather.presenter.MapsPresenter;
import upv.tfg.freeweather.presenter.interfaces.I_MapsPresenter;
import upv.tfg.freeweather.view.interfaces.I_MapsView;

/**
 * This fragment shows a spinner with some types of maps
 */
public class MapsFragment extends Fragment implements I_MapsView {

    // Presenter reference
    private I_MapsPresenter presenter;

    private View view;
    private Spinner spinner;
    private ProgressBar progressBar;
    private ImageView ivMap;
    private ImageView ivMap2;
    private TextView tvInfo;

    public MapsFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        presenter = new MapsPresenter(this, getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_menu_maps, container, false);

        spinner = view.findViewById(R.id.spinner);
        progressBar = view.findViewById(R.id.prgBar);
        ivMap = view.findViewById(R.id.ivMap);
        ivMap2 = view.findViewById(R.id.ivMap2);
        tvInfo = view.findViewById(R.id.tvInformation);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                presenter.notifySpinnerClicked(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;
    }

    @Override
    public void setMapImage(Bitmap img) {
        ivMap.setImageBitmap(img);
        ivMap.setVisibility(View.VISIBLE);
    }

    @Override
    public void setMapImage2(Bitmap img) {
        ivMap2.setImageBitmap(img);
        ivMap2.setVisibility(View.VISIBLE);
    }

    @Override
    public void setMapImage2Invisible() {
        ivMap2.setVisibility(View.GONE);
    }

    @Override
    public void setTextDescription(String description){
        tvInfo.setText(description);
    }

    @Override
    public void clearData() {
        tvInfo.setText("");
        ivMap.setImageResource(0);
        ivMap2.setImageResource(0);
    }

    @Override
    public void initProgressBar(){
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void closeProgressBar(){
        progressBar.setVisibility(View.INVISIBLE);
    }
}