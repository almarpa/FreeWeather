package upv.tfg.freeweather.adapters.interfaces;

import android.support.v4.app.Fragment;

import upv.tfg.freeweather.presenter.interfaces.I_HomePresenter;

public interface I_ViewPagerAdapter {
    void onAttach(I_HomePresenter presenter);
    void onDetach();
    void addFragment(Fragment fragment, String title);
    void clearFragments();
}
