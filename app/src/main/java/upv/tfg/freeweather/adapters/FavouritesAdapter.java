package upv.tfg.freeweather.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import upv.tfg.freeweather.R;
import upv.tfg.freeweather.adapters.interfaces.I_FavouritesAdapter;
import upv.tfg.freeweather.presenter.interfaces.I_FavouritePresenter;
import upv.tfg.freeweather.view.FavouritesFragment;


public class FavouritesAdapter extends BaseAdapter implements ListAdapter, I_FavouritesAdapter {

    // Presenter reference
    private I_FavouritePresenter presenter;
    // View reference
    private FavouritesFragment fragment;

    // Adapter list
    private List<String> listFavs;

    private TextView listItemText;
    private ImageButton deleteBtn;

    public FavouritesAdapter(FavouritesFragment view) {
        listFavs = new ArrayList<>();
        fragment = view;
    }

    @Override
    public int getCount() {
        return listFavs.size();
    }

    @Override
    public Object getItem(int pos) {
        return listFavs.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) fragment.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.layout_favourite_item, null);
        }

        listItemText = view.findViewById(R.id.tvFavouriteItem);
        listItemText.setText(listFavs.get(position));

        deleteBtn = view.findViewById(R.id.ibDeleteItem);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Delete the location from the model and also from the view
                String location = listFavs.get(position);
                presenter.deleteLocationFromFavourites(location);
                listFavs.remove(location);
                notifyDataSetChanged();
            }
        });

        return view;
    }

    @Override
    public void setFavourites(ArrayList<String> list) {
        this.listFavs = list;
        notifyDataSetChanged();
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
}

