package com.example.gameclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainScreen extends AppCompatActivity implements View.OnClickListener {

    ImageButton btn_select;
    ImageButton btn_enter;
    ImageButton btn_instructions;
    boolean enter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        enter = false;
        btn_select = (ImageButton) findViewById(R.id.button_select);
        btn_select.setOnClickListener(this);
        btn_enter = (ImageButton) findViewById(R.id.button_enter);
        btn_enter.setOnClickListener(this);
        btn_instructions = (ImageButton) findViewById(R.id.button_instructions);
        btn_instructions.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        if (view==btn_enter)
        {
            SendThread sendThread = new SendThread();
            sendThread.start();
        }
        else if(view==btn_instructions)
        {
            Intent intent = new Intent(this,InstructionsScreen.class);
            startActivity(intent);
        }
        else if(view==btn_select)
        {
            Intent intent = new Intent(this,SelectScreen.class);
            startActivity(intent);
        }

    }
    class SendThread extends Thread
    {
        @Override
        public void run() {
            SocketHandler.send_with_size("ENTER");
            Intent intent = new Intent(MainScreen.this, WaitScreen.class);
            startActivity(intent);

        }
    }

}
