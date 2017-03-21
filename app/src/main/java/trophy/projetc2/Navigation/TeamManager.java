package trophy.projetc2.Navigation;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

import devlight.io.library.ntb.NavigationTabBar;
import me.drakeet.materialdialog.MaterialDialog;
import trophy.projetc2.Http.HttpClient;
import trophy.projetc2.MainActivity;
import trophy.projetc2.R;

import static trophy.projetc2.MainActivity.activity;
import static trophy.projetc2.Navigation.TeamManager_ContestJoin.Contestjoin_timer;
import static trophy.projetc2.Navigation.TeamManager_PlayerManager.PlayerManager_timer;

/**
 * Created by 박효근 on 2017-01-10.
 */

public class TeamManager extends AppCompatActivity {
    static Activity TeamManager_Activity;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    static String TeamManager_Team_Pk,TeamManager_TeamName, User_Pk;
    ImageView Teammanager_ImageVIew_Back, Teammanager_ImageVIew_disperse;
    String[][] parseredData_teamdisperse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_navigation_teammanager);
        Teammanager_ImageVIew_Back = (ImageView)findViewById(R.id.Teammanager_ImageVIew_Back);
        Teammanager_ImageVIew_disperse = (ImageView)findViewById(R.id.Teammanager_ImageVIew_disperse);

        TeamManager_Activity = this;
        Intent intent = getIntent();
        User_Pk = intent.getStringExtra("User_Pk");
        TeamManager_Team_Pk = intent.getStringExtra("Team_Pk");
        TeamManager_TeamName = intent.getStringExtra("TeamName");
        //프래그먼트 정의
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(1);
        ////////////
        final int pageCount = 3;
        final NavigationTabBar navigationTabBar = (NavigationTabBar) findViewById(R.id.ntb);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.teammanager),Color.WHITE
                )
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.teammanager_player),
                        Color.WHITE
                )
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.teammanager),
                        Color.WHITE
                )
                        .build()
        );
        navigationTabBar.setModels(models);
        navigationTabBar.setViewPager(mViewPager, 0);

        navigationTabBar.setTitleMode(NavigationTabBar.TitleMode.ACTIVE);
        navigationTabBar.setBadgeGravity(NavigationTabBar.BadgeGravity.BOTTOM);
        navigationTabBar.setBadgePosition(NavigationTabBar.BadgePosition.CENTER);
//        navigationTabBar.setIsBadged(true);
//        navigationTabBar.setIsTitled(true);
//        navigationTabBar.setIsTinted(true);
//        navigationTabBar.setIsBadgeUseTypeface(true);
//        navigationTabBar.setIsSwiped(true);
//        navigationTabBar.setBadgeSize(10);
        //navigationTabBar.setTitleSize(10);
        Teammanager_ImageVIew_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Contestjoin_timer.cancel();
                PlayerManager_timer.cancel();
                TeamManager.this.finish();
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
            }
        });
        Teammanager_ImageVIew_disperse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater)view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
                final View layout = inflater.inflate(R.layout.layout_customdialog_1, (ViewGroup) view.findViewById(R.id.TeamInfo_Customdialog_1_Root));
                final TextView TeamInfo_Customdialog_1_Title = (TextView)layout.findViewById(R.id.TeamInfo_Customdialog_1_Title);
                final ImageView TeamInfo_Customdialog_1_Back = (ImageView)layout.findViewById(R.id.TeamInfo_Customdialog_1_Back);
                final TextView TeamInfo_Customdialog_1_Content = (TextView)layout.findViewById(R.id.TeamInfo_Customdialog_1_Content);
                final Button TeamInfo_Customdialog_1_Ok = (Button)layout.findViewById(R.id.TeamInfo_Customdialog_1_Ok);
                TeamInfo_Customdialog_1_Title.setText("팀 해산");
                TeamInfo_Customdialog_1_Content.setText("팀 해산은 팀원이 팀 대표 1인일 경우에만 가능합니다. \n팀을 해산할 경우 현재 참가중인 대회에 자동 취소됩니다.\n 참가 확정중인 대회가 있을경우 관리자 문의 후 팀을 해산하시기 바랍니다.");
                final MaterialDialog TeamInfo_Dialog = new MaterialDialog(view.getContext());
                TeamInfo_Dialog
                        .setContentView(layout)
                        .setCanceledOnTouchOutside(true);
                TeamInfo_Dialog.show();
                TeamInfo_Customdialog_1_Back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TeamInfo_Dialog.dismiss();
                    }
                });
                TeamInfo_Customdialog_1_Ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                                HttpClient http_TeamDisperse= new HttpClient();
                                String result12 = http_TeamDisperse.HttpClient("Trophy_part1","TeamManager_TeamDisperse.jsp",User_Pk,TeamManager_Team_Pk);
                                parseredData_teamdisperse = jsonParserList_teamDisperse(result12);
                                if(parseredData_teamdisperse[0][0].equals("Exist_Player"))
                                {
                                    Snackbar.make(view,"팀 대표 외 팀원이 존재합니다.",Snackbar.LENGTH_SHORT).show();
                                }
                                else if(parseredData_teamdisperse[0][0].equals("Exist_Contest"))
                                {
                                    Snackbar.make(view,"참가 확정중인 대회가 있습니다.",Snackbar.LENGTH_SHORT).show();
                                }
                                else if(parseredData_teamdisperse[0][0].equals("Exist_Joiner"))
                                {
                                    Snackbar.make(view,"가입 신청 중 인원이 있습니다.",Snackbar.LENGTH_SHORT).show();
                                }
                                else{
                                    Snackbar.make(view,"잠시 후 다시 시도해주시기 바랍니다.",Snackbar.LENGTH_SHORT).show();
                                }
                    }
                });
            }
        });
    }
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            return rootView;
        }
    }
    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            // return PlaceholderFragment.newInstance(position + 1);
            Fragment fragment = null;
            Bundle args = null;
            switch (position) {
                case 0:
                    fragment = new TeamManager_ContestJoin();
                    args = new Bundle();
                    break;
                case 1:
                    fragment = new TeamManager_PlayerManager();
                    args = new Bundle();
                    break;
                case 2:
                    fragment = new TeamManager_TeamIntroduce();
                    args = new Bundle();
                    break;
            }
            return fragment;
        }
        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }

    }
    @Override
    public void onBackPressed() {
        Contestjoin_timer.cancel();
        PlayerManager_timer.cancel();
        finish();
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
    }
    private String[][] jsonParserList_teamDisperse(String pRecvServerPage) {
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try {
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jarr = json.getJSONArray("List");

            String[] jsonName = {"msg1"};
            String[][] parseredData = new String[jarr.length()][jsonName.length];
            for (int i = 0; i < jarr.length(); i++) {
                json = jarr.getJSONObject(i);
                for (int j = 0; j < jsonName.length; j++) {
                    parseredData[i][j] = json.getString(jsonName[j]);
                }
            }
            for (int i = 0; i < parseredData.length; i++) {
                Log.i("JSON을 분석한 데이터" + i + ":", parseredData[i][0]);
            }
            return parseredData;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
