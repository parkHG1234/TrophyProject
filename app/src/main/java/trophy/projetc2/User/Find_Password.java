package trophy.projetc2.User;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
 * Created by 박효근 on 2017-04-03.
 */

public class Find_Password extends AppCompatActivity{
    ImageView User_Find_Password_ImageView_Back;
    EditText User_Find_Password_EditText_Phone, User_Find_Password_EditText_Confirm;
    Button User_Find_Password_Button_Phone, User_Find_Password_Button_Confirm;
    TextView User_Find_Password_TextView_Warning;
    Button User_Find_Password_Button_Next;

    String Phone;int rnd;boolean Phone_flag = false;boolean Phone_Confirm_flag = false;
    static Activity activity_findpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_user_find_password);

        activity_findpassword = Find_Password.this;
        User_Find_Password_ImageView_Back = (ImageView)findViewById(R.id.User_Find_Password_ImageView_Back);
        User_Find_Password_EditText_Phone = (EditText)findViewById(R.id.User_Find_Password_EditText_Phone);
        User_Find_Password_Button_Phone = (Button)findViewById(R.id.User_Find_Password_Button_Phone);
        User_Find_Password_EditText_Confirm = (EditText)findViewById(R.id.User_Find_Password_EditText_Confirm);
        User_Find_Password_Button_Confirm = (Button)findViewById(R.id.User_Find_Password_Button_Confirm);
        User_Find_Password_TextView_Warning = (TextView)findViewById(R.id.User_Find_Password_TextView_Warning);
        User_Find_Password_Button_Next = (Button)findViewById(R.id.User_Find_Password_Button_Next);
        User_Find_Password_TextView_Warning.setVisibility(View.GONE);

        User_Find_Password_Button_Phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(User_Find_Password_EditText_Phone.getWindowToken(), 0);
                Phone = User_Find_Password_EditText_Phone.getText().toString();
                if (Phone.length() == 11 && Phone.substring(0,3).equals("010")) {
                    HttpClient user = new HttpClient();
                    String result = user.HttpClient("Trophy_part2", "Phone_Confirm.jsp",Phone);
                    String[][] parseData = jsonParserList(result);
                    if (parseData[0][0].toString().equals("not existent")) {
                        User_Find_Password_TextView_Warning.setText("가입하신 정보가 없습니다. 회원가입 후 이용해주시기 바랍니다.");
                        User_Find_Password_TextView_Warning.setVisibility(View.VISIBLE);
                        Phone_flag = false;
                    } else {
                        Random random = new Random();
                        rnd = Math.abs(random.nextInt(899999) + 100000);
                        Log.i("aaaabb",String.valueOf(rnd));
                        String msg = "바스켓북 인증번호는 [" + String.valueOf(rnd) + "] 입니다.감사합니다.";
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault());
                        Date d = new Date();
                        String date = dateFormat.format(d);
                        user = new HttpClient();
                        user.HttpClient("InetSMSExample", "example.jsp", msg, Phone, Phone, date);
                        Phone_flag = true;
                        User_Find_Password_TextView_Warning.setText("입력한 번호로 전송된 인증번호를 입력해 주세요");
                        User_Find_Password_TextView_Warning.setVisibility(View.VISIBLE);
                    }
                } else {
                    Phone_flag = false;
                    User_Find_Password_TextView_Warning.setText("정확한 휴대전화번호를 입력해 주세요");
                    User_Find_Password_TextView_Warning.setVisibility(View.VISIBLE);
                }
            }
        });
        User_Find_Password_Button_Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Phone_flag) {
                    if (User_Find_Password_EditText_Confirm.getText().toString().equals(String.valueOf(rnd))) {
                        Snackbar.make(getCurrentFocus(), "인증번호가 확인되었습니다", Snackbar.LENGTH_SHORT).show();
                        Phone_Confirm_flag = true;
                        User_Find_Password_Button_Next.setBackgroundColor(getResources().getColor(R.color.MainColor1));
                        User_Find_Password_Button_Next.setTextColor(getResources().getColor(R.color.White));
                    }else {
                        Snackbar.make(getCurrentFocus(), "인증번호를 확인해 주세요", Snackbar.LENGTH_SHORT).show();
                    }
                }else {
                    Snackbar.make(getCurrentFocus(), "휴대전화번호를 확인해 주세요", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        User_Find_Password_Button_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Phone_Confirm_flag){
                    Intent intent1 = new Intent(Find_Password.this, Find_Password_Change.class);
                    intent1.putExtra("Phone",Phone);
                    startActivity(intent1);
                    finish();
                }else{
                    Snackbar.make(getCurrentFocus(), "핸드폰 인증 후 이용해주시기 바랍니다.", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        User_Find_Password_ImageView_Back.setOnClickListener(new View.OnClickListener() {
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
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
