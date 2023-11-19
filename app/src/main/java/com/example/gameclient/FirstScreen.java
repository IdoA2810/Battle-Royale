package com.example.gameclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class FirstScreen extends AppCompatActivity implements View.OnClickListener {

    ImageButton btn_play;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_screen);
        btn_play = (ImageButton) findViewById(R.id.button_play);
        btn_play.setOnClickListener(this);
        ClientThread connectThread = new ClientThread();
        connectThread.start();


    }

    @Override
    public void onClick(View view) {
        if(view==btn_play)
        {
            Intent intent = new Intent(this,ConnectScreen.class);
            startActivity(intent);
        }

    }

    class ClientThread extends Thread {

        @Override
        public void run()
        {
            try
            {
                SocketHandler.setSocket(new Socket("192.168.14.128", 12345));
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            SocketHandler.send_with_size("Shalom");
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int height = displayMetrics.heightPixels;
            int width = displayMetrics.widthPixels;
            SocketHandler.send_with_size("SIZE_" + Integer.toString(width) + "_" + Integer.toString(height));
        }
    }
}
