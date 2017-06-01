package trophy.projetc2.User;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tsengvn.typekit.TypekitContextWrapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import trophy.projetc2.Http.HttpClient;
import trophy.projetc2.R;

import static trophy.projetc2.User.Find_Password.activity_findpassword;

/**
 * Created by 박효근 on 2017-04-03.
 */

public class Find_Password_Change extends AppCompatActivity {
    String Phone;
    EditText edt_confirmPw1, edt_confirmPw2;
    Button btn_confirmPw2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_user_change_pw2);

        Intent intent = getIntent();
        Phone = intent.getStringExtra("Phone");

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
                    String result = changePw.HttpClient("Trophy_part1", "User_FindPassword_Chnage.jsp", pw1, Phone);

                    if(jsonParserListgetChangePwIsOk(result)[0][0].equals("isOk")) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(Find_Password_Change.this);
                        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                                activity_findpassword.finish();
                                dialog.dismiss();     //닫기
                            }
                        });
                        alert.setMessage("비밀번호가 변경되었습니다");
                        alert.show();

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

            String[] jsonName = {"msg1"};
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
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
