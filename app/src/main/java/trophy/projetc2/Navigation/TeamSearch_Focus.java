package trophy.projetc2.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import trophy.projetc2.Http.HttpClient;
import trophy.projetc2.R;

/**
 * Created by 박효근 on 2017-01-03.
 */

public class TeamSearch_Focus extends AppCompatActivity {
    TextView Layout_Navigation_TeamSearch_Focus_EditText_TeamName;
    TextView Layout_Navigation_TeamSearch_Focus_EditText_TeamAddress_Do;
    TextView Layout_Navigation_TeamSearch_Focus_EditText_TeamAddress_Si;
    TextView Layout_Navigation_TeamSearch_Focus_EditText_HomeCourt;
    TextView Layout_Navigation_TeamSearch_Focus_EditText_Introduce;
    Button Layout_Navigation_TeamSearch_Focus_Button_Join;

    private String Pk, TeamName, TeamAddress_Do, TeamAddress_Si, HomeCourt, Introduce, Emblem;

    String[][] parseredData_teamInfo, parseredData_teamOverlap, parseredData_teamJoin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_navigation_teamsearch_focus);

        Intent intent = getIntent();
        Pk = intent.getStringExtra("Pk");
        TeamName = intent.getStringExtra("TeamName");

        HttpClient TeamInfo = new HttpClient();
        String result1 =TeamInfo.HttpClient("Trophy_part1","TeamSearch_Focus.jsp",TeamName);
        parseredData_teamInfo =  jsonParserList_getTeamInfo(result1);

        Layout_Navigation_TeamSearch_Focus_EditText_TeamName = (TextView)findViewById(R.id.Layout_Navigation_TeamSearch_Focus_EditText_TeamName);
        Layout_Navigation_TeamSearch_Focus_EditText_TeamAddress_Do = (TextView)findViewById(R.id.Layout_Navigation_TeamSearch_Focus_EditText_TeamAddress_Do);
        Layout_Navigation_TeamSearch_Focus_EditText_TeamAddress_Si = (TextView)findViewById(R.id.Layout_Navigation_TeamSearch_Focus_EditText_TeamAddress_Si);
        Layout_Navigation_TeamSearch_Focus_EditText_HomeCourt = (TextView)findViewById(R.id.Layout_Navigation_TeamSearch_Focus_EditText_HomeCourt);
        Layout_Navigation_TeamSearch_Focus_EditText_Introduce = (TextView)findViewById(R.id.Layout_Navigation_TeamSearch_Focus_EditText_Introduce);
        Layout_Navigation_TeamSearch_Focus_Button_Join = (Button)findViewById(R.id.Layout_Navigation_TeamSearch_Focus_Button_Join);

        TeamAddress_Do = parseredData_teamInfo[0][1];
        TeamAddress_Si = parseredData_teamInfo[0][2];
        HomeCourt = parseredData_teamInfo[0][3];
        Introduce = parseredData_teamInfo[0][4];
        Emblem = parseredData_teamInfo[0][5];

        Layout_Navigation_TeamSearch_Focus_EditText_TeamName.setText(TeamName);
        Layout_Navigation_TeamSearch_Focus_EditText_TeamAddress_Do.setText(TeamAddress_Do);
        Layout_Navigation_TeamSearch_Focus_EditText_TeamAddress_Si.setText(TeamAddress_Si);
        Layout_Navigation_TeamSearch_Focus_EditText_HomeCourt.setText(HomeCourt);
        Layout_Navigation_TeamSearch_Focus_EditText_Introduce.setText(Introduce);

        Layout_Navigation_TeamSearch_Focus_Button_Join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HttpClient TeamInfo = new HttpClient();
                String result =TeamInfo.HttpClient("Trophy_part1","TeamSearch_Focus_TeamJoin_OverLap.jsp",Pk);

                parseredData_teamOverlap = jsonParserList_teamOverlap(result);
                if(parseredData_teamOverlap[0][0].equals("overLap")){
                    Snackbar.make(view,"이미 다른 팀에 가입 중 이십니다.", Snackbar.LENGTH_SHORT).show();
                }else{
                    HttpClient TeamJoin = new HttpClient();
                    String result_join =TeamJoin.HttpClient("Trophy_part1","TeamSearch_Focus_Join.jsp",Pk,TeamName);
                    parseredData_teamJoin = jsonParserList_teamJoin(result_join);
                    if(parseredData_teamJoin[0][0].equals("succed")){
                        Snackbar.make(view, "가입 신청 완료", Snackbar.LENGTH_SHORT).show();
                        finish();
                    }
                    else {
                        Snackbar.make(view, "해당 팀에 이미 신청중입니다", Snackbar.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    private String[][] jsonParserList_getTeamInfo(String pRecvServerPage) {
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try {
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jarr = json.getJSONArray("List");

            String[] jsonName = {"msg1", "msg2", "msg3", "msg4", "msg5", "msg6"};
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
    public String[][] jsonParserList_teamOverlap(String pRecvServerPage){
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
    public String[][] jsonParserList_teamJoin(String pRecvServerPage){
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