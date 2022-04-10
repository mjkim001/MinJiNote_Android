package com.example.androidtest05.ui.rotation;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RotationViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public RotationViewModel() {
        mText = new MutableLiveData<>();
//        mText.setValue("This is Rotation fragment");
    }

    public LiveData<String> setText(String text) {
        mText.setValue(text);
        return mText;
    }

    public LiveData<String> getText() {
        return mText;
    }
}