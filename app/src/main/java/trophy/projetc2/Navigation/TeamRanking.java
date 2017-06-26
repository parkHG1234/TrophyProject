package trophy.projetc2.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import trophy.projetc2.Http.HttpClient;
import trophy.projetc2.R;

/**
 * Created by 박효근 on 2017-03-23.
 */

public class TeamRanking extends AppCompatActivity {
    ImageView Layout_Navigation_TeamSearch_Button_Back;
    EditText Layout_Navigation_TeamSearch_EditText_TeamName;
    ListView Layout_Navigation_TeamSearch_ListView_TeamSearch;
    TeamRanking_Customlist_MyAdapter adapter;
    String[][] parsedData_TeamSearch;
    String Pk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_navigation_teamsearch);
        Layout_Navigation_TeamSearch_Button_Back = (ImageView)findViewById(R.id.Layout_Navigation_TeamSearch_Button_Back);
        Layout_Navigation_TeamSearch_ListView_TeamSearch = (ListView) findViewById(R.id.Layout_Navigation_TeamSearch_ListView_TeamSearch);
        Layout_Navigation_TeamSearch_EditText_TeamName = (EditText)findViewById(R.id.Layout_Navigation_TeamSearch_EditText_TeamName);

        Intent intent1 = getIntent();
        Pk = intent1.getStringExtra("Pk");

        HttpClient http_teamsearch = new HttpClient();
        String result = http_teamsearch.HttpClient("Trophy_part1","TeamRanking.jsp");
        parsedData_TeamSearch = jsonParserList_User(result);

        ArrayList<TeamRanking_Customlist_MyData> TeamRanking_Customlist_MyData;
        TeamRanking_Customlist_MyData = new ArrayList<TeamRanking_Customlist_MyData>();
        for (int i = 0; i < parsedData_TeamSearch.length; i++) {
            TeamRanking_Customlist_MyData.add(new TeamRanking_Customlist_MyData(Pk,parsedData_TeamSearch[i][0], parsedData_TeamSearch[i][1], parsedData_TeamSearch[i][2],parsedData_TeamSearch[i][3],i+1, parsedData_TeamSearch[i][4],parsedData_TeamSearch[i][5]));
        }
        adapter = new TeamRanking_Customlist_MyAdapter(this, TeamRanking_Customlist_MyData);
        Layout_Navigation_TeamSearch_ListView_TeamSearch.setAdapter(adapter);

        //검색창 변화 이벤트
        Layout_Navigation_TeamSearch_EditText_TeamName.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                String text = Layout_Navigation_TeamSearch_EditText_TeamName.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filter(text);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub
            }
        });
        Layout_Navigation_TeamSearch_Button_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
            }
        });
    }


    public String[][] jsonParserList_User(String pRecvServerPage){
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try{
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");
            String[] jsonName = {"msg1","msg2","msg3","msg4","msg5","msg6"};
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
    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
    }
}
