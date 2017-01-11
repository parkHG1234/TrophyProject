package trophy.projetc2.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
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

public class Last_Contest_Detail extends AppCompatActivity{
    String[][] parsedData_Last_Contest_Detail_Search;
    String Pk;
    LinearLayout Last_Contest_Detail_Layout_Root;
    ListView Last_Contest_Detail_ListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_last_contest_detail);


        Intent intent = getIntent();
        Pk = intent.getStringExtra("Pk");

        Last_Contest_Detail_Layout_Root = (LinearLayout)findViewById(R.id.Last_Contest_Detail_Layout_Root);
        Last_Contest_Detail_ListView = (ListView) findViewById(R.id.Last_Contest_Detail_ListView);

        HttpClient http_Last_Contest_Detail_Search = new HttpClient();
        String result = http_Last_Contest_Detail_Search.HttpClient("Trophy_part2","Last_Contest_Detail.jsp",Pk);
        parsedData_Last_Contest_Detail_Search = jsonParserList_Last_Contest_Detail(result);
        ArrayList<Last_Contest_Detail_CustomList_MyData> Last_Contest_Detail_CustomList_MyData;
        Last_Contest_Detail_CustomList_MyData = new ArrayList<Last_Contest_Detail_CustomList_MyData>();
        for (int i = 0; i < parsedData_Last_Contest_Detail_Search.length; i++) {
            Last_Contest_Detail_CustomList_MyData.add(new Last_Contest_Detail_CustomList_MyData(parsedData_Last_Contest_Detail_Search[i][0], parsedData_Last_Contest_Detail_Search[i][1], parsedData_Last_Contest_Detail_Search[i][2]));
        }
        Last_Contest_Detail_CustomList_Adapter adapter = new Last_Contest_Detail_CustomList_Adapter(this, Last_Contest_Detail_CustomList_MyData);
        Last_Contest_Detail_ListView.setAdapter(adapter);

    }
    public String[][] jsonParserList_Last_Contest_Detail(String pRecvServerPage){
        Log.i("Last_Contest_Detail내용", pRecvServerPage);
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
}
