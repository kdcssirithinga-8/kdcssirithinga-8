package com.blackey.fuel_delivery_app2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class paymentMethod extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);

        Button cardPaymentButton = findViewById(R.id.button);

        cardPaymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the CardPayment activity and pass the order price
                Intent intent = new Intent(paymentMethod.this, CardPayment.class);
                intent.putExtra("paymentAmount", 2200.0); // Replace 50.0 with the actual order price
                startActivity(intent);
            }
        });
    }
}
