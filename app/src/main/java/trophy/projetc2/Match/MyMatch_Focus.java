package trophy.projetc2.Match;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import me.drakeet.materialdialog.MaterialDialog;
import trophy.projetc2.Http.HttpClient;
import trophy.projetc2.Navigation.TeamSearch_Focus;
import trophy.projetc2.R;

/**
 * Created by 박효근 on 2017-04-17.
 */

public class MyMatch_Focus extends AppCompatActivity {
    TextView title1,title2,title3,title4,title5, title6;
    ImageView Match_Focus_ImageView_Back, Match_Focus_ImageView_Emblem;
    static ImageView Match_Focus_ImageView_Status;
    TextView Match_Focus_TextView_TeamName, Match_Focus_TextView_Title, Match_Focus_TextView_Date, Match_Focus_TextView_Time,
            Match_Focus_TextView_Place, Match_Focus_TextView_Pay, Match_Focus_TextView_Color, Match_Focus_TextView_Extra, Match_Focus_ImageView_Delete;
    static LinearLayout home, away;
    static ImageView Match_Focus_ImageView_OpponentEmblem;
    static TextView Match_Focus_TextView_OpponentTeamName;
    static TextView vs;
    static Button MyMatch_Focus_Joined_ImageView_Phone;
    CheckBox Match_Focus_CheckBox_Parking_Not, Match_Focus_CheckBox_Parking_Free, Match_Focus_CheckBox_Parking_Charge,
            Match_Focus_CheckBox_Display, Match_Focus_CheckBox_Shower, Match_Focus_CheckBox_ColdHot;
    ListView MyMatch_Focus_ListView_JoinerList;
    static LinearLayout MyMatch_Focus_LinerLayout_Joining;
    String Match_Pk, Team_Pk, User_Pk, Time, Title, StartTime, MatchPlace,Emblem,TeamName, Match_User_Pk, FinishTime,
            Parking_Not, Parking_Free, Parking_Charge, Display, Shower, ColdHot, Status, Pay, Color, Extra;
    String Away_Emblem;
    String Away_TeamName;
    String Away_Team_Pk;
    String strCurYear, strCurMonth, strCurDay, strCurHour,strCurMinute, strCurToday, strCurTime;
    String[][] parsedData_Match_Focus, parsedData_MyMatch_Focus_Joiner, parseredData_Delete;
    static String OtherTeam_Phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_navigation_mymatch_focus);
        currentTime();
        Intent intent1 = getIntent();
        Match_Pk = intent1.getStringExtra("Match_Pk");
        User_Pk = intent1.getStringExtra("User_Pk");

        HttpClient http_match_focus = new HttpClient();
        String result = http_match_focus.HttpClient("Trophy_part1","Match_Focus.jsp",Match_Pk);
        parsedData_Match_Focus = jsonParserList_MyMatch(result);

        String str = parsedData_Match_Focus[0][19];
        String[] data = str.split(":::");
        String str1 = data[1];
        String[] MatchDate = str1.split(" / ");

        Match_Pk = parsedData_Match_Focus[0][0];Match_User_Pk = parsedData_Match_Focus[0][1];Team_Pk = parsedData_Match_Focus[0][2];
        Time = data[0];Title = parsedData_Match_Focus[0][4];StartTime = parsedData_Match_Focus[0][5];
        MatchPlace = parsedData_Match_Focus[0][6];Parking_Not = parsedData_Match_Focus[0][7];Parking_Free = parsedData_Match_Focus[0][8];
        Parking_Charge = parsedData_Match_Focus[0][9];Display = parsedData_Match_Focus[0][10];Shower = parsedData_Match_Focus[0][11];
        ColdHot = parsedData_Match_Focus[0][12];Status = parsedData_Match_Focus[0][13];
        Emblem = parsedData_Match_Focus[0][14]; TeamName = parsedData_Match_Focus[0][15];
        Pay = parsedData_Match_Focus[0][16]; Color = parsedData_Match_Focus[0][17]; Extra = parsedData_Match_Focus[0][18];
        FinishTime = parsedData_Match_Focus[0][20];

        Match_Focus_ImageView_Back = (ImageView)findViewById(R.id.Match_Focus_ImageView_Back);
        Match_Focus_ImageView_Status = (ImageView)findViewById(R.id.Match_Focus_ImageView_Status);
        Match_Focus_ImageView_Emblem = (ImageView)findViewById(R.id.Match_Focus_ImageView_Emblem);
        Match_Focus_ImageView_OpponentEmblem = (ImageView)findViewById(R.id.Match_Focus_ImageView_OpponentEmblem);
        Match_Focus_TextView_TeamName = (TextView)findViewById(R.id.Match_Focus_TextView_TeamName);
        Match_Focus_TextView_OpponentTeamName = (TextView)findViewById(R.id.Match_Focus_TextView_OpponentTeamName);
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
        MyMatch_Focus_ListView_JoinerList = (ListView)findViewById(R.id.MyMatch_Focus_ListView_JoinerList);
        Match_Focus_ImageView_Delete = (TextView) findViewById(R.id.Match_Focus_ImageView_Delete);
        home = (LinearLayout)findViewById(R.id.home);away = (LinearLayout)findViewById(R.id.away);

        title1 = (TextView)findViewById(R.id.title1);title2 = (TextView)findViewById(R.id.title2);title3 = (TextView)findViewById(R.id.title3);
        title4 = (TextView)findViewById(R.id.title4);title5 = (TextView)findViewById(R.id.title5);title6= (TextView)findViewById(R.id.title6);
        vs = (TextView)findViewById(R.id.vs);
        title1.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/BMJUA_ttf.ttf"));
        title2.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/BMJUA_ttf.ttf"));
        title3.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/BMJUA_ttf.ttf"));
        title4.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/BMJUA_ttf.ttf"));
        title5.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/BMJUA_ttf.ttf"));
        title6.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/BMJUA_ttf.ttf"));
        vs.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/BMJUA_ttf.ttf"));
        Match_Focus_TextView_Title.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/BMJUA_ttf.ttf"));
        Match_Focus_TextView_TeamName.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/BMJUA_ttf.ttf"));
        Match_Focus_TextView_OpponentTeamName.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/BMJUA_ttf.ttf"));

        Match_Focus_CheckBox_Parking_Not.setClickable(false);Match_Focus_CheckBox_Parking_Free.setClickable(false);Match_Focus_CheckBox_Parking_Charge.setClickable(false);
        Match_Focus_CheckBox_Display.setClickable(false);Match_Focus_CheckBox_Shower.setClickable(false);Match_Focus_CheckBox_ColdHot.setClickable(false);
        MyMatch_Focus_LinerLayout_Joining = (LinearLayout)findViewById(R.id.MyMatch_Focus_LinerLayout_Joining);

        MyMatch_Focus_Joined_ImageView_Phone = (Button)findViewById(R.id.MyMatch_Focus_Joined_ImageView_Phone);

        try {
            String En_Profile = URLEncoder.encode(Emblem, "utf-8");
            if (En_Profile.equals(".")) {
                Glide.with(MyMatch_Focus.this).load(R.drawable.emblem).bitmapTransform(new CropCircleTransformation(Glide.get(MyMatch_Focus.this).getBitmapPool()))
                        .into(Match_Focus_ImageView_Emblem);
            } else {
                Glide.with(MyMatch_Focus.this).load("http://210.122.7.193:8080/Trophy_img/team/" + En_Profile + ".jpg").bitmapTransform(new CropCircleTransformation(Glide.get(MyMatch_Focus.this).getBitmapPool()))
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(Match_Focus_ImageView_Emblem);
            }
        } catch (UnsupportedEncodingException e) {

        }

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
        Log.i("Match",Match_Pk);


        if(parsedData_Match_Focus[0][13].equals("recruiting")){
            Match_Focus_ImageView_Status.setImageResource(R.drawable.recruiting);
            MyMatch_Focus_LinerLayout_Joining.setVisibility(View.VISIBLE);

            HttpClient http_mymatch_focus_joiner = new HttpClient();
            String result1 = http_mymatch_focus_joiner.HttpClient("Trophy_part1","MyMatch_Focus_Joiner.jsp", Match_Pk);
            parsedData_MyMatch_Focus_Joiner = jsonParserList_MyMatch_Focus_Join(result1);

            final ArrayList<MyMatch_Focus_Joiner_MyData> MyMatch_Focus_Joiner_MyData;
            MyMatch_Focus_Joiner_MyData = new ArrayList<MyMatch_Focus_Joiner_MyData>();
            for (int i = 0; i < parsedData_MyMatch_Focus_Joiner.length; i++) {
                MyMatch_Focus_Joiner_MyData.add(new MyMatch_Focus_Joiner_MyData(parsedData_MyMatch_Focus_Joiner[i][0], parsedData_MyMatch_Focus_Joiner[i][1], parsedData_MyMatch_Focus_Joiner[i][2],parsedData_MyMatch_Focus_Joiner[i][3],parsedData_MyMatch_Focus_Joiner[i][4],parsedData_MyMatch_Focus_Joiner[i][5],parsedData_MyMatch_Focus_Joiner[i][6],parsedData_MyMatch_Focus_Joiner[i][6],MyMatch_Focus.this,parsedData_MyMatch_Focus_Joiner[i][8],User_Pk));
            }
            MyMatch_Focus_Joiner_MyAdapter adapter = new MyMatch_Focus_Joiner_MyAdapter(this, MyMatch_Focus_Joiner_MyData);
            MyMatch_Focus_ListView_JoinerList.setAdapter(adapter);
            setListViewHeightBasedOnChildren(MyMatch_Focus_ListView_JoinerList);
            away.setVisibility(View.GONE);
            vs.setVisibility(View.GONE);
            MyMatch_Focus_Joined_ImageView_Phone.setVisibility(View.GONE);
        }
        else{
            Match_Focus_ImageView_Status.setImageResource(R.drawable.deadline);
            MyMatch_Focus_LinerLayout_Joining.setVisibility(View.GONE);
            HttpClient http_mymatch_focus_joined = new HttpClient();
            String result1 = http_mymatch_focus_joined.HttpClient("Trophy_part1","MyMatch_Focus_Joined.jsp", Match_Pk);
            parsedData_MyMatch_Focus_Joiner = jsonParserList_MyMatch_Focus_Join(result1);
            OtherTeam_Phone = parsedData_MyMatch_Focus_Joiner[0][8];
            Away_Emblem = parsedData_MyMatch_Focus_Joiner[0][5];
            Away_TeamName = parsedData_MyMatch_Focus_Joiner[0][6];
            Away_Team_Pk = parsedData_MyMatch_Focus_Joiner[0][7];
            try {
                String En_Profile = URLEncoder.encode(parsedData_MyMatch_Focus_Joiner[0][5], "utf-8");
                if (En_Profile.equals(".")) {
                    Glide.with(MyMatch_Focus.this).load(R.drawable.emblem).bitmapTransform(new CropCircleTransformation(Glide.get(MyMatch_Focus.this).getBitmapPool()))
                            .into(Match_Focus_ImageView_OpponentEmblem);
                } else {
                    Glide.with(MyMatch_Focus.this).load("http://210.122.7.193:8080/Trophy_img/team/" + En_Profile + ".jpg").bitmapTransform(new CropCircleTransformation(Glide.get(MyMatch_Focus.this).getBitmapPool()))
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true)
                            .into(Match_Focus_ImageView_OpponentEmblem);
                }
            } catch (UnsupportedEncodingException e) {

            }
            Match_Focus_TextView_OpponentTeamName.setText(parsedData_MyMatch_Focus_Joiner[0][6]);
            away.setVisibility(View.VISIBLE);
            vs.setVisibility(View.VISIBLE);
            MyMatch_Focus_Joined_ImageView_Phone.setVisibility(View.VISIBLE);
        }
//        String day="";
//        if(Integer.parseInt(MatchDate[1])<10){
//            day = "0"+MatchDate[1];
//        }
//        else{
//            day = MatchDate[1];
//        }
//        String str12 = FinishTime;
//        String[] data2 = str12.split(":");
//        String our = data2[0];
//        int match_day_time = Integer.parseInt(data[0]+MatchDate[0]+day+our);
//        if(Integer.parseInt(strCurToday) > match_day_time){
//            if(parsedData_Match_Focus[0][13].equals("deadline"))
//            {
//                Log.i("ttt","ttt");
//            }
//        }
        Match_Focus_ImageView_OpponentEmblem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyMatch_Focus.this,TeamSearch_Focus.class);
                intent.putExtra("TeamName", Away_TeamName);
                intent.putExtra("User_Pk",User_Pk);
                intent.putExtra("Team_Pk",Away_Team_Pk);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
            }
        });
        Match_Focus_ImageView_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater)view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
                final View layout = inflater.inflate(R.layout.layout_customdialog_2choice, (ViewGroup) view.findViewById(R.id.Customdialog_2Choice_Root));
                final TextView Customdialog_2Choice_Title = (TextView)layout.findViewById(R.id.Customdialog_2Choice_Title);
                final ImageView Customdialog_2Choice_Back = (ImageView)layout.findViewById(R.id.Customdialog_2Choice_Back);
                final TextView Customdialog_2Choice_Content = (TextView)layout.findViewById(R.id.Customdialog_2Choice_Content);
                final Button Customdialog_2Choice_First = (Button)layout.findViewById(R.id.Customdialog_2Choice_First);
                final Button Customdialog_2Choice_Second = (Button)layout.findViewById(R.id.Customdialog_2Choice_Second);
                Customdialog_2Choice_Title.setText("게시글 삭제");
                Customdialog_2Choice_Content.setText("해당 게시물을 삭제합니다.\n삭제 시 시합 요청 팀 목록이 삭제됩니다.");
                Customdialog_2Choice_First.setText("확 인");
                Customdialog_2Choice_Second.setText("취 소");
                final MaterialDialog TeamInfo_Dialog = new MaterialDialog(view.getContext());
                TeamInfo_Dialog
                        .setContentView(layout)
                        .setCanceledOnTouchOutside(true);
                TeamInfo_Dialog.show();
                Customdialog_2Choice_Back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TeamInfo_Dialog.dismiss();
                    }
                });
                Customdialog_2Choice_First.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        HttpClient TeamJoin = new HttpClient();
                        String result_delete =TeamJoin.HttpClient("Trophy_part1","MyMatch_Focus_Delete.jsp",Match_Pk);
                        parseredData_Delete = jsonParserList_Delete(result_delete);
                        if(parseredData_Delete[0][0].equals("succed")){
                            TeamInfo_Dialog.dismiss();
                            finish();
                            overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
                        }
                    }
                });
                Customdialog_2Choice_Second.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TeamInfo_Dialog.dismiss();
                    }
                });
            }
        });
        MyMatch_Focus_Joined_ImageView_Phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+OtherTeam_Phone));
                startActivity(intent);
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
    public String[][] jsonParserList_MyMatch(String pRecvServerPage){
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try{
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");
            String[] jsonName = {"msg1","msg2","msg3","msg4","msg5","msg6","msg7","msg8","msg9","msg10","msg11","msg12","msg13","msg14","msg15","msg16","msg17","msg18","msg19","msg20","msg21"};
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
    public String[][] jsonParserList_MyMatch_Focus_Join(String pRecvServerPage){
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
    public String[][] jsonParserList_Delete(String pRecvServerPage){
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
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);

        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
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
    public void currentTime(){
        long now = System.currentTimeMillis();
// 현재 시간을 저장 한다.
        Date date = new Date(now);
// 시간 포맷 지정
        SimpleDateFormat CurDateFormat = new SimpleDateFormat("yyyyMMddHH");
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
}
