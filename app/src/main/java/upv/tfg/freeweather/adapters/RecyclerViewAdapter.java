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


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private Context mContext;
    private OnRecyclerItemListener mOnItemListener;

    private ArrayList<String> mDays;
    private ArrayList<String> mHours;
    private ArrayList<String> mTemperatures ;
    private ArrayList<Integer> mImageUrls;
    private ArrayList<Integer> mTempImages;


    public RecyclerViewAdapter(Context context, OnRecyclerItemListener onItemListener, ArrayList<String> days, ArrayList<String> hours, ArrayList<String> temperature,ArrayList<Integer> imageUrls, ArrayList<Integer> imageTempUrls) {
        mContext = context;
        mOnItemListener = onItemListener;
        mDays = days;
        mHours = hours;
        mImageUrls = imageUrls;
        mTempImages = imageTempUrls;
        mTemperatures = temperature;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_recycler_item, parent, false);
        return new ViewHolder(view, mOnItemListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Glide.with(mContext)
                .load(mImageUrls.get(position))
                .override(90, 90)
                .into(holder.ivImage);
        Glide.with(mContext)
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


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvDay;
        TextView tvHour;
        TextView tvTemp;
        ImageView ivImage;
        ImageView ivTempImage;
        OnRecyclerItemListener onItemListener;


        public ViewHolder(View itemView, OnRecyclerItemListener onItemListener) {
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
        public void onClick(View v) {
            onItemListener.onAdapterItemClick(getAdapterPosition());
        }
    }

    public interface OnRecyclerItemListener {
        void onAdapterItemClick(int position);
    }

}