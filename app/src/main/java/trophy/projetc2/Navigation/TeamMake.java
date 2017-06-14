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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import trophy.projetc2.Get_Spinner_Si;
import trophy.projetc2.Http.HttpClient;
import trophy.projetc2.MainActivity;
import trophy.projetc2.R;

import static trophy.projetc2.MainActivity.activity;

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
    Spinner Layout_Navigation_TeamMake_Spinner_AddressSi;
    EditText Layout_Navigation_TeamMake_EditText_HomeCourt;
    EditText Layout_Navigation_TeamMake_EditText_TeamIntro;
    Button Layout_Navigation_TeamMake_Button_TeamMake;
    ImageView Teammake_ImageView_Back;
    private trophy.projetc2.Get_Spinner_Si Get_Spinner_Si;
    private ArrayAdapter<CharSequence> adspin1, adspin2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_navigation_teammake);
        Intent intent1 = getIntent();
        Pk = intent1.getStringExtra("Pk");

        Teammake_ImageView_Back = (ImageView)findViewById(R.id.Teammake_ImageView_Back);
        Layout_Navigation_TeamMake_EditText_TeamName = (EditText)findViewById(R.id.Layout_Navigation_TeamMake_EditText_TeamName);
        Layout_Navigation_TeamMake_Spinner_AddressDo = (Spinner)findViewById(R.id.Layout_Navigation_TeamMake_Spinner_AddressDo);
        Layout_Navigation_TeamMake_Spinner_AddressSi= (Spinner)findViewById(R.id.Layout_Navigation_TeamMake_Spinner_AddressSi);
        Layout_Navigation_TeamMake_EditText_HomeCourt = (EditText)findViewById(R.id.Layout_Navigation_TeamMake_EditText_HomeCourt);
        Layout_Navigation_TeamMake_EditText_TeamIntro = (EditText)findViewById(R.id.Layout_Navigation_TeamMake_EditText_TeamIntro);
        Layout_Navigation_TeamMake_Button_TeamMake = (Button)findViewById(R.id.Layout_Navigation_TeamMake_Button_TeamMake);


        adspin1 = ArrayAdapter.createFromResource(getApplicationContext(), R.array.spinner_do, R.layout.spinner_style);
        adspin1.setDropDownViewResource(R.layout.spinner_style);
        Layout_Navigation_TeamMake_Spinner_AddressDo.setAdapter(adspin1);
        adspin2 = ArrayAdapter.createFromResource(getApplicationContext(), R.array.spinner_do_seoul, R.layout.spinner_style);
        adspin2.setDropDownViewResource(R.layout.spinner_style);
        Layout_Navigation_TeamMake_Spinner_AddressSi.setAdapter(adspin2);

        Layout_Navigation_TeamMake_Spinner_AddressDo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Get_Spinner_Si = new Get_Spinner_Si(getApplicationContext());
                adspin2 = Get_Spinner_Si.getAdapter(position);
                Layout_Navigation_TeamMake_Spinner_AddressSi.setAdapter(adspin2);
                adspin2.setDropDownViewResource(R.layout.spinner_style);
                //setSpinText(Layout_Navigation_TeamMake_Spinner_AddressSi, parseredData[0][2]);
                Spinner_Do = Layout_Navigation_TeamMake_Spinner_AddressDo.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        Layout_Navigation_TeamMake_Spinner_AddressSi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Spinner_Si = Layout_Navigation_TeamMake_Spinner_AddressSi.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Layout_Navigation_TeamMake_Button_TeamMake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //에러 처리 -팀명 입력안 한 경우
                TeamName = Layout_Navigation_TeamMake_EditText_TeamName.getText().toString();
                HomeCourt = Layout_Navigation_TeamMake_EditText_HomeCourt.getText().toString();
                TeamIntro = Layout_Navigation_TeamMake_EditText_TeamIntro.getText().toString();
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
                            if(parsedData_TeamMake[0][0].equals("succed"))
                            {
                                dlg = new AlertDialog.Builder(TeamMake.this).setTitle("오늘의 농구")
                                        .setMessage("팀이 생성되었습니다.")
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dlg.dismiss();
                                                activity.finish();
                                                startActivity(new Intent(TeamMake.this, MainActivity.class));
                                                finish();
                                                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
                                            }
                                        }).show();
                            }
                            else if(parsedData_TeamMake[0][0].equals("already")){
                                Snackbar.make(view,"중복된 팀명입니다.",Snackbar.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Snackbar.make(view,"잠시 후 다시 시도해주시기 바랍니다.",Snackbar.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }
        });
        Teammake_ImageView_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
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
    public void setSpinText(Spinner spin, String text) {
        for (int i = 0; i < spin.getAdapter().getCount(); i++) {
            if (spin.getAdapter().getItem(i).toString().contains(text)) {
                spin.setSelection(i);
                break;
            }
        }
    }
    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
    }
}
