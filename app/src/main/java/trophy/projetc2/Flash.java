package trophy.projetc2;

import android.content.Intent;
import android.net.Uri;
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
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import me.drakeet.materialdialog.MaterialDialog;
import trophy.projetc2.Http.HttpClient;

/**
 * Created by 박효근 on 2017-05-18.
 */

public class Flash extends AppCompatActivity{
    static String Project_version = "21";
    static TimerTask myTask;
    static Timer timer;
    String[][] ParsedData_Setting;
    String strCurYear, strCurMonth, strCurDay, strCurHour,strCurMinute, strCurToday, strCurTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flash);
        currentTime();
        HttpClient http_setting = new HttpClient();
        String result = http_setting.HttpClient("Trophy_part1","Setting.jsp");
        ParsedData_Setting = jsonParserList_Setting(result);
        if(!Project_version.equals(ParsedData_Setting[0][0])){
            LayoutInflater inflater = (LayoutInflater)Flash.this.getSystemService(Flash.LAYOUT_INFLATER_SERVICE);
            final View layout = inflater.inflate(R.layout.layout_customdialog_update, (ViewGroup)findViewById(R.id.Customdialog_Update_Root));
            final Button Customdialog_Update_Button_Ok = (Button)layout.findViewById(R.id.Customdialog_Update_Button_Ok);
            final MaterialDialog TeamInfo_Dialog = new MaterialDialog(Flash.this);
            TeamInfo_Dialog
                    .setContentView(layout)
                    .setCanceledOnTouchOutside(true);
            TeamInfo_Dialog.show();
            Customdialog_Update_Button_Ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=trophy.projetc2"));
                    startActivity(intent);
                    finish();
                }
            });
        }
        else{
            myTask = new TimerTask() {
                int i = 1;

                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // 해당 작업을 처리함
                            if (i <= 0) {
                                HttpClient http_count = new HttpClient();
                                http_count.HttpClient("Trophy_manager","Today_Counting.jsp", strCurToday);
                                timer.cancel();
                                Intent intent = new Intent(Flash.this, MainActivity.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.anim_fade_in,R.anim.anim_fade_out);
                                finish();
                            }
                        }
                    });
                    i--;
                    //시간이 초과된 경우 game 내 데이터 삭제 및 초기화.

                }
            };
            timer = new Timer();
            timer.schedule(myTask, 1000, 1000); // 5초후 첫실행, 1초마다 계속실행
        }
    }
    public String[][] jsonParserList_Setting(String pRecvServerPage) {
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
        SimpleDateFormat CurDateFormat = new SimpleDateFormat("yyyy / MM / dd");
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
