package com.sanjana.jbm_covid_protectionapp.homeScreen.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LocationViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public LocationViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Location Manager Fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}