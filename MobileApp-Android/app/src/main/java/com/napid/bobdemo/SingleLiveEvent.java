package com.napid.bobdemo;

import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

public class SingleLiveEvent<T> extends MutableLiveData<T> {
    private boolean mPending = false;

    @Override
    public void observe(LifecycleOwner owner, final Observer<? super T> observer) {
        // Observe the internal MutableLiveData
        super.observe(owner, new Observer<T>() {
            @Override
            public void onChanged(@Nullable T t) {
                if (mPending) {
                    mPending = false;  // Mark event as consumed
                    observer.onChanged(t);
                }
            }
        });
    }

    @Override
    public void setValue(@Nullable T t) {
        mPending = true;  // Mark event as pending
        super.setValue(t);
    }

    // Optional: If you want to post a value from a background thread
    public void postValue(@Nullable T t) {
        mPending = true;
        super.postValue(t);
    }
}
