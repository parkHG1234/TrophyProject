package trophy.projetc2.Match;

import android.content.Intent;
import android.graphics.Typeface;
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
 * Created by 박효근 on 2017-04-13.
 */

public class Match_Focus extends AppCompatActivity {
    TextView title1,title2,title3,title4,title5, title6;
    ImageView Match_Focus_ImageView_Back, Match_Focus_ImageView_Status, Match_Focus_ImageView_Emblem;
    TextView Match_Focus_TextView_TeamName, Match_Focus_TextView_Title, Match_Focus_TextView_Date, Match_Focus_TextView_Time,
            Match_Focus_TextView_Place, Match_Focus_TextView_Pay, Match_Focus_TextView_Color, Match_Focus_TextView_Extra;
    CheckBox Match_Focus_CheckBox_Parking_Not, Match_Focus_CheckBox_Parking_Free, Match_Focus_CheckBox_Parking_Charge,
            Match_Focus_CheckBox_Display, Match_Focus_CheckBox_Shower, Match_Focus_CheckBox_ColdHot;
    Button Match_Focus_Button_Apply;
    LinearLayout home;
    String strCurYear, strCurMonth, strCurDay, strCurHour,strCurMinute, strCurToday, strCurTime;
    String Match_Pk, Team_Pk, User_Pk, Time, Title, StartTime, FinishTime,MatchPlace,Emblem,TeamName, Match_User_Pk, Match_Date,
            Parking_Not, Parking_Free, Parking_Charge, Display, Shower, ColdHot, Status, Pay, Color, Extra, MyTeam_Pk;
    String Away_Emblem;
    String Away_TeamName;
    String Away_Team_Pk;
    String[][] parsedData_Match_Focus, parsedData_Match_Focus_Join, parsedData_Match_Focus_Overlap, parsedData_Match_Focus_Joined;
    String[][] parsedData_User;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_navigation_match_focus);
        currentTime();
        final Intent intent1 = getIntent();
        Match_Pk = intent1.getStringExtra("Match_Pk");
        User_Pk = intent1.getStringExtra("User_Pk");
        MyTeam_Pk = intent1.getStringExtra("MyTeam_Pk");
        Status = intent1.getStringExtra("Status");

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
        Match_Date = parsedData_Match_Focus[0][19];
        FinishTime = parsedData_Match_Focus[0][20];
        String str = parsedData_Match_Focus[0][19];

        String[] data = str.split(":::");
        Match_Date = data[0] + " / " + data[1];
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

        //우리팀 정보
        HttpClient http_user= new HttpClient();
        String result1 = http_user.HttpClient("Trophy_part1","User.jsp", User_Pk);
        parsedData_User = jsonParserList_User(result1);
        Away_TeamName = parsedData_User[0][1];

        if(Status.equals("recruiting")){
            Match_Focus_ImageView_Status.setImageResource(R.drawable.recruiting);
            Match_Focus_Button_Apply.setBackgroundColor(getResources().getColor(R.color.main1color));
            Match_Focus_Button_Apply.setTextColor(getResources().getColor(R.color.White));
        }
        else if(Status.equals("finish")){
            Match_Focus_ImageView_Status.setImageResource(R.drawable.deadline);
            Match_Focus_Button_Apply.setBackgroundColor(getResources().getColor(R.color.BlackGray));
            Match_Focus_Button_Apply.setTextColor(getResources().getColor(R.color.White));
            Match_Focus_Button_Apply.setText("마 감");
        }

        try {
            String En_Profile = URLEncoder.encode(Emblem, "utf-8");
            if (En_Profile.equals(".")) {
                Glide.with(Match_Focus.this).load(R.drawable.emblem).bitmapTransform(new CropCircleTransformation(Glide.get(Match_Focus.this).getBitmapPool()))
                        .into(Match_Focus_ImageView_Emblem);
            } else {
                Glide.with(Match_Focus.this).load("http://210.122.7.193:8080/Trophy_img/team/" + En_Profile + ".jpg").bitmapTransform(new CropCircleTransformation(Glide.get(Match_Focus.this).getBitmapPool()))
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(Match_Focus_ImageView_Emblem);
            }
        } catch (UnsupportedEncodingException e) {

        }
        Match_Focus_ImageView_Emblem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Match_Focus.this,TeamSearch_Focus.class);
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
                if(User_Pk.equals(".")){
                    Intent intent_login = new Intent(Match_Focus.this, Login.class);
                    startActivity(intent_login);
                    overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                }
                else if(MyTeam_Pk.equals(".")){
                    Snackbar.make(view,"팀 가입 후 이용해주시기 바랍니다.",Snackbar.LENGTH_SHORT).show();
                }
                else{
                    HttpClient http_match_focus = new HttpClient();
                    String result = http_match_focus.HttpClient("Trophy_part1","Match_Focus_Overlap.jsp",User_Pk,Match_Pk,MyTeam_Pk);
                    parsedData_Match_Focus_Overlap = jsonParserList_Match_Focus_Overlap(result);
                    if(parsedData_Match_Focus_Overlap[0][0].equals("overlap")){
                        Snackbar.make(view,"이미 신청하셨습니다.",Snackbar.LENGTH_SHORT).show();
                    }
                    else if(parsedData_Match_Focus_Overlap[0][0].equals("sameteam")){
                        Snackbar.make(view,"소속된 팀의 게시글입니다.",Snackbar.LENGTH_SHORT).show();
                    }
                    else{
                        if(Status.equals("recruiting")){
                            LayoutInflater inflater = (LayoutInflater)view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
                            final View layout = inflater.inflate(R.layout.layout_navigation_match_focus_customdialog_memo, (ViewGroup) view.findViewById(R.id.TeamInfo_Customdialog_1_Root));
                            final TextView TeamInfo_Customdialog_1_Title = (TextView)layout.findViewById(R.id.TeamInfo_Customdialog_1_Title);
                            final ImageView TeamInfo_Customdialog_1_Back = (ImageView)layout.findViewById(R.id.TeamInfo_Customdialog_1_Back);
                            final EditText TeamInfo_Customdialog_1_Content = (EditText)layout.findViewById(R.id.TeamInfo_Customdialog_1_Content);
                            final Button TeamInfo_Customdialog_1_Ok = (Button)layout.findViewById(R.id.TeamInfo_Customdialog_1_Ok);
                            TeamInfo_Customdialog_1_Title.setText("시합 신청");
                            final MaterialDialog TeamInfo_Dialog = new MaterialDialog(view.getContext());
                            TeamInfo_Dialog
                                    .setContentView(layout)
                                    .setCanceledOnTouchOutside(true);
                            TeamInfo_Dialog.show();
                            TeamInfo_Customdialog_1_Back.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    TeamInfo_Dialog.dismiss();
                                }
                            });
                            TeamInfo_Customdialog_1_Ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    HttpClient http_match_focus_overlap = new HttpClient();
                                    String result1 = http_match_focus_overlap.HttpClient("Trophy_part1","Match_Focus_Join.jsp", User_Pk, Match_Pk, strCurToday +":::"+strCurTime, TeamInfo_Customdialog_1_Content.getText().toString());
                                    parsedData_Match_Focus_Join = jsonParserList_Match_Focus_Join(result1);
                                    if(parsedData_Match_Focus_Join[0][0].equals("succed")){
                                        TeamInfo_Dialog.setCanceledOnTouchOutside(false);
                                        Snackbar.make(view,"신청되었습니다.",Snackbar.LENGTH_INDEFINITE).setAction("확인", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                HttpClient http_match_focus_overlap = new HttpClient();
                                                http_match_focus_overlap.HttpClient("TodayBasket_manager","push.jsp", Match_User_Pk, Away_TeamName+"팀이 시합요청 하였습니다");
                                                TeamInfo_Dialog.dismiss();
                                            }
                                        })
                                                .show();
                                    }
                                    else{
                                        Snackbar.make(view,"잠시 후 다시 시도해주세요.",Snackbar.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                        else if(Status.equals("finish")){
                            Snackbar.make(view,"신청이 마감되었습니다.",Snackbar.LENGTH_SHORT).show();
                        }
                    }
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
    public String[][] jsonParserList_Match_Focus_Join(String pRecvServerPage){
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
    public String[][] jsonParserList_Match_Focus_Overlap(String pRecvServerPage){
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
    public String[][] jsonParserList_Match_Focus_Joined(String pRecvServerPage){
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try{
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");
            String[] jsonName = {"msg1","msg2","msg3","msg4","msg5","msg6","msg7","msg8","msg9"};
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
    public String[][] jsonParserList_User(String pRecvServerPage){
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try{
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");
            String[] jsonName = {"msg1","msg2","msg3","msg4","msg5","msg6","msg7"};
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

}
