package com.example.gameclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.IOException;

public class SelectScreen extends AppCompatActivity implements View.OnClickListener {

    ImageButton btn_left, btn_right, btn_confirm, btn_return;
    ImageView image;
    String characters;
    int place = 0;
    int pic = 0;
    String current_character;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_screen);
        image = (ImageView)findViewById(R.id.image);
        btn_left = (ImageButton)findViewById(R.id.button_left);
        btn_left.setOnClickListener(this);
        btn_right = (ImageButton)findViewById(R.id.button_right);
        btn_right.setOnClickListener(this);
        btn_confirm = (ImageButton)findViewById(R.id.button_confirm);
        btn_confirm.setOnClickListener(this);
        btn_return = (ImageButton)findViewById(R.id.button_return);
        btn_return.setOnClickListener(this);
        ReceiveThread receiveThread = new ReceiveThread();
        receiveThread.start();

    }

    @Override
    public void onClick(View v)
    {
        if (v==btn_right)
        {
            if (place<characters.split("_").length - 1 )
                place += 1;
            else
                place = 0;
            current_character = characters.split("_")[place];
            if (current_character.equals("KID"))
                pic = R.drawable.kid;
            else if (current_character.equals("BLUE"))
                pic = R.drawable.blue;
            else if (current_character.equals("SKELETON"))
                pic = R.drawable.skeleton;
            image.setImageResource(pic);
        }
        else if (v==btn_left)
        {
            if (place>0 )
                place -= 1;
            else
                place = characters.split("_").length - 1;
            current_character = characters.split("_")[place];
            if (current_character.equals("KID"))
                pic = R.drawable.kid;
            else if (current_character.equals("BLUE"))
                pic = R.drawable.blue;
            else if (current_character.equals("SKELETON"))
                pic = R.drawable.skeleton;
            image.setImageResource(pic);
        }
        else if(v==btn_return)
        {
            Intent intent = new Intent(this,MainScreen.class);
            startActivity(intent);
        }
        else if(v==btn_confirm)
        {
            SendThread sendThread = new SendThread();
            sendThread.start();
        }

    }

    class ReceiveThread extends Thread
    {
        @Override
        public void run() {
            try {
                SocketHandler.send_with_size("SELECT");
                characters = SocketHandler.read();
                current_character = characters.split("_")[place];
                if (current_character.equals("KID"))
                    pic = R.drawable.kid;
                else if (current_character.equals("BLUE"))
                    pic = R.drawable.blue;
                else if (current_character.equals("SKELETON"))
                    pic = R.drawable.skeleton;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        image.setImageResource(pic);
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    class SendThread extends Thread
    {
        @Override
        public void run()
        {
            SocketHandler.send_with_size("CHOOSE" + "_" + current_character);
            Intent intent = new Intent(SelectScreen.this,MainScreen.class);
            startActivity(intent);
        }
    }
}
