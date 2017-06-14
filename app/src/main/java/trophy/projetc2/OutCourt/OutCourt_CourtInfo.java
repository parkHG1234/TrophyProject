package trophy.projetc2.OutCourt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.tsengvn.typekit.TypekitContextWrapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import trophy.projetc2.Http.HttpClient;
import trophy.projetc2.R;

/**
 * Created by 박효근 on 2017-05-01.
 */

public class OutCourt_CourtInfo extends AppCompatActivity {
    TextView OutCourt_CourtInfo_TextView_Title;
    ImageView OutCourt_CourtInfo_ImageView_Back;
    ListView OutCourt_CourtInfo_ListView_Court;
    static String Address_Si = "";static String Address_Do = "";static String User_Pk = "";
    String[][] parsedData_Match;
    String strCurYear, strCurMonth, strCurDay, strCurHour,strCurMinute, strCurToday, strCurTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_navigation_outcourt_courtinfo);
        Intent intent1 = getIntent();
        Address_Si = intent1.getStringExtra("Address_Si");
        Address_Do = intent1.getStringExtra("Address_Do");
        User_Pk = intent1.getStringExtra("User_Pk");
        currentTime();

        OutCourt_CourtInfo_TextView_Title = (TextView)findViewById(R.id.OutCourt_CourtInfo_TextView_Title);
        OutCourt_CourtInfo_ImageView_Back = (ImageView)findViewById(R.id.OutCourt_CourtInfo_ImageView_Back);
        OutCourt_CourtInfo_ListView_Court = (ListView)findViewById(R.id.OutCourt_CourtInfo_ListView_Court);
        OutCourt_CourtInfo_TextView_Title.setText(Address_Si);

        final ArrayList<OutCourt_CourtInfo_MyData> OutCourt_CourtInfo_MyData;
        OutCourt_CourtInfo_MyData = new ArrayList<OutCourt_CourtInfo_MyData>();
        String str = Address_Si;
        String[] data = str.split("/");
        for(int i=0; i<data.length; i++)
        {
            HttpClient http_match = new HttpClient();
            String result = http_match.HttpClient("Trophy_part1","OutCourt.jsp", Address_Do,data[i], strCurToday);
            parsedData_Match = jsonParserList_Match(result);
            if(parsedData_Match[0][0] == "."){
            }else{
                for (int j = 0; j < parsedData_Match.length; j++) {
                    OutCourt_CourtInfo_MyData.add(new OutCourt_CourtInfo_MyData(parsedData_Match[j][0], parsedData_Match[j][1] + "  " + parsedData_Match[j][2],parsedData_Match[j][5],OutCourt_CourtInfo.this,User_Pk,parsedData_Match[j][3],parsedData_Match[j][4]));
                }
            }
        }
        final OutCourt_CourtInfo_MyAdapter adapter = new OutCourt_CourtInfo_MyAdapter(this, OutCourt_CourtInfo_MyData);
        OutCourt_CourtInfo_ListView_Court.setAdapter(adapter);

        OutCourt_CourtInfo_ImageView_Back.setOnClickListener(new View.OnClickListener() {
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
            String[] jsonName = {"msg1","msg2","msg3","msg4","msg5","msg6"};

            if(jArr.length()==0){
                String[][] parseredData = new String[1][1];
                parseredData[0][0] = ".";
                return parseredData;
            }else{
                String[][] parseredData = new String[jArr.length()][jsonName.length];
                for(int i = 0; i<jArr.length();i++){
                    json = jArr.getJSONObject(i);
                    for (int j=0;j<jsonName.length; j++){
                        parseredData[i][j] = json.getString(jsonName[j]);
                    }
                }
                return parseredData;
            }
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

}
