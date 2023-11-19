package com.example.gameclient;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    Game game;
    ImageButton up;
    ImageButton down;
    ImageButton left;
    ImageButton right;
    ImageButton shoot;
    ImageButton ult;
    boolean up_held;
    boolean down_held;
    boolean right_held;
    boolean left_held;
    boolean released;
    boolean shoot_held;
    boolean ult_held;
    RelativeLayout back;
    String line;
    String background;

    //  Socket socket;
   // OutputStream output;
   // InputStream input;
  //  boolean connected;
  //  PrintWriter writer;
   // BufferedReader reader;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //connected = false;
        setContentView(R.layout.activity_main);
        back=(RelativeLayout) findViewById(R.id.background);
       /* DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        System.out.println("Height: " + height + " Width: " + width);*/
        game = new Game(this);
        FrameLayout frameLayout = (FrameLayout)findViewById(R.id.mainframe);
        frameLayout.addView(game);
        up = (ImageButton)findViewById(R.id.button_up);
        down = (ImageButton)findViewById(R.id.button_down);
        right = (ImageButton)findViewById(R.id.button_right);
        left = (ImageButton)findViewById(R.id.button_left);
        shoot = (ImageButton)findViewById(R.id.button_shoot);
        ult = (ImageButton)findViewById(R.id.button_ult);
        /*up.setOnClickListener(this);
        right.setOnClickListener(this);
        down.setOnClickListener(this);
        left.setOnClickListener(this);
        shoot.setOnClickListener(this);*/

        up.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Do something
                        up_held = true;
                        return true;
                    case MotionEvent.ACTION_UP:
                        // No longer down
                        released = true;
                        return true;
                }
                return false;
            }});
        down.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Do something
                        down_held = true;
                        return true;
                    case MotionEvent.ACTION_UP:
                        // No longer down
                        released = true;
                        return true;
                }
                return false;
            }});
        right.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Do something
                        right_held = true;
                        return true;
                    case MotionEvent.ACTION_UP:
                        // No longer down
                        released = true;
                        return true;
                }
                return false;
            }});
        left.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Do something
                        left_held = true;
                        return true;
                    case MotionEvent.ACTION_UP:
                        // No longer down
                        released = true;
                        return true;
                }
                return false;
            }});
        shoot.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Do something
                        shoot_held = true;
                        return true;
                    case MotionEvent.ACTION_UP:
                        // No longer down
                        shoot_held = false;
                        return true;
                }
                return false;
            }});
        ult.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Do something
                        ult_held = true;
                        return true;
                    case MotionEvent.ACTION_UP:
                        // No longer down
                        ult_held = false;
                        return true;
                }
                return false;
            }});

        ReceiveThread receiveThread = new ReceiveThread();
        receiveThread.start();




    }

   /* @Override
    public void onClick(View view)
    {
         String data = "";
        if (connected)
        {
            if (view == shoot)
                send_with_size("SHOOT");
            else if(view == up)
                send_with_size("UP");
            else if(view == down)
                send_with_size("DOWN");
            else if(view == right)
                send_with_size("RIGHT");
            else if(view == left)
                send_with_size("LEFT");
        }

    }*/
    class CommandThread extends Thread
    {
        @Override
        public void run() {
            super.run();
            while (true)
            {

                if (right_held)
                {
                    SocketHandler.send_with_size("RIGHT");
                    right_held = false;
                }
                else if (left_held)
                {
                    SocketHandler.send_with_size("LEFT");
                    left_held =false;
                }
                else if (down_held)
                {
                    SocketHandler.send_with_size("DOWN");
                    down_held = false;
                }
                else if (up_held)
                {
                    SocketHandler.send_with_size("UP");
                    up_held = false;
                }

                else if (released)
                {
                    SocketHandler.send_with_size("RELEASED");
                    released = false;
                }
                else if (shoot_held)
                {
                    SocketHandler.send_with_size("SHOOT");
                    shoot_held = false;
                }
                else if (ult_held)
                {
                    SocketHandler.send_with_size("ULT");
                    ult_held = false;
                }

                try {
                    Thread.sleep(25);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    class ReceiveThread extends Thread {

        @Override
        public void run() {

            try {
                //socket = SocketHandler.getSocket();
                //output = socket.getOutputStream();
                //input  = socket.getInputStream();
                //writer = new PrintWriter(output, true);
                //reader = new BufferedReader(new InputStreamReader(input));
                //connected = true;
                CommandThread commandThread = new CommandThread();
                commandThread.start();
                boolean cont = true;
                while (cont)
                {
                    line = SocketHandler.read();

                    if (line.equals("END"))
                        cont = false;
                    else if (line.split("_")[0].equals("FACTORS"))
                    {
                        game.SetFactors(Double.parseDouble(line.split("_")[1]), Double.parseDouble(line.split("_")[2]));
                    }
                    else if (line.split("_")[0].equals("CAC"))
                    {
                        game.SetCharacter(line);
                    }
                    else if (line.split("_")[0].equals("SHOT"))
                    {
                        game.SetShot(line);
                    }
                    else if (line.split(("_"))[0].equals("DEL"))
                    {
                        game.RemoveSprite(line.split(("_"))[1]);
                    }
                    else if (line.split("_")[0].equals("BACK"))
                    {
                        background = line.split("_")[1];
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run()
                            {
                                ChangeBackground(background);
                            }
                        });
                    }
                    else if (line.split("_")[0].equals("AMOUNT"))
                    {
                        game.amount = line.split("_")[1];
                    }
                    else if (line.split("_")[0].equals("POINTS"))
                    {
                        game.score = line.split("_")[1];
                    }

                }
                game.cont = false;
                Intent intent = new Intent(MainActivity.this, ScoreScreen.class);
                startActivity(intent);

            }
            catch (IOException e) {
                e.printStackTrace();
            }



        }

        public void ChangeBackground(String place)
        {
            System.out.println(place);
            if (place.equals("1,1"))
                back.setBackgroundResource(R.drawable.background_1);
            else if (place.equals("1,2"))
                back.setBackgroundResource(R.drawable.background_2);
            else if (place.equals("1,3"))
                back.setBackgroundResource(R.drawable.background_3);
            else if (place.equals("2,1"))
                back.setBackgroundResource(R.drawable.background_4);
            else if (place.equals("2,2"))
                back.setBackgroundResource(R.drawable.background_5);
            else if (place.equals("2,3"))
                back.setBackgroundResource(R.drawable.background_6);
            else if (place.equals("3,1"))
                back.setBackgroundResource(R.drawable.background_7);
            else if (place.equals("3,2"))
                back.setBackgroundResource(R.drawable.background_8);
            else if (place.equals("3,3"))
                back.setBackgroundResource(R.drawable.background_9);
            System.out.println("Changed");
        }

    }

   /* public void send_with_size(String data)
    {
        data = Integer.toString(data.length()) + "_" + data;
        while(data.split("_")[0].length()<6)
            data = "0" + data;
        writer.print(data);
        writer.flush();


    } */

    /*public String recv_by_size()
    {
        String SIZE_HEADER_FORMAT = "000000_";
        int size_header_size = SIZE_HEADER_FORMAT.length();
        String str_size = "";
        int data_len = 0;
        while(str_size.length()<size_header_size)
        {
            str_size += reader.readLine();
        }
    }*/
}