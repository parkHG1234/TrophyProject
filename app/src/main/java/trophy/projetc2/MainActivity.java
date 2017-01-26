package trophy.projetc2;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import me.drakeet.materialdialog.MaterialDialog;
import trophy.projetc2.Contest.Contests_Customlist_Adapter;
import trophy.projetc2.Contest.Contests_Customlist_MyData;
import trophy.projetc2.Http.HttpClient;
import trophy.projetc2.Navigation.Last_Contest;
import trophy.projetc2.Navigation.TeamMake;
import trophy.projetc2.Navigation.TeamManager;
import trophy.projetc2.Navigation.TeamSearch;
import trophy.projetc2.User.ChangePersonalInfoActivity;
import trophy.projetc2.User.Login;

public class MainActivity extends AppCompatActivity {
    String Pk, Name, Team, Profile;
    String[][] parseredData_user, parseredData_teammake;
    public static Activity activity;

    SharedPreferences preferences; //캐쉬 데이터 생성
    SharedPreferences.Editor editor; //캐쉬 데이터 에디터 생성
    ImageView Main_Navigation_ImageView_Profile;
    private LayoutInflater inflater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = MainActivity.this;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        inflater=getLayoutInflater();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar123);
        toolbar.setTitle("트로피");
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ListView listView = (ListView) findViewById(R.id.Contest_ListView_contest);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //navigationView.setNavigationItemSelectedListener(this);
        //유저 네비게이션바
        HttpClient a= new HttpClient();
        Log.i("결과",a.HttpClient("Web_basket","NaviTeamInfo_Player.jsp","park123"));
        preferences = getSharedPreferences("trophy", MODE_PRIVATE);
        Pk = preferences.getString("Pk", ".");
        //네비게이션 메뉴 선언 및 연결
        final View aa = navigationView.inflateHeaderView(R.layout.layout_navigationbar);
        Main_Navigation_ImageView_Profile = (ImageView)aa.findViewById(R.id.Main_Navigation_ImageView_Profile);
        final TextView Main_Navigation_TextView_Name = (TextView)aa.findViewById(R.id.Main_Navigation_TextView_Name);
        final TextView Main_Navigation_TextView_Team = (TextView)aa.findViewById(R.id.Main_Navigation_TextView_Team);
        final Button Main_Navigation_Button_SportChoice = (Button)aa.findViewById(R.id.Main_Navigation_Button_SportChoice);
        final Button Main_Navigation_Button_TeamMake = (Button) aa.findViewById(R.id.Main_Navigation_Button_TeamMake);
        final Button Main_Navigation_Button_TeamManager = (Button) aa.findViewById(R.id.Main_Navigation_Button_TeamManager);
        final Button Main_Navigation_Button_TeamSearch = (Button) aa.findViewById(R.id.Main_Navigation_Button_TeamSearch);
        final Button Main_Navigation_Button_LastContest = (Button) aa.findViewById(R.id.Main_Navigation_Button_LastContest);
        final Button Main_Navigation_Button_Notice = (Button)aa.findViewById(R.id.Main_Navigation_Button_Notice);
        final Button Main_Navigation_Button_Suggest = (Button) aa.findViewById(R.id.Main_Navigation_Button_Suggest);
        final Button Main_Navigation_Button_Setting = (Button)aa.findViewById(R.id.Main_Navigation_Button_Setting);
        final Button Main_Navigation_Button_Logout = (Button)aa.findViewById(R.id.Main_Navigation_Button_Logout);
        final Button Main_Navigation_Button_Change_PersonalInfo = (Button)aa.findViewById(R.id.Main_Navigation_Button_Change_PersonalInfo);

        if(Pk.equals("") || Pk.equals(".")) { ///////////////////////비로그인시
            Glide.with(MainActivity.this).load(R.drawable.profile_basic_image).diskCacheStrategy(DiskCacheStrategy.NONE).bitmapTransform(new CropCircleTransformation(Glide.get(MainActivity.this).getBitmapPool()))
                    .skipMemoryCache(true)
                    .into(Main_Navigation_ImageView_Profile);

            Main_Navigation_TextView_Name.setText("로그인을 해주세요");
            Main_Navigation_TextView_Team.setVisibility(View.GONE);
            Main_Navigation_Button_Logout.setVisibility(View.GONE);
            Main_Navigation_Button_TeamMake.setVisibility(View.GONE);
            Main_Navigation_Button_TeamManager.setVisibility(View.GONE);

            Main_Navigation_ImageView_Profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent_Login = new Intent(MainActivity.this, Login.class);
                    startActivity(intent_Login);
                    finish();
                }
            });
        } else { ///////////////////////////로그인시
            HttpClient user= new HttpClient();
            String result1 =user.HttpClient("Trophy_part1","User.jsp",Pk);
            Log.i("결과",result1);
            parseredData_user =  jsonParserList_User(result1);
            Name = parseredData_user[0][0];
            Team = parseredData_user[0][1];
            Profile = parseredData_user[0][2];
            //프로필 관리
            try{
                String Profile1 = URLEncoder.encode(Profile, "utf-8");
                Log.i("Profile1 : ", Profile1);
                if(Profile1.equals(".")){
                    Glide.with(MainActivity.this).load(R.drawable.profile_basic_image).diskCacheStrategy(DiskCacheStrategy.NONE).bitmapTransform(new CropCircleTransformation(Glide.get(MainActivity.this).getBitmapPool()))
                            .skipMemoryCache(true)
                            .into(Main_Navigation_ImageView_Profile);
                }
                else{
                    Glide.with(MainActivity.this).load("http://210.122.7.193:8080/Trophy_img/profile/"+Pk+".jpg") .diskCacheStrategy(DiskCacheStrategy.NONE).bitmapTransform(new CropCircleTransformation(Glide.get(MainActivity.this).getBitmapPool()))
                            .skipMemoryCache(true)
                            .into(Main_Navigation_ImageView_Profile);
                }
            }
            catch (UnsupportedEncodingException e){

            }
            Main_Navigation_TextView_Name.setText(Name);
            Main_Navigation_TextView_Team.setText(Team);

            Main_Navigation_ImageView_Profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Profile.equals(".")) {
                        HttpClient user = new HttpClient();
                        String result = user.HttpClient("Trophy_part2", "Profile_Image.jsp", Pk, Pk);
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
                        aDialog.setTitle("이미지 변경");
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
                                user.HttpClient("Trophy_part2", "Profile_Image.jsp", Pk, ".");
//                            Main_Navigation_ImageView_Profile.setImageResource(R.drawable.profile_basic_image);
                                ad.dismiss();
                                Profile = ".";
                            }
                        });
                        Layout_CustomDialog_Album_AlbumImage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                HttpClient user = new HttpClient();
                                String result = user.HttpClient("Trophy_part2", "Profile_Image.jsp", Pk,Pk);
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
            });
        }

        // 개인정보 수정
        Main_Navigation_Button_Change_PersonalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Pk.equals(".")) {
                    Snackbar.make(view, "로그인을 해주세요", Snackbar.LENGTH_LONG)
                            .show();
                }else {
                    Intent intent = new Intent(MainActivity.this, ChangePersonalInfoActivity.class);
                    intent.putExtra("TeamName", Team);
                    startActivity(intent);
                }
            }
        });


        //스포츠 버튼 이미지 변경
        preferences = getSharedPreferences("trophy", MODE_PRIVATE);
        String sport = preferences.getString("sportType", "");
        if (sport.equals("basketball")) {
            Main_Navigation_Button_SportChoice.setBackgroundResource(R.drawable.basketball_a);
        } else if (sport.equals("baseball")) {
            Main_Navigation_Button_SportChoice.setBackgroundResource(R.drawable.baseball_a);
        } else if (sport.equals("Coach")) {
            Main_Navigation_Button_SportChoice.setBackgroundResource(R.drawable.badminton_a);
        } else if (sport.equals("balling")) {
            Main_Navigation_Button_SportChoice.setBackgroundResource(R.drawable.balling_a);
        } else if (sport.equals("biking")) {
            Main_Navigation_Button_SportChoice.setBackgroundResource(R.drawable.biking_a);
        } else if (sport.equals("soccer")) {
            Main_Navigation_Button_SportChoice.setBackgroundResource(R.drawable.soccer_a);
        }



        //스포츠 변경 버튼 이벤트
        Main_Navigation_Button_SportChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preferences = getSharedPreferences("trophy", MODE_PRIVATE);
                editor = preferences.edit();
                editor.remove("sportType");
                editor.commit();

                Intent intent_SportChoice = new Intent(MainActivity.this, SportChoiceActivity.class);
                startActivity(intent_SportChoice);
                finish();
            }
        });
        //팀 만들기 이벤트
        Main_Navigation_Button_TeamMake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HttpClient http_teammake= new HttpClient();
                String result1 =http_teammake.HttpClient("Trophy_part1","TeamMake_Check.jsp",Pk);
                parseredData_teammake =  jsonParserList_TeamMake(result1);
                if(parseredData_teammake[0][0].equals("succed")){
                    Intent intent_TeamMake = new Intent(MainActivity.this, TeamMake.class);
                    intent_TeamMake.putExtra("Pk", Pk);
                    startActivity(intent_TeamMake);
                    overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                }
                else if(parseredData_teammake[0][0].equals("already")){
                    Snackbar.make(view, "팀에 이미 가입 중 입니다.",Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        Main_Navigation_Button_TeamSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_TeamSearch = new Intent(MainActivity.this, TeamSearch.class);
                intent_TeamSearch.putExtra("Pk", Pk);
                startActivity(intent_TeamSearch);
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
            }
        });
        Main_Navigation_Button_LastContest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_LastContest = new Intent(MainActivity.this, Last_Contest.class);
                startActivity(intent_LastContest);
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
            }
        });
        Main_Navigation_Button_TeamManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_TeamSearch = new Intent(MainActivity.this, TeamManager.class);
                intent_TeamSearch.putExtra("TeamName", Team);
                startActivity(intent_TeamSearch);
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
            }
        });
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
        //리스트뷰
        HttpClient ContestHttp = new HttpClient();
        String result = ContestHttp.HttpClient("Trophy_part3", "Contest_Customlist.jsp");
        String[][] ContestsParsedList = jsonParserList_getContestList(result);

        ArrayList<Contests_Customlist_MyData> Contests_Customlist_MyData;
        Contests_Customlist_MyData = new ArrayList<Contests_Customlist_MyData>();
        for (int i = 0; i < ContestsParsedList.length; i++) {
            Contests_Customlist_MyData.add(new Contests_Customlist_MyData(ContestsParsedList[i][0], ContestsParsedList[i][1],
                    ContestsParsedList[i][2], ContestsParsedList[i][3], ContestsParsedList[i][4], ContestsParsedList[i][5],
                    ContestsParsedList[i][6], ContestsParsedList[i][7], ContestsParsedList[i][8], ContestsParsedList[i][9],
                    ContestsParsedList[i][10], ContestsParsedList[i][11], ContestsParsedList[i][12],this, ContestsParsedList[i][13]));
        }
        Contests_Customlist_Adapter Adapter = new Contests_Customlist_Adapter(this, Contests_Customlist_MyData);
        listView.setAdapter(Adapter);


    }

    private String[][] jsonParserList_getContestList(String pRecvServerPage) {
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try {
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jarr = json.getJSONArray("List");

            String[] jsonName = {"_Pk", "_Title", "_Image", "_currentNum", "_maxNum", "_Payment", "_Host", "_Management", "_Support", "_ContestDate", "_RecruitStartDate", "_RecruitFinishDate", "_DetailInfo", "_Place"};
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
    public String[][] jsonParserList_User(String pRecvServerPage){
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try{
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");
            String[] jsonName = {"msg1","msg2","msg3"};
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
    public String[][] jsonParserList_TeamMake(String pRecvServerPage){
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

}
