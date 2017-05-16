package trophy.projetc2.OutCourt;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
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
import java.text.SimpleDateFormat;
import java.util.Date;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import trophy.projetc2.Http.HttpClient;
import trophy.projetc2.R;

/**
 * Created by 박효근 on 2017-05-06.
 */

public class OutCourt_CourtInfo_Focus_Write  extends AppCompatActivity {
    TextView OutCourt_CourtInfo_Focus_Write_TextView_Back;
    TextView OutCourt_CourtInfo_Focus_Write_TextView_Write;
    ImageView OutCourt_CourtInfo_Focus_Write_ImageView_Profile;
    TextView OutCourt_CourtInfo_Focus_Write_TextView_Name;
    EditText OutCourt_CourtInfo_Focus_Write_EditText_Content;
    String CourtName, User_Pk, Name, Profile, Court_Pk;
    String[][] parseredData_user, parseredData_write;
    String strCurYear, strCurMonth, strCurDay, strCurHour,strCurMinute, strCurToday, strCurTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_navigation_outcourt_courtinfo_focus_write);
        Intent intent1 = getIntent();
        User_Pk = intent1.getStringExtra("User_Pk");
        Court_Pk = intent1.getStringExtra("Court_Pk");
        currentTime();
        OutCourt_CourtInfo_Focus_Write_TextView_Write = (TextView)findViewById(R.id.OutCourt_CourtInfo_Focus_Write_TextView_Write);
        OutCourt_CourtInfo_Focus_Write_EditText_Content = (EditText) findViewById(R.id.OutCourt_CourtInfo_Focus_Write_EditText_Content);
        OutCourt_CourtInfo_Focus_Write_TextView_Back = (TextView)findViewById(R.id.OutCourt_CourtInfo_Focus_Write_TextView_Back);
        OutCourt_CourtInfo_Focus_Write_ImageView_Profile = (ImageView) findViewById(R.id.OutCourt_CourtInfo_Focus_Write_ImageView_Profile);
        OutCourt_CourtInfo_Focus_Write_TextView_Name = (TextView)findViewById(R.id.OutCourt_CourtInfo_Focus_Write_TextView_Name);
        HttpClient user = new HttpClient();
        String result1 = user.HttpClient("Trophy_part1", "User.jsp", User_Pk);
        parseredData_user = jsonParserList_User(result1);
        Name = parseredData_user[0][0];
        Profile = parseredData_user[0][2];
        //프로필 관리
        try {
            String Profile1 = URLEncoder.encode(Profile, "utf-8");
            Log.i("Profile1 : ", Profile1);
            if (Profile1.equals(".")) {
                Glide.with(OutCourt_CourtInfo_Focus_Write.this).load(R.drawable.profile_basic_image).diskCacheStrategy(DiskCacheStrategy.NONE).bitmapTransform(new CropCircleTransformation(Glide.get(OutCourt_CourtInfo_Focus_Write.this).getBitmapPool()))
                        .skipMemoryCache(true)
                        .into(OutCourt_CourtInfo_Focus_Write_ImageView_Profile);
            } else {
                Glide.with(OutCourt_CourtInfo_Focus_Write.this).load("http://210.122.7.193:8080/Trophy_img/profile/" + Profile1 + ".jpg").diskCacheStrategy(DiskCacheStrategy.NONE).bitmapTransform(new CropCircleTransformation(Glide.get(OutCourt_CourtInfo_Focus_Write.this).getBitmapPool()))
                        .skipMemoryCache(true)
                        .into(OutCourt_CourtInfo_Focus_Write_ImageView_Profile);
            }
        } catch (UnsupportedEncodingException e) {

        }
        OutCourt_CourtInfo_Focus_Write_TextView_Name.setText(Name);

        OutCourt_CourtInfo_Focus_Write_EditText_Content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(OutCourt_CourtInfo_Focus_Write_EditText_Content.getText().toString().equals("")){
                    OutCourt_CourtInfo_Focus_Write_TextView_Write.setTextColor(getResources().getColor(R.color.DarkGray));
                }
                else{
                    OutCourt_CourtInfo_Focus_Write_TextView_Write.setTextColor(getResources().getColor(R.color.MainColor1));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        OutCourt_CourtInfo_Focus_Write_TextView_Write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(OutCourt_CourtInfo_Focus_Write_EditText_Content.getText().toString().equals("")){
                    OutCourt_CourtInfo_Focus_Write_TextView_Write.setEnabled(false);
                }
                else{
                    HttpClient user = new HttpClient();
                    String result1 = user.HttpClient("Trophy_part1", "OutCourt_Focus_Write.jsp", User_Pk, Court_Pk, strCurToday, OutCourt_CourtInfo_Focus_Write_EditText_Content.getText().toString());
                    parseredData_write = jsonParserList_Write(result1);
                    if(parseredData_write[0][0].equals("succed")){
                        finish();
                        overridePendingTransition(R.anim.anim_slide_in_top, R.anim.anim_slide_out_bottom);
                    }
                   else{
                        Snackbar.make(view,"잠시 후 다시 시도해주시기 바랍니다.", Snackbar.LENGTH_SHORT).show();
                    }
                }
            }
        });
        OutCourt_CourtInfo_Focus_Write_TextView_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.anim_slide_in_top, R.anim.anim_slide_out_bottom);
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.anim_slide_in_top, R.anim.anim_slide_out_bottom);
    }
    public String[][] jsonParserList_User(String pRecvServerPage) {
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try {
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");
            String[] jsonName = {"msg1", "msg2", "msg3", "msg4", "msg5"};
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
    public String[][] jsonParserList_Write(String pRecvServerPage) {
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
    public void currentTime(){
        long now = System.currentTimeMillis();
// 현재 시간을 저장 한다.
        Date date = new Date(now);
// 시간 포맷 지정
        SimpleDateFormat CurDateFormat = new SimpleDateFormat("yyyy / MM / dd:::HH : mm");
        SimpleDateFormat CurTimeFormat = new SimpleDateFormat("HH : mm");
        SimpleDateFormat CurYearFormat = new SimpleDateFormat("yyyy");
        SimpleDateFormat CurMonthFormat = new SimpleDateFormat("MM");
        SimpleDateFormat CurDayFormat = new SimpleDateFormat("dd");
        SimpleDateFormat CurHourFormat = new SimpleDateFormat("HH");
        SimpleDateFormat CurMinuteFormat = new SimpleDateFormat("mm");
// 지정된 포맷으로 String 타입 리턴
        strCurToday = CurDateFormat.format(date);
        strCurTime = CurTimeFormat.format(date);
        strCurYear = CurYearFormat.format(date);
        strCurYear = CurYearFormat.format(date);
        strCurMonth = CurMonthFormat.format(date);
        strCurDay = CurDayFormat.format(date);
        strCurHour = CurHourFormat.format(date);
        strCurMinute = CurMinuteFormat.format(date);
    }
}
