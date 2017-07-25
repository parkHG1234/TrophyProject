package trophy.projetc2.Score;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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

/**
 * Created by 박효근 on 2017-06-30.
 */

public class Score_Team extends AppCompatActivity {
    ImageView Score_Team_ImageView_Back;
    ListView Score_Team_ListView;
    Score_MyAdapter adapter;
    ArrayList<Score_MyData> Score_MyData;
    String[][] parsedData_Score;
    String strCurAll;
    String User_Pk,Team_Pk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_navigation_score_team);
        currentTime();
        Intent intent1 = getIntent();
        User_Pk = intent1.getStringExtra("User_Pk");
        Team_Pk = intent1.getStringExtra("Team_Pk");
        Score_Team_ImageView_Back = (ImageView) findViewById(R.id.Score_Team_ImageView_Back);
        Score_Team_ListView = (ListView)findViewById(R.id.Score_Team_ListView);

        HttpClient http_score = new HttpClient();
        String result = http_score.HttpClient("Trophy_part1","Score_Team.jsp", Team_Pk);
        parsedData_Score = jsonParserList_Score(result);

        Score_MyData = new ArrayList<Score_MyData>();
        for (int i = 0; i < parsedData_Score.length; i++) {
            String Game_Status = getStatus(parsedData_Score[i][1], parsedData_Score[i][4],parsedData_Score[i][5]);
            Log.i("ttt",Game_Status);
            Score_MyData.add(new Score_MyData(User_Pk, parsedData_Score[i][0], parsedData_Score[i][1], parsedData_Score[i][2],parsedData_Score[i][3],parsedData_Score[i][4],parsedData_Score[i][5],parsedData_Score[i][6],parsedData_Score[i][7],parsedData_Score[i][8],parsedData_Score[i][9],Game_Status,parsedData_Score[i][10],parsedData_Score[i][11],Score_Team.this));
        }
        adapter = new Score_MyAdapter(this, Score_MyData);
        Score_Team_ListView.setAdapter(adapter);

        Score_Team_ImageView_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
            }
        });
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
        Log.i("test1", strCurAll);
        Log.i("test2", match_starttime);
        if(Long.parseLong(strCurAll) < Long.parseLong(match_starttime)){
            return "Before";
        }
        else if((Long.parseLong(match_starttime) < Long.parseLong(strCurAll) &&  Long.parseLong(strCurAll) < Long.parseLong(match_finishtime))){
            return "Gameing";
        }
        else{
            return "After";
        }
    }
    public void currentTime(){
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat ALLFormat = new SimpleDateFormat("yyyyMMddHHmm");
        strCurAll = ALLFormat.format(date);
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
    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        HttpClient http_score = new HttpClient();
        String result = http_score.HttpClient("Trophy_part1","Score_Team.jsp", User_Pk);
        parsedData_Score = jsonParserList_Score(result);

        Score_MyData = new ArrayList<Score_MyData>();
        for (int i = 0; i < parsedData_Score.length; i++) {
            String Game_Status = getStatus(parsedData_Score[i][1], parsedData_Score[i][4],parsedData_Score[i][5]);
            Log.i("ttt",Game_Status);
            Score_MyData.add(new Score_MyData(User_Pk, parsedData_Score[i][0], parsedData_Score[i][1], parsedData_Score[i][2],parsedData_Score[i][3],parsedData_Score[i][4],parsedData_Score[i][5],parsedData_Score[i][6],parsedData_Score[i][7],parsedData_Score[i][8],parsedData_Score[i][9],Game_Status,parsedData_Score[i][10],parsedData_Score[i][11],Score_Team.this));
        }
        adapter = new Score_MyAdapter(this, Score_MyData);
        Score_Team_ListView.setAdapter(adapter);
    }
}
