package upv.tfg.freeweather.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import upv.tfg.freeweather.R;
import upv.tfg.freeweather.adapters.interfaces.I_FavouritesRecyclerViewAdapter;
import upv.tfg.freeweather.adapters.interfaces.I_OnRecyclerItemListener;
import upv.tfg.freeweather.presenter.interfaces.I_FavouritePresenter;
import upv.tfg.freeweather.view.fragments.FavouritesFragment;


public class FavouritesRecyclerViewAdapter extends RecyclerView.Adapter<FavouritesRecyclerViewAdapter.ViewHolder> implements I_OnRecyclerItemListener, I_FavouritesRecyclerViewAdapter {

    // Presenter reference
    private I_FavouritePresenter presenter;
    // View reference
    private FavouritesFragment view;

    // Adapter list
    private List<String> listFavs;
    private Context context;

    public FavouritesRecyclerViewAdapter(Context context, FavouritesFragment view) {
        this.context = context;
        this.view = view;
        listFavs = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_favourite_item, viewGroup, false);
        return new ViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouritesRecyclerViewAdapter.ViewHolder holder, final int pos) {
        Glide.with(context)
                .load(R.drawable.bin_icon)
                .into(holder.ivDelete);
        holder.tvLocation.setText(listFavs.get(pos));
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String location = listFavs.get(pos);
                presenter.onLocationDeleted(listFavs.get(pos));
                listFavs.remove(location);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listFavs.size();
    }

    /**
     * Set the old favourite list
     * @param list favourite list
     */
    @Override
    public void setFavourites(ArrayList<String> list) {
        this.listFavs = list;
    }

    /**
     * Remove a favourite location from the list
     * @param pos
     */
    @Override
    public void removeFavourite(int pos) {
        this.listFavs.remove(pos);
    }

    /**
     * Listener called when an adapter item is clicked
     * @param view actual view
     * @param position adapter item position
     */
    @Override
    public void onAdapterItemClick(View view, int position) {
        presenter.onLocationPressed(listFavs.get( position ) );
    }

    /**
     * Associates the presenter to the adapter
     * @param presenter presenter class
     */
    @Override
    public void onAttach(I_FavouritePresenter presenter) {
        this.presenter = presenter;
    }

    /**
     * Dissociate the presenter
     */
    @Override
    public void onDetach() {
        presenter = null;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvLocation;
        ImageButton ivDelete;
        I_OnRecyclerItemListener onItemListener;

        public ViewHolder(View itemView, I_OnRecyclerItemListener onItemListener) {
            super(itemView);
            tvLocation = itemView.findViewById(R.id.tvFavouriteItem);
            ivDelete = itemView.findViewById(R.id.ibDeleteItem);
            this.onItemListener = onItemListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemListener.onAdapterItemClick(view, getAdapterPosition());
        }
    }

}

