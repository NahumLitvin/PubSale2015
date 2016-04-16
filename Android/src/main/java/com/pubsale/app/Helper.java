package com.pubsale.app;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;
import com.pubsale.dto.IsLoggedInRequestDTO;

import java.io.ByteArrayOutputStream;

/**
 * Created by Nahum on 04/03/2016.
 */
public abstract class Helper {
    public static boolean isOnline(Context context) {
        ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
            Toast.makeText(context, "No Internet connection!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public static IsLoggedInRequestDTO GetIsLoggedInRequest(Activity thisActivity) {
        SharedPreferences prefs = thisActivity.getSharedPreferences("cred", Context.MODE_PRIVATE);
        String email = prefs.getString("email", null);
        String session = prefs.getString("session", null);
        return new IsLoggedInRequestDTO(session, email);

    }


    public static byte[] ByteArrayFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static Bitmap ByteArrayToBitmap(byte[] arr) {
        if (arr == null) return null;
        Bitmap decodedByte = BitmapFactory.decodeByteArray(arr, 0, arr.length);
        return decodedByte;
    }
}
