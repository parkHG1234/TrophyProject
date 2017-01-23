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
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import trophy.projetc2.Http.HttpClient;
import trophy.projetc2.MainActivity;
import trophy.projetc2.R;

/**
 * Created by ldong on 2017-01-16.
 */

public class Login extends AppCompatActivity {

    protected EditText phone_Layout_EditText, pw_Layout_EditText;
    protected Button login_button;
    protected TextView join_button, search_button;
    protected String phone, pw;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);

        phone_Layout_EditText = (EditText) findViewById(R.id.phone_Layout_EditText);
        pw_Layout_EditText = (EditText) findViewById(R.id.pw_Layout_EditText);
        login_button = (Button) findViewById(R.id.login_button);
        join_button = (TextView) findViewById(R.id.join_button);
        search_button = (TextView) findViewById(R.id.search_button);

        login_button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            phone = phone_Layout_EditText.getText().toString();
            pw = pw_Layout_EditText.getText().toString();
            if(phone.equals("")) {
                Snackbar.make(v, "아이디를 입력해 주세요", Snackbar.LENGTH_SHORT).show();
            } else if (pw.equals("")){
                Snackbar.make(v, "비밀번호를 입력해 주세요", Snackbar.LENGTH_SHORT).show();
            } else {
                HttpClient user = new HttpClient();
                String result = user.HttpClient("Trophy_part3", "Login_Confirm.jsp",phone,pw);
                String[][] parseData = jsonParserListLogin(result);
                Log.i("parsedData[0][0]", parseData[0][0]);
                if(parseData[0][0].equals("isOk")){

                    preferences = getSharedPreferences("trophy", MODE_PRIVATE);
                    editor = preferences.edit();
                    editor.putString("Pk", parseData[0][1]);
                    editor.commit();

                    Intent MainActivityintent =  new Intent(Login.this, MainActivity.class);
                    startActivity(MainActivityintent);
                    finish();

                }else{
                    Snackbar.make(getCurrentFocus(), "아이디 비밀번호를 확인해 주세요", Snackbar.LENGTH_SHORT).show();
                }
                Log.i("Aaa",parseData[0][0]);
            }
        }});

        join_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, MemberShip.class));
            }
        });
    }

    private String[][] jsonParserListLogin(String pRecvServerPage){
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try {
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jarr = json.getJSONArray("List");

            String[] jsonName = {"msg1", "msg2"};
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
