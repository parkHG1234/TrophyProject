package trophy.projetc2.Navigation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import trophy.projetc2.R;

/**
 * Created by 박효근 on 2017-05-01.
 */

public class OutCourt_Address extends AppCompatActivity{
    ImageView OutCourt_Address_ImageView_Back;
    TextView Layout_Navigation_OutCourt_Address_Seoul, Layout_Navigation_OutCourt_Address_Gyeonggido, Layout_Navigation_OutCourt_Address_Incheon,
            Layout_Navigation_OutCourt_Address_GangWon,Layout_Navigation_OutCourt_Address_DeaJeon,Layout_Navigation_OutCourt_Address_ChungBuk,
            Layout_Navigation_OutCourt_Address_ChungNam, Layout_Navigation_OutCourt_Address_Busan, Layout_Navigation_OutCourt_Address_Ulsan,
            Layout_Navigation_OutCourt_Address_GyeongNam, Layout_Navigation_OutCourt_Address_DeaGu, Layout_Navigation_OutCourt_Address_GyeongBuk,
            Layout_Navigation_OutCourt_Address_GangJu, Layout_Navigation_OutCourt_Address_JeoNam, Layout_Navigation_OutCourt_Address_JeoBuk,
            Layout_Navigation_OutCourt_Address_JeJu;
    ListView Layout_Navigation_OutCourt_Address_ListView;
    String Choice="서울";
    Activity activity;String User_Pk;
    String[] Se_Seoul;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_navigation_outcourt_address);
        activity = OutCourt_Address.this;
        Intent intent1 = getIntent();
        User_Pk = intent1.getStringExtra("User_Pk");
        OutCourt_Address_ImageView_Back = (ImageView)findViewById(R.id.OutCourt_Address_ImageView_Back);
        Layout_Navigation_OutCourt_Address_Seoul= (TextView)findViewById(R.id.Layout_Navigation_OutCourt_Address_Seoul);
        Layout_Navigation_OutCourt_Address_Gyeonggido= (TextView)findViewById(R.id.Layout_Navigation_OutCourt_Address_Gyeonggido);
        Layout_Navigation_OutCourt_Address_Incheon= (TextView)findViewById(R.id.Layout_Navigation_OutCourt_Address_Incheon);
        Layout_Navigation_OutCourt_Address_GangWon= (TextView)findViewById(R.id.Layout_Navigation_OutCourt_Address_GangWon);
        Layout_Navigation_OutCourt_Address_DeaJeon= (TextView)findViewById(R.id.Layout_Navigation_OutCourt_Address_DeaJeon);
        Layout_Navigation_OutCourt_Address_ChungBuk= (TextView)findViewById(R.id.Layout_Navigation_OutCourt_Address_ChungBuk);
        Layout_Navigation_OutCourt_Address_ChungNam= (TextView)findViewById(R.id.Layout_Navigation_OutCourt_Address_ChungNam);
        Layout_Navigation_OutCourt_Address_Busan= (TextView)findViewById(R.id.Layout_Navigation_OutCourt_Address_Busan);
        Layout_Navigation_OutCourt_Address_Ulsan= (TextView)findViewById(R.id.Layout_Navigation_OutCourt_Address_Ulsan);
        Layout_Navigation_OutCourt_Address_GyeongNam= (TextView)findViewById(R.id.Layout_Navigation_OutCourt_Address_GyeongNam);
        Layout_Navigation_OutCourt_Address_DeaGu= (TextView)findViewById(R.id.Layout_Navigation_OutCourt_Address_DeaGu);
        Layout_Navigation_OutCourt_Address_GyeongBuk= (TextView)findViewById(R.id.Layout_Navigation_OutCourt_Address_GyeongBuk);
        Layout_Navigation_OutCourt_Address_GangJu= (TextView)findViewById(R.id.Layout_Navigation_OutCourt_Address_GangJu);
        Layout_Navigation_OutCourt_Address_JeoNam= (TextView)findViewById(R.id.Layout_Navigation_OutCourt_Address_JeoNam);
        Layout_Navigation_OutCourt_Address_JeoBuk= (TextView)findViewById(R.id.Layout_Navigation_OutCourt_Address_JeoBuk);
        Layout_Navigation_OutCourt_Address_JeJu= (TextView)findViewById(R.id.Layout_Navigation_OutCourt_Address_JeJu);
        Layout_Navigation_OutCourt_Address_ListView = (ListView)findViewById(R.id.Layout_Navigation_OutCourt_Address_ListView);

        Layout_Navigation_OutCourt_Address_Seoul.setBackgroundColor(getResources().getColor(R.color.White));
        Layout_Navigation_OutCourt_Address_Gyeonggido.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
        Layout_Navigation_OutCourt_Address_Incheon.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
        Layout_Navigation_OutCourt_Address_GangWon.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
        Layout_Navigation_OutCourt_Address_DeaJeon.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
        Layout_Navigation_OutCourt_Address_ChungBuk.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
        Layout_Navigation_OutCourt_Address_ChungNam.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
        Layout_Navigation_OutCourt_Address_Busan.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
        Layout_Navigation_OutCourt_Address_Ulsan.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
        Layout_Navigation_OutCourt_Address_GyeongNam.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
        Layout_Navigation_OutCourt_Address_DeaGu.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
        Layout_Navigation_OutCourt_Address_GyeongBuk.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
        Layout_Navigation_OutCourt_Address_GangJu.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
        Layout_Navigation_OutCourt_Address_JeoNam.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
        Layout_Navigation_OutCourt_Address_JeoBuk.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
        Layout_Navigation_OutCourt_Address_JeJu.setBackgroundColor(getResources().getColor(R.color.WhiteGray));

        final ArrayList<OutCourt_Address_MyData> OutCourt_Address_MyData;
        OutCourt_Address_MyData = new ArrayList<OutCourt_Address_MyData>();
        OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "서울","강남/서초"));
        OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "서울","강동/송파"));
        OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "서울","강서/양천"));
        OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "서울","구로/영등포/여의도"));
        OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "서울","금천/관악/동작"));
        OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "서울","동대문/중랑/성동/광진"));
        OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "서울","도봉/노원/강북/성북"));
        OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "서울","종로/중구/용산"));
        OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "서울","은평/서대문/마포"));
        final OutCourt_Address_MyAdapter adapter = new OutCourt_Address_MyAdapter(this, OutCourt_Address_MyData);
        Layout_Navigation_OutCourt_Address_ListView.setAdapter(adapter);

        Layout_Navigation_OutCourt_Address_Seoul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Choice="서울";
                OutCourt_Address_MyData.clear();
                OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "서울","강남/서초"));
                OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "서울","강동/송파"));
                OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "서울","강서/양천"));
                OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "서울","구로/영등포/여의도"));
                OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "서울","금천/관악/동작"));
                OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "서울","동대문/중랑/성동/광진"));
                OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "서울","도봉/노원/강북/성북"));
                OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "서울","종로/중구/용산"));
                OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "서울","은평/서대문/마포"));
                adapter.notifyDataSetChanged();
                Layout_Navigation_OutCourt_Address_Seoul.setBackgroundColor(getResources().getColor(R.color.White));
                Layout_Navigation_OutCourt_Address_Gyeonggido.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_Incheon.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_GangWon.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_DeaJeon.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_ChungBuk.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_ChungNam.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_Busan.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_Ulsan.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_GyeongNam.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_DeaGu.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_GyeongBuk.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_GangJu.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_JeoNam.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_JeoBuk.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_JeJu.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
            }
        });
        Layout_Navigation_OutCourt_Address_Gyeonggido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Choice="경기";
                OutCourt_Address_MyData.clear();
                OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "경기도","김포/고양/파주"));
                OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "경기도","부천/광명/시흥"));
                OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "경기도","안산/화성/오산/평택"));
                OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "경기도","안양/군포/과천/의왕"));
                OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "경기도","성남/수원"));
                OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "경기도","용인/안성"));
                OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "경기도","하남/광주"));
                OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "경기도","양평/여주/이천"));
                OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "경기도","양주/의정부/동두천"));
                OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "경기도","연천/포천/가평"));
                OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "경기도","구리/남양주"));
                adapter.notifyDataSetChanged();
                Layout_Navigation_OutCourt_Address_Seoul.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_Gyeonggido.setBackgroundColor(getResources().getColor(R.color.White));
                Layout_Navigation_OutCourt_Address_Incheon.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_GangWon.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_DeaJeon.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_ChungBuk.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_ChungNam.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_Busan.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_Ulsan.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_GyeongNam.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_DeaGu.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_GyeongBuk.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_GangJu.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_JeoNam.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_JeoBuk.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_JeJu.setBackgroundColor(getResources().getColor(R.color.WhiteGray));

            }
        });
        Layout_Navigation_OutCourt_Address_Incheon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Choice="인천";
                OutCourt_Address_MyData.clear();
                OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "인천","부평/계양"));
                OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "인천","서구/동구"));
                OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "인천","남구/남동구/연수"));
                OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "인천","강화/중구/옹진"));
                adapter.notifyDataSetChanged();
                Layout_Navigation_OutCourt_Address_Seoul.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_Gyeonggido.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_Incheon.setBackgroundColor(getResources().getColor(R.color.White));
                Layout_Navigation_OutCourt_Address_GangWon.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_DeaJeon.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_ChungBuk.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_ChungNam.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_Busan.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_Ulsan.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_GyeongNam.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_DeaGu.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_GyeongBuk.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_GangJu.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_JeoNam.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_JeoBuk.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_JeJu.setBackgroundColor(getResources().getColor(R.color.WhiteGray));


            }
        });
        Layout_Navigation_OutCourt_Address_GangWon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Choice="강원";
                OutCourt_Address_MyData.clear();
                OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "강원도","철원/화천/양구"));
                OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "강원도","춘천/홍천"));
                OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "강원도","양구/인제"));
                OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "강원도","고성/속초/양양"));
                OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "강원도","원주/횡성/평창"));
                OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "강원도","영월/정선/강릉"));
                OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "강원도","동해/삼척/태백"));
                adapter.notifyDataSetChanged();
                Layout_Navigation_OutCourt_Address_Seoul.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_Gyeonggido.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_Incheon.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_GangWon.setBackgroundColor(getResources().getColor(R.color.White));
                Layout_Navigation_OutCourt_Address_DeaJeon.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_ChungBuk.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_ChungNam.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_Busan.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_Ulsan.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_GyeongNam.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_DeaGu.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_GyeongBuk.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_GangJu.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_JeoNam.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_JeoBuk.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_JeJu.setBackgroundColor(getResources().getColor(R.color.WhiteGray));


            }
        });
        Layout_Navigation_OutCourt_Address_DeaJeon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Choice="대전";
                OutCourt_Address_MyData.clear();
                OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "대전","유성구"));
                OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "대전","중구/서구"));
                OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "대전","동구/대덕구"));
                adapter.notifyDataSetChanged();
                Layout_Navigation_OutCourt_Address_Seoul.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_Gyeonggido.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_Incheon.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_GangWon.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_DeaJeon.setBackgroundColor(getResources().getColor(R.color.White));
                Layout_Navigation_OutCourt_Address_ChungBuk.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_ChungNam.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_Busan.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_Ulsan.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_GyeongNam.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_DeaGu.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_GyeongBuk.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_GangJu.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_JeoNam.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_JeoBuk.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_JeJu.setBackgroundColor(getResources().getColor(R.color.WhiteGray));

            }
        });
        Layout_Navigation_OutCourt_Address_ChungBuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Choice="충북";
                OutCourt_Address_MyData.clear();
                OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "충청북도","청주"));
                OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "충청북도","충주/제천/진천/음성/단양"));
                OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "충청북도","보은/옥천/괴산/증평/영동"));
                adapter.notifyDataSetChanged();
                Layout_Navigation_OutCourt_Address_Seoul.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_Gyeonggido.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_Incheon.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_GangWon.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_DeaJeon.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_ChungBuk.setBackgroundColor(getResources().getColor(R.color.White));
                Layout_Navigation_OutCourt_Address_ChungNam.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_Busan.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_Ulsan.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_GyeongNam.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_DeaGu.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_GyeongBuk.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_GangJu.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_JeoNam.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_JeoBuk.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_JeJu.setBackgroundColor(getResources().getColor(R.color.WhiteGray));

            }
        });
        Layout_Navigation_OutCourt_Address_ChungNam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Choice="충남";
                OutCourt_Address_MyData.clear();
                OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "충청남도","천안/세종"));
                OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "충청남도","논산/계룡/공주/금산"));
                OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "충청남도","태안/서산/당진/아산/예산"));
                OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "충청남도","보령/홍성/서천/부여/청양"));
                adapter.notifyDataSetChanged();
                Layout_Navigation_OutCourt_Address_Seoul.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_Gyeonggido.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_Incheon.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_GangWon.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_DeaJeon.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_ChungBuk.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_ChungNam.setBackgroundColor(getResources().getColor(R.color.White));
                Layout_Navigation_OutCourt_Address_Busan.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_Ulsan.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_GyeongNam.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_DeaGu.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_GyeongBuk.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_GangJu.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_JeoNam.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_JeoBuk.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_JeJu.setBackgroundColor(getResources().getColor(R.color.WhiteGray));

            }
        });
        Layout_Navigation_OutCourt_Address_Busan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Choice="부산";
                OutCourt_Address_MyData.clear();
                OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "부산","강서/사하/사상"));
                OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "부산","서구/동구/중구/남구/영도"));
                OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "부산","부산진구/북구"));
                OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "부산","동래/연제/수영"));
                OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "부산","금정/해운대/기장"));
                adapter.notifyDataSetChanged();
                Layout_Navigation_OutCourt_Address_Seoul.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_Gyeonggido.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_Incheon.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_GangWon.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_DeaJeon.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_ChungBuk.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_ChungNam.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_Busan.setBackgroundColor(getResources().getColor(R.color.White));
                Layout_Navigation_OutCourt_Address_Ulsan.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_GyeongNam.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_DeaGu.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_GyeongBuk.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_GangJu.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_JeoNam.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_JeoBuk.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_JeJu.setBackgroundColor(getResources().getColor(R.color.WhiteGray));

            }
        });
        Layout_Navigation_OutCourt_Address_Ulsan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Choice="울산";
                OutCourt_Address_MyData.clear();
                OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "울산","남구/중구"));
                OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "울산","동구/북구/울주군"));
                adapter.notifyDataSetChanged();
                Layout_Navigation_OutCourt_Address_Seoul.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_Gyeonggido.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_Incheon.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_GangWon.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_DeaJeon.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_ChungBuk.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_ChungNam.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_Busan.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_Ulsan.setBackgroundColor(getResources().getColor(R.color.White));
                Layout_Navigation_OutCourt_Address_GyeongNam.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_DeaGu.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_GyeongBuk.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_GangJu.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_JeoNam.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_JeoBuk.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_JeJu.setBackgroundColor(getResources().getColor(R.color.WhiteGray));

            }
        });
        Layout_Navigation_OutCourt_Address_GyeongNam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Choice="경남";
                OutCourt_Address_MyData.clear();
                OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "경상남도","창원"));
                OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "경상남도","마산/진해"));
                OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "경상남도","김해/양산/밀양"));
                OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "경상남도","진주/사천/남해"));
                OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "경상남도","거제/통영/거창/하동"));
                adapter.notifyDataSetChanged();
                Layout_Navigation_OutCourt_Address_Seoul.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_Gyeonggido.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_Incheon.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_GangWon.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_DeaJeon.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_ChungBuk.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_ChungNam.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_Busan.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_Ulsan.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_GyeongNam.setBackgroundColor(getResources().getColor(R.color.White));
                Layout_Navigation_OutCourt_Address_DeaGu.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_GyeongBuk.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_GangJu.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_JeoNam.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_JeoBuk.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_JeJu.setBackgroundColor(getResources().getColor(R.color.WhiteGray));

            }
        });
        Layout_Navigation_OutCourt_Address_DeaGu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Choice="대구";
                OutCourt_Address_MyData.clear();
                OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "대구","동구/북구"));
                OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "대구","서구/중구/남구"));
                OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "대구","수성구/달서구/달성군"));
                adapter.notifyDataSetChanged();
                Layout_Navigation_OutCourt_Address_Seoul.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_Gyeonggido.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_Incheon.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_GangWon.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_DeaJeon.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_ChungBuk.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_ChungNam.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_Busan.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_Ulsan.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_GyeongNam.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_DeaGu.setBackgroundColor(getResources().getColor(R.color.White));
                Layout_Navigation_OutCourt_Address_GyeongBuk.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_GangJu.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_JeoNam.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_JeoBuk.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_JeJu.setBackgroundColor(getResources().getColor(R.color.WhiteGray));

            }
        });
        Layout_Navigation_OutCourt_Address_GyeongBuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Choice="경북";
                OutCourt_Address_MyData.clear();
                OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "경상북도","울진/봉화/영양/영덕/울릉군"));
                OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "경상북도","영주/문경/예천/안동"));
                OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "경상북도","상주/의성/청송"));
                OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "경상북도","포항/영천/군위/구미"));
                OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "경상북도","경주/경산/청도"));
                OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "경상북도","김천/성주/칠곡/고령"));
                adapter.notifyDataSetChanged();
                Layout_Navigation_OutCourt_Address_Seoul.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_Gyeonggido.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_Incheon.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_GangWon.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_DeaJeon.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_ChungBuk.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_ChungNam.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_Busan.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_Ulsan.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_GyeongNam.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_DeaGu.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_GyeongBuk.setBackgroundColor(getResources().getColor(R.color.White));
                Layout_Navigation_OutCourt_Address_GangJu.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_JeoNam.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_JeoBuk.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_JeJu.setBackgroundColor(getResources().getColor(R.color.WhiteGray));

            }
        });
        Layout_Navigation_OutCourt_Address_GangJu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Choice="광주";
                OutCourt_Address_MyData.clear();
                OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "광주","서구/남구"));
                OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "광주","광산구/북구/동구"));
                adapter.notifyDataSetChanged();
                Layout_Navigation_OutCourt_Address_Seoul.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_Gyeonggido.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_Incheon.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_GangWon.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_DeaJeon.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_ChungBuk.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_ChungNam.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_Busan.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_Ulsan.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_GyeongNam.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_DeaGu.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_GyeongBuk.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_GangJu.setBackgroundColor(getResources().getColor(R.color.White));
                Layout_Navigation_OutCourt_Address_JeoNam.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_JeoBuk.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_JeJu.setBackgroundColor(getResources().getColor(R.color.WhiteGray));

            }
        });
        Layout_Navigation_OutCourt_Address_JeoNam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Choice="전남";
                OutCourt_Address_MyData.clear();
                OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "전라남도","여수"));
                OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "전라남도","순천/광양"));
                OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "전라남도","목포/무안/나주"));
                OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "전라남도","전남"));
                adapter.notifyDataSetChanged();
                Layout_Navigation_OutCourt_Address_Seoul.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_Gyeonggido.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_Incheon.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_GangWon.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_DeaJeon.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_ChungBuk.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_ChungNam.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_Busan.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_Ulsan.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_GyeongNam.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_DeaGu.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_GyeongBuk.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_GangJu.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_JeoNam.setBackgroundColor(getResources().getColor(R.color.White));
                Layout_Navigation_OutCourt_Address_JeoBuk.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_JeJu.setBackgroundColor(getResources().getColor(R.color.WhiteGray));

            }
        });
        Layout_Navigation_OutCourt_Address_JeoBuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Choice="전북";
                OutCourt_Address_MyData.clear();
                OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "전라북도","전주/완주"));
                OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "전라북도","군산/익산"));
                OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "전라북도","남원/정읍/부안/김제"));
                adapter.notifyDataSetChanged();
                Layout_Navigation_OutCourt_Address_Seoul.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_Gyeonggido.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_Incheon.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_GangWon.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_DeaJeon.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_ChungBuk.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_ChungNam.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_Busan.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_Ulsan.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_GyeongNam.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_DeaGu.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_GyeongBuk.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_GangJu.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_JeoNam.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_JeoBuk.setBackgroundColor(getResources().getColor(R.color.White));
                Layout_Navigation_OutCourt_Address_JeJu.setBackgroundColor(getResources().getColor(R.color.WhiteGray));

            }
        });
        Layout_Navigation_OutCourt_Address_JeJu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Choice="제주";
                OutCourt_Address_MyData.clear();
                OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "제주도","제주시"));
                OutCourt_Address_MyData.add(new OutCourt_Address_MyData(activity, User_Pk, "제주도","서귀포시"));
                adapter.notifyDataSetChanged();
                Layout_Navigation_OutCourt_Address_Seoul.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_Gyeonggido.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_Incheon.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_GangWon.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_DeaJeon.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_ChungBuk.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_ChungNam.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_Busan.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_Ulsan.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_GyeongNam.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_DeaGu.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_GyeongBuk.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_GangJu.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_JeoNam.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_JeoBuk.setBackgroundColor(getResources().getColor(R.color.WhiteGray));
                Layout_Navigation_OutCourt_Address_JeJu.setBackgroundColor(getResources().getColor(R.color.White));

            }
        });
        OutCourt_Address_ImageView_Back.setOnClickListener(new View.OnClickListener() {
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
