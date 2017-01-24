package trophy.projetc2.Contest;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import trophy.projetc2.R;
import trophy.projetc2.User.Login;

/**
 * Created by ldong on 2016-11-12.
 */

public class Contest_Detail extends AppCompatActivity {
    Button layout_contest_submit;
    private LayoutInflater inflater;
    static TimerTask myTask;
    static Timer timer;
    static String Pk,Contest_Pk;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_contest_detail);
        //GlobalApplication.setCurrentActivity(this);
        inflater=getLayoutInflater();
        layout_contest_submit = (Button)findViewById(R.id.layout_contest_submit);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Intent intent = getIntent();
        Contest_Pk = intent.getStringExtra("Contest_Pk");
        preferences = getSharedPreferences("trophy", MODE_PRIVATE);
        Pk = preferences.getString("Pk", ".");
        String Da = " 일";
////다이얼로그 광고
        final View layout = inflater.inflate(R.layout.layout_customdialog_contest_ad, (ViewGroup) findViewById(R.id.Layout_CustomDialog_Contest_AD_Root));
        final ImageView Layout_CustomDialog_Contest_AD_ImageView = (ImageView) layout.findViewById(R.id.Layout_CustomDialog_Contest_AD_ImageView);
        final LinearLayout aaa= (LinearLayout)layout.findViewById(R.id.aaa);
        final TextView Layout_CustomDialog_Contest_AD_TextView = (TextView) layout.findViewById(R.id.Layout_CustomDialog_Contest_AD_TextView);
        final Button foucs = (Button)layout.findViewById(R.id.focus);
        final Button close = (Button)layout.findViewById(R.id.close);
        final Dialog DutyDialog = new Dialog(Contest_Detail.this);
        DutyDialog
                .setContentView(layout);
        myTask = new TimerTask() {
            int i = 5;

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
        ////////////////////////////////////
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
        String result = "";
        try {
            HttpClient client = new DefaultHttpClient();
            String postURL = "http://210.122.7.193:8080/Trophy_part3/Contest_Customlist_detail.jsp";
            HttpPost post = new HttpPost(postURL);

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("Pk", Contest_Pk));

            UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params, HTTP.UTF_8);
            post.setEntity(ent);

            HttpResponse response = client.execute(post);
            BufferedReader bufreader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "utf-8"));

            String line = null;
            while ((line = bufreader.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[][] ContestsDetailParsedData = jsonParserList_getContestDetail(result);

        title.setText(ContestsDetailParsedData[0][1]);
        host.setText(ContestsDetailParsedData[0][6]);
        management.setText(ContestsDetailParsedData[0][7]);
        support.setText(ContestsDetailParsedData[0][8]);
        date.setText(ContestsDetailParsedData[0][9]);
        recruitPeriod.setText(ContestsDetailParsedData[0][10] + " ~ " + ContestsDetailParsedData[0][11]);
        DetailInfo.setText(ContestsDetailParsedData[0][12]);

        Glide.with(Contest_Detail.this).load("http://210.122.7.193:8080/Web_basket/imgs1/Contest/"+ContestsDetailParsedData[0][2]+".jpg")
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(image);

        String a = ContestsDetailParsedData[0][10].trim();
        String b = ContestsDetailParsedData[0][11].trim();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yy / MM / dd");
        try {
            Date date2 = dateFormat.parse(b);
            Date currentDay = new Date();

            if(currentDay.getTime() < date2.getTime()) {
                long diff = date2.getTime() - currentDay.getTime();
                long diffday = diff / (24 * 60 * 60 * 1000);
                if(diff > 7) {
                    diffday = diffday/7;
                    Da = " 주";
                }
                String aaaa = Long.toString(diffday);
                remainder.setText(diffday+Da);
            }else {
                remainder.setText("마감");
                remainder1.setVisibility(View.GONE);
                remainder2.setVisibility(View.GONE);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        layout_contest_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Pk.equals("") || Pk.equals(".")) {
                    Intent intent_login = new Intent(Contest_Detail.this, Login.class);
                    startActivity(intent_login);
                }
                else {
                    trophy.projetc2.Http.HttpClient http_check = new trophy.projetc2.Http.HttpClient();
                    String result = http_check.HttpClient("Trophy_part1","Contest_Detail_Check.jsp",Pk,Contest_Pk);
                    String[][] ParsedData_Check = jsonParserList_ContestDetail_Check(result);
                    if (ParsedData_Check[0][0].equals("succed")) {
                        Intent intent = new Intent(Contest_Detail.this, Contest_Detail_Form.class);
                        intent.putExtra("Pk",Pk);
                        intent.putExtra("Contest_Pk",Contest_Pk);
                        startActivity(intent);
                        overridePendingTransition(R.anim.anim_slide_in_top, R.anim.anim_slide_out_bottom);
                    }
                    else if (ParsedData_Check[0][0].equals("already")) {
                        Snackbar.make(view,"이미 신청중입니다.", Snackbar.LENGTH_SHORT).show();
                    }
                    else if (ParsedData_Check[0][0].equals("notTeam")) {
                        Snackbar.make(view,"대회 신청할 팀이 존재하지 않습니다", Snackbar.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    public String[][] jsonParserList_getContestDetail(String pRecvServerPage) {
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try {
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");

            String[] jsonName = {"Pk", "Title", "Image", "currentNum", "maxNum", "Payment", "Host", "Management", "Support", "ContestDate", "RecruitStartDate", "RecruitFinishDate", "DetailInfo"};
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
}