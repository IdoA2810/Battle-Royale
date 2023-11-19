package com.example.gameclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class InstructionsScreen extends AppCompatActivity implements View.OnClickListener {

    ImageButton btn_return;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions_screen);
        btn_return = (ImageButton)findViewById(R.id.button_return);
        btn_return.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v==btn_return)
        {
            Intent intent = new Intent(this,MainScreen.class);
            startActivity(intent);
        }

    }
}
