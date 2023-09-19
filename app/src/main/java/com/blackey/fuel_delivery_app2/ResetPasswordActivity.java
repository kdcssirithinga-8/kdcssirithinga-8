// ResetPasswordActivity.java
package com.blackey.fuel_delivery_app2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText resetCode;
    private EditText newPassword;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        resetCode = findViewById(R.id.reset_code);
        newPassword = findViewById(R.id.new_password);

        Intent intent = getIntent();
        if (intent != null) {
            String userInput = intent.getStringExtra("userInput");
            // Here, you can use the userInput to display or pre-fill any necessary fields
        }
    }

    public void resetPassword(View view) {
        String code = resetCode.getText().toString().trim();
        String password = newPassword.getText().toString().trim();

        // Validate the reset code and new password
        if (code.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter the reset code and new password", Toast.LENGTH_SHORT).show();
        } else {
            // Here, you can implement the logic to validate the reset code and update the user's password
            // You can use your existing DBHelper methods or any other authentication mechanism

            // For demonstration purposes, let's assume the password reset is successful
            Toast.makeText(this, "Password reset successful", Toast.LENGTH_SHORT).show();

            // Redirect the user to the login screen
            Intent intent = new Intent(this, Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
    }
}
