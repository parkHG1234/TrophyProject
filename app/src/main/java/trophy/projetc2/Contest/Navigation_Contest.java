package trophy.projetc2.Contest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import trophy.projetc2.Contest.Navigation_Contest_Customlist_Adapter;
import trophy.projetc2.Contest.Navigation_Contest_Customlist_MyData;
import trophy.projetc2.Http.HttpClient;
import trophy.projetc2.R;

/**
 * Created by 박효근 on 2017-03-24.
 */

public class Navigation_Contest extends AppCompatActivity {
    ImageView Navigation_Contest_ImageView_Back;
    ListView Navigation_Contest_ListView_List;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_navigation_contest);
        Navigation_Contest_ImageView_Back = (ImageView)findViewById(R.id.Navigation_Contest_ImageView_Back);
        Navigation_Contest_ListView_List = (ListView)findViewById(R.id.Navigation_Contest_ListView_List);
        //리스트뷰
        HttpClient ContestHttp = new HttpClient();
        String result = ContestHttp.HttpClient("Trophy_part1", "Contest.jsp");
        String[][] ContestsParsedList = jsonParserList_getContestList(result);

        ArrayList<Navigation_Contest_Customlist_MyData> Navigation_Contest_Customlist_MyData;
        Navigation_Contest_Customlist_MyData = new ArrayList<Navigation_Contest_Customlist_MyData>();
        for (int i = 0; i < ContestsParsedList.length; i++) {
            Navigation_Contest_Customlist_MyData.add(new Navigation_Contest_Customlist_MyData(ContestsParsedList[i][0], ContestsParsedList[i][1],
                    ContestsParsedList[i][2], ContestsParsedList[i][3], ContestsParsedList[i][4], ContestsParsedList[i][5],
                    ContestsParsedList[i][6], ContestsParsedList[i][7], ContestsParsedList[i][8], ContestsParsedList[i][9],
                    ContestsParsedList[i][10], ContestsParsedList[i][11], ContestsParsedList[i][12], this, ContestsParsedList[i][13]));
        }
        Log.i("length", Integer.toString(ContestsParsedList.length));
        Navigation_Contest_Customlist_Adapter Adapter = new Navigation_Contest_Customlist_Adapter(this, Navigation_Contest_Customlist_MyData);
        Navigation_Contest_ListView_List.setAdapter(Adapter);
        Navigation_Contest_ImageView_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
            }
        });
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
    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
    }
}
