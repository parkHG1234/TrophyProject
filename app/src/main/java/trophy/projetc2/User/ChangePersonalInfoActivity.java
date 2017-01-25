package trophy.projetc2.User;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import trophy.projetc2.Http.HttpClient;
import trophy.projetc2.MainActivity;
import trophy.projetc2.R;

/**
 * Created by ldong on 2017-01-23.
 */

public class ChangePersonalInfoActivity extends AppCompatActivity {
    TextView tv_name, tv_sex, tv_birth, tv_do, tv_si, tv_phone;
    Button btn_change_area, btn_change_phone, btn_change_pw, btn_withdrawal;
    SharedPreferences preferences;
    String Pk;
    public static Activity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_change_personalinfo);
        activity = ChangePersonalInfoActivity.this;

        preferences = getSharedPreferences("trophy", MODE_PRIVATE);
        Pk = preferences.getString("Pk", ".");

        HttpClient getInfo = new HttpClient();
        String result = getInfo.HttpClient("Trophy_part3", "ChangePersonalInfo.jsp", Pk);
        final String[][] parsedData = jsonParserListgetInfo(result);
        if(parsedData[0][1].equals("W")) {
            parsedData[0][1] = "여자";
        }else if(parsedData[0][1].equals("M")) {
            parsedData[0][1] = "남자";
        }

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



        tv_name.setText(parsedData[0][0]);
        tv_sex.setText(parsedData[0][1]);
        tv_birth.setText(parsedData[0][2]);
        tv_do.setText(parsedData[0][3]);
        tv_si.setText(parsedData[0][4]);
        tv_phone.setText(parsedData[0][5]);

        btn_change_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChangePersonalInfoActivity.this, MainActivity.class));
            }
        });

        btn_change_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChangePersonalInfoActivity.this, MainActivity.class));
            }
        });

        btn_change_pw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent changePw_intent = new Intent(ChangePersonalInfoActivity.this, ChangePw1Activity.class);
                changePw_intent.putExtra("Pw", parsedData[0][6]);
                startActivity(changePw_intent);
            }
        });

        btn_withdrawal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChangePersonalInfoActivity.this, Withdrawal.class));
            }
        });



    }


    private String[][] jsonParserListgetInfo(String pRecvServerPage) {
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try {
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jarr = json.getJSONArray("List");

            String[] jsonName = {"Name", "Sex", "Birth", "Do", "Si", "Phone", "Pw"};
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
