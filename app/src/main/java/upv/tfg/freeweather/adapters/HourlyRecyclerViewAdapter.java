package upv.tfg.freeweather.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import upv.tfg.freeweather.R;
import upv.tfg.freeweather.adapters.interfaces.I_HourlyRecyclerViewAdapter;
import upv.tfg.freeweather.adapters.interfaces.I_OnRecyclerItemListener;
import upv.tfg.freeweather.presenter.interfaces.I_HourlyPresenter;
import upv.tfg.freeweather.view.fragments.HourlyFragment;


public class HourlyRecyclerViewAdapter extends RecyclerView.Adapter<HourlyRecyclerViewAdapter.ViewHolder> implements I_OnRecyclerItemListener, I_HourlyRecyclerViewAdapter {

    // Presenter reference
    private I_HourlyPresenter presenter;
    // View reference
    private HourlyFragment view;

    private Context context;

    private ArrayList<String> mDays;
    private ArrayList<String> mHours;
    private ArrayList<String> mTemperatures;
    private ArrayList<Integer> mImageUrls;
    private ArrayList<Integer> mTempImages;


    public HourlyRecyclerViewAdapter(Context context, HourlyFragment view) {
        this.context = context;
        this.view = view;
        mDays = new ArrayList<>();
        mHours = new ArrayList<>();
        mImageUrls = new ArrayList<>();
        mTempImages = new ArrayList<>();
        mTemperatures = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_hourly_recycler_item, parent, false);
        return new ViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Glide.with(context)
                .load(mImageUrls.get(position))
                .override(90, 90)
                .into(holder.ivImage);
        Glide.with(context)
                .load(mTempImages.get(position))
                .override(90, 90)
                .into(holder.ivTempImage);
        holder.tvTemp.setText(mTemperatures.get(position));
        holder.tvHour.setText(mHours.get(position));
        holder.tvDay.setText(mDays.get(position));
    }

    @Override
    public int getItemCount() {
        return mDays.size();
    }

    @Override
    public void onAttach(I_HourlyPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onDetach() {
        presenter = null;
    }

    @Override
    public void setDays(ArrayList<String> days) {
        this.mDays = days;
    }

    @Override
    public void setHours(ArrayList<String> hours) {
        this.mHours = hours;
    }

    @Override
    public void setImageUrls(ArrayList<Integer> urls) {
        this.mImageUrls = urls;
    }

    @Override
    public void setTempImages(ArrayList<Integer> tempImages) {
        this.mTempImages = tempImages;
    }

    @Override
    public void setTemperatures(ArrayList<String> temperatures) {
        this.mTemperatures = temperatures;
    }

    /**
     * Listener called when an adapter item is clicked
     * @param view actual view
     * @param position adapter item position selected
     */
    @Override
    public void onAdapterItemClick(View view, int position) {
        presenter.onItemSelected(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvDay;
        TextView tvHour;
        TextView tvTemp;
        ImageView ivImage;
        ImageView ivTempImage;
        I_OnRecyclerItemListener onItemListener;


        public ViewHolder(View itemView, I_OnRecyclerItemListener onItemListener) {
            super(itemView);
            tvDay = itemView.findViewById(R.id.tvDay);
            tvHour = itemView.findViewById(R.id.tvHour);
            tvTemp = itemView.findViewById(R.id.tvTemperature);
            ivImage = itemView.findViewById(R.id.ivImage);
            ivTempImage = itemView.findViewById(R.id.ivTemp);
            this.onItemListener = onItemListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemListener.onAdapterItemClick(view, getAdapterPosition());
        }
    }
}