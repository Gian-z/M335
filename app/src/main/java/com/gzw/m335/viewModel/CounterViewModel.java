package com.gzw.m335.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gzw.m335.model.Counter;

public class CounterViewModel extends ViewModel {
    private final MutableLiveData<Counter> counter = new MutableLiveData<>(
            new Counter(0)
    );

    public void updateCounter(int count){
        counter.setValue(new Counter(count));
    }

    public LiveData<Counter> getUiState() {
        return counter;
    }
}
