package com.blackey.fuel_delivery_app2;

public class Order {
    private String fuelType;
    private int quantity;
    private double price;
    private String date;
    private String phoneNumber;
    private String address;
    private String orderId;

    public Order(String id, String fuelType, int quantity, String date, double totalPrice) {
        // Default constructor required for Firebase
    }

    public Order(String fuelType, int quantity, double price, String date, String address, String phoneNumber, String orderId) {
        this.fuelType = fuelType;
        this.quantity = quantity;
        this.price = price;
        this.date = date;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.orderId = orderId;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
