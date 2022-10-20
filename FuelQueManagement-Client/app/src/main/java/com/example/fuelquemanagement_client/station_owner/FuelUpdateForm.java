package com.example.fuelquemanagement_client.station_owner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fuelquemanagement_client.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;

public class FuelUpdateForm extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    private TextView tv_arrival_date, tv_arrival_time;
    private int hour, min;
    private EditText et_fuel_amount;
    private Spinner sp_fuel_type;
    private Button btn_update;
    private String selectedType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel_update_form);

        // App Bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Update Fuel Arrival");

        et_fuel_amount = findViewById(R.id.editTextFuelAmount);
        tv_arrival_date = findViewById(R.id.tv_ArrivalDate);
        tv_arrival_time = findViewById(R.id.tv_ArrivalTime);
        btn_update = findViewById(R.id.btn_update);
        btn_update.setOnClickListener(this);
        sp_fuel_type = (Spinner) findViewById(R.id.sp_fuel_type);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.fuel_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_fuel_type.setAdapter(adapter);
        sp_fuel_type.setOnItemSelectedListener(this);

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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
        selectedType = (String) adapterView.getItemAtPosition(pos).toString();
        Toast.makeText(this, selectedType, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_update) {
            postFuelUpdate();
        }
    }

    private void postFuelUpdate() {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            String URL = "http://192.168.1.71:5000/Fuel";
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", selectedType);
            jsonObject.put("amount", et_fuel_amount.getText().toString());
            jsonObject.put("date", tv_arrival_date.getText().toString());
            jsonObject.put("time", tv_arrival_time.getText().toString());
            jsonObject.put("stationsId", "634daba7601fef8b9474a684");
            final String mRequestBody = jsonObject.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("LOG_VOLLEY", response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("LOG_VOLLEY", error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                        return null;
                    }
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };
            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        //If user clicks on the back button
        if(id == android.R.id.home){
            Intent intent = new Intent(FuelUpdateForm.this, StationOwnerDashboard.class);
            startActivity(intent);
        }
        return true;
    }
}