package com.example.gameclient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.IOException;

public class WaitScreen extends AppCompatActivity implements View.OnClickListener {

    ImageButton btn_return;
    TextView amount;
    boolean cont;
    boolean returned;
    String text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_screen);
        cont = true;
        returned = false;
        amount = (TextView)findViewById(R.id.amount);
        btn_return = (ImageButton)findViewById(R.id.button_return);
        btn_return.setOnClickListener(this);
        ReceiveThread receiveThread = new ReceiveThread();
        receiveThread.start();
    }

    @Override
    public void onClick(View v)
    {
        if (v==btn_return)
        {
            returned = true;

        }
    }

    class ReceiveThread extends Thread
    {
        @Override
        public void run() {
            while (cont)
            {
                try {
                    text = SocketHandler.read();
                    if (text.equals("STARTED"))
                        cont = false;
                    else if(text.split("_")[0].equals("WAIT"))
                    {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                amount.setText("Waiting for players: " + text.split("_")[2] + "\n" + "Starting in: " + text.split("_")[1] +" seconds");
                                amount.setTextColor(Color.WHITE);
                            }
                        });

                    }
                    if (returned)
                    {
                        SocketHandler.send_with_size("RETURN");
                        Intent intent = new Intent(WaitScreen.this, MainScreen.class);
                        startActivity(intent);
                        cont = false;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            if (!returned)
            {
                Intent intent = new Intent(WaitScreen.this, MainActivity.class);
                startActivity(intent);
            }
        }
    }

}
