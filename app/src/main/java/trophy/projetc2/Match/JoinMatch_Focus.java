package trophy.projetc2.Match;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import me.drakeet.materialdialog.MaterialDialog;
import trophy.projetc2.Http.HttpClient;
import trophy.projetc2.Navigation.TeamSearch_Focus;
import trophy.projetc2.R;
import trophy.projetc2.User.Login;

/**
 * Created by 박효근 on 2017-05-26.
 */

public class JoinMatch_Focus extends AppCompatActivity{
    TextView title1,title2,title3,title4,title5, title6;
    ImageView Match_Focus_ImageView_Back, Match_Focus_ImageView_Status, Match_Focus_ImageView_Emblem;
    TextView Match_Focus_TextView_TeamName, Match_Focus_TextView_Title, Match_Focus_TextView_Date, Match_Focus_TextView_Time,
            Match_Focus_TextView_Place, Match_Focus_TextView_Pay, Match_Focus_TextView_Color, Match_Focus_TextView_Extra;
    CheckBox Match_Focus_CheckBox_Parking_Not, Match_Focus_CheckBox_Parking_Free, Match_Focus_CheckBox_Parking_Charge,
            Match_Focus_CheckBox_Display, Match_Focus_CheckBox_Shower, Match_Focus_CheckBox_ColdHot;
    Button Match_Focus_Button_Apply;
    LinearLayout home;

    String strCurYear, strCurMonth, strCurDay, strCurHour,strCurMinute, strCurToday, strCurTime;
    String Match_Pk, User_Pk,Team_Pk, Time, Title, StartTime, MatchPlace,Emblem,TeamName, Match_User_Pk, Match_Date, FinishTime,
            Parking_Not, Parking_Free, Parking_Charge, Display, Shower, ColdHot, Status, Pay, Color, Extra, GameStatus;
    String[][] parsedData_Match_Focus, parsedData_succed, parsedData_JoinMatch_Focus_Joined;
    String OtherTeam_Phone="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_navigation_joinmatch_focus);

        final Intent intent1 = getIntent();
        Match_Pk = intent1.getStringExtra("Match_Pk");
        User_Pk = intent1.getStringExtra("User_Pk");
        Status = intent1.getStringExtra("Status");
        GameStatus = intent1.getStringExtra("GameStatus");

        HttpClient http_match_focus = new HttpClient();
        String result = http_match_focus.HttpClient("Trophy_part1","Match_Focus.jsp",Match_Pk);
        parsedData_Match_Focus = jsonParserList_Match(result);

        Match_Pk = parsedData_Match_Focus[0][0];Match_User_Pk = parsedData_Match_Focus[0][1];Team_Pk = parsedData_Match_Focus[0][2];
        Time = parsedData_Match_Focus[0][3];Title = parsedData_Match_Focus[0][4];StartTime = parsedData_Match_Focus[0][5];
        MatchPlace = parsedData_Match_Focus[0][6];Parking_Not = parsedData_Match_Focus[0][7];Parking_Free = parsedData_Match_Focus[0][8];
        Parking_Charge = parsedData_Match_Focus[0][9];Display = parsedData_Match_Focus[0][10];Shower = parsedData_Match_Focus[0][11];
        ColdHot = parsedData_Match_Focus[0][12];
        Emblem = parsedData_Match_Focus[0][14]; TeamName = parsedData_Match_Focus[0][15];
        Pay = parsedData_Match_Focus[0][16]; Color = parsedData_Match_Focus[0][17]; Extra = parsedData_Match_Focus[0][18];
        Match_Date = parsedData_Match_Focus[0][19];FinishTime = parsedData_Match_Focus[0][20];
        String str = parsedData_Match_Focus[0][19];
        String[] data = str.split(":::");
        String str1 = data[1];
        String[] MatchDate = str1.split(" / ");

        Match_Focus_ImageView_Back = (ImageView)findViewById(R.id.Match_Focus_ImageView_Back);
        Match_Focus_ImageView_Status = (ImageView)findViewById(R.id.Match_Focus_ImageView_Status);
        Match_Focus_ImageView_Emblem = (ImageView)findViewById(R.id.Match_Focus_ImageView_Emblem);
        Match_Focus_TextView_TeamName = (TextView)findViewById(R.id.Match_Focus_TextView_TeamName);
        Match_Focus_TextView_Title = (TextView)findViewById(R.id.Match_Focus_TextView_Title);
        Match_Focus_TextView_Date = (TextView)findViewById(R.id.Match_Focus_TextView_Date);
        Match_Focus_TextView_Time = (TextView)findViewById(R.id.Match_Focus_TextView_Time);
        Match_Focus_TextView_Place = (TextView)findViewById(R.id.Match_Focus_TextView_Place);
        Match_Focus_TextView_Pay = (TextView)findViewById(R.id.Match_Focus_TextView_Pay);
        Match_Focus_TextView_Color = (TextView)findViewById(R.id.Match_Focus_TextView_Color);
        Match_Focus_TextView_Extra = (TextView)findViewById(R.id.Match_Focus_TextView_Extra);
        Match_Focus_CheckBox_Parking_Not = (CheckBox)findViewById(R.id.Match_Focus_CheckBox_Parking_Not);
        Match_Focus_CheckBox_Parking_Free = (CheckBox)findViewById(R.id.Match_Focus_CheckBox_Parking_Free);
        Match_Focus_CheckBox_Parking_Charge = (CheckBox)findViewById(R.id.Match_Focus_CheckBox_Parking_Charge);
        Match_Focus_CheckBox_Display = (CheckBox)findViewById(R.id.Match_Focus_CheckBox_Display);
        Match_Focus_CheckBox_Shower = (CheckBox)findViewById(R.id.Match_Focus_CheckBox_Shower);
        Match_Focus_CheckBox_ColdHot = (CheckBox)findViewById(R.id.Match_Focus_CheckBox_ColdHot);
        Match_Focus_Button_Apply = (Button)findViewById(R.id.Match_Focus_Button_Apply);
        home = (LinearLayout)findViewById(R.id.home);

        title1 = (TextView)findViewById(R.id.title1);title2 = (TextView)findViewById(R.id.title2);title3 = (TextView)findViewById(R.id.title3);
        title4 = (TextView)findViewById(R.id.title4);title5 = (TextView)findViewById(R.id.title5);title6= (TextView)findViewById(R.id.title6);
        title1.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/BMJUA_ttf.ttf"));
        title2.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/BMJUA_ttf.ttf"));
        title3.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/BMJUA_ttf.ttf"));
        title4.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/BMJUA_ttf.ttf"));
        title5.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/BMJUA_ttf.ttf"));
        title6.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/BMJUA_ttf.ttf"));
        Match_Focus_TextView_Title.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/BMJUA_ttf.ttf"));
        Match_Focus_TextView_TeamName.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/BMJUA_ttf.ttf"));
        Match_Focus_CheckBox_Parking_Not.setClickable(false);Match_Focus_CheckBox_Parking_Free.setClickable(false);Match_Focus_CheckBox_Parking_Charge.setClickable(false);
        Match_Focus_CheckBox_Display.setClickable(false);Match_Focus_CheckBox_Shower.setClickable(false);Match_Focus_CheckBox_ColdHot.setClickable(false);

        if(Status.equals("Joining")){
            if(GameStatus.equals("After")||GameStatus.equals("Gameing")){
                Match_Focus_ImageView_Status.setImageResource(R.drawable.deadline);
                Match_Focus_Button_Apply.setText("삭제하기");
            }
            else{
                Match_Focus_ImageView_Status.setImageResource(R.drawable.joinmatch_joining);
                Match_Focus_Button_Apply.setText("신청취소");
            }

        }
        else if(Status.equals("refuse")){
            Match_Focus_ImageView_Status.setImageResource(R.drawable.joinmatch_refuse);
            Match_Focus_Button_Apply.setText("게시글 삭제");
//            Match_Focus_Button_Apply.setBackgroundColor(getResources().getColor(R.color.White));
//            Match_Focus_Button_Apply.setTextColor(getResources().getColor(R.color.Black));
//            Match_Focus_Button_Apply.setText("마 감");
        }
        else if(Status.equals("allow")){
            Match_Focus_ImageView_Status.setImageResource(R.drawable.joinmatch_allow);
            Match_Focus_Button_Apply.setVisibility(View.GONE);
            HttpClient http_joinmatch_focus_joined = new HttpClient();
            String result1 = http_joinmatch_focus_joined.HttpClient("Trophy_part1","JoinMatch_Focus_Joined.jsp", Match_Pk);
            parsedData_JoinMatch_Focus_Joined = jsonParserList_JoinMatch_Focus_Joined(result1);
            OtherTeam_Phone = parsedData_JoinMatch_Focus_Joined[0][4];
        }
        try {
            String En_Profile = URLEncoder.encode(Emblem, "utf-8");
            if (En_Profile.equals(".")) {
                Glide.with(JoinMatch_Focus.this).load(R.drawable.emblem).bitmapTransform(new CropCircleTransformation(Glide.get(JoinMatch_Focus.this).getBitmapPool()))
                        .into(Match_Focus_ImageView_Emblem);
            } else {
                Glide.with(JoinMatch_Focus.this).load("http://210.122.7.193:8080/Trophy_img/team/" + En_Profile + ".jpg").bitmapTransform(new CropCircleTransformation(Glide.get(JoinMatch_Focus.this).getBitmapPool()))
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(Match_Focus_ImageView_Emblem);
            }
        } catch (UnsupportedEncodingException e) {

        }
        Match_Focus_ImageView_Emblem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JoinMatch_Focus.this,TeamSearch_Focus.class);
                intent.putExtra("TeamName", TeamName);
                intent.putExtra("User_Pk",User_Pk);
                intent.putExtra("Team_Pk",Team_Pk);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
            }
        });

        Match_Focus_TextView_TeamName.setText(TeamName);
        Match_Focus_TextView_Title.setText(Title);
        Match_Focus_TextView_Date.setText(MatchDate[0] + "월  " + MatchDate[1] + "일(" + DayoftheWeek(data[0]+MatchDate[0]+MatchDate[1]) + ")");
        Match_Focus_TextView_Time.setText(time_changestr(StartTime) + " ~ " + time_changestr(FinishTime));
        Match_Focus_TextView_Place.setText(MatchPlace);
        Match_Focus_TextView_Pay.setText(Pay);
        Match_Focus_TextView_Color.setText(Color);
        Match_Focus_TextView_Extra.setText(Extra);

        if(Parking_Not.equals("true")){
            Match_Focus_CheckBox_Parking_Not.setChecked(true);
        }else{
            Match_Focus_CheckBox_Parking_Not.setChecked(false);
        }
        if(Parking_Free.equals("true")){
            Match_Focus_CheckBox_Parking_Free.setChecked(true);
        }else{
            Match_Focus_CheckBox_Parking_Free.setChecked(false);
        }
        if(Parking_Charge.equals("true")){
            Match_Focus_CheckBox_Parking_Charge.setChecked(true);
        }else{
            Match_Focus_CheckBox_Parking_Charge.setChecked(false);
        }
        if(Display.equals("true")){
            Match_Focus_CheckBox_Display.setChecked(true);
        }else{
            Match_Focus_CheckBox_Display.setChecked(false);
        }
        if(Shower.equals("true")){
            Match_Focus_CheckBox_Shower.setChecked(true);
        }else{
            Match_Focus_CheckBox_Shower.setChecked(false);
        }
        if(ColdHot.equals("true")){
            Match_Focus_CheckBox_ColdHot.setChecked(true);
        }else{
            Match_Focus_CheckBox_ColdHot.setChecked(false);
        }

        Match_Focus_Button_Apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Status.equals("Joining")) {
                    if(GameStatus.equals("After")||GameStatus.equals("Gameing")){
                        HttpClient http = new HttpClient();
                        String result = http.HttpClient("Trophy_part1", "JoinMatch_Focus_Delete.jsp", Match_Pk, User_Pk);
                        parsedData_succed = jsonParserList_Succed(result);
                        if (parsedData_succed[0][0].equals("succed")) {
                            Snackbar.make(view, "삭제되었습니다.", Snackbar.LENGTH_INDEFINITE).setAction("확인", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    finish();
                                    overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
                                }
                            }).show();
                        }
                    }
                    else{
                        HttpClient http = new HttpClient();
                        String result = http.HttpClient("Trophy_part1", "JoinMatch_Focus_Cancel.jsp", Match_Pk, User_Pk);
                        parsedData_succed = jsonParserList_Succed(result);
                        if (parsedData_succed[0][0].equals("succed")) {
                            Snackbar.make(view, "신청 취소되었습니다.", Snackbar.LENGTH_INDEFINITE).setAction("확인", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    finish();
                                    overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
                                }
                            }).show();
                        }
                    }
                } else if (Status.equals("refuse")) {
                    HttpClient http = new HttpClient();
                    String result = http.HttpClient("Trophy_part1", "JoinMatch_Focus_Delete.jsp", Match_Pk, User_Pk);
                    parsedData_succed = jsonParserList_Succed(result);
                    if (parsedData_succed[0][0].equals("succed")) {
                        Snackbar.make(view, "삭제되었습니다.", Snackbar.LENGTH_INDEFINITE).setAction("확인", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                finish();
                                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
                            }
                        }).show();
                    }
                } else if (Status.equals("allow")) {
//                    Match_Focus_ImageView_Status.setImageResource(R.drawable.joinmatch_allow);
                    //Match_Focus_Button_Apply.setText("게시글 삭제");
//            Match_Focus_Button_Apply.setBackgroundColor(getResources().getColor(R.color.White));
//            Match_Focus_Button_Apply.setTextColor(getResources().getColor(R.color.Black));
//            Match_Focus_Button_Apply.setText("마 감");
                }
            }
        });

        Match_Focus_ImageView_Back.setOnClickListener(new View.OnClickListener() {
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
    public String[][] jsonParserList_Match(String pRecvServerPage){
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try{
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");
            String[] jsonName = {"msg1","msg2","msg3","msg4","msg5","msg6","msg7","msg8","msg9","msg10","msg11","msg12","msg13","msg14","msg15","msg16","msg17","msg18","msg19", "msg20","msg21"};
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
    public String[][] jsonParserList_JoinMatch_Focus_Joined(String pRecvServerPage){
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try{
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");
            String[] jsonName = {"msg1","msg2","msg3","msg4","msg5"};
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
    public String[][] jsonParserList_Succed(String pRecvServerPage){
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
                    day = "목";
                    break;
                case 2:
                    day = "금";
                    break;
                case 3:
                    day = "토";
                    break;
                case 4:
                    day = "일";
                    break;
                case 5:
                    day = "월";
                    break;
                case 6:
                    day = "화";
                    break;
                case 7:
                    day = "수";
                    break;

            }
        }catch (ParseException e){

        }
        return day;
    }
}

