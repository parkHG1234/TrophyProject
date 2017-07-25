package trophy.projetc2.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import trophy.projetc2.Http.HttpClient;
import trophy.projetc2.R;
import trophy.projetc2.User.Login;

/**
 * Created by 박효근 on 2017-04-18.
 */

public class TeamFocus  extends AppCompatActivity {
    TextView TeamInfo_TextView_caution;
    ImageView TeamInfo_ImageView_Emblem, TeamInfo_ImageView_Image1;
    ImageView TeamInfo_ImageVIew_Back, TeamInfo_ImageVIew_TeamManger;
    TextView TeamInfo_TextView_Grade;
    TextView TeamInfo_TextView_TeamName, TeamInfo_TextView_TeamAddress_Do, TeamInfo_TextView_TeamAddress_Si, TeamInfo_TextView_HomeCourt;
    TextView TeamInfo_TextView_TeamIntro;
    ListView TeamInfo_ListView_Player;
    Button TeamInfo_Button_Out;
    RatingBar ratingBar;

    TeamSearch_Focus_MyAdapter TeamSearch_Focus_MyAdapter;
    ArrayList<TeamSearch_Focus_MyData> TeamSearch_Focus_MyData;

    private String Team_Pk, TeamName, TeamDuty, TeamAddress_Do, TeamAddress_Si, HomeCourt, Introduce, Emblem, Image1, Image2, Image3;
    String[][] parseredData_teamInfo, parseredData_teamOverlap, parseredData_teamJoin, parsedData_Player,parsedData_Grade;
    int JoinerCount=0,Row=0,Extra=0,PlayerCount=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_teaminfo);
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
        TeamInfo_ListView_Player = (ListView)findViewById(R.id.TeamInfo_ListView_Player);
        TeamInfo_Button_Out = (Button)findViewById(R.id.TeamInfo_Button_Out);
        ratingBar = (RatingBar)findViewById(R.id.ratingBar);

        TeamInfo_TextView_caution.setVisibility(View.GONE);
        TeamInfo_Button_Out.setVisibility(View.GONE);
        TeamInfo_ImageVIew_TeamManger.setVisibility(View.INVISIBLE);
        Intent intent = getIntent();
        Team_Pk = intent.getStringExtra("Team_Pk");
        TeamName = intent.getStringExtra("TeamName");

        final HttpClient TeamInfo = new HttpClient();
        String result1 =TeamInfo.HttpClient("Trophy_part1","TeamInfo.jsp",TeamName);
        parseredData_teamInfo =  jsonParserList_getTeamInfo(result1);

        TeamAddress_Do = parseredData_teamInfo[0][1];
        TeamAddress_Si = parseredData_teamInfo[0][2];
        HomeCourt = parseredData_teamInfo[0][3];
        Introduce = parseredData_teamInfo[0][4];
        Emblem = parseredData_teamInfo[0][5];
        Image1 = parseredData_teamInfo[0][6];
        Image2 = parseredData_teamInfo[0][7];
        Image3 = parseredData_teamInfo[0][8];

        if(Emblem.equals(".")) {
            Glide.with(TeamFocus.this).load(R.drawable.emblem).diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(TeamInfo_ImageView_Emblem);
        }else {
            Glide.with(TeamFocus.this).load("http://210.122.7.193:8080/Trophy_img/team/" + Emblem + ".jpg")
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
            TeamInfo_ImageView_Image1.setVisibility(View.GONE);
            TeamInfo_ImageView_Image1.setMinimumHeight(250);
        }else{
            Glide.with(TeamFocus.this).load("http://210.122.7.193:8080/Trophy_img/team/" + Image1 + ".jpg")
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(TeamInfo_ImageView_Image1);
        }
        //팀 평점
        HttpClient http_Grade= new HttpClient();
        String result123 = http_Grade.HttpClient("Trophy_part1","TeamGrade.jsp",Team_Pk);
        parsedData_Grade = jsonParserList_Grade(result123);
        if(parsedData_Grade[0][0].equals("NaN")){
            TeamInfo_TextView_Grade.setText("0.0");
            ratingBar.setRating(0);
        }
        else{
            TeamInfo_TextView_Grade.setText(parsedData_Grade[0][0]);
            Rating_Range(Double.parseDouble(parsedData_Grade[0][0]));
        }
        //팀원 정보
        //팀원 리스트
        HttpClient http_Player= new HttpClient();
        String result12 = http_Player.HttpClient("Trophy_part1","TeamInfo_Player.jsp",TeamName, Team_Pk);
        parsedData_Player = jsonParserList_Player(result12);
        setData_Player();
        TeamSearch_Focus_MyAdapter = new TeamSearch_Focus_MyAdapter(TeamFocus.this, TeamSearch_Focus_MyData);
        //리스트뷰에 어댑터 연결
        TeamInfo_ListView_Player.setAdapter(TeamSearch_Focus_MyAdapter);
        setListViewHeightBasedOnChildren(TeamInfo_ListView_Player);

        TeamInfo_ImageVIew_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
            }
        });
        TeamInfo_Button_Out.setVisibility(View.GONE);

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
    private String[][] jsonParserList_teamJoin(String pRecvServerPage) {
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
    private String[][] jsonParserList_teamOverlap(String pRecvServerPage) {
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
        TeamSearch_Focus_MyData = new ArrayList<TeamSearch_Focus_MyData>();

        for(int i =0; i<Row; i++)
        {
            int _i = i*4;
            TeamSearch_Focus_MyData.add(new TeamSearch_Focus_MyData(parsedData_Player[_i][0],parsedData_Player[_i][1],parsedData_Player[_i][2],parsedData_Player[_i][4],parsedData_Player[_i+1][0],parsedData_Player[_i+1][1],parsedData_Player[_i+1][2],parsedData_Player[_i+1][4],parsedData_Player[_i+2][0],parsedData_Player[_i+2][1],parsedData_Player[_i+2][2],parsedData_Player[_i+2][4],parsedData_Player[_i+3][0],parsedData_Player[_i+3][1],parsedData_Player[_i+3][2],parsedData_Player[_i+3][4]));
        }
        if(Extra==0){
        }
        else if(Extra==1){
            TeamSearch_Focus_MyData.add(new TeamSearch_Focus_MyData(parsedData_Player[(4*Row)][0],parsedData_Player[(4*Row)][1],parsedData_Player[(4*Row)][2],parsedData_Player[(4*Row)][4],"null","null","null","null","null","null","null","null","null","null","null","null"));
        }else if(Extra==2){
            TeamSearch_Focus_MyData.add(new TeamSearch_Focus_MyData(parsedData_Player[(4*Row)][0],parsedData_Player[(4*Row)][1],parsedData_Player[(4*Row)][2],parsedData_Player[(4*Row)][4],parsedData_Player[(4*Row)+1][0],parsedData_Player[(4*Row)+1][1],parsedData_Player[(4*Row)+1][2],parsedData_Player[(4*Row)+1][4],"null","null","null","null","null","null","null","null"));
        }else if(Extra==3){
            TeamSearch_Focus_MyData.add(new TeamSearch_Focus_MyData(parsedData_Player[(4*Row)][0],parsedData_Player[(4*Row)][1],parsedData_Player[(4*Row)][2],parsedData_Player[(4*Row)][4],parsedData_Player[(4*Row)+1][0],parsedData_Player[(4*Row)+1][1],parsedData_Player[(4*Row)+1][2],parsedData_Player[(4*Row)+1][4],parsedData_Player[(4*Row)+2][0],parsedData_Player[(4*Row)+2][1],parsedData_Player[(4*Row)+2][2],parsedData_Player[(4*Row)+2][4],"null","null","null","null"));
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
        else if(Rating > 4.5 && Rating <= 5){
            ratingBar.setRating((float)4.5);
        }
        else if(Rating == 5){
            ratingBar.setRating((float)5);
        }
    }
}
