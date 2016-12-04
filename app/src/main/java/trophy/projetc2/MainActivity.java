package trophy.projetc2;

import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;

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

import trophy.projetc2.Contest.Contests_Customlist_Adapter;
import trophy.projetc2.Contest.Contests_Customlist_MyData;
import trophy.projetc2.Http.HttpClient;

public class MainActivity extends AppCompatActivity {
    String[][] pa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
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
        HttpClient a= new HttpClient();
        Log.i("결과",a.HttpClient("Web_basket","NaviTeamInfo_Player.jsp","park123"));

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
}
