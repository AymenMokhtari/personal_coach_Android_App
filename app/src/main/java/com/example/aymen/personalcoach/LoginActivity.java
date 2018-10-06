package com.example.aymen.personalcoach;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.aymen.personalcoach.data.DBhelper;
import com.example.aymen.personalcoach.tools.Session;

public class LoginActivity extends AppCompatActivity {
    Button login;
    EditText username ;
    EditText pass;
    Button registerAct ;
    DBhelper helper;
    Session session ;
    SharedPreferences prefs;
    int idSP;

    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        login = (Button)findViewById(R.id.btnLogin);
        username = (EditText)findViewById(R.id.usernameLogin);
        pass = (EditText)findViewById(R.id.passwordLogin);
        // adduser
        login = (Button) findViewById(R.id.btnLogin);
session = new Session(this  );
helper = new DBhelper( this );

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = username.getText().toString();
                final String password = pass.getText().toString();


                helper.loginUser( email, password, new DBhelper.VolleyCallbackUserLogin() {
                    @Override
                    public void onSuccessResponse(int id, String email, String password) {
                        session.setLoggedin(true,email , password , id);
                        Intent intent = new Intent( LoginActivity.this, HomePage.class );
                        startActivity( intent );
                    }

                    @Override
                    public void onFail(String msg) {

                    }
                } );

                Log.d( "fsdf", email+"---"+password );



                //authenticate user

            }
        });
    }




}