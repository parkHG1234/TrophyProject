package trophy.projetc2.User;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import trophy.projetc2.Get_Spinner_Si;
import trophy.projetc2.Http.HttpClient;
import trophy.projetc2.R;

/**
 * Created by 박지훈 on 2016-11-30.
 */

public class MemberShip extends AppCompatActivity {

    LinearLayout MemberShip_Layout_Root;
    EditText MemberShip_EditText_Name;
    EditText MemberShip_EditText_PW;
    EditText MemberShip_EditText_Confirm_PW;
    EditText MemberShip_EditText_Birth_Year;
    EditText MemberShip_EditText_Birth_Month;
    EditText MemberShip_EditText_Birth_Day;
    RadioGroup MemberShip_RadioGroup_Sex_RadioGroup;
    RadioButton MemberShip_RadioButton_Sex_Radio_male;
    RadioButton MemberShip_RadioButton_Sex_Radio_Female;
    Spinner MemberShip_Spinner_Do;
    Spinner MemberShip_Spinner_Si;
    EditText MemberShip_EditText_Phone;
    Button MemberShip_Button_Phone;
    TextView MemberShip_TextView_Count;
    TextView MemberShip_TextView_Time;
    EditText MemberShip_EditText_Confirm_Phone;
    Button MemberShip_Button_Confirm_Phone;
    Button MemberShip_Button_MemberShip;
    trophy.projetc2.Get_Spinner_Si Get_Spinner_Si;
    ArrayAdapter<CharSequence> adspin1, adspin2;
    static TimerTask myTask;
    static Timer timer;
    int rnd;
    int cnt = 3;
    int time = 0;
    boolean Name_flag = false;
    boolean Password_flag = false;
    boolean Password_Confirm_flag = false;
    boolean Year_flag = false;
    boolean Month_flag = false;
    boolean Day_flag = false;
    boolean Sex_flag = false;
    boolean Phone_flag = false;
    boolean Phone_Confirm_flag = false;

    String Name;
    String Password;
    String Year;
    String Month;
    String Day;
    String Sex = null;
    String Address_Do;
    String Address_Si;
    String Phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_membership);

        MemberShip_Layout_Root = (LinearLayout) findViewById(R.id.MemberShip_Layout_Root);
        MemberShip_EditText_Name = (EditText) findViewById(R.id.MemberShip_EditText_Name);
        MemberShip_EditText_PW = (EditText) findViewById(R.id.MemberShip_EditText_PW);
        MemberShip_EditText_Confirm_PW = (EditText) findViewById(R.id.MemberShip_EditText_Confirm_PW);
        MemberShip_EditText_Birth_Year = (EditText) findViewById(R.id.MemberShip_EditText_Birth_Year);
        MemberShip_EditText_Birth_Month = (EditText) findViewById(R.id.MemberShip_EditText_Birth_Month);
        MemberShip_EditText_Birth_Day = (EditText) findViewById(R.id.MemberShip_EditText_Birth_Day);
        MemberShip_RadioGroup_Sex_RadioGroup = (RadioGroup) findViewById(R.id.MemberShip_RadioGroup_Sex_RadioGroup);
        MemberShip_RadioButton_Sex_Radio_male = (RadioButton) findViewById(R.id.MemberShip_RadioButton_Sex_Radio_male);
        MemberShip_RadioButton_Sex_Radio_Female = (RadioButton) findViewById(R.id.MemberShip_RadioButton_Sex_Radio_Female);
        MemberShip_Spinner_Do = (Spinner) findViewById(R.id.MemberShip_Spinner_Do);
        MemberShip_Spinner_Si = (Spinner) findViewById(R.id.MemberShip_Spinner_Si);
        MemberShip_EditText_Phone = (EditText) findViewById(R.id.MemberShip_EditText_Phone);
        MemberShip_Button_Phone = (Button) findViewById(R.id.MemberShip_Button_Phone);
        MemberShip_TextView_Count = (TextView)findViewById(R.id.MemberShip_TextView_Count);
        MemberShip_TextView_Time = (TextView)findViewById(R.id.MemberShip_TextView_Time);
        MemberShip_EditText_Confirm_Phone = (EditText) findViewById(R.id.MemberShip_EditText_Confirm_Phone);
        MemberShip_Button_Confirm_Phone = (Button) findViewById(R.id.MemberShip_Button_Confirm_Phone);
        MemberShip_Button_MemberShip = (Button) findViewById(R.id.MemberShip_Button_MemberShip);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        final BackThread thread = new BackThread();


        adspin1 = ArrayAdapter.createFromResource(getApplicationContext(), R.array.spinner_do, android.R.layout.simple_spinner_item);
        adspin1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        MemberShip_Spinner_Do.setAdapter(adspin1);
        adspin2 = ArrayAdapter.createFromResource(getApplicationContext(), R.array.spinner_do_seoul, android.R.layout.simple_spinner_item);
        adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        MemberShip_Spinner_Si.setAdapter(adspin2);

        MemberShip_EditText_Name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                Name_flag = true;
                Name = MemberShip_EditText_Name.getText().toString();
            }
        });

        MemberShip_EditText_PW.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Password_flag = true;
                Password = MemberShip_EditText_PW.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        MemberShip_EditText_Confirm_PW.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Password.equals(MemberShip_EditText_Confirm_PW.getText().toString())) {
                    Password_Confirm_flag = true;
                    MemberShip_EditText_Confirm_PW.setHintTextColor(Color.parseColor("#008000"));
                } else {
                    if(Password.length()==MemberShip_EditText_Confirm_PW.getText().length()) {
                        Snackbar.make(getCurrentFocus(), "비밀번호를확인해주세요", Snackbar.LENGTH_SHORT).show();
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(MemberShip_EditText_Confirm_PW.getWindowToken(), 0);
                    }
                    Password_Confirm_flag = false;
                    MemberShip_EditText_Confirm_PW.setHintTextColor(Color.parseColor("#ff0000"));
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        MemberShip_EditText_Birth_Year.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (MemberShip_EditText_Birth_Year.length() == 4) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(MemberShip_EditText_Birth_Year.getWindowToken(), 0);
                    if ((Integer.parseInt(MemberShip_EditText_Birth_Year.getText().toString()) > 1900) && (Integer.parseInt(MemberShip_EditText_Birth_Year.getText().toString()) < 2018)) {
                        Year_flag = true;
                        Year = MemberShip_EditText_Birth_Year.getText().toString();
                    } else {
                        Year_flag = false;
                        Snackbar.make(getCurrentFocus(), "다시입력해주세요", Snackbar.LENGTH_SHORT).show();
                    }
                }
            }
        });

        MemberShip_EditText_Birth_Month.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (MemberShip_EditText_Birth_Month.length() == 2) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(MemberShip_EditText_Birth_Month.getWindowToken(), 0);
                    if ((Integer.parseInt(MemberShip_EditText_Birth_Month.getText().toString()) > 0) && (Integer.parseInt(MemberShip_EditText_Birth_Month.getText().toString()) < 13)) {
                        Month_flag = true;
                        Month = MemberShip_EditText_Birth_Month.getText().toString();
                    } else {
                        Month_flag = false;
                        Snackbar.make(getCurrentFocus(), "다시입력해주세요", Snackbar.LENGTH_SHORT).show();
                    }
                }
            }
        });

        MemberShip_EditText_Birth_Day.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (MemberShip_EditText_Birth_Day.length() == 2) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(MemberShip_EditText_Birth_Day.getWindowToken(), 0);
                    if ((Integer.parseInt(MemberShip_EditText_Birth_Day.getText().toString()) > 0) && (Integer.parseInt(MemberShip_EditText_Birth_Day.getText().toString()) < 32)) {
                        Day_flag = true;
                        Day = MemberShip_EditText_Birth_Day.getText().toString();
                    } else {
                        Day_flag = false;
                        Snackbar.make(getCurrentFocus(), "다시입력해주세요", Snackbar.LENGTH_SHORT).show();
                    }
                }
            }
        });

        MemberShip_RadioGroup_Sex_RadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (String.valueOf(MemberShip_RadioGroup_Sex_RadioGroup.getCheckedRadioButtonId()).equals("2131558537")) {
                    Sex_flag = true;
                    Sex = "M";
                } else if (String.valueOf(MemberShip_RadioGroup_Sex_RadioGroup.getCheckedRadioButtonId()).equals("2131558538")) {
                    Sex_flag = true;
                    Sex = "W";
                }
            }
        });

        MemberShip_Spinner_Do.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Get_Spinner_Si = new Get_Spinner_Si(MemberShip.this);
                adspin2 = Get_Spinner_Si.getAdapter(position);
                MemberShip_Spinner_Si.setAdapter(adspin2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



        MemberShip_Button_Phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(MemberShip_EditText_Phone.getWindowToken(), 0);
                Phone = MemberShip_EditText_Phone.getText().toString();
                HttpClient user = new HttpClient();
                String result = user.HttpClient("Trophy_part2", "Phone_Confirm.jsp",Phone);
                String[][] parseData = jsonParserList(result);
                if (parseData[0][0].toString().equals("not existent")) {
                    String phone = MemberShip_EditText_Phone.getText().toString();
                    if (phone.length() == 11) {
                        Random random = new Random();
                        rnd = Math.abs(random.nextInt(899999) + 100000);
                        Log.i("aaaabb",String.valueOf(rnd));
                        String msg = "바스켓북 인증번호는 [" + String.valueOf(rnd) + "] 입니다.감사합니다.";
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault());
                        Date d = new Date();
                        String date = dateFormat.format(d);
                        user = new HttpClient();
                        //user.HttpClient("InetSMSExample", "example.jsp", msg, phone, phone, date);
                        Phone_flag = true;

                        cnt=3;
                        MemberShip_TextView_Count.setText("남은횟수 : " + String.valueOf(cnt));

                        myTask = new TimerTask() {
                            int i = 300;
                            public void run() {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        // 해당 작업을 처리함
                                        MemberShip_TextView_Time.setText("남은시간 : " + i);
                                    }
                                });
                                i--;
                                //시간이 초과된 경우 game 내 데이터 삭제 및 초기화.
                                if (i == 0) {
                                    timer.cancel();
                                    Snackbar.make(getCurrentFocus(), "인증시간이초과되었습니다", Snackbar.LENGTH_SHORT).show();
                                }
                            }
                        };
                        timer = new Timer();
                        timer.schedule(myTask, 0, 1000); // 5초후 첫실행, 1초마다 계속실행


                    } else {
                        Phone_flag = false;
                        Snackbar.make(getCurrentFocus(), "핸드폰번호를확인해주세요", Snackbar.LENGTH_SHORT).show();
                    }
                }else{
                    Snackbar.make(getCurrentFocus(), "이미등록된핸드폰번호입니다", Snackbar.LENGTH_SHORT).show();
                }
            }
        });



        MemberShip_Button_Confirm_Phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(MemberShip_EditText_Confirm_Phone.getWindowToken(), 0);

                cnt--;
                if (cnt == 0) {
                    Snackbar.make(getCurrentFocus(), "처음부터 다시 시도해주세요.", Snackbar.LENGTH_SHORT).show();
                    finish();
                }
                MemberShip_TextView_Count.setText("남은횟수 : " + String.valueOf(cnt));
                if (MemberShip_EditText_Confirm_Phone.getText().toString().equals(String.valueOf(rnd))) {
                    Snackbar.make(getCurrentFocus(), "인증번호가확인되었습니다", Snackbar.LENGTH_SHORT).show();
                    Phone_Confirm_flag = true;
                } else {
                    Snackbar.make(getCurrentFocus(), "인증번호를확인해주세요", Snackbar.LENGTH_SHORT).show();
                    Phone_Confirm_flag = false;
                }
            }
        });

        MemberShip_Button_MemberShip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Address_Do = MemberShip_Spinner_Do.getSelectedItem().toString();
                Address_Si = MemberShip_Spinner_Si.getSelectedItem().toString();
                if (Name_flag && Password_flag && Password_Confirm_flag && Year_flag && Month_flag && Day_flag && Sex_flag && Phone_flag && Phone_Confirm_flag) {
                    String Birth = Year + " / " + Month + " / " + Day;
                    HttpClient user = new HttpClient();
                    user.HttpClient("Trophy_part2", "MemberShip.jsp", Name, Password, Birth, Sex, Address_Do, Address_Si, Phone);
                } else {
                    Snackbar.make(getCurrentFocus(), "입력하신정보를확인해주세요", Snackbar.LENGTH_SHORT).show();
                }
                finish();
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

    class BackThread extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(1000);
                myTask.cancel();
                timer.cancel();
                finish();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } // end run()
    }
}
