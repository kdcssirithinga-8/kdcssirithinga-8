package com.blackey.fuel_delivery_app2;

import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class Diesel extends Fragment implements AdapterView.OnItemSelectedListener {

    private Spinner quantitySpinner;
    private Spinner fuelTypeSpinner;

    private TextView dateTextView;
    private Button dateButton;

    private TextView mPriceTextView;
    private Spinner mQuantitySpinner;
    private Spinner mFuelTypeSpinner;

    private double mFuelTypePrice;

    private DatabaseReference databaseReference;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_diesel, container, false);

        // Initialize Firebase
        FirebaseApp.initializeApp(getContext());
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("orders");

        // Find the spinners in the layout
        quantitySpinner = view.findViewById(R.id.quantity_spinner);
        fuelTypeSpinner = view.findViewById(R.id.fuel_type_spinner);

        dateTextView = view.findViewById(R.id.date_text_view);
        dateButton = view.findViewById(R.id.date_button);

        // Get references to views
        mPriceTextView = view.findViewById(R.id.price_text_view);
        mQuantitySpinner = view.findViewById(R.id.quantity_spinner);
        mFuelTypeSpinner = view.findViewById(R.id.fuel_type_spinner);

        // Set up listeners
        mQuantitySpinner.setOnItemSelectedListener(this);
        mFuelTypeSpinner.setOnItemSelectedListener(this);

        // Set initial price
        updatePrice();

        Button orderButton = view.findViewById(R.id.button8);
        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFields()) {
                    saveOrder();
                    showNotification();

                    Intent intent = new Intent(getActivity(), paymentMethod.class);
                    startActivity(intent);
                }
            }
        });



        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a new instance of DatePickerDialog and show it
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                // Set the selected date to the TextView
                                dateTextView.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                            }
                        },
                        // Set the initial date to the current date
                        Calendar.getInstance().get(Calendar.YEAR),
                        Calendar.getInstance().get(Calendar.MONTH),
                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> quantityAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.quantity_array, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> fuelTypeAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.fuel_type_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        quantityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fuelTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinners
        quantitySpinner.setAdapter(quantityAdapter);
        fuelTypeSpinner.setAdapter(fuelTypeAdapter);

        return view;
    }

    private void updatePrice() {
        // Get selected quantity
        int quantity = common.getQuantity(mQuantitySpinner.getSelectedItem().toString());

        // Get selected fuel type
        String fuelType = mFuelTypeSpinner.getSelectedItem().toString();

        // Set fuel type price based on selection
        if (fuelType.equals("LankaAutoDiesel")) {
            mFuelTypePrice = common.plad;
        } else if (fuelType.equals("LankaSuperDiesel4StarEuro4\n")) {
            mFuelTypePrice = common.plsd4se4;
        }

        // Calculate total price
        double totalPrice = mFuelTypePrice * quantity;

        // Update price TextView
        mPriceTextView.setText("Price: RS." + totalPrice);
    }

    private boolean validateFields() {
        String fuelType = mFuelTypeSpinner.getSelectedItem().toString();
        String quantity = mQuantitySpinner.getSelectedItem().toString();
        String date = dateTextView.getText().toString();

        if (TextUtils.isEmpty(fuelType)) {
            Toast.makeText(getContext(), "Please select a fuel type", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(quantity)) {
            Toast.makeText(getContext(), "Please select a quantity", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(date)) {
            Toast.makeText(getContext(), "Please select a date", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void saveOrder() {
        // Get selected values
        String fuelType = mFuelTypeSpinner.getSelectedItem().toString();
        int quantity = common.getQuantity(mQuantitySpinner.getSelectedItem().toString());
        double price = mFuelTypePrice * quantity;
        String date = dateTextView.getText().toString();
        String phoneNumber = ((EditText) requireView().findViewById(R.id.editTextPhone4)).getText().toString();
        String address = ((EditText) requireView().findViewById(R.id.editTextTextPostalAddress2)).getText().toString();

        // Generate order ID (You can use your own logic to generate the order ID)
        String orderId = "Order " + System.currentTimeMillis();

        // Save order to Firebase database
        Order order = new Order(fuelType, quantity, price, date,phoneNumber,address,orderId);
        databaseReference.child(orderId).setValue(order).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    // Order saved successfully
                    Toast.makeText(getContext(), "Order placed successfully", Toast.LENGTH_SHORT).show();
                    showOrderDetails(order);
                } else {
                    // Failed to save order
                    Toast.makeText(getContext(), "Failed to place order", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Pass payment amount as extra value in intent
        Intent intent = new Intent(getActivity(), CardPayment.class);
        intent.putExtra("paymentAmount", price);
        startActivity(intent);
    }

    private void showOrderDetails(Order order) {
        StringBuilder orderDetails = new StringBuilder();
        orderDetails.append("Order ID: ").append(order.getOrderId()).append("\n");
        orderDetails.append("Fuel Type: ").append(order.getFuelType()).append("\n");
        orderDetails.append("Quantity: ").append(order.getQuantity()).append("\n");
        orderDetails.append("Price: RS.").append(order.getPrice()).append("\n");
        orderDetails.append("Date: ").append(order.getDate()).append("\n");
        orderDetails.append("Address: ").append(order.getAddress()).append("\n");
        orderDetails.append("Phone Number: ").append(order.getPhoneNumber()).append("\n");

        // Display order details to the user
        Toast.makeText(getContext(), orderDetails.toString(), Toast.LENGTH_LONG).show();
    }

    private void showNotification() {
        // Create a notification channel for Android Oreo and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "FuelDeliveryChannel";
            String channelName = "Fuel Delivery";
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Fuel delivery notifications");
            channel.enableLights(true);
            channel.setLightColor(Color.BLUE);
            NotificationManager notificationManager = requireContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        // Create a notification
        Notification.Builder builder = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            builder = new Notification.Builder(getContext(), "FuelDeliveryChannel")
                    .setSmallIcon(R.drawable.notification_icon)
                    .setContentTitle("Order Placed")
                    .setContentText("Your order has been placed successfully!")
                    .setPriority(Notification.PRIORITY_DEFAULT);
        }

        // Show the notification
        NotificationManager notificationManager = (NotificationManager) requireContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        updatePrice();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // Do nothing
    }
    public void Home(View view) {
        startActivity(new Intent(requireActivity(),navigation.class));
    }
}
