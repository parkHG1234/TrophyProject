package trophy.projetc2.User;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.tsengvn.typekit.TypekitContextWrapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import trophy.projetc2.Http.HttpClient;
import trophy.projetc2.R;

/**
 * Created by ldong on 2017-01-23.
 */

public class ChangePersonalInfoActivity extends AppCompatActivity {
    TextView tv_name, tv_sex, tv_birth, tv_do, tv_si, tv_phone;
    ImageView User_Change_Personalinfo_ImageView_Back;
    Button btn_change_area, btn_change_phone, btn_change_pw, btn_withdrawal;
    Switch switch_push;
    SharedPreferences preferences;
    String Pk, TeamName;
    public static Activity activity;
    String[][] parsedData, parsedData_Alarm_Off;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_user_change_personalinfo);
        activity = ChangePersonalInfoActivity.this;

        preferences = getSharedPreferences("trophy", MODE_PRIVATE);
        Pk = preferences.getString("Pk", ".");

        Intent intent = getIntent();
        TeamName = intent.getStringExtra("TeamName");

        HttpClient getInfo = new HttpClient();
        String result = getInfo.HttpClient("Trophy_part1", "ChangePersonalInfo.jsp", Pk);
        parsedData = jsonParserListgetInfo(result);
        if(parsedData[0][1].equals("W")) {
            parsedData[0][1] = "여자";
        }else if(parsedData[0][1].equals("M")) {
            parsedData[0][1] = "남자";
        }

        User_Change_Personalinfo_ImageView_Back = (ImageView)findViewById(R.id.User_Change_Personalinfo_ImageView_Back);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_sex = (TextView) findViewById(R.id.tv_sex);
        tv_birth = (TextView) findViewById(R.id.tv_birth);
        tv_do = (TextView) findViewById(R.id.tv_do);
        tv_si = (TextView) findViewById(R.id.tv_si);
        tv_phone = (TextView) findViewById(R.id.tv_phone);
        btn_change_area = (Button) findViewById(R.id.btn_change_area);
        btn_change_pw = (Button) findViewById(R.id.btn_change_pw);
        btn_change_phone = (Button) findViewById(R.id.btn_change_phone);
        btn_withdrawal = (Button) findViewById(R.id.btn_withdrawal);
        switch_push = (Switch)findViewById(R.id.switch_push);


        tv_name.setText(parsedData[0][0]);
        tv_sex.setText(parsedData[0][1]);
        tv_birth.setText(parsedData[0][2]);
        tv_do.setText(parsedData[0][3]);
        tv_si.setText(parsedData[0][4]);
        tv_phone.setText(parsedData[0][5]);
        if(parsedData[0][8].equals("on")){
            switch_push.setChecked(true);
        }
        else if(parsedData[0][8].equals("off")){
            switch_push.setChecked(false);
        }
        btn_change_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChangePersonalInfoActivity.this, ChangeAreaActivity.class));
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
            }
        });

        btn_change_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChangePersonalInfoActivity.this, ChangePhoneActivity.class));
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
            }
        });

        btn_change_pw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent changePw_intent = new Intent(ChangePersonalInfoActivity.this, ChangePw1Activity.class);
                changePw_intent.putExtra("Pw", parsedData[0][6]);
                startActivity(changePw_intent);
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
            }
        });
        switch_push.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked == true){
                    HttpClient http_alarm_on = new HttpClient();
                    String result = http_alarm_on.HttpClient("Trophy_part1", "User_Alarm_On.jsp", Pk);
                }
                else{
                    HttpClient http_alarm_off = new HttpClient();
                    String result = http_alarm_off.HttpClient("Trophy_part1", "User_Alarm_Off.jsp", Pk);
                    //parsedData_Alarm_Off = jsonParserList_Alarm_Off(result);
                }
            }
        });
        btn_withdrawal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChangePersonalInfoActivity.this, Withdrawal.class);
                intent.putExtra("TeamName", TeamName);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
            }
        });
        User_Change_Personalinfo_ImageView_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
            }
        });


    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
    private String[][] jsonParserListgetInfo(String pRecvServerPage) {
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try {
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jarr = json.getJSONArray("List");

            String[] jsonName = {"msg1", "msg2", "msg3", "msg4", "msg5", "msg6", "msg7", "msg8", "msg9"};
            String[][] parseredData = new String[jarr.length()][jsonName.length];
            for (int i = 0; i < jarr.length(); i++) {
                json = jarr.getJSONObject(i);
                for (int j = 0; j < jsonName.length; j++) {
                    parseredData[i][j] = json.getString(jsonName[j]);
                }
            }
            for (int i = 0; i < parseredData.length; i++) {
                Log.i("JSON을 분석한 데이터" + i + ":", parseredData[i][0]);
            }
            return parseredData;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    private String[][] jsonParserList_Alarm_Off(String pRecvServerPage) {
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try {
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jarr = json.getJSONArray("List");

            String[] jsonName = {"msg1"};
            String[][] parseredData = new String[jarr.length()][jsonName.length];
            for (int i = 0; i < jarr.length(); i++) {
                json = jarr.getJSONObject(i);
                for (int j = 0; j < jsonName.length; j++) {
                    parseredData[i][j] = json.getString(jsonName[j]);
                }
            }
            for (int i = 0; i < parseredData.length; i++) {
                Log.i("JSON을 분석한 데이터" + i + ":", parseredData[i][0]);
            }
            return parseredData;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
