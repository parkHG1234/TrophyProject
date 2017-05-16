package trophy.projetc2.Match;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import trophy.projetc2.Http.HttpClient;
import trophy.projetc2.R;

/**
 * Created by 박효근 on 2017-04-04.
 */

public class Match_Write extends AppCompatActivity {
    ImageView Match_Write_ImageView_Back;
    EditText Match_Write_EditText_Title,Match_Write_EditText_Time,Match_Write_EditText_Place,Match_Write_EditText_Pay,
            Match_Write_EditText_Color,Match_Write_EditText_Extra,
            Match_Write_EditText_Year, Match_Write_EditText_Month, Match_Write_EditText_Day;
    CheckBox Match_Write_CheckBox_Parking_Not, Match_Write_CheckBox_Parking_Free, Match_Write_CheckBox_Parking_Charge,
            Match_Write_CheckBox_Display,Match_Write_CheckBox_Shower,Match_Write_CheckBox_ColdHot;
    Button Match_Write_Button_Write;
    String strCurYear, strCurMonth, strCurDay, strCurHour,strCurMinute, strCurToday, strCurTime;
    String Title, MatchTime, MatchPlace, Pay, Color, Extra, MatchDate,
            Parking_Not="false", Parking_Free="false", Parking_Charge="false", Display="false", Shower="false", ColdHot="false";
    String[][] parsedData_Match;
    String Team_Pk, Team_Duty, User_Pk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_navigation_match_write);

        Intent intent1 = getIntent();
        User_Pk = intent1.getStringExtra("User_Pk");
        Team_Pk = intent1.getStringExtra("Team_Pk");
        Team_Duty = intent1.getStringExtra("Team_Duty");

        Match_Write_ImageView_Back = (ImageView)findViewById(R.id.Match_Write_ImageView_Back);
        Match_Write_EditText_Title = (EditText)findViewById(R.id.Match_Write_EditText_Title);
        Match_Write_EditText_Year = (EditText)findViewById(R.id.Match_Write_EditText_Year);
        Match_Write_EditText_Month = (EditText)findViewById(R.id.Match_Write_EditText_Month);
        Match_Write_EditText_Day = (EditText)findViewById(R.id.Match_Write_EditText_Day);
        Match_Write_EditText_Time = (EditText)findViewById(R.id.Match_Write_EditText_Time);
        Match_Write_EditText_Place = (EditText)findViewById(R.id.Match_Write_EditText_Place);
        Match_Write_EditText_Pay = (EditText)findViewById(R.id.Match_Write_EditText_Pay);
        Match_Write_EditText_Color = (EditText)findViewById(R.id.Match_Write_EditText_Color);
        Match_Write_EditText_Extra = (EditText)findViewById(R.id.Match_Write_EditText_Extra);
        Match_Write_CheckBox_Parking_Not = (CheckBox)findViewById(R.id.Match_Write_CheckBox_Parking_Not);
        Match_Write_CheckBox_Parking_Free = (CheckBox)findViewById(R.id.Match_Write_CheckBox_Parking_Free);
        Match_Write_CheckBox_Parking_Charge = (CheckBox)findViewById(R.id.Match_Write_CheckBox_Parking_Charge);
        Match_Write_CheckBox_Display = (CheckBox)findViewById(R.id.Match_Write_CheckBox_Display);
        Match_Write_CheckBox_Shower = (CheckBox)findViewById(R.id.Match_Write_CheckBox_Shower);
        Match_Write_CheckBox_ColdHot = (CheckBox)findViewById(R.id.Match_Write_CheckBox_ColdHot);
        Match_Write_Button_Write = (Button)findViewById(R.id.Match_Write_Button_Write);

        currentTime();
        Match_Write_EditText_Year.setText(strCurYear);
        Match_Write_EditText_Month.setText(strCurMonth);
        Match_Write_EditText_Day.setText(strCurDay);
        Match_Write_EditText_Title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String result_null = edittext_EditText_NotNull();
                if(result_null.equals("succed")){
                    Match_Write_Button_Write.setBackgroundColor(getResources().getColor(R.color.MainColor1));
                    Match_Write_Button_Write.setTextColor(getResources().getColor(R.color.White));
                }
                else{
                    Match_Write_Button_Write.setBackgroundColor(getResources().getColor(R.color.White));
                    Match_Write_Button_Write.setTextColor(getResources().getColor(R.color.Black));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        Match_Write_EditText_Time.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String result_null = edittext_EditText_NotNull();
                if(result_null.equals("succed")){
                    Match_Write_Button_Write.setBackgroundColor(getResources().getColor(R.color.MainColor1));
                    Match_Write_Button_Write.setTextColor(getResources().getColor(R.color.White));
                }
                else{
                    Match_Write_Button_Write.setBackgroundColor(getResources().getColor(R.color.White));
                    Match_Write_Button_Write.setTextColor(getResources().getColor(R.color.Black));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        Match_Write_EditText_Place.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String result_null = edittext_EditText_NotNull();
                if(result_null.equals("succed")){
                    Match_Write_Button_Write.setBackgroundColor(getResources().getColor(R.color.MainColor1));
                    Match_Write_Button_Write.setTextColor(getResources().getColor(R.color.White));
                }
                else{
                    Match_Write_Button_Write.setBackgroundColor(getResources().getColor(R.color.White));
                    Match_Write_Button_Write.setTextColor(getResources().getColor(R.color.Black));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        Match_Write_EditText_Pay.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String result_null = edittext_EditText_NotNull();
                if(result_null.equals("succed")){
                    Match_Write_Button_Write.setBackgroundColor(getResources().getColor(R.color.MainColor1));
                    Match_Write_Button_Write.setTextColor(getResources().getColor(R.color.White));
                }
                else{
                    Match_Write_Button_Write.setBackgroundColor(getResources().getColor(R.color.White));
                    Match_Write_Button_Write.setTextColor(getResources().getColor(R.color.Black));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        Match_Write_EditText_Color.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String result_null = edittext_EditText_NotNull();
                if(result_null.equals("succed")){
                    Match_Write_Button_Write.setBackgroundColor(getResources().getColor(R.color.MainColor1));
                    Match_Write_Button_Write.setTextColor(getResources().getColor(R.color.White));
                }
                else{
                    Match_Write_Button_Write.setBackgroundColor(getResources().getColor(R.color.White));
                    Match_Write_Button_Write.setTextColor(getResources().getColor(R.color.Black));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        Match_Write_Button_Write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String result_null = button_EditText_NotNull(view);
                if(result_null.equals("succed")){
                    Title = Match_Write_EditText_Title.getText().toString();
                    MatchTime = Match_Write_EditText_Time.getText().toString();
                    MatchPlace = Match_Write_EditText_Place.getText().toString();
                    Pay = Match_Write_EditText_Pay.getText().toString();
                    Color = Match_Write_EditText_Color.getText().toString();
                    Extra = Match_Write_EditText_Extra.getText().toString();
                    MatchDate = Match_Write_EditText_Year.getText().toString() +":::"+ Match_Write_EditText_Month.getText().toString() +" / "+ Match_Write_EditText_Day.getText().toString();
                    Log.i("MatchDate", MatchDate);
                    checkbox_IsChecked();
                    HttpClient http_match_write = new HttpClient();
                    String result = http_match_write.HttpClient("Trophy_part1","Match_Write.jsp",
                            User_Pk, Team_Pk, strCurToday +":::"+strCurTime, Title, MatchTime, MatchPlace, Pay, Color, Extra, Parking_Not, Parking_Free, Parking_Charge, Display, Shower, ColdHot, MatchDate);
                    parsedData_Match = jsonParserList_Match_Write(result);
                    if(parsedData_Match[0][0].equals("succed")){
                        finish();
                        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
                    }
                }
            }
        });
        Match_Write_ImageView_Back.setOnClickListener(new View.OnClickListener() {
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
    public void checkbox_IsChecked(){
        if(Match_Write_CheckBox_Parking_Not.isChecked()){
            Parking_Not = "true";
        }else{
            Parking_Not = "false";
        }
        if(Match_Write_CheckBox_Parking_Free.isChecked()){
            Parking_Free = "true";
        }else{
            Parking_Free = "false";
        }
        if(Match_Write_CheckBox_Parking_Charge.isChecked()){
            Parking_Charge = "true";
        }else{
            Parking_Charge = "false";
        }
        if(Match_Write_CheckBox_Display.isChecked()){
            Display = "true";
        }else{
            Display = "false";
        }
        if(Match_Write_CheckBox_Shower.isChecked()){
            Shower = "true";
        }else{
            Shower = "false";
        }
        if(Match_Write_CheckBox_ColdHot.isChecked()){
            ColdHot = "true";
        }else{
            ColdHot = "false";
        }
    }
    public String edittext_EditText_NotNull(){
        if(Match_Write_EditText_Title.getText().toString().equals("")){
            return "failed";
        }else{
            if(Match_Write_EditText_Time.getText().toString().equals("")){
                return "failed";
            }else{
                if(Match_Write_EditText_Place.getText().toString().equals("")){
                    return "failed";
                }else{
                    if(Match_Write_EditText_Pay.getText().toString().equals("")){
                        return "failed";
                    }else{
                        if(Match_Write_EditText_Color.getText().toString().equals("")){
                            return "failed";
                        }else{
                            if(Match_Write_EditText_Year.getText().toString().equals("")){
                                return "failed";
                            }else{
                                if(Match_Write_EditText_Month.getText().toString().equals("")){
                                    return "failed";
                                }else{
                                    if(Match_Write_EditText_Day.getText().toString().equals("")){
                                        return "failed";
                                    }else{
                                        return "succed";
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    public String button_EditText_NotNull(View view){
        if(Match_Write_EditText_Title.getText().toString().equals("")){
            Snackbar.make(view,"제목을 입력해주시기 바랍니다.",Snackbar.LENGTH_SHORT).show();
            return "failed";
        }else{
            if(Match_Write_EditText_Time.getText().toString().equals("")){
                Snackbar.make(view,"일시을 입력해주시기 바랍니다.",Snackbar.LENGTH_SHORT).show();
                return "failed";
            }else{
                if(Match_Write_EditText_Place.getText().toString().equals("")){
                    Snackbar.make(view,"장소를 입력해주시기 바랍니다.",Snackbar.LENGTH_SHORT).show();
                    return "failed";
                }else{
                    if(Match_Write_EditText_Pay.getText().toString().equals("")){
                        Snackbar.make(view,"비용을 입력해주시기 바랍니다.",Snackbar.LENGTH_SHORT).show();
                        return "failed";
                    }else{
                        if(Match_Write_EditText_Color.getText().toString().equals("")){
                            Snackbar.make(view,"유니폼색을 입력해주시기 바랍니다.",Snackbar.LENGTH_SHORT).show();
                            return "failed";
                        }else{
                            if(Match_Write_EditText_Year.getText().toString().equals("")){
                                Snackbar.make(view,"시합 일정을 입력해주시기 바랍니다.",Snackbar.LENGTH_SHORT).show();
                                return "failed";
                            }else{
                                if(Match_Write_EditText_Month.getText().toString().equals("")){
                                    Snackbar.make(view,"시합 일정을 입력해주시기 바랍니다.",Snackbar.LENGTH_SHORT).show();
                                    return "failed";
                                }else{
                                    if(Match_Write_EditText_Day.getText().toString().equals("")){
                                        Snackbar.make(view,"시합 일정를 입력해주시기 바랍니다.",Snackbar.LENGTH_SHORT).show();
                                        return "failed";
                                    }else{
                                        return "succed";
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    public void currentTime(){
        long now = System.currentTimeMillis();
// 현재 시간을 저장 한다.
        Date date = new Date(now);
// 시간 포맷 지정
        SimpleDateFormat CurDateFormat = new SimpleDateFormat("yyyy / MM / dd");
        SimpleDateFormat CurTimeFormat = new SimpleDateFormat("HH : mm");
        SimpleDateFormat CurYearFormat = new SimpleDateFormat("yyyy");
        SimpleDateFormat CurMonthFormat = new SimpleDateFormat("MM");
        SimpleDateFormat CurDayFormat = new SimpleDateFormat("dd");
        SimpleDateFormat CurHourFormat = new SimpleDateFormat("HH");
        SimpleDateFormat CurMinuteFormat = new SimpleDateFormat("mm");
// 지정된 포맷으로 String 타입 리턴
        strCurToday = CurDateFormat.format(date);
        strCurTime = CurTimeFormat.format(date);
        strCurYear = CurYearFormat.format(date);
        strCurYear = CurYearFormat.format(date);
        strCurMonth = CurMonthFormat.format(date);
        strCurDay = CurDayFormat.format(date);
        strCurHour = CurHourFormat.format(date);
        strCurMinute = CurMinuteFormat.format(date);
    }
    public String[][] jsonParserList_Match_Write(String pRecvServerPage){
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try{
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");
            String[] jsonName = {"msg1"};
            String[][] parseredData = new String[jArr.length()][jsonName.length];
            for(int i = 0; i<jArr.length();i++){
                json = jArr.getJSONObject(i);
                for (int j=0;j<jsonName.length; j++){
                    parseredData[i][j] = json.getString(jsonName[j]);
                }
            }
            return parseredData;
        }catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }
}
