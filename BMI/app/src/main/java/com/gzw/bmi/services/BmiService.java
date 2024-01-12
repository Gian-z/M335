package com.gzw.bmi.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.gzw.bmi.components.TypedBinder;

public class BmiService extends Service {

    private final IBinder binder = new TypedBinder<BmiService>() {
        @Override
        public BmiService getService(){
            return BmiService.this;
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public double calculateBMI(double weight, double height) {
        if (weight <= 0 || height <= 0) {
            System.out.println("Ungültige Eingabewerte für Gewicht oder Größe.");
            return -1;
        }

        return weight / (height * height);
    }

    public String classifyBMI(double bmi) {
        if (bmi < 18.5) {
            return "Untergewicht";
        } else if (bmi < 24.9) {
            return "Normalgewicht";
        } else if (bmi < 29.9) {
            return "Übergewicht";
        } else {
            return "Adipositas";
        }
    }
}
