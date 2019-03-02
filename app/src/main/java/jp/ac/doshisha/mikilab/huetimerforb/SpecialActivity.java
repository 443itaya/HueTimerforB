package jp.ac.doshisha.mikilab.huetimerforb;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import okhttp3.*;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class SpecialActivity extends AppCompatActivity {

    String url = "http://192.168.1.38/api/p1SV3amWQL4aQF9tPJdJ4vOBqSwzK3iFH1gwENrI/lights/";
    String urlGroups = "http://192.168.1.38/api/p1SV3amWQL4aQF9tPJdJ4vOBqSwzK3iFH1gwENrI/groups/1/action";
//    String url = "http://172.20.11.100/api/z2YrJsBIMyPZlHWprsFmIjlfI2WaR9kxTHA6XVaI/lights/";
//    String urlGroups = "http://172.20.11.100/api/z2YrJsBIMyPZlHWprsFmIjlfI2WaR9kxTHA6XVaI/groups/1/action";
    String json;
    private String res = "";
    int random;
    int tmp = 0;
    long interval = 1500;

    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        baseColor();

        timer = new Timer();

        Button moveButton = (Button) findViewById(R.id.move_button);
        Button stopButton = (Button) findViewById(R.id.stop_button);


        moveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                timer.scheduleAtFixedRate(
                        new TimerTask()
                        {
                            @Override
                            public void run()
                            {
                                changeColor();
                            }
                        }, 0, interval);
                //10ミリ秒後に100ミリ秒間隔でタスク実行
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                timer.cancel();
                timer = new Timer();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();

        // Nav barの非表示化
        View decor = this.getWindow().getDecorView();
        decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    public void baseColor(){
        for (int i = 1; i < 16; i++) {
            if (i % 5 == 0) {
                json = "{\"on\":true,\"hue\":0,\"bri\":254,\"sat\":254}";
                postTest(i);
            }
            if (i % 5 == 1){
                json = "{\"on\":true,\"hue\":60000,\"bri\":254,\"sat\":254}";
                postTest(i);
            }
            if (i % 5 == 2){
                json = "{\"on\":true,\"hue\":7774,\"bri\":254,\"sat\":254}";
                postTest(i);
            }
            if (i % 5 == 3){
                json = "{\"on\":true,\"hue\":50000,\"bri\":254,\"sat\":254}";
                postTest(i);
            }
            if (i % 5 == 4){
                json = "{\"on\":true,\"hue\":24000,\"bri\":254,\"sat\":254}";
                postTest(i);
            }
        }
    }

    public void changeColor() {
        random = (int) (Math.random() * 5);
        if (random == tmp) random += 1;
        for (int i = 1; i < 16; i++) {
            if (i % 5 == random) {
                json = "{\"on\":true,\"hue\":0,\"bri\":254,\"sat\":254}";
                postTest(i);
            }
            int random1 = (random + 1) % 5;
            if (i % 5 == random1) {
                json = "{\"on\":true,\"hue\":60000,\"bri\":254,\"sat\":254}";
                postTest(i);
            }
            int random2 = (random + 2) % 5;
            if (i % 5 == random2) {
                json = "{\"on\":true,\"hue\":7774,\"bri\":254,\"sat\":254}";
                postTest(i);
            }
            int random3 = (random + 3) % 5;
            if (i % 5 == random3) {
                json = "{\"on\":true,\"hue\":50000,\"bri\":254,\"sat\":254}";
                postTest(i);
            }
            int random4 = (random + 4) % 5;
            if (i % 5 == random4) {
                json = "{\"on\":true,\"hue\":24000,\"bri\":254,\"sat\":254}";
                postTest(i);
            }
        }
        tmp = random;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            json = "{\"on\":false}";
            postTest(0);
            Intent intent = new Intent(this, MainActivity.class);
            setResult(RESULT_OK, intent);
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    public void postTest(int num) {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, json);
        Request request;

        if (num == 0){
            request = new Request.Builder()
                    .url(urlGroups)
                    .put(body)
                    .build();
        }else {
           request = new Request.Builder()
                    .url(url + num + "/state")
                    .put(body)
                    .build();
        }

        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                failMessage();
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                res = response.body().string();
                runOnUiThread(new Runnable() {
                    public void run() {
                        Log.w("onResponse", res);
                    }
                });
            }
        });
    }
    private void failMessage() {
        runOnUiThread(new Runnable() {
            public void run() {
                Log.w("failMessage", "fail");
            }
        });
    }

}
