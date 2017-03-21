package trophy.projetc2.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import trophy.projetc2.Http.HttpClient;
import trophy.projetc2.Navigation.Last_Contest_Detail_CustomList_Adapter;
import trophy.projetc2.Navigation.Last_Contest_Detail_CustomList_MyData;
import trophy.projetc2.R;

/**
 * Created by 박효근 on 2016-12-01.
 */

public class Suggest extends AppCompatActivity {
    private ImageView Suggest_ImageVIew_Back;
    private EditText Sugegest_EditText_Content;
    private EditText Sugegest_EditText_Email;
    private CheckBox Suggest_CheckBox;
    private Button Suggest_Button;
    private String Pk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_suggest);

        Suggest_ImageVIew_Back = (ImageView)findViewById(R.id.Suggest_ImageVIew_Back);
        Sugegest_EditText_Content = (EditText) findViewById(R.id.Sugegest_EditText_Content);
        Sugegest_EditText_Email = (EditText)findViewById(R.id.Sugegest_EditText_Email);
        Suggest_CheckBox = (CheckBox)findViewById(R.id.Suggest_CheckBox);
        Suggest_Button = (Button) findViewById(R.id.Suggest_Button);
        Intent intent = getIntent();
        Pk = intent.getStringExtra("Pk");

        Suggest_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((Sugegest_EditText_Content.getText().toString().equals(""))) {
                    Snackbar.make(v,"건의사항을 입력해주시기 바랍니다.",Snackbar.LENGTH_SHORT).show();
                }
                else{
                    if((Sugegest_EditText_Email.getText().toString().equals(""))){
                        Snackbar.make(v,"이메일을 입력해주시기 바랍니다.",Snackbar.LENGTH_SHORT).show();
                    }else{
                        if(Suggest_CheckBox.isChecked() == false){
                            Snackbar.make(v,"위 내용에 동의해주시기 바랍니다.",Snackbar.LENGTH_SHORT).show();
                        }
                        else{
                            HttpClient ContestHttp = new HttpClient();
                            ContestHttp.HttpClient("Trophy_part1", "suggest.jsp", Pk, Sugegest_EditText_Content.getText().toString(),Sugegest_EditText_Email.getText().toString());
                            Snackbar.make(v, "건의사항이 접수되었습니다.", Snackbar.LENGTH_INDEFINITE).setAction("확인", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    finish();
                                }
                            }).show();
                        }
                    }

                }
            }
        });
        Suggest_ImageVIew_Back.setOnClickListener(new View.OnClickListener() {
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
}
