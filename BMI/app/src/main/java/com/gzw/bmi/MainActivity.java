package com.gzw.bmi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        EditText height = findViewById(R.id.txt_height);
        EditText weight = findViewById(R.id.txt_weight);
        Button submit = findViewById(R.id.button);

        submit.setOnClickListener((View v) ->{
            Intent intent = new Intent(this, BmiResultActivity.class);
            intent.putExtra(BmiResultActivity.ATTR_HEIGHT, Double.parseDouble(height.getText().toString()));
            intent.putExtra(BmiResultActivity.ATTR_WEIGHT, Double.parseDouble(weight.getText().toString()));

            startActivity(intent);
        });
    }
}