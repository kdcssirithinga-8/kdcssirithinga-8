package com.blackey.fuel_delivery_app2;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

public class CardPayment extends AppCompatActivity {

    private EditText dateYearEditText, securityCodeEditText;
    private TextInputLayout dateYearTextInputLayout, securityCodeTextInputLayout;
    private TextView paymentAmountTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_payment);

        // Retrieve the payment amount from the Intent
        double paymentAmount = getIntent().getDoubleExtra("paymentAmount", 0.0);

        paymentAmountTextView = findViewById(R.id.textView10);
        Button payButton = findViewById(R.id.btnpay);

        // Set the payment amount in the payment amount TextView with "RS." prefix
        String paymentAmountText = "RS. " + String.valueOf(paymentAmount);
        paymentAmountTextView.setText(paymentAmountText);

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform payment processing

                // Assuming payment is successful
                String orderId = "12345"; // Replace with actual order ID

                // Save the order details in the database
                saveOrderDetails(orderId, paymentAmount);

                // Show payment success message
                Toast.makeText(CardPayment.this, "Payment Success! Order Placed Successfully.", Toast.LENGTH_SHORT).show();

                // Finish the activity and return to the previous screen
                finish();
            }
        });

        dateYearEditText = findViewById(R.id.dateYearEditText);
        securityCodeEditText = findViewById(R.id.securityCodeEditText);

        dateYearTextInputLayout = findViewById(R.id.dateYearTextInputLayout);
        securityCodeTextInputLayout = findViewById(R.id.securityCodeTextInputLayout);

        dateYearEditText.addTextChangedListener(new DateYearInputTextWatcher());
        securityCodeEditText.addTextChangedListener(new SecurityCodeInputTextWatcher());
    }

    private void saveOrderDetails(String orderId, double paymentAmount) {
        // Code to save order details in the database goes here
        // Replace with your implementation
    }

    private class DateYearInputTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // No implementation needed
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // No implementation needed
        }

        @Override
        public void afterTextChanged(Editable s) {
            validateDateYearInput(s.toString());
        }


        private void validateDateYearInput(String input) {
            if (input.isEmpty() || input.length() != 6) {
                dateYearTextInputLayout.setError("Invalid input");
            } else {
                String month = input.substring(0, 2);
                String year = input.substring(3, 6);

                // Perform your validation logic here for month and year inputs

                int monthValue = Integer.parseInt(month);
                int yearValue = Integer.parseInt(year);

                if (monthValue < 1 || monthValue > 12) {
                    dateYearTextInputLayout.setError("Invalid month");
                } else if (year.length() != 4) {
                    dateYearTextInputLayout.setError("Invalid year");
                } else {
                    dateYearTextInputLayout.setError(null);
                }
            }
        }
    }

    private class SecurityCodeInputTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // No implementation needed
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // No implementation needed
        }

        @Override
        public void afterTextChanged(Editable s) {
            validateSecurityCodeInput(s.toString());
        }

        private void validateSecurityCodeInput(String input) {
            if (input.isEmpty() || input.length() != 3) {
                securityCodeTextInputLayout.setError("Invalid input");
            } else {
                // Perform your validation logic here for the security code input
                securityCodeTextInputLayout.setError(null);
            }
        }
    }
}
