package com.sanjana.jbm_covid_protectionapp.homeScreen.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FaceVerificationViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public FaceVerificationViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("FaceVerification Fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}