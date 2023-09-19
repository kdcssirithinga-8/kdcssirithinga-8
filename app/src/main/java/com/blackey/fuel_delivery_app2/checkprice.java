package com.blackey.fuel_delivery_app2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class checkprice extends AppCompatActivity {

    Button checkPriceButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkprice);

        // get a reference to the check price button
        checkPriceButton = findViewById(R.id.check_price_button);

        // set a click listener on the button
        checkPriceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create an intent to open a web page
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://ceypetco.gov.lk/marketing-sales/"));

                // start the activity to open the web page
                startActivity(intent);
            }
        });
    }
}
