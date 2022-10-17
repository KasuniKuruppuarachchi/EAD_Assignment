package com.example.fuelquemanagement_client.station_owner;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.fuelquemanagement_client.R;

import java.util.Calendar;

public class FuelUpdateForm extends AppCompatActivity {
    private TextView tv_arrival_date, tv_arrival_time;
    private int hour, min;
    private EditText et_fuel_amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel_update_form);

        // App Bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Update Fuel Status");

        et_fuel_amount = findViewById(R.id.editTextFuelAmount);
        tv_arrival_date = findViewById(R.id.tv_ArrivalDate);
        tv_arrival_time = findViewById(R.id.tv_ArrivalTime);

        // Time Picker
        tv_arrival_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(FuelUpdateForm.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        hour = hourOfDay;
                        min = minute;
                        Calendar calendar = Calendar.getInstance();

                        // Set hour and minute
                        calendar.set(0, 0, 0, hour, min);

                        // Set selected time on text view
                        tv_arrival_time.setText(DateFormat.format("hh:mm aa", calendar));
                    }
                }, 12, 0, false);
                // Display previous selected time
                timePickerDialog.updateTime(hour, min);
                timePickerDialog.show();
            }
        });

        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Date Picker
        tv_arrival_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(FuelUpdateForm.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String date = day + "/" + month + "/" + year;
                        tv_arrival_date.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
    }
}