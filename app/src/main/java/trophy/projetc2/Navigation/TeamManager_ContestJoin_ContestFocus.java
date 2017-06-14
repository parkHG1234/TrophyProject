package trophy.projetc2.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import me.drakeet.materialdialog.MaterialDialog;
import trophy.projetc2.Contest.Contest_Detail_Form_Customlist_Adapter;
import trophy.projetc2.Contest.Contest_Detail_Form_Customlist_MyData;
import trophy.projetc2.Http.HttpClient;
import trophy.projetc2.R;

import static trophy.projetc2.Navigation.TeamManager_ContestJoin.refresh_contestjoin;

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
    ImageView Layout_Navigation_TeamManager_ContestJoin_ContestFocus_ImageView_Back;
    ImageView Layout_Navigation_TeamManager_ContestJoin_ImageView_Delete;
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
    String[][] parsedData_joinerinfo,parsedData_ContestDelete,parsedData_ContestDelete_Content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_navigation_teammanager_contestjoin_contestfocus);
//
//        Layout_Navigation_TeamManager_ContestJoin_ContestFocus_ImageView_Back = (ImageView)findViewById(R.id.Layout_Navigation_TeamManager_ContestJoin_ContestFocus_ImageView_Back);
//        Layout_Navigation_TeamManager_ContestJoin_ImageView_Delete = (ImageView)findViewById(R.id.Layout_Navigation_TeamManager_ContestJoin_ImageView_Delete);
//        TeamManager_ContestJoin_ContestFocus_Scroll = (ScrollView)findViewById(R.id.TeamManager_ContestJoin_ContestFocus_Scroll);
//        TeamManager_ContestJoin_ContestFocus_ImageView_ContestImage = (ImageView)findViewById(R.id.TeamManager_ContestJoin_ContestFocus_ImageView_ContestImage);
//        TeamManager_ContestJoin_ContestFocus_TextView_ContestTitle = (TextView) findViewById(R.id.TeamManager_ContestJoin_ContestFocus_TextView_ContestTitle);
//        TeamManager_ContestJoin_ContestFocus_TextView_JoinStatus = (TextView) findViewById(R.id.TeamManager_ContestJoin_ContestFocus_TextView_JoinStatus);
//        TeamManager_ContestJoin_ContestFocus_TextView_AcountName = (TextView) findViewById(R.id.TeamManager_ContestJoin_ContestFocus_TextView_AcountName);
//        TeamManager_ContestJoin_ContestFocus_TextView_AcountNumber = (TextView) findViewById(R.id.TeamManager_ContestJoin_ContestFocus_TextView_AcountNumber);
//        TeamManager_ContestJoin_ContestFocus_TextView_JoinDate = (TextView) findViewById(R.id.TeamManager_ContestJoin_ContestFocus_TextView_JoinDate);
//        TeamManager_ContestJoin_ContestFocus_TextView_ContestDate = (TextView) findViewById(R.id.TeamManager_ContestJoin_ContestFocus_TextView_ContestDate);
//        TeamManager_ContestJoin_ContestFocus_TextView_ContestDetailTitle = (TextView)findViewById(R.id.TeamManager_ContestJoin_ContestFocus_TextView_ContestDetailTitle);
//        TeamManager_ContestJoin_ContestFocus_TextView_ContestDetail = (TextView) findViewById(R.id.TeamManager_ContestJoin_ContestFocus_TextView_ContestDetail);
//        TeamManager_ContestJoin_ContestFocus_TextView_TeamName = (TextView) findViewById(R.id.TeamManager_ContestJoin_ContestFocus_TextView_TeamName);
//        TeamManager_ContestJoin_ContestFocus_TextView_Represent = (TextView) findViewById(R.id.TeamManager_ContestJoin_ContestFocus_TextView_Represent);
//        TeamManager_ContestJoin_ContestFocus_TextView_RepresentPhone = (TextView) findViewById(R.id.TeamManager_ContestJoin_ContestFocus_TextView_RepresentPhone);
//        TeamManager_ContestJoin_ContestFocus_ListView_Player = (ListView) findViewById(R.id.TeamManager_ContestJoin_ContestFocus_ListView_Player);
//
//        Intent intent = getIntent();
//        Contest_Pk = intent.getStringExtra("Contest_Pk");
//        Contest_Image = intent.getStringExtra("Contest_Image");
//        Contest_Title = intent.getStringExtra("Contest_Title");
//        Contest_Status = intent.getStringExtra("Contest_Status");
//        AcountName = intent.getStringExtra("AcountName");
//        AcountNumber = intent.getStringExtra("AcountNumber");
//        HttpClient http_contestinfo= new HttpClient();
//        String result = http_contestinfo.HttpClient("Trophy_part1","TeamManager_ContestJoin_ContestFocus_ContestInfo.jsp",Contest_Pk);
//        String[][] parsedData_contestinfo = jsonParserList_ContestInfo(result);
//
//        HttpClient http_representinfo= new HttpClient();
//        String result1 = http_representinfo.HttpClient("Trophy_part1","TeamManager_ContestJoin_ContestFocus_Represent.jsp",TeamManager_Team_Pk);
//        String[][] parsedData_representinfo = jsonParserList_represent(result1);
//
//        HttpClient http_joinerinfo = new HttpClient();
//        String result2 = http_joinerinfo.HttpClient("Trophy_part1","TeamManager_ContestJoin_ContestFocus_Joiner.jsp",TeamManager_Team_Pk,Contest_Pk);
//        parsedData_joinerinfo = jsonParserList_joiner(result2);
//
//        //대회이미지 로드
//        Glide.with(TeamManager_ContestJoin_ContestFocus.this).load("http://210.122.7.193:8080/Web_basket/imgs1/Contest/"+Contest_Image+".jpg")
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .skipMemoryCache(true)
//                .into(TeamManager_ContestJoin_ContestFocus_ImageView_ContestImage);
//        //대회간략정보 로드
//        TeamManager_ContestJoin_ContestFocus_TextView_ContestTitle.setText(Contest_Title);
//        TeamManager_ContestJoin_ContestFocus_TextView_JoinStatus.setText(Contest_Status);
//        TeamManager_ContestJoin_ContestFocus_TextView_AcountName.setText(AcountName);
//        TeamManager_ContestJoin_ContestFocus_TextView_AcountNumber.setText(AcountNumber);
//        TeamManager_ContestJoin_ContestFocus_TextView_JoinDate.setText(parsedData_contestinfo[0][0]+" ~ "+parsedData_contestinfo[0][1]);
//        TeamManager_ContestJoin_ContestFocus_TextView_ContestDate.setText(parsedData_contestinfo[0][2]);
//        TeamManager_ContestJoin_ContestFocus_TextView_ContestDetail.setText(parsedData_contestinfo[0][3]);
//        TeamManager_ContestJoin_ContestFocus_TextView_TeamName.setText(TeamManager_TeamName);
//        TeamManager_ContestJoin_ContestFocus_TextView_Represent.setText(parsedData_representinfo[0][0]);
//        TeamManager_ContestJoin_ContestFocus_TextView_RepresentPhone.setText(parsedData_representinfo[0][1]);
//
//        TeamManager_ContestJoin_ContestFocus_TextView_ContestDetail.setVisibility(View.VISIBLE);
//
//        setData_Player();
//        Contest_Detail_Form_Customlist_Adapter = new Contest_Detail_Form_Customlist_Adapter(TeamManager_ContestJoin_ContestFocus.this, Contest_Detail_Form_Customlist_MyData);
//        //리스트뷰에 어댑터 연결
//        TeamManager_ContestJoin_ContestFocus_ListView_Player.setAdapter(Contest_Detail_Form_Customlist_Adapter);
//        setListViewHeightBasedOnChildren(TeamManager_ContestJoin_ContestFocus_ListView_Player);
//        Layout_Navigation_TeamManager_ContestJoin_ContestFocus_ImageView_Back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
//            }
//        });
//        Layout_Navigation_TeamManager_ContestJoin_ImageView_Delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                LayoutInflater inflater = (LayoutInflater)view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
//                final View layout = inflater.inflate(R.layout.layout_customdialog_teammanager_contestjoin_contestfocus_delete, (ViewGroup) view.findViewById(R.id.Customdialog_TeamManager_ContestJoin_ContestFocus_Delete_Root));
//                final ImageView Customdialog_TeamManager_ContestJoin_ContestFocus_Delete_Button_Back = (ImageView)layout.findViewById(R.id.Customdialog_TeamManager_ContestJoin_ContestFocus_Delete_Button_Back);
//                final TextView Customdialog_TeamManager_ContestJoin_ContestFocus_Delete_TextView_Content = (TextView)layout.findViewById(R.id.Customdialog_TeamManager_ContestJoin_ContestFocus_Delete_TextView_Content);
//                final Button Customdialog_TeamManager_ContestJoin_ContestFocus_Delete_Button_Delete = (Button)layout.findViewById(R.id.Customdialog_TeamManager_ContestJoin_ContestFocus_Delete_Button_Delete);
//                final Button Customdialog_TeamManager_ContestJoin_ContestFocus_Delete_Button_Call = (Button)layout.findViewById(R.id.Customdialog_TeamManager_ContestJoin_ContestFocus_Delete_Button_Call);
//                if(Contest_Status.equals("입금대기")){
//                    Customdialog_TeamManager_ContestJoin_ContestFocus_Delete_Button_Delete.setBackgroundColor(getResources().getColor(R.color.MainColor1));
//                    Customdialog_TeamManager_ContestJoin_ContestFocus_Delete_Button_Delete.setTextColor(getResources().getColor(R.color.White));
//                }
//                else{
//                    Customdialog_TeamManager_ContestJoin_ContestFocus_Delete_Button_Call.setBackgroundColor(getResources().getColor(R.color.MainColor1));
//                    Customdialog_TeamManager_ContestJoin_ContestFocus_Delete_Button_Call.setTextColor(getResources().getColor(R.color.White));
//                }
//
//                HttpClient http_Delete_content = new HttpClient();
//                String result2 = http_Delete_content.HttpClient("Trophy_part1","TeamManager_ContestJoin_ContestFocus_Delete_Content.jsp");
//                parsedData_ContestDelete_Content = jsonParserList_ContentDelete_Content(result2);
//                Customdialog_TeamManager_ContestJoin_ContestFocus_Delete_TextView_Content.setText(parsedData_ContestDelete_Content[0][0]);
//                final MaterialDialog Contest_Delete_Dialog = new MaterialDialog(view.getContext());
//                Contest_Delete_Dialog
//                        .setContentView(layout)
//                        .setCanceledOnTouchOutside(true);
//                Contest_Delete_Dialog.show();
//                Customdialog_TeamManager_ContestJoin_ContestFocus_Delete_Button_Back.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Contest_Delete_Dialog.dismiss();
//                    }
//                });
//                Customdialog_TeamManager_ContestJoin_ContestFocus_Delete_Button_Delete.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        if(Contest_Status.equals("입금대기")){
//                            delete_Player();
//                            HttpClient http_contest_delete = new HttpClient();
//                            String result2 = http_contest_delete.HttpClient("Trophy_part1","TeamManager_ContestJoin_ContestFocus_Delete.jsp",TeamManager_Team_Pk,Contest_Pk);
//                            parsedData_ContestDelete = jsonParserList_ContestDelete(result2);
//                            if(parsedData_ContestDelete[0][0].equals("succed")){
//                                Snackbar.make(view,"대회가 취소되었습니다.",Snackbar.LENGTH_INDEFINITE).setAction("확인", new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View view) {
//                                        refresh_contestjoin = "refresh";
//                                        Contest_Delete_Dialog.dismiss();
//                                        finish();
//                                    }
//                                }).show();
//                            }
//                        }
//                        else{
//                            Snackbar.make(view,"고객센터로 문의주시기 바랍니다.",Snackbar.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//            }
//        });
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
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
    public String[][] jsonParserList_ContentDelete_Content(String pRecvServerPage) {
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
    public String[][] jsonParserList_ContestDelete(String pRecvServerPage) {
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
    private void delete_Player()
    {
        HttpClient http_contest_delete_player = new HttpClient();
        for(int i = 0; i<parsedData_joinerinfo.length; i++){
            http_contest_delete_player.HttpClient("Trophy_part1","TeamManager_ContestJoin_ContestFocus_Delete_Player.jsp",parsedData_joinerinfo[i][3],Contest_Pk);
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
}
