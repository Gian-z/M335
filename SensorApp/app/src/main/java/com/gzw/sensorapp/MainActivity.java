package com.gzw.sensorapp;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();

        TextView xValue = findViewById(R.id.xValue);
        TextView yValue = findViewById(R.id.yValue);
        TextView zValue = findViewById(R.id.zValue);

        LineDataSet xLineDataSet = createLineDataSet("xLineDataSet", R.color.green);
        LineDataSet yLineDataSet = createLineDataSet("yLineDataSet", R.color.blue);
        LineDataSet zLineDataSet = createLineDataSet("zLineDataSet", R.color.red);

        LineChart chart = findViewById(R.id.chart);
        LineData lineData = new LineData(xLineDataSet, yLineDataSet, zLineDataSet);
        chart.setData(lineData);

        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);

        sensorManager.registerListener(new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];

                xValue.setText(String.valueOf(x));
                yValue.setText(String.valueOf(y));
                zValue.setText(String.valueOf(z));

                addEntry(lineData, 0, x);
                addEntry(lineData, 1, y);
                addEntry(lineData, 2, z);

                lineData.notifyDataChanged();
                chart.notifyDataSetChanged();
                chart.invalidate();
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                //ignored
            }
        }, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void addEntry(LineData lineData, int set, float value) {
        ILineDataSet dataSet = lineData.getDataSetByIndex(set);
        Entry entry = new Entry(dataSet.getEntryCount(), value);
        lineData.addEntry(entry, lineData.getIndexOfDataSet(dataSet));
        if (dataSet.getEntryCount() > 20) {
            dataSet.removeFirst();
        }
    }

    private LineDataSet createLineDataSet(String label, int colorResource) {
        LineDataSet dataSet = new LineDataSet(null, label);
        int color = ContextCompat.getColor(this, colorResource);
        dataSet.setColor(color);
        dataSet.setCircleColor(color);
        dataSet.setDrawValues(false);
        return dataSet;
    }
}