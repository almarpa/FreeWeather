package upv.tfg.freeweather.presenter.interfaces;


public interface I_HomePresenter {

    //METHODS ACCESSED BY THE VIEW
    void notifyFavButtonClicked(String location);
    void notifyIsItFavourite(String location);
    void notifySearchTextChanged(String text);
    void notifySearchPrediction(String location);

    //METHODS ACCESSED BY THE MODEL
    void makeFavourite();
    void removeFavourite();
}
