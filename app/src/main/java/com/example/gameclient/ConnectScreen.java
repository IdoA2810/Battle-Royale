package com.example.gameclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class ConnectScreen extends AppCompatActivity implements View.OnClickListener {

    ImageButton btn_sign;
    ImageButton btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_screen);
        btn_sign = (ImageButton) findViewById(R.id.button_sign);
        btn_sign.setOnClickListener(this);
        btn_login = (ImageButton) findViewById(R.id.button_login);
        btn_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view==btn_sign)
        {
            Intent intent = new Intent(this,SignScreen.class);
            startActivity(intent);
        }
        else if (view==btn_login)
        {
            Intent intent = new Intent(this,LoginScreen.class);
            startActivity(intent);
        }

    }
}
