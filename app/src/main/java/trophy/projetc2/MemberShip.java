package trophy.projetc2;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by 박지훈 on 2016-11-30.
 */

public class MemberShip extends AppCompatActivity{

    LinearLayout MemberShip_Layout_Root;
    EditText MemberShip_EditText_Phone;
    Button MemberShip_Button_Confirm_Phone;
    EditText MemberShip_EditText_Name;
    EditText MemberShip_EditText_PW;
    EditText MemberShip_EditText_Confirm_PW;
    EditText MemberShip_EditText_Birth_Year;
    EditText MemberShip_EditText_Birth_Month;
    EditText MemberShip_EditText_Birth_Day;
    RadioGroup MemberShip_EditText_Sex_RadioGroup;
    RadioButton MemberShip_EditText_Sex_Radio_male;
    RadioButton MemberShip_EditText_Sex_Radio_Female;
    Spinner MemberShip_Spinner_Do;
    Spinner MemberShip_Spinner_Si;
    Button MemberShip_Button_MemberShip;
    Spinner_si_setAdapter Spinner_si_setAdapter;
    ArrayAdapter<CharSequence> adspin1, adspin2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_membership);


        MemberShip_Layout_Root = (LinearLayout)findViewById(R.id.MemberShip_Layout_Root);
        MemberShip_EditText_Phone = (EditText)findViewById(R.id.MemberShip_EditText_Phone);
        MemberShip_Button_Confirm_Phone = (Button)findViewById(R.id.MemberShip_Button_Confirm_Phone);
        MemberShip_EditText_Name = (EditText)findViewById(R.id.MemberShip_EditText_Name);
        MemberShip_EditText_PW = (EditText)findViewById(R.id.MemberShip_EditText_PW);
        MemberShip_EditText_Confirm_PW = (EditText)findViewById(R.id.MemberShip_EditText_Confirm_PW);
        MemberShip_EditText_Birth_Year = (EditText)findViewById(R.id.MemberShip_EditText_Birth_Year);
        MemberShip_EditText_Birth_Month = (EditText)findViewById(R.id.MemberShip_EditText_Birth_Month);
        MemberShip_EditText_Birth_Day = (EditText)findViewById(R.id.MemberShip_EditText_Birth_Day);
        MemberShip_EditText_Sex_RadioGroup = (RadioGroup)findViewById(R.id.MemberShip_EditText_Sex_RadioGroup);
        MemberShip_EditText_Sex_Radio_male = (RadioButton)findViewById(R.id.MemberShip_EditText_Sex_Radio_male);
        MemberShip_EditText_Sex_Radio_Female = (RadioButton)findViewById(R.id.MemberShip_EditText_Sex_Radio_Female);
        MemberShip_Spinner_Do = (Spinner)findViewById(R.id.MemberShip_Spinner_Do);
        MemberShip_Spinner_Si = (Spinner)findViewById(R.id.MemberShip_Spinner_Si);
        MemberShip_Button_MemberShip = (Button)findViewById(R.id.MemberShip_Button_MemberShip);

        adspin1 = ArrayAdapter.createFromResource(getApplicationContext(), R.array.spinner_do,android.R.layout.simple_spinner_item);
        adspin1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        MemberShip_Spinner_Do.setAdapter(adspin1);
        adspin2 =  ArrayAdapter.createFromResource(getApplicationContext(), R.array.spinner_do_seoul,android.R.layout.simple_spinner_item);
        adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        MemberShip_Spinner_Si.setAdapter(adspin2);


        MemberShip_Button_Confirm_Phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        MemberShip_EditText_Birth_Year.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(MemberShip_EditText_Birth_Year.length()==4){
                if((Integer.parseInt(MemberShip_EditText_Birth_Year.getText().toString())>1900)&&(Integer.parseInt(MemberShip_EditText_Birth_Year.getText().toString())<2018)){
                     //Snackbar.make(v, "재접속 후 다시 시도해주세요.", Snackbar.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"asdffads",Toast.LENGTH_LONG).show();
                }}
            }
        });

        MemberShip_EditText_Birth_Month.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(MemberShip_EditText_Birth_Month.length()==2){
                    if((Integer.parseInt(MemberShip_EditText_Birth_Month.getText().toString())>0)&&(Integer.parseInt(MemberShip_EditText_Birth_Month.getText().toString())<13)){
                    }else{
                        Toast.makeText(getApplicationContext(),"asdffads",Toast.LENGTH_LONG).show();
                    }}
            }
        });
        MemberShip_EditText_Birth_Day.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(MemberShip_EditText_Birth_Day.length()==2){
                    if((Integer.parseInt(MemberShip_EditText_Birth_Day.getText().toString())>0)&&(Integer.parseInt(MemberShip_EditText_Birth_Day.getText().toString())<32)){
                    }else{
                        Toast.makeText(getApplicationContext(),"asdffads",Toast.LENGTH_LONG).show();
                    }}
            }
        });

        MemberShip_Button_MemberShip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(String.valueOf(MemberShip_EditText_Sex_RadioGroup.getCheckedRadioButtonId()).equals("2131558537")){
                }else if(String.valueOf(MemberShip_EditText_Sex_RadioGroup.getCheckedRadioButtonId()).equals("2131558538")){
                }
            }
        });

        MemberShip_Spinner_Do.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Spinner_si_setAdapter = new Spinner_si_setAdapter(MemberShip.this);
                adspin2 = Spinner_si_setAdapter.getAdapter(position);
                MemberShip_Spinner_Si.setAdapter(adspin2);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}
