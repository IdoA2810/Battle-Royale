package com.example.gameclient;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Trace;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class LoginScreen extends AppCompatActivity implements View.OnClickListener {

    ImageButton btn_confirm, btn_return;
    EditText username, password;
    TextView errors;
    boolean confirmed;
    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        confirmed = false;
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        errors = (TextView)findViewById(R.id.errors);
        btn_confirm = (ImageButton)findViewById(R.id.button_confirm);
        btn_confirm.setOnClickListener(this);
        btn_return = (ImageButton)findViewById(R.id.button_return);
        btn_return.setOnClickListener(this);
        SendThread sendThread = new SendThread();
        sendThread.start();
    }

    @Override
    public void onClick(View view) {
        if(view==btn_confirm)
        {
            confirmed = true;
        }
        else if (view==btn_return)
        {
            Intent intent = new Intent(LoginScreen.this, ConnectScreen.class);
            startActivity(intent);
        }
    }

    class SendThread extends Thread
    {
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void run() {
            boolean cont = true;
            while (cont) {
                if (confirmed) {
                    try
                    {
                        String encrypted_password =SHA256.toHexString(SHA256.getSHA(password.getText().toString()));
                        SocketHandler.send_with_size("LOGIN_" + username.getText().toString() + "_" + encrypted_password);
                        try {
                            result = SocketHandler.read();
                            if (result.equals("OK")) {
                                cont = false;
                            }
                            else if (result.split("_")[0].equals("ERROR"))
                            {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        errors.setText(result.split("_")[1]);
                                        errors.setTextColor(Color.RED);
                                    }
                                });
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        confirmed = false;

                    }
                    catch (NoSuchAlgorithmException e)
                    {
                        e.printStackTrace();
                    }

                }
            }
            Intent intent = new Intent(LoginScreen.this, MainScreen.class);
            startActivity(intent);



        }
    }
}
