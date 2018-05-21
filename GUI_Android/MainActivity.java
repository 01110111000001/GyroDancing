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

import org.w3c.dom.Text;


public class MainActivity extends AppCompatActivity implements SensorEventListener {

    Button buttonConnect;
    Button buttonStop;
    TextView textViewState;

    UdpClientHandler udpClientHandler;
    UdpClientThread udpClientThread;

    SensorManager sensorManager;
    Sensor accelerometer;

    Integer Port;
    String Addr;
    boolean Stop_Start = true;

    double x, y, z;

    int puiss;
    int dir;

    //private static int SPLASH_TIME_OUT = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        buttonConnect = (Button) findViewById(R.id.connect);
        buttonStop = (Button) findViewById(R.id.stop);
        textViewState = (TextView)findViewById(R.id.state);

        buttonConnect.setOnClickListener(buttonConnectOnClickListener);
        buttonStop.setOnClickListener(buttonStopOnClickListener);

        udpClientHandler = new UdpClientHandler(this);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        Addr = (String) getIntent().getSerializableExtra("addr");
        Port = (Integer) getIntent().getSerializableExtra("port");

    }

    View.OnClickListener buttonConnectOnClickListener = new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    Stop_Start=true;

                    udpClientThread = new UdpClientThread(
                            //editTextAddress.getText().toString(),
                            Addr,
                            //Integer.parseInt(editTextPort.getText().toString()),
                            Port,
                            udpClientHandler,
                            puiss,
                            dir);
                    udpClientThread.start();

                    //buttonConnect.setEnabled(false);
                }
            };

    View.OnClickListener buttonStopOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View arg0) {
            Stop_Start=false;
        }
    };
    private void updateState(String state){
        textViewState.setText(state);
    }


    private void clientEnd(){
        if(Stop_Start) {
            udpClientThread = new UdpClientThread(
                    //editTextAddress.getText().toString(),
                    Addr,
                    //Integer.parseInt(editTextPort.getText().toString()),
                    Port,
                    udpClientHandler,
                    puiss,
                    dir);
            udpClientThread.start();
        }
        else {
            udpClientThread = null;
            textViewState.setText("Déconnecté");
        }
    }

    public static class UdpClientHandler extends Handler {
        public static final int UPDATE_STATE = 0;
        public static final int UPDATE_MSG = 1;
        public static final int UPDATE_END = 2;
        private MainActivity parent;

        public UdpClientHandler(MainActivity parent) {
            super();
            this.parent = parent;
        }

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){
                case UPDATE_STATE:
                    parent.updateState((String)msg.obj);
                    break;
                case UPDATE_END:;
                    parent.clientEnd();
                    break;
                default:
                    super.handleMessage(msg);
            }

        }
    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener((SensorEventListener) this, accelerometer);
        super.onPause();
    }

    @Override
    protected void onResume() {
        /* Ce qu'en dit Google&#160;dans le cas de l'accéléromètre :
         * «&#160; Ce n'est pas nécessaire d'avoir les évènements des capteurs à un rythme trop rapide.
         * En utilisant un rythme moins rapide (SENSOR_DELAY_UI), nous obtenons un filtre
         * automatique de bas-niveau qui "extrait" la gravité  de l'accélération.
         * Un autre bénéfice étant que l'on utilise moins d'énergie et de CPU.&#160;»
         */
        sensorManager.registerListener((SensorEventListener) this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        super.onResume();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override // c'est ici que je récupère les données
    public void onSensorChanged(SensorEvent event) {
        // Récupérer les valeurs du capteur
        double angleY, angleX, angleZ;
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            x = event.values[0];
            y = event.values[1];
            z = event.values[2];
            angleX=Math.asin(x/Math.sqrt(x*x+y*y+z*z));
            angleX=Math.toDegrees(angleX);
            angleY=Math.asin(y/Math.sqrt(x*x+y*y+z*z));
            angleY=Math.toDegrees(angleY);
            angleZ=Math.asin(z/Math.sqrt(x*x+y*y+z*z));
            angleZ=Math.toDegrees(angleZ);
            //puissance de -100->100
            if(angleY>40&&angleY<50)
                puiss=0;
            else{
                if(angleY<40){
                    puiss=100-(int)((100*angleY)/40);
                }
                else
                    puiss=-(int)((100*(angleY-50))/40);
            }
            if(z<0)
                puiss=-100;
            if(y<0)
                puiss=100;
            //direction de -100->100
            else{
                if(angleX>5){
                    dir=(int)((100*angleX-500)/60);
                }
                else
                    dir=(int)((100*angleX+500)/60);
            }
            if(angleX>-5&&angleX<5)
                dir=0;
            if(dir>100)
                dir=100;
            if(dir<-100)
                dir=-100;
        }
    }
}

