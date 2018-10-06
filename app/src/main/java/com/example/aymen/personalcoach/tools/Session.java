package com.example.aymen.personalcoach.tools;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.SharedPreferences.Editor;

/**
 * Created by Aymen on 10/24/2017.
 */

    public class Session {
        SharedPreferences prefs;
        Editor editor;
        Context ctx;

        public Session(Context ctx){
            this.ctx = ctx;
            prefs = ctx.getSharedPreferences("myapp", Context.MODE_PRIVATE);
            editor = prefs.edit();
        }

        public void setLoggedin(boolean logggedin, String email , String password , int id){
            editor.putBoolean("loggedInmode",logggedin);
            editor.putInt("id",id );
            editor.putString("email",email);
            editor.putString("password",password);



            editor.commit();
        }

        public boolean loggedin(){
            return prefs.getBoolean("loggedInmode", false);
        }
    }


