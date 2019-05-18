package upv.tfg.freeweather.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import upv.tfg.freeweather.adapters.interfaces.I_ViewPagerAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter implements I_ViewPagerAdapter {

    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> fragmentListTitles = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
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

    @Override
    public void addFragment(Fragment fragment, String title) {
        fragmentList.add(fragment);
        fragmentListTitles.add(title);
    }
    @Override
    public void clearFragments(){
        fragmentList.clear();
        fragmentListTitles.clear();
    }
}
