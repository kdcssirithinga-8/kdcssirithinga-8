package com.blackey.fuel_delivery_app2;

public class common {

    public static float ppo92 = 92.0f;
    public static float ppo95 = 95.0f;
    public static float pseo = 0.0f;
    public static float psbo = 0.0f;
    public static float pceo = 0.0f;
    public static float plad = 0.0f;
    public static float plsd4se4 = 0.0f;



    public static int getQuantity(String liter) {
        String numericString = liter.replaceAll("\\D+", "");
        return Integer.parseInt(numericString);
    }
}
