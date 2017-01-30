package trophy.projetc2.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

public class Last_Contest extends AppCompatActivity{

    LinearLayout Last_Contest_Layout_Root;
    ListView Last_contest_ListView;
    String[][] parsedData_Last_Contest_Search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_last_contest);
        Last_Contest_Layout_Root = (LinearLayout)findViewById(R.id.Last_Contest_Layout_Root);
        Last_contest_ListView = (ListView) findViewById(R.id.Last_contest_ListView);

        HttpClient http_Last_Contest_Search = new HttpClient();
        String result = http_Last_Contest_Search.HttpClient("Trophy_part2","Last_Contest.jsp");
        parsedData_Last_Contest_Search = jsonParserList_Last_Contest(result);
        ArrayList<Last_Contest_CustomList_MyData> Last_Contest_CustomList_MyData;
        Last_Contest_CustomList_MyData = new ArrayList<Last_Contest_CustomList_MyData>();
        for (int i = 0; i < parsedData_Last_Contest_Search.length; i++) {
            Last_Contest_CustomList_MyData.add(new Last_Contest_CustomList_MyData(parsedData_Last_Contest_Search[i][0], parsedData_Last_Contest_Search[i][1], parsedData_Last_Contest_Search[i][2]));
        }
        Last_Contest_CustomList_Adapter adapter = new Last_Contest_CustomList_Adapter(this, Last_Contest_CustomList_MyData);
        Last_contest_ListView.setAdapter(adapter);

        Last_contest_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent Last_Contest_Detail_Intent = new Intent(Last_Contest.this , Last_Contest_Detail.class);
                Last_Contest_Detail_Intent.putExtra("Pk",parsedData_Last_Contest_Search[position][0].toString());
                Last_Contest_Detail_Intent.putExtra("Title",parsedData_Last_Contest_Search[position][1].toString());
                Last_Contest_Detail_Intent.putExtra("ContestDate",parsedData_Last_Contest_Search[position][3].toString());
                startActivity(Last_Contest_Detail_Intent);
            }
        });


        Last_Contest_Layout_Root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    public String[][] jsonParserList_Last_Contest(String pRecvServerPage){
        Log.i("Last_Contest_Search내용", pRecvServerPage);
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
}
