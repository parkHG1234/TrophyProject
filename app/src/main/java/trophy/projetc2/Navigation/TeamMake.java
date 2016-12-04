package trophy.projetc2.Navigation;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import me.drakeet.materialdialog.MaterialDialog;
import trophy.projetc2.Http.HttpClient;
import trophy.projetc2.R;

/**
 * Created by 박효근 on 2016-12-01.
 */

public class TeamMake extends AppCompatActivity{
    String Pk;
    String TeamName,Spinner_Do,Spinner_Si,HomeCourt,TeamIntro;
    String[][] parsedData_TeamMake;
    AlertDialog dlg;
    EditText Layout_Navigation_TeamMake_EditText_TeamName;
    Spinner Layout_Navigation_TeamMake_Spinner_AddressDo;
    Spinner Layout_Navigation_TeamMake_Spinner_AddressSe;
    EditText Layout_Navigation_TeamMake_EditText_HomeCourt;
    EditText Layout_Navigation_TeamMake_EditText_TeamIntro;
    Button Layout_Navigation_TeamMake_Button_TeamMake;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_navigation_teammake);
        Intent intent1 = getIntent();
        Pk = intent1.getStringExtra("Pk");

        Layout_Navigation_TeamMake_EditText_TeamName = (EditText)findViewById(R.id.Layout_Navigation_TeamMake_EditText_TeamName);
        Layout_Navigation_TeamMake_Spinner_AddressDo = (Spinner)findViewById(R.id.Layout_Navigation_TeamMake_Spinner_AddressDo);
        Layout_Navigation_TeamMake_Spinner_AddressSe= (Spinner)findViewById(R.id.Layout_Navigation_TeamMake_Spinner_AddressSe);
        Layout_Navigation_TeamMake_EditText_HomeCourt = (EditText)findViewById(R.id.Layout_Navigation_TeamMake_EditText_HomeCourt);
        Layout_Navigation_TeamMake_EditText_TeamIntro = (EditText)findViewById(R.id.Layout_Navigation_TeamMake_EditText_TeamIntro);
        Layout_Navigation_TeamMake_Button_TeamMake = (Button)findViewById(R.id.Layout_Navigation_TeamMake_Button_TeamMake);


        Layout_Navigation_TeamMake_Button_TeamMake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //에러 처리 -팀명 입력안 한 경우
                TeamName = Layout_Navigation_TeamMake_EditText_TeamName.getText().toString();
                HomeCourt = Layout_Navigation_TeamMake_EditText_HomeCourt.getText().toString();
                TeamIntro = Layout_Navigation_TeamMake_EditText_TeamIntro.getText().toString();
                Spinner_Do = "서울";
                Spinner_Si = "강남구";
                if(TeamName.equals("")){
                    Snackbar.make(view,"팀명을 입력해주세요.",Snackbar.LENGTH_SHORT).show();
                }
                else{
                    if(HomeCourt.equals("")){
                        Snackbar.make(view,"홈코트를 입력해주세요.",Snackbar.LENGTH_SHORT).show();
                    }
                    else{
                        if(TeamIntro.equals("")){
                            Snackbar.make(view,"팀소개를 입력해주세요.",Snackbar.LENGTH_SHORT).show();
                        }
                        else
                        {
                            HttpClient http_teammake = new HttpClient();
                            String result =http_teammake.HttpClient("Trophy_part1","TeamMake.jsp",Pk,TeamName,Spinner_Do,Spinner_Si,HomeCourt,TeamIntro);
                            parsedData_TeamMake = jsonParserList_TeamMake(result);
                            if(parsedData_TeamMake[0][0].equals("succed")){
                                ///
                                dlg = new AlertDialog.Builder(TeamMake.this).setTitle("트로피")
                                        .setMessage("팀이 생성되었습니다.")
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                finish();
                                            }
                                        }).show();
                            }
                            else{
                                ///
                                dlg = new AlertDialog.Builder(TeamMake.this).setTitle("트로피")
                                        .setMessage("잠시 후 다시 시도해주세요.")
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dlg.dismiss();
                                            }
                                        }).show();
                            }
                        }
                    }
                }
            }
        });

    }
    public String[][] jsonParserList_TeamMake(String pRecvServerPage){
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try{
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");
            String[] jsonName = {"msg1"};
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
