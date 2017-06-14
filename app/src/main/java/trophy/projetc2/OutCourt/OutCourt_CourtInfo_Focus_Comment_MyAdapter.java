package trophy.projetc2.OutCourt;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import me.drakeet.materialdialog.MaterialDialog;
import trophy.projetc2.Http.HttpClient;
import trophy.projetc2.R;

/**
 * Created by 박효근 on 2017-06-10.
 */

public class OutCourt_CourtInfo_Focus_Comment_MyAdapter extends BaseAdapter{
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<OutCourt_CourtInfo_Focus_Comment_MyData> arrData;
    ImageView OutCourt_CourtInfo_Focus_Comment_Customlist_ImageView_Emblem;
    TextView OutCourt_CourtInfo_Focus_Comment_Customlist_TextView_Date;
    TextView OutCourt_CourtInfo_Focus_Comment_Customlist_TextView_Name;
    TextView OutCourt_CourtInfo_Focus_Comment_Customlist_TextView_Memo;
    String[][] parsedData_Content_Delete, parsedData_User;
    String[][] parsedData_Player_Focus;
    String strCurAll, strCurMonth, strCurDay, strCurHour,strCurMinute, strCurToday, strCurTime;
    int a;
    public OutCourt_CourtInfo_Focus_Comment_MyAdapter(Context c, ArrayList<OutCourt_CourtInfo_Focus_Comment_MyData> arr) {
        this.context = c;
        this.arrData = arr;
        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return arrData.size();
    }

    public Object getItem(int position) {
        return arrData.get(position).getOutCourt_Content_Pk();
    }

    public long getItemId(int position) {
        return position;
    }


    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_navigation_outcourt_courtinfo_focus_comment_customlist, parent, false);
        }
        OutCourt_CourtInfo_Focus_Comment_Customlist_ImageView_Emblem= (ImageView) convertView.findViewById(R.id.OutCourt_CourtInfo_Focus_Comment_Customlist_ImageView_Emblem);
        OutCourt_CourtInfo_Focus_Comment_Customlist_TextView_Name= (TextView)convertView.findViewById(R.id.OutCourt_CourtInfo_Focus_Comment_Customlist_TextView_Name);
        OutCourt_CourtInfo_Focus_Comment_Customlist_TextView_Memo= (TextView)convertView.findViewById(R.id.OutCourt_CourtInfo_Focus_Comment_Customlist_TextView_Memo);
        OutCourt_CourtInfo_Focus_Comment_Customlist_TextView_Date =(TextView)convertView.findViewById(R.id.OutCourt_CourtInfo_Focus_Comment_Customlist_TextView_Date);
        currentTime();
        HttpClient http_user = new HttpClient();
        String result1 = http_user.HttpClient("Trophy_part1","User.jsp",arrData.get(position).getUser_Pk());
        parsedData_User = jsonParserList_User(result1);

        try{
            String En_Profile = URLEncoder.encode(parsedData_User[0][2], "utf-8");
            if(En_Profile.equals("."))
            {
                Glide.with(context).load(R.drawable.profile_basic_image).diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true).into(OutCourt_CourtInfo_Focus_Comment_Customlist_ImageView_Emblem);
            }
            else
            {
                Glide.with(context).load("http://210.122.7.193:8080/Trophy_img/profile/"+En_Profile+".jpg").diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true).bitmapTransform(new CropCircleTransformation(Glide.get(context).getBitmapPool()))
                        .into(OutCourt_CourtInfo_Focus_Comment_Customlist_ImageView_Emblem);
            }
        }
        catch (UnsupportedEncodingException e){

        }
        ///내 댓글인 경우
        if(arrData.get(position).getMyUser_Pk().equals(arrData.get(position).getUser_Pk())){
            a = 1;
        }
        else{
            a = 2;
        }

        if(parsedData_User[0][0].equals(".")){
            OutCourt_CourtInfo_Focus_Comment_Customlist_TextView_Name.setText("탈퇴한 이용자");
        }
        else{
            OutCourt_CourtInfo_Focus_Comment_Customlist_TextView_Name.setText(parsedData_User[0][0]);
        }


        String str = arrData.get(position).getDate();
        String[] data = str.split(":::");
        if(data[0].equals(strCurToday)){
            OutCourt_CourtInfo_Focus_Comment_Customlist_TextView_Date.setText(data[1]);
        }
        else{
            OutCourt_CourtInfo_Focus_Comment_Customlist_TextView_Date.setText(data[0]);
        }
        OutCourt_CourtInfo_Focus_Comment_Customlist_ImageView_Emblem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(parsedData_User[0][0].equals(".")){
                    Snackbar.make(view, "탈퇴한 이용자입니다.",Snackbar.LENGTH_SHORT).show();
                }
                else{
                    LayoutInflater inflater = (LayoutInflater)view.getContext().getSystemService(context.LAYOUT_INFLATER_SERVICE);
                    final View layout = inflater.inflate(R.layout.layout_customdialog_teaminfo_player_focus, (ViewGroup) view.findViewById(R.id.Layout_CustomDialog_TeamInfo_Player_Focus_Root));
                    final TextView Layout_CustomDialog_TeamInfo_Player_Focus_Title = (TextView)layout.findViewById(R.id.Layout_CustomDialog_TeamInfo_Player_Focus_Title);
                    final ImageView Layout_CustomDialog_TeamInfo_Player_Focus_Back = (ImageView)layout.findViewById(R.id.Layout_CustomDialog_TeamInfo_Player_Focus_Back);
                    final ImageView Layout_CustomDialog_TeamInfo_Player_Focus_Profile = (ImageView)layout.findViewById(R.id.Layout_CustomDialog_TeamInfo_Player_Focus_Profile);
                    final Button Layout_CustomDialog_TeamInfo_Player_Focus_TeamName = (Button)layout.findViewById(R.id.Layout_CustomDialog_TeamInfo_Player_Focus_TeamName);
                    final Button Layout_CustomDialog_TeamInfo_Player_Focus_Age = (Button)layout.findViewById(R.id.Layout_CustomDialog_TeamInfo_Player_Focus_Age);
                    final Button Layout_CustomDialog_TeamInfo_Player_Focus_Sex = (Button)layout.findViewById(R.id.Layout_CustomDialog_TeamInfo_Player_Focus_Sex);
                    final Button Layout_CustomDialog_TeamInfo_Player_Focus_Address = (Button)layout.findViewById(R.id.Layout_CustomDialog_TeamInfo_Player_Focus_Address);
                    final ImageView Layout_CustomDialog_TeamInfo_Player_Focus_Phone = (ImageView) layout.findViewById(R.id.Layout_CustomDialog_TeamInfo_Player_Focus_Phone);
                    Layout_CustomDialog_TeamInfo_Player_Focus_Title.setText("선수 정보");

                    try{
                        String En_Profile = URLEncoder.encode(parsedData_User[0][2], "utf-8");
                        if(parsedData_User[0][2].equals("."))
                        {
                            Glide.with(context).load(R.drawable.profile_basic_image).diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .skipMemoryCache(true).into(Layout_CustomDialog_TeamInfo_Player_Focus_Profile);
                        }
                        else
                        {
                            Glide.with(context).load("http://210.122.7.193:8080/Trophy_img/profile/"+parsedData_User[0][2]+".jpg").bitmapTransform(new CropCircleTransformation(Glide.get(context).getBitmapPool()))
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .skipMemoryCache(true)
                                    .into(Layout_CustomDialog_TeamInfo_Player_Focus_Profile);
                        }
                    }
                    catch (UnsupportedEncodingException e)
                    {

                    }
                    HttpClient http_Joiner= new HttpClient();
                    String result = http_Joiner.HttpClient("Trophy_part1","OutCourt_Player_Focus.jsp",arrData.get(position).getUser_Pk());
                    parsedData_Player_Focus = jsonParserList_Player_Focus(result);
                    Layout_CustomDialog_TeamInfo_Player_Focus_TeamName.setText(parsedData_Player_Focus[0][0]);
                    Layout_CustomDialog_TeamInfo_Player_Focus_Age.setText(ChangeAge(parsedData_Player_Focus[0][1]));
                    Layout_CustomDialog_TeamInfo_Player_Focus_Sex.setText(parsedData_Player_Focus[0][2]);
                    Layout_CustomDialog_TeamInfo_Player_Focus_Address.setText(parsedData_Player_Focus[0][3]);
                    Layout_CustomDialog_TeamInfo_Player_Focus_Phone.setVisibility(View.GONE);
                    final MaterialDialog TeamPlayerDialog = new MaterialDialog(context);
                    TeamPlayerDialog
                            .setTitle("신청자 정보")
                            .setView(layout)
                            .setCanceledOnTouchOutside(true);
                    TeamPlayerDialog.show();
                    Layout_CustomDialog_TeamInfo_Player_Focus_Back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            TeamPlayerDialog.dismiss();
                        }
                    });
                }
            }
        });
        OutCourt_CourtInfo_Focus_Comment_Customlist_TextView_Memo.setText(arrData.get(position).getMemo());

        return convertView;

    }
    public String[][] jsonParserList_Content_Delete(String pRecvServerPage){
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
    @Override
    public int getItemViewType(int position) {
        return a;
    }
    public String[][] jsonParserList_User(String pRecvServerPage){
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
    public String[][] jsonParserList_Player_Focus(String pRecvServerPage) {
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try {
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");

            String[] jsonName = {"msg1","msg2","msg3","msg4"};
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
    //date 입력받아 나이 구하는 함수
    public String ChangeAge(String Age){
        Calendar cal= Calendar.getInstance ();
        String[] str = new String(Age).split(" \\/ ");
        String[] str_day = new String(str[2]).split(" ");
        int year = Integer.parseInt(str[0]);
        int month = Integer.parseInt(str[1]);
        int day = Integer.parseInt(str_day[0]);

        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month-1);
        cal.set(Calendar.DATE, day);

        Calendar now = Calendar.getInstance ();

        int age = now.get(Calendar.YEAR) - cal.get(Calendar.YEAR);
        if (  (cal.get(Calendar.MONTH) > now.get(Calendar.MONTH))
                || (    cal.get(Calendar.MONTH) == now.get(Calendar.MONTH)
                && cal.get(Calendar.DAY_OF_MONTH) > now.get(Calendar.DAY_OF_MONTH)   )
                ){
            age--;
        }
        String Str_age = Integer.toString(age);
        return Str_age;
    }
}
