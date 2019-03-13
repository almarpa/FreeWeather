package upv.tfg.freeweather.adapters;
import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import upv.tfg.freeweather.R;


public class RadarAdapter extends BaseAdapter {

    private Context context;
    private TypedArray images;

    public RadarAdapter(Context context) {
        this.context = context;
        images = context.getResources().obtainTypedArray(R.array.grid_list);
    }

    @Override
    public int getCount() {
        return images.length();
    }

    @Override
    public Object getItem(int position) {
        return images.getDrawable(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /*
     * Gets the View to display the data in the required position of the array.
     * */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        // Views are recycled while scrolling through Lists and Grids, so reuse them
        if (convertView == null) {
            // Get the existing ImageView to display the Drawable
            imageView = new ImageView(context);
        } else {
            imageView = (ImageView) convertView;
        }
        // Assign the Drawable at the given position of the array to the obtained ImageView
        imageView.setImageDrawable(images.getDrawable(position));
        return imageView;
    }

    public void recycle() {
        images.recycle();
    }
}
