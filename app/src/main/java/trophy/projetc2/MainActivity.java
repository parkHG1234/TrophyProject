package trophy.projetc2;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.iid.FirebaseInstanceId;
import com.matthewtamlin.sliding_intro_screen_library.DotIndicator;
import com.tsengvn.typekit.TypekitContextWrapper;

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
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import me.drakeet.materialdialog.MaterialDialog;
import trophy.projetc2.Contest.Contest_Text_Customlist_Adapter;
import trophy.projetc2.Contest.Contest_Text_Customlist_MyData;
import trophy.projetc2.Contest.Contests_Customlist_Adapter;
import trophy.projetc2.Contest.Contests_Customlist_MyData;
import trophy.projetc2.Http.HttpClient;
import trophy.projetc2.Main.MainActivity_VierPage1;
import trophy.projetc2.Main.MainActivity_VierPage2;
import trophy.projetc2.Main.MainActivity_VierPage3;
import trophy.projetc2.Contest.Navigation_Contest;
import trophy.projetc2.Match.Match_MyAdapter;
import trophy.projetc2.Match.Match_MyData;
import trophy.projetc2.Match.Match;
import trophy.projetc2.Navigation.Notice;
import trophy.projetc2.Score.Score;
import trophy.projetc2.OutCourt.OutCourt_Address;
import trophy.projetc2.Navigation.Suggest;
import trophy.projetc2.Navigation.TeamInfo;
import trophy.projetc2.Navigation.TeamMake;
import trophy.projetc2.Navigation.TeamRanking;
import trophy.projetc2.Navigation.TeamSearch;
import trophy.projetc2.Navigation.TeamSearch_Focus;
import trophy.projetc2.OutCourt.OutCourt_CourtInfo_Main;
import trophy.projetc2.OutCourt.OutCourt_CourtInfo_MyAdapter;
import trophy.projetc2.OutCourt.OutCourt_CourtInfo_MyData;
import trophy.projetc2.Score.Score_Focus;
import trophy.projetc2.User.ChangePersonalInfoActivity;
import trophy.projetc2.User.Login;

public class MainActivity extends AppCompatActivity {
    String Name, Team, Profile, Token = "hh", Team_Pk, Team_Duty, Address_Do, Address_Si;
    static String Pk;
    String strCurYear, strCurMonth, strCurDay, strCurHour,strCurMinute, strCurToday, strCurTime, strCurAll;
    String[][] parseredData_AddToken, parseredData_user, parseredData_teammake,RankingParsedList;
    public static Activity activity;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    DrawerLayout drawer;
    SharedPreferences preferences; //캐쉬 데이터 생성
    SharedPreferences.Editor editor; //캐쉬 데이터 에디터 생성
    ImageView Main_Navigation_ImageView_Profile;
    static TimerTask myTask;
    static Timer timer;
    private LayoutInflater inflater;
    String[][] parseredData_score;int score_count = 0;String Game_Status="";String[][] parseredData_score_away;

    ListView Contest_ListView_OutCourt;ArrayList<OutCourt_CourtInfo_MyData> OutCourt_CourtInfo_MyData;OutCourt_CourtInfo_MyAdapter adapter_outcourt;
    ListView Contest_ListView_contest_text;
    ArrayList<Match_MyData> Match_MyData;Match_MyAdapter adapter;ListView Contest_ListView_Match;
    protected boolean shouldAskPermissions() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @TargetApi(23)
    protected void askPermissions() {
        String[] permissions = {
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WRITE_EXTERNAL_STORAGE"
        };
        int requestCode = 200;
        requestPermissions(permissions, requestCode);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (shouldAskPermissions()) {
            askPermissions();
        }
        activity = MainActivity.this;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        inflater = getLayoutInflater();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar123);
        setSupportActionBar(toolbar);
        currentTime();
        ImageView MainActivity_ActionBar_Drawer = (ImageView) toolbar.findViewById(R.id.MainActivity_ActionBar_Drawer);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        MainActivity_ActionBar_Drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("aa", "tt");
                drawer.openDrawer(Gravity.LEFT);
            }
        });
//추가한 라인

        ListView listView = (ListView) findViewById(R.id.Contest_ListView_contest);
        Contest_ListView_contest_text = (ListView) findViewById(R.id.Contest_ListView_contest_text);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //navigationView.setNavigationItemSelectedListener(this);

        //유저 네비게이션바
        preferences = getSharedPreferences("trophy", MODE_PRIVATE);
        Pk = preferences.getString("Pk", ".");


        //네비게이션 메뉴 선언 및 연결
        final View aa = navigationView.inflateHeaderView(R.layout.layout_navigationbar);
        Main_Navigation_ImageView_Profile = (ImageView) aa.findViewById(R.id.Main_Navigation_ImageView_Profile);
        final LinearLayout Main_Navigation_UserInfo = (LinearLayout)aa.findViewById(R.id.Main_Navigation_UserInfo);
        final TextView Main_Navigation_TextView_Name = (TextView) aa.findViewById(R.id.Main_Navigation_TextView_Name);
        final TextView Main_Navigation_Button_TeamManager = (TextView) aa.findViewById(R.id.Main_Navigation_Button_TeamManager);
        final LinearLayout Main_Navigation_Button_Contest = (LinearLayout)aa.findViewById(R.id.Main_Navigation_Button_Contest);
        final LinearLayout Main_Navigation_Button_LastContest = (LinearLayout) aa.findViewById(R.id.Main_Navigation_Button_LastContest);
        final LinearLayout Main_Navigation_Button_Notice = (LinearLayout) aa.findViewById(R.id.Main_Navigation_Button_Notice);
        final LinearLayout Main_Navigation_Button_Suggest = (LinearLayout) aa.findViewById(R.id.Main_Navigation_Button_Suggest);
        final LinearLayout Main_Navigation_Button_Logout = (LinearLayout) aa.findViewById(R.id.Main_Navigation_Button_Logout);
        final LinearLayout Main_Navigation_Button_Ranking = (LinearLayout) aa.findViewById(R.id.Main_Navigation_Button_Ranking);
        final LinearLayout Main_Navigation_Button_Match = (LinearLayout)aa.findViewById(R.id.Main_Navigation_Button_Match);
        final LinearLayout Main_Navigation_Button_OutCourt = (LinearLayout)aa.findViewById(R.id.Main_Navigation_Button_OutCourt);
        final TextView Main_Navigation_Line_Logout = (TextView)aa.findViewById(R.id.Main_Navigation_Line_Logout);
        if (Pk.equals("") || Pk.equals(".")) { ///////////////////////비로그인시
            Glide.with(MainActivity.this).load(R.drawable.profile_basic_image).diskCacheStrategy(DiskCacheStrategy.NONE).bitmapTransform(new CropCircleTransformation(Glide.get(MainActivity.this).getBitmapPool()))
                    .skipMemoryCache(true)
                    .into(Main_Navigation_ImageView_Profile);

            Main_Navigation_TextView_Name.setText("로그인을 해주세요");
            Main_Navigation_Button_Logout.setVisibility(View.GONE);Main_Navigation_Line_Logout.setVisibility(View.GONE);
            Main_Navigation_TextView_Name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Pk.equals(".")) {
                        Intent intent_login = new Intent(MainActivity.this, Login.class);
                        startActivity(intent_login);
                        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                    }
                    else{
                        Intent intent = new Intent(MainActivity.this, ChangePersonalInfoActivity.class);
                        intent.putExtra("TeamName", Team);
                        startActivity(intent);
                        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                    }
                }
            });
        } else { ///////////////////////////로그인시
            //gcm 데이터 등록
            Token = FirebaseInstanceId.getInstance().getToken();
            Log.i("token", Token);
            HttpClient http_addtoken = new HttpClient();
            String result12 = http_addtoken.HttpClient("Trophy_part1", "Fcm_Add_AND.jsp", Pk, Token);
            parseredData_AddToken = jsonParserList_AddToken(result12);
            
            HttpClient user = new HttpClient();
            String result1 = user.HttpClient("Trophy_part1", "User.jsp", Pk);
            parseredData_user = jsonParserList_User(result1);
            Name = parseredData_user[0][0];
            Team = parseredData_user[0][1];
            Log.i("UserTeam", Team);
            Profile = parseredData_user[0][2];
            Team_Pk = parseredData_user[0][3];
            Team_Duty = parseredData_user[0][4];
            Address_Do = parseredData_user[0][5];
            Address_Si = parseredData_user[0][6];
            //프로필 관리
            try {
                String Profile1 = URLEncoder.encode(Profile, "utf-8");
                Log.i("Profile1 : ", Profile1);
                if (Profile1.equals(".")) {
                    Glide.with(MainActivity.this).load(R.drawable.profile_basic_image).diskCacheStrategy(DiskCacheStrategy.NONE).bitmapTransform(new CropCircleTransformation(Glide.get(MainActivity.this).getBitmapPool()))
                            .skipMemoryCache(true)
                            .into(Main_Navigation_ImageView_Profile);
                } else {
                    Glide.with(MainActivity.this).load("http://210.122.7.193:8080/Trophy_img/profile/" + Profile1 + ".jpg").diskCacheStrategy(DiskCacheStrategy.NONE).bitmapTransform(new CropCircleTransformation(Glide.get(MainActivity.this).getBitmapPool()))
                            .skipMemoryCache(true)
                            .into(Main_Navigation_ImageView_Profile);
                }
            } catch (UnsupportedEncodingException e) {

            }
            Main_Navigation_TextView_Name.setText(Name);
            Main_Navigation_TextView_Name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Pk.equals(".")) {
                        Intent intent_login = new Intent(MainActivity.this, Login.class);
                        startActivity(intent_login);
                        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                    }
                    else{
                        Intent intent = new Intent(MainActivity.this, ChangePersonalInfoActivity.class);
                        intent.putExtra("TeamName", Team);
                        startActivity(intent);
                        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                    }
                }
            });
            Main_Navigation_ImageView_Profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Pk.equals(".")) {
                    }
                    else{
                        if (Profile.equals(".")) {
                            HttpClient user = new HttpClient();
                            String result = user.HttpClient("Trophy_part1", "Profile_Image.jsp", Pk, Pk);
                            Profile = "exist";
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
                        } else {
                            LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            View layout = inflater.inflate(R.layout.layout_customdialog_album, (ViewGroup) view.findViewById(R.id.Layout_CustomDialog_Album_Root));
                            final Button Layout_CustomDialog_Album_BasicImage = (Button) layout.findViewById(R.id.Layout_CustomDialog_Album_BasicImage);
                            final Button Layout_CustomDialog_Album_AlbumImage = (Button) layout.findViewById(R.id.Layout_CustomDialog_Album_AlbumImage);
                            final Button Layout_CustomDialog_Album_Cancel = (Button) layout.findViewById(R.id.Layout_CustomDialog_Album_Cancel);
                            final AlertDialog.Builder aDialog = new AlertDialog.Builder(view.getContext());
                            aDialog.setView(layout);
                            final AlertDialog ad = aDialog.create();
                            ad.show();
                            Layout_CustomDialog_Album_Cancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    ad.dismiss();
                                }
                            });
                            Layout_CustomDialog_Album_BasicImage.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Glide.with(MainActivity.this).load(R.drawable.profile_basic_image).diskCacheStrategy(DiskCacheStrategy.NONE).bitmapTransform(new CropCircleTransformation(Glide.get(MainActivity.this).getBitmapPool()))
                                            .skipMemoryCache(true)
                                            .into(Main_Navigation_ImageView_Profile);
                                    HttpClient user = new HttpClient();
                                    user.HttpClient("Trophy_part1", "Profile_Image.jsp", Pk, ".");
//                            Main_Navigation_ImageView_Profile.setImageResource(R.drawable.profile_basic_image);
                                    ad.dismiss();
                                    Profile = ".";
                                }
                            });
                            Layout_CustomDialog_Album_AlbumImage.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    HttpClient user = new HttpClient();
                                    String result = user.HttpClient("Trophy_part1", "Profile_Image.jsp", Pk, Pk);
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
                                    ad.dismiss();
                                    Profile = "exist";
                                }
                            });
                        }
                    }
                }
            });
        }

        Main_Navigation_Button_Contest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_Suggest = new Intent(MainActivity.this, Navigation_Contest.class);
                intent_Suggest.putExtra("Pk", Pk);
                startActivity(intent_Suggest);
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
            }
        });
        Main_Navigation_Button_LastContest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent_LastContest = new Intent(MainActivity.this, Last_Contest.class);
//                startActivity(intent_LastContest);
//                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                Snackbar.make(v,"준비 중입니다.",Snackbar.LENGTH_SHORT).show();
            }
        });
        Main_Navigation_Button_TeamManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Pk.equals(".")) {
                    Intent intent_login = new Intent(MainActivity.this, Login.class);
                    startActivity(intent_login);
                    overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                }
                else{
                    if(Team.equals(".")){
                        LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
                        final View layout = inflater.inflate(R.layout.layout_customdialog_2choice_x, (ViewGroup) findViewById(R.id.Customdialog_2Choice_Root));
                        final TextView Customdialog_2Choice_Title = (TextView)layout.findViewById(R.id.Customdialog_2Choice_Title);
                        final ImageView Customdialog_2Choice_Back = (ImageView)layout.findViewById(R.id.Customdialog_2Choice_Back);
                        final TextView Customdialog_2Choice_Content = (TextView)layout.findViewById(R.id.Customdialog_2Choice_Content);
                        final Button Customdialog_2Choice_First = (Button)layout.findViewById(R.id.Customdialog_2Choice_First);
                        final Button Customdialog_2Choice_Second = (Button)layout.findViewById(R.id.Customdialog_2Choice_Second);
                        Customdialog_2Choice_Title.setText("팀 정보");
                        Customdialog_2Choice_Content.setText("가입된 팀이 없습니다.");
                        Customdialog_2Choice_First.setText("팀 생성");
                        Customdialog_2Choice_First.setBackgroundColor(getResources().getColor(R.color.main1color));
                        Customdialog_2Choice_First.setTextColor(getResources().getColor(R.color.White));
                        Customdialog_2Choice_Second.setText("팀 가입");
                        Customdialog_2Choice_Second.setBackgroundColor(getResources().getColor(R.color.main1color));
                        Customdialog_2Choice_Second.setTextColor(getResources().getColor(R.color.White));
                        final MaterialDialog TeamInfo_Dialog = new MaterialDialog(MainActivity.this);
                        TeamInfo_Dialog
                                .setContentView(layout)
                                .setCanceledOnTouchOutside(true);
                        TeamInfo_Dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialogInterface) {
                                TeamInfo_Dialog.dismiss();
                            }
                        });
                        TeamInfo_Dialog.show();
                        Customdialog_2Choice_Back.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                TeamInfo_Dialog.dismiss();
                            }
                        });
                        Customdialog_2Choice_First.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                TeamInfo_Dialog.dismiss();
                                Intent intent_TeamMake = new Intent(MainActivity.this, TeamMake.class);
                                intent_TeamMake.putExtra("Pk", Pk);
                                startActivity(intent_TeamMake);
                                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                            }
                        });
                        Customdialog_2Choice_Second.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                TeamInfo_Dialog.dismiss();
                                Intent intent_TeamSearch = new Intent(MainActivity.this, TeamSearch.class);
                                intent_TeamSearch.putExtra("Pk", Pk);
                                startActivity(intent_TeamSearch);
                                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                            }
                        });
                    }else{
                        Intent intent_TeamSearch = new Intent(MainActivity.this, TeamInfo.class);
                        intent_TeamSearch.putExtra("User_Pk", Pk);
                        intent_TeamSearch.putExtra("Team_Pk", Team_Pk);
                        intent_TeamSearch.putExtra("TeamName", Team);
                        startActivity(intent_TeamSearch);
                        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                    }
                }
            }
        });
        Main_Navigation_Button_Ranking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_TeamSearch = new Intent(MainActivity.this, TeamRanking.class);
                intent_TeamSearch.putExtra("Pk", Pk);
                startActivity(intent_TeamSearch);
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
            }
        });
        Main_Navigation_Button_Notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_Notice = new Intent(MainActivity.this, Notice.class);
                startActivity(intent_Notice);
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
            }
        });
        Main_Navigation_Button_Suggest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Pk.equals(".")) {
                    Intent intent_login = new Intent(MainActivity.this, Login.class);
                    startActivity(intent_login);
                    overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                } else {
                    Intent intent_Suggest = new Intent(MainActivity.this, Suggest.class);
                    intent_Suggest.putExtra("Pk", Pk);
                    startActivity(intent_Suggest);
                    overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                }
            }
        });
        //시합요청
        Main_Navigation_Button_Match.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_Match = new Intent(MainActivity.this, Match.class);
                intent_Match.putExtra("User_Pk", Pk);
                intent_Match.putExtra("Team_Pk", Team_Pk);
                intent_Match.putExtra("Team_Duty", Team_Duty);
                startActivity(intent_Match);
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
            }
        });
//        Main_Navigation_Button_Succed_Score.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, Score.class);
//                intent.putExtra("User_Pk", Pk);
//                intent.putExtra("Team_Pk", Team_Pk);
//                startActivity(intent);
//                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
//            }
//        });
        //로그아웃
        Main_Navigation_Button_Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preferences = getSharedPreferences("trophy", MODE_PRIVATE);
                editor = preferences.edit();

                editor.putString("Pk", ".");
                editor.commit();

                startActivity(new Intent(MainActivity.this, MainActivity.class));
                finish();
            }
        });
        /////////////////////////////////////////////
        Main_Navigation_Button_OutCourt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_Match = new Intent(MainActivity.this, OutCourt_Address.class);
                intent_Match.putExtra("User_Pk", Pk);
                intent_Match.putExtra("Team_Pk", Team_Pk);
                intent_Match.putExtra("Team_Duty", Team_Duty);
                startActivity(intent_Match);
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
            }
        });
        //슬라이딩
        //프래그먼트 정의
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        final DotIndicator indicator = (DotIndicator) findViewById(R.id.main_indicator_ad);
        // 도트 색 지정
        indicator.setSelectedDotColor(Color.parseColor("#F96332"));
        indicator.setUnselectedDotColor(Color.parseColor("#CFCFCF"));

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(1);
        ////////////
        final int pageCount = 3;
        indicator.setNumberOfItems(pageCount);

        myTask = new TimerTask() {
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int currentPage = mViewPager.getCurrentItem();
                        if (currentPage >= pageCount - 1) mViewPager.setCurrentItem(0, true);
                        else mViewPager.setCurrentItem(currentPage + 1, true);
                        indicator.setSelectedItem((currentPage + 1 == pageCount) ? 0 : currentPage + 1, true);
                    }
                });
            }
        };
        timer = new Timer();
        //timer.schedule(myTask, 5000);  // 5초후 실행하고 종료
       // timer.schedule(myTask, 500, 6000); // 5초후 첫실행, 3초마다 계속실행
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                indicator.setSelectedItem(mViewPager.getCurrentItem(), true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        ////////////////////////////////////////////
        //대회 더보기
        TextView Main_Contest_TextView_ContestFocus = (TextView)findViewById(R.id.Main_Contest_TextView_ContestFocus);
        Main_Contest_TextView_ContestFocus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_Suggest = new Intent(MainActivity.this, Navigation_Contest.class);
                intent_Suggest.putExtra("Pk", Pk);
                startActivity(intent_Suggest);
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
            }
        });
        //대회 리스트뷰
        HttpClient ContestHttp = new HttpClient();
        String result = ContestHttp.HttpClient("Trophy_part1", "Main_Contest.jsp");
        String[][] ContestsParsedList = jsonParserList_getContestList(result);

        ArrayList<Contests_Customlist_MyData> Contests_Customlist_MyData;
        Contests_Customlist_MyData = new ArrayList<Contests_Customlist_MyData>();
        for (int i = 0; i < 2; i=i+2) {
            Contests_Customlist_MyData.add(new Contests_Customlist_MyData(this, ContestsParsedList[i][0], ContestsParsedList[i][1],
                    ContestsParsedList[i][2], ContestsParsedList[i][3], ContestsParsedList[i][4], ContestsParsedList[i][5],
                    ContestsParsedList[i][6], ContestsParsedList[i][7], ContestsParsedList[i+1][0], ContestsParsedList[i+1][1],
                    ContestsParsedList[i+1][2], ContestsParsedList[i+1][3], ContestsParsedList[i+1][4], ContestsParsedList[i+1][5], ContestsParsedList[i+1][6], ContestsParsedList[i+1][7]));
        }
        Contests_Customlist_Adapter Adapter = new Contests_Customlist_Adapter(this, Contests_Customlist_MyData);
        listView.setAdapter(Adapter);

        ArrayList<Contest_Text_Customlist_MyData> Contest_Text_Customlist_MyData;
        Contest_Text_Customlist_MyData = new ArrayList<Contest_Text_Customlist_MyData>();
        for (int i = 2; i < 6; i++) {
            Contest_Text_Customlist_MyData.add(new Contest_Text_Customlist_MyData(this, ContestsParsedList[i][0], ContestsParsedList[i][1],
                    ContestsParsedList[i][2], ContestsParsedList[i][3], ContestsParsedList[i][4], ContestsParsedList[i][5],
                    ContestsParsedList[i][6], ContestsParsedList[i][7]));
        }
        Contest_Text_Customlist_Adapter Adapter1 = new Contest_Text_Customlist_Adapter(this, Contest_Text_Customlist_MyData);
        Contest_ListView_contest_text.setAdapter(Adapter1);

        //랭킹 더보기
        TextView Main_Contest_TextView_RankingFocus = (TextView)findViewById(R.id.Main_Contest_TextView_RankingFocus);
        Main_Contest_TextView_RankingFocus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_TeamSearch = new Intent(MainActivity.this, TeamRanking.class);
                intent_TeamSearch.putExtra("Pk", Pk);
                startActivity(intent_TeamSearch);
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
            }
        });
        Contest_ListView_Match = (ListView)findViewById(R.id.Contest_ListView_Match);
        TextView Main_Contest_TextView_Match_Focus = (TextView)findViewById(R.id.Main_Contest_TextView_Match_Focus);

        //매치 리스트뷰
        HttpClient http_match = new HttpClient();
        String result1 = http_match.HttpClient("Trophy_part1","Match.jsp");
        String[][] parsedData_Match = jsonParserList_Match(result1);

        Match_MyData = new ArrayList<Match_MyData>();
        if(parsedData_Match.length <= 5){
            for (int i = 0; i < parsedData_Match.length; i++) {
                String Status = getStatus(parsedData_Match[i][9], parsedData_Match[i][10]);
                Match_MyData.add(new Match_MyData(parsedData_Match[i][0], parsedData_Match[i][1], parsedData_Match[i][2],parsedData_Match[i][3],parsedData_Match[i][4],parsedData_Match[i][5],parsedData_Match[i][6],Status,MainActivity.this,Pk,parsedData_Match[i][9],Team_Pk,parsedData_Match[i][10]));
            }
        }
        else{
            for (int i = 0; i < 5; i++) {
                String Status = getStatus(parsedData_Match[i][9], parsedData_Match[i][10]);
                Match_MyData.add(new Match_MyData(parsedData_Match[i][0], parsedData_Match[i][1], parsedData_Match[i][2],parsedData_Match[i][3],parsedData_Match[i][4],parsedData_Match[i][5],parsedData_Match[i][6],Status,MainActivity.this,Pk,parsedData_Match[i][9],Team_Pk,parsedData_Match[i][10]));
            }
        }
        adapter = new Match_MyAdapter(this, Match_MyData);
        Contest_ListView_Match.setAdapter(adapter);
        setListViewHeightBasedOnChildren(Contest_ListView_Match);

        Main_Contest_TextView_Match_Focus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_Match = new Intent(MainActivity.this, Match.class);
                intent_Match.putExtra("User_Pk", Pk);
                intent_Match.putExtra("Team_Pk", Team_Pk);
                intent_Match.putExtra("Team_Duty", Team_Duty);
                startActivity(intent_Match);
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
            }
        });

        //실시간 야외코트
        Contest_ListView_OutCourt = (ListView)findViewById(R.id.Contest_ListView_OutCourt);
        TextView Main_Contest_TextView_OutCourt_Focus = (TextView)findViewById(R.id.Main_Contest_TextView_OutCourt_Focus);

        OutCourt_CourtInfo_MyData = new ArrayList<OutCourt_CourtInfo_MyData>();
        HttpClient http_outcourt = new HttpClient();
        if(Pk.equals(".")){
            Address_Do = "서울";Address_Si = "강남구";
        }
        String result2 = http_outcourt.HttpClient("Trophy_part1","OutCourt_Main.jsp", Address_Do,Address_Si, strCurToday);
        String[][] parsedData_outcourt = jsonParserList_OutCourt(result2);
        if(parsedData_outcourt[0][0] == "."){
        }else{
            for (int j = 0; j < parsedData_outcourt.length; j++) {
                OutCourt_CourtInfo_MyData.add(new OutCourt_CourtInfo_MyData(parsedData_outcourt[j][0], parsedData_outcourt[j][1] + "  " + parsedData_outcourt[j][2],parsedData_outcourt[j][5],this,Pk,parsedData_outcourt[j][3],parsedData_outcourt[j][4]));
            }
        }
        adapter_outcourt = new OutCourt_CourtInfo_MyAdapter(this, OutCourt_CourtInfo_MyData);
        Contest_ListView_OutCourt.setAdapter(adapter_outcourt);
        setListViewHeightBasedOnChildren(Contest_ListView_OutCourt);

        Main_Contest_TextView_OutCourt_Focus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_Match = new Intent(MainActivity.this, OutCourt_CourtInfo_Main.class);
                intent_Match.putExtra("User_Pk", Pk);
                if(Pk.equals(".")){
                    intent_Match.putExtra("Address_Do", "서울");
                }else{
                    intent_Match.putExtra("Address_Do", Address_Do);
                }
                intent_Match.putExtra("Address_Si", Address_Si);
                startActivity(intent_Match);
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
            }
        });
////////////////////////////////////////////////////////////////////////////
        //메인 랭킹 -xml연결
        TextView Main_Ranking_TextView_Ranking1 = (TextView)findViewById(R.id.Main_Ranking_TextView_Ranking1);
        ImageView Main_Ranking_ImageView_Emblem1 = (ImageView)findViewById(R.id.Main_Ranking_ImageView_Emblem1);
        TextView Main_Ranking_ImageView_TeamName1 = (TextView)findViewById(R.id.Main_Ranking_ImageView_TeamName1);

        TextView Main_Ranking_TextView_Ranking2 = (TextView)findViewById(R.id.Main_Ranking_TextView_Ranking2);
        ImageView Main_Ranking_ImageView_Emblem2 = (ImageView)findViewById(R.id.Main_Ranking_ImageView_Emblem2);
        TextView Main_Ranking_ImageView_TeamName2 = (TextView)findViewById(R.id.Main_Ranking_ImageView_TeamName2);

        TextView Main_Ranking_TextView_Ranking3 = (TextView)findViewById(R.id.Main_Ranking_TextView_Ranking3);
        ImageView Main_Ranking_ImageView_Emblem3 = (ImageView)findViewById(R.id.Main_Ranking_ImageView_Emblem3);
        TextView Main_Ranking_ImageView_TeamName3 = (TextView)findViewById(R.id.Main_Ranking_ImageView_TeamName3);

        TextView Main_Ranking_TextView_Ranking4 = (TextView)findViewById(R.id.Main_Ranking_TextView_Ranking4);
        ImageView Main_Ranking_ImageView_Emblem4 = (ImageView)findViewById(R.id.Main_Ranking_ImageView_Emblem4);
        TextView Main_Ranking_ImageView_TeamName4 = (TextView)findViewById(R.id.Main_Ranking_ImageView_TeamName4);
        HttpClient RankingHttp = new HttpClient();String result_Ranking="";

        //메인 랭킹 -http 값 받아오기
        result_Ranking = RankingHttp.HttpClient("Trophy_part1", "Main_Ranking.jsp",Pk);
        RankingParsedList = jsonParserList_Ranking(result_Ranking);

        //메인 랭킹 -받아온 텍스트값 저장
        Main_Ranking_TextView_Ranking1.setText(RankingParsedList[0][3]);
        Main_Ranking_TextView_Ranking2.setText(RankingParsedList[1][3]);
        Main_Ranking_TextView_Ranking3.setText(RankingParsedList[2][3]);
        Main_Ranking_TextView_Ranking4.setText(RankingParsedList[3][3]);

        Main_Ranking_ImageView_TeamName1.setText(RankingParsedList[0][2]);
        Main_Ranking_ImageView_TeamName2.setText(RankingParsedList[1][2]);
        Main_Ranking_ImageView_TeamName3.setText(RankingParsedList[2][2]);
        Main_Ranking_ImageView_TeamName4.setText(RankingParsedList[3][2]);

        //메인 랭킹 -받아온 이미지값 저장
        try{
            String En_Emblem1 = URLEncoder.encode(RankingParsedList[0][1], "utf-8");
            if (En_Emblem1.equals(".")) {
                Glide.with(getApplicationContext()).load(R.drawable.emblem).bitmapTransform(new CropCircleTransformation(Glide.get(getApplicationContext()).getBitmapPool()))
                        .into(Main_Ranking_ImageView_Emblem1);
            } else {
                Glide.with(getApplicationContext()).load("http://210.122.7.193:8080/Trophy_img/team/" + En_Emblem1 + ".jpg").bitmapTransform(new CropCircleTransformation(Glide.get(getApplicationContext()).getBitmapPool()))
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(Main_Ranking_ImageView_Emblem1);
            }
            String En_Emblem2 = URLEncoder.encode(RankingParsedList[1][1], "utf-8");
            if (En_Emblem2.equals(".")) {
                Glide.with(getApplicationContext()).load(R.drawable.emblem).bitmapTransform(new CropCircleTransformation(Glide.get(getApplicationContext()).getBitmapPool()))
                        .into(Main_Ranking_ImageView_Emblem2);
            } else {
                Glide.with(getApplicationContext()).load("http://210.122.7.193:8080/Trophy_img/team/" + En_Emblem2 + ".jpg").bitmapTransform(new CropCircleTransformation(Glide.get(getApplicationContext()).getBitmapPool()))
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(Main_Ranking_ImageView_Emblem2);
            }
            String En_Emblem3 = URLEncoder.encode(RankingParsedList[2][1], "utf-8");
            if (En_Emblem3.equals(".")) {
                Glide.with(getApplicationContext()).load(R.drawable.emblem).bitmapTransform(new CropCircleTransformation(Glide.get(getApplicationContext()).getBitmapPool()))
                        .into(Main_Ranking_ImageView_Emblem3);
            } else {
                Glide.with(getApplicationContext()).load("http://210.122.7.193:8080/Trophy_img/team/" + En_Emblem3 + ".jpg").bitmapTransform(new CropCircleTransformation(Glide.get(getApplicationContext()).getBitmapPool()))
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(Main_Ranking_ImageView_Emblem3);
            }
            String En_Emblem4 = URLEncoder.encode(RankingParsedList[3][1], "utf-8");
            if (En_Emblem4.equals(".")) {
                Glide.with(getApplicationContext()).load(R.drawable.emblem).bitmapTransform(new CropCircleTransformation(Glide.get(getApplicationContext()).getBitmapPool()))
                        .into(Main_Ranking_ImageView_Emblem4);
            } else {
                Glide.with(getApplicationContext()).load("http://210.122.7.193:8080/Trophy_img/team/" + En_Emblem4 + ".jpg").bitmapTransform(new CropCircleTransformation(Glide.get(getApplicationContext()).getBitmapPool()))
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(Main_Ranking_ImageView_Emblem4);
            }
        }catch (UnsupportedEncodingException e){

        }
        //메인 랭킹 -각 팀 클릭시 팀 정보로 넘어가는 이벤트
        Main_Ranking_ImageView_Emblem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity.this,TeamSearch_Focus.class);
                intent1.putExtra("TeamName", RankingParsedList[0][2]);
                intent1.putExtra("User_Pk", Pk);
                intent1.putExtra("Team_Pk",RankingParsedList[0][0]);
                startActivity(intent1);
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
            }
        });
        Main_Ranking_ImageView_Emblem2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity.this,TeamSearch_Focus.class);
                intent1.putExtra("TeamName", RankingParsedList[1][2]);
                intent1.putExtra("User_Pk", Pk);
                intent1.putExtra("Team_Pk",RankingParsedList[0][0]);
                startActivity(intent1);
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
            }
        });
        Main_Ranking_ImageView_Emblem3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity.this,TeamSearch_Focus.class);
                intent1.putExtra("TeamName", RankingParsedList[2][2]);
                intent1.putExtra("User_Pk", Pk);
                intent1.putExtra("Team_Pk",RankingParsedList[2][0]);
                startActivity(intent1);
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
            }
        });
        Main_Ranking_ImageView_Emblem4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity.this,TeamSearch_Focus.class);
                intent1.putExtra("TeamName", RankingParsedList[3][2]);
                intent1.putExtra("User_Pk", Pk);
                intent1.putExtra("Team_Pk",RankingParsedList[3][0]);
                startActivity(intent1);
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
            }
        });
        //점수입력안된 연습게임이 있는 경우
        HttpClient score = new HttpClient();
        String result5 = score.HttpClient("Trophy_part1", "Main_Score.jsp", Pk);
        parseredData_score = jsonParserList_Score(result5);
        Log.i("test", Integer.toString(parseredData_score.length));
        for(score_count = 0 ;score_count < parseredData_score.length; score_count++)
        {
                Game_Status = getStatus(parseredData_score[score_count][1], parseredData_score[score_count][2],parseredData_score[score_count][3]);
                if(Game_Status.equals("After")){
                    LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
                    final View layout = inflater.inflate(R.layout.layout_customdialog_2choice, (ViewGroup) findViewById(R.id.Customdialog_2Choice_Root));
                    final TextView Customdialog_2Choice_Title = (TextView)layout.findViewById(R.id.Customdialog_2Choice_Title);
                    final ImageView Customdialog_2Choice_Back = (ImageView)layout.findViewById(R.id.Customdialog_2Choice_Back);
                    final TextView Customdialog_2Choice_Content = (TextView)layout.findViewById(R.id.Customdialog_2Choice_Content);
                    final Button Customdialog_2Choice_First = (Button)layout.findViewById(R.id.Customdialog_2Choice_First);
                    final Button Customdialog_2Choice_Second = (Button)layout.findViewById(R.id.Customdialog_2Choice_Second);
                    Customdialog_2Choice_Title.setText("점수입력");
                    Customdialog_2Choice_Content.setText("점수입력되지 않은 연습게임이 잇습니다");
                    Customdialog_2Choice_First.setText("이 동");
                    Customdialog_2Choice_Second.setText("다음에 하기");
                    final MaterialDialog TeamInfo_Dialog = new MaterialDialog(MainActivity.this);
                    TeamInfo_Dialog
                            .setContentView(layout)
                            .setCanceledOnTouchOutside(true);
                    TeamInfo_Dialog.show();
                    Customdialog_2Choice_Back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            TeamInfo_Dialog.dismiss();
                        }
                    });
                    Customdialog_2Choice_First.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(MainActivity.this, Score_Focus.class);
                            intent.putExtra("Match_Pk", parseredData_score[score_count][0]);
                            intent.putExtra("User_Pk", Pk);
                            intent.putExtra("GameStatus", Game_Status);
                            startActivity(intent);
                            overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                            TeamInfo_Dialog.dismiss();
                        }
                    });
                    Customdialog_2Choice_Second.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            TeamInfo_Dialog.dismiss();
                        }
                    });
                    break;
                }
        }
        //점수확인안된 연습게임이 있는 경우
        HttpClient score_away = new HttpClient();
        String result6 = score_away.HttpClient("Trophy_part1", "Main_Score_Away.jsp", Pk);
        parseredData_score_away = jsonParserList_Score(result6);
        for(score_count = 0 ;score_count < parseredData_score_away.length; score_count++)
        {
            Game_Status = getStatus(parseredData_score_away[score_count][1], parseredData_score_away[score_count][2],parseredData_score_away[score_count][3]);
            if(Game_Status.equals("After")){
                LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
                final View layout = inflater.inflate(R.layout.layout_customdialog_2choice, (ViewGroup) findViewById(R.id.Customdialog_2Choice_Root));
                final TextView Customdialog_2Choice_Title = (TextView)layout.findViewById(R.id.Customdialog_2Choice_Title);
                final ImageView Customdialog_2Choice_Back = (ImageView)layout.findViewById(R.id.Customdialog_2Choice_Back);
                final TextView Customdialog_2Choice_Content = (TextView)layout.findViewById(R.id.Customdialog_2Choice_Content);
                final Button Customdialog_2Choice_First = (Button)layout.findViewById(R.id.Customdialog_2Choice_First);
                final Button Customdialog_2Choice_Second = (Button)layout.findViewById(R.id.Customdialog_2Choice_Second);
                Customdialog_2Choice_Title.setText("점수확인");
                Customdialog_2Choice_Content.setText("점수확인되지 않은 연습게임이 잇습니다");
                Customdialog_2Choice_First.setText("이 동");
                Customdialog_2Choice_Second.setText("다음에 하기");
                final MaterialDialog TeamInfo_Dialog = new MaterialDialog(MainActivity.this);
                TeamInfo_Dialog
                        .setContentView(layout)
                        .setCanceledOnTouchOutside(true);
                TeamInfo_Dialog.show();
                Customdialog_2Choice_Back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TeamInfo_Dialog.dismiss();
                    }
                });
                Customdialog_2Choice_First.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TeamInfo_Dialog.dismiss();
                        Intent intent = new Intent(MainActivity.this, Score_Focus.class);
                        intent.putExtra("Match_Pk", parseredData_score_away[score_count][0]);
                        intent.putExtra("User_Pk", Pk);
                        intent.putExtra("GameStatus", Game_Status);
                        startActivity(intent);
                        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                    }
                });
                Customdialog_2Choice_Second.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TeamInfo_Dialog.dismiss();
                    }
                });
                break;
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //아웃코트 리로드
        OutCourt_CourtInfo_MyData = new ArrayList<OutCourt_CourtInfo_MyData>();
        HttpClient http_outcourt = new HttpClient();
        String result2 = http_outcourt.HttpClient("Trophy_part1","OutCourt_Main.jsp", Address_Do,Address_Si, strCurToday);
        String[][] parsedData_outcourt = jsonParserList_OutCourt(result2);
        if(parsedData_outcourt[0][0] == "."){
        }else{
            for (int j = 0; j < parsedData_outcourt.length; j++) {
                OutCourt_CourtInfo_MyData.add(new OutCourt_CourtInfo_MyData(parsedData_outcourt[j][0], parsedData_outcourt[j][1] + "  " + parsedData_outcourt[j][2],parsedData_outcourt[j][5],this,Pk,parsedData_outcourt[j][3],parsedData_outcourt[j][4]));
            }
        }
        adapter_outcourt = new OutCourt_CourtInfo_MyAdapter(this, OutCourt_CourtInfo_MyData);
        Contest_ListView_OutCourt.setAdapter(adapter_outcourt);
        setListViewHeightBasedOnChildren(Contest_ListView_OutCourt);

        //연습게임 리로드
        //매치 리스트뷰
        HttpClient http_match = new HttpClient();
        String result1 = http_match.HttpClient("Trophy_part1","Match.jsp");
        String[][] parsedData_Match = jsonParserList_Match(result1);

        Match_MyData = new ArrayList<Match_MyData>();
        if(parsedData_Match.length <= 5){
            for (int i = 0; i < parsedData_Match.length; i++) {
                String Status = getStatus(parsedData_Match[i][9], parsedData_Match[i][10]);
                Match_MyData.add(new Match_MyData(parsedData_Match[i][0], parsedData_Match[i][1], parsedData_Match[i][2],parsedData_Match[i][3],parsedData_Match[i][4],parsedData_Match[i][5],parsedData_Match[i][6],Status,MainActivity.this,Pk,parsedData_Match[i][9],Team_Pk,parsedData_Match[i][10]));
            }
        }
        else{
            for (int i = 0; i < 5; i++) {
                String Status = getStatus(parsedData_Match[i][9], parsedData_Match[i][10]);
                Match_MyData.add(new Match_MyData(parsedData_Match[i][0], parsedData_Match[i][1], parsedData_Match[i][2],parsedData_Match[i][3],parsedData_Match[i][4],parsedData_Match[i][5],parsedData_Match[i][6],Status,MainActivity.this,Pk,parsedData_Match[i][9],Team_Pk,parsedData_Match[i][10]));
            }
        }
        adapter = new Match_MyAdapter(this, Match_MyData);
        Contest_ListView_Match.setAdapter(adapter);
        setListViewHeightBasedOnChildren(Contest_ListView_Match);

    }

    private String[][] jsonParserList_getContestList(String pRecvServerPage) {
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try {
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jarr = json.getJSONArray("List");

            String[] jsonName = {"msg1", "msg2", "msg3", "msg4", "msg5", "msg6", "msg7", "msg8"};
            String[][] parseredData = new String[jarr.length()][jsonName.length];
            for (int i = 0; i < jarr.length(); i++) {
                json = jarr.getJSONObject(i);
                for (int j = 0; j < jsonName.length; j++) {
                    parseredData[i][j] = json.getString(jsonName[j]);
                }
            }
            for (int i = 0; i < parseredData.length; i++) {
                Log.i("JSON을 분석한 데이터" + i + ":", parseredData[i][0]);
            }
            return parseredData;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String[][] jsonParserList_User(String pRecvServerPage) {
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try {
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");
            String[] jsonName = {"msg1", "msg2", "msg3", "msg4", "msg5", "msg6", "msg7"};
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

    public String[][] jsonParserList_TeamMake(String pRecvServerPage) {
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

    public String[][] jsonParserList_AddToken(String pRecvServerPage) {
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
    public String[][] jsonParserList_Ranking(String pRecvServerPage) {
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

    public String[][] jsonParserList_Match(String pRecvServerPage){
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try{
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");
            String[] jsonName = {"msg1","msg2","msg3","msg4","msg5","msg6","msg7","msg8", "msg9", "msg10","msg11"};
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
    public String[][] jsonParserList_OutCourt(String pRecvServerPage){
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try{
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");
            String[] jsonName = {"msg1","msg2","msg3","msg4","msg5","msg6"};

            if(jArr.length()==0){
                String[][] parseredData = new String[1][1];
                parseredData[0][0] = ".";
                return parseredData;
            }else{
                String[][] parseredData = new String[jArr.length()][jsonName.length];
                for(int i = 0; i<jArr.length();i++){
                    json = jArr.getJSONObject(i);
                    for (int j=0;j<jsonName.length; j++){
                        parseredData[i][j] = json.getString(jsonName[j]);
                    }
                }
                return parseredData;
            }
        }catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }
    public String[][] jsonParserList_Score(String pRecvServerPage){
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try{
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");
            String[] jsonName = {"msg1","msg2","msg3","msg4"};
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
                urlString = "http://210.122.7.193:8080/Trophy_part2/Profile_Image_Upload.jsp";
                //절대경로를 획득한다!!! 중요~
                Cursor c = getContentResolver().query(Uri.parse(selPhotoUri.toString()), null, null, null, null);
                c.moveToNext();
                //업로드할 파일의 절대경로 얻어오기("_data") 로 해도 된다.
                String absolutePath = c.getString(c.getColumnIndex(MediaStore.MediaColumns.DATA));
                Log.e("###파일의 절대 경로###", absolutePath);
                //파일 업로드 시작!
                HttpFileUpload(urlString, "", absolutePath);
                String En_Profile = URLEncoder.encode(Pk, "utf-8");
                Glide.with(MainActivity.this).load("http://210.122.7.193:8080/Trophy_img/profile/" + En_Profile + ".jpg").bitmapTransform(new CropCircleTransformation(Glide.get(MainActivity.this).getBitmapPool()))
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(Main_Navigation_ImageView_Profile);

            }
        } catch (FileNotFoundException e) {
            Log.e("tt",e.toString());
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("tt",e.toString());
            e.printStackTrace();
        } catch (NullPointerException e) {
            Log.e("tt",e.toString());
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
            dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + URLEncoder.encode(Pk, "utf-8") + ".jpg" + "\"" + lineEnd);
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
        SimpleDateFormat CurAllFormat = new SimpleDateFormat("yyyyMMddHHmm");
// 지정된 포맷으로 String 타입 리턴
        strCurToday = CurDateFormat.format(date);
        strCurTime = CurTimeFormat.format(date);
        strCurYear = CurYearFormat.format(date);
        strCurYear = CurYearFormat.format(date);
        strCurMonth = CurMonthFormat.format(date);
        strCurDay = CurDayFormat.format(date);
        strCurHour = CurHourFormat.format(date);
        strCurMinute = CurMinuteFormat.format(date);
        strCurAll= CurAllFormat.format(date);
    }
//    @Override
//    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
//    }

    @Override
    public void onBackPressed() {
        //  super.onBackPressed();
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
        } else {
            finish();
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            // return PlaceholderFragment.newInstance(position + 1);
            Fragment fragment = null;
            Bundle args = null;
            switch (position) {
                case 0:
                    fragment = new MainActivity_VierPage1();
                    args = new Bundle();
                    break;
                case 1:
                    fragment = new MainActivity_VierPage2();
                    args = new Bundle();
                    break;
                case 2:
                    fragment = new MainActivity_VierPage3();
                    args = new Bundle();
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }

    }
    public String getStatus(String Match_Date, String StartTime, String FinishTime){
        String str1 = Match_Date;
        String[] data1 = str1.split(":::");
        String str2 = data1[1];
        String[] data2 = str2.split(" / ");
        if(Integer.parseInt(data2[1]) < 10){
            data2[1] = "0"+data2[1];
        }
        String str3 = StartTime;
        String[] data3 = str3.split(":");
        if(Integer.parseInt(data3[1]) < 10){
            data3[1] = "0"+data3[1];
        }
        String str4 = FinishTime;
        String[] data4 = str4.split(":");
        if(Integer.parseInt(data4[1]) < 10){
            data4[1] = "0"+data4[1];
        }
        ////////////////////////////////////////////////////////////////////
        String match_starttime = data1[0]+data2[0]+data2[1]+data3[0]+data3[1];
        String match_finishtime = data1[0]+data2[0]+data2[1]+data4[0]+data4[1];
        if(Long.parseLong(strCurAll) < Long.parseLong(match_starttime)){
            return "Before";
        }
        else if((Long.parseLong(match_starttime) < Long.parseLong(strCurAll) &&  Long.parseLong(strCurAll) < Long.parseLong(match_finishtime))){
            return "Gameing";
        }
        else{
            return "After";
        }
    }
    public String getStatus(String Match_Date, String FinishTime){
        String str1 = Match_Date;
        String[] data1 = str1.split(":::");
        String str2 = data1[1];
        String[] data2 = str2.split(" / ");
        if(Integer.parseInt(data2[1]) < 10){
            data2[1] = "0"+data2[1];
        }
        String str3 = FinishTime;
        String[] data3 = str3.split(":");
        if(Integer.parseInt(data3[1]) < 10){
            data3[1] = "0"+data3[1];
        }
        ////////////////////////////////////////////////////////////////////
        String match_finishtime = data1[0]+data2[0]+data2[1]+data3[0]+data3[1];
        Log.i("test123",match_finishtime);
        if(Long.parseLong(match_finishtime)>Long.parseLong(strCurAll)){
            return  "recruiting";
        }
        else{
            return "finish";
        }
    }
}
