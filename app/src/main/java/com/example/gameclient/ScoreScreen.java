package com.example.gameclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.IOException;

public class ScoreScreen extends AppCompatActivity implements View.OnClickListener {

    TextView score;
    ImageButton btn_return;
    String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_screen);
        score = (TextView)findViewById(R.id.score);
        btn_return = (ImageButton)findViewById(R.id.button_return);
        btn_return.setOnClickListener(this);
        ReceiveThread receiveThread = new ReceiveThread();
        receiveThread.start();
    }

    @Override
    public void onClick(View v) {
        if (v==btn_return)
        {
            Intent intent = new Intent(ScoreScreen.this, MainScreen.class);
            startActivity(intent);
        }
    }
    class ReceiveThread extends Thread
    {
        @Override
        public void run() {
            try {
                text = SocketHandler.read();

                if(text.split("_")[0].equals("SCORE"))
                {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run()
                        {
                            score.setText("Your place: " + text.split("_")[1] + "\n" + "Score: " + text.split("_")[2] +"\n" +"Your total score: " + text.split("_")[3] );
                            score.setTextColor(Color.WHITE);
                        }
                    });

                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
