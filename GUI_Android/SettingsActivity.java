package com.example.pierr.testudp;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class SettingsActivity extends AppCompatActivity {

    EditText editTextAddress, editTextPort;
    Button buttonOK;

    String Addr;
    Integer Port;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_settings);


        editTextAddress = (EditText) findViewById(R.id.address);
        editTextPort = (EditText) findViewById(R.id.port);
        buttonOK=(Button)findViewById(R.id.OK);

        buttonOK.setOnClickListener(buttonOKOnClickListener);

        Addr = (String) getIntent().getSerializableExtra("addr");
        Port = (Integer) getIntent().getSerializableExtra("port");

        if(Addr != null){
            editTextPort.setText(Port.toString(), TextView.BufferType.EDITABLE);
            editTextAddress.setText(Addr, TextView.BufferType.EDITABLE);
        }

    }

    View.OnClickListener buttonOKOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Addr = editTextAddress.getText().toString();
            Port= Integer.parseInt(editTextPort.getText().toString());
            Intent intent = new Intent(SettingsActivity.this,MenuActivity.class);
            intent.putExtra("Addr",Addr);
            intent.putExtra("Port",Port);
            startActivity(intent);
        }
    };

}
