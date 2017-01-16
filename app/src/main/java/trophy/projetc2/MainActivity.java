package trophy.projetc2;

import android.app.Dialog;
import android.content.Intent;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import me.drakeet.materialdialog.MaterialDialog;
import trophy.projetc2.Contest.Contests_Customlist_Adapter;
import trophy.projetc2.Contest.Contests_Customlist_MyData;
import trophy.projetc2.Http.HttpClient;
import trophy.projetc2.Navigation.TeamMake;
import trophy.projetc2.Navigation.TeamManager;
import trophy.projetc2.Navigation.TeamSearch;

public class MainActivity extends AppCompatActivity {
    String Pk, Name, Team, Profile;
    String[][] parseredData_user;
    private LayoutInflater inflater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        inflater=getLayoutInflater();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar123);
        toolbar.setTitle("트로피");
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ListView listView = (ListView) findViewById(R.id.Contest_ListView_contest);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //navigationView.setNavigationItemSelectedListener(this);
        //유저 네비게이션바
        HttpClient a= new HttpClient();
        Log.i("결과",a.HttpClient("Web_basket","NaviTeamInfo_Player.jsp","park123"));
        Pk="1";
        //네비게이션 메뉴 선언 및 연결
        final View aa = navigationView.inflateHeaderView(R.layout.layout_navigationbar);
        final ImageView Main_Navigation_ImageView_Profile = (ImageView)aa.findViewById(R.id.Main_Navigation_ImageView_Profile);
        final TextView Main_Navigation_TextView_Name = (TextView)aa.findViewById(R.id.Main_Navigation_TextView_Name);
        final TextView Main_Navigation_TextView_Team = (TextView)aa.findViewById(R.id.Main_Navigation_TextView_Team);
        final Button Main_Navigation_Button_TeamMake = (Button) aa.findViewById(R.id.Main_Navigation_Button_TeamMake);
        final Button Main_Navigation_Button_TeamManager = (Button) aa.findViewById(R.id.Main_Navigation_Button_TeamManager);
        final Button Main_Navigation_Button_TeamSearch = (Button) aa.findViewById(R.id.Main_Navigation_Button_TeamSearch);
        final Button Main_Navigation_Button_LastContest = (Button) aa.findViewById(R.id.Main_Navigation_Button_LastContest);
        final Button Main_Navigation_Button_Notice = (Button)aa.findViewById(R.id.Main_Navigation_Button_Notice);
        final Button Main_Navigation_Button_Suggest = (Button) aa.findViewById(R.id.Main_Navigation_Button_Suggest);
        final Button Main_Navigation_Button_Setting = (Button)aa.findViewById(R.id.Main_Navigation_Button_Setting);

        HttpClient user= new HttpClient();
        String result1 =user.HttpClient("Trophy_part1","User.jsp",Pk);
        Log.i("결과",result1);
        parseredData_user =  jsonParserList_User(result1);
        Name = parseredData_user[0][0];
        Team = parseredData_user[0][1];
        Profile = parseredData_user[0][2];
        //프로필 관리
        try{
            String Profile1 = URLEncoder.encode(Profile, "utf-8");
            if(Profile1.equals(".")){
                Glide.with(MainActivity.this).load(R.drawable.profile_basic_image).diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(Main_Navigation_ImageView_Profile);
            }
            else{
                Glide.with(MainActivity.this).load("http://210.122.7.193:8080/Trophy_part1/imgs/Profile/"+Profile1+".jpg") .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(Main_Navigation_ImageView_Profile);
            }
        }
        catch (UnsupportedEncodingException e){

        }
        Main_Navigation_TextView_Name.setText(Name);
        Main_Navigation_TextView_Team.setText(Team);

        //팀 만들기 이벤트
        Main_Navigation_Button_TeamMake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_TeamMake = new Intent(MainActivity.this, TeamMake.class);
                intent_TeamMake.putExtra("Pk", Pk);
                startActivity(intent_TeamMake);
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
            }
        });
        Main_Navigation_Button_TeamSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_TeamSearch = new Intent(MainActivity.this, TeamSearch.class);
                intent_TeamSearch.putExtra("Pk", Pk);
                startActivity(intent_TeamSearch);
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
            }
        });
        Main_Navigation_Button_TeamManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                final View layout = inflater.inflate(R.layout.layout_customdialog_navigation_teammanager, (ViewGroup) findViewById(R.id.Layout_CustomDialog_Navigation_TeamManager));
//                final LinearLayout ContestJoin = (LinearLayout)layout.findViewById(R.id.Layout_CustomDialog_Navigation_TeamManager_ContestJoin);
//                final LinearLayout PlayerManager = (LinearLayout)layout.findViewById(R.id.Layout_CustomDialog_Navigation_TeamManager_PlayerManager);
//                final LinearLayout TeamIntroduce = (LinearLayout)layout.findViewById(R.id.Layout_CustomDialog_Navigation_TeamManager_Teamintroduce);
//                final MaterialDialog DutyDialog = new MaterialDialog(MainActivity.this);
//                DutyDialog
//                        .setNegativeButton("취소", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                DutyDialog.dismiss();
//                            }
//                        })
//                        .setContentView(layout);
//                DutyDialog.show();
                Intent intent_TeamManager = new Intent(MainActivity.this, TeamManager.class);
                intent_TeamManager.putExtra("TeamName", Team);
                startActivity(intent_TeamManager);
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
            }
        });
        /////////////////////////////////////////////
        //리스트뷰
        HttpClient ContestHttp = new HttpClient();
        String result = ContestHttp.HttpClient("Trophy_part3", "Contest_Customlist.jsp");
        String[][] ContestsParsedList = jsonParserList_getContestList(result);
        Log.i("개 ㅄ이세요 ?", result);

        ArrayList<Contests_Customlist_MyData> Contests_Customlist_MyData;
        Contests_Customlist_MyData = new ArrayList<Contests_Customlist_MyData>();
        for (int i = 0; i < ContestsParsedList.length; i++) {
            Contests_Customlist_MyData.add(new Contests_Customlist_MyData(ContestsParsedList[i][0], ContestsParsedList[i][1],
                    ContestsParsedList[i][2], ContestsParsedList[i][3], ContestsParsedList[i][4], ContestsParsedList[i][5],
                    ContestsParsedList[i][6], ContestsParsedList[i][7], ContestsParsedList[i][8], ContestsParsedList[i][9],
                    ContestsParsedList[i][10], ContestsParsedList[i][11], ContestsParsedList[i][12]));
        }
        Contests_Customlist_Adapter Adapter = new Contests_Customlist_Adapter(this, Contests_Customlist_MyData);
        listView.setAdapter(Adapter);


    }

    private String[][] jsonParserList_getContestList(String pRecvServerPage) {
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try {
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jarr = json.getJSONArray("List");

            String[] jsonName = {"_Pk", "_Title", "_Image", "_currentNum", "_maxNum", "_Payment", "_Host", "_Management", "_Support", "_ContestDate", "_RecruitStartDate", "_RecruitFinishDate", "_DetailInfo"};
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
    public String[][] jsonParserList_User(String pRecvServerPage){
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try{
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");
            String[] jsonName = {"msg1","msg2","msg3"};
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
