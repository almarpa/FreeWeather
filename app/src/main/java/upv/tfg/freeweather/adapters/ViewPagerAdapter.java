package upv.tfg.freeweather.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import upv.tfg.freeweather.adapters.interfaces.I_ViewPagerAdapter;
import upv.tfg.freeweather.presenters.interfaces.I_HomePresenter;
import upv.tfg.freeweather.views.fragments.HomeFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter implements I_ViewPagerAdapter {

    // Presenter reference
    private I_HomePresenter presenter;
    // View reference
    private HomeFragment view;

    private final List<Fragment> fragmentList;
    private final List<String> fragmentListTitles;

    public ViewPagerAdapter(FragmentManager fm, HomeFragment view) {
        super(fm);
        this.view = view;
        fragmentList  = new ArrayList<>();
        fragmentListTitles = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentListTitles.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentListTitles.get(position);
    }

    /**
     * Associates the presenter to the adapter
     * @param presenter presenter class
     */
    @Override
    public void onAttach(I_HomePresenter presenter) {
        this.presenter = presenter;
    }

    /**
     * Dissociate the presenter
     */
    @Override
    public void onDetach() {
        presenter = null;
    }

    /**
     * Add a new fragment to the list
     * @param fragment
     * @param title
     */
    @Override
    public void addFragment(Fragment fragment, String title) {
        fragmentList.add(fragment);
        fragmentListTitles.add(title);
        notifyDataSetChanged();
    }

    /**
     * Clear both lists
     */
    @Override
    public void clearFragments(){
        fragmentList.clear();
        fragmentListTitles.clear();
        notifyDataSetChanged();
    }
}
