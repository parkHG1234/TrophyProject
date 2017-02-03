package trophy.projetc2.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

    private EditText Sugegest_EditText;
    private Button Suggest_Button;
    private String Pk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_suggest);

        Sugegest_EditText = (EditText) findViewById(R.id.Sugegest_EditText);
        Suggest_Button = (Button) findViewById(R.id.Suggest_Button);

        Intent intent = getIntent();
        Pk = intent.getStringExtra("Pk");

        Suggest_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(Sugegest_EditText.getText().toString().equals(null))) {
                    HttpClient ContestHttp = new HttpClient();
                    ContestHttp.HttpClient("Trophy_part2", "suggest_upload.jsp", Pk, Sugegest_EditText.getText().toString());
                    finish();
                }
            }
        });
    }
}
