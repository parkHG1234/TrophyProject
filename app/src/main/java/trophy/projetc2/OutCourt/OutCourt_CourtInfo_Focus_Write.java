package trophy.projetc2.OutCourt;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tsengvn.typekit.TypekitContextWrapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import trophy.projetc2.Http.HttpClient;
import trophy.projetc2.R;

import static trophy.projetc2.OutCourt.OutCourt_CourtInfo_Focus.imageH;
import static trophy.projetc2.OutCourt.OutCourt_CourtInfo_Focus_Write_Camera.Image_width;
import static trophy.projetc2.OutCourt.OutCourt_CourtInfo_Focus_Write_Camera.Image_Height;
import static trophy.projetc2.OutCourt.OutCourt_CourtInfo_Focus_Write_Camera.rout;

/**
 * Created by 박효근 on 2017-05-06.
 */

public class OutCourt_CourtInfo_Focus_Write  extends AppCompatActivity {
    TextView OutCourt_CourtInfo_Focus_Write_TextView_Back;
    TextView OutCourt_CourtInfo_Focus_Write_TextView_Write;
    ImageView OutCourt_CourtInfo_Focus_Write_ImageView_Profile;
    ImageView OutCourt_CourtInfo_Focus_Write_Image_Camera;
    TextView OutCourt_CourtInfo_Focus_Write_TextView_Name;
    EditText OutCourt_CourtInfo_Focus_Write_EditText_Content;
    LinearLayout OutCourt_CourtInfo_Focus_Write_Button_Camera;
    ImageView OutCourt_CourtInfo_Focus_Write_Image_Cancel;
    String CourtName, User_Pk, Name, Profile, Court_Pk;
    String[][] parseredData_user, parseredData_write;
    String strCurYear, strCurMonth, strCurDay, strCurHour,strCurMinute, strCurToday, strCurTime;
    String ImageURL = null, ImageFile = null;Intent dataIntent = null;boolean flag = false;
    String filename="";String content="";
    int ratio = 0; int ratio_Height=0;
    int Display_Weight=0, Display_Height=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_navigation_outcourt_courtinfo_focus_write);
        Intent intent1 = getIntent();
        User_Pk = intent1.getStringExtra("User_Pk");
        Court_Pk = intent1.getStringExtra("Court_Pk");
        currentTime();
        Display display = ((WindowManager)getSystemService(WINDOW_SERVICE)).getDefaultDisplay() ;
        Display_Weight = display.getWidth();
        Display_Height = display.getHeight();

        OutCourt_CourtInfo_Focus_Write_TextView_Write = (TextView)findViewById(R.id.OutCourt_CourtInfo_Focus_Write_TextView_Write);
        OutCourt_CourtInfo_Focus_Write_EditText_Content = (EditText) findViewById(R.id.OutCourt_CourtInfo_Focus_Write_EditText_Content);
        OutCourt_CourtInfo_Focus_Write_TextView_Back = (TextView)findViewById(R.id.OutCourt_CourtInfo_Focus_Write_TextView_Back);
        OutCourt_CourtInfo_Focus_Write_ImageView_Profile = (ImageView) findViewById(R.id.OutCourt_CourtInfo_Focus_Write_ImageView_Profile);
        OutCourt_CourtInfo_Focus_Write_TextView_Name = (TextView)findViewById(R.id.OutCourt_CourtInfo_Focus_Write_TextView_Name);
        OutCourt_CourtInfo_Focus_Write_Button_Camera = (LinearLayout)findViewById(R.id.OutCourt_CourtInfo_Focus_Write_Button_Camera);
        OutCourt_CourtInfo_Focus_Write_Image_Camera = (ImageView)findViewById(R.id.OutCourt_CourtInfo_Focus_Write_Image_Camera);
        OutCourt_CourtInfo_Focus_Write_Image_Cancel = (ImageView)findViewById(R.id.OutCourt_CourtInfo_Focus_Write_Image_Cancel);

//        int a = Integer.parseInt(String.valueOf(Math.round(imageH)));
//        OutCourt_CourtInfo_Focus_Write_Image_Camera.getLayoutParams().height = a;
        OutCourt_CourtInfo_Focus_Write_Image_Camera.setVisibility(View.GONE);
        OutCourt_CourtInfo_Focus_Write_Image_Cancel.setVisibility(View.GONE);

        HttpClient user = new HttpClient();
        String result1 = user.HttpClient("Trophy_part1", "User.jsp", User_Pk);
        parseredData_user = jsonParserList_User(result1);
        Name = parseredData_user[0][0];
        Profile = parseredData_user[0][2];
        //프로필 관리
        try {
            String Profile1 = URLEncoder.encode(Profile, "utf-8");
            Log.i("Profile1 : ", Profile1);
            if (Profile1.equals(".")) {
                Glide.with(OutCourt_CourtInfo_Focus_Write.this).load(R.drawable.profile_basic_image).diskCacheStrategy(DiskCacheStrategy.NONE).bitmapTransform(new CropCircleTransformation(Glide.get(OutCourt_CourtInfo_Focus_Write.this).getBitmapPool()))
                        .skipMemoryCache(true)
                        .into(OutCourt_CourtInfo_Focus_Write_ImageView_Profile);
            } else {
                Glide.with(OutCourt_CourtInfo_Focus_Write.this).load("http://210.122.7.193:8080/Trophy_img/profile/" + Profile1 + ".jpg").diskCacheStrategy(DiskCacheStrategy.NONE).bitmapTransform(new CropCircleTransformation(Glide.get(OutCourt_CourtInfo_Focus_Write.this).getBitmapPool()))
                        .skipMemoryCache(true)
                        .into(OutCourt_CourtInfo_Focus_Write_ImageView_Profile);
            }
        } catch (UnsupportedEncodingException e) {

        }
        OutCourt_CourtInfo_Focus_Write_TextView_Name.setText(Name);

        OutCourt_CourtInfo_Focus_Write_EditText_Content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(OutCourt_CourtInfo_Focus_Write_EditText_Content.getText().toString().equals("")){
                    OutCourt_CourtInfo_Focus_Write_TextView_Write.setTextColor(getResources().getColor(R.color.DarkGray));
                }
                else{
                    OutCourt_CourtInfo_Focus_Write_TextView_Write.setTextColor(getResources().getColor(R.color.main1color));
                    content = OutCourt_CourtInfo_Focus_Write_EditText_Content.getText().toString();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        OutCourt_CourtInfo_Focus_Write_TextView_Write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(OutCourt_CourtInfo_Focus_Write_EditText_Content.getText().toString().equals("")){
                    OutCourt_CourtInfo_Focus_Write_TextView_Write.setEnabled(false);
                }
                else{
                    write_input write_input = new write_input();
                    write_input.execute();

                }
            }
        });
        OutCourt_CourtInfo_Focus_Write_TextView_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.anim_slide_in_top, R.anim.anim_slide_out_bottom);
            }
        });
        OutCourt_CourtInfo_Focus_Write_Button_Camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OutCourt_CourtInfo_Focus_Write_Camera camera = new OutCourt_CourtInfo_Focus_Write_Camera();
                camera.camera_ImageView(OutCourt_CourtInfo_Focus_Write_Image_Camera);
                Intent CameraIntent = new Intent(OutCourt_CourtInfo_Focus_Write.this, OutCourt_CourtInfo_Focus_Write_Camera.class);

                startActivityForResult(CameraIntent, 1);
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN );

            }
        });
        OutCourt_CourtInfo_Focus_Write_Image_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OutCourt_CourtInfo_Focus_Write_Image_Camera.setVisibility(View.GONE);
                OutCourt_CourtInfo_Focus_Write_Image_Cancel.setVisibility(View.GONE);
                flag = false;
            }
        });
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        this.dataIntent = data;
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {

                ImageURL = data.getStringExtra("ImageURL");
                ImageFile = data.getStringExtra("ImageFile");
                ImageDownload();
                flag = true;
            }
        }
    }
    public void ImageDownload() {
        try {
            FileInputStream in;
            BufferedInputStream buf;
            in = new FileInputStream(String.valueOf(ImageURL));
            buf = new BufferedInputStream(in);
            Bitmap orgImage = BitmapFactory.decodeFile(String.valueOf(ImageURL));
            Bitmap resize = Bitmap.createScaledBitmap(orgImage, 1080, 1080, true);

//            // 이미지를 상황에 맞게 회전시킨다
//            ExifInterface exif = new ExifInterface(ImageURL);
//            int exifOrientation = exif.getAttributeInt(
//                    ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
//            int exifDegree = exifOrientationToDegrees(exifOrientation);
//            resize = rotate(resize, exifDegree);
            ratio = Image_width / Display_Weight;
            ratio_Height = Image_Height / ratio;
            OutCourt_CourtInfo_Focus_Write_Image_Camera.getLayoutParams().height = ratio_Height;
            OutCourt_CourtInfo_Focus_Write_Image_Camera.setVisibility(View.VISIBLE);
            OutCourt_CourtInfo_Focus_Write_Image_Camera.setImageBitmap(orgImage);
            OutCourt_CourtInfo_Focus_Write_Image_Cancel.setVisibility(View.VISIBLE);
        }
        catch (Exception e) {

        }
    }
    public int exifOrientationToDegrees(int exifOrientation)
    {
        return rout;
    }

    /**
     * 이미지를 회전시킵니다.
     *
     * @param bitmap 비트맵 이미지
     * @param degrees 회전 각도
     * @return 회전된 이미지
     */
    public Bitmap rotate(Bitmap bitmap, int degrees)
    {
        if(degrees != 0 && bitmap != null)
        {
            Matrix m = new Matrix();
            m.setRotate(degrees, (float) bitmap.getWidth() / 2,
                    (float) bitmap.getHeight() / 2);

            try
            {
                Bitmap converted = Bitmap.createBitmap(bitmap, 0, 0,
                        bitmap.getWidth(), bitmap.getHeight(), m, true);
                if(bitmap != converted)
                {
                    bitmap.recycle();
                    bitmap = converted;
                }
            }
            catch(OutOfMemoryError ex)
            {
                // 메모리가 부족하여 회전을 시키지 못할 경우 그냥 원본을 반환합니다.
            }
        }
        return bitmap;
    }
    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.anim_slide_in_top, R.anim.anim_slide_out_bottom);
    }
    public String[][] jsonParserList_User(String pRecvServerPage) {
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try {
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");
            String[] jsonName = {"msg1", "msg2", "msg3", "msg4", "msg5"};
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
    public String[][] jsonParserList_Write(String pRecvServerPage) {
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try {
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");
            String[] jsonName = {"msg1","msg2"};
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
        strCurYear = CurYearFormat.format(date);
        strCurYear = CurYearFormat.format(date);
        strCurMonth = CurMonthFormat.format(date);
        strCurDay = CurDayFormat.format(date);
        strCurHour = CurHourFormat.format(date);
        strCurMinute = CurMinuteFormat.format(date);
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
            dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + URLEncoder.encode(filename, "utf-8") + ".jpg" + "\"" + lineEnd);
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
    public class write_input extends AsyncTask<String, Void, String> {
        ProgressDialog asyncDialog = new ProgressDialog(OutCourt_CourtInfo_Focus_Write.this);
        String[][] parsedData;

        @Override
        protected void onPreExecute() {
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage("게시 중입니다..");
            // show dialog
            asyncDialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                HttpClient user = new HttpClient();
                String result1 = user.HttpClient("Trophy_part1", "OutCourt_Focus_Write.jsp", User_Pk, Court_Pk, strCurToday, content, strCurYear);
                parseredData_write = jsonParserList_Write(result1);
                if(parseredData_write[0][0].equals("succed")){
                    filename = parseredData_write[0][1];
                    if(flag){
                        //파일 업로드 시작!
                        String urlString = "http://210.122.7.193:8080/Trophy_part1/Content_Image_Upload.jsp";
                        HttpFileUpload(urlString, "", ImageURL);
                        HttpClient image = new HttpClient();
                        image.HttpClient("Trophy_part1", "OutCourt_Focus_Write_Image.jsp", filename,Integer.toString(Image_width),Integer.toString(Image_Height));
                    }else{

                    }
                    finish();
                    overridePendingTransition(R.anim.anim_slide_in_top, R.anim.anim_slide_out_bottom);
                    flag = false;
                }
                else{
                    Snackbar.make(getCurrentFocus(),"잠시 후 다시 시도해주시기 바랍니다.", Snackbar.LENGTH_SHORT).show();
                }
                return "succed";
            } catch (Exception e) {
                e.printStackTrace();
                return "failed";
            }
        }

        @Override
        protected void onPostExecute(String result) {

            asyncDialog.dismiss();
            super.onPostExecute(result);
        }
    }
}
