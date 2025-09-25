package com.napid.bobdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class ConvertToImage {

    // Method to convert Base64 string to Bitmap
    public Bitmap decodeBase64ToImage(String base64String) {
        // Decode Base64 string into byte array
        byte[] decodedBytes = Base64.decode(base64String, Base64.DEFAULT);

        // Convert byte array into Bitmap
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    public Bitmap setImageFromBase64(String base64String) {
        Bitmap bitmap = decodeBase64ToImage(base64String);
        return bitmap;
    }
}
