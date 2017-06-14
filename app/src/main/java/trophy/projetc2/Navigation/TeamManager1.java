package trophy.projetc2.Navigation;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import me.drakeet.materialdialog.MaterialDialog;
import trophy.projetc2.Http.HttpClient;
import trophy.projetc2.MainActivity;
import trophy.projetc2.R;

import static trophy.projetc2.MainActivity.activity;
import static trophy.projetc2.Navigation.TeamInfo.TeamInfo_activity;

/**
 * Created by 박효근 on 2017-06-04.
 */

public class TeamManager1 extends AppCompatActivity{
    ImageView TeamManager_ImageView_Emblem, TeamManager_ImageView_Image1;
    ImageView TeamManager_ImageVIew_Back, Teammanager_ImageView_disperse;
    TextView TeamManager_TextView_TeamName, TeamManager_TextView_TeamAddress_Do, TeamManager_TextView_TeamAddress_Si, TeamManager_TextView_HomeCourt;
    TextView TeamManager_TextView_TeamIntro;
    ListView TeamManager_ListView_Player;
    ImageView TeamManager_ImageVIew_ChangeArea, TeamManager_ImageVIew_HomeCourt, TeamManager_ImageVIew_TeamIntro,TeamManager_ImageVIew_TeamPlayer;
    TeamInfo_Player_MyAdapter TeamInfo_Player_MyAdapter;
    ArrayList<TeamInfo_Player_MyData> TeamInfo_Player_MyData;

    private String User_Pk, Team_Pk, TeamName, TeamDuty, TeamAddress_Do, TeamAddress_Si, HomeCourt, Introduce, Emblem, Image1, Image2, Image3;
    String[][] parseredData_teamInfo, parseredData_teamdisperse, parseredData_teamJoin, parsedData_Player,parsedData_Represent;
    int JoinerCount=0,Row=0,Extra=0,PlayerCount=0;
    private static String FileName;
    private int flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_teammanager);
        TeamManager_ImageVIew_Back = (ImageView)findViewById(R.id.TeamManager_ImageView_Back);
        Teammanager_ImageView_disperse = (ImageView) findViewById(R.id.Teammanager_ImageView_disperse);
        TeamManager_ImageView_Emblem = (ImageView)findViewById(R.id.TeamManager_ImageView_Emblem);
        TeamManager_ImageView_Image1 = (ImageView)findViewById(R.id.TeamManager_ImageView_Image1);
        TeamManager_TextView_TeamName = (TextView)findViewById(R.id.TeamManager_TextView_TeamName);
        TeamManager_TextView_TeamAddress_Do = (TextView)findViewById(R.id.TeamManager_TextView_TeamAddress_Do);
        TeamManager_TextView_TeamAddress_Si = (TextView)findViewById(R.id.TeamManager_TextView_TeamAddress_Si);
        TeamManager_TextView_HomeCourt = (TextView)findViewById(R.id.TeamManager_TextView_HomeCourt);
        TeamManager_TextView_TeamIntro = (TextView)findViewById(R.id.TeamManager_TextView_TeamIntro);
        TeamManager_ListView_Player = (ListView)findViewById(R.id.TeamManager_ListView_Player);
        TeamManager_ImageVIew_ChangeArea = (ImageView)findViewById(R.id.TeamManager_ImageVIew_ChangeArea);
        TeamManager_ImageVIew_HomeCourt =(ImageView)findViewById(R.id.TeamManager_ImageVIew_HomeCourt);
        TeamManager_ImageVIew_TeamIntro =(ImageView)findViewById(R.id.TeamManager_ImageVIew_TeamIntro);
        TeamManager_ImageVIew_TeamPlayer = (ImageView)findViewById(R.id.TeamManager_ImageVIew_TeamPlayer);

        TeamManager_TextView_TeamName.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/BMJUA_ttf.ttf"));
        Intent intent = getIntent();
        User_Pk = intent.getStringExtra("User_Pk");
        Team_Pk = intent.getStringExtra("Team_Pk");
        TeamName = intent.getStringExtra("TeamName");

        final HttpClient TeamInfo = new HttpClient();
        String result1 =TeamInfo.HttpClient("Trophy_part1","TeamInfo.jsp",TeamName);
        parseredData_teamInfo =  jsonParserList_getTeamInfo(result1);

        HttpClient http_Represent= new HttpClient();
        String result123 = http_Represent.HttpClient("Trophy_part1","TeamInfo_Represent.jsp", User_Pk,Team_Pk);
        parsedData_Represent = jsonParserList_Represent(result123);
        if(parsedData_Represent[0][0].equals("succed")){
            TeamDuty = "팀대표";
        }
        else{
            TeamDuty = "팀원";
        }


        TeamAddress_Do = parseredData_teamInfo[0][1];
        TeamAddress_Si = parseredData_teamInfo[0][2];
        HomeCourt = parseredData_teamInfo[0][3];
        Introduce = parseredData_teamInfo[0][4];
        Emblem = parseredData_teamInfo[0][5];
        Image1 = parseredData_teamInfo[0][6];
        Image2 = parseredData_teamInfo[0][7];
        Image3 = parseredData_teamInfo[0][8];

        if(Emblem.equals(".")) {
            Glide.with(TeamManager1.this).load(R.drawable.emblem).diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(TeamManager_ImageView_Emblem);
        }else {
            Glide.with(TeamManager1.this).load("http://210.122.7.193:8080/Trophy_img/team/" + Emblem + ".jpg").bitmapTransform(new CropCircleTransformation(Glide.get(TeamManager1.this).getBitmapPool()))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(TeamManager_ImageView_Emblem);
        }
        TeamManager_TextView_TeamName.setText(parseredData_teamInfo[0][0]);
        TeamManager_TextView_TeamAddress_Do.setText(TeamAddress_Do);
        TeamManager_TextView_TeamAddress_Si.setText(TeamAddress_Si);
        TeamManager_TextView_HomeCourt.setText(HomeCourt);
        TeamManager_TextView_TeamIntro.setText(Introduce);

        if(Image1.equals(".")) {
            TeamManager_ImageView_Image1.setBackgroundColor(getResources().getColor(R.color.main1color_back));
        }else{
            Glide.with(TeamManager1.this).load("http://210.122.7.193:8080/Trophy_img/team/" + Image1 + ".jpg")
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(TeamManager_ImageView_Image1);
        }


        //팀원 정보
        //팀원 리스트
        HttpClient http_Player= new HttpClient();
        String result12 = http_Player.HttpClient("Trophy_part1","TeamInfo_Player.jsp",TeamName, Team_Pk);
        parsedData_Player = jsonParserList_Player(result12);
        setData_Player();
        TeamInfo_Player_MyAdapter = new TeamInfo_Player_MyAdapter(TeamManager1.this, TeamInfo_Player_MyData);
        //리스트뷰에 어댑터 연결
        TeamManager_ListView_Player.setAdapter(TeamInfo_Player_MyAdapter);
        setListViewHeightBasedOnChildren(TeamManager_ListView_Player);

        TeamManager_ImageVIew_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
            }
        });
        Teammanager_ImageView_disperse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater)view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
                final View layout = inflater.inflate(R.layout.layout_customdialog_1, (ViewGroup) view.findViewById(R.id.TeamInfo_Customdialog_1_Root));
                final TextView TeamInfo_Customdialog_1_Title = (TextView)layout.findViewById(R.id.TeamInfo_Customdialog_1_Title);
                final ImageView TeamInfo_Customdialog_1_Back = (ImageView)layout.findViewById(R.id.TeamInfo_Customdialog_1_Back);
                final TextView TeamInfo_Customdialog_1_Content = (TextView)layout.findViewById(R.id.TeamInfo_Customdialog_1_Content);
                final Button TeamInfo_Customdialog_1_Ok = (Button)layout.findViewById(R.id.TeamInfo_Customdialog_1_Ok);
                TeamInfo_Customdialog_1_Title.setText("팀 해산");
                TeamInfo_Customdialog_1_Content.setText("팀 해산은 팀원이 팀 대표 1인일 경우에만 가능합니다. \n팀을 해산할 경우 현재 참가중인 대회에 자동 취소됩니다.\n 참가 확정중인 대회가 있을경우 관리자 문의 후 팀을 해산하시기 바랍니다.");
                final MaterialDialog TeamInfo_Dialog = new MaterialDialog(view.getContext());
                TeamInfo_Dialog
                        .setContentView(layout)
                        .setCanceledOnTouchOutside(true);
                TeamInfo_Dialog.show();
                TeamInfo_Customdialog_1_Back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TeamInfo_Dialog.dismiss();
                    }
                });
                TeamInfo_Customdialog_1_Ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        HttpClient http_TeamDisperse= new HttpClient();
                        String result12 = http_TeamDisperse.HttpClient("Trophy_part1","TeamManager_TeamDisperse.jsp",User_Pk,Team_Pk);
                        parseredData_teamdisperse = jsonParserList_teamDisperse(result12);
                        Log.i("test123", User_Pk);
                        if(parseredData_teamdisperse[0][0].equals("Exist_Player"))
                        {
                            Snackbar.make(view,"팀 대표 외 팀원이 존재합니다.",Snackbar.LENGTH_SHORT).show();
                        }
                        else if(parseredData_teamdisperse[0][0].equals("Exist_Contest"))
                        {
                            Snackbar.make(view,"참가 확정중인 대회가 있습니다.",Snackbar.LENGTH_SHORT).show();
                        }
                        else if(parseredData_teamdisperse[0][0].equals("Exist_Joiner"))
                        {
                            Snackbar.make(view,"가입 신청 중 인원이 있습니다.",Snackbar.LENGTH_SHORT).show();
                        }
                        else if(parseredData_teamdisperse[0][0].equals("Failed")){
                            Snackbar.make(view,"잠시 후 다시 시도해주시기 바랍니다.",Snackbar.LENGTH_SHORT).show();
                        }
                        else{
                            Snackbar.make(view,"팀이 해산되었습니다.",Snackbar.LENGTH_INDEFINITE).setAction("확인", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    TeamInfo_Dialog.dismiss();
                                    TeamManager1.this.finish();
                                    TeamInfo_activity.finish();
                                    startActivity(new Intent(TeamManager1.this, MainActivity.class));
                                    activity.finish();
                                    overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
                                }
                            }).show();
                        }
                    }
                });
            }
        });
        TeamManager_ImageView_Emblem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.layout_customdialog_album, (ViewGroup) view.findViewById(R.id.Layout_CustomDialog_Album_Root));
                final TextView TeamInfo_Customdialog_1_Title = (TextView)layout.findViewById(R.id.TeamInfo_Customdialog_1_Title);
                final Button Layout_CustomDialog_Album_BasicImage = (Button) layout.findViewById(R.id.Layout_CustomDialog_Album_BasicImage);
                final Button Layout_CustomDialog_Album_AlbumImage = (Button) layout.findViewById(R.id.Layout_CustomDialog_Album_AlbumImage);
                final Button Layout_CustomDialog_Album_Cancel = (Button) layout.findViewById(R.id.Layout_CustomDialog_Album_Cancel);
                TeamInfo_Customdialog_1_Title.setText("엠블렘 이미지 변경");
                final AlertDialog.Builder aDialog = new AlertDialog.Builder(view.getContext());
                aDialog.setView(layout);
                final AlertDialog ad = aDialog.create();
                ad.show();
                Layout_CustomDialog_Album_Cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ad.dismiss();
                    }
                });
                Layout_CustomDialog_Album_BasicImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Glide.with(TeamManager1.this).load(R.drawable.emblem).diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true)
                                .into(TeamManager_ImageView_Emblem);
                        HttpClient user = new HttpClient();
                        user.HttpClient("Trophy_part2", "TeamManager_TeamIntroduce_Image.jsp", TeamName, ".", "Emblem");
                        TeamManager_ImageView_Emblem.setImageResource(R.drawable.emblem);
                        ad.dismiss();
                    }
                });
                Layout_CustomDialog_Album_AlbumImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        HttpClient user = new HttpClient();
                        //사진 읽어오기위한 uri 작성하기.
                        Uri uri = Uri.parse("content://media/external/images/media");
                        //무언가 보여달라는 암시적 인텐트 객체 생성하기.
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        //인텐트에 요청을 덛붙인다.
                        intent.setAction(Intent.ACTION_PICK);
                        //모든 이미지
                        intent.setType("image/*");
                        //결과값을 받아오는 액티비티를 실행한다.
                        FileName = TeamName;
                        flag = 0;
                        startActivityForResult(intent, 0);
                        user.HttpClient("Trophy_part2", "TeamManager_TeamIntroduce_Image.jsp", TeamName, TeamName, "Emblem");
                        ad.dismiss();
                    }
                });
            }
        });
        TeamManager_ImageView_Image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.layout_customdialog_album, (ViewGroup) view.findViewById(R.id.Layout_CustomDialog_Album_Root));
                final TextView TeamInfo_Customdialog_1_Title = (TextView)layout.findViewById(R.id.TeamInfo_Customdialog_1_Title);
                final Button Layout_CustomDialog_Album_BasicImage = (Button) layout.findViewById(R.id.Layout_CustomDialog_Album_BasicImage);
                final Button Layout_CustomDialog_Album_AlbumImage = (Button) layout.findViewById(R.id.Layout_CustomDialog_Album_AlbumImage);
                final Button Layout_CustomDialog_Album_Cancel = (Button) layout.findViewById(R.id.Layout_CustomDialog_Album_Cancel);
                TeamInfo_Customdialog_1_Title.setText("배경이미지 변경");
                final AlertDialog.Builder aDialog = new AlertDialog.Builder(view.getContext());
                aDialog.setView(layout);
                final AlertDialog ad = aDialog.create();
                ad.show();
                Layout_CustomDialog_Album_Cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ad.dismiss();
                    }
                });
                Layout_CustomDialog_Album_BasicImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Glide.with(TeamManager1.this).load(R.color.main1color_back).diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true)
                                .into(TeamManager_ImageView_Image1);
                        HttpClient user = new HttpClient();
                        user.HttpClient("Trophy_part2", "TeamManager_TeamIntroduce_Image.jsp", TeamName, ".", "Image1");
                        TeamManager_ImageView_Image1.setBackgroundColor(getResources().getColor(R.color.main1color_back));
                        ad.dismiss();
                    }
                });
                Layout_CustomDialog_Album_AlbumImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        HttpClient user = new HttpClient();
                        //사진 읽어오기위한 uri 작성하기.
                        Uri uri = Uri.parse("content://media/external/images/media");
                        //무언가 보여달라는 암시적 인텐트 객체 생성하기.
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        //인텐트에 요청을 덛붙인다.
                        intent.setAction(Intent.ACTION_PICK);
                        //모든 이미지
                        intent.setType("image/*");
                        //결과값을 받아오는 액티비티를 실행한다.
                        FileName = TeamName + "1";
                        flag = 1;
                        startActivityForResult(intent, 0);
                        user.HttpClient("Trophy_part2", "TeamManager_TeamIntroduce_Image.jsp", TeamName, TeamName + "1", "Image1");
                        ad.dismiss();
                    }
                });
            }
        });
        TeamManager_ImageVIew_ChangeArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(TeamManager1.this, TeamManager_ChangeArea.class);
                intent1.putExtra("Team_Pk", Team_Pk);
                startActivity(intent1);
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
            }
        });
        TeamManager_ImageVIew_HomeCourt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(TeamManager1.this, TeamManager_ChangeHomeCourt.class);
                intent1.putExtra("Team_Pk", Team_Pk);
                startActivity(intent1);
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
            }
        });
        TeamManager_ImageVIew_TeamIntro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(TeamManager1.this, TeamManager_ChangeTeamIntro.class);
                intent1.putExtra("Team_Pk", Team_Pk);
                intent1.putExtra("Team_Intro", Introduce);
                startActivity(intent1);
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
            }
        });
        TeamManager_ImageVIew_TeamPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(TeamManager1.this, TeamManager_TeamPlayer.class);
                intent1.putExtra("Team_Pk", Team_Pk);
                intent1.putExtra("TeamName", TeamName);
                startActivity(intent1);
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
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
    private String[][] jsonParserList_getDisperse(String pRecvServerPage) {
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
        TeamInfo_Player_MyData = new ArrayList<TeamInfo_Player_MyData>();

        for(int i =0; i<Row; i++)
        {
            int _i = i*4;
            TeamInfo_Player_MyData.add(new TeamInfo_Player_MyData(parsedData_Player[_i][0],parsedData_Player[_i][1],parsedData_Player[_i][2],parsedData_Player[_i+1][0],parsedData_Player[_i+1][1],parsedData_Player[_i+1][2],parsedData_Player[_i+2][0],parsedData_Player[_i+2][1],parsedData_Player[_i+2][2],parsedData_Player[_i+3][0],parsedData_Player[_i+3][1],parsedData_Player[_i+3][2]));
        }
        if(Extra==0){
        }
        else if(Extra==1){
            TeamInfo_Player_MyData.add(new TeamInfo_Player_MyData(parsedData_Player[(4*Row)][0],parsedData_Player[(4*Row)][1],parsedData_Player[(4*Row)][2],"null","null","null","null","null","null","null","null","null"));
        }else if(Extra==2){
            TeamInfo_Player_MyData.add(new TeamInfo_Player_MyData(parsedData_Player[(4*Row)][0],parsedData_Player[(4*Row)][1],parsedData_Player[(4*Row)][2],parsedData_Player[(4*Row)+1][0],parsedData_Player[(4*Row)+1][1],parsedData_Player[(4*Row)+1][2],"null","null","null","null","null","null"));
        }else if(Extra==3){
            TeamInfo_Player_MyData.add(new TeamInfo_Player_MyData(parsedData_Player[(4*Row)][0],parsedData_Player[(4*Row)][1],parsedData_Player[(4*Row)][2],parsedData_Player[(4*Row)+1][0],parsedData_Player[(4*Row)+1][1],parsedData_Player[(4*Row)+1][2],parsedData_Player[(4*Row)+2][0],parsedData_Player[(4*Row)+2][1],parsedData_Player[(4*Row)+2][2],"null","null","null"));
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
    private String[][] jsonParserList_teamDisperse(String pRecvServerPage) {
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
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        try {
            //인텐트에 데이터가 담겨 왔다면
            if (!intent.getData().equals(null)) {
                //해당경로의 이미지를 intent에 담긴 이미지 uri를 이용해서 Bitmap형태로 읽어온다.
                Bitmap selPhoto = MediaStore.Images.Media.getBitmap(getContentResolver(), intent.getData());
                //이미지의 크기 조절하기.
                selPhoto = Bitmap.createScaledBitmap(selPhoto, 100, 100, true);
                //image_bt.setImageBitmap(selPhoto);//썸네일
                //화면에 출력해본다.
                //Profile_ImageVIew_Profile.setImageBitmap(selPhoto);
                Log.e("선택 된 이미지 ", "selPhoto : " + selPhoto);

                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                //선택한 이미지의 uri를 읽어온다.
                Uri selPhotoUri = intent.getData();
                Log.e("전송", "시~~작 ~~~~~!");
                //업로드할 서버의 url 주소
                String urlString = "";
                urlString = "http://210.122.7.193:8080/Trophy_part2/TeamManager_TeamIntroduce_Image_Upload.jsp";
                //절대경로를 획득한다!!! 중요~
                Cursor c = getContentResolver().query(Uri.parse(selPhotoUri.toString()), null, null, null, null);
                c.moveToNext();
                //업로드할 파일의 절대경로 얻어오기("_data") 로 해도 된다.
                String absolutePath = c.getString(c.getColumnIndex(MediaStore.MediaColumns.DATA));
                Log.e("###파일의 절대 경로###", absolutePath);
                //파일 업로드 시작!
                HttpFileUpload(urlString, "", absolutePath);
                if (flag == 0) {
                    String En_Profile = URLEncoder.encode(FileName, "utf-8");
                    Glide.with(TeamManager1.this).load("http://210.122.7.193:8080/Trophy_img/team/" + En_Profile + ".jpg").bitmapTransform(new CropCircleTransformation(Glide.get(TeamManager1.this).getBitmapPool()))
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true)
                            .into(TeamManager_ImageView_Emblem);
                } else if (flag == 1) {
                    String En_Profile = URLEncoder.encode(FileName, "utf-8");
                    Glide.with(TeamManager1.this).load("http://210.122.7.193:8080/Trophy_img/team/" + En_Profile + ".jpg")
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true)
                            .into(TeamManager_ImageView_Image1);
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {

        }

    }

    String lineEnd = "\r\n";
    String twoHyphens = "--";
    String boundary = "*****";

    public void HttpFileUpload(String urlString, String params, String fileName) {
        // fileName=TeamName;
        try {
            //선택한 파일의 절대 경로를 이용해서 파일 입력 스트림 객체를 얻어온다.
            FileInputStream mFileInputStream = new FileInputStream(fileName);
            //파일을 업로드할 서버의 url 주소를이용해서 URL 객체 생성하기.
            URL connectUrl = new URL(urlString);
            //Connection 객체 얻어오기.
            HttpURLConnection conn = (HttpURLConnection) connectUrl.openConnection();
            conn.setDoInput(true);//입력할수 있도록
            conn.setDoOutput(true); //출력할수 있도록
            conn.setUseCaches(false);  //캐쉬 사용하지 않음

            //post 전송
            conn.setRequestMethod("POST");
            //파일 업로드 할수 있도록 설정하기.
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

            //DataOutputStream 객체 생성하기.
            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
            //전송할 데이터의 시작임을 알린다.
            //String En_TeamName = URLEncoder.encode(TeamName, "utf-8");
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + URLEncoder.encode(FileName, "utf-8") + ".jpg" + "\"" + lineEnd);
            dos.writeBytes(lineEnd);
            //한번에 읽어들일수있는 스트림의 크기를 얻어온다.
            int bytesAvailable = mFileInputStream.available();
            //byte단위로 읽어오기 위하여 byte 배열 객체를 준비한다.
            byte[] buffer = new byte[bytesAvailable];
            int bytesRead = 0;
            // read image
            while (bytesRead != -1) {
                //파일에서 바이트단위로 읽어온다.
                bytesRead = mFileInputStream.read(buffer);
                if (bytesRead == -1) break; //더이상 읽을 데이터가 없다면 빠저나온다.
                Log.d("Test", "image byte is " + bytesRead);
                //읽은만큼 출력한다.
                dos.write(buffer, 0, bytesRead);
                //출력한 데이터 밀어내기
                dos.flush();
            }
            //전송할 데이터의 끝임을 알린다.
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
            //flush() 타이밍??
            //dos.flush();
            dos.close();//스트림 닫아주기
            mFileInputStream.close();//스트림 닫아주기.
            // get response
            int ch;
            //입력 스트림 객체를 얻어온다.
            InputStream is = conn.getInputStream();
            StringBuffer b = new StringBuffer();
            while ((ch = is.read()) != -1) {
                b.append((char) ch);
            }
            String s = b.toString();
            Log.e("Test", "result = " + s);

        } catch (Exception e) {
            Log.d("Test", "exception " + e.getMessage());
        }
    }
}
