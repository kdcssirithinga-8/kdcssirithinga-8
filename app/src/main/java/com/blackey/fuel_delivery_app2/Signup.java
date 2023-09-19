
package com.blackey.fuel_delivery_app2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

public class Signup extends AppCompatActivity {

    EditText username, password, confirmpassword;
    Button signup,signin;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        username = ((TextInputLayout)findViewById(R.id.username1)).getEditText();
        password =((TextInputLayout)findViewById(R.id.password1)).getEditText();
        confirmpassword= ((TextInputLayout)findViewById(R.id.passwordconfirm)).getEditText();
        signup=(Button) findViewById(R.id.btnsignup);
        DB = new DBHelper(this);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString().trim();
                String pass = password.getText().toString().trim();
                String confirm= confirmpassword.getText().toString().trim();

                if (user.equals("")||pass.equals("")||confirm.equals(""))
                    Toast.makeText(Signup.this,"Please enter all the fields", Toast.LENGTH_SHORT).show();
                else {
                    if (pass.length() < 6) {
                        Toast.makeText(Signup.this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                    } else if (isValidEmail(user) || isValidPhoneNumber(user)) {
                        if (pass.equals(confirm)) {
                            Boolean checkuser = DB.checkusername(user);
                            if (checkuser == false) {
                                Boolean insert = DB.insertData(user, pass);
                                if (insert == true) {
                                    Toast.makeText(Signup.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), navigation.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(Signup.this, "Registration failed", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(Signup.this, "User already exists, please sign in", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(Signup.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(Signup.this, "Invalid email or phone number", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidPhoneNumber(String phone) {
        return Patterns.PHONE.matcher(phone).matches() && phone.length() == 10;
    }

    //public void openLOgin(View view){
    //    startActivity(new Intent(this,Navigation.class));
    //}
}

