package upv.tfg.freeweather.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import upv.tfg.freeweather.R;
import upv.tfg.freeweather.adapters.interfaces.I_DailyRecyclerViewAdapter;
import upv.tfg.freeweather.adapters.interfaces.I_OnRecyclerItemListener;
import upv.tfg.freeweather.presenter.interfaces.I_DailyPresenter;
import upv.tfg.freeweather.view.fragments.DailyFragment;

public class DailyRecyclerViewAdapter extends RecyclerView.Adapter<DailyRecyclerViewAdapter.ViewHolder> implements I_OnRecyclerItemListener, I_DailyRecyclerViewAdapter {

    // Presenter reference
    private I_DailyPresenter presenter;
    // View reference
    private DailyFragment view;

    private Context context;

    private ArrayList<String> lDays;
    private ArrayList<Integer> lStateImages;
    private ArrayList<String> lMaxTemperatures;
    private ArrayList<String> lMinTemperatures;

    public DailyRecyclerViewAdapter(Context context, DailyFragment view) {
        this.context = context;
        this.view = view;
        lDays = new ArrayList<>();
        lStateImages = new ArrayList<>();
        lMaxTemperatures = new ArrayList<>();
        lMinTemperatures = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_daily_recycler_item, parent, false);
        return new ViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull DailyRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.tvDay.setText(lDays.get(position));
        Glide.with(context)
                .load(lStateImages.get(position))
                .override(120, 120)
                .into(holder.ivState);
        holder.tvMaxTemperature.setText(lMaxTemperatures.get(position));
        holder.tvMinTemperature.setText(lMinTemperatures.get(position));
    }

    @Override
    public int getItemCount() {
        return 7;
    }

    @Override
    public void onAttach(I_DailyPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onDetach() {
        presenter = null;
    }

    @Override
    public void setDays(ArrayList<String> days) {
        this.lDays = days;
    }

    @Override
    public void setImages(ArrayList<Integer> urls) {
        this.lStateImages = urls;
    }

    @Override
    public void setMaxTemperatures(ArrayList<String> maxtemps) {
        this.lMaxTemperatures = maxtemps;
    }

    @Override
    public void setMinTemperatures(ArrayList<String> mintemps) {
        this.lMinTemperatures = mintemps;
    }

    @Override
    public void onAdapterItemClick(View v, int position) {
        presenter.onItemSelected(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView tvDay;
        private ImageView ivState;
        private TextView tvMaxTemperature;
        private TextView tvMinTemperature;
        I_OnRecyclerItemListener onItemListener;

        public ViewHolder(View itemView, I_OnRecyclerItemListener onItemListener) {
            super(itemView);
            tvDay = itemView.findViewById(R.id.tvDay);
            ivState = itemView.findViewById(R.id.ivState);
            tvMaxTemperature = itemView.findViewById(R.id.tvMaxDegrees);
            tvMinTemperature = itemView.findViewById(R.id.tvMinDegrees);

            this.onItemListener = onItemListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemListener.onAdapterItemClick(view, getAdapterPosition());
        }
    }
}
