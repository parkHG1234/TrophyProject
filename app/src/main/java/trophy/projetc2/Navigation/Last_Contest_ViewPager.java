package trophy.projetc2.Navigation;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
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
import android.widget.ImageView;


import trophy.projetc2.ImageDownload;
import trophy.projetc2.Navigation.Last_Contest_ImageView.Last_Contest_ImageView1;
import trophy.projetc2.Navigation.Last_Contest_ImageView.Last_Contest_ImageView10;
import trophy.projetc2.Navigation.Last_Contest_ImageView.Last_Contest_ImageView11;
import trophy.projetc2.Navigation.Last_Contest_ImageView.Last_Contest_ImageView12;
import trophy.projetc2.Navigation.Last_Contest_ImageView.Last_Contest_ImageView13;
import trophy.projetc2.Navigation.Last_Contest_ImageView.Last_Contest_ImageView14;
import trophy.projetc2.Navigation.Last_Contest_ImageView.Last_Contest_ImageView15;
import trophy.projetc2.Navigation.Last_Contest_ImageView.Last_Contest_ImageView16;
import trophy.projetc2.Navigation.Last_Contest_ImageView.Last_Contest_ImageView17;
import trophy.projetc2.Navigation.Last_Contest_ImageView.Last_Contest_ImageView18;
import trophy.projetc2.Navigation.Last_Contest_ImageView.Last_Contest_ImageView19;
import trophy.projetc2.Navigation.Last_Contest_ImageView.Last_Contest_ImageView2;
import trophy.projetc2.Navigation.Last_Contest_ImageView.Last_Contest_ImageView20;
import trophy.projetc2.Navigation.Last_Contest_ImageView.Last_Contest_ImageView21;
import trophy.projetc2.Navigation.Last_Contest_ImageView.Last_Contest_ImageView22;
import trophy.projetc2.Navigation.Last_Contest_ImageView.Last_Contest_ImageView23;
import trophy.projetc2.Navigation.Last_Contest_ImageView.Last_Contest_ImageView24;
import trophy.projetc2.Navigation.Last_Contest_ImageView.Last_Contest_ImageView25;
import trophy.projetc2.Navigation.Last_Contest_ImageView.Last_Contest_ImageView26;
import trophy.projetc2.Navigation.Last_Contest_ImageView.Last_Contest_ImageView27;
import trophy.projetc2.Navigation.Last_Contest_ImageView.Last_Contest_ImageView28;
import trophy.projetc2.Navigation.Last_Contest_ImageView.Last_Contest_ImageView29;
import trophy.projetc2.Navigation.Last_Contest_ImageView.Last_Contest_ImageView3;
import trophy.projetc2.Navigation.Last_Contest_ImageView.Last_Contest_ImageView30;
import trophy.projetc2.Navigation.Last_Contest_ImageView.Last_Contest_ImageView4;
import trophy.projetc2.Navigation.Last_Contest_ImageView.Last_Contest_ImageView5;
import trophy.projetc2.Navigation.Last_Contest_ImageView.Last_Contest_ImageView6;
import trophy.projetc2.Navigation.Last_Contest_ImageView.Last_Contest_ImageView7;
import trophy.projetc2.Navigation.Last_Contest_ImageView.Last_Contest_ImageView8;
import trophy.projetc2.Navigation.Last_Contest_ImageView.Last_Contest_ImageView9;
import trophy.projetc2.R;


/**
 * Created by 박효근 on 2017-01-10.
 */

public class Last_Contest_ViewPager extends AppCompatActivity {
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private ImageView Last_Contest_ImageView_Back;
    private ImageView Last_Contest_ImageView_Save;
    static String Images;
    public static String[] imgs;
    public static String Num;
    public static String Line;
    public static String Last_Contest_ViewPager_Pk=null;
    public static String Pk=null;
    public static String PictureCount="0";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_last_contest_viewpager);

        Intent intent = getIntent();
        Images = intent.getStringExtra("Images");
        Num = intent.getStringExtra("Num");
        Line = intent.getStringExtra("Line");
        Last_Contest_ViewPager_Pk = intent.getStringExtra("Pk")+"_";

        Last_Contest_ImageView_Back = (ImageView)findViewById(R.id.Last_Contest_ImageView_Back);
        Last_Contest_ImageView_Save = (ImageView)findViewById(R.id.Last_Contest_ImageView_Save);
        Last_Contest_ImageView_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Last_Contest_ImageView_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String imgUrl = "http://210.122.7.193:8080/Trophy_img/last_contest/" +  Last_Contest_ViewPager_Pk + PictureCount+ ".jpg";
                new ImageDownload(getApplication()).execute(imgUrl);
                Snackbar.make(view, "사진이저장되었습니다.", Snackbar.LENGTH_SHORT).show();
            }
        });
        Last_Contest_ImageView_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
            }
        });

        //프래그먼트 정의
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.Contest_container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem((Integer.parseInt(Line) * 3 + Integer.parseInt(Num)));
        //mViewPager.setOffscreenPageLimit(1);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        //client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                PictureCount = Integer.toString(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

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
            switch (position) {
                case 0:
                    fragment = new Last_Contest_ImageView1();
                    break;
                case 1:
                    fragment = new Last_Contest_ImageView2();
                    break;
                case 2:
                    fragment = new Last_Contest_ImageView3();
                    break;
                case 3:
                    fragment = new Last_Contest_ImageView4();
                    break;
                case 4:
                    fragment = new Last_Contest_ImageView5();
                    break;
                case 5:
                    fragment = new Last_Contest_ImageView6();
                    break;
                case 6:
                    fragment = new Last_Contest_ImageView7();
                    break;
                case 7:
                    fragment = new Last_Contest_ImageView8();
                    break;
                case 8:
                    fragment = new Last_Contest_ImageView9();
                    break;
                case 9:
                    fragment = new Last_Contest_ImageView10();
                    break;
                case 10:
                    fragment = new Last_Contest_ImageView11();
                    break;
                case 11:
                    fragment = new Last_Contest_ImageView12();
                    break;
                case 12:
                    fragment = new Last_Contest_ImageView13();
                    break;
                case 13:
                    fragment = new Last_Contest_ImageView14();
                    break;
                case 14:
                    fragment = new Last_Contest_ImageView15();
                    break;
                case 15:
                    fragment = new Last_Contest_ImageView16();
                    break;
                case 16:
                    fragment = new Last_Contest_ImageView17();
                    break;
                case 17:
                    fragment = new Last_Contest_ImageView18();
                    break;
                case 18:
                    fragment = new Last_Contest_ImageView19();
                    break;
                case 19:
                    fragment = new Last_Contest_ImageView20();
                    break;
                case 20:
                    fragment = new Last_Contest_ImageView21();
                    break;
                case 21:
                    fragment = new Last_Contest_ImageView22();
                    break;
                case 22:
                    fragment = new Last_Contest_ImageView23();
                    break;
                case 23:
                    fragment = new Last_Contest_ImageView24();
                    break;
                case 24:
                    fragment = new Last_Contest_ImageView25();
                    break;
                case 25:
                    fragment = new Last_Contest_ImageView26();
                    break;
                case 26:
                    fragment = new Last_Contest_ImageView27();
                    break;
                case 27:
                    fragment = new Last_Contest_ImageView28();
                    break;
                case 28:
                    fragment = new Last_Contest_ImageView29();
                    break;
                case 29:
                    fragment = new Last_Contest_ImageView30();
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            imgs = Images.split("/");
            return imgs.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
    }
}
