package trophy.projetc2.Navigation;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tsengvn.typekit.TypekitContextWrapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import me.drakeet.materialdialog.MaterialDialog;
import trophy.projetc2.Http.HttpClient;
import trophy.projetc2.MainActivity;
import trophy.projetc2.R;
import trophy.projetc2.Score.Score_MyAdapter;
import trophy.projetc2.Score.Score_MyData;
import trophy.projetc2.Score.Score_Team;

import static trophy.projetc2.MainActivity.activity;

/**
 * Created by 박효근 on 2017-03-08.
 */

public class TeamInfo extends AppCompatActivity {
    TextView TeamInfo_TextView_caution;
    ImageView TeamInfo_ImageView_Emblem, TeamInfo_ImageView_Image1;
    ImageView TeamInfo_ImageVIew_Back, TeamInfo_ImageVIew_TeamManger;
    TextView TeamInfo_TextView_Grade;
    TextView TeamInfo_TextView_TeamName, TeamInfo_TextView_TeamAddress_Do, TeamInfo_TextView_TeamAddress_Si, TeamInfo_TextView_HomeCourt;
    TextView TeamInfo_TextView_TeamIntro;
    TextView TeamInfo_TextView_Score_Focus;
    ListView TeamInfo_ListView_Player;ListView TeamInfo_ListView_Score;
    Button TeamInfo_Button_Out;
    RatingBar ratingBar;

    LinearLayout view;
    static Activity TeamInfo_activity;
    TeamInfo_Player_MyAdapter TeamInfo_Player_MyAdapter;
    ArrayList<TeamInfo_Player_MyData> TeamInfo_Player_MyData;
    ArrayList<Score_MyData> Score_MyData;
    Score_MyAdapter adapter;
    private String User_Pk, Team_Pk, TeamName, TeamDuty, TeamAddress_Do, TeamAddress_Si, HomeCourt, Introduce, Emblem, Image1, Image2, Image3;
    String[][] parseredData_teamInfo, parseredData_disperse, parsedData_Score, parsedData_Player,parsedData_Represent, parsedData_Grade;
    int JoinerCount=0,Row=0,Extra=0,PlayerCount=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_teaminfo);
        TeamInfo_activity = TeamInfo.this;
        view = (LinearLayout)findViewById(R.id.view);
        TeamInfo_ImageVIew_Back = (ImageView)findViewById(R.id.TeamInfo_ImageVIew_Back);
        TeamInfo_ImageVIew_TeamManger = (ImageView) findViewById(R.id.TeamInfo_ImageVIew_TeamManger);
        TeamInfo_TextView_caution = (TextView)findViewById(R.id.TeamInfo_TextView_caution);
        TeamInfo_ImageView_Emblem = (ImageView)findViewById(R.id.TeamInfo_ImageView_Emblem);
        TeamInfo_TextView_Grade = (TextView)findViewById(R.id.TeamInfo_TextView_Grade);
        TeamInfo_ImageView_Image1 = (ImageView)findViewById(R.id.TeamInfo_ImageView_Image1);
        TeamInfo_TextView_TeamName = (TextView)findViewById(R.id.TeamInfo_TextView_TeamName);
        TeamInfo_TextView_TeamAddress_Do = (TextView)findViewById(R.id.TeamInfo_TextView_TeamAddress_Do);
        TeamInfo_TextView_TeamAddress_Si = (TextView)findViewById(R.id.TeamInfo_TextView_TeamAddress_Si);
        TeamInfo_TextView_HomeCourt = (TextView)findViewById(R.id.TeamInfo_TextView_HomeCourt);
        TeamInfo_TextView_TeamIntro = (TextView)findViewById(R.id.TeamInfo_TextView_TeamIntro);
        TeamInfo_TextView_Score_Focus = (TextView)findViewById(R.id.TeamInfo_TextView_Score_Focus);
        TeamInfo_ListView_Player = (ListView)findViewById(R.id.TeamInfo_ListView_Player);
        TeamInfo_ListView_Score = (ListView)findViewById(R.id.TeamInfo_ListView_Score);
        TeamInfo_Button_Out = (Button)findViewById(R.id.TeamInfo_Button_Out);
        ratingBar = (RatingBar)findViewById(R.id.ratingBar);

        TeamInfo_TextView_TeamName.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/BMJUA_ttf.ttf"));
        Intent intent = getIntent();
        User_Pk = intent.getStringExtra("User_Pk");
        Team_Pk = intent.getStringExtra("Team_Pk");
        TeamName = intent.getStringExtra("TeamName");

        if(Team_Pk.equals(".")){
            view.setVisibility(View.GONE);

        }
        else{
            final HttpClient TeamInfo = new HttpClient();
            String result1 =TeamInfo.HttpClient("Trophy_part1","TeamInfo.jsp",TeamName);
            parseredData_teamInfo =  jsonParserList_getTeamInfo(result1);

            HttpClient http_Represent= new HttpClient();
            String result123 = http_Represent.HttpClient("Trophy_part1","TeamInfo_Represent.jsp", User_Pk,Team_Pk);
            parsedData_Represent = jsonParserList_Represent(result123);
            if(parsedData_Represent[0][0].equals("succed")){
                TeamDuty = "팀대표";
                TeamInfo_TextView_caution.setVisibility(View.GONE);
                TeamInfo_Button_Out.setBackgroundColor(getResources().getColor(R.color.DarkGray));
            }
            else{
                TeamDuty = "팀원";
                TeamInfo_TextView_caution.setVisibility(View.GONE);
            }


            TeamAddress_Do = parseredData_teamInfo[0][1];
            TeamAddress_Si = parseredData_teamInfo[0][2];
            HomeCourt = parseredData_teamInfo[0][3];
            Introduce = parseredData_teamInfo[0][4];
            Emblem = parseredData_teamInfo[0][5];
            Image1 = parseredData_teamInfo[0][6];
            Image2 = parseredData_teamInfo[0][7];
            Image3 = parseredData_teamInfo[0][8];

            if(Emblem.equals(".")) {
                Glide.with(TeamInfo.this).load(R.drawable.emblem).diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(TeamInfo_ImageView_Emblem);
            }else {
                Glide.with(TeamInfo.this).load("http://210.122.7.193:8080/Trophy_img/team/" + Emblem + ".jpg").bitmapTransform(new CropCircleTransformation(Glide.get(TeamInfo.this).getBitmapPool()))
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(TeamInfo_ImageView_Emblem);
            }
            TeamInfo_TextView_TeamName.setText(parseredData_teamInfo[0][0]);
            TeamInfo_TextView_TeamAddress_Do.setText(TeamAddress_Do);
            TeamInfo_TextView_TeamAddress_Si.setText(TeamAddress_Si);
            TeamInfo_TextView_HomeCourt.setText(HomeCourt);
            TeamInfo_TextView_TeamIntro.setText(Introduce);

            if(Image1.equals(".")) {
                //TeamInfo_ImageView_Image1.setVisibility(View.GONE);
                TeamInfo_ImageView_Image1.setBackgroundColor(getResources().getColor(R.color.main1color_back));
                TeamInfo_ImageView_Image1.setMinimumHeight(250);
            }else{
                Glide.with(TeamInfo.this).load("http://210.122.7.193:8080/Trophy_img/team/" + Image1 + ".jpg")
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(TeamInfo_ImageView_Image1);
            }

            //팀 평점
            HttpClient http_Grade= new HttpClient();
            String result1234 = http_Grade.HttpClient("Trophy_part1","TeamGrade.jsp",Team_Pk);
            parsedData_Grade = jsonParserList_Grade(result1234);
            if(parsedData_Grade[0][0].equals("NaN")){
                TeamInfo_TextView_Grade.setText("0.0");
                ratingBar.setRating(0);
            }
            else{
                TeamInfo_TextView_Grade.setText(parsedData_Grade[0][0]);
                Rating_Range(Double.parseDouble(parsedData_Grade[0][0]));
            }

            //연습게임 결과 리스트
            HttpClient http_score = new HttpClient();
            String result = http_score.HttpClient("Trophy_part1","Score_Team_Finish.jsp", Team_Pk);
            parsedData_Score = jsonParserList_Score(result);

            Score_MyData = new ArrayList<Score_MyData>();
            for (int i = 0; i < parsedData_Score.length; i++) {
                Score_MyData.add(new Score_MyData(User_Pk, parsedData_Score[i][0], parsedData_Score[i][1], parsedData_Score[i][2],parsedData_Score[i][3],parsedData_Score[i][4],parsedData_Score[i][5],parsedData_Score[i][6],parsedData_Score[i][7],parsedData_Score[i][8],parsedData_Score[i][9],"After",parsedData_Score[i][10],parsedData_Score[i][11],TeamInfo.this));
            }
            adapter = new Score_MyAdapter(this, Score_MyData);
            TeamInfo_ListView_Score.setAdapter(adapter);
            setListViewHeightBasedOnChildren(TeamInfo_ListView_Score);
            TeamInfo_TextView_Score_Focus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent1 = new Intent(TeamInfo.this, Score_Team.class);
                    intent1.putExtra("User_Pk", User_Pk);
                    intent1.putExtra("Team_Pk", Team_Pk);
                    startActivity(intent1);
                    overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                }
            });
            //팀원 리스트
            HttpClient http_Player= new HttpClient();
            String result12 = http_Player.HttpClient("Trophy_part1","TeamInfo_Player.jsp",TeamName, Team_Pk);
            parsedData_Player = jsonParserList_Player(result12);
            setData_Player();
            TeamInfo_Player_MyAdapter = new TeamInfo_Player_MyAdapter(TeamInfo.this, TeamInfo_Player_MyData);
            //리스트뷰에 어댑터 연결
            TeamInfo_ListView_Player.setAdapter(TeamInfo_Player_MyAdapter);
            setListViewHeightBasedOnChildren(TeamInfo_ListView_Player);

            TeamInfo_ImageVIew_Back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                    overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
                }
            });
            TeamInfo_ImageVIew_TeamManger.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(TeamDuty.equals("팀대표")){
                        Intent intent1 = new Intent(TeamInfo.this,TeamManager1.class);
                        intent1.putExtra("TeamName", TeamName);
                        intent1.putExtra("Team_Pk", Team_Pk);
                        intent1.putExtra("User_Pk", User_Pk);
                        startActivity(intent1);
                        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                    }
                    else{
                        Snackbar.make(view,"팀 관리 권한이 없습니다.",Snackbar.LENGTH_SHORT).show();
                    }
                }
            });
            TeamInfo_Button_Out.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(TeamDuty.equals("팀대표")){
                        Snackbar.make(view,"팀 대표는 대표인계 후 탈퇴 가능합니다.",Snackbar.LENGTH_SHORT).show();
                    }
                    else{
                        LayoutInflater inflater = (LayoutInflater)view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
                        final View layout = inflater.inflate(R.layout.layout_customdialog_1, (ViewGroup) view.findViewById(R.id.TeamInfo_Customdialog_1_Root));
                        final ImageView TeamInfo_Customdialog_1_Back = (ImageView)layout.findViewById(R.id.TeamInfo_Customdialog_1_Back);
                        final TextView TeamInfo_Customdialog_1_Content = (TextView)layout.findViewById(R.id.TeamInfo_Customdialog_1_Content);
                        final Button TeamInfo_Customdialog_1_Ok = (Button)layout.findViewById(R.id.TeamInfo_Customdialog_1_Ok);

                        TeamInfo_Customdialog_1_Content.setText("현재 참가중인 대회가 있을 시, 참가가 제한될 수 있습니다. \n팀을 탈퇴하시겠습니까?");
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
                                HttpClient http_Player= new HttpClient();
                                String result12 = http_Player.HttpClient("Trophy_part1","TeamInfo_Disperse.jsp",User_Pk);
                                parseredData_disperse = jsonParserList_getDisperse(result12);
                                if(parseredData_disperse[0][0].equals("succed")){
                                    Snackbar.make(view,"팀 탈퇴되었습니다.",Snackbar.LENGTH_INDEFINITE).setAction("확인", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            TeamInfo_Dialog.dismiss();
                                            activity.finish();
                                            startActivity(new Intent(TeamInfo.this, MainActivity.class));
                                            finish();
                                            overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
                                        }
                                    }).show();
                                }
                                else{
                                    Snackbar.make(view,"잠시 후 다시 시도해주시기 바랍니다.",Snackbar.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            });
        }
    }
    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
    }

    private String[][] jsonParserList_getTeamInfo(String pRecvServerPage) {
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try {
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jarr = json.getJSONArray("List");

            String[] jsonName = {"msg1", "msg2", "msg3", "msg4", "msg5", "msg6", "msg7","msg8","msg9"};
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
    public String[][] jsonParserList_Player(String pRecvServerPage) {
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try {
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");

            String[] jsonName = {"msg1","msg2","msg3","msg4","msg5"};
            String[][] parseredData = new String[jArr.length()][jsonName.length];
            for (int i = 0; i < jArr.length(); i++) {
                json = jArr.getJSONObject(i);
                for (int j = 0; j < jsonName.length; j++) {
                    parseredData[i][j] = json.getString(jsonName[j]);
                }
            }
            return parseredData;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    public String[][] jsonParserList_Represent(String pRecvServerPage) {
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try {
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");

            String[] jsonName = {"msg1"};
            String[][] parseredData = new String[jArr.length()][jsonName.length];
            for (int i = 0; i < jArr.length(); i++) {
                json = jArr.getJSONObject(i);
                for (int j = 0; j < jsonName.length; j++) {
                    parseredData[i][j] = json.getString(jsonName[j]);
                }
            }
            return parseredData;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    private String[][] jsonParserList_getDisperse(String pRecvServerPage) {
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
    private void setData_Player()
    {
        PlayerCount = parsedData_Player.length;
        Row = PlayerCount/4;
        Extra = PlayerCount%4;
        TeamInfo_Player_MyData = new ArrayList<TeamInfo_Player_MyData>();

        for(int i =0; i<Row; i++)
        {
            int _i = i*4;
            TeamInfo_Player_MyData.add(new TeamInfo_Player_MyData(parsedData_Player[_i][0],parsedData_Player[_i][1],parsedData_Player[_i][2],parsedData_Player[_i][4],parsedData_Player[_i+1][0],parsedData_Player[_i+1][1],parsedData_Player[_i+1][2],parsedData_Player[_i+1][4],parsedData_Player[_i+2][0],parsedData_Player[_i+2][1],parsedData_Player[_i+2][2],parsedData_Player[_i+2][4],parsedData_Player[_i+3][0],parsedData_Player[_i+3][1],parsedData_Player[_i+3][2],parsedData_Player[_i+3][4]));
        }
        if(Extra==0){
        }
        else if(Extra==1){
            TeamInfo_Player_MyData.add(new TeamInfo_Player_MyData(parsedData_Player[(4*Row)][0],parsedData_Player[(4*Row)][1],parsedData_Player[(4*Row)][2],parsedData_Player[(4*Row)][4],"null","null","null","null","null","null","null","null","null","null","null","null"));
        }else if(Extra==2){
            TeamInfo_Player_MyData.add(new TeamInfo_Player_MyData(parsedData_Player[(4*Row)][0],parsedData_Player[(4*Row)][1],parsedData_Player[(4*Row)][2],parsedData_Player[(4*Row)][4],parsedData_Player[(4*Row)+1][0],parsedData_Player[(4*Row)+1][1],parsedData_Player[(4*Row)+1][2],parsedData_Player[(4*Row)+1][4],"null","null","null","null","null","null","null","null"));
        }else if(Extra==3){
            TeamInfo_Player_MyData.add(new TeamInfo_Player_MyData(parsedData_Player[(4*Row)][0],parsedData_Player[(4*Row)][1],parsedData_Player[(4*Row)][2],parsedData_Player[(4*Row)][4],parsedData_Player[(4*Row)+1][0],parsedData_Player[(4*Row)+1][1],parsedData_Player[(4*Row)+1][2],parsedData_Player[(4*Row)+1][4],parsedData_Player[(4*Row)+2][0],parsedData_Player[(4*Row)+2][1],parsedData_Player[(4*Row)+2][2],parsedData_Player[(4*Row)+2][4],"null","null","null","null"));
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

    @Override
    protected void onRestart() {
        super.onRestart();
        final HttpClient TeamInfo = new HttpClient();
        String result1 =TeamInfo.HttpClient("Trophy_part1","TeamInfo.jsp",TeamName);
        parseredData_teamInfo =  jsonParserList_getTeamInfo(result1);

        HttpClient http_Represent= new HttpClient();
        String result123 = http_Represent.HttpClient("Trophy_part1","TeamInfo_Represent.jsp", User_Pk,Team_Pk);
        parsedData_Represent = jsonParserList_Represent(result123);
        if(parsedData_Represent[0][0].equals("succed")){
            TeamDuty = "팀대표";
            TeamInfo_TextView_caution.setVisibility(View.GONE);
            TeamInfo_Button_Out.setBackgroundColor(getResources().getColor(R.color.DarkGray));
        }
        else{
            TeamDuty = "팀원";
            TeamInfo_TextView_caution.setVisibility(View.GONE);
        }


        TeamAddress_Do = parseredData_teamInfo[0][1];
        TeamAddress_Si = parseredData_teamInfo[0][2];
        HomeCourt = parseredData_teamInfo[0][3];
        Introduce = parseredData_teamInfo[0][4];
        Emblem = parseredData_teamInfo[0][5];
        Image1 = parseredData_teamInfo[0][6];
        Image2 = parseredData_teamInfo[0][7];
        Image3 = parseredData_teamInfo[0][8];

        if(Emblem.equals(".")) {
            Glide.with(TeamInfo.this).load(R.drawable.emblem).diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(TeamInfo_ImageView_Emblem);
        }else {
            Glide.with(TeamInfo.this).load("http://210.122.7.193:8080/Trophy_img/team/" + Emblem + ".jpg").bitmapTransform(new CropCircleTransformation(Glide.get(TeamInfo.this).getBitmapPool()))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(TeamInfo_ImageView_Emblem);
        }
        TeamInfo_TextView_TeamName.setText(parseredData_teamInfo[0][0]);
        TeamInfo_TextView_TeamAddress_Do.setText(TeamAddress_Do);
        TeamInfo_TextView_TeamAddress_Si.setText(TeamAddress_Si);
        TeamInfo_TextView_HomeCourt.setText(HomeCourt);
        TeamInfo_TextView_TeamIntro.setText(Introduce);

        if(Image1.equals(".")) {
            //TeamInfo_ImageView_Image1.setVisibility(View.GONE);
            TeamInfo_ImageView_Image1.setBackgroundColor(getResources().getColor(R.color.main1color_back));
        }else{
            Glide.with(TeamInfo.this).load("http://210.122.7.193:8080/Trophy_img/team/" + Image1 + ".jpg")
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(TeamInfo_ImageView_Image1);
        }
        //팀원 정보
        //팀원 리스트
        HttpClient http_Player= new HttpClient();
        String result12 = http_Player.HttpClient("Trophy_part1","TeamInfo_Player.jsp",TeamName, Team_Pk);
        parsedData_Player = jsonParserList_Player(result12);
        setData_Player();
        TeamInfo_Player_MyAdapter = new TeamInfo_Player_MyAdapter(TeamInfo.this, TeamInfo_Player_MyData);
        //리스트뷰에 어댑터 연결
        TeamInfo_ListView_Player.setAdapter(TeamInfo_Player_MyAdapter);
        setListViewHeightBasedOnChildren(TeamInfo_ListView_Player);
    }
    private String[][] jsonParserList_Grade(String pRecvServerPage) {
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
    public String[][] jsonParserList_Score(String pRecvServerPage){
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try{
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");
            String[] jsonName = {"msg1","msg2","msg3","msg4","msg5","msg6","msg7","msg8", "msg9","msg10","msg11","msg12"};
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
    public void Rating_Range(double Rating){
        if(Rating == 0){
            ratingBar.setRating(0);
        }
        else if(Rating > 0 && Rating <= 0.5){
            ratingBar.setRating((float)0);
        }
        else if(Rating > 0.5 && Rating <= 1){
            ratingBar.setRating((float)0.5);
        }
        else if(Rating > 1 && Rating <= 1.5){
            ratingBar.setRating((float)1);
        }
        else if(Rating > 1.5 && Rating <= 2){
            ratingBar.setRating((float)1.5);
        }
        else if(Rating > 2 && Rating <= 2.5){
            ratingBar.setRating((float)2);
        }
        else if(Rating > 2.5 && Rating <= 3){
            ratingBar.setRating((float)2.5);
        }
        else if(Rating > 3 && Rating <= 3.5){
            ratingBar.setRating((float)3);
        }
        else if(Rating > 3.5 && Rating <= 4){
            ratingBar.setRating((float)3.5);
        }
        else if(Rating > 4 && Rating <= 4.5){
            ratingBar.setRating((float)4);
        }
        else if(Rating > 4.5 && Rating < 5){
            ratingBar.setRating((float)4.5);
        }
        else if(Rating == 5){
            ratingBar.setRating((float)5);
        }
    }
}
