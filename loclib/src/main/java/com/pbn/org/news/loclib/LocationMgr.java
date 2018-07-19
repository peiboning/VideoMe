package com.pbn.org.news.loclib;

import android.content.Context;
import android.util.Log;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

//TODO 定位操作 放在子线程
public class LocationMgr {
    private static LocationMgr sInstance = null;
    private String county;
    private String prov;
    private String city;
    private String cityCode;
    private String street;
    private double latitude;
    private double longitude;
    private String buildName;
    public static LocationMgr getInstance() {
        if (null == sInstance) {
            synchronized (LocationMgr.class) {
                if (null == sInstance) {
                    sInstance = new LocationMgr();
                }
            }
        }
        return sInstance;
    }

    private LocationMgr() {
    }

    public String getCounty() {
        return county;
    }

    public String getProv() {
        return prov;
    }

    public String getCity() {
        return city;
    }

    public String getCityCode() {
        return cityCode;
    }

    public String getStreet() {
        return street;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    private LocationClient mLocationClient;
    private Context mContext;
    private LocationListener mLocationListener;

    public void init(Context context) {
        mContext = context;
    }

    public void startLoc(){
        mLocationClient = new LocationClient(mContext);
        mLocationClient.setLocOption(option());
        mLocationListener = new LocationListener();
        mLocationClient.registerLocationListener(mLocationListener);
        mLocationClient.start();
    }

    public void stopLoc(){
        if(null != mLocationClient){
            mLocationClient.unRegisterLocationListener(mLocationListener);
            mLocationClient.stop();
            mLocationClient = null;
        }
    }

    private LocationClientOption option() {
        LocationClientOption option = new LocationClientOption();

        //可选，设置定位模式，默认高精度
        //LocationMode.Hight_Accuracy：高精度；
        //LocationMode. Battery_Saving：低功耗；
        //LocationMode. Device_Sensors：仅使用设备；
        option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);

        //可选，设置返回经纬度坐标类型，默认gcj02
        //gcj02：国测局坐标；
        //bd09ll：百度经纬度坐标；
        //bd09：百度墨卡托坐标；
        //海外地区定位，无需设置坐标类型，统一返回wgs84类型坐标
        option.setCoorType("bd09ll");

        //可选，设置发起定位请求的间隔，int类型，单位ms
        //如果设置为0，则代表单次定位，即仅定位一次，默认为0
        //如果设置非0，需设置1000ms以上才有效
        option.setScanSpan(1000000);

        //可选，设置是否使用gps，默认false
        //使用高精度和仅用设备两种定位模式的，参数必须设置为true
//        option.setOpenGps(true);

        //可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false
//        option.setLocationNotify(true);

        //可选，定位SDK内部是一个service，并放到了独立进程。
        //设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)
        option.setIgnoreKillProcess(false);

        //可选，设置是否收集Crash信息，默认收集，即参数为false
        option.SetIgnoreCacheException(true);

        //可选，7.2版本新增能力
        //如果设置了该接口，首次启动定位时，会先判断当前WiFi是否超出有效期，若超出有效期，会先重新扫描WiFi，然后定位
        option.setWifiCacheTimeOut(5 * 60 * 1000);

        //可选，设置是否需要过滤GPS仿真结果，默认需要，即参数为false
        option.setEnableSimulateGps(false);

        //可选，是否需要地址信息，默认为不需要，即参数为false
        //如果开发者需要获得当前点的地址信息，此处必须为true
        option.setIsNeedAddress(true);

        return option;

    }

    private class LocationListener extends BDAbstractLocationListener{

        @Override
        public void onReceiveLocation(BDLocation location) {
            String addr = location.getAddrStr();    //获取详细地址信息
            county = location.getCountry();    //获取国家
            prov = location.getProvince();    //获取省份
            city = location.getCity();    //获取城市
            String district = location.getDistrict();    //获取区县
            street = location.getStreet();    //获取街道信息
            cityCode = location.getCityCode();
            latitude = location.getLatitude();
            longitude = location.getLongitude();

            Log.e("onReceiveLocation", "addr:" + addr);
            Log.e("onReceiveLocation", "country:" + county);
            Log.e("onReceiveLocation", "province:" + prov);
            Log.e("onReceiveLocation", "city:" + city);
            Log.e("onReceiveLocation", "district:" + district);
            Log.e("onReceiveLocation", "street:" + street);
            Log.e("onReceiveLocation", "buildName:" + buildName);
            stopLoc();
        }
    }
}