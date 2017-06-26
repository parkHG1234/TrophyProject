package trophy.projetc2.Navigation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListViewCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import trophy.projetc2.Http.HttpClient;
import trophy.projetc2.R;

/**
 * Created by 박효근 on 2016-12-01.
 */

public class Notice extends AppCompatActivity {

    TextView Notice_TextView_MainTitle, Notice_TextView_MainContent;
    ListView Notice_ListView;
    ImageView Notice_ImageView_ListTop, Notice_ImageView_ListBottom, Notice_ImageVIew_Back,Notice_ImageView_Image;
    LinearLayout Notice_ImageView_ImageBack;
    Notice_Adapter Notice_Adapter;
    ArrayList<Notice_Setting> Notice_arrData;
    String[][] ContestsParsedList;
    static int Notice_Num_Position = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_notice);
        Notice_ImageVIew_Back = (ImageView)findViewById(R.id.Notice_ImageVIew_Back);
        Notice_TextView_MainTitle = (TextView)findViewById(R.id.Notice_TextView_MainTitle);
        Notice_ListView = (ListView) findViewById(R.id.Notice_ListView);
        Notice_TextView_MainContent = (TextView)findViewById(R.id.Notice_TextView_MainContent);
        Notice_ImageView_ListTop = (ImageView) findViewById(R.id.Notice_ImageView_ListTop);
        Notice_ImageView_ListBottom = (ImageView) findViewById(R.id.Notice_ImageView_ListBottom);
        Notice_ImageView_Image = (ImageView)findViewById(R.id.Notice_ImageView_Image);
        Notice_ImageView_ImageBack = (LinearLayout)findViewById(R.id.Notice_ImageView_ImageBack);

        HttpClient ContestHttp = new HttpClient();
        String result = ContestHttp.HttpClient("Trophy_part1", "Notice.jsp");
        ContestsParsedList = jsonParserList_getNotice(result);

        Notice_arrData = new ArrayList<Notice_Setting>();
        for (int i = 0; i < ContestsParsedList.length; i++) {
            Notice_arrData.add(new Notice_Setting(ContestsParsedList[i][0], ContestsParsedList[i][1], ContestsParsedList[i][2],ContestsParsedList[i][3]));
        }

        Notice_Adapter = new Notice_Adapter(this, Notice_arrData);
        Notice_ListView.setAdapter(Notice_Adapter);
        Notice_TextView_MainTitle.setText(ContestsParsedList[0][1]);
        Notice_TextView_MainContent.setText(ContestsParsedList[0][2]);
        if(ContestsParsedList[0][3].equals(".")) {
//            Glide.with(Notice.this).load(R.drawable.back_white).diskCacheStrategy(DiskCacheStrategy.NONE)
//                    .skipMemoryCache(true)
//                    .into(Notice_ImageView_Image);
            Notice_ImageView_ImageBack.setVisibility(View.GONE);
        }else {
            Notice_ImageView_ImageBack.setVisibility(View.VISIBLE);
            Glide.with(Notice.this).load("http://210.122.7.193:8080/Trophy_img/notice/1.jpg")
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(Notice_ImageView_Image);
        }
        Notice_ListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Notice_TextView_MainTitle.setText(ContestsParsedList[i][1]);
                Notice_TextView_MainContent.setText(ContestsParsedList[i][2]);
                view.setBackgroundColor(getResources().getColor(R.color.main1color));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        Notice_ImageView_ListTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Notice_Num_Position == 0){

                }else{
                    Notice_Num_Position--;
                    Notice_TextView_MainTitle.setText(ContestsParsedList[Notice_Num_Position][1]);
                    Notice_TextView_MainContent.setText(ContestsParsedList[Notice_Num_Position][2]);
                    if(ContestsParsedList[Notice_Num_Position][3].equals(".")) {
//                        Glide.with(Notice.this).load(R.drawable.back_white).diskCacheStrategy(DiskCacheStrategy.NONE)
//                                .skipMemoryCache(true)
//                                .into(Notice_ImageView_Image);
                        Notice_ImageView_ImageBack.setVisibility(View.GONE);
                    }else {
                        Notice_ImageView_ImageBack.setVisibility(View.VISIBLE);
                        Glide.with(Notice.this).load("http://210.122.7.193:8080/Trophy_img/notice/"+ContestsParsedList[Notice_Num_Position][3]+".jpg")
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true)
                                .into(Notice_ImageView_Image);
                    }
                }
            }
        });
        Notice_ImageView_ListBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Notice_Num_Position+1 == ContestsParsedList.length){

                }else{
                    Notice_Num_Position++;
                    if(ContestsParsedList[Notice_Num_Position][3].equals(".")) {
//                        Glide.with(Notice.this).load(R.drawable.back_white).diskCacheStrategy(DiskCacheStrategy.NONE)
//                                .skipMemoryCache(true)
//                                .into(Notice_ImageView_Image);
                        Notice_ImageView_ImageBack.setVisibility(View.GONE);
                    }else {
                        Notice_ImageView_ImageBack.setVisibility(View.VISIBLE);
                        Glide.with(Notice.this).load("http://210.122.7.193:8080/Trophy_img/notice/"+ContestsParsedList[Notice_Num_Position][3]+".jpg")
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true)
                                .into(Notice_ImageView_Image);
                    }
                    Notice_TextView_MainTitle.setText(ContestsParsedList[Notice_Num_Position][1]);
                    Notice_TextView_MainContent.setText(ContestsParsedList[Notice_Num_Position][2]);
                    Notice_Adapter.notifyDataSetChanged();
                    Notice_ListView.setSelection(Notice_Num_Position);
                }

            }
        });
        Notice_ImageVIew_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
            }
        });
    }


    private String[][] jsonParserList_getNotice(String pRecvServerPage) {
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try {
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jarr = json.getJSONArray("List");


            String[] jsonName = {"notice_num", "notice_title", "notice_data","notice_image"};
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
    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
    }
}
