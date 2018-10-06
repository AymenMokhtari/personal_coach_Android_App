package com.example.aymen.personalcoach.tools;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Aymen on 10/26/2017.
 */

public class Message {
    public static void message(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
