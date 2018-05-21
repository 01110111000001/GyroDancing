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
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;


public class MenuActivity extends AppCompatActivity {

    Button buttonSet;
    Button buttonSelect;

    TextView space;

    Integer Port;
    String Addr;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_menu);

        buttonSelect=(Button) findViewById(R.id.select);
        buttonSet=(Button) findViewById(R.id.settings);
        space=(TextView) findViewById(R.id.space1);

        buttonSet.setOnClickListener(buttonSetOnClickListener);
        buttonSelect.setOnClickListener(buttonSelectOnClickListener);

        buttonSelect.setEnabled(false);

        //try {
        Addr = (String) getIntent().getSerializableExtra("Addr");
        Port = (Integer) getIntent().getSerializableExtra("Port");
        /*}finally {
            Addr="no";
        }*/
        if (Addr != null){
            buttonSelect.setEnabled(true);
        }

        //space.setText(Addr + " " + Port);

    }

    View.OnClickListener buttonSetOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent homeIntent = new Intent(MenuActivity.this, SettingsActivity.class);
            if(Addr != null) {
                homeIntent.putExtra("addr", Addr);
                homeIntent.putExtra("port", Port);
            }
            startActivity(homeIntent);
            finish();
        }
    };

    View.OnClickListener buttonSelectOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent selectIntent = new Intent(MenuActivity.this, MainActivity.class);
            selectIntent.putExtra("addr",Addr);
            selectIntent.putExtra("port",Port);
            startActivity(selectIntent);
        }
    };
}
