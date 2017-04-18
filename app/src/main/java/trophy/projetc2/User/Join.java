package trophy.projetc2.User;

import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.drakeet.materialdialog.MaterialDialog;
import trophy.projetc2.BaseActivity;
import trophy.projetc2.Get_Spinner_Si;
import trophy.projetc2.Http.HttpClient;
import trophy.projetc2.R;

import static android.view.View.GONE;
import static trophy.projetc2.User.Terms.activity_terms;

/**
 * Created by ldong on 2017-02-01.
 */

public class Join extends BaseActivity {
    ImageView User_Join_ImageView_Back;
    EditText Join_EditText_Name, Join_EditText_Password, Join_EditText_Password_Confirm, Join_EditText_Birth_Year, Join_EditText_Birth_Month, Join_EditText_Birth_Day, Join_EditText_Phone, Join_EditText_Phone_Confirm;
    CheckBox Join_CheckBox_SexM, Join_CheckBox_SexW;
    Button Join_Button_Phone, Join_Button_Phone_Confirm;
    LinearLayout Join_LinearLayout_Join;
    Spinner Join_Spinner_AddressDo, Join_Spinner_AddressSi;
    TextView Join_TextView_Name_Warning, Join_TextView_Password_Warning, Join_TextView_Password_Confirm_Warning, Join_TextView_Birth_Warning, Join_TextView_Sex_Warning, Join_TextView_Phone_Warning;
    String Year, Month, Day, Name, Password, Birth, Address_Do, Address_Si, Sex="남자", Phone;
    ArrayAdapter<CharSequence> adspin1, adspin2;
    trophy.projetc2.Get_Spinner_Si Get_Spinner_Si;
    int rnd;
    boolean Name_flag = false;
    boolean Password_flag = false;
    boolean Password_Confirm_flag = false;
    boolean Year_flag = false;
    boolean Month_flag = false;
    boolean Day_flag = false;
    boolean Sex_flag = true;
    boolean Phone_flag = false;
    boolean Phone_Confirm_flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_user_join);

        User_Join_ImageView_Back = (ImageView)findViewById(R.id.User_Join_ImageView_Back);
        Join_EditText_Name = (EditText) findViewById(R.id.Join_EditText_Name);
        Join_EditText_Password = (EditText) findViewById(R.id.Join_EditText_Password);
        Join_EditText_Password_Confirm = (EditText) findViewById(R.id.Join_EditText_Password_Confirm);
        Join_EditText_Birth_Year = (EditText) findViewById(R.id.Join_EditText_Birth_Year);
        Join_EditText_Birth_Month = (EditText) findViewById(R.id.Join_EditText_Birth_Month);
        Join_EditText_Birth_Day = (EditText) findViewById(R.id.Join_EditText_Birth_Day);
        Join_EditText_Phone = (EditText) findViewById(R.id.Join_EditText_Phone);
        Join_EditText_Phone_Confirm = (EditText) findViewById(R.id.Join_EditText_Phone_Confirm);
        Join_CheckBox_SexM = (CheckBox) findViewById(R.id.Join_CheckBox_SexM);
        Join_CheckBox_SexW = (CheckBox) findViewById(R.id.Join_CheckBox_SexW);
        Join_Button_Phone = (Button) findViewById(R.id.Join_Button_Phone);
        Join_Button_Phone_Confirm = (Button) findViewById(R.id.Join_Button_Phone_Confirm);
        Join_LinearLayout_Join = (LinearLayout) findViewById(R.id.Join_LinearLayout_Join);
        Join_Spinner_AddressDo = (Spinner) findViewById(R.id.Join_Spinner_AddressDo);
        Join_Spinner_AddressSi = (Spinner) findViewById(R.id.Join_Spinner_AddressSi);
        Join_TextView_Name_Warning = (TextView) findViewById(R.id.Join_TextView_Name_Warning);
        Join_TextView_Password_Warning = (TextView) findViewById(R.id.Join_TextView_Password_Warning);
        Join_TextView_Password_Confirm_Warning = (TextView) findViewById(R.id.Join_TextView_Password_Confirm_Warning);
        Join_TextView_Birth_Warning = (TextView)  findViewById(R.id.Join_TextView_Birth_Warning);
        Join_TextView_Sex_Warning = (TextView) findViewById(R.id.Join_TextView_Sex_Warning);
        Join_TextView_Phone_Warning = (TextView) findViewById(R.id.Join_TextView_Phone_Warning);

        Join_CheckBox_SexM.setChecked(true);

        Join_TextView_Name_Warning.setVisibility(GONE);
        Join_TextView_Password_Warning.setVisibility(GONE);
        Join_TextView_Password_Confirm_Warning.setVisibility(GONE);
        Join_TextView_Sex_Warning.setVisibility(GONE);
        Join_TextView_Birth_Warning.setVisibility(GONE);
        Join_TextView_Sex_Warning.setVisibility(GONE);
        Join_TextView_Phone_Warning.setVisibility(GONE);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        adspin1 = ArrayAdapter.createFromResource(getApplicationContext(), R.array.spinner_do,R.layout.spinner_style);
        adspin1.setDropDownViewResource(R.layout.spinner_style);
        Join_Spinner_AddressDo.setAdapter(adspin1);
        adspin2 = ArrayAdapter.createFromResource(getApplicationContext(), R.array.spinner_do_seoul, R.layout.spinner_style);
        adspin2.setDropDownViewResource(R.layout.spinner_style);
        Join_Spinner_AddressSi.setAdapter(adspin2);


        Join_EditText_Name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (Join_EditText_Name.getText().toString().equals("")) {
                    Join_TextView_Name_Warning.setVisibility(View.VISIBLE);
                    Name_flag = false;
                } else {
                    Join_TextView_Name_Warning.setVisibility(GONE);
                    Name_flag = true;
                    Name = Join_EditText_Name.getText().toString();
                }
            }
        });

        Join_EditText_Password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String passwd = passwordValidator(Join_EditText_Password.getText().toString());
                if (passwd.equals("success")) {
                    Join_TextView_Password_Warning.setVisibility(GONE);
                    Password_flag = true;
                    Password = Join_EditText_Password.getText().toString();
                } else {
                    Join_TextView_Password_Warning.setText(passwd);
                    Join_TextView_Password_Warning.setVisibility(View.VISIBLE);
                    Password_flag = false;
                }
            }
        });

        Join_EditText_Password_Confirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (Join_EditText_Password_Confirm.getText().toString().equals(Join_EditText_Password.getText().toString())) {
                    Join_TextView_Password_Confirm_Warning.setVisibility(GONE);
                    Password_Confirm_flag = true;
                } else {
                    Join_TextView_Password_Confirm_Warning.setVisibility(View.VISIBLE);
                    Password_Confirm_flag = false;
                }
            }
        });

        Join_EditText_Birth_Year.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (Join_EditText_Birth_Year.length() == 4) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(Join_EditText_Birth_Year.getWindowToken(), 0);
                    if ((Integer.parseInt(Join_EditText_Birth_Year.getText().toString()) > 1800) && (Integer.parseInt(Join_EditText_Birth_Year.getText().toString()) < 2017)) {
                        Year_flag = true;
                        Join_TextView_Birth_Warning.setVisibility(GONE);
                        Year = Join_EditText_Birth_Year.getText().toString();
                    } else {
                        Year_flag = false;
                        Join_TextView_Birth_Warning.setVisibility(View.VISIBLE);
                    }
                } else {
                    Year_flag = false;
                    Join_TextView_Birth_Warning.setVisibility(View.VISIBLE);
                }
            }
        });

        Join_EditText_Birth_Month.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (Join_EditText_Birth_Month.length() == 2) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(Join_EditText_Birth_Month.getWindowToken(), 0);
                }
                if (Join_EditText_Birth_Month.length() > 0) {
                    if ((Integer.parseInt(Join_EditText_Birth_Month.getText().toString()) > 0) && (Integer.parseInt(Join_EditText_Birth_Month.getText().toString()) < 13)) {
                        Month_flag = true;
                        Join_TextView_Birth_Warning.setVisibility(GONE);
                        Month = Join_EditText_Birth_Month.getText().toString();
                        if(Month.length() == 1){
                            Month = "0"+Month;
                        }
                    } else {
                        Month_flag = false;
                        Join_TextView_Birth_Warning.setVisibility(View.VISIBLE);
                    }
                } else {
                    Month_flag = false;
                    Join_TextView_Birth_Warning.setVisibility(View.VISIBLE);
                }
            }
        });

        Join_EditText_Birth_Day.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (Join_EditText_Birth_Day.length() == 2) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(Join_EditText_Birth_Day.getWindowToken(), 0);
                }
                if (Join_EditText_Birth_Day.length() > 0) {
                    if ((Integer.parseInt(Join_EditText_Birth_Day.getText().toString()) > 0) && (Integer.parseInt(Join_EditText_Birth_Day.getText().toString()) < 32)) {
                        Day_flag = true;
                        Join_TextView_Birth_Warning.setVisibility(GONE);
                        Day = Join_EditText_Birth_Day.getText().toString();
                    } else {
                        Day_flag = false;
                        Join_TextView_Birth_Warning.setVisibility(View.VISIBLE);
                    }
                } else {
                    Day_flag = false;
                    Join_TextView_Birth_Warning.setVisibility(View.VISIBLE);
                }
            }
        });


        Join_Spinner_AddressDo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Get_Spinner_Si = new Get_Spinner_Si(Join.this);
                adspin2 = Get_Spinner_Si.getAdapter(position);
                Join_Spinner_AddressSi.setAdapter(adspin2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



        Join_CheckBox_SexM.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Join_CheckBox_SexW.setChecked(false);
                    Join_TextView_Sex_Warning.setVisibility(GONE);
                    Sex = "남자";
                    Sex_flag = true;
                    Log.i("Sex : ", Sex);
                } else if (!Join_CheckBox_SexW.isChecked()) {
                    Join_TextView_Sex_Warning.setVisibility(View.VISIBLE);
                    Sex = "false";
                    Sex_flag = false;
                    Log.i("Sex : ", Sex);
                }
            }
        });

        Join_CheckBox_SexW.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Join_CheckBox_SexM.setChecked(false);
                    Join_TextView_Sex_Warning.setVisibility(GONE);
                    Sex = "여자";
                    Sex_flag = true;
                    Log.i("Sex : ", Sex);
                } else if (!Join_CheckBox_SexM.isChecked()) {
                    Join_TextView_Sex_Warning.setVisibility(View.VISIBLE);
                    Sex = "false";
                    Sex_flag = false;
                    Log.i("Sex : ", Sex);
                }
            }
        });

        Join_Button_Phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(Join_EditText_Phone.getWindowToken(), 0);
                Phone = Join_EditText_Phone.getText().toString();
                if (Phone.length() == 11 && Phone.substring(0,3).equals("010")) {
                    HttpClient user = new HttpClient();
                    String result = user.HttpClient("Trophy_part2", "Phone_Confirm.jsp",Phone);
                    String[][] parseData = jsonParserList(result);
                    if (parseData[0][0].toString().equals("not existent")) {
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
                        Join_TextView_Phone_Warning.setText("입력한 번호로 전송된 인증번호를 입력해 주세요");
                        Join_TextView_Phone_Warning.setVisibility(View.VISIBLE);
                    } else {
                        Phone_flag = false;
                        Join_TextView_Phone_Warning.setText("이미 가입된 번호가 있습니다");
                        Join_TextView_Phone_Warning.setVisibility(View.VISIBLE);
                    }
                } else {
                    Phone_flag = false;
                    Join_TextView_Phone_Warning.setText("정확한 휴대전화번호를 입력해 주세요");
                    Join_TextView_Phone_Warning.setVisibility(View.VISIBLE);
                }
            }
        });

        Join_Button_Phone_Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Phone_flag) {
                    if (Join_EditText_Phone_Confirm.getText().toString().equals(String.valueOf(rnd))) {
                        Snackbar.make(getCurrentFocus(), "인증번호가 확인되었습니다", Snackbar.LENGTH_SHORT).show();
                        Phone_Confirm_flag = true;
                    }else {
                        Snackbar.make(getCurrentFocus(), "인증번호를 확인해 주세요", Snackbar.LENGTH_SHORT).show();
                    }
                }else {
                    Snackbar.make(getCurrentFocus(), "휴대전화번호를 확인해 주세요", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        Join_LinearLayout_Join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Address_Do = Join_Spinner_AddressDo.getSelectedItem().toString();
                Address_Si = Join_Spinner_AddressSi.getSelectedItem().toString();
                Birth = Year + " / " + Month + " / " + Day;
                if (Name_flag && Password_flag && Password_Confirm_flag && Year_flag && Month_flag && Day_flag && Sex_flag && Phone_flag && Phone_Confirm_flag) {
                    HttpClient user = new HttpClient();
                    user.HttpClient("Trophy_part1", "User_Join.jsp", Name, Password, Birth, Sex, Address_Do, Address_Si, Phone);
                    final MaterialDialog TeamPlayerDialog = new MaterialDialog(Join.this);
                    TeamPlayerDialog
                            .setTitle("확인")
                            .setMessage("회원가입이 완료되었습니다.")
                            .setCanceledOnTouchOutside(true)
                            .setPositiveButton("확인", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    finish();
                                    activity_terms.finish();
                                    overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
                                }
                            });
                    TeamPlayerDialog.show();
                } else {
                    Snackbar.make(v, "입력하신 정보를 확인해주세요", Snackbar.LENGTH_LONG).show();
                }
            }
        });
        User_Join_ImageView_Back.setOnClickListener(new View.OnClickListener() {
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

    public String ChangeAge(String Age){
        Calendar cal= Calendar.getInstance ();
        String[] str = new String(Age).split(" \\/ ");
        String[] str_day = new String(str[2]).split(" ");
        int year = Integer.parseInt(str[0]);
        int month = Integer.parseInt(str[1]);
        int day = Integer.parseInt(str_day[0]);

        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month-1);
        cal.set(Calendar.DATE, day);

        Calendar now = Calendar.getInstance ();

        int age = now.get(Calendar.YEAR) - cal.get(Calendar.YEAR);
        if (  (cal.get(Calendar.MONTH) > now.get(Calendar.MONTH))
                || (    cal.get(Calendar.MONTH) == now.get(Calendar.MONTH)
                && cal.get(Calendar.DAY_OF_MONTH) > now.get(Calendar.DAY_OF_MONTH)   )
                ){
            age--;
        }
        String Str_age = Integer.toString(age);
        return Str_age;
    }

    public String passwordValidator(String passwd){
        String returnValue = "success";
//.*[!,@,#,$,%,^,&amp;,*,?,_,~])|([!,@,#,$,%,^,&amp;,*,?,_,~].*[a-zA-Z0-9]
        Pattern p = Pattern.compile("([a-zA-Z].*[0-9])|([0-9].*[a-zA-Z])");
        Matcher m = p.matcher(passwd);

        Pattern p2 = Pattern.compile("(\\w)\\1\\1\\1");
        Matcher m2 = p2.matcher(passwd);

        Pattern p3 = Pattern.compile("([\\{\\}\\[\\]\\/?.,;:|\\)*~`!^\\-_+&lt;&gt;@\\#$%&amp;\\\\\\=\\(\\'\\\"])\\1\\1\\1");
        Matcher m3 = p3.matcher(passwd);

        if(spaceCheck(passwd)){	//패스워드 공백 문자열 체크
            returnValue = "비밀번호에 공백문자를 허용하지 않습니다.";
        }else if(passwd.length() < 8 || passwd.length() > 16){	//자릿수 검증
            returnValue = "비밀번호는 8자 이상, 16자 이하로 구성하세요.";
        }else if(!m.find()){	//정규식 이용한 패턴 체크
            returnValue = "비밀번호는 영문,숫자를 조합하여 8~16자로 구성하세요.";
        }else if(m2.find() || m3.find()){	// 동일 문자 4번 입력 시 패턴 체크
            returnValue = "비밀번호에 동일문자를 4번 이상 사용할 수 없습니다.";
        }

        return returnValue;
    }

    /**
     * 공백 문자 체크
     *
     * @param spaceCheck
     * @return
     */
    public boolean spaceCheck(String spaceCheck){
        for(int i = 0 ; i < spaceCheck.length() ; i++) {
            if(spaceCheck.charAt(i) == ' ')
                return true;
        }

        return false;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
    }
}
