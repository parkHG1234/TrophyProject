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

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import me.drakeet.materialdialog.MaterialDialog;
import trophy.projetc2.Http.HttpClient;
import trophy.projetc2.R;

import static trophy.projetc2.Navigation.TeamManager.TeamManager_TeamName;
import static trophy.projetc2.Navigation.TeamManager.TeamManager_Team_Pk;
import static trophy.projetc2.Navigation.TeamManager_PlayerManager.refresh_joiner;
import static trophy.projetc2.Navigation.TeamManager_PlayerManager.refresh_player;

/**
 * Created by 박효근 on 2017-01-14.
 */

public class TeamManager_PlayerManager_Customlist_MyAdapter_Joiner extends BaseAdapter {
    private Context context;
    private ArrayList<TeamManager_PlayerManager_Customlist_MyData_Joiner> arrData;
    private LayoutInflater inflater;
    ImageView FirstProfile, SecondProfile, ThirdProfile, FourthProfile;
    TextView FirstName, SecondName, ThirdName, FourthName;
    LinearLayout Player1, Player2, Player3, Player4;
    String[][] parsedData_Player_Focus,parsedData_Joiner_Refuse,parsedData_Joiner_Allow;
    String ProfileUrl;
    public TeamManager_PlayerManager_Customlist_MyAdapter_Joiner(Context c, ArrayList<TeamManager_PlayerManager_Customlist_MyData_Joiner> arr) {
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
            FirstName.setText(arrData.get(position).getFirst_Name());
            FirstProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LayoutInflater inflater = (LayoutInflater)view.getContext().getSystemService(context.LAYOUT_INFLATER_SERVICE);
                    final View layout = inflater.inflate(R.layout.layout_customdialog_teammanager_playermanager_focus, (ViewGroup) view.findViewById(R.id.Layout_CustomDialog_TeamManager_PlayerManager_Focus_Root));
                    final ImageView Layout_CustomDialog_TeamManager_PlayerManager_Focus_ImageView_Back = (ImageView)layout.findViewById(R.id.Layout_CustomDialog_TeamManager_PlayerManager_Focus_ImageView_Back);
                    final ImageView Layout_CustomDialog_TeamManager_PlayerManager_Focus_ImageView_Profile = (ImageView)layout.findViewById(R.id.Layout_CustomDialog_TeamManager_PlayerManager_Focus_ImageView_Profile);
                    final Button Layout_CustomDialog_TeamManager_PlayerManager_Focus_Button_TeamName = (Button)layout.findViewById(R.id.Layout_CustomDialog_TeamManager_PlayerManager_Focus_Button_TeamName);
                    final Button Layout_CustomDialog_TeamManager_PlayerManager_Focus_Button_Age = (Button)layout.findViewById(R.id.Layout_CustomDialog_TeamManager_PlayerManager_Focus_Button_Age);
                    final Button Layout_CustomDialog_TeamManager_PlayerManager_Focus_Button_Sex = (Button)layout.findViewById(R.id.Layout_CustomDialog_TeamManager_PlayerManager_Focus_Button_Sex);
                    final Button Layout_CustomDialog_TeamManager_PlayerManager_Focus_Button_Address = (Button)layout.findViewById(R.id.Layout_CustomDialog_TeamManager_PlayerManager_Focus_Button_Address);
                    final ImageView Layout_CustomDialog_TeamManager_PlayerManager_Focus_ImageView_Phone = (ImageView) layout.findViewById(R.id.Layout_CustomDialog_TeamManager_PlayerManager_Focus_ImageView_Phone);
                    final LinearLayout Layout_CustomDialog_TeamManager_PlayerManager_Focus_LinearLayout_Duty = (LinearLayout)layout.findViewById(R.id.Layout_CustomDialog_TeamManager_PlayerManager_Focus_LinearLayout_Duty);
                    Layout_CustomDialog_TeamManager_PlayerManager_Focus_LinearLayout_Duty.setVisibility(View.GONE);
                    final Button Layout_CustomDialog_TeamManager_PlayerManager_Focus_LinearLayout_Joiner_Agree = (Button)layout.findViewById(R.id.Layout_CustomDialog_TeamManager_PlayerManager_Focus_LinearLayout_Joiner_Agree);
                    final Button Layout_CustomDialog_TeamManager_PlayerManager_Focus_LinearLayout_Joiner_DisAgree = (Button)layout.findViewById(R.id.Layout_CustomDialog_TeamManager_PlayerManager_Focus_LinearLayout_Joiner_DisAgree);
                    try{
                        String En_Profile = URLEncoder.encode(arrData.get(position).getFirst_Profile(), "utf-8");
                        if(arrData.get(position).getFirst_Profile().equals("."))
                        {
                            Glide.with(context).load(R.drawable.teammanager_player).into(Layout_CustomDialog_TeamManager_PlayerManager_Focus_ImageView_Profile);
                        }
                        else
                        {
                            Glide.with(context).load("http://210.122.7.193:8080/Web_basket/imgs/Profile/" + En_Profile + ".jpg").bitmapTransform(new CropCircleTransformation(Glide.get(context).getBitmapPool()))
                                    .into(Layout_CustomDialog_TeamManager_PlayerManager_Focus_ImageView_Profile);
                        }
                    }
                    catch (UnsupportedEncodingException e){

                    }

                    HttpClient http_Joiner= new HttpClient();
                    String result = http_Joiner.HttpClient("Trophy_part1","TeamManager_Player_Focus.jsp",arrData.get(position).getFirst_Pk());
                    parsedData_Player_Focus = jsonParserList_Player_Focus(result);
                    Layout_CustomDialog_TeamManager_PlayerManager_Focus_Button_TeamName.setText(parsedData_Player_Focus[0][0]);
                    Layout_CustomDialog_TeamManager_PlayerManager_Focus_Button_Age.setText(ChangeAge(parsedData_Player_Focus[0][1]));
                    Layout_CustomDialog_TeamManager_PlayerManager_Focus_Button_Sex.setText(parsedData_Player_Focus[0][2]);
                    Layout_CustomDialog_TeamManager_PlayerManager_Focus_Button_Address.setText(parsedData_Player_Focus[0][3]);
                    //Layout_CustomDialog_TeamManager_PlayerManager_Focus_Button_Phone.setText(parsedData_Player_Focus[0][4]);
                    Layout_CustomDialog_TeamManager_PlayerManager_Focus_ImageView_Phone.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:01073225945"));
                            context.startActivity(intent);
                        }
                    });
                    final MaterialDialog TeamPlayerDialog = new MaterialDialog(context);
                    TeamPlayerDialog
                            .setTitle("신청자 정보")
                            .setView(layout)
                            .setCanceledOnTouchOutside(true);
                    TeamPlayerDialog.show();
                    Layout_CustomDialog_TeamManager_PlayerManager_Focus_ImageView_Back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            TeamPlayerDialog.dismiss();
                        }
                    });
                    Layout_CustomDialog_TeamManager_PlayerManager_Focus_LinearLayout_Joiner_Agree.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            HttpClient http_Joiner_Allow= new HttpClient();
                            String result = http_Joiner_Allow.HttpClient("Trophy_part1","TeamManager_Joiner_Allow.jsp",arrData.get(position).getFirst_Pk(),TeamManager_Team_Pk);
                            parsedData_Joiner_Allow = jsonParserList_Joiner_Allow(result);
                            if(parsedData_Joiner_Allow[0][0].equals("succed")){
                                TeamPlayerDialog.dismiss();
                                refresh_player= "refresh";
                                refresh_joiner= "refresh";
                            }
                            else{
                                Snackbar.make(view,"잠시 후 다시 시도해주시기 바랍니다.",Snackbar.LENGTH_SHORT).show();
                            }
                        }
                    });
                    Layout_CustomDialog_TeamManager_PlayerManager_Focus_LinearLayout_Joiner_DisAgree.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            HttpClient http_Joiner_Refuse= new HttpClient();
                            String result = http_Joiner_Refuse.HttpClient("Trophy_part1","TeamManager_Joiner_Refuse.jsp",arrData.get(position).getFirst_Pk(),TeamManager_Team_Pk);
                            parsedData_Joiner_Refuse = jsonParserList_Joiner_Refuse(result);
                            if(parsedData_Joiner_Refuse[0][0].equals("succed")){
                                TeamPlayerDialog.dismiss();
                                refresh_joiner= "refresh";
                            }
                            else{
                                Snackbar.make(view,"잠시 후 다시 시도해주시기 바랍니다.",Snackbar.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });
        }
        if(SecondProfile.getVisibility()==View.VISIBLE){
            SecondName.setText(arrData.get(position).getSecond_Name());
            SecondProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LayoutInflater inflater = (LayoutInflater)view.getContext().getSystemService(context.LAYOUT_INFLATER_SERVICE);
                    final View layout = inflater.inflate(R.layout.layout_customdialog_teammanager_playermanager_focus, (ViewGroup) view.findViewById(R.id.Layout_CustomDialog_TeamManager_PlayerManager_Focus_Root));
                    final ImageView Layout_CustomDialog_TeamManager_PlayerManager_Focus_ImageView_Back = (ImageView)layout.findViewById(R.id.Layout_CustomDialog_TeamManager_PlayerManager_Focus_ImageView_Back);
                    final ImageView Layout_CustomDialog_TeamManager_PlayerManager_Focus_ImageView_Profile = (ImageView)layout.findViewById(R.id.Layout_CustomDialog_TeamManager_PlayerManager_Focus_ImageView_Profile);
                    final Button Layout_CustomDialog_TeamManager_PlayerManager_Focus_Button_TeamName = (Button)layout.findViewById(R.id.Layout_CustomDialog_TeamManager_PlayerManager_Focus_Button_TeamName);
                    final Button Layout_CustomDialog_TeamManager_PlayerManager_Focus_Button_Age = (Button)layout.findViewById(R.id.Layout_CustomDialog_TeamManager_PlayerManager_Focus_Button_Age);
                    final Button Layout_CustomDialog_TeamManager_PlayerManager_Focus_Button_Sex = (Button)layout.findViewById(R.id.Layout_CustomDialog_TeamManager_PlayerManager_Focus_Button_Sex);
                    final Button Layout_CustomDialog_TeamManager_PlayerManager_Focus_Button_Address = (Button)layout.findViewById(R.id.Layout_CustomDialog_TeamManager_PlayerManager_Focus_Button_Address);
                    final ImageView Layout_CustomDialog_TeamManager_PlayerManager_Focus_ImageView_Phone = (ImageView) layout.findViewById(R.id.Layout_CustomDialog_TeamManager_PlayerManager_Focus_ImageView_Phone);
                    final LinearLayout Layout_CustomDialog_TeamManager_PlayerManager_Focus_LinearLayout_Duty = (LinearLayout)layout.findViewById(R.id.Layout_CustomDialog_TeamManager_PlayerManager_Focus_LinearLayout_Duty);
                    Layout_CustomDialog_TeamManager_PlayerManager_Focus_LinearLayout_Duty.setVisibility(View.GONE);
                    final Button Layout_CustomDialog_TeamManager_PlayerManager_Focus_LinearLayout_Joiner_Agree = (Button)layout.findViewById(R.id.Layout_CustomDialog_TeamManager_PlayerManager_Focus_LinearLayout_Joiner_Agree);
                    final Button Layout_CustomDialog_TeamManager_PlayerManager_Focus_LinearLayout_Joiner_DisAgree = (Button)layout.findViewById(R.id.Layout_CustomDialog_TeamManager_PlayerManager_Focus_LinearLayout_Joiner_DisAgree);
                    try{
                        String En_Profile = URLEncoder.encode(arrData.get(position).getSecond_Profile(), "utf-8");
                        if(arrData.get(position).getSecond_Profile().equals("."))
                        {
                            Glide.with(context).load(R.drawable.teammanager_player).into(Layout_CustomDialog_TeamManager_PlayerManager_Focus_ImageView_Profile);
                        }
                        else
                        {
                            Glide.with(context).load("http://210.122.7.193:8080/Web_basket/imgs/Profile/" + En_Profile + ".jpg").bitmapTransform(new CropCircleTransformation(Glide.get(context).getBitmapPool()))
                                    .into(Layout_CustomDialog_TeamManager_PlayerManager_Focus_ImageView_Profile);
                        }
                    }
                    catch (UnsupportedEncodingException e){

                    }

                    HttpClient http_Joiner= new HttpClient();
                    String result = http_Joiner.HttpClient("Trophy_part1","TeamManager_Player_Focus.jsp",arrData.get(position).getSecond_Pk());
                    parsedData_Player_Focus = jsonParserList_Player_Focus(result);
                    Layout_CustomDialog_TeamManager_PlayerManager_Focus_Button_TeamName.setText(parsedData_Player_Focus[0][0]);
                    Layout_CustomDialog_TeamManager_PlayerManager_Focus_Button_Age.setText(ChangeAge(parsedData_Player_Focus[0][1]));
                    Layout_CustomDialog_TeamManager_PlayerManager_Focus_Button_Sex.setText(parsedData_Player_Focus[0][2]);
                    Layout_CustomDialog_TeamManager_PlayerManager_Focus_Button_Address.setText(parsedData_Player_Focus[0][3]);
                    //Layout_CustomDialog_TeamManager_PlayerManager_Focus_Button_Phone.setText(parsedData_Player_Focus[0][4]);
                    Layout_CustomDialog_TeamManager_PlayerManager_Focus_ImageView_Phone.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:01073225945"));
                            context.startActivity(intent);
                        }
                    });
                    final MaterialDialog TeamPlayerDialog = new MaterialDialog(context);
                    TeamPlayerDialog
                            .setTitle("신청자 정보")
                            .setView(layout)
                            .setCanceledOnTouchOutside(true);
                    TeamPlayerDialog.show();
                    Layout_CustomDialog_TeamManager_PlayerManager_Focus_ImageView_Back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            TeamPlayerDialog.dismiss();
                        }
                    });
                    Layout_CustomDialog_TeamManager_PlayerManager_Focus_LinearLayout_Joiner_Agree.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            HttpClient http_Joiner_Allow= new HttpClient();
                            String result = http_Joiner_Allow.HttpClient("Trophy_part1","TeamManager_Joiner_Allow.jsp",arrData.get(position).getSecond_Pk(),TeamManager_Team_Pk);
                            parsedData_Joiner_Allow = jsonParserList_Joiner_Allow(result);
                            if(parsedData_Joiner_Allow[0][0].equals("succed")){
                                TeamPlayerDialog.dismiss();
                                refresh_player= "refresh";
                                refresh_joiner= "refresh";
                            }
                            else{
                                Snackbar.make(view,"잠시 후 다시 시도해주시기 바랍니다.",Snackbar.LENGTH_SHORT).show();
                            }
                        }
                    });
                    Layout_CustomDialog_TeamManager_PlayerManager_Focus_LinearLayout_Joiner_DisAgree.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            HttpClient http_Joiner_Refuse= new HttpClient();
                            String result = http_Joiner_Refuse.HttpClient("Trophy_part1","TeamManager_Joiner_Refuse.jsp",arrData.get(position).getSecond_Pk(),TeamManager_Team_Pk);
                            parsedData_Joiner_Refuse = jsonParserList_Joiner_Refuse(result);
                            if(parsedData_Joiner_Refuse[0][0].equals("succed")){
                                TeamPlayerDialog.dismiss();
                                refresh_joiner= "refresh";
                            }
                            else{
                                Snackbar.make(view,"잠시 후 다시 시도해주시기 바랍니다.",Snackbar.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });
        }
        if(ThirdProfile.getVisibility()==View.VISIBLE){
            ThirdName.setText(arrData.get(position).getThird_Name());
            ThirdProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LayoutInflater inflater = (LayoutInflater)view.getContext().getSystemService(context.LAYOUT_INFLATER_SERVICE);
                    final View layout = inflater.inflate(R.layout.layout_customdialog_teammanager_playermanager_focus, (ViewGroup) view.findViewById(R.id.Layout_CustomDialog_TeamManager_PlayerManager_Focus_Root));
                    final ImageView Layout_CustomDialog_TeamManager_PlayerManager_Focus_ImageView_Back = (ImageView)layout.findViewById(R.id.Layout_CustomDialog_TeamManager_PlayerManager_Focus_ImageView_Back);
                    final ImageView Layout_CustomDialog_TeamManager_PlayerManager_Focus_ImageView_Profile = (ImageView)layout.findViewById(R.id.Layout_CustomDialog_TeamManager_PlayerManager_Focus_ImageView_Profile);
                    final Button Layout_CustomDialog_TeamManager_PlayerManager_Focus_Button_TeamName = (Button)layout.findViewById(R.id.Layout_CustomDialog_TeamManager_PlayerManager_Focus_Button_TeamName);
                    final Button Layout_CustomDialog_TeamManager_PlayerManager_Focus_Button_Age = (Button)layout.findViewById(R.id.Layout_CustomDialog_TeamManager_PlayerManager_Focus_Button_Age);
                    final Button Layout_CustomDialog_TeamManager_PlayerManager_Focus_Button_Sex = (Button)layout.findViewById(R.id.Layout_CustomDialog_TeamManager_PlayerManager_Focus_Button_Sex);
                    final Button Layout_CustomDialog_TeamManager_PlayerManager_Focus_Button_Address = (Button)layout.findViewById(R.id.Layout_CustomDialog_TeamManager_PlayerManager_Focus_Button_Address);
                    final ImageView Layout_CustomDialog_TeamManager_PlayerManager_Focus_ImageView_Phone = (ImageView) layout.findViewById(R.id.Layout_CustomDialog_TeamManager_PlayerManager_Focus_ImageView_Phone);
                    final LinearLayout Layout_CustomDialog_TeamManager_PlayerManager_Focus_LinearLayout_Duty = (LinearLayout)layout.findViewById(R.id.Layout_CustomDialog_TeamManager_PlayerManager_Focus_LinearLayout_Duty);
                    Layout_CustomDialog_TeamManager_PlayerManager_Focus_LinearLayout_Duty.setVisibility(View.GONE);
                    final Button Layout_CustomDialog_TeamManager_PlayerManager_Focus_LinearLayout_Joiner_Agree = (Button)layout.findViewById(R.id.Layout_CustomDialog_TeamManager_PlayerManager_Focus_LinearLayout_Joiner_Agree);
                    final Button Layout_CustomDialog_TeamManager_PlayerManager_Focus_LinearLayout_Joiner_DisAgree = (Button)layout.findViewById(R.id.Layout_CustomDialog_TeamManager_PlayerManager_Focus_LinearLayout_Joiner_DisAgree);
                    try{
                        String En_Profile = URLEncoder.encode(arrData.get(position).getThird_Profile(), "utf-8");
                        if(arrData.get(position).getThird_Profile().equals("."))
                        {
                            Glide.with(context).load(R.drawable.teammanager_player).into(Layout_CustomDialog_TeamManager_PlayerManager_Focus_ImageView_Profile);
                        }
                        else
                        {
                            Glide.with(context).load("http://210.122.7.193:8080/Web_basket/imgs/Profile/" + En_Profile + ".jpg").bitmapTransform(new CropCircleTransformation(Glide.get(context).getBitmapPool()))
                                    .into(Layout_CustomDialog_TeamManager_PlayerManager_Focus_ImageView_Profile);
                        }
                    }
                    catch (UnsupportedEncodingException e){

                    }

                    HttpClient http_Joiner= new HttpClient();
                    String result = http_Joiner.HttpClient("Trophy_part1","TeamManager_Player_Focus.jsp",arrData.get(position).getThird_Pk());
                    parsedData_Player_Focus = jsonParserList_Player_Focus(result);
                    Layout_CustomDialog_TeamManager_PlayerManager_Focus_Button_TeamName.setText(parsedData_Player_Focus[0][0]);
                    Layout_CustomDialog_TeamManager_PlayerManager_Focus_Button_Age.setText(ChangeAge(parsedData_Player_Focus[0][1]));
                    Layout_CustomDialog_TeamManager_PlayerManager_Focus_Button_Sex.setText(parsedData_Player_Focus[0][2]);
                    Layout_CustomDialog_TeamManager_PlayerManager_Focus_Button_Address.setText(parsedData_Player_Focus[0][3]);
                    //Layout_CustomDialog_TeamManager_PlayerManager_Focus_Button_Phone.setText(parsedData_Player_Focus[0][4]);
                    Layout_CustomDialog_TeamManager_PlayerManager_Focus_ImageView_Phone.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:01073225945"));
                            context.startActivity(intent);
                        }
                    });
                    final MaterialDialog TeamPlayerDialog = new MaterialDialog(context);
                    TeamPlayerDialog
                            .setTitle("신청자 정보")
                            .setView(layout)
                            .setCanceledOnTouchOutside(true);
                    TeamPlayerDialog.show();
                    Layout_CustomDialog_TeamManager_PlayerManager_Focus_ImageView_Back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            TeamPlayerDialog.dismiss();
                        }
                    });
                    Layout_CustomDialog_TeamManager_PlayerManager_Focus_LinearLayout_Joiner_Agree.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            HttpClient http_Joiner_Allow= new HttpClient();
                            String result = http_Joiner_Allow.HttpClient("Trophy_part1","TeamManager_Joiner_Allow.jsp",arrData.get(position).getThird_Pk(),TeamManager_Team_Pk);
                            parsedData_Joiner_Allow = jsonParserList_Joiner_Allow(result);
                            if(parsedData_Joiner_Allow[0][0].equals("succed")){
                                TeamPlayerDialog.dismiss();
                                refresh_player= "refresh";
                                refresh_joiner= "refresh";
                            }
                            else{
                                Snackbar.make(view,"잠시 후 다시 시도해주시기 바랍니다.",Snackbar.LENGTH_SHORT).show();
                            }
                        }
                    });
                    Layout_CustomDialog_TeamManager_PlayerManager_Focus_LinearLayout_Joiner_DisAgree.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            HttpClient http_Joiner_Refuse= new HttpClient();
                            String result = http_Joiner_Refuse.HttpClient("Trophy_part1","TeamManager_Joiner_Refuse.jsp",arrData.get(position).getThird_Pk(),TeamManager_Team_Pk);
                            parsedData_Joiner_Refuse = jsonParserList_Joiner_Refuse(result);
                            if(parsedData_Joiner_Refuse[0][0].equals("succed")){
                                TeamPlayerDialog.dismiss();
                                refresh_joiner= "refresh";
                            }
                            else{
                                Snackbar.make(view,"잠시 후 다시 시도해주시기 바랍니다.",Snackbar.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });
        }
        if(FourthProfile.getVisibility()==View.VISIBLE){
            FourthName.setText(arrData.get(position).getFourth_Name());
            FourthProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LayoutInflater inflater = (LayoutInflater)view.getContext().getSystemService(context.LAYOUT_INFLATER_SERVICE);
                    final View layout = inflater.inflate(R.layout.layout_customdialog_teammanager_playermanager_focus, (ViewGroup) view.findViewById(R.id.Layout_CustomDialog_TeamManager_PlayerManager_Focus_Root));
                    final ImageView Layout_CustomDialog_TeamManager_PlayerManager_Focus_ImageView_Back = (ImageView)layout.findViewById(R.id.Layout_CustomDialog_TeamManager_PlayerManager_Focus_ImageView_Back);
                    final ImageView Layout_CustomDialog_TeamManager_PlayerManager_Focus_ImageView_Profile = (ImageView)layout.findViewById(R.id.Layout_CustomDialog_TeamManager_PlayerManager_Focus_ImageView_Profile);
                    final Button Layout_CustomDialog_TeamManager_PlayerManager_Focus_Button_TeamName = (Button)layout.findViewById(R.id.Layout_CustomDialog_TeamManager_PlayerManager_Focus_Button_TeamName);
                    final Button Layout_CustomDialog_TeamManager_PlayerManager_Focus_Button_Age = (Button)layout.findViewById(R.id.Layout_CustomDialog_TeamManager_PlayerManager_Focus_Button_Age);
                    final Button Layout_CustomDialog_TeamManager_PlayerManager_Focus_Button_Sex = (Button)layout.findViewById(R.id.Layout_CustomDialog_TeamManager_PlayerManager_Focus_Button_Sex);
                    final Button Layout_CustomDialog_TeamManager_PlayerManager_Focus_Button_Address = (Button)layout.findViewById(R.id.Layout_CustomDialog_TeamManager_PlayerManager_Focus_Button_Address);
                    final ImageView Layout_CustomDialog_TeamManager_PlayerManager_Focus_ImageView_Phone = (ImageView) layout.findViewById(R.id.Layout_CustomDialog_TeamManager_PlayerManager_Focus_ImageView_Phone);
                    final LinearLayout Layout_CustomDialog_TeamManager_PlayerManager_Focus_LinearLayout_Duty = (LinearLayout)layout.findViewById(R.id.Layout_CustomDialog_TeamManager_PlayerManager_Focus_LinearLayout_Duty);
                    Layout_CustomDialog_TeamManager_PlayerManager_Focus_LinearLayout_Duty.setVisibility(View.GONE);
                    final Button Layout_CustomDialog_TeamManager_PlayerManager_Focus_LinearLayout_Joiner_Agree = (Button)layout.findViewById(R.id.Layout_CustomDialog_TeamManager_PlayerManager_Focus_LinearLayout_Joiner_Agree);
                    final Button Layout_CustomDialog_TeamManager_PlayerManager_Focus_LinearLayout_Joiner_DisAgree = (Button)layout.findViewById(R.id.Layout_CustomDialog_TeamManager_PlayerManager_Focus_LinearLayout_Joiner_DisAgree);
                    try{
                        String En_Profile = URLEncoder.encode(arrData.get(position).getFourth_Profile(), "utf-8");
                        if(arrData.get(position).getFourth_Profile().equals("."))
                        {
                            Glide.with(context).load(R.drawable.teammanager_player).into(Layout_CustomDialog_TeamManager_PlayerManager_Focus_ImageView_Profile);
                        }
                        else
                        {
                            Glide.with(context).load("http://210.122.7.193:8080/Web_basket/imgs/Profile/" + En_Profile + ".jpg").bitmapTransform(new CropCircleTransformation(Glide.get(context).getBitmapPool()))
                                    .into(Layout_CustomDialog_TeamManager_PlayerManager_Focus_ImageView_Profile);
                        }
                    }
                    catch (UnsupportedEncodingException e){

                    }

                    HttpClient http_Joiner= new HttpClient();
                    String result = http_Joiner.HttpClient("Trophy_part1","TeamManager_Player_Focus.jsp",arrData.get(position).getFourth_Pk());
                    parsedData_Player_Focus = jsonParserList_Player_Focus(result);
                    Layout_CustomDialog_TeamManager_PlayerManager_Focus_Button_TeamName.setText(parsedData_Player_Focus[0][0]);
                    Layout_CustomDialog_TeamManager_PlayerManager_Focus_Button_Age.setText(ChangeAge(parsedData_Player_Focus[0][1]));
                    Layout_CustomDialog_TeamManager_PlayerManager_Focus_Button_Sex.setText(parsedData_Player_Focus[0][2]);
                    Layout_CustomDialog_TeamManager_PlayerManager_Focus_Button_Address.setText(parsedData_Player_Focus[0][3]);
                    //Layout_CustomDialog_TeamManager_PlayerManager_Focus_Button_Phone.setText(parsedData_Player_Focus[0][4]);
                    Layout_CustomDialog_TeamManager_PlayerManager_Focus_ImageView_Phone.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:01073225945"));
                            context.startActivity(intent);
                        }
                    });
                    final MaterialDialog TeamPlayerDialog = new MaterialDialog(context);
                    TeamPlayerDialog
                            .setTitle("신청자 정보")
                            .setView(layout)
                            .setCanceledOnTouchOutside(true);
                    TeamPlayerDialog.show();
                    Layout_CustomDialog_TeamManager_PlayerManager_Focus_ImageView_Back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            TeamPlayerDialog.dismiss();
                        }
                    });
                    Layout_CustomDialog_TeamManager_PlayerManager_Focus_LinearLayout_Joiner_Agree.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            HttpClient http_Joiner_Allow= new HttpClient();
                            String result = http_Joiner_Allow.HttpClient("Trophy_part1","TeamManager_Joiner_Allow.jsp",arrData.get(position).getFourth_Pk(),TeamManager_Team_Pk);
                            parsedData_Joiner_Allow = jsonParserList_Joiner_Allow(result);
                            if(parsedData_Joiner_Allow[0][0].equals("succed")){
                                TeamPlayerDialog.dismiss();
                                refresh_player= "refresh";
                                refresh_joiner= "refresh";
                            }
                            else{
                                Snackbar.make(view,"잠시 후 다시 시도해주시기 바랍니다.",Snackbar.LENGTH_SHORT).show();
                            }
                        }
                    });
                    Layout_CustomDialog_TeamManager_PlayerManager_Focus_LinearLayout_Joiner_DisAgree.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            HttpClient http_Joiner_Refuse= new HttpClient();
                            String result = http_Joiner_Refuse.HttpClient("Trophy_part1","TeamManager_Joiner_Refuse.jsp",arrData.get(position).getFourth_Pk(),TeamManager_Team_Pk);
                            parsedData_Joiner_Refuse = jsonParserList_Joiner_Refuse(result);
                            if(parsedData_Joiner_Refuse[0][0].equals("succed")){
                                TeamPlayerDialog.dismiss();
                                refresh_joiner= "refresh";
                            }
                            else{
                                Snackbar.make(view,"잠시 후 다시 시도해주시기 바랍니다.",Snackbar.LENGTH_SHORT).show();
                            }
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
    public String[][] jsonParserList_Joiner_Allow(String pRecvServerPage) {
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
