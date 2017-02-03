package trophy.projetc2.Navigation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListViewCompat;
import android.util.Log;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import trophy.projetc2.Http.HttpClient;
import trophy.projetc2.R;

/**
 * Created by 박효근 on 2016-12-01.
 */

public class Notice extends AppCompatActivity {

    ExpandableListView Notice_ListView;
    Notice_Adapter Notice_Adapter;
    ArrayList<Notice_Setting> Notice_arrData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_notice);

        Notice_ListView = (ExpandableListView)findViewById(R.id.Notice_ListView);


        HttpClient ContestHttp = new HttpClient();
        String result = ContestHttp.HttpClient("Trophy_part2", "notice_download.jsp");
        String[][] ContestsParsedList = jsonParserList_getNotice(result);

        Notice_arrData = new ArrayList<Notice_Setting>();
        for (int i = 0; i < ContestsParsedList.length; i++) {
            Notice_arrData.add(new Notice_Setting(ContestsParsedList[i][0], ContestsParsedList[i][1], ContestsParsedList[i][2]));
        }
        Notice_Adapter = new Notice_Adapter(this, Notice_arrData);
        Notice_ListView.setAdapter(Notice_Adapter);
    }


    private String[][] jsonParserList_getNotice(String pRecvServerPage) {
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try {
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jarr = json.getJSONArray("List");


            String[] jsonName = {"notice_num", "notice_title", "notice_data"};
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
}
