package com.gzw.bmi;

import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.gzw.bmi.components.ServiceProvider;
import com.gzw.bmi.services.BmiService;

public class BmiResultActivity extends AppCompatActivity {
    ServiceProvider<BmiService> bmiServiceProvider;

    public static final String ATTR_HEIGHT = "height";
    public static final String ATTR_WEIGHT = "weight";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi_result);
    }

    @Override
    protected void onStart() {
        super.onStart();

        TextView classification = findViewById(R.id.textView2);
        TextView bmi = findViewById(R.id.textView3);

        Double height = getIntent().getDoubleExtra(ATTR_HEIGHT, 0);
        Double weight = getIntent().getDoubleExtra(ATTR_WEIGHT, 0);

        bmiServiceProvider = new ServiceProvider<BmiService>(this, BmiService.class, Context.BIND_AUTO_CREATE){
            @Override
            public void onServiceConnected(ComponentName className, IBinder service) {
                super.onServiceConnected(className, service);

                double bmiScore = getService().calculateBMI(weight, height);
                bmi.setText(String.valueOf(bmiScore));
                classification.setText(getService().classifyBMI(bmiScore));
            }
        }.bind();
    }

    @Override
    protected void onStop() {
        super.onStop();
        bmiServiceProvider.unbind();
    }
}