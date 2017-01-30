package trophy.projetc2.Navigation;

import android.content.Intent;
import android.content.SearchRecentSuggestionsProvider;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import trophy.projetc2.Http.HttpClient;
import trophy.projetc2.R;

/**
 * Created by 박효근 on 2016-12-01.
 */

public class Last_Contest_Detail extends AppCompatActivity {
    private String[][] parsedData_Last_Contest_Detail_Search;
    private String Pk;
    private String Title;
    private String ContestDate;
    private LinearLayout Last_Contest_Detail_Layout_Root;
    private ListView Last_Contest_Detail_ListView;
    private TextView Last_Contest_Detail_TextView_ContestName;
    private TextView Last_Contest_Detail_TextView_ContestDate;
    private TextView Last_Contest_Detail_TextView_Award;
    private String[] Award;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_last_contest_detail);


        Intent intent = getIntent();
        Pk = intent.getStringExtra("Pk");
        Title = intent.getStringExtra("Title");
        ContestDate = intent.getStringExtra("ContestDate");
        Last_Contest_Detail_Layout_Root = (LinearLayout) findViewById(R.id.Last_Contest_Detail_Layout_Root);
        Last_Contest_Detail_ListView = (ListView) findViewById(R.id.Last_Contest_Detail_ListView);
        Last_Contest_Detail_TextView_ContestName = (TextView)findViewById(R.id.Last_Contest_Detail_TextView_ContestName);
        Last_Contest_Detail_TextView_ContestDate = (TextView)findViewById(R.id.Last_Contest_Detail_TextView_ContestDate);
        Last_Contest_Detail_TextView_Award = (TextView)findViewById(R.id.Last_Contest_Detail_TextView_Award);


        HttpClient http_Last_Contest_Detail_Search = new HttpClient();
        String result = http_Last_Contest_Detail_Search.HttpClient("Trophy_part2", "Last_Contest_Detail.jsp", Pk);
        parsedData_Last_Contest_Detail_Search = jsonParserList_Last_Contest_Detail(result);
        ArrayList<Last_Contest_Detail_CustomList_MyData> Last_Contest_Detail_CustomList_MyData;
        Last_Contest_Detail_CustomList_MyData = new ArrayList<Last_Contest_Detail_CustomList_MyData>();
        String img = parsedData_Last_Contest_Detail_Search[0][1];
        String[] imgs = img.split("/");

        Last_Contest_Detail_TextView_ContestName.setText(Title);
        Last_Contest_Detail_TextView_ContestDate.setText(ContestDate);
        Award = parsedData_Last_Contest_Detail_Search[0][2].split("/");
        for(int i = 0; i<Award.length;i++)
                Last_Contest_Detail_TextView_Award.setText(Last_Contest_Detail_TextView_Award.getText()+Award[i].toString()+"\n");
        int cnt = 0;
        for (int i = 0; i < imgs.length / 3; i++) {
            Last_Contest_Detail_CustomList_MyData.add(new Last_Contest_Detail_CustomList_MyData(parsedData_Last_Contest_Detail_Search[0][0], parsedData_Last_Contest_Detail_Search[0][1], imgs[cnt], imgs[cnt + 1], imgs[cnt + 2], parsedData_Last_Contest_Detail_Search[0][2], String.valueOf(cnt)));
            cnt += 3;
        }
        Last_Contest_Detail_CustomList_Adapter adapter = new Last_Contest_Detail_CustomList_Adapter(this, Last_Contest_Detail_CustomList_MyData);
        Last_Contest_Detail_ListView.setAdapter(adapter);
    }

    public String[][] jsonParserList_Last_Contest_Detail(String pRecvServerPage) {
        Log.i("Last_Contest_Detail내용", pRecvServerPage);
        try {
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");
            String[] jsonName = {"msg1", "msg2", "msg3"};
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
}
