package com.example.vanhi.convertmilestokm;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    static String ip = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkWiFi();
        final EditText textBoxIP = (EditText) findViewById(R.id.TextBoxIP);
        textBoxIP.setText(ip);
        Button buttonOK = (Button) findViewById(R.id.ButtonOK);
        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ip = textBoxIP.getText().toString();
                if (!ip.isEmpty()) {
                    String url = "http://" + ip;
                    goToSecondActivity(url);
                }
            }
        });
    }
    public void goToSecondActivity(String url) {
        Intent goToSecond = new Intent();
        goToSecond.setClass(getApplicationContext(), SecondActivity.class);
        goToSecond.putExtra("url", url);
        startActivity(goToSecond);
    }
    @Override
    public void onBackPressed() {
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory( Intent.CATEGORY_HOME );
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }
    public void popToast() {
        Toast toast = Toast.makeText(getApplicationContext(),
                "WiFi is off!", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 100);
        toast.show();
    }
    public void checkWiFi() {
        WifiManager wifi =(WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (!wifi.isWifiEnabled()) popToast();
    }
}

