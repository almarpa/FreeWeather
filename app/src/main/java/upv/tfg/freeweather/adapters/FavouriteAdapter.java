package upv.tfg.freeweather.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextView;
import java.util.ArrayList;

import upv.tfg.freeweather.R;
import upv.tfg.freeweather.adapters.interfaces.I_FavouriteAdapter;
import upv.tfg.freeweather.presenter.FavouriteAdapterPresenter;
import upv.tfg.freeweather.presenter.interfaces.I_FavouriteAdapterPresenter;


public class FavouriteAdapter extends BaseAdapter implements ListAdapter, I_FavouriteAdapter {

    private I_FavouriteAdapterPresenter presenter;

    private ArrayList<String> list = new ArrayList<String>();
    private Context context;
    private SharedPreferences prefs;

    private TextView listItemText;
    private ImageButton deleteBtn;

    public FavouriteAdapter(ArrayList<String> list, Context context) {
        this.list = list;
        this.context = context;

        presenter = new FavouriteAdapterPresenter(this, context);

        prefs = context.getSharedPreferences("SHARED_PREFERENCES",Context.MODE_PRIVATE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.layout_favourite_item, null);
        }

        listItemText = view.findViewById(R.id.tvFavouriteItem);
        listItemText.setText(list.get(position));

        deleteBtn = view.findViewById(R.id.ibDeleteItem);
        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Delete the location from the model and also from the view
                String location = list.get(position);
                prefs.edit().putBoolean(location,false).apply();
                list.remove(location);
                notifyDataSetChanged();
            }
        });
        return view;
    }
}

