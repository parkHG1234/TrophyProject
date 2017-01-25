package trophy.projetc2.User;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import trophy.projetc2.Http.HttpClient;
import trophy.projetc2.MainActivity;
import trophy.projetc2.R;

/**
 * Created by ldong on 2017-01-24.
 */

public class ChangePw2Activity extends AppCompatActivity {
    String Pw;
    EditText edt_confirmPw1, edt_confirmPw2;
    Button btn_confirmPw2;


    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_change_pw2);

        Intent intent = getIntent();
        Pw = intent.getStringExtra("Pw");

        edt_confirmPw1 = (EditText) findViewById(R.id.edt_confirmPw1);
        edt_confirmPw2 = (EditText) findViewById(R.id.edt_confirmPw2);

        btn_confirmPw2 = (Button) findViewById(R.id.btn_confirmPw2);

        btn_confirmPw2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pw1 = edt_confirmPw1.getText().toString();
                String pw2 = edt_confirmPw2.getText().toString();
                SharedPreferences preferences = getSharedPreferences("trophy", MODE_PRIVATE);
                if(pw1.equals(pw2)) {
                    HttpClient changePw = new HttpClient();
                    String result = changePw.HttpClient("Trophy_part3", "Change_Password.jsp", pw1, preferences.getString("Pk", "."));

                    if(jsonParserListgetChangePwIsOk(result)[0][0].equals("isOk")) {
                        //추후 다이얼로그 넣기
                        finish();
                    }
                }else {
                    Snackbar.make(v , "정확한 비밀번호를 입력해 주세요.", Snackbar.LENGTH_LONG)
                            .show();
                }
            }
        });
    }

    private String[][] jsonParserListgetChangePwIsOk(String pRecvServerPage) {
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try {
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jarr = json.getJSONArray("List");

            String[] jsonName = {"isOk"};
            String[][] parseredData = new String[jarr.length()][jsonName.length];
            for (int i = 0; i < jarr.length(); i++) {
                json = jarr.getJSONObject(i);
                for (int j = 0; j < jsonName.length; j++) {
                    parseredData[i][j] = json.getString(jsonName[j]);
                }
            }
            for (int i = 0; i < parseredData.length; i++) {
                Log.i("JSON을 분석한 데이터" + i + ":", parseredData[0][i]);
            }
            return parseredData;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

}
