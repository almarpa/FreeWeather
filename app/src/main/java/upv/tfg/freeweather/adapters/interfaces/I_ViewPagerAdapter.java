package upv.tfg.freeweather.adapters.interfaces;

import android.support.v4.app.Fragment;

public interface I_ViewPagerAdapter {
    void addFragment(Fragment fragment, String title);
    void clearFragments();
}
