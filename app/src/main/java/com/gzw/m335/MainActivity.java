package com.gzw.m335;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.gzw.m335.viewModel.CounterViewModel;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private CounterViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        model = new ViewModelProvider(this).get(CounterViewModel.class);
        model.getUiState().observe(this, uiState -> {
            setLabel(String.valueOf(uiState.getCount()));
        });

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        int persCounter = sharedPref.getInt(getString(R.string.persist_key_counter), 0);
        model.updateCounter(persCounter);
    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(getString(R.string.persist_key_counter), getCount());
        editor.apply();
    }

    private void setLabel(String s){
        TextView label = (TextView)findViewById(R.id.lbl_counter);
        label.setText(s);
    }

    private int getCount(){
        return Objects.requireNonNull(model.getUiState().getValue()).getCount();
    }

    public void onClickAdd(View v){
        model.updateCounter(getCount() + 1);
    }

    public void onClickSubtract(View v){
       model.updateCounter(getCount() - 1);
    }

    public void onClickReset(View v){
        model.updateCounter(0);
    }
}