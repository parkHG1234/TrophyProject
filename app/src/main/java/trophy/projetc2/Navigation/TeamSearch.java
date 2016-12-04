package trophy.projetc2.Navigation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import trophy.projetc2.Http.HttpClient;
import trophy.projetc2.R;

/**
 * Created by 박효근 on 2016-12-02.
 */

public class TeamSearch extends AppCompatActivity{
    EditText Layout_Navigation_TeamSearch_EditText_TeamName;
    ListView Layout_Navigation_TeamSearch_ListView_TeamSearch;
    private TeamSearch_CustomList_Adapter adapter;
    ArrayList<TeamSearch_CustomList_MyData> TeamSearch_CustomList_MyData;
    String[][] parsedData_TeamSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_navigation_teamsearch);
        Layout_Navigation_TeamSearch_EditText_TeamName = (EditText)findViewById(R.id.Layout_Navigation_TeamSearch_EditText_TeamName);
        Layout_Navigation_TeamSearch_ListView_TeamSearch = (ListView)findViewById(R.id.Layout_Navigation_TeamSearch_ListView_TeamSearch);

        init();

        Layout_Navigation_TeamSearch_EditText_TeamName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String text = Layout_Navigation_TeamSearch_EditText_TeamName.getText().toString()
                        .toLowerCase(Locale.getDefault());
                adapter.filter(text);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
    public void init() {
        setData_Player();
        adapter = new TeamSearch_CustomList_Adapter(TeamSearch.this, TeamSearch_CustomList_MyData);
        //리스트뷰에 어댑터 연결
        Layout_Navigation_TeamSearch_ListView_TeamSearch.setAdapter(adapter);
    }
    private void setData_Player()
    {
        HttpClient http_teamsearch = new HttpClient();
        String result = http_teamsearch.HttpClient("Trophy_part1","TeamSearch.jsp");
        parsedData_TeamSearch = jsonParserList_User(result);
        TeamSearch_CustomList_MyData = new ArrayList<TeamSearch_CustomList_MyData>();
        for(int i =0; i<parsedData_TeamSearch.length; i++)
        {
            TeamSearch_CustomList_MyData.add(new TeamSearch_CustomList_MyData(parsedData_TeamSearch[i][0],parsedData_TeamSearch[i][1]));
        }
    }
    public String[][] jsonParserList_User(String pRecvServerPage){
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try{
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");
            String[] jsonName = {"msg1","msg2"};
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
