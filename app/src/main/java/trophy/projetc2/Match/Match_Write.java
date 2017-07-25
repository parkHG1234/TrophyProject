package trophy.projetc2.Match;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import trophy.projetc2.Http.HttpClient;
import trophy.projetc2.MainActivity;
import trophy.projetc2.Navigation.TeamInfo;
import trophy.projetc2.R;

/**
 * Created by 박효근 on 2017-04-04.
 */

public class Match_Write extends AppCompatActivity {
    TextView title1,title2,title3,title4,title5, title6, Match_Write_TextView_ExtraCount;
    ImageView Match_Write_ImageView_Back, Match_Write_ImageView_Emblem;
    TextView Match_Write_ImageView_TeamName;
    EditText Match_Write_EditText_StartTime, Match_Write_EditText_FinishTime, Match_Write_EditText_Place,Match_Write_EditText_Pay,
            Match_Write_EditText_Color,Match_Write_EditText_Extra,
            Match_Write_EditText_Date;
    CheckBox Match_Write_CheckBox_Parking_Not, Match_Write_CheckBox_Parking_Free, Match_Write_CheckBox_Parking_Charge,
            Match_Write_CheckBox_Display,Match_Write_CheckBox_Shower,Match_Write_CheckBox_ColdHot;
    Button Match_Write_Button_Write;
    String strCurYear, strCurMonth, strCurDay, strCurHour,strCurMinute, strCurToday, strCurTime;
    String Title, MatchTime, MatchPlace, Pay, Color, Extra, MatchDate, MatchMonth, MatchDay,MatchStartTime,MatchFinishTime,
            Parking_Not="false", Parking_Free="false", Parking_Charge="false", Display="false", Shower="false", ColdHot="false";
    String[][] parsedData_Match, parseredData_teamInfo;
    String Team_Pk, Team_Duty, User_Pk, Emblem, TeamName;
    boolean timepicker_start= false;boolean timepicker_finsih= false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_navigation_match_write);

        Intent intent1 = getIntent();
        User_Pk = intent1.getStringExtra("User_Pk");
        Team_Pk = intent1.getStringExtra("Team_Pk");
        Team_Duty = intent1.getStringExtra("Team_Duty");

        Match_Write_ImageView_Emblem = (ImageView)findViewById(R.id.Match_Write_ImageView_Emblem);
        Match_Write_ImageView_TeamName = (TextView)findViewById(R.id.Match_Write_ImageView_TeamName);

        title1 = (TextView)findViewById(R.id.title1);title2 = (TextView)findViewById(R.id.title2);title3 = (TextView)findViewById(R.id.title3);
        title4 = (TextView)findViewById(R.id.title4);title5 = (TextView)findViewById(R.id.title5);title6= (TextView)findViewById(R.id.title6);
        title1.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/BMJUA_ttf.ttf"));
        title2.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/BMJUA_ttf.ttf"));
        title3.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/BMJUA_ttf.ttf"));
        title4.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/BMJUA_ttf.ttf"));
        title5.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/BMJUA_ttf.ttf"));
        title6.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/BMJUA_ttf.ttf"));

        Match_Write_ImageView_Back = (ImageView)findViewById(R.id.Match_Write_ImageView_Back);
        Match_Write_EditText_Date = (EditText)findViewById(R.id.Match_Write_EditText_Date);
        Match_Write_EditText_StartTime = (EditText)findViewById(R.id.Match_Write_EditText_StartTime);
        Match_Write_EditText_FinishTime = (EditText)findViewById(R.id.Match_Write_EditText_FinishTime);
        Match_Write_EditText_Place = (EditText)findViewById(R.id.Match_Write_EditText_Place);
        Match_Write_EditText_Pay = (EditText)findViewById(R.id.Match_Write_EditText_Pay);
        Match_Write_EditText_Color = (EditText)findViewById(R.id.Match_Write_EditText_Color);
        Match_Write_EditText_Extra = (EditText)findViewById(R.id.Match_Write_EditText_Extra);
        Match_Write_TextView_ExtraCount = (TextView)findViewById(R.id.Match_Write_TextView_ExtraCount);
        Match_Write_CheckBox_Parking_Not = (CheckBox)findViewById(R.id.Match_Write_CheckBox_Parking_Not);
        Match_Write_CheckBox_Parking_Free = (CheckBox)findViewById(R.id.Match_Write_CheckBox_Parking_Free);
        Match_Write_CheckBox_Parking_Charge = (CheckBox)findViewById(R.id.Match_Write_CheckBox_Parking_Charge);
        Match_Write_CheckBox_Display = (CheckBox)findViewById(R.id.Match_Write_CheckBox_Display);
        Match_Write_CheckBox_Shower = (CheckBox)findViewById(R.id.Match_Write_CheckBox_Shower);
        Match_Write_CheckBox_ColdHot = (CheckBox)findViewById(R.id.Match_Write_CheckBox_ColdHot);
        Match_Write_Button_Write = (Button)findViewById(R.id.Match_Write_Button_Write);

        currentTime();

        HttpClient TeamInfo = new HttpClient();
        String result1 =TeamInfo.HttpClient("Trophy_part1","Match_Write_TeamInfo.jsp",Team_Pk);
        parseredData_teamInfo =  jsonParserList_getTeamInfo(result1);

        Emblem = parseredData_teamInfo[0][1];TeamName = parseredData_teamInfo[0][0];
        Match_Write_ImageView_TeamName.setText(TeamName);

        if(Emblem.equals(".")) {
            Glide.with(Match_Write.this).load(R.drawable.emblem).diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(Match_Write_ImageView_Emblem);
        }else {
            Glide.with(Match_Write.this).load("http://210.122.7.193:8080/Trophy_img/team/" + Emblem + ".jpg").bitmapTransform(new CropCircleTransformation(Glide.get(Match_Write.this).getBitmapPool()))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(Match_Write_ImageView_Emblem);
        }

        Match_Write_EditText_Date.setFocusableInTouchMode(false);
        Match_Write_EditText_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(Match_Write.this, listener1, Integer.parseInt(strCurYear), Integer.parseInt(strCurMonth)-1, Integer.parseInt(strCurDay));
                dialog.show();
            }
        });
        Match_Write_EditText_StartTime.setFocusableInTouchMode(false);
        Match_Write_EditText_StartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timepicker_start = true;
                TimePickerDialog dialog = new TimePickerDialog(Match_Write.this, listener, 12, 00, false);
                dialog.show();
            }
        });
        Match_Write_EditText_FinishTime.setFocusableInTouchMode(false);
        Match_Write_EditText_FinishTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timepicker_finsih = true;
                TimePickerDialog dialog = new TimePickerDialog(Match_Write.this, listener, 12, 00, false);
                dialog.show();
            }
        });
        Match_Write_EditText_Place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_login = new Intent(Match_Write.this, Match_Write_Place.class);
                startActivity(intent_login);
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
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
                    Match_Write_Button_Write.setBackgroundColor(getResources().getColor(R.color.main1color));
                    Match_Write_Button_Write.setTextColor(getResources().getColor(R.color.White));
                }
                else{
                    Match_Write_Button_Write.setBackgroundColor(getResources().getColor(R.color.soso));
                   // Match_Write_Button_Write.setTextColor(getResources().getColor(R.color.Black));
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
                    Match_Write_Button_Write.setBackgroundColor(getResources().getColor(R.color.main1color));
                    Match_Write_Button_Write.setTextColor(getResources().getColor(R.color.White));
                }
                else{
                    Match_Write_Button_Write.setBackgroundColor(getResources().getColor(R.color.soso));
                    //Match_Write_Button_Write.setTextColor(getResources().getColor(R.color.Black));
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
                    Match_Write_Button_Write.setBackgroundColor(getResources().getColor(R.color.main1color));
                    Match_Write_Button_Write.setTextColor(getResources().getColor(R.color.White));
                }
                else{
                    Match_Write_Button_Write.setBackgroundColor(getResources().getColor(R.color.soso));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        Match_Write_EditText_Extra.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Match_Write_TextView_ExtraCount.setText(Match_Write_EditText_Extra.length() +"/200");
                if(Match_Write_EditText_Extra.length() ==200){
                    Match_Write_TextView_ExtraCount.setTextColor(getResources().getColor(R.color.Red));
                }
                else{
                    Match_Write_TextView_ExtraCount.setTextColor(getResources().getColor(R.color.Black));
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

                    //MatchTime = Match_Write_EditText_StartTime.getText().toString();
                    MatchPlace = Match_Write_EditText_Place.getText().toString();
                    Pay = Match_Write_EditText_Pay.getText().toString();
                    Color = Match_Write_EditText_Color.getText().toString();
                    Extra = Match_Write_EditText_Extra.getText().toString();
                    Log.i("MatchDate", MatchDate);
                    checkbox_IsChecked();
                    HttpClient http_match_write = new HttpClient();
                    String result = http_match_write.HttpClient("Trophy_part1","Match_Write.jsp",
                            User_Pk, Team_Pk, strCurToday +":::"+strCurTime, Title, MatchStartTime, MatchFinishTime, MatchPlace, Pay, Color, Extra, Parking_Not, Parking_Free, Parking_Charge, Display, Shower, ColdHot, MatchDate);
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
            if(Match_Write_EditText_StartTime.getText().toString().equals("")){
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
                            if(Match_Write_EditText_Date.getText().toString().equals("")){
                                return "failed";
                            }else{
                                if(Match_Write_EditText_FinishTime.getText().toString().equals("")){
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
    public String button_EditText_NotNull(View view){
            if(Match_Write_EditText_StartTime.getText().toString().equals("")){
                Snackbar.make(view,"경기 시작시간을 입력해주시기 바랍니다.",Snackbar.LENGTH_SHORT).show();
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
                            if(Match_Write_EditText_Date.getText().toString().equals("")){
                                Snackbar.make(view,"시합 날짜를 입력해주시기 바랍니다.",Snackbar.LENGTH_SHORT).show();
                                return "failed";
                            }else{
                                if(Match_Write_EditText_FinishTime.getText().toString().equals("")){
                                    Snackbar.make(view,"경기 종료시간을 입력해주시기 바랍니다.",Snackbar.LENGTH_SHORT).show();
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
    public String DayoftheWeek(String date){
        String day="월";
        try{
            String inputDate = date;
            DateFormat df =new SimpleDateFormat("yyyyMMDD");
            Date date1 = df.parse(inputDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date1);
            switch (calendar.get(Calendar.DAY_OF_WEEK)) {
                case 1:
                    day = "토";
                    break;
                case 2:
                    day = "일";
                    break;
                case 3:
                    day = "월";
                    break;
                case 4:
                    day = "화";
                    break;
                case 5:
                    day = "수";
                    break;
                case 6:
                    day = "목";
                    break;
                case 7:
                    day = "금";
                    break;

            }
        }catch (ParseException e){

        }
        return day;
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
    public String[][] jsonParserList_getTeamInfo(String pRecvServerPage){
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try{
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");
            String[] jsonName = {"msg1","msg2"};
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
    private TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            if(timepicker_start == true){
                MatchStartTime = Integer.toString(hourOfDay) + ":" + Integer.toString(minute);
                Match_Write_EditText_StartTime.setText(time_changestr(MatchStartTime));
                timepicker_start = false;
            }
            if(timepicker_finsih == true){
                MatchFinishTime = Integer.toString(hourOfDay) + ":" + Integer.toString(minute);
                Match_Write_EditText_FinishTime.setText(time_changestr(MatchFinishTime));
                timepicker_finsih = false;
            }

            String result_null = edittext_EditText_NotNull();
            if(result_null.equals("succed")){
                Match_Write_Button_Write.setBackgroundColor(getResources().getColor(R.color.main1color));
                Match_Write_Button_Write.setTextColor(getResources().getColor(R.color.White));
            }
            else{
                Match_Write_Button_Write.setBackgroundColor(getResources().getColor(R.color.soso));
            }
        }
    };
    private DatePickerDialog.OnDateSetListener listener1 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            view.setMinDate(20170716);
            if(monthOfYear+1 < 10){
                MatchMonth = "0"+Integer.toString(monthOfYear+1);
            }
            else{
                MatchMonth = Integer.toString(monthOfYear+1);
            }
            if(dayOfMonth < 10){
                MatchDay = "0"+Integer.toString(dayOfMonth);
            }
            else{
                MatchDay = Integer.toString(dayOfMonth);
            }
            MatchDate = year +":::"+ MatchMonth +" / "+ dayOfMonth;
            String dayoftheweek = DayoftheWeek(Integer.toString(year) +  MatchMonth + Integer.toString(dayOfMonth));
            Title = MatchMonth + "월 " + dayOfMonth + "일(" + dayoftheweek + ") 교류전 팀 구합니다." ;
            Match_Write_EditText_Date.setText(MatchMonth + "월  " + dayOfMonth + "일(" + dayoftheweek + ")");
            String result_null = edittext_EditText_NotNull();

            if(result_null.equals("succed")){
                Match_Write_Button_Write.setBackgroundColor(getResources().getColor(R.color.main1color));
                Match_Write_Button_Write.setTextColor(getResources().getColor(R.color.White));
            }
            else{
                Match_Write_Button_Write.setBackgroundColor(getResources().getColor(R.color.soso));
            }
        }
    };
    public String time_changestr(String time){
        String str = time;
        String[] data = str.split(":");
        if(Integer.parseInt(data[0])>12){
            return "오후 " +Integer.toString(Integer.parseInt(data[0])-12)+"시 "+Integer.parseInt(data[1])+"분";
        }
        else{
            return "오전 " +Integer.toString(Integer.parseInt(data[0]))+"시 "+Integer.parseInt(data[1])+"분";
        }
    }
}
