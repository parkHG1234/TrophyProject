package trophy.projetc2.Score;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ButtonBarLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.FileNameMap;
import java.net.URLEncoder;
import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import me.drakeet.materialdialog.MaterialDialog;
import trophy.projetc2.Http.HttpClient;
import trophy.projetc2.Navigation.TeamSearch_Focus;
import trophy.projetc2.R;

import static java.security.AccessController.getContext;
import static trophy.projetc2.R.id.view;

/**
 * Created by 박효근 on 2017-06-29.
 */

public class Score_Focus extends AppCompatActivity {
    LinearLayout Score_Layout_MainScore;TextView Score_Focus_ImageView_ScoreInput;
    ImageView Score_Focus_ImageView_Back;
    ImageView Score_Focus_ImageView_Emblem, Score_Focus_ImageView_OpponentEmblem;
    Button Score_Button_Phone;
    TextView Score_TextView_HomeScore, Score_TextView_AwayScore;
    TextView Score_Focus_TextView_TeamName, Score_TextView_VS, Score_Focus_TextView_OpponentTeamName;
    TextView Score_Focus_TextView_Date, Score_Focus_TextView_Time, Score_Focus_TextView_Place, Score_Focus_TextView_Pay, Score_Focus_TextView_Uniform;
    ListView Score_Focus_ListView_Result;
    CheckBox Score_Focus_CheckBox_Parking_Not, Score_Focus_CheckBox_Parking_Free, Score_Focus_CheckBox_Parking_Charge;
    CheckBox Score_Focus_CheckBox_Display, Score_Focus_CheckBox_Shower, Score_Focus_CheckBox_ColdHot;
    TextView Score_Focus_TextView_Extra;

    String User_Pk, GameStatus;
    String Match_Pk , HomeTeam_Pk, Home_Emblem, Home_TeamName, Content_User_Pk;
    String Pay, Color, Extra, MatchDate, StartTime, FinishTime, MatchPlace, Status;
    String Parking_Not, Parking_Free, Parking_Charge, Display, Shower, ColdHot;
    String Game1_Home, Game1_Away, Game2_Home, Game2_Away, Game3_Home, Game3_Away;
    String AwayTeam_Pk, Away_TeamName, Away_Emblem, AwayTeam_User_Pk;
    String[][] parsedData_Score_Focus; String[][] parsedData_Score_Succed;
    String[][] parsedData_Phone;
    int GameCount=1; int Grade=0;

    //점수 다이얼로그 함수 선언
    LayoutInflater inflater;View layout;TextView Title1;TextView Title2;
    TextView scoreinput_FirstGame;TextView scoreinput_SecondGame;TextView scoreinput_ThirdGame;
    TextView scoreinput_FirstScore_Title;TextView scoreinput_FirstScore_Title_DD;TextView scoreinput_SecondScore_Title;
    TextView scoreinput_SecondScore_Title_DD;TextView scoreinput_ThirdScore_Title;TextView scoreinput_ThirdScore_Title_DD;
    EditText scoreinput_FirstScore_Home;EditText scoreinput_FirstScore_Away;EditText scoreinput_SecondScore_Home;
    EditText scoreinput_SecondScore_Away;EditText scoreinput_ThirdScore_Home;EditText scoreinput_ThirdScore_Away;
    TextView scoreinput_Input;TextView scoreinput_Back;
    RatingBar ratingBar;
    MaterialDialog TeamInfo_Dialog;

    //점수 결과
    Score_Focus_Result_MyAdapter adapter;
    ArrayList<Score_Focus_Result_MyData> Score_Focus_Result_MyData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_navigation_score_focus);

        //currentTime();
        Intent intent1 = getIntent();
        Match_Pk = intent1.getStringExtra("Match_Pk");
        User_Pk = intent1.getStringExtra("User_Pk");
        GameStatus = intent1.getStringExtra("GameStatus");

        HttpClient http_match_focus = new HttpClient();
        String result = http_match_focus.HttpClient("Trophy_part1","Score_Focus.jsp",Match_Pk);
        parsedData_Score_Focus = jsonParserList_Score(result);

        Match_Pk = parsedData_Score_Focus[0][0];HomeTeam_Pk = parsedData_Score_Focus[0][1];Home_Emblem = parsedData_Score_Focus[0][2];
        Home_TeamName = parsedData_Score_Focus[0][3];Content_User_Pk = parsedData_Score_Focus[0][4];Pay = parsedData_Score_Focus[0][5];
        Color = parsedData_Score_Focus[0][6];Extra = parsedData_Score_Focus[0][7];MatchDate = parsedData_Score_Focus[0][8];
        StartTime = parsedData_Score_Focus[0][9];FinishTime = parsedData_Score_Focus[0][10]; MatchPlace = parsedData_Score_Focus[0][11];
        Parking_Not = parsedData_Score_Focus[0][12];Parking_Free = parsedData_Score_Focus[0][13];Parking_Charge = parsedData_Score_Focus[0][14];
        Display = parsedData_Score_Focus[0][15];Shower = parsedData_Score_Focus[0][16];ColdHot = parsedData_Score_Focus[0][17];
        Status = parsedData_Score_Focus[0][18];

        Game1_Home = parsedData_Score_Focus[0][19]; Game1_Away = parsedData_Score_Focus[0][20]; Game2_Home = parsedData_Score_Focus[0][21];
        Game2_Away = parsedData_Score_Focus[0][22]; Game3_Home = parsedData_Score_Focus[0][23]; Game3_Away = parsedData_Score_Focus[0][24];

        AwayTeam_Pk = parsedData_Score_Focus[0][25];Away_Emblem = parsedData_Score_Focus[0][26];Away_TeamName = parsedData_Score_Focus[0][27];
        AwayTeam_User_Pk = parsedData_Score_Focus[0][28];

        String[] data = MatchDate.split(":::");
        String str1 = data[1];
        String[] data2 = str1.split(" / ");

        Score_Focus_ImageView_Back = (ImageView)findViewById(R.id.Score_Focus_ImageView_Back);
        Score_Layout_MainScore = (LinearLayout)findViewById(R.id.Score_Layout_MainScore);
        Score_TextView_HomeScore = (TextView)findViewById(R.id.Score_TextView_HomeScore);
        Score_TextView_AwayScore = (TextView)findViewById(R.id.Score_TextView_AwayScore);
        Score_Focus_ImageView_ScoreInput = (TextView) findViewById(R.id.Score_Focus_ImageView_ScoreInput);
        Score_Focus_ImageView_Emblem = (ImageView)findViewById(R.id.Score_Focus_ImageView_Emblem);
        Score_Focus_ImageView_OpponentEmblem = (ImageView)findViewById(R.id.Score_Focus_ImageView_OpponentEmblem);
        Score_Focus_TextView_TeamName = (TextView)findViewById(R.id.Score_Focus_TextView_TeamName);
        Score_TextView_VS = (TextView)findViewById(R.id.Score_TextView_VS);
        Score_Button_Phone = (Button)findViewById(R.id.Score_Button_Phone);
        Score_Focus_TextView_OpponentTeamName = (TextView)findViewById(R.id.Score_Focus_TextView_OpponentTeamName);
        Score_Focus_TextView_Date = (TextView)findViewById(R.id.Score_Focus_TextView_Date);
        Score_Focus_TextView_Time = (TextView)findViewById(R.id.Score_Focus_TextView_Time);
        Score_Focus_TextView_Place = (TextView)findViewById(R.id.Score_Focus_TextView_Place);
        Score_Focus_TextView_Pay = (TextView)findViewById(R.id.Score_Focus_TextView_Pay);
        Score_Focus_TextView_Uniform = (TextView)findViewById(R.id.Score_Focus_TextView_Uniform);
        Score_Focus_TextView_Extra = (TextView)findViewById(R.id.Score_Focus_TextView_Extra);
        Score_Focus_ListView_Result = (ListView)findViewById(R.id.Score_Focus_ListView_Result);

        Score_Focus_CheckBox_Parking_Not = (CheckBox)findViewById(R.id.Score_Focus_CheckBox_Parking_Not);
        Score_Focus_CheckBox_Parking_Free = (CheckBox)findViewById(R.id.Score_Focus_CheckBox_Parking_Free);
        Score_Focus_CheckBox_Parking_Charge = (CheckBox)findViewById(R.id.Score_Focus_CheckBox_Parking_Charge);
        Score_Focus_CheckBox_Display = (CheckBox)findViewById(R.id.Score_Focus_CheckBox_Display);
        Score_Focus_CheckBox_Shower = (CheckBox)findViewById(R.id.Score_Focus_CheckBox_Shower);
        Score_Focus_CheckBox_ColdHot = (CheckBox)findViewById(R.id.Score_Focus_CheckBox_ColdHot);

        //점수 다이얼로그 함수 선언
        inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        layout = inflater.inflate(R.layout.layout_navigation_score_focus_scoreinput, (ViewGroup) findViewById(R.id.scoreinput_root));
        Title1 = (TextView)layout.findViewById(R.id.title1);
        Title2 = (TextView)layout.findViewById(R.id.title2);
        scoreinput_FirstGame = (TextView)layout.findViewById(R.id.scoreinput_FirstGame);
        scoreinput_SecondGame = (TextView)layout.findViewById(R.id.scoreinput_SecondGame);
        scoreinput_ThirdGame = (TextView)layout.findViewById(R.id.scoreinput_ThirdGame);
        scoreinput_FirstScore_Title = (TextView)layout.findViewById(R.id.scoreinput_FirstScore_Title);
        scoreinput_FirstScore_Title_DD = (TextView)layout.findViewById(R.id.scoreinput_FirstScore_Title_DD);
        scoreinput_SecondScore_Title = (TextView)layout.findViewById(R.id.scoreinput_SecondScore_Title);
        scoreinput_SecondScore_Title_DD = (TextView)layout.findViewById(R.id.scoreinput_SecondScore_Title_DD);
        scoreinput_ThirdScore_Title = (TextView)layout.findViewById(R.id.scoreinput_ThirdScore_Title);
        scoreinput_ThirdScore_Title_DD = (TextView)layout.findViewById(R.id.scoreinput_ThirdScore_Title_DD);
        scoreinput_FirstScore_Home = (EditText)layout.findViewById(R.id.scoreinput_FirstScore_Home);
        scoreinput_FirstScore_Away = (EditText)layout.findViewById(R.id.scoreinput_FirstScore_Away);
        scoreinput_SecondScore_Home = (EditText)layout.findViewById(R.id.scoreinput_SecondScore_Home);
        scoreinput_SecondScore_Away = (EditText)layout.findViewById(R.id.scoreinput_SecondScore_Away);
        scoreinput_ThirdScore_Home = (EditText)layout.findViewById(R.id.scoreinput_ThirdScore_Home);
        scoreinput_ThirdScore_Away = (EditText)layout.findViewById(R.id.scoreinput_ThirdScore_Away);
        scoreinput_Input = (TextView)layout.findViewById(R.id.scoreinput_Input);
        scoreinput_Back = (TextView)layout.findViewById(R.id.scoreinput_Back);
        ratingBar = (RatingBar)layout.findViewById(R.id.ratingBar);
        Title1.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/BMJUA_ttf.ttf"));
        Title2.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/BMJUA_ttf.ttf"));
        TeamInfo_Dialog = new MaterialDialog(Score_Focus.this);

        Score_Layout_MainScore.setVisibility(View.GONE);
        Score_Button_Phone.setVisibility(View.GONE);
        if(User_Pk.equals(Content_User_Pk)){
            Score_Button_Phone.setVisibility(View.VISIBLE);
            HttpClient http_phone = new HttpClient();
            String result1 = http_phone.HttpClient("Trophy_part1","User_Phone.jsp",AwayTeam_User_Pk);
            parsedData_Phone = jsonParserList_Phone(result1);
        }
        else if(User_Pk.equals(AwayTeam_User_Pk)){
            Score_Button_Phone.setVisibility(View.VISIBLE);
            HttpClient http_phone = new HttpClient();
            String result1 = http_phone.HttpClient("Trophy_part1","User_Phone.jsp",Content_User_Pk);
            parsedData_Phone = jsonParserList_Phone(result1);
        }
        Score_Button_Phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+parsedData_Phone[0][0]));
                startActivity(intent);
            }
        });

        Score_Focus_CheckBox_Parking_Not.setClickable(false);Score_Focus_CheckBox_Parking_Free.setClickable(false);Score_Focus_CheckBox_Parking_Charge.setClickable(false);
        Score_Focus_CheckBox_Display.setClickable(false);Score_Focus_CheckBox_Shower.setClickable(false);Score_Focus_CheckBox_ColdHot.setClickable(false);

        //점수 입력안된 경우
        if(GameStatus.equals("After")){
            scoreinput_FirstGame.setBackgroundColor(getResources().getColor(R.color.main1color));
            scoreinput_FirstGame.setTextColor(getResources().getColor(R.color.White));
            scoreinput_FirstScore_Title.setTextColor(getResources().getColor(R.color.Black));
            scoreinput_SecondScore_Home.setEnabled(false);scoreinput_SecondScore_Away.setEnabled(false);
            scoreinput_ThirdScore_Home.setEnabled(false);scoreinput_ThirdScore_Away.setEnabled(false);

            if(Status.equals("Not_Insert")){
                if(User_Pk.equals(Content_User_Pk)) {
                    HomeInsert_Home();
                }
            }
            else if(Status.equals("Home_Insert")){
                if(User_Pk.equals(AwayTeam_User_Pk)){
                    HomeInsert_Away();
                }
            }
            else if(Status.equals("Finish")){
                Score_TextView_VS.setVisibility(View.INVISIBLE);
                Score_Layout_MainScore.setVisibility(View.VISIBLE);
                Score_TextView_HomeScore.setText(Game1_Home);
                Score_TextView_AwayScore.setText(Game1_Away);

                ScoreResult();
            }
        }
        Score_Focus_ImageView_ScoreInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(GameStatus.equals("Before")){
                    Snackbar.make(view,"경기 후 이용해주시기 바랍니다",Snackbar.LENGTH_SHORT).show();
                }
                else if(GameStatus.equals("Gameing")){
                    Snackbar.make(view,"경기 후 이용해주시기 바랍니다",Snackbar.LENGTH_SHORT).show();
                }
                else if(GameStatus.equals("After")){
                    if(Status.equals("Not_Insert")){
                        if(User_Pk.equals(Content_User_Pk)) {
                            HomeInsert_Home();
                        }
                        else{
                            Snackbar.make(view, "홈팀이 점수입력하지 않았습니다",Snackbar.LENGTH_SHORT).show();
                        }
                    }
                    //점수가 입력된 경우
                    else if(Status.equals("Home_Insert")){
                        if(User_Pk.equals(Content_User_Pk)){
                            Snackbar.make(view,"점수 확인 중 입니다", Snackbar.LENGTH_SHORT).show();
                        }
                        else if(User_Pk.equals(AwayTeam_User_Pk)){
                            HomeInsert_Away();
                        }
                    }

                }
            }
        });
        //홈팀 앰블렘 및 클릭시 팀 정보 이동
        try {
            String En_Profile = URLEncoder.encode(Home_Emblem, "utf-8");
            if (En_Profile.equals(".")) {
                Glide.with(Score_Focus.this).load(R.drawable.emblem).bitmapTransform(new CropCircleTransformation(Glide.get(Score_Focus.this).getBitmapPool()))
                        .into(Score_Focus_ImageView_Emblem);
            } else {
                Glide.with(Score_Focus.this).load("http://210.122.7.193:8080/Trophy_img/team/" + En_Profile + ".jpg").bitmapTransform(new CropCircleTransformation(Glide.get(Score_Focus.this).getBitmapPool()))
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(Score_Focus_ImageView_Emblem);
            }
        } catch (UnsupportedEncodingException e) {

        }
        Score_Focus_ImageView_Emblem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Home_TeamName.equals(".")){
                    Snackbar.make(view,"삭제된 팀입니다.",Snackbar.LENGTH_SHORT);
                }
                else{
                    Intent intent = new Intent(Score_Focus.this,TeamSearch_Focus.class);
                    intent.putExtra("TeamName", Home_TeamName);
                    intent.putExtra("User_Pk",User_Pk);
                    intent.putExtra("Team_Pk",HomeTeam_Pk);
                    startActivity(intent);
                    overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                }
            }
        });

        //어웨이이팀 앰블렘 및클릭시 팀 정보 이동
        try {
            String En_Profile = URLEncoder.encode(Away_Emblem, "utf-8");
            if (En_Profile.equals(".")) {
                Glide.with(Score_Focus.this).load(R.drawable.emblem).bitmapTransform(new CropCircleTransformation(Glide.get(Score_Focus.this).getBitmapPool()))
                        .into(Score_Focus_ImageView_OpponentEmblem);
            } else {
                Glide.with(Score_Focus.this).load("http://210.122.7.193:8080/Trophy_img/team/" + En_Profile + ".jpg").bitmapTransform(new CropCircleTransformation(Glide.get(Score_Focus.this).getBitmapPool()))
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(Score_Focus_ImageView_OpponentEmblem);
            }
        } catch (UnsupportedEncodingException e) {

        }
        Score_Focus_ImageView_OpponentEmblem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Away_TeamName.equals(".")){
                    Snackbar.make(view,"삭제된 팀입니다.", Snackbar.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(Score_Focus.this,TeamSearch_Focus.class);
                    intent.putExtra("TeamName", Away_TeamName);
                    intent.putExtra("User_Pk",User_Pk);
                    intent.putExtra("Team_Pk",AwayTeam_Pk);
                    startActivity(intent);
                    overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                }
            }
        });
        if(Home_TeamName.equals(".")){
            Score_Focus_TextView_TeamName.setText(".");
        }else {
            Score_Focus_TextView_TeamName.setText(Home_TeamName);
        }
        Score_Focus_TextView_TeamName.setText(Home_TeamName);
        if(Away_TeamName.equals(".")){
            Score_Focus_TextView_OpponentTeamName.setText("삭제된 팀");
        }else {
            Score_Focus_TextView_OpponentTeamName.setText(Away_TeamName);
        }
        Score_Focus_TextView_Date.setText(data[0] + " / " + data2[0] + " / " + data2[1]);
        Score_Focus_TextView_Time.setText(time_changestr(StartTime) + " ~ " + time_changestr(FinishTime));
        Score_Focus_TextView_Place.setText(MatchPlace);
        Score_Focus_TextView_Pay.setText(Pay);
        Score_Focus_TextView_Uniform.setText(Color);
        Score_Focus_TextView_Extra.setText(Extra);

        if(GameStatus.equals("Before")){
            Score_Focus_ImageView_ScoreInput.setVisibility(View.INVISIBLE);
            Score_TextView_VS.setText("경기전");
        }
        else if(GameStatus.equals("Gameing")){
            Score_Focus_ImageView_ScoreInput.setVisibility(View.INVISIBLE);
            Score_TextView_VS.setText("경기중");
        }
        else if(GameStatus.equals("After")){
            if(Status.equals("Not_Insert")){
                if(User_Pk.equals(Content_User_Pk)) {
                    Score_Focus_ImageView_ScoreInput.setVisibility(View.VISIBLE);
                    Score_Focus_ImageView_ScoreInput.setText("점수입력");
                }
                else{
                    Score_Focus_ImageView_ScoreInput.setVisibility(View.INVISIBLE);
                }
            }
            //점수가 입력된 경우
            else if(Status.equals("Home_Insert")){
                if(User_Pk.equals(Content_User_Pk)){
                    Score_Focus_ImageView_ScoreInput.setVisibility(View.VISIBLE);
                    Score_Focus_ImageView_ScoreInput.setText("점수확인중");
                }
                else if(User_Pk.equals(AwayTeam_User_Pk)){
                    Score_Focus_ImageView_ScoreInput.setVisibility(View.VISIBLE);
                    Score_Focus_ImageView_ScoreInput.setText("점수확인");
                }
            }
            else
            {
                Score_Focus_ImageView_ScoreInput.setVisibility(View.INVISIBLE);
            }

        }

        if(Parking_Not.equals("true")){
            Score_Focus_CheckBox_Parking_Not.setChecked(true);
        }else{
            Score_Focus_CheckBox_Parking_Not.setChecked(false);
        }
        if(Parking_Free.equals("true")){
            Score_Focus_CheckBox_Parking_Free.setChecked(true);
        }else{
            Score_Focus_CheckBox_Parking_Free.setChecked(false);
        }
        if(Parking_Charge.equals("true")){
            Score_Focus_CheckBox_Parking_Charge.setChecked(true);
        }else{
            Score_Focus_CheckBox_Parking_Charge.setChecked(false);
        }
        if(Display.equals("true")){
            Score_Focus_CheckBox_Display.setChecked(true);
        }else{
            Score_Focus_CheckBox_Display.setChecked(false);
        }
        if(Shower.equals("true")){
            Score_Focus_CheckBox_Shower.setChecked(true);
        }else{
            Score_Focus_CheckBox_Shower.setChecked(false);
        }
        if(ColdHot.equals("true")){
            Score_Focus_CheckBox_ColdHot.setChecked(true);
        }else{
            Score_Focus_CheckBox_ColdHot.setChecked(false);
        }



        Score_Focus_ImageView_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
            }
        });
    }
    public String[][] jsonParserList_Score(String pRecvServerPage){
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try{
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");
            String[] jsonName = {"msg1","msg2","msg3","msg4","msg5","msg6","msg7","msg8","msg9","msg10","msg11","msg12","msg13","msg14","msg15","msg16","msg17","msg18","msg19","msg20","msg21","msg22","msg23","msg24","msg25","msg26","msg27","msg28","msg29"};
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
    public String[][] jsonParserList_Phone(String pRecvServerPage){
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
    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
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
    public void HomeInsert_Home(){
        TeamInfo_Dialog
                .setContentView(layout)
                .setCanceledOnTouchOutside(true);
        TeamInfo_Dialog.show();
        scoreinput_FirstGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GameCount = 1;
                scoreinput_FirstGame.setBackgroundColor(getResources().getColor(R.color.main1color));
                scoreinput_FirstGame.setTextColor(getResources().getColor(R.color.White));
                scoreinput_SecondGame.setBackgroundColor(getResources().getColor(R.color.White));
                scoreinput_SecondGame.setTextColor(getResources().getColor(R.color.main1color));
                scoreinput_ThirdGame.setBackgroundColor(getResources().getColor(R.color.White));
                scoreinput_ThirdGame.setTextColor(getResources().getColor(R.color.main1color));
                scoreinput_FirstScore_Title.setTextColor(getResources().getColor(R.color.Black));
                scoreinput_SecondScore_Title.setTextColor(getResources().getColor(R.color.DarkGray));
                scoreinput_SecondScore_Title_DD.setTextColor(getResources().getColor(R.color.DarkGray));
                scoreinput_ThirdScore_Title.setTextColor(getResources().getColor(R.color.DarkGray));
                scoreinput_ThirdScore_Title_DD.setTextColor(getResources().getColor(R.color.DarkGray));
                scoreinput_SecondScore_Home.setText("");scoreinput_SecondScore_Away.setText("");
                scoreinput_ThirdScore_Home.setText("");scoreinput_ThirdScore_Away.setText("");
                scoreinput_FirstScore_Home.setEnabled(true);scoreinput_FirstScore_Away.setEnabled(true);
                scoreinput_SecondScore_Home.setEnabled(false);scoreinput_SecondScore_Away.setEnabled(false);
                scoreinput_ThirdScore_Home.setEnabled(false);scoreinput_ThirdScore_Away.setEnabled(false);
            }
        });
        scoreinput_SecondGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GameCount = 2;
                scoreinput_FirstGame.setBackgroundColor(getResources().getColor(R.color.White));
                scoreinput_FirstGame.setTextColor(getResources().getColor(R.color.main1color));
                scoreinput_SecondGame.setBackgroundColor(getResources().getColor(R.color.main1color));
                scoreinput_SecondGame.setTextColor(getResources().getColor(R.color.White));
                scoreinput_ThirdGame.setBackgroundColor(getResources().getColor(R.color.White));
                scoreinput_ThirdGame.setTextColor(getResources().getColor(R.color.main1color));
                scoreinput_FirstScore_Title.setTextColor(getResources().getColor(R.color.Black));
                scoreinput_SecondScore_Title.setTextColor(getResources().getColor(R.color.Black));
                scoreinput_SecondScore_Title_DD.setTextColor(getResources().getColor(R.color.Black));
                scoreinput_ThirdScore_Title.setTextColor(getResources().getColor(R.color.DarkGray));
                scoreinput_ThirdScore_Title_DD.setTextColor(getResources().getColor(R.color.DarkGray));
                scoreinput_ThirdScore_Home.setText("");scoreinput_ThirdScore_Away.setText("");
                scoreinput_FirstScore_Home.setEnabled(true);scoreinput_FirstScore_Away.setEnabled(true);
                scoreinput_SecondScore_Home.setEnabled(true);scoreinput_SecondScore_Away.setEnabled(true);
                scoreinput_ThirdScore_Home.setEnabled(false);scoreinput_ThirdScore_Away.setEnabled(false);
            }
        });
        scoreinput_ThirdGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GameCount = 3;
                scoreinput_FirstGame.setBackgroundColor(getResources().getColor(R.color.White));
                scoreinput_FirstGame.setTextColor(getResources().getColor(R.color.main1color));
                scoreinput_SecondGame.setBackgroundColor(getResources().getColor(R.color.White));
                scoreinput_SecondGame.setTextColor(getResources().getColor(R.color.main1color));
                scoreinput_ThirdGame.setBackgroundColor(getResources().getColor(R.color.main1color));
                scoreinput_ThirdGame.setTextColor(getResources().getColor(R.color.White));
                scoreinput_FirstScore_Title.setTextColor(getResources().getColor(R.color.Black));
                scoreinput_SecondScore_Title.setTextColor(getResources().getColor(R.color.Black));
                scoreinput_ThirdScore_Title.setTextColor(getResources().getColor(R.color.Black));
                scoreinput_SecondScore_Title_DD.setTextColor(getResources().getColor(R.color.Black));
                scoreinput_ThirdScore_Title_DD.setTextColor(getResources().getColor(R.color.Black));
                scoreinput_FirstScore_Home.setEnabled(true);scoreinput_FirstScore_Away.setEnabled(true);
                scoreinput_SecondScore_Home.setEnabled(true);scoreinput_SecondScore_Away.setEnabled(true);
                scoreinput_ThirdScore_Home.setEnabled(true);scoreinput_ThirdScore_Away.setEnabled(true);
            }
        });
        scoreinput_Input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(GameCount == 1){
                    if(scoreinput_FirstScore_Home.getText().toString().equals("")){
                        Snackbar.make(view, "점수를 입력해주세요", Snackbar.LENGTH_SHORT).show();
                    }
                    else if(scoreinput_FirstScore_Away.getText().toString().equals("")){
                        Snackbar.make(view, "점수를 입력해주세요", Snackbar.LENGTH_SHORT).show();
                    }
                    else{
                        HttpClient http_input= new HttpClient();
                        String result = http_input.HttpClient("Trophy_part1","Score_Focus_Input.jsp",Match_Pk, String.valueOf(ratingBar.getRating()), scoreinput_FirstScore_Home.getText().toString(),scoreinput_FirstScore_Away.getText().toString(), ".",".",".",".");
                        parsedData_Score_Succed = jsonParserList_Succed(result);
                        if(parsedData_Score_Succed[0][0].equals("succed")){
                            TeamInfo_Dialog.dismiss();
                            Status = "Home_Insert";
                            Score_TextView_VS.setText("점수확인중");
                            HttpClient http_push = new HttpClient();
                            http_push.HttpClient("TodayBasket_manager","push.jsp", AwayTeam_User_Pk, "홈팀이 점수입력하였습니다");
                        }
                    }
                }
                if(GameCount == 2){
                    if(scoreinput_FirstScore_Home.getText().toString().equals("")){
                        Snackbar.make(view, "점수를 입력해주세요", Snackbar.LENGTH_SHORT).show();
                    }
                    else if(scoreinput_FirstScore_Away.getText().toString().equals("")){
                        Snackbar.make(view, "점수를 입력해주세요", Snackbar.LENGTH_SHORT).show();
                    }
                    else if(scoreinput_SecondScore_Home.getText().toString().equals("")){
                        Snackbar.make(view, "점수를 입력해주세요", Snackbar.LENGTH_SHORT).show();
                    }
                    else if(scoreinput_SecondScore_Away.getText().toString().equals("")){
                        Snackbar.make(view, "점수를 입력해주세요", Snackbar.LENGTH_SHORT).show();
                    }
                    else
                    {
                        HttpClient http_input= new HttpClient();
                        String result = http_input.HttpClient("Trophy_part1","Score_Focus_Input.jsp",Match_Pk, String.valueOf(ratingBar.getRating()), scoreinput_FirstScore_Home.getText().toString(),scoreinput_FirstScore_Away.getText().toString(), scoreinput_SecondScore_Home.getText().toString(),scoreinput_SecondScore_Away.getText().toString(),".",".");
                        parsedData_Score_Succed = jsonParserList_Succed(result);
                        if(parsedData_Score_Succed[0][0].equals("succed")){
                            TeamInfo_Dialog.dismiss();
                            Status = "Home_Insert";
                            Score_TextView_VS.setText("점수확인중");
                            HttpClient http_push = new HttpClient();
                            http_push.HttpClient("TodayBasket_manager","push.jsp", AwayTeam_User_Pk, "홈팀이 점수입력하였습니다");
                        }
                    }
                }
                if(GameCount == 3){
                    if(scoreinput_FirstScore_Home.getText().toString().equals("")){
                        Snackbar.make(view, "점수를 입력해주세요", Snackbar.LENGTH_SHORT).show();
                    }
                    else if(scoreinput_FirstScore_Away.getText().toString().equals("")){
                        Snackbar.make(view, "점수를 입력해주세요", Snackbar.LENGTH_SHORT).show();
                    }
                    else if(scoreinput_SecondScore_Home.getText().toString().equals("")){
                        Snackbar.make(view, "점수를 입력해주세요", Snackbar.LENGTH_SHORT).show();
                    }
                    else if(scoreinput_SecondScore_Away.getText().toString().equals("")){
                        Snackbar.make(view, "점수를 입력해주세요", Snackbar.LENGTH_SHORT).show();
                    }
                    else if(scoreinput_ThirdScore_Home.getText().toString().equals("")){
                        Snackbar.make(view, "점수를 입력해주세요", Snackbar.LENGTH_SHORT).show();
                    }
                    else if(scoreinput_ThirdScore_Away.getText().toString().equals("")){
                        Snackbar.make(view, "점수를 입력해주세요", Snackbar.LENGTH_SHORT).show();
                    }
                    else
                    {
                        HttpClient http_input= new HttpClient();
                        String result = http_input.HttpClient("Trophy_part1","Score_Focus_Input.jsp",Match_Pk, String.valueOf(ratingBar.getRating()), scoreinput_FirstScore_Home.getText().toString(),scoreinput_FirstScore_Away.getText().toString(), scoreinput_SecondScore_Home.getText().toString(),scoreinput_SecondScore_Away.getText().toString(),scoreinput_ThirdScore_Home.getText().toString(),scoreinput_ThirdScore_Away.getText().toString());
                        parsedData_Score_Succed = jsonParserList_Succed(result);
                        if(parsedData_Score_Succed[0][0].equals("succed")){
                            TeamInfo_Dialog.dismiss();
                            Status = "Home_Insert";
                            Score_TextView_VS.setText("점수확인중");
                            HttpClient http_push = new HttpClient();
                            http_push.HttpClient("TodayBasket_manager","push.jsp", AwayTeam_User_Pk, "홈팀이 점수를 입력했습니다 상대팀의 매너점수를 평가해주세요");
                        }
                    }
                }
            }
        });
        scoreinput_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TeamInfo_Dialog.dismiss();
            }
        });
    }
    public void HomeInsert_Away(){
        Title1.setText("경기결과를 확인해주세요!");
        scoreinput_Input.setText("결과확인");
        scoreinput_FirstScore_Home.setEnabled(false);scoreinput_FirstScore_Away.setEnabled(false);
        scoreinput_SecondScore_Home.setEnabled(false);scoreinput_SecondScore_Away.setEnabled(false);
        scoreinput_ThirdScore_Home.setEnabled(false);scoreinput_ThirdScore_Away.setEnabled(false);
        if(Game3_Home.equals(".")){
            //두경기인 경우
            scoreinput_FirstGame.setBackgroundColor(getResources().getColor(R.color.White));
            scoreinput_FirstGame.setTextColor(getResources().getColor(R.color.main1color));
            scoreinput_SecondGame.setBackgroundColor(getResources().getColor(R.color.main1color));
            scoreinput_SecondGame.setTextColor(getResources().getColor(R.color.White));
            scoreinput_ThirdGame.setBackgroundColor(getResources().getColor(R.color.White));
            scoreinput_ThirdGame.setTextColor(getResources().getColor(R.color.main1color));
            scoreinput_FirstScore_Title.setTextColor(getResources().getColor(R.color.Black));
            scoreinput_SecondScore_Title.setTextColor(getResources().getColor(R.color.Black));
            scoreinput_SecondScore_Title_DD.setTextColor(getResources().getColor(R.color.Black));
            scoreinput_ThirdScore_Title.setTextColor(getResources().getColor(R.color.DarkGray));
            scoreinput_ThirdScore_Title_DD.setTextColor(getResources().getColor(R.color.DarkGray));
            scoreinput_ThirdScore_Home.setText("");scoreinput_ThirdScore_Away.setText("");
            scoreinput_FirstScore_Home.setText(Game1_Home);scoreinput_FirstScore_Away.setText(Game1_Away);
            scoreinput_SecondScore_Home.setText(Game2_Home);scoreinput_SecondScore_Away.setText(Game2_Away);
            if(Game2_Home.equals(".")){
                //한경기인 경우
                scoreinput_FirstGame.setBackgroundColor(getResources().getColor(R.color.main1color));
                scoreinput_FirstGame.setTextColor(getResources().getColor(R.color.White));
                scoreinput_SecondGame.setBackgroundColor(getResources().getColor(R.color.White));
                scoreinput_SecondGame.setTextColor(getResources().getColor(R.color.main1color));
                scoreinput_ThirdGame.setBackgroundColor(getResources().getColor(R.color.White));
                scoreinput_ThirdGame.setTextColor(getResources().getColor(R.color.main1color));
                scoreinput_FirstScore_Title.setTextColor(getResources().getColor(R.color.Black));
                scoreinput_SecondScore_Title.setTextColor(getResources().getColor(R.color.DarkGray));
                scoreinput_SecondScore_Title_DD.setTextColor(getResources().getColor(R.color.DarkGray));
                scoreinput_ThirdScore_Title.setTextColor(getResources().getColor(R.color.DarkGray));
                scoreinput_ThirdScore_Title_DD.setTextColor(getResources().getColor(R.color.DarkGray));
                scoreinput_SecondScore_Home.setText("");scoreinput_SecondScore_Away.setText("");
                scoreinput_ThirdScore_Home.setText("");scoreinput_ThirdScore_Away.setText("");
                scoreinput_FirstScore_Home.setText(Game1_Home);scoreinput_FirstScore_Away.setText(Game1_Away);
            }
        }
        else{
            //3경기인 경우
            scoreinput_FirstGame.setBackgroundColor(getResources().getColor(R.color.White));
            scoreinput_FirstGame.setTextColor(getResources().getColor(R.color.main1color));
            scoreinput_SecondGame.setBackgroundColor(getResources().getColor(R.color.White));
            scoreinput_SecondGame.setTextColor(getResources().getColor(R.color.main1color));
            scoreinput_ThirdGame.setBackgroundColor(getResources().getColor(R.color.main1color));
            scoreinput_ThirdGame.setTextColor(getResources().getColor(R.color.White));
            scoreinput_FirstScore_Title.setTextColor(getResources().getColor(R.color.Black));
            scoreinput_SecondScore_Title.setTextColor(getResources().getColor(R.color.Black));
            scoreinput_ThirdScore_Title.setTextColor(getResources().getColor(R.color.Black));
            scoreinput_SecondScore_Title_DD.setTextColor(getResources().getColor(R.color.Black));
            scoreinput_ThirdScore_Title_DD.setTextColor(getResources().getColor(R.color.Black));
            scoreinput_FirstScore_Home.setText(Game1_Home);scoreinput_FirstScore_Away.setText(Game1_Away);
            scoreinput_SecondScore_Home.setText(Game2_Home);scoreinput_SecondScore_Away.setText(Game2_Away);
            scoreinput_ThirdScore_Home.setText(Game3_Home);scoreinput_ThirdScore_Away.setText(Game3_Away);
        }
        TeamInfo_Dialog
                .setContentView(layout)
                .setCanceledOnTouchOutside(true);
        TeamInfo_Dialog.show();
        scoreinput_Input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HttpClient http_input= new HttpClient();
                String result = http_input.HttpClient("Trophy_part1","Score_Focus_Input_Away.jsp",Match_Pk, String.valueOf(ratingBar.getRating()));
                parsedData_Score_Succed = jsonParserList_Succed(result);
                if(parsedData_Score_Succed[0][0].equals("succed")){
                    TeamInfo_Dialog.dismiss();
                    Status = "Finish";
                    Score_TextView_VS.setVisibility(View.INVISIBLE);
                    Score_Layout_MainScore.setVisibility(View.VISIBLE);
                    Score_TextView_HomeScore.setText(Game1_Home);
                    Score_TextView_AwayScore.setText(Game1_Away);
                    ScoreResult();
                    HttpClient http_push = new HttpClient();
                    http_push.HttpClient("TodayBasket_manager","push.jsp", Content_User_Pk, "어웨이팀에서 연습게임 결과를 확인하였습니다");
                }
            }
        });
        scoreinput_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TeamInfo_Dialog.dismiss();
            }
        });
    }
    public void ScoreResult(){
        Score_Focus_Result_MyData = new ArrayList<Score_Focus_Result_MyData>();
        Score_Focus_Result_MyData.add(new Score_Focus_Result_MyData(Home_Emblem, Home_TeamName, Away_Emblem, Away_TeamName,Game1_Home,Game1_Away));
        if(!Game2_Home.equals(".")){
            Score_Focus_Result_MyData.add(new Score_Focus_Result_MyData(Home_Emblem, Home_TeamName, Away_Emblem, Away_TeamName,Game2_Home,Game2_Away));
            if(!Game3_Home.equals(".")){
                Score_Focus_Result_MyData.add(new Score_Focus_Result_MyData(Home_Emblem, Home_TeamName, Away_Emblem, Away_TeamName,Game3_Home,Game3_Away));
            }
        }
        adapter = new Score_Focus_Result_MyAdapter(this, Score_Focus_Result_MyData);
        Score_Focus_ListView_Result.setAdapter(adapter);
        setListViewHeightBasedOnChildren_scroll(Score_Focus_ListView_Result);
    }
    public static void setListViewHeightBasedOnChildren_scroll(ListView listView) {
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
}
