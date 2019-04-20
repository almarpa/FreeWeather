package upv.tfg.freeweather.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import upv.tfg.freeweather.R;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    //vars
    private ArrayList<String> mHours = new ArrayList<>();
    private ArrayList<String> mTemperatures = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapter(Context context, ArrayList<String> hours, ArrayList<String> imageUrls, ArrayList<String> temperature) {
        mHours = hours;
        mImageUrls = imageUrls;
        mTemperatures = temperature;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recycler_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        /*Glide.with(mContext)
                .asBitmap()
                .load(mImageUrls.get(position))
                .into(holder.image);
        */
        holder.tvTemp.setText(mTemperatures.get(position));

        holder.tvHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, mHours.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvHour;
        TextView tvTemp;
        ImageView ivImage;

        public ViewHolder(View itemView){
            super(itemView);
            tvHour.findViewById(R.id.ivImage);
            tvHour.findViewById(R.id.tvTemperature);
            ivImage.findViewById(R.id.tvHour);

        }
    }
}
