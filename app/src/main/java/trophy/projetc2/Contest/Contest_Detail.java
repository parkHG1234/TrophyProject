package trophy.projetc2.Contest;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import me.drakeet.materialdialog.MaterialDialog;
import trophy.projetc2.Http.HttpClient;
import trophy.projetc2.MainActivity;
import trophy.projetc2.R;
import trophy.projetc2.User.Login;

/**
 * Created by ldong on 2016-11-12.
 */

public class Contest_Detail extends AppCompatActivity {
    Button layout_contest_submit;
    ImageView Contest_Detail_Back;
    private LayoutInflater inflater;
    static TimerTask myTask;
    static Timer timer;
    static String Pk,Contest_Pk,Contest_Name;
    static String Status="succed";
    String Support_Image, Support_Url;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    static Activity Contest_Detail_activity;
    String[][] ContestsDetailParsedData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_contest_detail);
        //GlobalApplication.setCurrentActivity(this);
        Contest_Detail_activity = Contest_Detail.this;
        inflater=getLayoutInflater();
        layout_contest_submit = (Button)findViewById(R.id.layout_contest_submit);
        Contest_Detail_Back = (ImageView) findViewById(R.id.Contest_Detail_Back);
        Contest_Detail_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
            }
        });
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Intent intent = getIntent();
        Contest_Pk = intent.getStringExtra("Contest_Pk");
        preferences = getSharedPreferences("trophy", MODE_PRIVATE);
        Pk = preferences.getString("Pk", ".");
        HttpClient http_contest_detail = new HttpClient();
        String result = http_contest_detail.HttpClient("Trophy_part1","Contest_Detail.jsp", Contest_Pk);
        ContestsDetailParsedData = jsonParserList_getContestDetail(result);
        Support_Image = ContestsDetailParsedData[0][14]; Support_Url = ContestsDetailParsedData[0][15];
        String Da = " 일";
////다이얼로그 광고
        final View layout = inflater.inflate(R.layout.layout_customdialog_contest_ad, (ViewGroup) findViewById(R.id.Layout_CustomDialog_Contest_AD_Root));
        final ImageView Layout_CustomDialog_Contest_AD_ImageView = (ImageView) layout.findViewById(R.id.Layout_CustomDialog_Contest_AD_ImageView);
        final LinearLayout aaa= (LinearLayout)layout.findViewById(R.id.aaa);
        final TextView Layout_CustomDialog_Contest_AD_TextView = (TextView) layout.findViewById(R.id.Layout_CustomDialog_Contest_AD_TextView);
        final Button foucs = (Button)layout.findViewById(R.id.focus);
        final Button close = (Button)layout.findViewById(R.id.close);
        //프로필 관리
        try {
            String Profile1 = URLEncoder.encode(Support_Image, "utf-8");
            Log.i("Profile1 : ", Profile1);
            if (Profile1.equals(".")) {
                Glide.with(Contest_Detail.this).load(R.drawable.ad1).diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(Layout_CustomDialog_Contest_AD_ImageView);
            } else {
                Glide.with(Contest_Detail.this).load("http://210.122.7.193:8080/Trophy_img/contest/" + Profile1 + ".jpg").diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(Layout_CustomDialog_Contest_AD_ImageView);
            }
        } catch (UnsupportedEncodingException e) {

        }
        foucs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Support_Url.equals(".")){

                }
                else{
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Support_Url));
                    startActivity(intent);
                }
            }
        });
        final Dialog DutyDialog = new Dialog(Contest_Detail.this);
        DutyDialog
                .setContentView(layout);
        myTask = new TimerTask() {
            int i = 3;

            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // 해당 작업을 처리함
                        Layout_CustomDialog_Contest_AD_TextView.setText(+i+"초 후 종료..     ");
                        if (i <= 0) {
                            timer.cancel();
                            Layout_CustomDialog_Contest_AD_TextView.setVisibility(View.GONE);
                            aaa.setVisibility(View.VISIBLE);
                            close.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    DutyDialog.dismiss();
                                }
                            });
                            DutyDialog.setCancelable(true);
                        }
                    }
                });
                i--;
                //시간이 초과된 경우 game 내 데이터 삭제 및 초기화.

            }
        };
        timer = new Timer();
        timer.schedule(myTask, 1000, 1000); // 5초후 첫실행, 1초마다 계속실행
        DutyDialog.show();
        DutyDialog.setCancelable(false);
        ////////////////////////////////////
        TextView layout_contest_detail_caution = (TextView)findViewById(R.id.layout_contest_detail_caution);
        TextView title = (TextView) findViewById(R.id.layout_contest_detail_title);
        TextView price = (TextView) findViewById(R.id.layout_contest_register_price);
        TextView remainder = (TextView) findViewById(R.id.layout_contest_remainder);
        TextView remainder1 = (TextView) findViewById(R.id.layout_contest_remainder1);
        TextView remainder2 = (TextView) findViewById(R.id.layout_contest_remainder2);

        TextView host = (TextView) findViewById(R.id.layout_contest_host);
        TextView management= (TextView) findViewById(R.id.layout_contest_management);
        TextView support = (TextView) findViewById(R.id.layout_contest_support);
        TextView recruitPeriod = (TextView) findViewById(R.id.layout_contest_recruit_period);
        TextView date = (TextView) findViewById(R.id.layout_contest_date);
        TextView place = (TextView) findViewById(R.id.layout_contest_place);
        TextView DetailInfo = (TextView) findViewById(R.id.layout_contest_detailInfo);
        ImageView image = (ImageView) findViewById(R.id.layout_contest_detail_image);
        /////////////로그인이 안되어 있는 경우


        Contest_Name = ContestsDetailParsedData[0][1];
        title.setText(ContestsDetailParsedData[0][1]+" │ ");
        host.setText(ContestsDetailParsedData[0][6]);
        management.setText(ContestsDetailParsedData[0][7]);
        support.setText(ContestsDetailParsedData[0][8]);
        date.setText(ContestsDetailParsedData[0][9]);
        recruitPeriod.setText(ContestsDetailParsedData[0][11] + " ~ " + ContestsDetailParsedData[0][12]);
        DetailInfo.setText(ContestsDetailParsedData[0][13]);
        price.setText(ContestsDetailParsedData[0][5]);
        place.setText(ContestsDetailParsedData[0][10]);
        Glide.with(Contest_Detail.this).load("http://210.122.7.193:8080/Trophy_img/contest/"+ContestsDetailParsedData[0][2]+".jpg")
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(image);

        String a = ContestsDetailParsedData[0][10].trim();
        String b = ContestsDetailParsedData[0][12].trim();


//대회신청 관련  - 현재는 외부접수만 -> 향후 업데이트 내용
        trophy.projetc2.Http.HttpClient http_check = new trophy.projetc2.Http.HttpClient();
        String result1 = http_check.HttpClient("Trophy_part1","Contest_Detail_Check.jsp",Pk,Contest_Pk);
        String[][] ParsedData_Check = jsonParserList_ContestDetail_Check(result1);
        if (ParsedData_Check[0][0].equals("succed")) {
            Status = "succed";
            layout_contest_detail_caution.setVisibility(View.GONE);
        }
        else if (ParsedData_Check[0][0].equals("already")) {
            Status = "already";
            layout_contest_detail_caution.setText("이미 신청중입니다.");
            layout_contest_submit.setBackgroundColor(getResources().getColor(R.color.DarkGray));
            layout_contest_submit.setTextColor(getResources().getColor(R.color.White));
        }
        else if (ParsedData_Check[0][0].equals("notTeam")) {
            Status = "notTeam";
            layout_contest_detail_caution.setText("팀 가입 후, 대회신청 가능합니다.");
            layout_contest_submit.setBackgroundColor(getResources().getColor(R.color.DarkGray));
            layout_contest_submit.setTextColor(getResources().getColor(R.color.White));
        }
        else if (ParsedData_Check[0][0].equals("notDuty")) {
            Status = "notDuty";
            layout_contest_detail_caution.setText("대회 신청은 팀 대표만 가능합니다.");
            layout_contest_submit.setBackgroundColor(getResources().getColor(R.color.DarkGray));
            layout_contest_submit.setTextColor(getResources().getColor(R.color.White));
        }
        layout_contest_submit.setText("외부접수");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy / MM / dd");
        try {
            Date date2 = dateFormat.parse(b);
            Date currentDay = new Date();
            Long FinishTime = date2.getTime()+86400000;
            if(currentDay.getTime() < FinishTime) {
                long diff = FinishTime - currentDay.getTime();
                long diffday = diff / (24 * 60 * 60 * 1000);
                if(diffday > 7) {
                    diffday = diffday/7;
                    Da = " 주";
                }

                else{
                    Da = " 일";
                }
                remainder.setText(diffday+Da);
            }else {
                remainder.setText("마감");
                remainder1.setVisibility(View.GONE);
                remainder2.setVisibility(View.GONE);
                layout_contest_submit.setText("마감");
                layout_contest_submit.setBackgroundColor(getResources().getColor(R.color.DarkGray));
                layout_contest_submit.setTextColor(getResources().getColor(R.color.White));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        layout_contest_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(layout_contest_submit.getText().toString().equals("마감")){
                    Snackbar.make(view,"대회 접수가 마감되었습니다.",Snackbar.LENGTH_SHORT).show();
                }
                else{
//                    if (Pk.equals("") || Pk.equals(".")) {
//                        Intent intent_login = new Intent(Contest_Detail.this, Login.class);
//                        startActivity(intent_login);
//                        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
//                    }
//                    else {
//                        if(Status.equals("succed")){
//                            Intent intent1 = new Intent(Contest_Detail.this, Contest_Detail_Form.class);
//                            intent1.putExtra("Pk",Pk);
//                            intent1.putExtra("Contest_Pk",Contest_Pk);
//                            intent1.putExtra("Contest_Name",Contest_Name);
//                            startActivity(intent1);
//                            overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
//                        }
//                        else {
//
//                        }
//                    }
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(ContestsDetailParsedData[0][16]));
                    startActivity(intent);
                }
            }
        });
    }

    public String[][] jsonParserList_getContestDetail(String pRecvServerPage) {
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try {
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");

            String[] jsonName = {"msg1", "msg2", "msg3", "msg4", "msg5", "msg6", "msg7", "msg8", "msg9", "msg10", "msg11", "msg12", "msg13","msg14", "msg15","msg16","msg17"};
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
    public String[][] jsonParserList_ContestDetail_Check(String pRecvServerPage) {
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

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
        super.onBackPressed();
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}