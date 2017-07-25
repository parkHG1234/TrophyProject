package trophy.projetc2.OutCourt;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import trophy.projetc2.Http.HttpClient;
import trophy.projetc2.R;

/**
 * Created by 박효근 on 2017-06-09.
 */

public class OutCourt_CourtInfo_Focus_Comment extends AppCompatActivity{
    ImageView OutCourt_CourtInfo_Focus_Comment_ImageView_Back;
    SwipeMenuListView OutCourt_CourtInfo_Focus_Comment_ListView_Comment;
    EditText OutCourt_CourtInfo_Focus_Comment_EditText_Memo;
    TextView OutCourt_CourtInfo_Focus_Comment_TextView_Input;

    String Court_Content_Pk, User_Pk;
    String strCurAll, strCurMonth, strCurDay, strCurHour,strCurMinute, strCurToday, strCurTime;
    String[][] parsedData_Comment_Input, parsedData_Comment, parsedData_Delete;
    ArrayList<OutCourt_CourtInfo_Focus_Comment_MyData> OutCourt_CourtInfo_Focus_Comment_MyData;
    OutCourt_CourtInfo_Focus_Comment_MyAdapter adapter;
    InputMethodManager imm;
    int width=0;int height=0;int a=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_navigation_outcourt_courtinfo_focus_comment);
        width = getWindowManager().getDefaultDisplay().getWidth();
        height = getWindowManager().getDefaultDisplay().getWidth();
        Intent intent1 = getIntent();
        Court_Content_Pk = intent1.getStringExtra("Court_Content_Pk");
        User_Pk = intent1.getStringExtra("User_Pk");
        currentTime();
        OutCourt_CourtInfo_Focus_Comment_ImageView_Back = (ImageView)findViewById(R.id.OutCourt_CourtInfo_Focus_Comment_ImageView_Back);
        OutCourt_CourtInfo_Focus_Comment_ListView_Comment = (SwipeMenuListView)findViewById(R.id.OutCourt_CourtInfo_Focus_Comment_ListView_Comment);
        OutCourt_CourtInfo_Focus_Comment_EditText_Memo = (EditText)findViewById(R.id.OutCourt_CourtInfo_Focus_Comment_EditText_Memo);
        OutCourt_CourtInfo_Focus_Comment_TextView_Input = (TextView) findViewById(R.id.OutCourt_CourtInfo_Focus_Comment_TextView_Input);
        OutCourt_CourtInfo_Focus_Comment_TextView_Input.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/BMJUA_ttf.ttf"));
        imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        HttpClient http_match_focus_Content = new HttpClient();
        String result1 = http_match_focus_Content.HttpClient("Trophy_part1","OutCourt_Focus_Content_Comment.jsp",Court_Content_Pk);
        parsedData_Comment = jsonParserList_Comment(result1);

        OutCourt_CourtInfo_Focus_Comment_MyData = new ArrayList<OutCourt_CourtInfo_Focus_Comment_MyData>();
        for (int j = 0; j < parsedData_Comment.length; j++) {
            OutCourt_CourtInfo_Focus_Comment_MyData.add(new OutCourt_CourtInfo_Focus_Comment_MyData(parsedData_Comment[j][0], parsedData_Comment[j][1], parsedData_Comment[j][2], parsedData_Comment[j][3], parsedData_Comment[j][4], OutCourt_CourtInfo_Focus_Comment.this,User_Pk));
        }
        adapter = new OutCourt_CourtInfo_Focus_Comment_MyAdapter(OutCourt_CourtInfo_Focus_Comment.this, OutCourt_CourtInfo_Focus_Comment_MyData);
        OutCourt_CourtInfo_Focus_Comment_ListView_Comment.setAdapter(adapter);
        a = Integer.parseInt(String.valueOf(Math.round(width*0.15)));
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                switch (menu.getViewType()){
                    case 1:
                        // create "delete" item
                        SwipeMenuItem deleteItem = new SwipeMenuItem(
                                getApplicationContext());
                        // set item background
                        deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                                0x3F, 0x25)));
                        // set item width
                        deleteItem.setWidth(a);
                        // set a icon
                        deleteItem.setIcon(R.drawable.comment_delete);
                        // add to menu
                        menu.addMenuItem(deleteItem);
                        break;
                    case  2:
                        // create "delete" item
                        SwipeMenuItem NodeleteItem = new SwipeMenuItem(
                                getApplicationContext());
                        // set item background
                        NodeleteItem.setBackground(new ColorDrawable(getResources().getColor(R.color.DarkGray)));
                        // set item width
                        NodeleteItem.setWidth(a);
                        // set a icon
                        NodeleteItem.setIcon(R.drawable.comment_delete);
                        // add to menu
                        menu.addMenuItem(NodeleteItem);
                        break;
                }
            }
        };
        OutCourt_CourtInfo_Focus_Comment_ListView_Comment.setMenuCreator(creator);
        OutCourt_CourtInfo_Focus_Comment_ListView_Comment.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        ///내 댓글인 경우
                        if(User_Pk.equals(OutCourt_CourtInfo_Focus_Comment_MyData.get(position).getUser_Pk())){
                            HttpClient http_delete = new HttpClient();
                            String result1 = http_delete.HttpClient("Trophy_part1","OutCourt_Focus_Content_Comment_Delete.jsp",OutCourt_CourtInfo_Focus_Comment_MyData.get(position).getOutCourt_Content_Comment_Pk());
                            parsedData_Delete = jsonParserList_Succed(result1);
                            OutCourt_CourtInfo_Focus_Comment_MyData.remove(position);
                            adapter.notifyDataSetChanged();
                        }
                        else{
                           Snackbar.make(getCurrentFocus(),"게시글을 삭제할 수 없습니다.",Snackbar.LENGTH_SHORT).show();
                        }

                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });

        OutCourt_CourtInfo_Focus_Comment_ImageView_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
            }
        });
        OutCourt_CourtInfo_Focus_Comment_TextView_Input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(OutCourt_CourtInfo_Focus_Comment_EditText_Memo.getText().toString().equals("")){

                }
                else{
                    HttpClient http_comment= new HttpClient();
                    String result = http_comment.HttpClient("Trophy_part1","OutCourt_Focus_Comment_Input.jsp",Court_Content_Pk, User_Pk, strCurToday, OutCourt_CourtInfo_Focus_Comment_EditText_Memo.getText().toString());
                    parsedData_Comment_Input = jsonParserList_CommentInput(result);
                    if(parsedData_Comment_Input[0][0].equals("succed")){
                        OutCourt_CourtInfo_Focus_Comment_MyData.clear();

                        HttpClient http_match_focus_Content = new HttpClient();
                        String result1 = http_match_focus_Content.HttpClient("Trophy_part1","OutCourt_Focus_Content_Comment.jsp",Court_Content_Pk);
                        parsedData_Comment = jsonParserList_Comment(result1);

                        for (int j = 0; j < parsedData_Comment.length; j++) {
                            OutCourt_CourtInfo_Focus_Comment_MyData.add(new OutCourt_CourtInfo_Focus_Comment_MyData(parsedData_Comment[j][0], parsedData_Comment[j][1], parsedData_Comment[j][2], parsedData_Comment[j][3], parsedData_Comment[j][4], OutCourt_CourtInfo_Focus_Comment.this,User_Pk));
                        }
                        adapter = new OutCourt_CourtInfo_Focus_Comment_MyAdapter(OutCourt_CourtInfo_Focus_Comment.this, OutCourt_CourtInfo_Focus_Comment_MyData);
                        OutCourt_CourtInfo_Focus_Comment_ListView_Comment.setAdapter(adapter);
                        OutCourt_CourtInfo_Focus_Comment_EditText_Memo.setText("");
                        if(parsedData_Comment_Input[0][1].equals(User_Pk)){

                        }
                        else{
                            try {
                                String En_Profile = URLDecoder.decode(parsedData_Comment_Input[0][2], "utf-8");
                                HttpClient http_push = new HttpClient();
                                http_push.HttpClient("TodayBasket_manager","push.jsp", parsedData_Comment_Input[0][1], En_Profile+"님이 피드에 답글을 남겼습니다");
                            }catch (UnsupportedEncodingException e) {

                            }
                            }
                    }
                }
            }
        });
        OutCourt_CourtInfo_Focus_Comment_EditText_Memo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(OutCourt_CourtInfo_Focus_Comment_EditText_Memo.getText().toString().equals("")){
                    OutCourt_CourtInfo_Focus_Comment_TextView_Input.setTextColor(getResources().getColor(R.color.BlackGray));
                }
                else{
                    OutCourt_CourtInfo_Focus_Comment_TextView_Input.setTextColor(getResources().getColor(R.color.main1color));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    public void currentTime(){
        long now = System.currentTimeMillis();
// 현재 시간을 저장 한다.
        Date date = new Date(now);
// 시간 포맷 지정
        SimpleDateFormat CurDateFormat = new SimpleDateFormat("yyyy / MM / dd:::HH : mm");
        SimpleDateFormat CurTimeFormat = new SimpleDateFormat("HH : mm");
        SimpleDateFormat CurYearFormat = new SimpleDateFormat("yyyy / MM / dd");
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
        strCurMinute = CurMinuteFormat.format(date);
    }
    public String[][] jsonParserList_Succed(String pRecvServerPage) {
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
    public String[][] jsonParserList_CommentInput(String pRecvServerPage) {
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try {
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");
            String[] jsonName = {"msg1","msg2","msg3"};
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
    public String[][] jsonParserList_Comment(String pRecvServerPage) {
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try {
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");
            String[] jsonName = {"msg1","msg2","msg3","msg4","msg5"};
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
