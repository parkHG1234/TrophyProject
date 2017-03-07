package trophy.projetc2.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

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

import me.drakeet.materialdialog.MaterialDialog;
import trophy.projetc2.Contest.Contest_Detail;
import trophy.projetc2.Http.HttpClient;
import trophy.projetc2.R;
import trophy.projetc2.User.Login;

/**
 * Created by 박효근 on 2017-01-03.
 */

public class TeamSearch_Focus extends AppCompatActivity {
    ImageView Layout_Navigation_TeamSearch_Focus_ImageView_Emblem;
    TextView Layout_Navigation_TeamSearch_Focus_EditText_TeamName;
    TextView Layout_Navigation_TeamSearch_Focus_EditText_TeamAddress;
    TextView Layout_Navigation_TeamSearch_Focus_EditText_HomeCourt;
    TextView Layout_Navigation_TeamSearch_Focus_EditText_Introduce;
    ListView Layout_Navigation_TeamSearch_Focus_ListView_TeamPlayer;
    Button Layout_Navigation_TeamSearch_Focus_Button_Join;
    LinearLayout Layout_Navigation_TeamSearch_Focus_LinearLayout_Image1;
    LinearLayout Layout_Navigation_TeamSearch_Focus_LinearLayout_Image2;
    LinearLayout Layout_Navigation_TeamSearch_Focus_LinearLayout_Image3;
    ImageView Layout_Navigation_TeamSearch_Focus_ImageView_Image1;
    ImageView Layout_Navigation_TeamSearch_Focus_ImageView_Image2;
    ImageView Layout_Navigation_TeamSearch_Focus_ImageView_Image3;

    TeamSearch_Focus_Customlist_MyAdapter TeamSearch_Focus_Customlist_MyAdapter;
    ArrayList<TeamManager_PlayerManager_Customlist_MyData_Player> TeamManager_PlayerManager_Customlist_MyData_Player;

    private String Pk, TeamName, TeamAddress_Do, TeamAddress_Si, HomeCourt, Introduce, Emblem, Image1, Image2, Image3;
    String[][] parseredData_teamInfo, parseredData_teamOverlap, parseredData_teamJoin, parsedData_Player;
    int JoinerCount=0,Row=0,Extra=0,PlayerCount=0;
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

        Layout_Navigation_TeamSearch_Focus_ImageView_Emblem = (ImageView) findViewById(R.id.Layout_Navigation_TeamSearch_Focus_ImageView_Emblem);
        Layout_Navigation_TeamSearch_Focus_EditText_TeamName = (TextView)findViewById(R.id.Layout_Navigation_TeamSearch_Focus_EditText_TeamName);
        Layout_Navigation_TeamSearch_Focus_EditText_TeamAddress = (TextView)findViewById(R.id.Layout_Navigation_TeamSearch_Focus_EditText_TeamAddress);
        Layout_Navigation_TeamSearch_Focus_EditText_HomeCourt = (TextView)findViewById(R.id.Layout_Navigation_TeamSearch_Focus_EditText_HomeCourt);
        Layout_Navigation_TeamSearch_Focus_EditText_Introduce = (TextView)findViewById(R.id.Layout_Navigation_TeamSearch_Focus_EditText_Introduce);
        Layout_Navigation_TeamSearch_Focus_Button_Join = (Button)findViewById(R.id.Layout_Navigation_TeamSearch_Focus_Button_Join);
        Layout_Navigation_TeamSearch_Focus_ListView_TeamPlayer = (ListView)findViewById(R.id.Layout_Navigation_TeamSearch_Focus_ListView_TeamPlayer);
        Layout_Navigation_TeamSearch_Focus_LinearLayout_Image1 = (LinearLayout)findViewById(R.id.Layout_Navigation_TeamSearch_Focus_LinearLayout_Image1);
        Layout_Navigation_TeamSearch_Focus_LinearLayout_Image2 = (LinearLayout)findViewById(R.id.Layout_Navigation_TeamSearch_Focus_LinearLayout_Image2);
        Layout_Navigation_TeamSearch_Focus_LinearLayout_Image3 = (LinearLayout)findViewById(R.id.Layout_Navigation_TeamSearch_Focus_LinearLayout_Image3);
        Layout_Navigation_TeamSearch_Focus_ImageView_Image1 = (ImageView)findViewById(R.id.Layout_Navigation_TeamSearch_Focus_ImageView_Image1);
        Layout_Navigation_TeamSearch_Focus_ImageView_Image2 = (ImageView)findViewById(R.id.Layout_Navigation_TeamSearch_Focus_ImageView_Image2);
        Layout_Navigation_TeamSearch_Focus_ImageView_Image3 = (ImageView)findViewById(R.id.Layout_Navigation_TeamSearch_Focus_ImageView_Image3);

        TeamAddress_Do = parseredData_teamInfo[0][1];
        TeamAddress_Si = parseredData_teamInfo[0][2];
        HomeCourt = parseredData_teamInfo[0][3];
        Introduce = parseredData_teamInfo[0][4];
        Emblem = parseredData_teamInfo[0][5];
        Image1 = parseredData_teamInfo[0][6];
        Image2 = parseredData_teamInfo[0][7];
        Image3 = parseredData_teamInfo[0][8];

        if(Emblem.equals(".")) {
            Glide.with(TeamSearch_Focus.this).load(R.drawable.plus).diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(Layout_Navigation_TeamSearch_Focus_ImageView_Emblem);
        }else {
            Glide.with(TeamSearch_Focus.this).load("http://210.122.7.193:8080/Trophy_img/team/" + Emblem + ".jpg")
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(Layout_Navigation_TeamSearch_Focus_ImageView_Emblem);
        }

        Layout_Navigation_TeamSearch_Focus_EditText_TeamName.setText(TeamName);
        Layout_Navigation_TeamSearch_Focus_EditText_TeamAddress.setText(TeamAddress_Do+"  "+TeamAddress_Si);
        Layout_Navigation_TeamSearch_Focus_EditText_HomeCourt.setText(HomeCourt);
        Layout_Navigation_TeamSearch_Focus_EditText_Introduce.setText(Introduce);

        //팀원 리스트
        HttpClient http_Player= new HttpClient();
        String result12 = http_Player.HttpClient("Trophy_part1","TeamManager_Player.jsp",TeamName);
        parsedData_Player = jsonParserList_Player(result12);
        setData_Player();
        TeamSearch_Focus_Customlist_MyAdapter = new TeamSearch_Focus_Customlist_MyAdapter(TeamSearch_Focus.this, TeamManager_PlayerManager_Customlist_MyData_Player);
        //리스트뷰에 어댑터 연결
        Layout_Navigation_TeamSearch_Focus_ListView_TeamPlayer.setAdapter(TeamSearch_Focus_Customlist_MyAdapter);

        if(Image1.equals(".")) {
            Layout_Navigation_TeamSearch_Focus_LinearLayout_Image1.setVisibility(View.GONE);
        }else{
            Glide.with(TeamSearch_Focus.this).load("http://210.122.7.193:8080/Trophy_img/team/" + Image1 + ".jpg")
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(Layout_Navigation_TeamSearch_Focus_ImageView_Image1);
        }
        if(Image2.equals(".")){
            Layout_Navigation_TeamSearch_Focus_LinearLayout_Image2.setVisibility(View.GONE);
        }else{
            Glide.with(TeamSearch_Focus.this).load("http://210.122.7.193:8080/Trophy_img/team/" + Image2 + ".jpg")
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(Layout_Navigation_TeamSearch_Focus_ImageView_Image2);
        }
        if(Image3.equals(".")){
            Layout_Navigation_TeamSearch_Focus_LinearLayout_Image3.setVisibility(View.GONE);
        }else{
            Glide.with(TeamSearch_Focus.this).load("http://210.122.7.193:8080/Trophy_img/team/" + Image3 + ".jpg")
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(Layout_Navigation_TeamSearch_Focus_ImageView_Image3);
        }

        //팀 가입신청하기
        Layout_Navigation_TeamSearch_Focus_Button_Join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Pk.equals(".")){
                    final MaterialDialog loginDialog = new MaterialDialog(TeamSearch_Focus.this);
                    loginDialog.setTitle("로그인 하기")
                            .setMessage("팀 가입시 로그인이 필요합니다.")
                            .setPositiveButton("취소", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    loginDialog.dismiss();
                                }
                            }).setNegativeButton("로그인 하기", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent_login = new Intent(TeamSearch_Focus.this, Login.class);
                            startActivity(intent_login);
                            overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                            loginDialog.dismiss();
                        }
                    });
                    loginDialog.show();
                }
                else{
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
            }
        });
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
    public String[][] jsonParserList_Player(String pRecvServerPage) {
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try {
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");

            String[] jsonName = {"msg1","msg2","msg3","msg4"};
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
    private void setData_Player()
    {
        PlayerCount = parsedData_Player.length;
        Row = PlayerCount/3;
        Extra = PlayerCount%3;
        TeamManager_PlayerManager_Customlist_MyData_Player = new ArrayList<TeamManager_PlayerManager_Customlist_MyData_Player>();

        for(int i =0; i<Row; i++)
        {
            int _i = i*3;
            TeamManager_PlayerManager_Customlist_MyData_Player.add(new TeamManager_PlayerManager_Customlist_MyData_Player(parsedData_Player[_i][0],parsedData_Player[_i][1],parsedData_Player[_i][2],parsedData_Player[_i+1][0],parsedData_Player[_i+1][1],parsedData_Player[_i+1][2],parsedData_Player[_i+2][0],parsedData_Player[_i+2][1],parsedData_Player[_i+2][2]));
        }
        if(Extra==0){
        }
        else if(Extra==1){
            TeamManager_PlayerManager_Customlist_MyData_Player.add(new TeamManager_PlayerManager_Customlist_MyData_Player(parsedData_Player[(3*Row)][0],parsedData_Player[(3*Row)][1],parsedData_Player[(3*Row)][2],"null","null","null","null","null","null"));
        }else if(Extra==2){
            TeamManager_PlayerManager_Customlist_MyData_Player.add(new TeamManager_PlayerManager_Customlist_MyData_Player(parsedData_Player[(3*Row)][0],parsedData_Player[(3*Row)][1],parsedData_Player[(3*Row)][2],parsedData_Player[(3*Row)+1][0],parsedData_Player[(3*Row)+1][1],parsedData_Player[(3*Row)+1][2],"null","null","null"));
        }
    }
}