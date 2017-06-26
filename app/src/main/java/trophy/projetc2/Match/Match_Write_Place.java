package trophy.projetc2.Match;

import android.content.ClipData;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPointBounds;
import net.daum.mf.map.api.MapView;

import java.util.HashMap;
import java.util.List;

import trophy.projetc2.Match.search.Item;
import trophy.projetc2.Match.search.OnFinishSearchListener;
import trophy.projetc2.Match.search.Searcher;
import trophy.projetc2.R;

/**
 * Created by 박효근 on 2017-06-21.
 */

public class Match_Write_Place extends AppCompatActivity {
    EditText Layout_Navigation_Match_Write_Place_Edit;
    Button Layout_Navigation_Match_Write_Place_Push;
    MapView mapView;
    String API="ebfeaf6de7984dff9e6deb80c48f8413";
    private HashMap<Integer, Item> mTagItemMap = new HashMap<Integer, Item>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_navigation_match_write_place);
        Layout_Navigation_Match_Write_Place_Edit = (EditText)findViewById(R.id.Layout_Navigation_Match_Write_Place_Edit);
        Layout_Navigation_Match_Write_Place_Push = (Button)findViewById(R.id.Layout_Navigation_Match_Write_Place_Push);
        mapView = new MapView(Match_Write_Place.this);
        mapView.setDaumMapApiKey(API);

        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);
        Layout_Navigation_Match_Write_Place_Push.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query = Layout_Navigation_Match_Write_Place_Edit.getText().toString();
                if (query == null || query.length() == 0) {
                    Layout_Navigation_Match_Write_Place_Edit.setHint("주소를 입력해주세요.");
                    return;
                }
                //hideSoftKeyboard(); // 키보드 숨김
                MapPoint.GeoCoordinate geoCoordinate = mapView.getMapCenterPoint().getMapPointGeoCoord();
                double latitude = geoCoordinate.latitude; // 위도
                double longitude = geoCoordinate.longitude; // 경도
                int radius = 10000; // 중심 좌표부터의 반경거리. 특정 지역을 중심으로 검색하려고 할 경우 사용. meter 단위 (0 ~ 10000)
                int page = 1; // 페이지 번호 (1 ~ 3). 한페이지에 15개
                String apikey = "ebfeaf6de7984dff9e6deb80c48f8413";

                Searcher searcher = new Searcher(); // net.daum.android.map.openapi.search.Searcher
                searcher.searchKeyword(getApplicationContext(), query, latitude, longitude, radius, page, apikey, new OnFinishSearchListener() {
                    @Override
                    public void onSuccess(List<Item> itemList) {
                        mapView.removeAllPOIItems(); // 기존 검색 결과 삭제
                        showResult(itemList); // 검색 결과 보여줌
                    }

                    @Override
                    public void onFail() {
                        //showToast("API_KEY의 제한 트래픽이 초과되었습니다.");
                    }
                });
            }
        });
    }
    private void showResult(List<Item> itemList) {
        MapPointBounds mapPointBounds = new MapPointBounds();

        for (int i = 0; i < itemList.size(); i++) {
            Item item = itemList.get(i);

            MapPOIItem poiItem = new MapPOIItem();
            poiItem.setItemName(item.title);
            poiItem.setTag(i);
            MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(item.latitude, item.longitude);
            poiItem.setMapPoint(mapPoint);
            mapPointBounds.add(mapPoint);
            poiItem.setMarkerType(MapPOIItem.MarkerType.CustomImage);
            poiItem.setCustomImageResourceId(R.drawable.map_pin_blue);
            poiItem.setSelectedMarkerType(MapPOIItem.MarkerType.CustomImage);
            poiItem.setCustomSelectedImageResourceId(R.drawable.map_pin_red);
            poiItem.setCustomImageAutoscale(false);
            poiItem.setCustomImageAnchor(0.5f, 1.0f);

            mapView.addPOIItem(poiItem);
            mTagItemMap.put(poiItem.getTag(), item);
        }

        mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds));

        MapPOIItem[] poiItems = mapView.getPOIItems();
        if (poiItems.length > 0) {
            mapView.selectPOIItem(poiItems[0], false);
        }
    }

}
