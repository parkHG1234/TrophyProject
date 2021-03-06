package trophy.projetc2.Match;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import trophy.projetc2.Http.HttpClient;
import trophy.projetc2.R;
import trophy.projetc2.User.Login;

/**
 * Created by 박효근 on 2017-04-04.
 */

public class Match extends AppCompatActivity{
    ListView Match_ListView_List;
    ImageView Match_ImageView_Back, Match_ImageView_Write;
    Button Match_Button_Match, Match_Button_MyMatch,Match_Button_JoinMatch;
    String[][] parsedData_Match,parsedData_MyMatch,parsedData_JoinMatch;
    String Team_Pk, Team_Duty, User_Pk;
    int ContentCount = 100000;
    boolean lastitemVisibleFlag = false;
    Match_MyAdapter adapter;MyMatch_MyAdapter MyMatch_adapter;JoinMatch_MyAdapter JoinMatch_adapter;
    ArrayList<Match_MyData> Match_MyData;ArrayList<JoinMatch_MyData> JoinMatch_MyData;
    ArrayList<MyMatch_MyData> MyMatch_MyData;
    SwipeRefreshLayout mSwipeRefreshLayout;
    String strCurAll;
    int MenuChoice = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_navigation_match);
        currentTime();
        Intent intent1 = getIntent();
        User_Pk = intent1.getStringExtra("User_Pk");
        Team_Pk = intent1.getStringExtra("Team_Pk");
        Team_Duty = intent1.getStringExtra("Team_Duty");

        Match_ImageView_Back = (ImageView)findViewById(R.id.Match_ImageView_Back);
        Match_ListView_List = (ListView)findViewById(R.id.Match_ListView_List);
        Match_ImageView_Write = (ImageView)findViewById(R.id.Match_ImageView_Write);
        Match_Button_Match = (Button)findViewById(R.id.Match_Button_Match);
        Match_Button_MyMatch = (Button)findViewById(R.id.Match_Button_MyMatch);
        Match_Button_JoinMatch = (Button)findViewById(R.id.Match_Button_JoinMatch);
        Match_Button_MyMatch.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/BMJUA_ttf.ttf"));
        Match_Button_JoinMatch.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/BMJUA_ttf.ttf"));
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(MenuChoice == 1){
                    MenuChoice_1();
                }
                else if(MenuChoice == 2){
                    MenuChoice_2();
                }
                else{
                    MenuChoice_3();
                }
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        HttpClient http_match = new HttpClient();
        String result = http_match.HttpClient("Trophy_part1","Match.jsp");
        parsedData_Match = jsonParserList_Match(result);

        Match_MyData = new ArrayList<Match_MyData>();
        for (int i = 0; i < parsedData_Match.length; i++) {
            String Status = getStatus(parsedData_Match[i][9], parsedData_Match[i][4], parsedData_Match[i][10]);
            Match_MyData.add(new Match_MyData(parsedData_Match[i][0], parsedData_Match[i][1], parsedData_Match[i][2],parsedData_Match[i][3],parsedData_Match[i][4],parsedData_Match[i][5],parsedData_Match[i][6],Status,Match.this,User_Pk,parsedData_Match[i][9],Team_Pk,parsedData_Match[i][10]));
            ContentCount = Integer.parseInt(parsedData_Match[i][8]);
        }
        Log.i("tt123", Integer.toString(ContentCount));
        adapter = new Match_MyAdapter(this, Match_MyData);
        Match_ListView_List.setAdapter(adapter);
        Match_Button_Match.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MenuChoice_1();
            }
        });
        Match_Button_MyMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(User_Pk.equals(".")){
                    Intent intent_login = new Intent(Match.this, Login.class);
                    startActivity(intent_login);
                    overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                }
                else{
                    if(Team_Duty.equals("팀원")||Team_Duty.equals("팀대표")){
                        MenuChoice_2();
                    }
                    else{
                        Snackbar.make(view, "팀 가입 후 이용해주세요.", Snackbar.LENGTH_LONG).show();
                    }
                }

            }
        });
        Match_Button_JoinMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(User_Pk.equals(".")){
                    Intent intent_login = new Intent(Match.this, Login.class);
                    startActivity(intent_login);
                    overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                }
                else{
                    if(Team_Duty.equals("팀원")||Team_Duty.equals("팀대표")){
                        MenuChoice_3();
                    }
                    else{
                        Snackbar.make(view, "팀 가입 후 이용해주세요.", Snackbar.LENGTH_LONG).show();
                    }
                }
                MenuChoice_3();
            }
        });
        Match_ImageView_Write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(User_Pk.equals(".")){
                    Intent intent_login = new Intent(Match.this, Login.class);
                    startActivity(intent_login);
                    overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                }
                else{
                    if(Team_Duty.equals("팀원")||Team_Duty.equals("팀대표")){
                        Intent intent1 = new Intent(Match.this, Match_Write.class);
                        intent1.putExtra("Team_Duty", Team_Duty);
                        intent1.putExtra("Team_Pk", Team_Pk);
                        intent1.putExtra("User_Pk", User_Pk);
                        startActivity(intent1);
                        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                    }
                    else{
                        Snackbar.make(view, "팀 가입 후 이용해주세요.", Snackbar.LENGTH_LONG).show();
                    }
                }
            }
        });

        Match_ListView_List.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override

            public void onScrollStateChanged(AbsListView absListView, int i) {
                    if (i == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && lastitemVisibleFlag) {
                        Match_Scroll Match_Scroll = new Match_Scroll();
                        Match_Scroll.execute();
                    }
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                lastitemVisibleFlag = (totalItemCount > 0) && (firstVisibleItem + visibleItemCount >= totalItemCount);
            }
        });
        Match_ImageView_Back.setOnClickListener(new View.OnClickListener() {
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

    @Override
    protected void onRestart() {
        super.onRestart();
        MenuChoice_1();
    }

    public String[][] jsonParserList_Match(String pRecvServerPage){
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try{
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");
            String[] jsonName = {"msg1","msg2","msg3","msg4","msg5","msg6","msg7","msg8", "msg9","msg10","msg11"};
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
    public String[][] jsonParserList_MyMatch(String pRecvServerPage){
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try{
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");
            String[] jsonName = {"msg1","msg2","msg3","msg4","msg5","msg6","msg7","msg8","msg9","msg10","msg11"};
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
    public String[][] jsonParserList_JoinMatch(String pRecvServerPage){
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try{
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");
            String[] jsonName = {"msg1","msg2","msg3","msg4","msg5","msg6","msg7","msg8","msg9","msg10"};
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
    public class Match_Scroll extends AsyncTask<String, Void, String> {
        ProgressDialog asyncDialog = new ProgressDialog(Match.this);
        String[][] parsedData;

        @Override
        protected void onPreExecute() {
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage("잠시만 기다려주세요..");
            // show dialog
            asyncDialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            Log.i("ggg",Integer.toString(ContentCount));
            try {
                HttpClient http_match = new HttpClient();
                String result1 = http_match.HttpClient("Trophy_part1","Match_Scroll.jsp",Integer.toString(ContentCount));
                parsedData_Match = jsonParserList_Match(result1);

                for (int j = 0; j < parsedData_Match.length; j++) {
                    String Status = getStatus(parsedData_Match[j][9], parsedData_Match[j][4], parsedData_Match[j][10]);
                    Match_MyData.add(new Match_MyData(parsedData_Match[j][0], parsedData_Match[j][1], parsedData_Match[j][2],parsedData_Match[j][3],parsedData_Match[j][4],parsedData_Match[j][5],parsedData_Match[j][6],Status,Match.this,User_Pk,parsedData_Match[j][9],Team_Pk,parsedData_Match[j][10]));ContentCount = Integer.parseInt(parsedData_Match[j][8]);
                }
                adapter = new Match_MyAdapter(Match.this, Match_MyData);
                //adapter.notifyDataSetChanged();
                return "succed";
            } catch (Exception e) {
                e.printStackTrace();
                return "failed";
            }

        }

        @Override
        protected void onPostExecute(String result) {
            asyncDialog.dismiss();
            super.onPostExecute(result);
        }
        public String getStatus(String Match_Date, String StartTime, String FinishTime){
            String str1 = Match_Date;
            String[] data1 = str1.split(":::");
            String str2 = data1[1];
            String[] data2 = str2.split(" / ");
            if(Integer.parseInt(data2[1]) < 10){
                data2[1] = "0"+data2[1];
            }
            String str3 = StartTime;
            String[] data3 = str3.split(":");
            if(Integer.parseInt(data3[1]) < 10){
                data3[1] = "0"+data3[1];
            }
            if(Integer.parseInt(data3[0]) < 10){
                data3[0] = "0"+data3[0];
            }
            String str4 = FinishTime;
            String[] data4 = str4.split(":");
            if(Integer.parseInt(data4[1]) < 10){
                data4[1] = "0"+data4[1];
            }
            if(Integer.parseInt(data4[0]) < 10){
                data4[0] = "0"+data4[0];
            }
            ////////////////////////////////////////////////////////////////////
            String match_starttime = data1[0]+data2[0]+data2[1]+data3[0]+data3[1];
            String match_finishtime = data1[0]+data2[0]+data2[1]+data4[0]+data4[1];
            if(Long.parseLong(strCurAll) < Long.parseLong(match_starttime)){
                return "recruiting";
            }
            else{
                return "finish";
            }
        }
    }
    public String getStatus(String Match_Date, String StartTime, String FinishTime){
        String str1 = Match_Date;
        String[] data1 = str1.split(":::");
        String str2 = data1[1];
        String[] data2 = str2.split(" / ");
        if(Integer.parseInt(data2[1]) < 10){
            data2[1] = "0"+data2[1];
        }
        String str3 = StartTime;
        String[] data3 = str3.split(":");
        if(Integer.parseInt(data3[1]) < 10){
            data3[1] = "0"+data3[1];
        }
        if(Integer.parseInt(data3[0]) < 10){
            data3[0] = "0"+data3[0];
        }
        String str4 = FinishTime;
        String[] data4 = str4.split(":");
        if(Integer.parseInt(data4[1]) < 10){
            data4[1] = "0"+data4[1];
        }
        if(Integer.parseInt(data4[0]) < 10){
            data4[0] = "0"+data4[0];
        }
        ////////////////////////////////////////////////////////////////////
        String match_starttime = data1[0]+data2[0]+data2[1]+data3[0]+data3[1];
        String match_finishtime = data1[0]+data2[0]+data2[1]+data4[0]+data4[1];
        if(Long.parseLong(strCurAll) < Long.parseLong(match_starttime)){
            return "recruiting";
        }
        else{
            return "finish";
        }
    }
    public void currentTime(){
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat ALLFormat = new SimpleDateFormat("yyyyMMddHHmm");
        strCurAll = ALLFormat.format(date);
        Log.i("test1234",strCurAll);
    }
    public void MenuChoice_1(){
        MenuChoice =1 ;
        Match_Button_Match.setBackgroundColor(getResources().getColor(R.color.main1color));
        Match_Button_MyMatch.setBackgroundColor(getResources().getColor(R.color.BlackGray));
        Match_Button_JoinMatch.setBackgroundColor(getResources().getColor(R.color.BlackGray));
        HttpClient http_match = new HttpClient();
        String result = http_match.HttpClient("Trophy_part1","Match.jsp");
        parsedData_Match = jsonParserList_Match(result);

        Match_MyData = new ArrayList<Match_MyData>();
        for (int i = 0; i < parsedData_Match.length; i++) {
            String Status = getStatus(parsedData_Match[i][9], parsedData_Match[i][4], parsedData_Match[i][10]);
            Match_MyData.add(new Match_MyData(parsedData_Match[i][0], parsedData_Match[i][1], parsedData_Match[i][2],parsedData_Match[i][3],parsedData_Match[i][4],parsedData_Match[i][5],parsedData_Match[i][6],Status,Match.this,User_Pk,parsedData_Match[i][9],Team_Pk,parsedData_Match[i][10]));
            ContentCount = Integer.parseInt(parsedData_Match[i][8]);
        }
        adapter = new Match_MyAdapter(Match.this, Match_MyData);
        Match_ListView_List.setAdapter(adapter);
    }
    public void MenuChoice_2(){
        MenuChoice = 2;
        Match_Button_Match.setBackgroundColor(getResources().getColor(R.color.BlackGray));
        Match_Button_MyMatch.setBackgroundColor(getResources().getColor(R.color.main1color));
        Match_Button_JoinMatch.setBackgroundColor(getResources().getColor(R.color.BlackGray));
        HttpClient http_match = new HttpClient();
        String result = http_match.HttpClient("Trophy_part1","MyMatch.jsp", User_Pk);
        parsedData_MyMatch = jsonParserList_MyMatch(result);


        MyMatch_MyData = new ArrayList<MyMatch_MyData>();
        for (int i = 0; i < parsedData_MyMatch.length; i++) {
            String Status = getStatus(parsedData_MyMatch[i][10], parsedData_MyMatch[i][4], parsedData_MyMatch[i][9]);
            MyMatch_MyData.add(new MyMatch_MyData(parsedData_MyMatch[i][0], parsedData_MyMatch[i][1], parsedData_MyMatch[i][2],parsedData_MyMatch[i][3],parsedData_MyMatch[i][4],parsedData_MyMatch[i][5],parsedData_MyMatch[i][6],Status,Match.this,User_Pk,parsedData_MyMatch[i][8],parsedData_MyMatch[i][9],parsedData_MyMatch[i][10]));
        }
        MyMatch_adapter = new MyMatch_MyAdapter(Match.this, MyMatch_MyData);
        Match_ListView_List.setAdapter(MyMatch_adapter);

    }
    public void MenuChoice_3(){
        MenuChoice = 3;
        Match_Button_Match.setBackgroundColor(getResources().getColor(R.color.BlackGray));
        Match_Button_MyMatch.setBackgroundColor(getResources().getColor(R.color.BlackGray));
        Match_Button_JoinMatch.setBackgroundColor(getResources().getColor(R.color.main1color));

        HttpClient http_joinmatch = new HttpClient();
        String result = http_joinmatch.HttpClient("Trophy_part1","JoinMatch.jsp",User_Pk);
        parsedData_JoinMatch = jsonParserList_JoinMatch(result);

        JoinMatch_MyData = new ArrayList<JoinMatch_MyData>();
        for (int i = 0; i < parsedData_JoinMatch.length; i++) {
            String Status = getStatus(parsedData_JoinMatch[i][9], parsedData_JoinMatch[i][4], parsedData_JoinMatch[i][8]);
            JoinMatch_MyData.add(new JoinMatch_MyData(parsedData_JoinMatch[i][0], parsedData_JoinMatch[i][1], parsedData_JoinMatch[i][2],parsedData_JoinMatch[i][3],parsedData_JoinMatch[i][4],parsedData_JoinMatch[i][5],parsedData_JoinMatch[i][6],parsedData_JoinMatch[i][7],Match.this,User_Pk,parsedData_JoinMatch[i][8],Status));
        }
        JoinMatch_adapter = new JoinMatch_MyAdapter(Match.this, JoinMatch_MyData);
        Match_ListView_List.setAdapter(JoinMatch_adapter);
    }
}
