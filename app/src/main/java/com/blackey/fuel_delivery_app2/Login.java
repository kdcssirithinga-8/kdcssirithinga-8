package com.blackey.fuel_delivery_app2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

public class Login extends AppCompatActivity implements SensorEventListener {

    private Sensor rotationSensor;
    private SensorManager sensorManager;

    EditText username,password;
    Button login;
    DBHelper DB;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null) actionBar.hide();

        username = ((TextInputLayout) findViewById(R.id.username1)).getEditText();
        password = ((TextInputLayout) findViewById(R.id.password2)).getEditText();
        login = (Button) findViewById(R.id.btnlogin);

        DB= new DBHelper(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user = username.getText().toString();
                String pass = password.getText().toString();

                if (user.equals("")||pass.equals(""))
                    Toast.makeText(Login.this, "please enter the all the fields",Toast.LENGTH_SHORT).show();
                else {
                    Boolean checkuserpass= DB.checkusernamepassword(user,pass);
                    if (checkuserpass==true){
                        Toast.makeText(Login.this, "sign in successfull", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), navigation.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(Login.this,"Invalid Credentials",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        rotationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
    }

    public void opensignup(View view){
        startActivity(new Intent(this,Signup.class));
    }

    public void openfoget(View view){
        startActivity(new Intent(this,forgotten_password.class));
    }

//    public void button2(View view){
//        startActivity(new Intent(this, navigation.class));
//    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // Get the rotation matrix from the rotation vector
        float[] rotationMatrix = new float[16];
        SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values);

        // Use the rotation matrix to determine the orientation
        float[] orientation = new float[3];
        SensorManager.getOrientation(rotationMatrix, orientation);
        float azimuth = orientation[0];
        float pitch = orientation[1];
        float roll = orientation[2];

        int screenOrientation = getWindowManager().getDefaultDisplay().getRotation();
        if (pitch < -45 && pitch > -135) {
            if (screenOrientation != ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
            }
        } else if (pitch > 45 && pitch < 135) {
            if (screenOrientation != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        } else if (roll > 45) {
            if (screenOrientation != ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
            }
        } else if (roll < -45) {
            if (screenOrientation != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // do nothing
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (rotationSensor != null) {
            sensorManager.registerListener(this, rotationSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}

