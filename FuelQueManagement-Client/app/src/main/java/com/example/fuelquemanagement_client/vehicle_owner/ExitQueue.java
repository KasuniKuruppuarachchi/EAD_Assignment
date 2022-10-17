package com.example.fuelquemanagement_client.vehicle_owner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fuelquemanagement_client.R;
import com.example.fuelquemanagement_client.constants.Constants;

import java.util.Date;

public class ExitQueue extends AppCompatActivity implements View.OnClickListener {

    private Button btnExitAfter, btnExitBefore;
    private TextView txtJoinedTime;
    private String joinedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exit_queue);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Exit Queue Process");

        joinedTime = getIntent().getStringExtra(Constants.JOINED_TIME);

        btnExitAfter = findViewById(R.id.btn_exitAfter);
        btnExitAfter.setOnClickListener(this);

        btnExitBefore = findViewById(R.id.btn_exitBefore);
        btnExitBefore.setOnClickListener(this);

        txtJoinedTime = findViewById(R.id.txt_joinedTime);
        txtJoinedTime.setText("You have joined to the queue at " + joinedTime);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        //If user clicks on the back button
        if(id == android.R.id.home){
            Intent intent = new Intent(ExitQueue.this, JoinQueue.class);
            startActivity(intent);
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_exitAfter:
                Toast.makeText(this, "Clicked Exit After", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_exitBefore:
                Toast.makeText(this, "Clicked Exit Before", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}