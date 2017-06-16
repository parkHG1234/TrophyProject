package trophy.projetc2.OutCourt;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.MediaStore;
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
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import me.drakeet.materialdialog.MaterialDialog;
import trophy.projetc2.Http.HttpClient;
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
    ImageView OutCourt_CourtInfo_Focus_TextView_menu;
    ImageView OutCourt_CourtInfo_Focus_ImageView_Modifer_Profile;
    ImageView OutCourt_CourtInfo_Focus_ImageView_Profile;
    EditText OutCourt_CourtInfo_Focus_EditText_Write;
    ListView OutCourt_CourtInfo_Focus_ListView_Content;
    ScrollView ScrollView;
    String CourtName, CourtIntro, User_Pk, Modifier, Modifier_Profile, Modifier_Date, Court_Pk, Today_Content;
    String[][] parsedData_OutCourt, parsedData_OutCourt_Content; String[][] parsedData_Modify;String[][] parsedData_User;
    String strCurAll, strCurMonth, strCurDay, strCurHour,strCurMinute, strCurToday, strCurTime;
    ArrayList<OutCourt_CourtInfo_Focus_MyData> OutCourt_CourtInfo_Focus_MyData;
    MaterialDialog Court_Menu_Dialog;
    boolean lastitemVisibleFlag = false;
    int ContentCount = 100000;
    static OutCourt_CourtInfo_Focus_MyAdapter adapter;
    int int_scrollViewPos;
    int int_TextView_lines;
    static int scrollHeight = 0;
    static int contentCount=0;
    String Content = "exist";
    View dialogview;
    static int Content_Total_ImageH = 0;
    int width;static int imageH=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_navigation_outcourt_courtinfo_focus);
        Intent intent1 = getIntent();
        CourtName = intent1.getStringExtra("CourtName");
        User_Pk = intent1.getStringExtra("User_Pk");
        Court_Pk = intent1.getStringExtra("Court_Pk");
        Today_Content = intent1.getStringExtra("Today_Content");
        int width = getWindowManager().getDefaultDisplay().getWidth();
        imageH = getWindowManager().getDefaultDisplay().getWidth();
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
        OutCourt_CourtInfo_Focus_ImageView_Profile = (ImageView)findViewById(R.id.OutCourt_CourtInfo_Focus_ImageView_Profile);
        OutCourt_CourtInfo_Focus_TextView_menu = (ImageView)findViewById(R.id.OutCourt_CourtInfo_Focus_TextView_menu);
        HttpClient http_user = new HttpClient();
        String result2 = http_user.HttpClient("Trophy_part1","OutCourt_Focus_User.jsp",User_Pk);
        parsedData_User = jsonParserList_User(result2);

        HttpClient http_match_focus = new HttpClient();
        String result = http_match_focus.HttpClient("Trophy_part1","OutCourt_Focus.jsp",CourtName);
        parsedData_OutCourt = jsonParserList_OurtCourt(result);

        CourtIntro = parsedData_OutCourt[0][4];
        Modifier_Profile = parsedData_OutCourt[0][5];
        Modifier = parsedData_OutCourt[0][6];
        Modifier_Date = parsedData_OutCourt[0][7];
        OutCourt_CourtInfo_Focus_TextView_CourtAddress.setText(parsedData_OutCourt[0][2]);
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
        try{
            String En_Profile = URLEncoder.encode(parsedData_User[0][0], "utf-8");
            Log.i("test", parsedData_User[0][0]);
            if(En_Profile.equals("."))
            {
                Glide.with(OutCourt_CourtInfo_Focus.this).load(R.drawable.profile_basic_image).diskCacheStrategy(DiskCacheStrategy.NONE).bitmapTransform(new CropCircleTransformation(Glide.get(OutCourt_CourtInfo_Focus.this).getBitmapPool()))
                        .skipMemoryCache(true)
                        .into(OutCourt_CourtInfo_Focus_ImageView_Profile);
            }
            else
            {
                Glide.with(OutCourt_CourtInfo_Focus.this).load("http://210.122.7.193:8080/Trophy_img/profile/"+En_Profile+".jpg").bitmapTransform(new CropCircleTransformation(Glide.get(getApplicationContext()).getBitmapPool()))
                        .diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(OutCourt_CourtInfo_Focus_ImageView_Profile);
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
            OutCourt_CourtInfo_Focus_MyData.add(new OutCourt_CourtInfo_Focus_MyData(parsedData_OutCourt_Content[j][0], parsedData_OutCourt_Content[j][1], parsedData_OutCourt_Content[j][2], parsedData_OutCourt_Content[j][3], parsedData_OutCourt_Content[j][4], parsedData_OutCourt_Content[j][5], parsedData_OutCourt_Content[j][6],strCurToday,OutCourt_CourtInfo_Focus.this,User_Pk, parsedData_OutCourt_Content[j][7], parsedData_OutCourt_Content[j][8]));
            ContentCount = Integer.parseInt(parsedData_OutCourt_Content[j][0]);
            Log.i("Content123",Integer.toString(ContentCount));
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
                        if(Content.equals("exist")){
                            OutCourt_Scroll OutCourt_Scroll = new OutCourt_Scroll();
                            OutCourt_Scroll.execute();
                        }
                        else if(Content.equals("noexist")){

                        }
                    }

            }
        });
        OutCourt_CourtInfo_Focus_TextView_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogview = view;
                LayoutInflater inflater = (LayoutInflater)view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
                final View layout = inflater.inflate(R.layout.layout_customdialog_menu, (ViewGroup) view.findViewById(R.id.Customdialog_Menu_Root));
                final Button Customdialog_Menu_Button_Menu1 = (Button)layout.findViewById(R.id.Customdialog_Menu_Button_Menu1);
                Court_Menu_Dialog = new MaterialDialog(view.getContext());
                Court_Menu_Dialog
                        .setContentView(layout)
                        .setCanceledOnTouchOutside(true);
                Court_Menu_Dialog.show();
                Customdialog_Menu_Button_Menu1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //사진 읽어오기위한 uri 작성하기.
                        Uri uri = Uri.parse("content://media/external/images/media");
                        //무언가 보여달라는 암시적 인텐트 객체 생성하기.
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        //인텐트에 요청을 덛붙인다.
                        intent.setAction(Intent.ACTION_PICK);
                        //모든 이미지
                        intent.setType("image/*");
                        //결과값을 받아오는 액티비티를 실행한다.
                        startActivityForResult(intent, 0);
                    }
                });
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
            OutCourt_CourtInfo_Focus_MyData.add(new OutCourt_CourtInfo_Focus_MyData(parsedData_OutCourt_Content[j][0], parsedData_OutCourt_Content[j][1], parsedData_OutCourt_Content[j][2], parsedData_OutCourt_Content[j][3], parsedData_OutCourt_Content[j][4], parsedData_OutCourt_Content[j][5], parsedData_OutCourt_Content[j][6],strCurToday,OutCourt_CourtInfo_Focus.this,User_Pk, parsedData_OutCourt_Content[j][7], parsedData_OutCourt_Content[j][8]));
            ContentCount = Integer.parseInt(parsedData_OutCourt_Content[j][0]);
        }
        adapter = new OutCourt_CourtInfo_Focus_MyAdapter(this, OutCourt_CourtInfo_Focus_MyData);
        OutCourt_CourtInfo_Focus_ListView_Content.setAdapter(adapter);
        //setListViewHeightBasedOnChildren(OutCourt_CourtInfo_Focus_ListView_Content);
    }
    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();

       // setListViewHeightBasedOnChildren(OutCourt_CourtInfo_Focus_ListView_Content);
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
    public String[][] jsonParserList_User(String pRecvServerPage){
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
    public void currentTime(){
        long now = System.currentTimeMillis();
// 현재 시간을 저장 한다.
        Date date = new Date(now);
// 시간 포맷 지정
        SimpleDateFormat CurDateFormat = new SimpleDateFormat("yyyy / MM / dd");
        SimpleDateFormat CurTimeFormat = new SimpleDateFormat("HH : mm");
        SimpleDateFormat CurAllFormat = new SimpleDateFormat("MM_dd_HH_mm");
        SimpleDateFormat CurMonthFormat = new SimpleDateFormat("MM");
        SimpleDateFormat CurDayFormat = new SimpleDateFormat("dd");
        SimpleDateFormat CurHourFormat = new SimpleDateFormat("HH");
        SimpleDateFormat CurMinuteFormat = new SimpleDateFormat("mm");
// 지정된 포맷으로 String 타입 리턴
        strCurToday = CurDateFormat.format(date);
        strCurTime = CurTimeFormat.format(date);
        strCurMonth = CurMonthFormat.format(date);
        strCurDay = CurDayFormat.format(date);
        strCurHour = CurHourFormat.format(date);
        strCurAll = CurAllFormat.format(date);
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
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1))-(imageH*9);
        listView.setLayoutParams(params);
        listView.requestLayout();
        scrollHeight= params.height;
        Log.i("test123", Integer.toString(scrollHeight));
    }
    public static void setListViewHeightBasedOnChildren_scroll(ListView listView) {
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
        Log.i("test123", Integer.toString(scrollHeight));
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
                parsedData = jsonParserList_OurtCourt_Content(result1);
                for (int j = 0; j < parsedData.length; j++) {
                    OutCourt_CourtInfo_Focus_MyData.add(new OutCourt_CourtInfo_Focus_MyData(parsedData[j][0], parsedData[j][1], parsedData[j][2], parsedData[j][3], parsedData[j][4], parsedData[j][5], parsedData[j][6],strCurToday,OutCourt_CourtInfo_Focus.this,User_Pk, parsedData[j][7], parsedData[j][8]));
                    ContentCount = Integer.parseInt(parsedData[j][0]);
                }
                if(parsedData.length == 0){
                    Content = "noexist";
                }
                adapter = new OutCourt_CourtInfo_Focus_MyAdapter(OutCourt_CourtInfo_Focus.this, OutCourt_CourtInfo_Focus_MyData);

                new Thread(new Runnable() { @Override public void run() {
                    // 현재 UI 스레드가 아니기 때문에 메시지 큐에 Runnable을 등록 함
                    runOnUiThread(new Runnable() {
                        public void run() {


                        } });
                }
                }).start();

                    출처: http://itmining.tistory.com/6 [IT 마이닝]

                return "succed";
            } catch (Exception e) {
                e.printStackTrace();
                return "failed";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            // 메시지 큐에 저장될 메시지의 내용
            OutCourt_CourtInfo_Focus_ListView_Content.setAdapter(adapter);
            setListViewHeightBasedOnChildren_scroll(OutCourt_CourtInfo_Focus_ListView_Content);
            asyncDialog.dismiss();
            super.onPostExecute(result);
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
                urlString = "http://210.122.7.193:8080/Trophy_part1/Court_Image_Upload.jsp";
                //절대경로를 획득한다!!! 중요~
                Cursor c = getContentResolver().query(Uri.parse(selPhotoUri.toString()), null, null, null, null);
                c.moveToNext();
                //업로드할 파일의 절대경로 얻어오기("_data") 로 해도 된다.
                String absolutePath = c.getString(c.getColumnIndex(MediaStore.MediaColumns.DATA));
                Log.e("###파일의 절대 경로###", absolutePath);
                //파일 업로드 시작!
                HttpFileUpload(urlString, "", absolutePath);
                Snackbar.make(dialogview,"사진 신청되었습니다.", Snackbar.LENGTH_INDEFINITE).setAction("확인", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Court_Menu_Dialog.dismiss();
                    }
                }).show();
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
            dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + URLEncoder.encode(Court_Pk+"_"+strCurAll, "utf-8") + ".jpg" + "\"" + lineEnd);
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
