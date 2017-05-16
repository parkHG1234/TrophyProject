package trophy.projetc2.Match;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
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

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import me.drakeet.materialdialog.MaterialDialog;
import trophy.projetc2.Http.HttpClient;
import trophy.projetc2.R;

/**
 * Created by 박효근 on 2017-04-17.
 */

public class MyMatch_Focus extends AppCompatActivity {
    ImageView Match_Focus_ImageView_Back, Match_Focus_ImageView_Emblem;
    static ImageView Match_Focus_ImageView_Status;
    TextView Match_Focus_TextView_TeamName, Match_Focus_TextView_Title, Match_Focus_TextView_Date, Match_Focus_TextView_Time,
            Match_Focus_TextView_Place, Match_Focus_TextView_Pay, Match_Focus_TextView_Color, Match_Focus_TextView_Extra, Match_Focus_ImageView_Delete;
    static ImageView MyMatch_Focus_Joined_ImageView_Emblem;
    static TextView MyMatch_Focus_Joined_TextView_TeamName;
    static TextView MyMatch_Focus_Joined_TextView_Title;
    static ImageView MyMatch_Focus_Joined_ImageView_Phone;
    CheckBox Match_Focus_CheckBox_Parking_Not, Match_Focus_CheckBox_Parking_Free, Match_Focus_CheckBox_Parking_Charge,
            Match_Focus_CheckBox_Display, Match_Focus_CheckBox_Shower, Match_Focus_CheckBox_ColdHot;
    ListView MyMatch_Focus_ListView_JoinerList;

    static LinearLayout MyMatch_Focus_LinerLayout_Joined, MyMatch_Focus_LinerLayout_Joining;
    String Match_Pk, Team_Pk, User_Pk, Time, Title, MatchTime, MatchPlace,Emblem,TeamName, Match_User_Pk,
            Parking_Not, Parking_Free, Parking_Charge, Display, Shower, ColdHot, Status, Pay, Color, Extra;
    String[][] parsedData_Match_Focus, parsedData_MyMatch_Focus_Joiner, parseredData_Delete;
    static String OtherTeam_Phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_navigation_mymatch_focus);

        Intent intent1 = getIntent();
        Match_Pk = intent1.getStringExtra("Match_Pk");
        User_Pk = intent1.getStringExtra("User_Pk");

        HttpClient http_match_focus = new HttpClient();
        String result = http_match_focus.HttpClient("Trophy_part1","Match_Focus.jsp",Match_Pk);
        parsedData_Match_Focus = jsonParserList_MyMatch(result);

        String str = parsedData_Match_Focus[0][3];
        String[] data = str.split(":::");
        Match_Pk = parsedData_Match_Focus[0][0];Match_User_Pk = parsedData_Match_Focus[0][1];Team_Pk = parsedData_Match_Focus[0][2];
        Time = data[0];Title = parsedData_Match_Focus[0][4];MatchTime = parsedData_Match_Focus[0][5];
        MatchPlace = parsedData_Match_Focus[0][6];Parking_Not = parsedData_Match_Focus[0][7];Parking_Free = parsedData_Match_Focus[0][8];
        Parking_Charge = parsedData_Match_Focus[0][9];Display = parsedData_Match_Focus[0][10];Shower = parsedData_Match_Focus[0][11];
        ColdHot = parsedData_Match_Focus[0][12];Status = parsedData_Match_Focus[0][13];
        Emblem = parsedData_Match_Focus[0][14]; TeamName = parsedData_Match_Focus[0][15];
        Pay = parsedData_Match_Focus[0][16]; Color = parsedData_Match_Focus[0][17]; Extra = parsedData_Match_Focus[0][18];
        Match_Focus_ImageView_Back = (ImageView)findViewById(R.id.Match_Focus_ImageView_Back);
        Match_Focus_ImageView_Status = (ImageView)findViewById(R.id.Match_Focus_ImageView_Status);
        Match_Focus_ImageView_Emblem = (ImageView)findViewById(R.id.Match_Focus_ImageView_Emblem);
        Match_Focus_TextView_TeamName = (TextView)findViewById(R.id.Match_Focus_TextView_TeamName);
        Match_Focus_TextView_Title = (TextView)findViewById(R.id.Match_Focus_TextView_Title);
        Match_Focus_TextView_Date = (TextView)findViewById(R.id.Match_Focus_TextView_Date);
        Match_Focus_TextView_Time = (TextView)findViewById(R.id.Match_Focus_TextView_Time);
        Match_Focus_TextView_Place = (TextView)findViewById(R.id.Match_Focus_TextView_Place);
        Match_Focus_TextView_Pay = (TextView)findViewById(R.id.Match_Focus_TextView_Pay);
        Match_Focus_TextView_Color = (TextView)findViewById(R.id.Match_Focus_TextView_Color);
        Match_Focus_TextView_Extra = (TextView)findViewById(R.id.Match_Focus_TextView_Extra);
        Match_Focus_CheckBox_Parking_Not = (CheckBox)findViewById(R.id.Match_Focus_CheckBox_Parking_Not);
        Match_Focus_CheckBox_Parking_Free = (CheckBox)findViewById(R.id.Match_Focus_CheckBox_Parking_Free);
        Match_Focus_CheckBox_Parking_Charge = (CheckBox)findViewById(R.id.Match_Focus_CheckBox_Parking_Charge);
        Match_Focus_CheckBox_Display = (CheckBox)findViewById(R.id.Match_Focus_CheckBox_Display);
        Match_Focus_CheckBox_Shower = (CheckBox)findViewById(R.id.Match_Focus_CheckBox_Shower);
        Match_Focus_CheckBox_ColdHot = (CheckBox)findViewById(R.id.Match_Focus_CheckBox_ColdHot);
        MyMatch_Focus_ListView_JoinerList = (ListView)findViewById(R.id.MyMatch_Focus_ListView_JoinerList);
        Match_Focus_ImageView_Delete = (TextView) findViewById(R.id.Match_Focus_ImageView_Delete);
        Match_Focus_CheckBox_Parking_Not.setClickable(false);Match_Focus_CheckBox_Parking_Free.setClickable(false);Match_Focus_CheckBox_Parking_Charge.setClickable(false);
        Match_Focus_CheckBox_Display.setClickable(false);Match_Focus_CheckBox_Shower.setClickable(false);Match_Focus_CheckBox_ColdHot.setClickable(false);
        MyMatch_Focus_LinerLayout_Joined = (LinearLayout)findViewById(R.id.MyMatch_Focus_LinerLayout_Joined);
        MyMatch_Focus_LinerLayout_Joining = (LinearLayout)findViewById(R.id.MyMatch_Focus_LinerLayout_Joining);

        MyMatch_Focus_Joined_ImageView_Emblem = (ImageView)findViewById(R.id.MyMatch_Focus_Joined_ImageView_Emblem);
        MyMatch_Focus_Joined_TextView_TeamName = (TextView)findViewById(R.id.MyMatch_Focus_Joined_TextView_TeamName);
        MyMatch_Focus_Joined_TextView_Title = (TextView)findViewById(R.id.MyMatch_Focus_Joined_TextView_Title);
        MyMatch_Focus_Joined_ImageView_Phone = (ImageView)findViewById(R.id.MyMatch_Focus_Joined_ImageView_Phone);

        MyMatch_Focus_LinerLayout_Joined.setVisibility(View.GONE);

        try {
            String En_Profile = URLEncoder.encode(Emblem, "utf-8");
            if (En_Profile.equals(".")) {
                Glide.with(MyMatch_Focus.this).load(R.drawable.emblem).bitmapTransform(new CropCircleTransformation(Glide.get(MyMatch_Focus.this).getBitmapPool()))
                        .into(Match_Focus_ImageView_Emblem);
            } else {
                Glide.with(MyMatch_Focus.this).load("http://210.122.7.193:8080/Trophy_img/team/" + En_Profile + ".jpg").bitmapTransform(new CropCircleTransformation(Glide.get(MyMatch_Focus.this).getBitmapPool()))
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(Match_Focus_ImageView_Emblem);
            }
        } catch (UnsupportedEncodingException e) {

        }

        Match_Focus_TextView_TeamName.setText(TeamName);
        Match_Focus_TextView_Title.setText(Title);
        Match_Focus_TextView_Date.setText(Time);
        Match_Focus_TextView_Time.setText(MatchTime);
        Match_Focus_TextView_Place.setText(MatchPlace);
        Match_Focus_TextView_Pay.setText(Pay);
        Match_Focus_TextView_Color.setText(Color);
        Match_Focus_TextView_Extra.setText(Extra);

        if(Parking_Not.equals("true")){
            Match_Focus_CheckBox_Parking_Not.setChecked(true);
        }else{
            Match_Focus_CheckBox_Parking_Not.setChecked(false);
        }
        if(Parking_Free.equals("true")){
            Match_Focus_CheckBox_Parking_Free.setChecked(true);
        }else{
            Match_Focus_CheckBox_Parking_Free.setChecked(false);
        }
        if(Parking_Charge.equals("true")){
            Match_Focus_CheckBox_Parking_Charge.setChecked(true);
        }else{
            Match_Focus_CheckBox_Parking_Charge.setChecked(false);
        }
        if(Display.equals("true")){
            Match_Focus_CheckBox_Display.setChecked(true);
        }else{
            Match_Focus_CheckBox_Display.setChecked(false);
        }
        if(Shower.equals("true")){
            Match_Focus_CheckBox_Shower.setChecked(true);
        }else{
            Match_Focus_CheckBox_Shower.setChecked(false);
        }
        if(ColdHot.equals("true")){
            Match_Focus_CheckBox_ColdHot.setChecked(true);
        }else{
            Match_Focus_CheckBox_ColdHot.setChecked(false);
        }
        Log.i("Match",Match_Pk);


        if(parsedData_Match_Focus[0][13].equals("recruiting")){
            Match_Focus_ImageView_Status.setImageResource(R.drawable.recruiting);
            MyMatch_Focus_LinerLayout_Joined.setVisibility(View.GONE);
            MyMatch_Focus_LinerLayout_Joining.setVisibility(View.VISIBLE);

            HttpClient http_mymatch_focus_joiner = new HttpClient();
            String result1 = http_mymatch_focus_joiner.HttpClient("Trophy_part1","MyMatch_Focus_Joiner.jsp", Match_Pk);
            parsedData_MyMatch_Focus_Joiner = jsonParserList_MyMatch_Focus_Join(result1);

            final ArrayList<MyMatch_Focus_Joiner_MyData> MyMatch_Focus_Joiner_MyData;
            MyMatch_Focus_Joiner_MyData = new ArrayList<MyMatch_Focus_Joiner_MyData>();
            for (int i = 0; i < parsedData_MyMatch_Focus_Joiner.length; i++) {
                MyMatch_Focus_Joiner_MyData.add(new MyMatch_Focus_Joiner_MyData(parsedData_MyMatch_Focus_Joiner[i][0], parsedData_MyMatch_Focus_Joiner[i][1], parsedData_MyMatch_Focus_Joiner[i][2],parsedData_MyMatch_Focus_Joiner[i][3],parsedData_MyMatch_Focus_Joiner[i][4],parsedData_MyMatch_Focus_Joiner[i][5],parsedData_MyMatch_Focus_Joiner[i][6],parsedData_MyMatch_Focus_Joiner[i][6],MyMatch_Focus.this,parsedData_MyMatch_Focus_Joiner[i][8]));
            }
            MyMatch_Focus_Joiner_MyAdapter adapter = new MyMatch_Focus_Joiner_MyAdapter(this, MyMatch_Focus_Joiner_MyData);
            MyMatch_Focus_ListView_JoinerList.setAdapter(adapter);
            setListViewHeightBasedOnChildren(MyMatch_Focus_ListView_JoinerList);
        }
        else{
            Match_Focus_ImageView_Status.setImageResource(R.drawable.deadline);
            MyMatch_Focus_LinerLayout_Joined.setVisibility(View.VISIBLE);
            MyMatch_Focus_LinerLayout_Joining.setVisibility(View.GONE);

            HttpClient http_mymatch_focus_joined = new HttpClient();
            String result1 = http_mymatch_focus_joined.HttpClient("Trophy_part1","MyMatch_Focus_Joined.jsp", Match_Pk);
            parsedData_MyMatch_Focus_Joiner = jsonParserList_MyMatch_Focus_Join(result1);
            OtherTeam_Phone = parsedData_MyMatch_Focus_Joiner[0][8];
            try {
                String En_Profile = URLEncoder.encode(parsedData_MyMatch_Focus_Joiner[0][5], "utf-8");
                if (En_Profile.equals(".")) {
                    Glide.with(MyMatch_Focus.this).load(R.drawable.emblem).bitmapTransform(new CropCircleTransformation(Glide.get(MyMatch_Focus.this).getBitmapPool()))
                            .into(MyMatch_Focus_Joined_ImageView_Emblem);
                } else {
                    Glide.with(MyMatch_Focus.this).load("http://210.122.7.193:8080/Trophy_img/team/" + En_Profile + ".jpg").bitmapTransform(new CropCircleTransformation(Glide.get(MyMatch_Focus.this).getBitmapPool()))
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true)
                            .into(MyMatch_Focus_Joined_ImageView_Emblem);
                }
            } catch (UnsupportedEncodingException e) {

            }
            MyMatch_Focus_Joined_TextView_TeamName.setText(parsedData_MyMatch_Focus_Joiner[0][6]);
            MyMatch_Focus_Joined_TextView_Title.setText(parsedData_MyMatch_Focus_Joiner[0][4]);
        }

        Match_Focus_ImageView_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater)view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
                final View layout = inflater.inflate(R.layout.layout_customdialog_2choice, (ViewGroup) view.findViewById(R.id.Customdialog_2Choice_Root));
                final TextView Customdialog_2Choice_Title = (TextView)layout.findViewById(R.id.Customdialog_2Choice_Title);
                final ImageView Customdialog_2Choice_Back = (ImageView)layout.findViewById(R.id.Customdialog_2Choice_Back);
                final TextView Customdialog_2Choice_Content = (TextView)layout.findViewById(R.id.Customdialog_2Choice_Content);
                final Button Customdialog_2Choice_First = (Button)layout.findViewById(R.id.Customdialog_2Choice_First);
                final Button Customdialog_2Choice_Second = (Button)layout.findViewById(R.id.Customdialog_2Choice_Second);
                Customdialog_2Choice_Title.setText("게시글 삭제");
                Customdialog_2Choice_Content.setText("해당 게시물을 삭제합니다.\n삭제 시 시합 요청 팀 목록이 삭제됩니다.");
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
                        String result_delete =TeamJoin.HttpClient("Trophy_part1","MyMatch_Focus_Delete.jsp",Match_Pk);
                        parseredData_Delete = jsonParserList_Delete(result_delete);
                        if(parseredData_Delete[0][0].equals("succed")){
                            TeamInfo_Dialog.dismiss();
                            finish();
                            overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
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
        });
        MyMatch_Focus_Joined_ImageView_Phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+OtherTeam_Phone));
                startActivity(intent);
            }
        });
        Match_Focus_ImageView_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
    }
    public String[][] jsonParserList_MyMatch(String pRecvServerPage){
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try{
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");
            String[] jsonName = {"msg1","msg2","msg3","msg4","msg5","msg6","msg7","msg8","msg9","msg10","msg11","msg12","msg13","msg14","msg15","msg16","msg17","msg18","msg19"};
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
    public String[][] jsonParserList_MyMatch_Focus_Join(String pRecvServerPage){
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try{
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");
            String[] jsonName = {"msg1","msg2","msg3","msg4","msg5","msg6","msg7","msg8","msg9"};
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
    public String[][] jsonParserList_Delete(String pRecvServerPage){
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
