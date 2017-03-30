package trophy.projetc2.Contest;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
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

import me.drakeet.materialdialog.MaterialDialog;
import trophy.projetc2.R;

import static trophy.projetc2.Contest.Contest_Detail.Contest_Detail_activity;
import static trophy.projetc2.R.id.Contest_Detail_Form_Player_CustomList_Check;

/**
 * Created by 박효근 on 2016-11-13.
 */

public class Contest_Detail_Form extends AppCompatActivity {
    static String Pk;
    static String Contest_Pk;
    static String Contest_Name;
    static String Team_Pk;
    static String MyTeam;
    static String Phone;
    static String Name;
    static int Player=0;
    static int JoinerCount=0;
    static String[] JoinerId;

    String[][] parsedData_Profile,parsedData_Player,parsedData_Join_Team;
    TextView Contest_Detail_Form_Button_ContestName;
    TextView Contest_Detail_Form_Button_TeamName;
    TextView Contest_Detail_Form_Button_TeamLeader;
    TextView Contest_Detail_Form_Button_TeamPhone;
    Button Contest_Detail_Form_Input;
    ListView Contest_Detail_Form_ListView;
    TextView Contest_Detail_Form_JoinerCount;
    ImageView Contest_Detail_Form_Back;
    Contest_Detail_Form_Customlist_Adapter Contest_Detail_Form_Customlist_Adapter;
    ArrayList<Contest_Detail_Form_Customlist_MyData> Contest_Detail_Form_Customlist_MyData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_contest_detail_form);
        Contest_Detail_Form_Back = (ImageView)findViewById(R.id.Contest_Detail_Form_Back);
        Contest_Detail_Form_Button_ContestName = (TextView)findViewById(R.id.Contest_Detail_Form_Button_ContestName);
        Contest_Detail_Form_Button_TeamName = (TextView)findViewById(R.id.Contest_Detail_Form_Button_TeamName);
        Contest_Detail_Form_Button_TeamLeader = (TextView)findViewById(R.id.Contest_Detail_Form_Button_TeamLeader);
        Contest_Detail_Form_Button_TeamPhone = (TextView)findViewById(R.id.Contest_Detail_Form_Button_TeamPhone);
        Contest_Detail_Form_JoinerCount = (TextView)findViewById(R.id.Contest_Detail_Form_JoinerCount);
        Contest_Detail_Form_ListView = (ListView)findViewById(R.id.Contest_Detail_Form_ListView);
        Contest_Detail_Form_Input = (Button)findViewById(R.id.Contest_Detail_Form_Input);

        Contest_Detail_Form_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
            }
        });


        Intent intent = getIntent();
        Contest_Pk = intent.getStringExtra("Contest_Pk");
        Pk = intent.getStringExtra("Pk");
        Contest_Name = intent.getStringExtra("Contest_Name");

        trophy.projetc2.Http.HttpClient http_profile = new trophy.projetc2.Http.HttpClient();
        String result = http_profile.HttpClient("Trophy_part1","Contest_Detail_Form_Profile.jsp",Pk);
        parsedData_Profile = jsonParserList_UserInfo(result);
        MyTeam = parsedData_Profile[0][1];
        Phone = parsedData_Profile[0][2];
        Name = parsedData_Profile[0][0];
        Team_Pk = parsedData_Profile[0][3];

        Contest_Detail_Form_Button_ContestName.setText(Contest_Name);
        Contest_Detail_Form_Button_TeamName.setText(MyTeam);
        Contest_Detail_Form_Button_TeamLeader.setText(Name);
        Contest_Detail_Form_Button_TeamPhone.setText(Phone);

        trophy.projetc2.Http.HttpClient http_player = new trophy.projetc2.Http.HttpClient();
        String result1 = http_player.HttpClient("Trophy_part1","Contest_Detail_Form_Player.jsp",MyTeam);
        parsedData_Player = jsonParserList_Player(result1);

        setData_Player();
        Contest_Detail_Form_Customlist_Adapter = new Contest_Detail_Form_Customlist_Adapter(Contest_Detail_Form.this, Contest_Detail_Form_Customlist_MyData);
        //리스트뷰에 어댑터 연결
        Contest_Detail_Form_ListView.setAdapter(Contest_Detail_Form_Customlist_Adapter);
        Log.i("qwer11", Integer.toString(Player));
        JoinerId = new String[Player];
        for(int i=0; i<Player;i++){
            JoinerId[i]= "false";
        }
        Contest_Detail_Form_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                view.buildDrawingCache();
                Bitmap bitmap = view.getDrawingCache();
                int color = bitmap.getPixel(0, 0);
                Log.e("ChecktedText","Background Color: " + color);
                view.destroyDrawingCache();
                if(color == getResources().getColor(R.color.Red))
                {
                    JoinerCount--;
                    Contest_Detail_Form_JoinerCount.setText(Integer.toString(JoinerCount)+"명 참가");
                    view.setBackgroundColor(getResources().getColor(R.color.White));
                    JoinerId[i]="false";
                }
                else
                {
                    JoinerCount++;
                    Contest_Detail_Form_JoinerCount.setText(Integer.toString(JoinerCount)+"명 참가");
                    view.setBackgroundColor(getResources().getColor(R.color.Red));
                    JoinerId[i]=Contest_Detail_Form_Customlist_MyData.get(i).getPk();
                }
                Log.i("aa", Integer.toString(i));
            }
        });
//
        Contest_Detail_Form_Input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i = 0;i<Player;i++){
                    if(!JoinerId[i].equals("false")){
                        Log.i("Contest_Pk", Contest_Pk);
                        trophy.projetc2.Http.HttpClient http_form_joiner = new trophy.projetc2.Http.HttpClient();
                        http_form_joiner.HttpClient("Trophy_part1","Contest_Detail_Form_Join.jsp",JoinerId[i],Contest_Pk);
                    }
                }
                trophy.projetc2.Http.HttpClient http_form_join_team = new trophy.projetc2.Http.HttpClient();
                String result1 = http_form_join_team.HttpClient("Trophy_part1","Contest_Detail_Form_Join_Team.jsp",Contest_Pk,Team_Pk);
                parsedData_Join_Team = jsonParserList_Join_Team(result1);
                if(parsedData_Join_Team[0][0].equals("succed")){
                    final MaterialDialog recommendDialog = new MaterialDialog(Contest_Detail_Form.this);
                    recommendDialog
                            .setTitle("참가신청서")
                            .setMessage("참가신청이 완료되었습니다.")
                            .setPositiveButton("확인", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    recommendDialog.dismiss();
                                    finish();
                                    Contest_Detail_activity.finish();
                                    overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
                                }
                            });
                    recommendDialog.show();
                }
            }
        });
    }
    /////프로필 탭 사용자정보를 파싱합니다.//////////////////////////////////////////////////////////
    public String[][] jsonParserList_UserInfo(String pRecvServerPage) {
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try {
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");

            String[] jsonName = {"msg1", "msg2", "msg3","msg4"};
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
    public String[][] jsonParserList_Player(String pRecvServerPage){
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
    public String[][] jsonParserList_Join_Team(String pRecvServerPage) {
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try {
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");

            String[] jsonName = {"msg1"};
            String[][] parseredData = new String[jArr.length()][jsonName.length];
            Player = jsonName.length;
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
    private void setData_Player()
    {
        Contest_Detail_Form_Customlist_MyData = new ArrayList<Contest_Detail_Form_Customlist_MyData>();
        for(int i =0; i<parsedData_Player.length; i++)
        {
            Contest_Detail_Form_Customlist_MyData.add(new Contest_Detail_Form_Customlist_MyData(parsedData_Player[i][0],parsedData_Player[i][1],parsedData_Player[i][2],parsedData_Player[i][3],parsedData_Player[i][4]));
        }
        Player = parsedData_Player.length;
    }
}
