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
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tsengvn.typekit.TypekitContextWrapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import trophy.projetc2.Http.HttpClient;
import trophy.projetc2.R;

/**
 * Created by ldong on 2017-01-31.
 */

public class ChangePhoneActivity extends AppCompatActivity {
    ImageView User_Change_Phone_ImageView_Back;
    LinearLayout linearChangePhone1, linearChangePhone2;
    EditText edt_confirmPhone, edt_confirmPhoneRnd;
    Button btn_confirmPhone, btn_confirmPhoneRnd;
    int rnd;
    String Pk, Phone;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_user_change_phone);

        SharedPreferences preferences = getSharedPreferences("trophy", MODE_PRIVATE);
        Pk = preferences.getString("Pk", ".");

        User_Change_Phone_ImageView_Back = (ImageView)findViewById(R.id.User_Change_Phone_ImageView_Back);
        linearChangePhone1 = (LinearLayout) findViewById(R.id.linearChangePhone1);
        linearChangePhone2 = (LinearLayout) findViewById(R.id.linearChangePhone2);
        edt_confirmPhone = (EditText) findViewById(R.id.edt_confirmPhone);
        edt_confirmPhoneRnd = (EditText) findViewById(R.id.edt_confirmPhoneRnd);
        btn_confirmPhone = (Button) findViewById(R.id.btn_confirmPhone);
        btn_confirmPhoneRnd = (Button) findViewById(R.id.btn_confirmPhoneRnd);

        linearChangePhone2.setVisibility(View.GONE);

        btn_confirmPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Phone = edt_confirmPhone.getText().toString();
                HttpClient user = new HttpClient();
                String result = user.HttpClient("Trophy_part2", "Phone_Confirm.jsp",Phone);
                String[][] parseData = jsonParserList(result);
                if (parseData[0][0].toString().equals("not existent")) {
                    if (Phone.length() == 11) {
                        Random random = new Random();
                        rnd = Math.abs(random.nextInt(899999) + 100000);
                        Log.i("인증번호 : ",String.valueOf(rnd));
                        String msg = "오늘의농구 인증번호는 [" + String.valueOf(rnd) + "] 입니다.감사합니다.";
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault());
                        Date d = new Date();
                        String date = dateFormat.format(d);
                        user = new HttpClient();
                        user.HttpClient("InetSMSExample", "example.jsp", msg, Phone, Phone, date);
                        linearChangePhone1.setVisibility(View.GONE);
                        linearChangePhone2.setVisibility(View.VISIBLE);
                    } else {
                        Snackbar.make(v, "정확한 핸드폰 번호를 입력해 주세요", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(v, "가입되어있는 핸드폰 번호가 있습니다", Snackbar.LENGTH_LONG).show();
                }
            }
        });


        btn_confirmPhoneRnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (String.valueOf(rnd).equals(edt_confirmPhoneRnd.getText().toString())) {
                    HttpClient changePhone = new HttpClient();
                    String result = changePhone.HttpClient("Trophy_part1", "User_ChangePhone.jsp", Phone, Pk);
                    Log.i("비밀번호 변경 확인 : ", result);
                    if (result.equals("succed")) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(ChangePhoneActivity.this);
                        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(ChangePhoneActivity.this, ChangePersonalInfoActivity.class);
                                finish();
                                ChangePersonalInfoActivity CA = (ChangePersonalInfoActivity) ChangePersonalInfoActivity.activity;
                                CA.finish();
                                startActivity(intent);
                                dialog.dismiss();     //닫기
                            }
                        });
                        alert.setMessage("핸드폰번호가 변경되었습니다");
                        alert.show();
                    } else {
                        AlertDialog.Builder alert = new AlertDialog.Builder(ChangePhoneActivity.this);
                        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        alert.setMessage("다시 시도해 주세요");
                        alert.show();
                    }

                } else {

                }
            }
        });
        User_Change_Phone_ImageView_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
            }
        });
    }

    private String[][] jsonParserList(String pRecvServerPage) {
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

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
