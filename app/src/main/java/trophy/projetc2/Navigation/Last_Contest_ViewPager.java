package trophy.projetc2.Navigation;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.api.GoogleApiClient;

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
    static String Images;
    public static String[] imgs;
    public static String Num;
    public static String Line;
    public static String Pk=null;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_last_contest_viewpager);
        Intent intent = getIntent();
        Images = intent.getStringExtra("Images");
        Num = intent.getStringExtra("Num");
        Line = intent.getStringExtra("Line");
        Pk = intent.getStringExtra("Pk")+"_";

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
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
//    public Action getIndexApiAction() {
//        Thing object = new Thing.Builder()
//                .setName("Last_Contest_ViewPager Page") // TODO: Define a title for the content shown.
//                // TODO: Make sure this auto-generated URL is correct.
//                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
//                .build();
//        return new Action.Builder(Action.TYPE_VIEW)
//                .setObject(object)
//                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
//                .build();
//    }

//    @Override
//    public void onStart() {
//        super.onStart();
//
//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        client.connect();
//        AppIndex.AppIndexApi.start(client, getIndexApiAction());
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//
//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        AppIndex.AppIndexApi.end(client, getIndexApiAction());
//        client.disconnect();
//    }

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
                    fragment = new Last_Contest_ImageView1(Pk);
                    break;
                case 1:
                    fragment = new Last_Contest_ImageView2(Pk);
                    break;
                case 2:
                    fragment = new Last_Contest_ImageView3(Pk);
                    break;
                case 3:
                    fragment = new Last_Contest_ImageView4(Pk);
                    break;
                case 4:
                    fragment = new Last_Contest_ImageView5(Pk);
                    break;
                case 5:
                    fragment = new Last_Contest_ImageView6(Pk);
                    break;
                case 6:
                    fragment = new Last_Contest_ImageView7(Pk);
                    break;
                case 7:
                    fragment = new Last_Contest_ImageView8(Pk);
                    break;
                case 8:
                    fragment = new Last_Contest_ImageView9(Pk);
                    break;
                case 9:
                    fragment = new Last_Contest_ImageView10(Pk);
                    break;
                case 10:
                    fragment = new Last_Contest_ImageView11(Pk);
                    break;
                case 11:
                    fragment = new Last_Contest_ImageView12(Pk);
                    break;
                case 12:
                    fragment = new Last_Contest_ImageView13(Pk);
                    break;
                case 13:
                    fragment = new Last_Contest_ImageView14(Pk);
                    break;
                case 14:
                    fragment = new Last_Contest_ImageView15(Pk);
                    break;
                case 15:
                    fragment = new Last_Contest_ImageView16(Pk);
                    break;
                case 16:
                    fragment = new Last_Contest_ImageView17(Pk);
                    break;
                case 17:
                    fragment = new Last_Contest_ImageView18(Pk);
                    break;
                case 18:
                    fragment = new Last_Contest_ImageView19(Pk);
                    break;
                case 19:
                    fragment = new Last_Contest_ImageView20(Pk);
                    break;
                case 20:
                    fragment = new Last_Contest_ImageView21(Pk);
                    break;
                case 21:
                    fragment = new Last_Contest_ImageView22(Pk);
                    break;
                case 22:
                    fragment = new Last_Contest_ImageView23(Pk);
                    break;
                case 23:
                    fragment = new Last_Contest_ImageView24(Pk);
                    break;
                case 24:
                    fragment = new Last_Contest_ImageView25(Pk);
                    break;
                case 25:
                    fragment = new Last_Contest_ImageView26(Pk);
                    break;
                case 26:
                    fragment = new Last_Contest_ImageView27(Pk);
                    break;
                case 27:
                    fragment = new Last_Contest_ImageView28(Pk);
                    break;
                case 28:
                    fragment = new Last_Contest_ImageView29(Pk);
                    break;
                case 29:
                    fragment = new Last_Contest_ImageView30(Pk);
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
}
