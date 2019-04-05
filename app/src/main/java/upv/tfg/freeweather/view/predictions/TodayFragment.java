package upv.tfg.freeweather.view.predictions;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import upv.tfg.freeweather.R;
import upv.tfg.freeweather.model.HourlyPrediction;

public class TodayFragment extends Fragment {

    private View view;

    public TodayFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_tab_today, container, false);
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
    }

    private void displayData(HourlyPrediction[] sp) {

        TextView descripcion =  getView().findViewById(R.id.tvState);
        TextView humedad =  getView().findViewById(R.id.tvHumedad);
        TextView precipitacion =  getView().findViewById(R.id.tvPrecipitacion);
        TextView sensTermica =  getView().findViewById(R.id.tvSensTermica);

        descripcion.setText(sp[0].getPrediccion().getHoraria().get(0).getEstadoCielo().get(0).getDescripcion());
        humedad.setText(sp[0].getPrediccion().getHoraria().get(0).getHumedadRelativa().get(0).getValue());
        precipitacion.setText(sp[0].getPrediccion().getHoraria().get(0).getPrecipitacion().get(0).getValue());
        sensTermica.setText(sp[0].getPrediccion().getHoraria().get(0).getSensTermica().get(0).getValue());

    }

}
