package trophy.projetc2.OutCourt;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import me.drakeet.materialdialog.MaterialDialog;
import trophy.projetc2.Http.HttpClient;
import trophy.projetc2.Match.MyMatch;
import trophy.projetc2.Match.MyMatch_MyData;
import trophy.projetc2.R;
import trophy.projetc2.User.Login;

/**
 * Created by 박효근 on 2017-05-03.
 */

public class OutCourt_CourtInfo_Focus extends AppCompatActivity {
    ImageView OutCourt_CourtInfo_Focus_ImageView_Court, OutCourt_CourtInfo_Focus_ImageView_Back, OutCourt_CourtInfo_Focus_ImageView_CourtIntro_Modify;
    TextView OutCourt_CourtInfo_Focus_TextView_CourtName, OutCourt_CourtInfo_Focus_TextView_Title, OutCourt_CourtInfo_Focus_TextView_CourtAddress, OutCourt_CourtInfo_Focus_TextView_CourtIntro;
    TextView OutCourt_CourtInfo_Focus_TextView_Modifer_Date, OutCourt_CourtInfo_Focus_TextView_Modifer;
    TextView OutCourt_CourtInfo_Focus_TextView_TodayWrite;
    ImageView OutCourt_CourtInfo_Focus_ImageView_Modifer_Profile;
    EditText OutCourt_CourtInfo_Focus_EditText_Write;
    ListView OutCourt_CourtInfo_Focus_ListView_Content;
    ScrollView ScrollView;
    String CourtName, CourtIntro, User_Pk, Modifier, Modifier_Profile, Modifier_Date, Court_Pk, Today_Content;
    String[][] parsedData_OutCourt, parsedData_OutCourt_Content; String[][] parsedData_Modify;
    String strCurYear, strCurMonth, strCurDay, strCurHour,strCurMinute, strCurToday, strCurTime;
    ArrayList<OutCourt_CourtInfo_Focus_MyData> OutCourt_CourtInfo_Focus_MyData;
    boolean lastitemVisibleFlag = false;
    int ContentCount = 100000;
    OutCourt_CourtInfo_Focus_MyAdapter adapter;
    int int_scrollViewPos;
    int int_TextView_lines;
    static int scrollHeight = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_navigation_outcourt_courtinfo_focus);
        Intent intent1 = getIntent();
        CourtName = intent1.getStringExtra("CourtName");
        User_Pk = intent1.getStringExtra("User_Pk");
        Court_Pk = intent1.getStringExtra("Court_Pk");
        Today_Content = intent1.getStringExtra("Today_Content");

        currentTime();
        ScrollView = (ScrollView)findViewById(R.id.ScrollView);
        OutCourt_CourtInfo_Focus_TextView_Title = (TextView)findViewById(R.id.OutCourt_CourtInfo_Focus_TextView_Title);
        OutCourt_CourtInfo_Focus_ImageView_Back = (ImageView)findViewById(R.id.OutCourt_CourtInfo_Focus_ImageView_Back);
        OutCourt_CourtInfo_Focus_ImageView_Court = (ImageView)findViewById(R.id.OutCourt_CourtInfo_Focus_ImageView_Court);
        OutCourt_CourtInfo_Focus_TextView_CourtName = (TextView)findViewById(R.id.OutCourt_CourtInfo_Focus_TextView_CourtName);
        OutCourt_CourtInfo_Focus_TextView_CourtAddress = (TextView)findViewById(R.id.OutCourt_CourtInfo_Focus_TextView_CourtAddress);
        OutCourt_CourtInfo_Focus_TextView_CourtIntro = (TextView)findViewById(R.id.OutCourt_CourtInfo_Focus_TextView_CourtIntro);
        OutCourt_CourtInfo_Focus_ImageView_CourtIntro_Modify = (ImageView)findViewById(R.id.OutCourt_CourtInfo_Focus_ImageView_CourtIntro_Modify);
        OutCourt_CourtInfo_Focus_TextView_Modifer_Date = (TextView)findViewById(R.id.OutCourt_CourtInfo_Focus_TextView_Modifer_Date);
        OutCourt_CourtInfo_Focus_TextView_Modifer = (TextView)findViewById(R.id.OutCourt_CourtInfo_Focus_TextView_Modifer);
        OutCourt_CourtInfo_Focus_ImageView_Modifer_Profile = (ImageView)findViewById(R.id.OutCourt_CourtInfo_Focus_ImageView_Modifer_Profile);
        OutCourt_CourtInfo_Focus_EditText_Write = (EditText)findViewById(R.id.OutCourt_CourtInfo_Focus_EditText_Write);
        OutCourt_CourtInfo_Focus_ListView_Content = (ListView)findViewById(R.id.OutCourt_CourtInfo_Focus_ListView_Content);
        OutCourt_CourtInfo_Focus_TextView_TodayWrite = (TextView)findViewById(R.id.OutCourt_CourtInfo_Focus_TextView_TodayWrite);

        HttpClient http_match_focus = new HttpClient();
        String result = http_match_focus.HttpClient("Trophy_part1","OutCourt_Focus.jsp",CourtName);
        parsedData_OutCourt = jsonParserList_OurtCourt(result);
        CourtIntro = parsedData_OutCourt[0][4];
        Modifier_Profile = parsedData_OutCourt[0][5];
        Modifier = parsedData_OutCourt[0][6];
        Modifier_Date = parsedData_OutCourt[0][7];

        try{
            String En_Profile = URLEncoder.encode(parsedData_OutCourt[0][3], "utf-8");
            if(En_Profile.equals("."))
            {
                Glide.with(OutCourt_CourtInfo_Focus.this).load(R.drawable.court_basic).into(OutCourt_CourtInfo_Focus_ImageView_Court);
            }
            else
            {
                Glide.with(OutCourt_CourtInfo_Focus.this).load("http://210.122.7.193:8080/Trophy_img/court/"+En_Profile+".jpg")
                        .into(OutCourt_CourtInfo_Focus_ImageView_Court);
            }
        }
        catch (UnsupportedEncodingException e){
        }

        OutCourt_CourtInfo_Focus_TextView_Title.setText(CourtName);
        OutCourt_CourtInfo_Focus_TextView_TodayWrite.setText("오늘의 게시글 : " + Today_Content);
        OutCourt_CourtInfo_Focus_TextView_CourtName.setText(CourtName);
        OutCourt_CourtInfo_Focus_TextView_CourtIntro.setText(CourtIntro);

        if(CourtIntro.equals("코트 정보를 입력해주세요.")){
            OutCourt_CourtInfo_Focus_TextView_Modifer_Date.setText("");
            OutCourt_CourtInfo_Focus_TextView_Modifer.setText("");
            OutCourt_CourtInfo_Focus_ImageView_Modifer_Profile.setVisibility(View.INVISIBLE);
        }else{
            try{
                String En_Profile = URLEncoder.encode(Modifier_Profile, "utf-8");
                if(En_Profile.equals("."))
                {
                    Glide.with(OutCourt_CourtInfo_Focus.this).load(R.drawable.profile_basic_image).into(OutCourt_CourtInfo_Focus_ImageView_Modifer_Profile);
                }
                else
                {
                    Glide.with(OutCourt_CourtInfo_Focus.this).load("http://210.122.7.193:8080/Trophy_img/profile/"+En_Profile+".jpg").bitmapTransform(new CropCircleTransformation(Glide.get(OutCourt_CourtInfo_Focus.this).getBitmapPool()))
                            .into(OutCourt_CourtInfo_Focus_ImageView_Modifer_Profile);
                }
            }
            catch (UnsupportedEncodingException e){
            }
            OutCourt_CourtInfo_Focus_TextView_Modifer.setText(Modifier);
            OutCourt_CourtInfo_Focus_TextView_Modifer_Date.setText(Modifier_Date);
        }

        HttpClient http_match_focus_Content = new HttpClient();
        String result1 = http_match_focus_Content.HttpClient("Trophy_part1","OutCourt_Focus_Content.jsp",Court_Pk);
        parsedData_OutCourt_Content = jsonParserList_OurtCourt_Content(result1);

        OutCourt_CourtInfo_Focus_MyData = new ArrayList<OutCourt_CourtInfo_Focus_MyData>();
        for (int j = 0; j < parsedData_OutCourt_Content.length; j++) {
            OutCourt_CourtInfo_Focus_MyData.add(new OutCourt_CourtInfo_Focus_MyData(parsedData_OutCourt_Content[j][0], parsedData_OutCourt_Content[j][1], parsedData_OutCourt_Content[j][2], parsedData_OutCourt_Content[j][3], parsedData_OutCourt_Content[j][4], parsedData_OutCourt_Content[j][5], parsedData_OutCourt_Content[j][6],strCurToday,OutCourt_CourtInfo_Focus.this,User_Pk));
            ContentCount = Integer.parseInt(parsedData_OutCourt_Content[j][0]);
        }
        adapter = new OutCourt_CourtInfo_Focus_MyAdapter(this, OutCourt_CourtInfo_Focus_MyData);
        OutCourt_CourtInfo_Focus_ListView_Content.setAdapter(adapter);
        setListViewHeightBasedOnChildren(OutCourt_CourtInfo_Focus_ListView_Content);
        OutCourt_CourtInfo_Focus_ListView_Content.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                if(i == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && lastitemVisibleFlag) {
                    OutCourt_Scroll OutCourt_Scroll = new OutCourt_Scroll();
                    OutCourt_Scroll.execute();
                }
                Log.i("tt123", Integer.toString(ContentCount));
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                lastitemVisibleFlag = (totalItemCount > 0) && (firstVisibleItem + visibleItemCount >= totalItemCount);
            }
        });
        OutCourt_CourtInfo_Focus_ImageView_CourtIntro_Modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(User_Pk.equals(".")){
                    Intent intent_login = new Intent(OutCourt_CourtInfo_Focus.this, Login.class);
                    startActivity(intent_login);
                    overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                }
                else{
                    LayoutInflater inflater = (LayoutInflater)view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
                    final View layout = inflater.inflate(R.layout.layout_navigation_outcourt_courtinfo_focus_customdialog_modify, (ViewGroup) view.findViewById(R.id.TeamInfo_Customdialog_1_Root));
                    final TextView TeamInfo_Customdialog_1_Title = (TextView)layout.findViewById(R.id.TeamInfo_Customdialog_1_Title);
                    final ImageView TeamInfo_Customdialog_1_Back = (ImageView)layout.findViewById(R.id.TeamInfo_Customdialog_1_Back);
                    final EditText TeamInfo_Customdialog_1_Content = (EditText)layout.findViewById(R.id.TeamInfo_Customdialog_1_Content);
                    final Button TeamInfo_Customdialog_1_Ok = (Button)layout.findViewById(R.id.TeamInfo_Customdialog_1_Ok);
                    TeamInfo_Customdialog_1_Title.setText("코트소개 수정");
                    TeamInfo_Customdialog_1_Ok.setText("수정하기");
                    if(CourtIntro.equals("코트 정보를 입력해주세요.")){
                        TeamInfo_Customdialog_1_Content.setHint("코트 정보를 입력해주세요.");
                    }else{
                        TeamInfo_Customdialog_1_Content.setText(CourtIntro);
                    }
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
                            String result1 = http_match_focus_overlap.HttpClient("Trophy_part1","OutCourt_Focus_CourtIntro_Modify.jsp", CourtName, strCurToday, User_Pk,TeamInfo_Customdialog_1_Content.getText().toString());
                            parsedData_Modify = jsonParserList_Modify(result1);
                            if(parsedData_Modify[0][0].equals("succed")){
                                HttpClient http_match_focus = new HttpClient();
                                String result = http_match_focus.HttpClient("Trophy_part1","OutCourt_Focus.jsp",CourtName);
                                parsedData_OutCourt = jsonParserList_OurtCourt(result);
                                CourtIntro = parsedData_OutCourt[0][4];
                                Modifier_Profile = parsedData_OutCourt[0][5];
                                Modifier = parsedData_OutCourt[0][6];
                                Modifier_Date = parsedData_OutCourt[0][7];
                                try{
                                    String En_Profile = URLEncoder.encode(Modifier_Profile, "utf-8");
                                    if(En_Profile.equals("."))
                                    {
                                        Glide.with(OutCourt_CourtInfo_Focus.this).load(R.drawable.profile_basic_image).into(OutCourt_CourtInfo_Focus_ImageView_Modifer_Profile);
                                    }
                                    else
                                    {
                                        Glide.with(OutCourt_CourtInfo_Focus.this).load("http://210.122.7.193:8080/Trophy_img/profile/"+En_Profile+".jpg").bitmapTransform(new RoundedCornersTransformation(Glide.get(OutCourt_CourtInfo_Focus.this).getBitmapPool(),1,1))
                                                .into(OutCourt_CourtInfo_Focus_ImageView_Modifer_Profile);
                                    }
                                }
                                catch (UnsupportedEncodingException e){
                                }
                                OutCourt_CourtInfo_Focus_TextView_Modifer.setText(Modifier);
                                OutCourt_CourtInfo_Focus_TextView_Modifer_Date.setText(Modifier_Date);
                                OutCourt_CourtInfo_Focus_TextView_CourtIntro.setText(CourtIntro);
                                TeamInfo_Dialog.dismiss();
                            }
                            else{
                                Snackbar.make(view,"잠시 후 다시 시도해주세요.",Snackbar.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
        OutCourt_CourtInfo_Focus_EditText_Write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(User_Pk.equals(".")){
                    Intent intent_login = new Intent(OutCourt_CourtInfo_Focus.this, Login.class);
                    startActivity(intent_login);
                    overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                }else{
                    Intent intent1 = new Intent(OutCourt_CourtInfo_Focus.this, OutCourt_CourtInfo_Focus_Write.class);
                    intent1.putExtra("User_Pk", User_Pk);
                    intent1.putExtra("Court_Pk", Court_Pk);
                    startActivity(intent1);
                    overridePendingTransition(R.anim.anim_slide_in_bottom, R.anim.anim_slide_out_top);
                }
            }
        });
        OutCourt_CourtInfo_Focus_ImageView_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
            }
        });
        ScrollView.post(new Runnable() {
            @Override
            public void run() {
                ScrollView.scrollTo(0,0);
            }
        });

        ScrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                int_scrollViewPos = ScrollView.getScrollY();
                int_TextView_lines = ScrollView.getChildAt(0).getBottom() - ScrollView.getHeight();
                if(int_TextView_lines == int_scrollViewPos){
                    OutCourt_Scroll OutCourt_Scroll = new OutCourt_Scroll();
                    OutCourt_Scroll.execute();
                }
            }
        });

    }
    @Override
    protected void onRestart() {
        super.onRestart();
        HttpClient http_match_focus_Content = new HttpClient();
        String result1 = http_match_focus_Content.HttpClient("Trophy_part1","OutCourt_Focus_Content.jsp",Court_Pk);
        parsedData_OutCourt_Content = jsonParserList_OurtCourt_Content(result1);

        OutCourt_CourtInfo_Focus_MyData = new ArrayList<OutCourt_CourtInfo_Focus_MyData>();
        for (int j = 0; j < parsedData_OutCourt_Content.length; j++) {
            OutCourt_CourtInfo_Focus_MyData.add(new OutCourt_CourtInfo_Focus_MyData(parsedData_OutCourt_Content[j][0], parsedData_OutCourt_Content[j][1], parsedData_OutCourt_Content[j][2], parsedData_OutCourt_Content[j][3], parsedData_OutCourt_Content[j][4], parsedData_OutCourt_Content[j][5], parsedData_OutCourt_Content[j][6],strCurToday,OutCourt_CourtInfo_Focus.this,User_Pk));
            ContentCount = Integer.parseInt(parsedData_OutCourt_Content[j][0]);
        }
        adapter = new OutCourt_CourtInfo_Focus_MyAdapter(this, OutCourt_CourtInfo_Focus_MyData);
        OutCourt_CourtInfo_Focus_ListView_Content.setAdapter(adapter);
        setListViewHeightBasedOnChildren(OutCourt_CourtInfo_Focus_ListView_Content);
    }
    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
    }
    public String[][] jsonParserList_OurtCourt(String pRecvServerPage){
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try{
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");
            String[] jsonName = {"msg1","msg2","msg3","msg4","msg5","msg6", "msg7", "msg8"};
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
    public String[][] jsonParserList_Modify(String pRecvServerPage){
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
    public String[][] jsonParserList_OurtCourt_Content(String pRecvServerPage){
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try{
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");
            String[] jsonName = {"msg1","msg2","msg3","msg4","msg5","msg6","msg7"};
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
        scrollHeight= params.height;
    }
    public static void setListViewHeightBasedOnChildren_scroll(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = scrollHeight;
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
        scrollHeight= params.height;
    }
    public class OutCourt_Scroll extends AsyncTask<String, Void, String> {
        ProgressDialog asyncDialog = new ProgressDialog(OutCourt_CourtInfo_Focus.this);
        String[][] parsedData;

        @Override
        protected void onPreExecute() {
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage("잠시만 기다려주세요..");
            // show dialog
            asyncDialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                HttpClient http_match_focus_Content = new HttpClient();
                String result1 = http_match_focus_Content.HttpClient("Trophy_part1","OutCourt_Focus_Content_Scroll.jsp",Court_Pk,Integer.toString(ContentCount));
                parsedData_OutCourt_Content = jsonParserList_OurtCourt_Content(result1);

                for (int j = 0; j < parsedData_OutCourt_Content.length; j++) {
                    OutCourt_CourtInfo_Focus_MyData.add(new OutCourt_CourtInfo_Focus_MyData(parsedData_OutCourt_Content[j][0], parsedData_OutCourt_Content[j][1], parsedData_OutCourt_Content[j][2], parsedData_OutCourt_Content[j][3], parsedData_OutCourt_Content[j][4], parsedData_OutCourt_Content[j][5], parsedData_OutCourt_Content[j][6],strCurToday,OutCourt_CourtInfo_Focus.this,User_Pk));
                    ContentCount = Integer.parseInt(parsedData_OutCourt_Content[j][0]);
                }
                Log.i("ttt","ttt");
                adapter.notifyDataSetChanged();
                setListViewHeightBasedOnChildren_scroll(OutCourt_CourtInfo_Focus_ListView_Content);
                return "succed";
            } catch (Exception e) {
                e.printStackTrace();
                return "failed";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            //OutCourt_CourtInfo_Focus_ListView_Content.setAdapter(adapter);
            asyncDialog.dismiss();
            super.onPostExecute(result);
        }
    }
}
