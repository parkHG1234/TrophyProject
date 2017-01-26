package trophy.projetc2.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import trophy.projetc2.Contest.Contest_Detail_Form_Customlist_Adapter;
import trophy.projetc2.Contest.Contest_Detail_Form_Customlist_MyData;
import trophy.projetc2.Http.HttpClient;
import trophy.projetc2.R;

import static trophy.projetc2.Navigation.TeamManager.TeamManager_TeamName;

/**
 * Created by 박효근 on 2017-01-25.
 */

public class TeamManager_ContestJoin_ContestFocus extends AppCompatActivity{
    String Contest_Pk;
    String Contest_Image;
    String Contest_Title;
    String Contest_Status;
    String AcountName;
    String AcountNumber;
    ScrollView TeamManager_ContestJoin_ContestFocus_Scroll;
    ImageView TeamManager_ContestJoin_ContestFocus_ImageView_ContestImage;
    TextView TeamManager_ContestJoin_ContestFocus_TextView_ContestTitle;
    TextView TeamManager_ContestJoin_ContestFocus_TextView_JoinStatus;
    TextView TeamManager_ContestJoin_ContestFocus_TextView_AcountName;
    TextView TeamManager_ContestJoin_ContestFocus_TextView_AcountNumber;
    TextView TeamManager_ContestJoin_ContestFocus_TextView_JoinDate;
    TextView TeamManager_ContestJoin_ContestFocus_TextView_ContestDetailTitle;
    TextView TeamManager_ContestJoin_ContestFocus_TextView_ContestDate;
    TextView TeamManager_ContestJoin_ContestFocus_TextView_ContestDetail;
    TextView TeamManager_ContestJoin_ContestFocus_TextView_TeamName;
    TextView TeamManager_ContestJoin_ContestFocus_TextView_Represent;
    TextView TeamManager_ContestJoin_ContestFocus_TextView_RepresentPhone;
    ListView TeamManager_ContestJoin_ContestFocus_ListView_Player;
    trophy.projetc2.Contest.Contest_Detail_Form_Customlist_Adapter Contest_Detail_Form_Customlist_Adapter;
    ArrayList<trophy.projetc2.Contest.Contest_Detail_Form_Customlist_MyData> Contest_Detail_Form_Customlist_MyData;
    String[][] parsedData_joinerinfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_navigation_teammanager_contestjoin_contestfocus);

        TeamManager_ContestJoin_ContestFocus_Scroll = (ScrollView)findViewById(R.id.TeamManager_ContestJoin_ContestFocus_Scroll);
        TeamManager_ContestJoin_ContestFocus_ImageView_ContestImage = (ImageView)findViewById(R.id.TeamManager_ContestJoin_ContestFocus_ImageView_ContestImage);
        TeamManager_ContestJoin_ContestFocus_TextView_ContestTitle = (TextView) findViewById(R.id.TeamManager_ContestJoin_ContestFocus_TextView_ContestTitle);
        TeamManager_ContestJoin_ContestFocus_TextView_JoinStatus = (TextView) findViewById(R.id.TeamManager_ContestJoin_ContestFocus_TextView_JoinStatus);
        TeamManager_ContestJoin_ContestFocus_TextView_AcountName = (TextView) findViewById(R.id.TeamManager_ContestJoin_ContestFocus_TextView_AcountName);
        TeamManager_ContestJoin_ContestFocus_TextView_AcountNumber = (TextView) findViewById(R.id.TeamManager_ContestJoin_ContestFocus_TextView_AcountNumber);
        TeamManager_ContestJoin_ContestFocus_TextView_JoinDate = (TextView) findViewById(R.id.TeamManager_ContestJoin_ContestFocus_TextView_JoinDate);
        TeamManager_ContestJoin_ContestFocus_TextView_ContestDate = (TextView) findViewById(R.id.TeamManager_ContestJoin_ContestFocus_TextView_ContestDate);
        TeamManager_ContestJoin_ContestFocus_TextView_ContestDetailTitle = (TextView)findViewById(R.id.TeamManager_ContestJoin_ContestFocus_TextView_ContestDetailTitle);
        TeamManager_ContestJoin_ContestFocus_TextView_ContestDetail = (TextView) findViewById(R.id.TeamManager_ContestJoin_ContestFocus_TextView_ContestDetail);
        TeamManager_ContestJoin_ContestFocus_TextView_TeamName = (TextView) findViewById(R.id.TeamManager_ContestJoin_ContestFocus_TextView_TeamName);
        TeamManager_ContestJoin_ContestFocus_TextView_Represent = (TextView) findViewById(R.id.TeamManager_ContestJoin_ContestFocus_TextView_Represent);
        TeamManager_ContestJoin_ContestFocus_TextView_RepresentPhone = (TextView) findViewById(R.id.TeamManager_ContestJoin_ContestFocus_TextView_RepresentPhone);
        TeamManager_ContestJoin_ContestFocus_ListView_Player = (ListView) findViewById(R.id.TeamManager_ContestJoin_ContestFocus_ListView_Player);

        Intent intent = getIntent();
        Contest_Pk = intent.getStringExtra("Contest_Pk");
        Contest_Image = intent.getStringExtra("Contest_Image");
        Contest_Title = intent.getStringExtra("Contest_Title");
        Contest_Status = intent.getStringExtra("Contest_Status");
        AcountName = intent.getStringExtra("AcountName");
        AcountNumber = intent.getStringExtra("AcountNumber");

        HttpClient http_contestinfo= new HttpClient();
        String result = http_contestinfo.HttpClient("Trophy_part1","TeamManager_ContestJoin_ContestFocus_ContestInfo.jsp",Contest_Pk);
        String[][] parsedData_contestinfo = jsonParserList_ContestInfo(result);

        HttpClient http_representinfo= new HttpClient();
        String result1 = http_representinfo.HttpClient("Trophy_part1","TeamManager_ContestJoin_ContestFocus_Represent.jsp",TeamManager_TeamName);
        String[][] parsedData_representinfo = jsonParserList_represent(result1);

        HttpClient http_joinerinfo = new HttpClient();
        String result2 = http_joinerinfo.HttpClient("Trophy_part1","TeamManager_ContestJoin_ContestFocus_Joiner.jsp",TeamManager_TeamName,Contest_Pk);
        parsedData_joinerinfo = jsonParserList_joiner(result2);

        //대회이미지 로드
        Glide.with(TeamManager_ContestJoin_ContestFocus.this).load("http://210.122.7.193:8080/Web_basket/imgs1/Contest/"+Contest_Image+".jpg")
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(TeamManager_ContestJoin_ContestFocus_ImageView_ContestImage);
        //대회간략정보 로드
        TeamManager_ContestJoin_ContestFocus_TextView_ContestTitle.setText(Contest_Title);
        TeamManager_ContestJoin_ContestFocus_TextView_JoinStatus.setText(Contest_Status);
        TeamManager_ContestJoin_ContestFocus_TextView_AcountName.setText(AcountName);
        TeamManager_ContestJoin_ContestFocus_TextView_AcountNumber.setText(AcountNumber);
        TeamManager_ContestJoin_ContestFocus_TextView_JoinDate.setText(parsedData_contestinfo[0][0]+" ~ "+parsedData_contestinfo[0][1]);
        TeamManager_ContestJoin_ContestFocus_TextView_ContestDate.setText(parsedData_contestinfo[0][2]);
        TeamManager_ContestJoin_ContestFocus_TextView_ContestDetail.setText(parsedData_contestinfo[0][3]);
        TeamManager_ContestJoin_ContestFocus_TextView_TeamName.setText(TeamManager_TeamName);
        TeamManager_ContestJoin_ContestFocus_TextView_Represent.setText(parsedData_representinfo[0][0]);
        TeamManager_ContestJoin_ContestFocus_TextView_RepresentPhone.setText(parsedData_representinfo[0][1]);

        TeamManager_ContestJoin_ContestFocus_TextView_ContestDetail.setVisibility(View.GONE);
        TeamManager_ContestJoin_ContestFocus_TextView_ContestDetailTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TeamManager_ContestJoin_ContestFocus_TextView_ContestDetail.getVisibility() == View.VISIBLE){
                    Animation animation = new AlphaAnimation(1, 0);
                    animation.setDuration(1000);
                    TeamManager_ContestJoin_ContestFocus_TextView_ContestDetail.setVisibility(View.GONE);
                    TeamManager_ContestJoin_ContestFocus_TextView_ContestDetail.setAnimation(animation);
                    TeamManager_ContestJoin_ContestFocus_TextView_ContestDetailTitle.setText("유의사항 ▼ ");
                }else{
                    TeamManager_ContestJoin_ContestFocus_TextView_ContestDetail.setVisibility(View.VISIBLE);
                    Animation animation = new AlphaAnimation(0, 1);
                    animation.setDuration(1000);
                    TeamManager_ContestJoin_ContestFocus_TextView_ContestDetail.setVisibility(View.VISIBLE);
                    TeamManager_ContestJoin_ContestFocus_TextView_ContestDetail.setAnimation(animation);
                    TeamManager_ContestJoin_ContestFocus_TextView_ContestDetailTitle.setText("유의사항 ▲ ");
                }
            }
        });

        setData_Player();
        Contest_Detail_Form_Customlist_Adapter = new Contest_Detail_Form_Customlist_Adapter(TeamManager_ContestJoin_ContestFocus.this, Contest_Detail_Form_Customlist_MyData);
        //리스트뷰에 어댑터 연결
        TeamManager_ContestJoin_ContestFocus_ListView_Player.setAdapter(Contest_Detail_Form_Customlist_Adapter);

        TeamManager_ContestJoin_ContestFocus_ListView_Player.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                TeamManager_ContestJoin_ContestFocus_Scroll.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
    }
    public String[][] jsonParserList_ContestInfo(String pRecvServerPage) {
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try {
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");

            String[] jsonName = {"msg1", "msg2", "msg3", "msg4"};
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
    public String[][] jsonParserList_represent(String pRecvServerPage) {
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try {
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");

            String[] jsonName = {"msg1", "msg2"};
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
    public String[][] jsonParserList_joiner(String pRecvServerPage){
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
    private void setData_Player()
    {
        Contest_Detail_Form_Customlist_MyData = new ArrayList<Contest_Detail_Form_Customlist_MyData>();
        for(int i =0; i<parsedData_joinerinfo.length; i++)
        {
            Contest_Detail_Form_Customlist_MyData.add(new Contest_Detail_Form_Customlist_MyData(parsedData_joinerinfo[i][0],parsedData_joinerinfo[i][1],parsedData_joinerinfo[i][2],parsedData_joinerinfo[i][3],parsedData_joinerinfo[i][4]));
        }
        //Player = parsedData_joinerinfo.length;
    }
}
