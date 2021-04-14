package com.example.vanhi.convertmilestokm;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.lang.*;

public class SecondActivity extends AppCompatActivity {
    boolean isConnected;
    AsyncTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Intent basket = getIntent();
        String url = basket.getStringExtra("url");
        isConnected = true;
        task = new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... params) {
                String result = "";
                while (isConnected)
                    try {
                        URL myUrl = new URL(params[0]);
                        HttpURLConnection connection = (HttpURLConnection) myUrl.openConnection();
                        connection.setRequestMethod("GET");
                        connection.connect();
                        InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
                        BufferedReader reader = new BufferedReader(streamReader);
                        StringBuilder stringBuilder = new StringBuilder();
                        String inputLine;
                        while ((inputLine = reader.readLine()) != null) {
                            stringBuilder.append(inputLine);
                        }
                        reader.close();
                        streamReader.close();
                        result = stringBuilder.toString();
                        publishProgress(result);
                        if (isCancelled()) break;
                        Thread.sleep(500);
                    } catch (Exception e) {
                        isConnected = false;
                    }
                return result;
            }

            @Override
            protected void onProgressUpdate(String... progress) {
                processValue(progress[0]);
            }

            @Override
            protected void onPostExecute(String result) {
                goToMainActivity();
            }
        }.execute(url);
    }

    @Override
    public void onStop() {
        super.onStop();
        task.cancel(true);
        popToast();
    }

    void processValue(String result) {
        if (result.equals("0")) setOffMode();
        else if (result.equals("1")) setSafeMode();
        else if (result.equals("2")) setGasMode();
        else if (result.equals("3")) setFireMode();
        else goToMainActivity();
    }

    public void goToMainActivity() {
        Intent goToMain = new Intent();
        goToMain.setClass(getApplicationContext(), MainActivity.class);
        startActivity(goToMain);
    }

    public void setOffMode() {
        View view = this.getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        view.setSystemUiVisibility(uiOptions);
        view.setBackgroundColor(Color.parseColor("#000000"));
        TextView tv = (TextView) findViewById(R.id.ModeText);
        tv.setText("OFF");
        tv.setTextColor(Color.parseColor("#ffffff"));
    }

    public void setSafeMode() {
        View view = this.getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        view.setSystemUiVisibility(uiOptions);
        view.setBackgroundColor(Color.parseColor("#2ecc71"));
        TextView tv = (TextView) findViewById(R.id.ModeText);
        tv.setText("SAFE");
        tv.setTextColor(Color.parseColor("#ffffff"));
    }

    public void setGasMode() {
        View view = this.getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        view.setSystemUiVisibility(uiOptions);
        view.setBackgroundColor(Color.parseColor("#f1c40f"));
        TextView tv = (TextView) findViewById(R.id.ModeText);
        tv.setText("GAS");
        tv.setTextColor(Color.parseColor("#ffffff"));
    }

    public void setFireMode() {
        View view = this.getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        view.setSystemUiVisibility(uiOptions);
        view.setBackgroundColor(Color.parseColor("#e74c3c"));
        TextView tv = (TextView) findViewById(R.id.ModeText);
        tv.setText("FIRE");
        tv.setTextColor(Color.parseColor("#ffffff"));
    }

    public void popToast() {
        Toast toast = Toast.makeText(getApplicationContext(),
                "Try again!", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 100);
        toast.show();
    }
}
