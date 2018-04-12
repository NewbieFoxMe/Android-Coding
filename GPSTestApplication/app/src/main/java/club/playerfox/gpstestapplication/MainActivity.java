package club.playerfox.gpstestapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LocationClient mLocationClient;
    private TextView show;
    private MapView bdMap;
    private boolean isFirstLocate = true;
    private BaiduMap baiduMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        show = findViewById(R.id.show);
        bdMap = findViewById(R.id.bd_map);

        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()){
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(this,permissions,1);
        }else {
            requestLocation();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        bdMap.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        bdMap.onResume();
    }

    private void requestLocation() {
        initLocation();
        mLocationClient.start();
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setIsNeedAddress(true);
        option.setCoorType("bd09ll");
        option.setLocationNotify(true);
        option.setScanSpan(5000);
        mLocationClient.setLocOption(option);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
        bdMap.onDestroy();
        baiduMap.setMyLocationEnabled(false);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length > 0){
                    for (int res : grantResults){
                        if (res != PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(this,"denied",Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    requestLocation();
                }
                break;
        }
    }

    class MyLocationListener extends BDAbstractLocationListener{

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            StringBuilder currentPosition = new StringBuilder();
            currentPosition.append("纬度:").append(bdLocation.getLatitude()).append("\n");
            currentPosition.append("经度:").append(bdLocation.getLongitude()).append("\n");
            currentPosition.append("定位方式:");
            if (bdLocation.getLocType() == BDLocation.TypeGpsLocation){
                currentPosition.append("GPS").append("\n");
            }else if (bdLocation.getLocType() == BDLocation.TypeNetWorkLocation){
                currentPosition.append("Network").append("\n");
                currentPosition.append("国家:").append(bdLocation.getCountry()).append("\n");
                currentPosition.append("省:").append(bdLocation.getProvince()).append("\n");
                currentPosition.append("市:").append(bdLocation.getCity()).append("\n");
                currentPosition.append("区:").append(bdLocation.getDistrict()).append("\n");
                currentPosition.append("街道:").append(bdLocation.getStreet()).append("\n");
                currentPosition.append("门牌:").append(bdLocation.getStreetNumber()).append("\n");
            }
            show.setText(currentPosition.toString());
            moveTo(bdLocation);
        }
    }

    private void moveTo(BDLocation bdLocation) {
        if (isFirstLocate){
            baiduMap = bdMap.getMap();
            baiduMap.setMyLocationEnabled(true);
            LatLng latLng = new LatLng(bdLocation.getLatitude(),bdLocation.getLongitude());
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLngZoom(latLng,19);
            baiduMap.animateMapStatus(update);
            isFirstLocate = false;
        }

        MyLocationData.Builder builder = new MyLocationData.Builder();
        builder.latitude(bdLocation.getLatitude());
        builder.longitude(bdLocation.getLongitude());
        builder.accuracy(bdLocation.getRadius());
        builder.direction(100);
        MyLocationData locationData = builder.build();
        baiduMap.setMyLocationData(locationData);

    }
}
