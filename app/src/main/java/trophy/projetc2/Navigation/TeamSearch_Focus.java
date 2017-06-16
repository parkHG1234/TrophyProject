package trophy.projetc2.Navigation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tsengvn.typekit.TypekitContextWrapper;

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
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import me.drakeet.materialdialog.MaterialDialog;
import trophy.projetc2.Contest.Contest_Detail;
import trophy.projetc2.Http.HttpClient;
import trophy.projetc2.Http.HttpClient_SSL;
import trophy.projetc2.R;
import trophy.projetc2.User.Login;

/**
 * Created by 박효근 on 2017-01-03.
 */

public class TeamSearch_Focus extends AppCompatActivity {
    TextView TeamInfo_TextView_caution;
    ImageView TeamInfo_ImageView_Emblem, TeamInfo_ImageView_Image1;
    ImageView TeamInfo_ImageVIew_Back, TeamInfo_ImageVIew_TeamManger;
    TextView TeamInfo_TextView_TeamName, TeamInfo_TextView_TeamAddress_Do, TeamInfo_TextView_TeamAddress_Si, TeamInfo_TextView_HomeCourt;
    TextView TeamInfo_TextView_TeamIntro;
    ListView TeamInfo_ListView_Player;
    Button TeamInfo_Button_Out;

    TeamSearch_Focus_MyAdapter TeamSearch_Focus_MyAdapter;
    ArrayList<TeamSearch_Focus_MyData> TeamSearch_Focus_MyData;

    private String User_Pk, Team_Pk, TeamName, TeamDuty, TeamAddress_Do, TeamAddress_Si, HomeCourt, Introduce, Emblem, Image1, Image2, Image3;
    String[][] parseredData_teamInfo, parseredData_teamOverlap, parseredData_teamJoin, parsedData_Player,parsedData_Represent;
    int JoinerCount=0,Row=0,Extra=0,PlayerCount=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_teaminfo);
        TeamInfo_ImageVIew_Back = (ImageView)findViewById(R.id.TeamInfo_ImageVIew_Back);
        TeamInfo_ImageVIew_TeamManger = (ImageView) findViewById(R.id.TeamInfo_ImageVIew_TeamManger);
        TeamInfo_TextView_caution = (TextView)findViewById(R.id.TeamInfo_TextView_caution);
        TeamInfo_ImageView_Emblem = (ImageView)findViewById(R.id.TeamInfo_ImageView_Emblem);
        TeamInfo_ImageView_Image1 = (ImageView)findViewById(R.id.TeamInfo_ImageView_Image1);
        TeamInfo_TextView_TeamName = (TextView)findViewById(R.id.TeamInfo_TextView_TeamName);
        TeamInfo_TextView_TeamAddress_Do = (TextView)findViewById(R.id.TeamInfo_TextView_TeamAddress_Do);
        TeamInfo_TextView_TeamAddress_Si = (TextView)findViewById(R.id.TeamInfo_TextView_TeamAddress_Si);
        TeamInfo_TextView_HomeCourt = (TextView)findViewById(R.id.TeamInfo_TextView_HomeCourt);
        TeamInfo_TextView_TeamIntro = (TextView)findViewById(R.id.TeamInfo_TextView_TeamIntro);
        TeamInfo_ListView_Player = (ListView)findViewById(R.id.TeamInfo_ListView_Player);
        TeamInfo_Button_Out = (Button)findViewById(R.id.TeamInfo_Button_Out);
        TeamInfo_TextView_caution.setVisibility(View.GONE);
        TeamInfo_ImageVIew_TeamManger.setVisibility(View.INVISIBLE);
        Intent intent = getIntent();
        User_Pk = intent.getStringExtra("User_Pk");
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

        TeamInfo_TextView_TeamName.setText(parseredData_teamInfo[0][0]);
        TeamInfo_TextView_TeamAddress_Do.setText(TeamAddress_Do);
        TeamInfo_TextView_TeamAddress_Si.setText(TeamAddress_Si);
        TeamInfo_TextView_HomeCourt.setText(HomeCourt);
        TeamInfo_TextView_TeamIntro.setText(Introduce);

        try {
            String En_Emblem = URLEncoder.encode(Emblem, "utf-8");
            String En_Image1 = URLEncoder.encode(Image1, "utf-8");
            String En_Image2 = URLEncoder.encode(Image2, "utf-8");
            String En_Image3 = URLEncoder.encode(Image3, "utf-8");
            if (En_Emblem.equals(".")) {
                Glide.with(TeamSearch_Focus.this).load(R.drawable.emblem).diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(TeamInfo_ImageView_Emblem);
            } else {
                Glide.with(TeamSearch_Focus.this).load("http://210.122.7.193:8080/Trophy_img/team/" + En_Emblem + ".jpg").bitmapTransform(new CropCircleTransformation(Glide.get(TeamSearch_Focus.this).getBitmapPool()))
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(TeamInfo_ImageView_Emblem);
            }
            if(En_Image1.equals(".")) {
                //TeamInfo_ImageView_Image1.setVisibility(View.GONE);
                TeamInfo_ImageView_Image1.setBackgroundColor(getResources().getColor(R.color.main1color_back));
            }else{
                Glide.with(TeamSearch_Focus.this).load("http://210.122.7.193:8080/Trophy_img/team/" + En_Image1 + ".jpg")
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(TeamInfo_ImageView_Image1);
            }

        } catch (UnsupportedEncodingException e) {

        }


        //팀원 정보
        //팀원 리스트
        HttpClient http_Player= new HttpClient();
        String result12 = http_Player.HttpClient("Trophy_part1","TeamInfo_Player.jsp",TeamName, Team_Pk);
        parsedData_Player = jsonParserList_Player(result12);
        setData_Player();
        TeamSearch_Focus_MyAdapter = new TeamSearch_Focus_MyAdapter(TeamSearch_Focus.this, TeamSearch_Focus_MyData);
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
        TeamInfo_Button_Out.setText("가입신청");
        TeamInfo_Button_Out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(User_Pk.equals(".")){
                    Intent intent_login = new Intent(TeamSearch_Focus.this, Login.class);
                    startActivity(intent_login);
                    overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                }
                else{
                    HttpClient TeamInfo = new HttpClient();
                    String result =TeamInfo.HttpClient("Trophy_part1","TeamSearch_Focus_TeamJoin_OverLap.jsp",User_Pk,Team_Pk);

                    parseredData_teamOverlap = jsonParserList_teamOverlap(result);
                    if(parseredData_teamOverlap[0][0].equals("Joined")){
                        Snackbar.make(view,"이미 다른 팀에 가입 중 이십니다.", Snackbar.LENGTH_SHORT).show();
                    }
                    else if(parseredData_teamOverlap[0][0].equals("ThisJoining")){
                        Snackbar.make(view,"이미 가입 신청하셨습니다.", Snackbar.LENGTH_SHORT).show();
                    }
                    else if(parseredData_teamOverlap[0][0].equals("Joining")){
                        LayoutInflater inflater = (LayoutInflater)view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
                        final View layout = inflater.inflate(R.layout.layout_customdialog_2choice, (ViewGroup) view.findViewById(R.id.Customdialog_2Choice_Root));
                        final TextView Customdialog_2Choice_Title = (TextView)layout.findViewById(R.id.Customdialog_2Choice_Title);
                        final ImageView Customdialog_2Choice_Back = (ImageView)layout.findViewById(R.id.Customdialog_2Choice_Back);
                        final TextView Customdialog_2Choice_Content = (TextView)layout.findViewById(R.id.Customdialog_2Choice_Content);
                        final Button Customdialog_2Choice_First = (Button)layout.findViewById(R.id.Customdialog_2Choice_First);
                        final Button Customdialog_2Choice_Second = (Button)layout.findViewById(R.id.Customdialog_2Choice_Second);
                        Customdialog_2Choice_Title.setText("팀 가입");
                        Customdialog_2Choice_Content.setText("이전 가입신청을 취소합니다.");
                        Customdialog_2Choice_First.setText("확 인");
                        Customdialog_2Choice_Second.setText("취 소");
                        final MaterialDialog TeamInfo_Dialog = new MaterialDialog(view.getContext());
                        TeamInfo_Dialog
                                .setContentView(layout)
                                .setCanceledOnTouchOutside(true);
                        TeamInfo_Dialog.show();
                        Customdialog_2Choice_Back.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                TeamInfo_Dialog.dismiss();
                            }
                        });
                        Customdialog_2Choice_First.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                HttpClient TeamJoin = new HttpClient();
                                String result_join =TeamJoin.HttpClient("Trophy_part1","TeamSearch_Focus_Join.jsp",User_Pk,Team_Pk);
                                parseredData_teamJoin = jsonParserList_teamJoin(result_join);
                                if(parseredData_teamJoin[0][0].equals("succed")){
                                    TeamInfo_Dialog.dismiss();
                                }
                            }
                        });
                        Customdialog_2Choice_Second.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                TeamInfo_Dialog.dismiss();
                            }
                        });
                    }
                    else{
                        HttpClient TeamJoin = new HttpClient();
                        String result_join =TeamJoin.HttpClient("Trophy_part1","TeamSearch_Focus_Join.jsp",User_Pk,Team_Pk);
                        parseredData_teamJoin = jsonParserList_teamJoin(result_join);
                        if(parseredData_teamJoin[0][0].equals("succed")){
                            Snackbar.make(view, "가입 신청되었습니다.", Snackbar.LENGTH_INDEFINITE).setAction("확인", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    finish();
                                }
                            }).show();
                        }
                        else {
                            Snackbar.make(view, "해당 팀에 이미 신청중입니다", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
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
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
