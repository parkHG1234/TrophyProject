package trophy.projetc2.Navigation;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import me.drakeet.materialdialog.MaterialDialog;
import trophy.projetc2.Http.HttpClient;
import trophy.projetc2.R;

import static trophy.projetc2.Navigation.TeamManager.TeamManager_TeamName;
import static trophy.projetc2.Navigation.TeamManager_PlayerManager.refresh_player;

/**
 * Created by 박효근 on 2017-03-08.
 */

public class TeamInfo_Player_MyAdapter extends BaseAdapter{
    private Context context;
    private ArrayList<TeamInfo_Player_MyData> arrData;
    private LayoutInflater inflater;
    ImageView FirstProfile, SecondProfile, ThirdProfile, FourthProfile;
    TextView FirstName, SecondName, ThirdName, FourthName;
    LinearLayout Player1, Player2, Player3, Player4;
    String[][] parsedData_Player_Focus,parsedData_Joiner_Refuse,parsedData_Player_Out,parsedData_ChangeRepresent;
    String ProfileUrl;
    public TeamInfo_Player_MyAdapter(Context c, ArrayList<TeamInfo_Player_MyData> arr) {
        this.context = c;
        this.arrData = arr;
        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return arrData.size();
    }

    public Object getItem(int position) {
        return arrData.get(position).getFirst_Pk();
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_navigation_teammanager_playermanager_joiner_customlist, parent, false);
        }
        FirstProfile = (ImageView) convertView.findViewById(R.id.TeamManager_PlayerManager_Joiner_CustomList_ImageView_FirstProfile);
        FirstName = (TextView) convertView.findViewById(R.id.TeamManager_PlayerManager_Joiner_CustomList_TextView_FirstName);
        SecondProfile = (ImageView) convertView.findViewById(R.id.TeamManager_PlayerManager_Joiner_CustomList_ImageView_SecondProfile);
        SecondName = (TextView) convertView.findViewById(R.id.TeamManager_PlayerManager_Joiner_CustomList_TextView_SecondName);
        ThirdProfile = (ImageView) convertView.findViewById(R.id.TeamManager_PlayerManager_Joiner_CustomList_ImageView_ThirdProfile);
        ThirdName = (TextView) convertView.findViewById(R.id.TeamManager_PlayerManager_Joiner_CustomList_TextView_ThirdName);
        FourthProfile = (ImageView)convertView.findViewById(R.id.TeamManager_PlayerManager_Joiner_CustomList_ImageView_FourthProfile);
        FourthName = (TextView)convertView.findViewById(R.id.TeamManager_PlayerManager_Joiner_CustomList_TextView_FourthName);
        Player1 = (LinearLayout)convertView.findViewById(R.id.TeamManager_PlayerManager_Joiner_CustomList_LinearLayout_Player1);
        Player2 = (LinearLayout)convertView.findViewById(R.id.TeamManager_PlayerManager_Joiner_CustomList_LinearLayout_Player2);
        Player3 = (LinearLayout)convertView.findViewById(R.id.TeamManager_PlayerManager_Joiner_CustomList_LinearLayout_Player3);
        Player4 = (LinearLayout)convertView.findViewById(R.id.TeamManager_PlayerManager_Joiner_CustomList_LinearLayout_Player4);

        if(arrData.get(position).getSecond_Name().equals("null")){
            SecondProfile.setVisibility(View.INVISIBLE);
            SecondName.setVisibility(View.INVISIBLE);
            Player2.setBackgroundColor(convertView.getResources().getColor(R.color.WhiteGray));
        }
        else{
            SecondProfile.setVisibility(View.VISIBLE);
            SecondName.setVisibility(View.VISIBLE);
            Player2.setBackgroundColor(convertView.getResources().getColor(R.color.DarkGray));
        }
        if(arrData.get(position).getThird_Name().equals("null")){
            ThirdProfile.setVisibility(View.INVISIBLE);
            ThirdName.setVisibility(View.INVISIBLE);
            Player3.setBackgroundColor(convertView.getResources().getColor(R.color.WhiteGray));
        }
        else{
            ThirdProfile.setVisibility(View.VISIBLE);
            ThirdName.setVisibility(View.VISIBLE);
            Player3.setBackgroundColor(convertView.getResources().getColor(R.color.DarkGray));
        }
        if(arrData.get(position).getFourth_Name().equals("null")){
            FourthProfile.setVisibility(View.INVISIBLE);
            FourthName.setVisibility(View.INVISIBLE);
            Player4.setBackgroundColor(convertView.getResources().getColor(R.color.WhiteGray));
        }
        else{
            FourthProfile.setVisibility(View.VISIBLE);
            FourthName.setVisibility(View.VISIBLE);
            Player4.setBackgroundColor(convertView.getResources().getColor(R.color.DarkGray));
        }
        //첫번째 프로필이 존재할경우
        if(FirstProfile.getVisibility()==View.VISIBLE){
            try{
                String En_Profile = URLEncoder.encode(arrData.get(position).getFirst_Profile(), "utf-8");
                if(En_Profile.equals("."))
                {
                    Glide.with(context).load(R.drawable.teammanager_player).into(FirstProfile);
                }
                else
                {
                    Glide.with(context).load("http://210.122.7.193:8080/Trophy_img/profile/"+En_Profile+".jpg").bitmapTransform(new RoundedCornersTransformation(Glide.get(context).getBitmapPool(),1,1))
                            .into(FirstProfile);
                }
            }
            catch (UnsupportedEncodingException e){

            }
            FirstName.setText(arrData.get(position).getFirst_Name());
            FirstProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LayoutInflater inflater = (LayoutInflater)view.getContext().getSystemService(context.LAYOUT_INFLATER_SERVICE);
                    final View layout = inflater.inflate(R.layout.layout_customdialog_teaminfo_player_focus, (ViewGroup) view.findViewById(R.id.Layout_CustomDialog_TeamInfo_Player_Focus_Root));
                    final ImageView Layout_CustomDialog_TeamInfo_Player_Focus_Back = (ImageView)layout.findViewById(R.id.Layout_CustomDialog_TeamInfo_Player_Focus_Back);
                    final ImageView Layout_CustomDialog_TeamInfo_Player_Focus_Profile = (ImageView)layout.findViewById(R.id.Layout_CustomDialog_TeamInfo_Player_Focus_Profile);
                    final Button Layout_CustomDialog_TeamInfo_Player_Focus_TeamName = (Button)layout.findViewById(R.id.Layout_CustomDialog_TeamInfo_Player_Focus_TeamName);
                    final Button Layout_CustomDialog_TeamInfo_Player_Focus_Age = (Button)layout.findViewById(R.id.Layout_CustomDialog_TeamInfo_Player_Focus_Age);
                    final Button Layout_CustomDialog_TeamInfo_Player_Focus_Sex = (Button)layout.findViewById(R.id.Layout_CustomDialog_TeamInfo_Player_Focus_Sex);
                    final Button Layout_CustomDialog_TeamInfo_Player_Focus_Address = (Button)layout.findViewById(R.id.Layout_CustomDialog_TeamInfo_Player_Focus_Address);
                    final ImageView Layout_CustomDialog_TeamInfo_Player_Focus_Phone = (ImageView) layout.findViewById(R.id.Layout_CustomDialog_TeamInfo_Player_Focus_Phone);
                    try{
                        String En_Profile = URLEncoder.encode(arrData.get(position).getFirst_Profile(), "utf-8");
                        if(arrData.get(position).getFirst_Profile().equals("."))
                        {
                            Glide.with(context).load(R.drawable.teammanager_player).into(Layout_CustomDialog_TeamInfo_Player_Focus_Profile);
                        }
                        else
                        {
                            Glide.with(context).load("http://210.122.7.193:8080/Trophy_img/profile/"+arrData.get(position).getFirst_Profile()+".jpg").bitmapTransform(new RoundedCornersTransformation(Glide.get(context).getBitmapPool(),1,1))
                                    .into(Layout_CustomDialog_TeamInfo_Player_Focus_Profile);
                        }
                    }
                    catch (UnsupportedEncodingException e)
                    {

                    }
                    HttpClient http_Joiner= new HttpClient();
                    String result = http_Joiner.HttpClient("Trophy_part1","TeamInfo_Player_Focus.jsp",arrData.get(position).getFirst_Pk());
                    parsedData_Player_Focus = jsonParserList_Player_Focus(result);
                    Layout_CustomDialog_TeamInfo_Player_Focus_TeamName.setText(parsedData_Player_Focus[0][0]);
                    Layout_CustomDialog_TeamInfo_Player_Focus_Age.setText(ChangeAge(parsedData_Player_Focus[0][1]));
                    Layout_CustomDialog_TeamInfo_Player_Focus_Sex.setText(parsedData_Player_Focus[0][2]);
                    Layout_CustomDialog_TeamInfo_Player_Focus_Address.setText(parsedData_Player_Focus[0][3]);
                    Layout_CustomDialog_TeamInfo_Player_Focus_Phone.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+parsedData_Player_Focus[0][4]));
                            context.startActivity(intent);
                        }
                    });
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
            });

        }
        if(SecondProfile.getVisibility()==View.VISIBLE){
            try{
                String En_Profile = URLEncoder.encode(arrData.get(position).getSecond_Profile(), "utf-8");
                if(En_Profile.equals("."))
                {
                    Glide.with(context).load(R.drawable.teammanager_player).into(SecondProfile);
                }
                else
                {
                    Glide.with(context).load("http://210.122.7.193:8080/Trophy_img/profile/"+En_Profile+".jpg").bitmapTransform(new RoundedCornersTransformation(Glide.get(context).getBitmapPool(),1,1))
                            .into(SecondProfile);
                }
            }
            catch (UnsupportedEncodingException e){

            }
            SecondName.setText(arrData.get(position).getSecond_Name());
            SecondProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LayoutInflater inflater = (LayoutInflater)view.getContext().getSystemService(context.LAYOUT_INFLATER_SERVICE);
                    final View layout = inflater.inflate(R.layout.layout_customdialog_teaminfo_player_focus, (ViewGroup) view.findViewById(R.id.Layout_CustomDialog_TeamInfo_Player_Focus_Root));
                    final ImageView Layout_CustomDialog_TeamInfo_Player_Focus_Back = (ImageView)layout.findViewById(R.id.Layout_CustomDialog_TeamInfo_Player_Focus_Back);
                    final ImageView Layout_CustomDialog_TeamInfo_Player_Focus_Profile = (ImageView)layout.findViewById(R.id.Layout_CustomDialog_TeamInfo_Player_Focus_Profile);
                    final Button Layout_CustomDialog_TeamInfo_Player_Focus_TeamName = (Button)layout.findViewById(R.id.Layout_CustomDialog_TeamInfo_Player_Focus_TeamName);
                    final Button Layout_CustomDialog_TeamInfo_Player_Focus_Age = (Button)layout.findViewById(R.id.Layout_CustomDialog_TeamInfo_Player_Focus_Age);
                    final Button Layout_CustomDialog_TeamInfo_Player_Focus_Sex = (Button)layout.findViewById(R.id.Layout_CustomDialog_TeamInfo_Player_Focus_Sex);
                    final Button Layout_CustomDialog_TeamInfo_Player_Focus_Address = (Button)layout.findViewById(R.id.Layout_CustomDialog_TeamInfo_Player_Focus_Address);
                    final ImageView Layout_CustomDialog_TeamInfo_Player_Focus_Phone = (ImageView) layout.findViewById(R.id.Layout_CustomDialog_TeamInfo_Player_Focus_Phone);
                    try{
                        String En_Profile = URLEncoder.encode(arrData.get(position).getSecond_Profile(), "utf-8");
                        if(arrData.get(position).getFirst_Profile().equals("."))
                        {
                            Glide.with(context).load(R.drawable.teammanager_player).into(Layout_CustomDialog_TeamInfo_Player_Focus_Profile);
                        }
                        else
                        {
                            Glide.with(context).load("http://210.122.7.193:8080/Trophy_img/profile/"+arrData.get(position).getSecond_Profile()+".jpg").bitmapTransform(new RoundedCornersTransformation(Glide.get(context).getBitmapPool(),1,1))
                                    .into(Layout_CustomDialog_TeamInfo_Player_Focus_Profile);
                        }
                    }
                    catch (UnsupportedEncodingException e)
                    {

                    }
                    HttpClient http_Joiner= new HttpClient();
                    String result = http_Joiner.HttpClient("Trophy_part1","TeamInfo_Player_Focus.jsp",arrData.get(position).getSecond_Pk());
                    parsedData_Player_Focus = jsonParserList_Player_Focus(result);
                    Layout_CustomDialog_TeamInfo_Player_Focus_TeamName.setText(parsedData_Player_Focus[0][0]);
                    Layout_CustomDialog_TeamInfo_Player_Focus_Age.setText(ChangeAge(parsedData_Player_Focus[0][1]));
                    Layout_CustomDialog_TeamInfo_Player_Focus_Sex.setText(parsedData_Player_Focus[0][2]);
                    Layout_CustomDialog_TeamInfo_Player_Focus_Address.setText(parsedData_Player_Focus[0][3]);
                    Layout_CustomDialog_TeamInfo_Player_Focus_Phone.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+parsedData_Player_Focus[0][4]));
                            context.startActivity(intent);
                        }
                    });
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
            });
        }
        if(ThirdProfile.getVisibility()==View.VISIBLE){
            try{
                String En_Profile = URLEncoder.encode(arrData.get(position).getThird_Profile(), "utf-8");
                Log.i("eeeee",En_Profile);
                if(En_Profile.equals("."))
                {
                    //Glide.with(context).load(R.drawable.teammanager_player).into(ThirdProfile);
                }
                else
                {
                    Glide.with(context).load("http://210.122.7.193:8080/Trophy_img/profile/"+En_Profile+".jpg").bitmapTransform(new RoundedCornersTransformation(Glide.get(context).getBitmapPool(),1,1))
                            .into(ThirdProfile);
                }
            }
            catch (UnsupportedEncodingException e){

            }
            ThirdName.setText(arrData.get(position).getThird_Name());
            ThirdProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LayoutInflater inflater = (LayoutInflater)view.getContext().getSystemService(context.LAYOUT_INFLATER_SERVICE);
                    final View layout = inflater.inflate(R.layout.layout_customdialog_teaminfo_player_focus, (ViewGroup) view.findViewById(R.id.Layout_CustomDialog_TeamInfo_Player_Focus_Root));
                    final ImageView Layout_CustomDialog_TeamInfo_Player_Focus_Back = (ImageView)layout.findViewById(R.id.Layout_CustomDialog_TeamInfo_Player_Focus_Back);
                    final ImageView Layout_CustomDialog_TeamInfo_Player_Focus_Profile = (ImageView)layout.findViewById(R.id.Layout_CustomDialog_TeamInfo_Player_Focus_Profile);
                    final Button Layout_CustomDialog_TeamInfo_Player_Focus_TeamName = (Button)layout.findViewById(R.id.Layout_CustomDialog_TeamInfo_Player_Focus_TeamName);
                    final Button Layout_CustomDialog_TeamInfo_Player_Focus_Age = (Button)layout.findViewById(R.id.Layout_CustomDialog_TeamInfo_Player_Focus_Age);
                    final Button Layout_CustomDialog_TeamInfo_Player_Focus_Sex = (Button)layout.findViewById(R.id.Layout_CustomDialog_TeamInfo_Player_Focus_Sex);
                    final Button Layout_CustomDialog_TeamInfo_Player_Focus_Address = (Button)layout.findViewById(R.id.Layout_CustomDialog_TeamInfo_Player_Focus_Address);
                    final ImageView Layout_CustomDialog_TeamInfo_Player_Focus_Phone = (ImageView) layout.findViewById(R.id.Layout_CustomDialog_TeamInfo_Player_Focus_Phone);
                    try{
                        String En_Profile = URLEncoder.encode(arrData.get(position).getThird_Profile(), "utf-8");
                        if(arrData.get(position).getFirst_Profile().equals("."))
                        {
                            Glide.with(context).load(R.drawable.teammanager_player).into(Layout_CustomDialog_TeamInfo_Player_Focus_Profile);
                        }
                        else
                        {
                            Glide.with(context).load("http://210.122.7.193:8080/Trophy_img/profile/"+arrData.get(position).getThird_Profile()+".jpg").bitmapTransform(new RoundedCornersTransformation(Glide.get(context).getBitmapPool(),1,1))
                                    .into(Layout_CustomDialog_TeamInfo_Player_Focus_Profile);
                        }
                    }
                    catch (UnsupportedEncodingException e)
                    {

                    }
                    HttpClient http_Joiner= new HttpClient();
                    String result = http_Joiner.HttpClient("Trophy_part1","TeamInfo_Player_Focus.jsp",arrData.get(position).getThird_Pk());
                    parsedData_Player_Focus = jsonParserList_Player_Focus(result);
                    Layout_CustomDialog_TeamInfo_Player_Focus_TeamName.setText(parsedData_Player_Focus[0][0]);
                    Layout_CustomDialog_TeamInfo_Player_Focus_Age.setText(ChangeAge(parsedData_Player_Focus[0][1]));
                    Layout_CustomDialog_TeamInfo_Player_Focus_Sex.setText(parsedData_Player_Focus[0][2]);
                    Layout_CustomDialog_TeamInfo_Player_Focus_Address.setText(parsedData_Player_Focus[0][3]);
                    Layout_CustomDialog_TeamInfo_Player_Focus_Phone.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+parsedData_Player_Focus[0][4]));
                            context.startActivity(intent);
                        }
                    });
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
            });
        }
        if(FourthProfile.getVisibility()==View.VISIBLE){
            try{
                String En_Profile = URLEncoder.encode(arrData.get(position).getFourth_Profile(), "utf-8");
                Log.i("eeeee",En_Profile);
                if(En_Profile.equals("."))
                {
                    //Glide.with(context).load(R.drawable.teammanager_player).into(ThirdProfile);
                }
                else
                {
                    Glide.with(context).load("http://210.122.7.193:8080/Trophy_img/profile/"+En_Profile+".jpg").bitmapTransform(new RoundedCornersTransformation(Glide.get(context).getBitmapPool(),1,1))
                            .into(FourthProfile);
                }
            }
            catch (UnsupportedEncodingException e){

            }
            FourthName.setText(arrData.get(position).getFourth_Name());
            FourthProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LayoutInflater inflater = (LayoutInflater)view.getContext().getSystemService(context.LAYOUT_INFLATER_SERVICE);
                    final View layout = inflater.inflate(R.layout.layout_customdialog_teaminfo_player_focus, (ViewGroup) view.findViewById(R.id.Layout_CustomDialog_TeamInfo_Player_Focus_Root));
                    final ImageView Layout_CustomDialog_TeamInfo_Player_Focus_Back = (ImageView)layout.findViewById(R.id.Layout_CustomDialog_TeamInfo_Player_Focus_Back);
                    final ImageView Layout_CustomDialog_TeamInfo_Player_Focus_Profile = (ImageView)layout.findViewById(R.id.Layout_CustomDialog_TeamInfo_Player_Focus_Profile);
                    final Button Layout_CustomDialog_TeamInfo_Player_Focus_TeamName = (Button)layout.findViewById(R.id.Layout_CustomDialog_TeamInfo_Player_Focus_TeamName);
                    final Button Layout_CustomDialog_TeamInfo_Player_Focus_Age = (Button)layout.findViewById(R.id.Layout_CustomDialog_TeamInfo_Player_Focus_Age);
                    final Button Layout_CustomDialog_TeamInfo_Player_Focus_Sex = (Button)layout.findViewById(R.id.Layout_CustomDialog_TeamInfo_Player_Focus_Sex);
                    final Button Layout_CustomDialog_TeamInfo_Player_Focus_Address = (Button)layout.findViewById(R.id.Layout_CustomDialog_TeamInfo_Player_Focus_Address);
                    final ImageView Layout_CustomDialog_TeamInfo_Player_Focus_Phone = (ImageView) layout.findViewById(R.id.Layout_CustomDialog_TeamInfo_Player_Focus_Phone);
                    try{
                        String En_Profile = URLEncoder.encode(arrData.get(position).getFourth_Profile(), "utf-8");
                        if(arrData.get(position).getFirst_Profile().equals("."))
                        {
                            Glide.with(context).load(R.drawable.teammanager_player).into(Layout_CustomDialog_TeamInfo_Player_Focus_Profile);
                        }
                        else
                        {
                            Glide.with(context).load("http://210.122.7.193:8080/Trophy_img/profile/"+arrData.get(position).getFourth_Profile()+".jpg").bitmapTransform(new RoundedCornersTransformation(Glide.get(context).getBitmapPool(),1,1))
                                    .into(Layout_CustomDialog_TeamInfo_Player_Focus_Profile);
                        }
                    }
                    catch (UnsupportedEncodingException e)
                    {

                    }
                    HttpClient http_Joiner= new HttpClient();
                    String result = http_Joiner.HttpClient("Trophy_part1","TeamInfo_Player_Focus.jsp",arrData.get(position).getFourth_Pk());
                    parsedData_Player_Focus = jsonParserList_Player_Focus(result);
                    Layout_CustomDialog_TeamInfo_Player_Focus_TeamName.setText(parsedData_Player_Focus[0][0]);
                    Layout_CustomDialog_TeamInfo_Player_Focus_Age.setText(ChangeAge(parsedData_Player_Focus[0][1]));
                    Layout_CustomDialog_TeamInfo_Player_Focus_Sex.setText(parsedData_Player_Focus[0][2]);
                    Layout_CustomDialog_TeamInfo_Player_Focus_Address.setText(parsedData_Player_Focus[0][3]);
                    Layout_CustomDialog_TeamInfo_Player_Focus_Phone.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+parsedData_Player_Focus[0][4]));
                            context.startActivity(intent);
                        }
                    });
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
            });
        }
        return convertView;
    }
    public String[][] jsonParserList_Player_Focus(String pRecvServerPage) {
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
    public String[][] jsonParserList_Player_Out(String pRecvServerPage) {
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
    public String[][] jsonParserList_ChangeRepresent(String pRecvServerPage) {
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
    public String[][] jsonParserList_Joiner_Refuse(String pRecvServerPage) {
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
