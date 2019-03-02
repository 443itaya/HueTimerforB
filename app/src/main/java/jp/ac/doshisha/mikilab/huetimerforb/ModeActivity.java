package jp.ac.doshisha.mikilab.huetimerforb;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

public class ModeActivity extends AppCompatActivity {

    private static final int REQUESTCODE_TEST = 1;
    int time_index[] = new int[3];
    int color_index[] = new int[3];
    int flash_index[] = new int[3];
    int conferenceMode;

    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        time_index = intent.getIntArrayExtra("time_index");
        color_index = intent.getIntArrayExtra("color_index");
        flash_index = intent.getIntArrayExtra("flash_index");
        conferenceMode = intent.getIntExtra("conferenceMode", 0);

        ArrayAdapter color_adapter = ArrayAdapter.createFromResource(this, R.array.color_list, R.drawable.spinner_item);
        color_adapter.setDropDownViewResource(R.drawable.spinner_dropdown_item);
        Spinner spinner1 = findViewById(R.id.spinner);
        spinner1.setAdapter(color_adapter);
        spinner1.setSelection(color_index[0]);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                color_index[0] = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner spinner2 = findViewById(R.id.spinner2);
        spinner2.setAdapter(color_adapter);
        spinner2.setSelection(color_index[1]);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                color_index[1] = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner spinner3 = findViewById(R.id.spinner3);
        spinner3.setAdapter(color_adapter);
        spinner3.setSelection(color_index[2]);
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                color_index[2] = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter time_adapter = ArrayAdapter.createFromResource(this, R.array.time_list, R.drawable.spinner_item);
        time_adapter.setDropDownViewResource(R.drawable.spinner_dropdown_item);
        Spinner spinner4 = findViewById(R.id.spinner4);
        spinner4.setAdapter(time_adapter);
        spinner4.setSelection(time_index[0]);
        spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                time_index[0] = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Spinner spinner5 = findViewById(R.id.spinner5);
        spinner5.setAdapter(time_adapter);
        spinner5.setSelection(time_index[1]);
        spinner5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position > time_index[0]) time_index[1] = position;
                else {
                    time_index[1] = time_index[0]+1;
                    Spinner spinner5 = findViewById(R.id.spinner5);
                    spinner5.setSelection(time_index[1]);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Spinner spinner6 = findViewById(R.id.spinner6);
        spinner6.setAdapter(time_adapter);
        spinner6.setSelection(time_index[2]);
        spinner6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position > time_index[1]) time_index[2] = position;
                else {
                    time_index[2] = time_index[1]+1;
                    Spinner spinner6 = findViewById(R.id.spinner6);
                    spinner6.setSelection(time_index[2]);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final CheckBox chkbox1 = (CheckBox)findViewById(R.id.checkBox);
        if(flash_index[0] == 1)  chkbox1.setChecked(true);
        chkbox1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(chkbox1.isChecked() == true) {
                    // チェックされた状態の時の処理を記述
                    flash_index[0] = 1;
                }
                else {
                    // チェックされていない状態の時の処理を記述
                    flash_index[0] = 0;
                }
            }
        });

        final CheckBox chkbox2 = (CheckBox)findViewById(R.id.checkBox2);
        if(flash_index[1] == 1)  chkbox2.setChecked(true);
        chkbox2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(chkbox2.isChecked() == true) {
                    // チェックされた状態の時の処理を記述
                    flash_index[1] = 1;
                }
                else {
                    // チェックされていない状態の時の処理を記述
                    flash_index[1] = 0;
                }
            }
        });

        final CheckBox chkbox3 = (CheckBox)findViewById(R.id.checkBox3);
        if(flash_index[2] == 1)  chkbox3.setChecked(true);
        chkbox3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(chkbox3.isChecked() == true) {
                    // チェックされた状態の時の処理を記述
                    flash_index[2] = 1;
                }
                else {
                    // チェックされていない状態の時の処理を記述
                    flash_index[2] = 0;
                }
            }
        });

        RadioGroup group = (RadioGroup)findViewById(R.id.radioGroup);
        id = IndexToId(conferenceMode);
        group.check(id);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                conferenceMode = IdToIndex(checkedId);
                RadioButton radio = (RadioButton)findViewById(checkedId);
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

    public void onButtonClick(View v) {
        switch (v.getId()){
            case R.id.start:
                if(time_index[1]>time_index[0] && time_index[2]>time_index[1]) {
                    Intent intent1 = new Intent(this, TimerActivity.class);
                    intent1.putExtra("time_index", time_index);
                    intent1.putExtra("color_index", color_index);
                    intent1.putExtra("flash_index", flash_index);
                    intent1.putExtra("conferenceMode", conferenceMode);
                    setResult(RESULT_OK, intent1);
                    finish(); // close this activity and return to preview activity (if there is any)
                    break;
                }else{
                    time_index[1] = time_index[0]+1;
                    Spinner spinner5 = findViewById(R.id.spinner5);
                    spinner5.setSelection(time_index[1]);
                    time_index[2] = time_index[1]+1;
                    Spinner spinner6 = findViewById(R.id.spinner5);
                    spinner6.setSelection(time_index[2]);
                }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            Intent intent2 = new Intent(this, TimerActivity.class);
//            setResult(RESULT_OK, intent2);
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    int IndexToId(int index){
        int tmp = -1;
        if (index == 0) tmp = R.id.radioButton;
        else if (index == 1) tmp = R.id.radioButton2;
        else if (index == 2) tmp = R.id.radioButton3;
        else if (index == 3) tmp = R.id.radioButton4;
        return tmp;
    }
    int IdToIndex(int id){
        int index = -1;
        if (id == R.id.radioButton) index = 0;
        else if (id == R.id.radioButton2) index = 1;
        else if (id == R.id.radioButton3) index = 2;
        else if (id == R.id.radioButton4) index = 3;
        return index;
    }

}
