package trophy.projetc2.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import trophy.projetc2.Http.HttpClient;
import trophy.projetc2.R;

import static java.sql.Types.NULL;

/**
 * Created by 박효근 on 2017-05-01.
 */

public class OutCourt_CourtInfo extends AppCompatActivity {
    TextView OutCourt_CourtInfo_TextView_Title;
    ImageView OutCourt_CourtInfo_ImageView_Back;
    ListView OutCourt_CourtInfo_ListView_Court;
    static String Address_Si = "";static String Address_Do = "";static String User_Pk = "";
    String[][] parsedData_Match;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_navigation_outcourt_courtinfo);
        Intent intent1 = getIntent();
        Address_Si = intent1.getStringExtra("Address_Si");
        Address_Do = intent1.getStringExtra("Address_Do");
        User_Pk = intent1.getStringExtra("User_Pk");
        OutCourt_CourtInfo_TextView_Title = (TextView)findViewById(R.id.OutCourt_CourtInfo_TextView_Title);
        OutCourt_CourtInfo_ImageView_Back = (ImageView)findViewById(R.id.OutCourt_CourtInfo_ImageView_Back);
        OutCourt_CourtInfo_ListView_Court = (ListView)findViewById(R.id.OutCourt_CourtInfo_ListView_Court);
        OutCourt_CourtInfo_TextView_Title.setText(Address_Si);

        final ArrayList<OutCourt_CourtInfo_MyData> OutCourt_CourtInfo_MyData;
        OutCourt_CourtInfo_MyData = new ArrayList<OutCourt_CourtInfo_MyData>();
        String str = Address_Si;
        String[] data = str.split("/");
        for(int i=0; i<data.length; i++)
        {
            HttpClient http_match = new HttpClient();
            String result = http_match.HttpClient("Trophy_part1","OutCourt.jsp", Address_Do,data[i]);
            parsedData_Match = jsonParserList_Match(result);
            if(parsedData_Match[0][0] == "."){
            }else{
                for (int j = 0; j < parsedData_Match.length; j++) {
                    OutCourt_CourtInfo_MyData.add(new OutCourt_CourtInfo_MyData(parsedData_Match[j][0], parsedData_Match[j][1] + parsedData_Match[j][2],"3",OutCourt_CourtInfo.this,User_Pk));
                }
            }
        }
        final OutCourt_CourtInfo_MyAdapter adapter = new OutCourt_CourtInfo_MyAdapter(this, OutCourt_CourtInfo_MyData);
        OutCourt_CourtInfo_ListView_Court.setAdapter(adapter);

        OutCourt_CourtInfo_ImageView_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
    }
    public String[][] jsonParserList_Match(String pRecvServerPage){
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try{
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");
            String[] jsonName = {"msg1","msg2","msg3"};

            if(jArr.length()==0){
                String[][] parseredData = new String[1][1];
                parseredData[0][0] = ".";
                return parseredData;
            }else{
                String[][] parseredData = new String[jArr.length()][jsonName.length];
                for(int i = 0; i<jArr.length();i++){
                    json = jArr.getJSONObject(i);
                    for (int j=0;j<jsonName.length; j++){
                        parseredData[i][j] = json.getString(jsonName[j]);
                    }
                }
                return parseredData;
            }
        }catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }
}
