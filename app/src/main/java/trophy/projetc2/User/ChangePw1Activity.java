package trophy.projetc2.User;

import android.app.Activity;
import android.content.Intent;
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
import trophy.projetc2.R;

/**
 * Created by ldong on 2017-01-24.
 */

public class ChangePw1Activity extends AppCompatActivity {
    String Pw;
    EditText edt_confromPw;
    Button btn_confirmPw;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_change_pw1);

        Intent intent = getIntent();
        Pw = intent.getStringExtra("Pw");

        edt_confromPw = (EditText) findViewById(R.id.edt_confromPw);
        btn_confirmPw = (Button) findViewById(R.id.btn_confirmPw);

        btn_confirmPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpClient encodePw = new HttpClient();
                String result = encodePw.HttpClient("Trophy_part3", "Encode_Password.jsp", edt_confromPw.getText().toString());
                String[][] currentPw = jsonParserListgetgetPW(result);
                if (currentPw[0][0].equals(Pw)) {
                    startActivity(new Intent(ChangePw1Activity.this, ChangePw2Activity.class));
                    finish();
                } else {
                    Snackbar.make(v, "비밀번호를 확인해 주세요.", Snackbar.LENGTH_LONG)
                            .show();
                }
            }
        });
    }

    private String[][] jsonParserListgetgetPW(String pRecvServerPage) {
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try {
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jarr = json.getJSONArray("List");

            String[] jsonName = {"strENCpw"};
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
