package trophy.projetc2.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import trophy.projetc2.BaseActivity;
import trophy.projetc2.R;

/**
 * Created by ldong on 2017-02-01.
 */

public class Terms extends BaseActivity {
    CheckBox Terms_CheckBox_All, Terms_CheckBox_1, Terms_CheckBox_2;
    ScrollView Terms_ScrollView_Main, Terms_ScrollView_1, Terms_ScrollView_2;
    LinearLayout Terms_LinearLayout_NoAgreement, Terms_LinearLayout_Agreement;
    TextView Terms_TextView_Warnning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_terms);

        Terms_CheckBox_All = (CheckBox) findViewById(R.id.Terms_CheckBox_All);
        Terms_CheckBox_1 = (CheckBox) findViewById(R.id.Terms_CheckBox_1);
        Terms_CheckBox_2 = (CheckBox) findViewById(R.id.Terms_CheckBox_2);
        Terms_ScrollView_Main = (ScrollView) findViewById(R.id.Terms_ScrollView_Main);
        Terms_ScrollView_1 = (ScrollView) findViewById(R.id.Terms_ScrollView_1);
        Terms_ScrollView_2 = (ScrollView) findViewById(R.id.Terms_ScrollView_2);
        Terms_LinearLayout_NoAgreement = (LinearLayout) findViewById(R.id.Terms_LinearLayout_NoAgreement);
        Terms_LinearLayout_Agreement = (LinearLayout) findViewById(R.id.Terms_LinearLayout_Agreement);
        Terms_TextView_Warnning = (TextView) findViewById(R.id.Terms_TextView_Warnning);

        Terms_TextView_Warnning.setText("");

        Terms_LinearLayout_NoAgreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Terms_LinearLayout_Agreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Terms_CheckBox_1.isChecked() && Terms_CheckBox_2.isChecked()) {
                    startActivity(new Intent(Terms.this, Join.class));
                    finish();
                } else {
                    Terms_TextView_Warnning.setText("트로피 이용약관과 개인정보 수집 및 이용에 대한 안내 모두 동의해 주세요");
                }
            }
        });

        Terms_ScrollView_1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP)
                    Terms_ScrollView_Main.requestDisallowInterceptTouchEvent(false);
                else
                    Terms_ScrollView_Main.requestDisallowInterceptTouchEvent(true);

                return false;
            }
        });

        Terms_ScrollView_2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP)
                    Terms_ScrollView_Main.requestDisallowInterceptTouchEvent(false);
                else
                    Terms_ScrollView_Main.requestDisallowInterceptTouchEvent(true);

                return false;
            }
        });

        Terms_CheckBox_All.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (Terms_CheckBox_All.isChecked()) {
                    Terms_CheckBox_1.setChecked(true);
                    Terms_CheckBox_2.setChecked(true);
                } else {
                    Terms_CheckBox_1.setChecked(false);
                    Terms_CheckBox_2.setChecked(false);
                }
            }
        });

    }
}
