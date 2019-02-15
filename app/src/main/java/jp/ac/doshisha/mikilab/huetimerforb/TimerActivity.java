package jp.ac.doshisha.mikilab.huetimerforb;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import okhttp3.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class TimerActivity extends AppCompatActivity {

    int time_index[] = new int[3];
    int color_index[] = new int[3];
    int flash_index[] = new int[3];
    int hue_index;
    int conferenceMode;

    String url = "http://192.168.1.38/api/p1SV3amWQL4aQF9tPJdJ4vOBqSwzK3iFH1gwENrI/groups/1/action";
//    String url = "http://192.168.1.38/api/v9Si5xeEsNFFPxGbkkGLkC7Bo6eajagN2JdKeTx7/groups/1/action";
//    String url = "http://172.20.11.100/api/z2YrJsBIMyPZlHWprsFmIjlfI2WaR9kxTHA6XVaI/groups/1/action";

    private static final int REQUESTCODE_TEST1 = 1;
    private static final int REQUESTCODE_TEST = 2;

    int minute = 0, second = 0;
    int start_flag = 0;
    int count;
    int countup_flag = 0;
    int demo_flag = 0;
    long countNumber;
    long interval = 100;
    int buttonNum;
    int time1, time2, time3;

    private TextView timerText;
    private Timer timer;
    private Handler handler = new Handler();
    private CountDown countDown;

    int flashTime = 20;

    Button sb;
    String json;
    private String res = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        time_index[0] = 0; time_index[1] = 5; time_index[2] = 10;
        color_index[0] = 5; color_index[1] = 1; color_index[2] = 0;
        flash_index[2] = 1;
        hue_index = 0;
        conferenceMode = 1;

        minute = 0;
        timerText = findViewById(R.id.timer);
        timerText.setText(String.format("%1$02d:%2$02d", minute, second));
    }

    public void onButtonClick(View v) {
        switch (v.getId()){
            case R.id.button_mode1:
                sb = findViewById(R.id.button_mode1);
                sb.setTextColor(0xFFFF8400);
                sb = findViewById(R.id.button_mode2);
                sb.setTextColor(0xFFFFFFFF);
                sb = findViewById(R.id.button_mode3);
                sb.setTextColor(0xFFFFFFFF);
                sb = findViewById(R.id.button_mode4);
                sb.setTextColor(0xFFFFFFFF);
                sb = findViewById(R.id.off_button);
                sb.setTextColor(0xFFFFFFFF);
                conferenceMode = 0;
                try {
                    ModeLights(conferenceMode);
                }catch (IOException e){

                }
                break;
            case R.id.button_mode2:
                sb = findViewById(R.id.button_mode1);
                sb.setTextColor(0xFFFFFFFF);
                sb = findViewById(R.id.button_mode2);
                sb.setTextColor(0xFFFF8400);
                sb = findViewById(R.id.button_mode3);
                sb.setTextColor(0xFFFFFFFF);
                sb = findViewById(R.id.button_mode4);
                sb.setTextColor(0xFFFFFFFF);
                sb = findViewById(R.id.off_button);
                sb.setTextColor(0xFFFFFFFF);
                conferenceMode = 1;
                try {
                    ModeLights(conferenceMode);
                }catch (IOException e){

                }
                break;
            case R.id.button_mode3:
                sb = findViewById(R.id.button_mode1);
                sb.setTextColor(0xFFFFFFFF);
                sb = findViewById(R.id.button_mode2);
                sb.setTextColor(0xFFFFFFFF);
                sb = findViewById(R.id.button_mode3);
                sb.setTextColor(0xFFFF8400);
                sb = findViewById(R.id.button_mode4);
                sb.setTextColor(0xFFFFFFFF);
                sb = findViewById(R.id.off_button);
                sb.setTextColor(0xFFFFFFFF);
                conferenceMode = 2;
                try {
                    ModeLights(conferenceMode);
                }catch (IOException e){

                }
                break;
            case R.id.button_mode4:
                sb = findViewById(R.id.button_mode1);
                sb.setTextColor(0xFFFFFFFF);
                sb = findViewById(R.id.button_mode2);
                sb.setTextColor(0xFFFFFFFF);
                sb = findViewById(R.id.button_mode3);
                sb.setTextColor(0xFFFFFFFF);
                sb = findViewById(R.id.button_mode4);
                sb.setTextColor(0xFFFF8400);
                sb = findViewById(R.id.off_button);
                sb.setTextColor(0xFFFFFFFF);
                conferenceMode = 3;
                try {
                    ModeLights(conferenceMode);
                }catch (IOException e){

                }
                break;
            case R.id.off_button:
                sb = findViewById(R.id.button_mode1);
                sb.setTextColor(0xFFFFFFFF);
                sb = findViewById(R.id.button_mode2);
                sb.setTextColor(0xFFFFFFFF);
                sb = findViewById(R.id.button_mode3);
                sb.setTextColor(0xFFFFFFFF);
                sb = findViewById(R.id.button_mode4);
                sb.setTextColor(0xFFFFFFFF);
                sb = findViewById(R.id.off_button);
                sb.setTextColor(0xFFFF8400);
                try {
                    noLights();
                }catch (IOException e){

                }
                break;

            case R.id.button_01:
                if(start_flag == 0 && demo_flag == 0) {
                    minute += 1;
                    timerText.setText(String.format("%1$02d:%2$02d", minute, second));
                }
                break;

            case R.id.button_05:
                if(start_flag == 0 && demo_flag == 0) {
                    minute += 5;
                    timerText.setText(String.format("%1$02d:%2$02d", minute, second));
                }
                break;

            case R.id.button_10:
                if(start_flag == 0 && demo_flag == 0) {
                    minute += 10;
                    timerText.setText(String.format("%1$02d:%2$02d", minute, second));
                }
                break;

            case R.id.button_15:
                if(start_flag == 0 && demo_flag == 0) {
                    minute += 15;
                    timerText.setText(String.format("%1$02d:%2$02d", minute, second));
                }
                break;

            case R.id.demo_button:
                if(start_flag == 0) {
                    sb = findViewById(R.id.demo_button);
                    if(demo_flag == 0) {
                        demo_flag = 1;
                        minute = 0;
                        second = 10;
                        timerText.setText(String.format("%1$02d:%2$02d", minute, second));
                        sb.setText("Demo");
                        sb.setTextColor(0xFFFF0000);
                    }
                }
                break;

            case R.id.clear_button:
                if(start_flag != 1) {
                    countup_flag = 0;
                    demo_flag = 0 ;
                    minute = 0; second = 0;
                    timerText.setText(String.format("%1$02d:%2$02d", minute, second));
                    timerText.setTextColor(Color.parseColor("#40C4FF"));
                    if(demo_flag == 0){
                        sb = findViewById(R.id.demo_button);
                        sb.setText("Demo");
                        sb.setTextColor(0xFF000000);
                        demo_flag = 0;
                    }

                    try {
                        ModeLights(conferenceMode);
                    }catch (IOException e){

                    }
                    sb = findViewById(R.id.start_button);
                    sb.setText("Start");
                    start_flag = 0;
                }
                break;

            case R.id.start_button:
                sb = findViewById(R.id.start_button);

                if(start_flag == 0 && (minute > 0 || second > 0)) {
                    sb = findViewById(R.id.button_mode1);
                    sb.setTextColor(0xFFFFFFFF);
                    sb = findViewById(R.id.button_mode2);
                    sb.setTextColor(0xFFFFFFFF);
                    sb = findViewById(R.id.button_mode3);
                    sb.setTextColor(0xFFFFFFFF);
                    sb = findViewById(R.id.button_mode4);
                    sb.setTextColor(0xFFFFFFFF);
                    sb = findViewById(R.id.off_button);
                    sb.setTextColor(0xFFFFFFFF);
                    Intent intent1 = new Intent(this, ModeActivity.class);
                    intent1.putExtra("time_index", time_index);
                    intent1.putExtra("color_index", color_index);
                    intent1.putExtra("flash_index", flash_index);
                    intent1.putExtra("conferenceMode", conferenceMode);
                    startActivityForResult(intent1, REQUESTCODE_TEST1);
                    break;
                }else if (start_flag == 1){
                    countDown.cancel();
                    if(countup_flag == 1){
                        timer.cancel();
                    }
                    start_flag = 2;
                    sb.setText("Restart");
                }else if (start_flag == 2){
                    if(countup_flag == 0) {
                        countNumber = (minute * 60 + second) * 1000;
                        countDown = new CountDown(countNumber, interval);
                        countDown.start();
                    }else{
                        countDown.countup();
                    }
                    sb = findViewById(R.id.start_button);
                    sb.setText("Stop");
                    start_flag = 1;
                }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            Intent intent2 = new Intent(this, MainActivity.class);
            startActivityForResult(intent2,REQUESTCODE_TEST);
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUESTCODE_TEST1:
                if (RESULT_OK == resultCode) {
                    time_index = data.getIntArrayExtra("time_index");
                    color_index = data.getIntArrayExtra("color_index");
                    flash_index = data.getIntArrayExtra("flash_index");
                    conferenceMode = data.getIntExtra("conferenceMode", 0);

                    if(start_flag == 0) {
                        if(conferenceMode == 0) {
                            sb = findViewById(R.id.button_mode1);
                            sb.setTextColor(0xFFFF8400);
                        }else if(conferenceMode == 1) {
                            sb = findViewById(R.id.button_mode2);
                            sb.setTextColor(0xFFFF8400);
                        }else if(conferenceMode == 2) {
                            sb = findViewById(R.id.button_mode3);
                            sb.setTextColor(0xFFFF8400);
                        }else if(conferenceMode == 3){
                            sb = findViewById(R.id.button_mode4);
                            sb.setTextColor(0xFFFF8400);
                        }
                        try{
                            ModeLights(conferenceMode);
                        }catch (IOException e){

                        }
                        start_flag = 1;
                        countNumber = (minute*60+second)*1000;
                        countDown = new CountDown(countNumber, interval);
                        countDown.start();
                        sb = findViewById(R.id.start_button);
                        sb.setText("Stop");
                    }
                }
                break;
        }
    }

    //カウントダウン
    public class CountDown extends CountDownTimer {

        public CountDown(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            // カウントダウン完了後に呼ばれる
            count = 0;
            countup_flag = 1;
            if(time_index[0] == 0) {
                try {
                    lights(color_index[0]);
                } catch (IOException e) {

                }
            }

            countup();
        }

        @Override
        public void onTick(long millisUntilFinished) {
            // インターバル(countDownInterval)毎に呼ばれる
            minute = (int)millisUntilFinished/1000/60;  second = (int)millisUntilFinished/1000%60;
            timerText.setText(String.format("%1$02d:%2$02d", minute, second));
        }

        //カウントアップ
        public void countup() {
            timer = new Timer();
            timerText.setTextColor(Color.RED);

            time1 = time_index[0] * 600;
            time2 = time_index[1] * 600;
            time3 = time_index[2] * 600;

            timer.scheduleAtFixedRate (new TimerTask() {
                @Override
                public void run() {
                    // handlerdを使って処理をキューイングする
                    handler.post(new Runnable() {
                        public void run() {
                            count++;
                            minute = count * 100 / 1000 / 60;   second = count * 100 / 1000 % 60;
                            timerText.setText(String.format("%1$02d:%2$02d", minute, second));
                            if(demo_flag == 1){     //デモ用
                                if(count < 100){
                                    if(flash_index[0] == 1){
                                        if(count % (flashTime * 2) == 0){
                                            try{
                                                noLights();
                                            }catch (IOException e){

                                            }
                                        }else if(count % flashTime == 0){
                                            try {
                                                lights(color_index[0]);
                                            }catch (IOException e){

                                            }
                                        }

                                    }
                                }else if(count == 100){
                                    try{
                                        lights(color_index[1]);
                                    }catch (IOException e){

                                    }
                                }else if(count < 200){
                                    if(flash_index[1] == 1){
                                        if(count % (flashTime * 2) == 0){
                                            try{
                                                noLights();
                                            }catch (IOException e){

                                            }
                                        }else if(count % flashTime == 0){
                                            try {
                                                lights(color_index[1]);
                                            }catch (IOException e){

                                            }
                                        }
                                    }
                                }else if(count == 200){
                                    try{
                                        lights(color_index[2]);
                                    }catch (IOException e){

                                    }
                                }else{
                                    if(flash_index[2] == 1){
                                        if(count % (flashTime * 2) == 0){
                                            try{
                                                noLights();
                                            }catch (IOException e){

                                            }
                                        }else if(count % flashTime == 0){
                                            try {
                                                lights(color_index[2]);
                                            }catch (IOException e){

                                            }
                                        }
                                    }
                                }
                            }else{      //通常使用
                                if(count < time1){
                                    if(flash_index[0] == 1){
                                        if(count % (flashTime * 2) == 0){
                                            try{
                                                noLights();
                                            }catch (IOException e){

                                            }
                                        }else if(count % flashTime == 0){
                                            try {
                                                lights(color_index[0]);
                                            }catch (IOException e){

                                            }
                                        }

                                    }
                                }else if(count == time1){
                                    try{
                                        lights(color_index[0]);
                                    }catch (IOException e){

                                    }
                                }else if(count < time2){
                                    if(flash_index[1] == 1){
                                        if(count % (flashTime * 2) == 0){
                                            try{
                                                noLights();
                                            }catch (IOException e){

                                            }
                                        }else if(count % flashTime == 0){
                                            try {
                                                lights(color_index[0]);
                                            }catch (IOException e){

                                            }
                                        }
                                    }
                                }else if(count == time2){
                                    try{
                                        lights(color_index[1]);
                                    }catch (IOException e){

                                    }
                                }else if(count < time3){
                                    if(flash_index[2] == 1){
                                        if(count % (flashTime * 2) == 0){
                                            try{
                                                noLights();
                                            }catch (IOException e){

                                            }
                                        }else if(count % flashTime == 0){
                                            try {
                                                lights(color_index[1]);
                                            }catch (IOException e){

                                            }
                                        }
                                    }
                                }else if(count == time3) {
                                    try {
                                        lights(color_index[2]);
                                    } catch (IOException e) {

                                    }
                                }else{
                                    if(flash_index[2] == 1){
                                        if(count % (flashTime * 2) == 0){
                                            try{
                                                noLights();
                                            }catch (IOException e){

                                            }
                                        }else if(count % flashTime == 0){
                                            try {
                                                lights(color_index[2]);
                                            }catch (IOException e){

                                            }
                                        }
                                    }
                                }

                            }

                        }
                    });
                }
            }, 0, interval);
        }

    }

    public void postTest() {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, json);

        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .build();

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

    void lights(int color) throws IOException{
        if(color == 0)  json = "{\"on\":true,\"hue\":0,\"bri\":254,\"sat\":254}";
        else if(color == 1) json = "{\"on\":true,\"hue\":60000,\"bri\":254,\"sat\":254}";
        else if(color == 2)  json = "{\"on\":true,\"hue\":46014,\"bri\":254,\"sat\":254}";
        else if(color == 3) json = "{\"on\":true,\"hue\":24000,\"bri\":254,\"sat\":254}";
        else if(color == 4) json = "{\"on\":true,\"hue\":7774,\"bri\":254,\"sat\":254}";
        else if(color == 5) json = "{\"on\":true,\"hue\":50000,\"bri\":254,\"sat\":254}";
        else json = "{\"on\":true,\"hue\":15324,\"bri\":254,\"sat\":121}";
        postTest();
    }

    void noLights() throws  IOException{
        json = "{\"on\":false}";
        postTest();
    }

    void ModeLights(int color) throws IOException{
        if(color == 0)  json = "{\"on\":true,\"bri\":254,\"hue\":41432,\"sat\":75}";
        else if(color == 1) json = "{\"on\":true,\"bri\":254,\"hue\":9000,\"sat\":100}";
        else if(color == 2)  json = "{\"on\":true,\"bri\":53,\"hue\":8504,\"sat\":130}";
        else if(color == 3)json = "{\"on\":true,\"bri\":254,\"hue\":7856,\"sat\":188}";
        else json = "{\"on\":true,\"bri\":254,\"hue\":7856,\"sat\":188}";
        postTest();
    }

}
