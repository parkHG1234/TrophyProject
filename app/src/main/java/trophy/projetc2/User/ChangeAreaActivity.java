package trophy.projetc2.User;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import trophy.projetc2.Get_Spinner_Si;
import trophy.projetc2.Http.HttpClient;
import trophy.projetc2.R;

/**
 * Created by ldong on 2017-01-31.
 */

public class ChangeAreaActivity extends AppCompatActivity {
    ImageView User_Chage_Area_ImageView_Back;
    Spinner ChangeArea_Spinner_Do, ChangeArea_Spinner_Si;
    Button btn_changeAreaCommit;
    trophy.projetc2.Get_Spinner_Si Get_Spinner_Si;
    ArrayAdapter<CharSequence> adspin1, adspin2;
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_user_change_area);

        User_Chage_Area_ImageView_Back = (ImageView)findViewById(R.id.User_Chage_Area_ImageView_Back);
        ChangeArea_Spinner_Do = (Spinner) findViewById(R.id.ChangeArea_Spinner_Do);
        ChangeArea_Spinner_Si = (Spinner) findViewById(R.id.ChangeArea_Spinner_Si);
        btn_changeAreaCommit = (Button) findViewById(R.id.btn_changeAreaCommit);

        adspin1 = ArrayAdapter.createFromResource(getApplicationContext(), R.array.spinner_do, R.layout.spinner_style);
        adspin1.setDropDownViewResource(R.layout.spinner_style);
        ChangeArea_Spinner_Do.setAdapter(adspin1);
        adspin2 = ArrayAdapter.createFromResource(getApplicationContext(), R.array.spinner_do_seoul, R.layout.spinner_style);
        adspin2.setDropDownViewResource(R.layout.spinner_style);
        ChangeArea_Spinner_Si.setAdapter(adspin2);

        ChangeArea_Spinner_Do.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Get_Spinner_Si = new Get_Spinner_Si(ChangeAreaActivity.this);
                adspin2 = Get_Spinner_Si.getAdapter(position);
                ChangeArea_Spinner_Si.setAdapter(adspin2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btn_changeAreaCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String addressDo = ChangeArea_Spinner_Do.getSelectedItem().toString();
                String addressSi = ChangeArea_Spinner_Si.getSelectedItem().toString();
                preferences = getSharedPreferences("trophy", MODE_PRIVATE);
                String Pk = preferences.getString("Pk", ".");

                HttpClient changeArea = new HttpClient();
                String result = changeArea.HttpClient("Trophy_part1", "User_ChangeArea.jsp", addressDo, addressSi, Pk);
                if (result.equals("succed")) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(ChangeAreaActivity.this);
                    alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(ChangeAreaActivity.this, ChangePersonalInfoActivity.class);
                            finish();
                            ChangePersonalInfoActivity CA = (ChangePersonalInfoActivity) ChangePersonalInfoActivity.activity;
                            CA.finish();
                            startActivity(intent);
                            dialog.dismiss();     //닫기
                        }
                    });
                    alert.setMessage("주소가 변경되었습니다");
                    alert.show();
                } else {
                    Snackbar.make(getCurrentFocus(), "다시 시도해 주세요", Snackbar.LENGTH_LONG).show();
                }
            }
        });
        User_Chage_Area_ImageView_Back.setOnClickListener(new View.OnClickListener() {
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
