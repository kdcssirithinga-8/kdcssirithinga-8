package com.blackey.fuel_delivery_app2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.twilio.Twilio;
import com.twilio.exception.TwilioException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class forgotten_password extends AppCompatActivity {
    // Twilio credentials
    private static final String TWILIO_ACCOUNT_SID = "YOUR_TWILIO_ACCOUNT_SID";
    private static final String TWILIO_AUTH_TOKEN = "YOUR_TWILIO_AUTH_TOKEN";
    private static final String TWILIO_PHONE_NUMBER = "YOUR_TWILIO_PHONE_NUMBER";

    private EditText emailOrMobile;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotten_password);

        emailOrMobile = findViewById(R.id.reset_code);
    }

    public void sendCode(View view) {
        String userInput = emailOrMobile.getText().toString().trim();

        // Validate the user input (e.g., check for empty fields, valid email, or mobile number format)
        if (userInput.isEmpty()) {
            Toast.makeText(this, "Please enter your email or mobile number", Toast.LENGTH_SHORT).show();
        } else {
            // Here, you can implement the logic to send the reset code to the user's email or mobile number
            // You can use email APIs or SMS gateways to send the code

            // For demonstration purposes, let's assume the code is sent successfully
            String resetCode = "123456"; // Example reset code

            // Send the code via SMS
            sendSMS(userInput, resetCode);

            // Display a success message
            Toast.makeText(this, "Reset code sent to your mobile number", Toast.LENGTH_SHORT).show();

            // Start the activity to enter the reset code
            Intent intent = new Intent(this, ResetPasswordActivity.class);
            intent.putExtra("userInput", userInput);
            startActivity(intent);
        }
    }

    private void sendSMS(String phoneNumber, String message) {
        Twilio.init(TWILIO_ACCOUNT_SID, TWILIO_AUTH_TOKEN);

        try {
            Message.creator(
                    new PhoneNumber(TWILIO_PHONE_NUMBER),
                    new PhoneNumber(phoneNumber),
                    message
            ).create();
        } catch (TwilioException e) {
            // Handle any exceptions that occur during message sending
            Toast.makeText(this, "Error sending SMS: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void openLogin(View view) {
        startActivity(new Intent(this, Login.class));
    }
}
