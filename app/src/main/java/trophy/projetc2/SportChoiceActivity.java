package trophy.projetc2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by ldong on 2017-01-16.
 */

public class SportChoiceActivity extends AppCompatActivity {
    String Id;
    Button Button_Basketball, Button_Baseball, Button_Coach, Button_Balling, Button_Biking, Button_Soccer;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_choice_sport);

        Intent intent = getIntent();
        Id = intent.getStringExtra("Id");

        Button_Basketball = (Button) findViewById(R.id.layout_choice_basketball_button);
        Button_Baseball = (Button) findViewById(R.id.layout_choice_baseball_button);
        Button_Coach = (Button) findViewById(R.id.layout_choice_coach_button);
        Button_Balling = (Button) findViewById(R.id.layout_choice_balling_button);
        Button_Biking = (Button) findViewById(R.id.layout_choice_biking_button);
        Button_Soccer = (Button) findViewById(R.id.layout_choice_soccer_button);

        Button_Basketball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(SportChoiceActivity.this, MainActivity.class);
                intent1.putExtra("Id", Id);
                intent1.putExtra("SportType", "basketball");
                startActivity(intent1);
            }
        });
        Button_Baseball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "준비중 입니다.", Snackbar.LENGTH_LONG)
                        .show();
            }
        });
        Button_Coach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "준비중 입니다.", Snackbar.LENGTH_LONG)
                        .show();
            }
        });
        Button_Balling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "준비중 입니다.", Snackbar.LENGTH_LONG)
                        .show();
            }
        });
        Button_Biking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "준비중 입니다.", Snackbar.LENGTH_LONG)
                        .show();
            }
        });
        Button_Soccer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "준비중 입니다.", Snackbar.LENGTH_LONG)
                        .show();
            }
        });
    }
}