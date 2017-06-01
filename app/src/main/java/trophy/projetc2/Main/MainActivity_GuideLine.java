package trophy.projetc2.Main;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.matthewtamlin.sliding_intro_screen_library.DotIndicator;
import android.support.v4.app.FragmentPagerAdapter;
import trophy.projetc2.MainActivity;
import trophy.projetc2.R;

/**
 * Created by 박효근 on 2017-06-01.
 */

public class MainActivity_GuideLine extends AppCompatActivity{
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_guideline);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        final DotIndicator indicator = (DotIndicator) findViewById(R.id.main_indicator_ad);
        // 도트 색 지정
        indicator.setSelectedDotColor(Color.parseColor("#F96332"));
        indicator.setUnselectedDotColor(Color.parseColor("#CFCFCF"));

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(3);
        ////////////
        final int pageCount = 4;
        indicator.setNumberOfItems(pageCount);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                indicator.setSelectedItem(mViewPager.getCurrentItem(), true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
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
                    fragment = new MainActivity_SlideMenu1_ViewPage1();
                    args = new Bundle();
                    break;
                case 1:
                    fragment = new MainActivity_SlideMenu1_ViewPage2();
                    args = new Bundle();
                    break;
                case 2:
                    fragment = new MainActivity_SlideMenu1_ViewPage3();
                    args = new Bundle();
                    break;
                case 3:
                    fragment = new MainActivity_SlideMenu1_ViewPage4();
                    args = new Bundle();
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.anim_slide_in_top, R.anim.anim_slide_out_bottom);
    }
}
