package trophy.projetc2.Navigation;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import trophy.projetc2.Get_Spinner_Si;
import trophy.projetc2.Http.HttpClient;
import trophy.projetc2.R;

import static trophy.projetc2.Navigation.TeamManager.TeamManager_TeamName;


/**
 * Created by 박효근 on 2017-01-10.
 */

public class TeamManager_TeamIntroduce extends Fragment {
    private TextView TeamManager_TeamIntro_TextView_TeamName;
    private Spinner TeamManager_TeamIntro_Spinner_Do;
    private Spinner TeamManager_TeamIntro_Spinner_Si;
    private EditText TeamManager_TeamIntro_EditText_HomeCourt;
    private EditText TeamManager_TeamIntro_EditText_Introduce;
    private Button TeamManager_TeamIntro_Button_Modify;
    private ImageView TeamManager_TeamIntro_ImageView_Emblem;
    private ImageView TeamManager_TeamIntro_ImageView_Image1;
    private ImageView TeamManager_TeamIntro_ImageView_Image2;
    private ImageView TeamManager_TeamIntro_ImageView_Image3;
    private ArrayAdapter<CharSequence> adspin1, adspin2;
    private trophy.projetc2.Get_Spinner_Si Get_Spinner_Si;
    private JSONObject json;
    private JSONArray jArr;
    private String[][] parseredData;
    private String TeamIntro_Do;
    private String TeamIntro_Si;
    private String TeamIntro_HomeCourt;
    private String TeamIntro_Introduce;
    private static String FileName;
    private int flag;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.layout_navigation_teammanager_teamintroduce, container, false);

        TeamManager_TeamIntro_TextView_TeamName = (TextView)rootView.findViewById(R.id.TeamManager_TeamIntro_TextView_TeamName);
        TeamManager_TeamIntro_Spinner_Do = (Spinner) rootView.findViewById(R.id.TeamManager_TeamIntro_Spinner_Do);
        TeamManager_TeamIntro_Spinner_Si = (Spinner) rootView.findViewById(R.id.TeamManager_TeamIntro_Spinner_Si);
        TeamManager_TeamIntro_EditText_HomeCourt = (EditText) rootView.findViewById(R.id.TeamManager_TeamIntro_EditText_HomeCourt);
        TeamManager_TeamIntro_EditText_Introduce = (EditText) rootView.findViewById(R.id.TeamManager_TeamIntro_EditText_Introduce);
        TeamManager_TeamIntro_Button_Modify = (Button) rootView.findViewById(R.id.TeamManager_TeamIntro_Button_Modify);
        TeamManager_TeamIntro_ImageView_Emblem = (ImageView) rootView.findViewById(R.id.TeamManager_TeamIntro_ImageView_Emblem);
        TeamManager_TeamIntro_ImageView_Image1 = (ImageView) rootView.findViewById(R.id.TeamManager_TeamIntro_ImageView_Image1);
        TeamManager_TeamIntro_ImageView_Image2 = (ImageView) rootView.findViewById(R.id.TeamManager_TeamIntro_ImageView_Image2);
        TeamManager_TeamIntro_ImageView_Image3 = (ImageView) rootView.findViewById(R.id.TeamManager_TeamIntro_ImageView_Image3);

        HttpClient ContestHttp = new HttpClient();
        String result = ContestHttp.HttpClient("Trophy_part2", "TeamManager_TeamIntroduce.jsp", TeamManager_TeamName.toString());
        String[][] ContestsParsedList = jsonParserList_TeamIntroduce(result);

        TeamManager_TeamIntro_TextView_TeamName.setText("  "+TeamManager_TeamName);
        adspin1 = ArrayAdapter.createFromResource(getContext(), R.array.spinner_do, android.R.layout.simple_spinner_item);
        adspin1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        TeamManager_TeamIntro_Spinner_Do.setAdapter(adspin1);
        adspin2 = ArrayAdapter.createFromResource(getContext(), R.array.spinner_do_seoul, android.R.layout.simple_spinner_item);
        adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        TeamManager_TeamIntro_Spinner_Si.setAdapter(adspin2);
        setSpinText(TeamManager_TeamIntro_Spinner_Do, parseredData[0][1]);
        TeamManager_TeamIntro_EditText_HomeCourt.setText(parseredData[0][3]);
        TeamManager_TeamIntro_EditText_Introduce.setText(parseredData[0][4]);

        if(parseredData[0][5].equals(".")) {
            Glide.with(TeamManager_TeamIntroduce.this).load(R.drawable.image_plus).diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(TeamManager_TeamIntro_ImageView_Emblem);
        }else {
            Glide.with(getContext()).load("http://210.122.7.193:8080/Trophy_img/team/" + parseredData[0][5] + ".jpg").bitmapTransform(new CropCircleTransformation(Glide.get(getContext()).getBitmapPool()))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(TeamManager_TeamIntro_ImageView_Emblem);
        }
        if(parseredData[0][6].equals(".")) {
            Glide.with(TeamManager_TeamIntroduce.this).load(R.drawable.image_plus).diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(TeamManager_TeamIntro_ImageView_Image1);
        }else{
            Glide.with(getContext()).load("http://210.122.7.193:8080/Trophy_img/team/" + parseredData[0][8] + ".jpg")
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(TeamManager_TeamIntro_ImageView_Image1);
        }
        if(parseredData[0][7].equals(".")){
            Glide.with(getContext()).load(R.drawable.image_plus)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(TeamManager_TeamIntro_ImageView_Image2);
        }else{
            Glide.with(getContext()).load("http://210.122.7.193:8080/Trophy_img/team/" + parseredData[0][7] + ".jpg")
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(TeamManager_TeamIntro_ImageView_Image2);
        }
        if(parseredData[0][8].equals(".")){
            Glide.with(getContext()).load(R.drawable.image_plus)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
        .into(TeamManager_TeamIntro_ImageView_Image3);
    }else{
        Glide.with(getContext()).load("http://210.122.7.193:8080/Trophy_img/team/" + parseredData[0][8] + ".jpg")
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(TeamManager_TeamIntro_ImageView_Image3);
    }

        TeamManager_TeamIntro_Button_Modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TeamIntro_HomeCourt = TeamManager_TeamIntro_EditText_HomeCourt.getText().toString();
                TeamIntro_Introduce = TeamManager_TeamIntro_EditText_Introduce.getText().toString();

                HttpClient ContestHttp = new HttpClient();
                String result = ContestHttp.HttpClient("Trophy_part2", "TeamManager_TeamIntroduce_Upload.jsp", TeamManager_TeamName.toString(), TeamIntro_Do.toString(), TeamIntro_Si.toString(), TeamIntro_HomeCourt.toString(), TeamIntro_Introduce.toString());
                Snackbar.make(v, "팀 정보가 변경되었습니다.", Snackbar.LENGTH_SHORT).show();
            }
        });
        TeamManager_TeamIntro_Spinner_Do.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Get_Spinner_Si = new Get_Spinner_Si(getContext());
                adspin2 = Get_Spinner_Si.getAdapter(position);
                TeamManager_TeamIntro_Spinner_Si.setAdapter(adspin2);
                setSpinText(TeamManager_TeamIntro_Spinner_Si, parseredData[0][2]);
                TeamIntro_Do = TeamManager_TeamIntro_Spinner_Do.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        TeamManager_TeamIntro_Spinner_Si.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TeamIntro_Si = TeamManager_TeamIntro_Spinner_Si.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        TeamManager_TeamIntro_ImageView_Emblem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
                        Glide.with(TeamManager_TeamIntroduce.this).load(R.drawable.profile_basic_image).diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true)
                                .into(TeamManager_TeamIntro_ImageView_Emblem);
                        HttpClient user = new HttpClient();
                        user.HttpClient("Trophy_part2", "TeamManager_TeamIntroduce_Image.jsp", TeamManager_TeamName.toString(), ".", "Emblem");
                        TeamManager_TeamIntro_ImageView_Emblem.setImageResource(R.drawable.profile_basic_image);
                        ad.dismiss();
                    }
                });
                Layout_CustomDialog_Album_AlbumImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        HttpClient user = new HttpClient();
                        String result = user.HttpClient("Trophy_part2", "TeamManager_TeamIntroduce_Image.jsp", TeamManager_TeamName.toString(), TeamManager_TeamName.toString(), "Emblem");
                        //사진 읽어오기위한 uri 작성하기.
                        Uri uri = Uri.parse("content://media/external/images/media");
                        //무언가 보여달라는 암시적 인텐트 객체 생성하기.
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        //인텐트에 요청을 덛붙인다.
                        intent.setAction(Intent.ACTION_PICK);
                        //모든 이미지
                        intent.setType("image/*");
                        //결과값을 받아오는 액티비티를 실행한다.
                        FileName = TeamManager_TeamName.toString();
                        flag = 0;
                        startActivityForResult(intent, 0);
                        ad.dismiss();
                    }
                });

            }
        });
        TeamManager_TeamIntro_ImageView_Image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                        Glide.with(TeamManager_TeamIntroduce.this).load(R.drawable.profile_basic_image).diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true)
                                .into(TeamManager_TeamIntro_ImageView_Image1);
                        HttpClient user = new HttpClient();
                        user.HttpClient("Trophy_part2", "TeamManager_TeamIntroduce_Image.jsp", TeamManager_TeamName.toString(), ".", "Image1");
                        TeamManager_TeamIntro_ImageView_Image1.setImageResource(R.drawable.profile_basic_image);
                        ad.dismiss();
                    }
                });
                Layout_CustomDialog_Album_AlbumImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        HttpClient user = new HttpClient();
                        String result = user.HttpClient("Trophy_part2", "TeamManager_TeamIntroduce_Image.jsp", TeamManager_TeamName.toString(), TeamManager_TeamName.toString() + "1", "Image1");
                        //사진 읽어오기위한 uri 작성하기.
                        Uri uri = Uri.parse("content://media/external/images/media");
                        //무언가 보여달라는 암시적 인텐트 객체 생성하기.
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        //인텐트에 요청을 덛붙인다.
                        intent.setAction(Intent.ACTION_PICK);
                        //모든 이미지
                        intent.setType("image/*");
                        //결과값을 받아오는 액티비티를 실행한다.
                        FileName = TeamManager_TeamName.toString() + "1";
                        flag = 1;
                        startActivityForResult(intent, 0);
                        ad.dismiss();
                    }
                });
            }
        });
        TeamManager_TeamIntro_ImageView_Image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
                        Glide.with(TeamManager_TeamIntroduce.this).load(R.drawable.profile_basic_image).diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true)
                                .into(TeamManager_TeamIntro_ImageView_Image2);
                        HttpClient user = new HttpClient();
                        user.HttpClient("Trophy_part2", "TeamManager_TeamIntroduce_Image.jsp", TeamManager_TeamName.toString(), ".", "Image2");
                        TeamManager_TeamIntro_ImageView_Image2.setImageResource(R.drawable.profile_basic_image);
                        ad.dismiss();
                    }
                });
                Layout_CustomDialog_Album_AlbumImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        HttpClient user = new HttpClient();
                        String result = user.HttpClient("Trophy_part2", "TeamManager_TeamIntroduce_Image.jsp", TeamManager_TeamName.toString(), TeamManager_TeamName.toString() + "2", "Image2");
                        //사진 읽어오기위한 uri 작성하기.
                        Uri uri = Uri.parse("content://media/external/images/media");
                        //무언가 보여달라는 암시적 인텐트 객체 생성하기.
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        //인텐트에 요청을 덛붙인다.
                        intent.setAction(Intent.ACTION_PICK);
                        //모든 이미지
                        intent.setType("image/*");
                        //결과값을 받아오는 액티비티를 실행한다.
                        FileName = TeamManager_TeamName.toString() + "2";
                        flag = 2;
                        startActivityForResult(intent, 0);
                        ad.dismiss();
                    }
                });
            }
        });
        TeamManager_TeamIntro_ImageView_Image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
                        Glide.with(TeamManager_TeamIntroduce.this).load(R.drawable.profile_basic_image).diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true)
                                .into(TeamManager_TeamIntro_ImageView_Image3);
                        HttpClient user = new HttpClient();
                        user.HttpClient("Trophy_part2", "TeamManager_TeamIntroduce_Image.jsp", TeamManager_TeamName.toString(), ".", "Image3");
                        TeamManager_TeamIntro_ImageView_Image3.setImageResource(R.drawable.profile_basic_image);
                        ad.dismiss();
                    }
                });
                Layout_CustomDialog_Album_AlbumImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        HttpClient user = new HttpClient();
                        String result = user.HttpClient("Trophy_part2", "TeamManager_TeamIntroduce_Image.jsp", TeamManager_TeamName.toString(), TeamManager_TeamName.toString() + "3", "Image3");
                        //사진 읽어오기위한 uri 작성하기.
                        Uri uri = Uri.parse("content://media/external/images/media");
                        //무언가 보여달라는 암시적 인텐트 객체 생성하기.
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        //인텐트에 요청을 덛붙인다.
                        intent.setAction(Intent.ACTION_PICK);
                        //모든 이미지
                        intent.setType("image/*");
                        //결과값을 받아오는 액티비티를 실행한다.
                        FileName = TeamManager_TeamName.toString() + "3";
                        flag = 3;
                        startActivityForResult(intent, 0);
                        ad.dismiss();
                    }
                });
            }
        });
        return rootView;
    }

    public void setSpinText(Spinner spin, String text) {
        for (int i = 0; i < spin.getAdapter().getCount(); i++) {
            if (spin.getAdapter().getItem(i).toString().contains(text)) {
                spin.setSelection(i);
                break;
            }
        }
    }

    private String[][] jsonParserList_TeamIntroduce(String pRecvServerPage) {
        Log.i("TeamIntroduce에서 받은 내용", pRecvServerPage);
        try {
            json = new JSONObject(pRecvServerPage);
            jArr = json.getJSONArray("List");
            String[] jsonName = {"msg1", "msg2", "msg3", "msg4", "msg5", "msg6", "msg7", "msg8", "msg9"};
            parseredData = new String[jArr.length()][jsonName.length];
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

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        try {
            //인텐트에 데이터가 담겨 왔다면
            if (!intent.getData().equals(null)) {
                //해당경로의 이미지를 intent에 담긴 이미지 uri를 이용해서 Bitmap형태로 읽어온다.
                Bitmap selPhoto = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), intent.getData());
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
                urlString = "http://210.122.7.193:8080/Trophy_part2/TeamManager_TeamIntroduce_Image_Upload.jsp";
                //절대경로를 획득한다!!! 중요~
                Cursor c = getContext().getContentResolver().query(Uri.parse(selPhotoUri.toString()), null, null, null, null);
                c.moveToNext();
                //업로드할 파일의 절대경로 얻어오기("_data") 로 해도 된다.
                String absolutePath = c.getString(c.getColumnIndex(MediaStore.MediaColumns.DATA));
                Log.e("###파일의 절대 경로###", absolutePath);
                //파일 업로드 시작!
                HttpFileUpload(urlString, "", absolutePath);
                if (flag == 0) {
                    String En_Profile = URLEncoder.encode(FileName, "utf-8");
                    Glide.with(TeamManager_TeamIntroduce.this).load("http://210.122.7.193:8080/Trophy_img/team/" + En_Profile + ".jpg")
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true)
                            .into(TeamManager_TeamIntro_ImageView_Emblem);
                } else if (flag == 1) {
                    String En_Profile = URLEncoder.encode(FileName, "utf-8");
                    Glide.with(TeamManager_TeamIntroduce.this).load("http://210.122.7.193:8080/Trophy_img/team/" + En_Profile + ".jpg")
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true)
                            .into(TeamManager_TeamIntro_ImageView_Image1);
                } else if (flag == 2) {
                    String En_Profile = URLEncoder.encode(FileName, "utf-8");
                    Glide.with(TeamManager_TeamIntroduce.this).load("http://210.122.7.193:8080/Trophy_img/team/" + En_Profile + ".jpg")
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true)
                            .into(TeamManager_TeamIntro_ImageView_Image2);
                } else if (flag == 3) {
                    String En_Profile = URLEncoder.encode(FileName, "utf-8");
                    Glide.with(TeamManager_TeamIntroduce.this).load("http://210.122.7.193:8080/Trophy_img/team/" + En_Profile + ".jpg")
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true)
                            .into(TeamManager_TeamIntro_ImageView_Image3);
                }

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
            dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + URLEncoder.encode(FileName, "utf-8") + ".jpg" + "\"" + lineEnd);
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