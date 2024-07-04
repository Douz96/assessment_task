package com.example.mytask.ui.user_details;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UserDetailsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public UserDetailsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is user fragment");

    }

    public LiveData<String> getText() {
        return mText;
    }
}