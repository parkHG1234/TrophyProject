package trophy.projetc2.Navigation;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import me.drakeet.materialdialog.MaterialDialog;
import trophy.projetc2.Http.HttpClient;
import trophy.projetc2.R;

/**
 * Created by 박효근 on 2017-04-13.
 */

public class Match_Focus extends AppCompatActivity {
    ImageView Match_Focus_ImageView_Back, Match_Focus_ImageView_Status, Match_Focus_ImageView_Emblem;
    TextView Match_Focus_TextView_TeamName, Match_Focus_TextView_Title, Match_Focus_TextView_Date, Match_Focus_TextView_Time,
            Match_Focus_TextView_Place, Match_Focus_TextView_Pay, Match_Focus_TextView_Color, Match_Focus_TextView_Extra;
    CheckBox Match_Focus_CheckBox_Parking_Not, Match_Focus_CheckBox_Parking_Free, Match_Focus_CheckBox_Parking_Charge,
            Match_Focus_CheckBox_Display, Match_Focus_CheckBox_Shower, Match_Focus_CheckBox_ColdHot;
    Button Match_Focus_Button_TeamInfo, Match_Focus_Button_Apply;

    String Match_Pk, Team_Pk, User_Pk, Time, Title, MatchTime, MatchPlace,Emblem,TeamName, Match_User_Pk, Match_Date,
            Parking_Not, Parking_Free, Parking_Charge, Display, Shower, ColdHot, Status, Pay, Color, Extra;
    String[][] parsedData_Match_Focus, parsedData_Match_Focus_Join, parsedData_Match_Focus_Overlap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_navigation_match_focus);

        Intent intent1 = getIntent();
        Match_Pk = intent1.getStringExtra("Match_Pk");
        User_Pk = intent1.getStringExtra("User_Pk");

        HttpClient http_match_focus = new HttpClient();
        String result = http_match_focus.HttpClient("Trophy_part1","Match_Focus.jsp",Match_Pk);
        parsedData_Match_Focus = jsonParserList_Match(result);

        Match_Pk = parsedData_Match_Focus[0][0];Match_User_Pk = parsedData_Match_Focus[0][1];Team_Pk = parsedData_Match_Focus[0][2];
        Time = parsedData_Match_Focus[0][3];Title = parsedData_Match_Focus[0][4];MatchTime = parsedData_Match_Focus[0][5];
        MatchPlace = parsedData_Match_Focus[0][6];Parking_Not = parsedData_Match_Focus[0][7];Parking_Free = parsedData_Match_Focus[0][8];
        Parking_Charge = parsedData_Match_Focus[0][9];Display = parsedData_Match_Focus[0][10];Shower = parsedData_Match_Focus[0][11];
        ColdHot = parsedData_Match_Focus[0][12];Status = parsedData_Match_Focus[0][13];
        Emblem = parsedData_Match_Focus[0][14]; TeamName = parsedData_Match_Focus[0][15];
        Pay = parsedData_Match_Focus[0][16]; Color = parsedData_Match_Focus[0][17]; Extra = parsedData_Match_Focus[0][18];
        Match_Date = parsedData_Match_Focus[0][19];

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
        Match_Focus_Button_TeamInfo = (Button)findViewById(R.id.Match_Focus_Button_TeamInfo);
        Match_Focus_Button_Apply = (Button)findViewById(R.id.Match_Focus_Button_Apply);

        if(Status.equals("recruiting")){
            Match_Focus_ImageView_Status.setImageResource(R.drawable.recruiting);
            Match_Focus_Button_Apply.setBackgroundColor(getResources().getColor(R.color.MainColor1));
            Match_Focus_Button_Apply.setTextColor(getResources().getColor(R.color.White));
        }
        else if(Status.equals("deadline")){
            Match_Focus_ImageView_Status.setImageResource(R.drawable.deadline);
            Match_Focus_Button_Apply.setBackgroundColor(getResources().getColor(R.color.White));
            Match_Focus_Button_Apply.setTextColor(getResources().getColor(R.color.Black));
            Match_Focus_Button_Apply.setText("마 감");
        }

        try {
            String En_Profile = URLEncoder.encode(Emblem, "utf-8");
            if (En_Profile.equals(".")) {
                Glide.with(Match_Focus.this).load(R.drawable.emblem).bitmapTransform(new CropCircleTransformation(Glide.get(Match_Focus.this).getBitmapPool()))
                        .into(Match_Focus_ImageView_Emblem);
            } else {
                Glide.with(Match_Focus.this).load("http://210.122.7.193:8080/Trophy_img/team/" + En_Profile + ".jpg").bitmapTransform(new CropCircleTransformation(Glide.get(Match_Focus.this).getBitmapPool()))
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(Match_Focus_ImageView_Emblem);
            }
        } catch (UnsupportedEncodingException e) {

        }

        Match_Focus_TextView_TeamName.setText(TeamName);
        Match_Focus_TextView_Title.setText(Title);
        Match_Focus_TextView_Date.setText(Match_Date);
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

        Match_Focus_Button_TeamInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Match_Focus.this,TeamSearch_Focus.class);
                intent.putExtra("TeamName", TeamName);
                intent.putExtra("User_Pk",User_Pk);
                intent.putExtra("Team_Pk",Team_Pk);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
            }
        });
        Match_Focus_Button_Apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HttpClient http_match_focus = new HttpClient();
                String result = http_match_focus.HttpClient("Trophy_part1","Match_Focus_Overlap.jsp",User_Pk,Match_Pk);
                parsedData_Match_Focus_Overlap = jsonParserList_Match_Focus_Overlap(result);
                if(parsedData_Match_Focus_Overlap[0][0].equals("overlap")){
                    Snackbar.make(view,"이미 신청하셨습니다.",Snackbar.LENGTH_SHORT).show();
                }
                else{
                    if(Status.equals("recruiting")){
                        LayoutInflater inflater = (LayoutInflater)view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
                        final View layout = inflater.inflate(R.layout.layout_navigation_match_focus_customdialog_memo, (ViewGroup) view.findViewById(R.id.TeamInfo_Customdialog_1_Root));
                        final TextView TeamInfo_Customdialog_1_Title = (TextView)layout.findViewById(R.id.TeamInfo_Customdialog_1_Title);
                        final ImageView TeamInfo_Customdialog_1_Back = (ImageView)layout.findViewById(R.id.TeamInfo_Customdialog_1_Back);
                        final EditText TeamInfo_Customdialog_1_Content = (EditText)layout.findViewById(R.id.TeamInfo_Customdialog_1_Content);
                        final Button TeamInfo_Customdialog_1_Ok = (Button)layout.findViewById(R.id.TeamInfo_Customdialog_1_Ok);
                        TeamInfo_Customdialog_1_Title.setText("시합 신청");
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
                                HttpClient http_match_focus_overlap = new HttpClient();
                                String result1 = http_match_focus_overlap.HttpClient("Trophy_part1","Match_Focus_Join.jsp", User_Pk, Match_Pk, Time, TeamInfo_Customdialog_1_Content.getText().toString());
                                parsedData_Match_Focus_Join = jsonParserList_Match_Focus_Join(result1);
                                if(parsedData_Match_Focus_Join[0][0].equals("succed")){
                                    Snackbar.make(view,"신청되었습니다.",Snackbar.LENGTH_SHORT).show();
                                    TeamInfo_Dialog.dismiss();
                                }
                                else{
                                    Snackbar.make(view,"잠시 후 다시 시도해주세요.",Snackbar.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                    else if(Status.equals("deadline")){
                        Snackbar.make(view,"신청이 마감되었습니다.",Snackbar.LENGTH_SHORT).show();
                    }
                }
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
    public String[][] jsonParserList_Match(String pRecvServerPage){
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try{
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");
            String[] jsonName = {"msg1","msg2","msg3","msg4","msg5","msg6","msg7","msg8","msg9","msg10","msg11","msg12","msg13","msg14","msg15","msg16","msg17","msg18","msg19", "msg20"};
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
    public String[][] jsonParserList_Match_Focus_Join(String pRecvServerPage){
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
    public String[][] jsonParserList_Match_Focus_Overlap(String pRecvServerPage){
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
}
