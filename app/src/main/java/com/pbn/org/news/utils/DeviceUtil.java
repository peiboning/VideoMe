package com.pbn.org.news.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.CellLocation;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import com.pbn.org.news.NewsApplication;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.UUID;

/**
 * 获取设备相关信息
 */

public class DeviceUtil {

    private static DeviceUtil instance;
    TelephonyManager tm;

    /** 设备唯一标识 IMEI/MEID/ESN **/
    private String deviceId;
    /** 国际移动用户识别码 **/
    private String imsi;
    /** 设备序列号 **/
    private String serialNumber;
    /** 设备首次启动生成Id **/
    private String androidId;
    /** 应用唯一标识(卸载重装后会发生变化) **/
    private String guid;
    /** 获取mac address **/
    private String macAddress;
    /** 设备唯一标识(客户端自定义,标识唯一设备) **/
    private String did;
    /** 设备名称 **/
    private String deviceModel ;
    /** 移动设备标识 **/
    private String imei;
    /** 移动设备标识 **/
    private String appName;
    private String appVersionName;
    private String appVersionCode;
    private float screenDesity;
    private int screenDesityDpi;
    private String screenSize;

    /** 路由器热点名称 **/
    private String ssid;
    /** Mobile Network Code，移动网络号码（中国移动为00，中国联通为01) **/
    private String mnc;
    /** Mobile Country Code，移动国家代码（中国的为460）**/
    private String mcc;
    /** Location Area Code 位置区域码 **/
    private String lac;
    /** Cell Identity，基站编号，是个16位的数据（范围是0到65535) **/
    private String cellid;
    /** 网络运营商 **/
    private String carrier;
    /** 无线路由的MAC地址 **/
    private String bssid;

    private Context mContext;

    private static final String FOLDER_NAME = "QuickNews";
    private static final String FILE_NAME = "INSTALLATION";

    private DeviceUtil(){
        mContext = NewsApplication.getContext();
        tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
    }

    public static DeviceUtil getInstance() {
        if (instance == null) {
            instance = new DeviceUtil();
        }
        return instance;
    }

    public String getDeviceId() {
        try {
            if (TextUtils.isEmpty(deviceId)) {
                deviceId = "" + tm.getDeviceId();
            }
            return deviceId;
        } catch (Exception e) {
            return "";
        }
    }

    public String getSerialNumber() {
        try {
            if (TextUtils.isEmpty(serialNumber)) {
                serialNumber = "" + tm.getSimSerialNumber();
            }
            return serialNumber;
        } catch (Exception e) {
            return "";
        }
    }

    public String getAndroidId() {
        if (TextUtils.isEmpty(androidId)) {
            androidId = "" + android.provider.Settings.Secure.getString(mContext.getContentResolver(),android.provider.Settings.Secure.ANDROID_ID);
        }
        return androidId;
    }

    public String getMacAddress() {
        if (TextUtils.isEmpty(macAddress)) {
            try{
                WifiManager status= (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
                WifiInfo info = status.getConnectionInfo();
                macAddress = "" + info.getMacAddress();
            } catch(Exception e){
                macAddress = "";
            }
        }
        return macAddress;
    }

    public String getImsi() {
        try {
            if (TextUtils.isEmpty(imsi)) {
                TelephonyManager telephonyManager = ((TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE));
                imsi = "" + telephonyManager.getSubscriberId();
            }
            return imsi;
        } catch (Exception e) {
            return "";
        }
    }

    public String getGuid() {
        if (TextUtils.isEmpty(guid)) {
            guid = SpUtils.getString("guid", "");
            if (TextUtils.isEmpty(guid)) {
                guid = "" + UUID.randomUUID().toString();
                SpUtils.putString("guid", guid);
            }
        }
        return guid;
    }

    public String getDid() {
        if (TextUtils.isEmpty(did)) {
            did = SpUtils.getString("cid", "");
            if (TextUtils.isEmpty(did)) {
                did = "" + generateDid();
                SpUtils.putString("cid", did);
            }
        }
        return did;
    }

    public String generateDid() {

        String macAddress = getMacAddress();
        String serialNumber = android.os.Build.SERIAL;
        String androidId = getAndroidId();

        String seed = macAddress + serialNumber + androidId;

        if (TextUtils.isEmpty(seed)) {
            return UUID.randomUUID().toString();
        }

        try {
            return UUID.nameUUIDFromBytes(seed.getBytes("utf-8")).toString();
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    /**
     * @return 返回手机型号
     */
    public String getDeviceModel() {
        if (TextUtils.isEmpty(deviceModel)) {
            try {
                deviceModel = URLEncoder.encode(Build.MODEL, "utf-8");
            } catch (Exception e) {
            }
        }
        return deviceModel;
    }


    public static String checkMac(String mac) {
        try {
            if (!TextUtils.isEmpty(mac)) {
                if (!mac.matches("^[A-Fa-f0-9]{2}([:-][A-Fa-f0-9]{2}){5}$")) {
                    if (!mac.equals(":::::")) {
                        mac = URLEncoder.encode(mac, "utf-8");
                    } else {
                        mac = "";
                    }
                }
            } else {
                mac = "";
            }
        } catch (Exception e) {
        }

        return mac;
    }

    public String getWifiSSID() {
        try {
            WifiManager wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            if (wifiInfo != null) {
                return URLEncoder.encode(wifiInfo.getSSID(), "utf-8");
            }
        } catch (Exception e) {
        }
        return "";
    }

    public String getWifiBSSID() {
        try {
            WifiManager wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            if (wifiInfo != null) {
                return checkMac(wifiInfo.getBSSID());
            }
        } catch (Exception e) {
        }
        return "";
    }


    public String getIMEI() {
        if(TextUtils.isEmpty(imei)) {
            try {
                TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
                if (telephonyManager != null) {
                    imei = "" + telephonyManager.getDeviceId();
                }
            } catch (Exception e) {
                return "";
            }
        }

        return imei;
    }

    public String getLac() {
        if(TextUtils.isEmpty(lac)) {
            try {
                TelephonyManager telephony = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);

                switch (telephony.getPhoneType()) {
                    case TelephonyManager.PHONE_TYPE_GSM:
                    case TelephonyManager.PHONE_TYPE_CDMA:
                        CellLocation location = telephony.getCellLocation();
                        if (location != null && location instanceof GsmCellLocation) {
                            lac = ((GsmCellLocation)location).getLac() + "";
                            cellid = ((GsmCellLocation)location).getCid() + "";
                        } else if (location != null && location instanceof CdmaCellLocation) {
                            lac = ((CdmaCellLocation)location).getBaseStationId() + "";
                            cellid = ((CdmaCellLocation)location).getBaseStationId() + "";
                        }
                        break;
                }
            } catch (Exception e) {
            }
        }

        return lac;
    }

    public String getCellid() {
        if(TextUtils.isEmpty(cellid)) {
            try {
                switch (tm.getPhoneType()) {
                    case TelephonyManager.PHONE_TYPE_GSM:
                    case TelephonyManager.PHONE_TYPE_CDMA:
                        CellLocation location = tm.getCellLocation();
                        if (location != null && location instanceof GsmCellLocation) {
                            lac = ((GsmCellLocation)location).getLac() + "";
                            cellid = ((GsmCellLocation)location).getCid() + "";
                        } else if (location != null && location instanceof CdmaCellLocation) {
                            lac = ((CdmaCellLocation)location).getBaseStationId() + "";
                            cellid = ((CdmaCellLocation)location).getBaseStationId() + "";
                        }
                        break;
                }
            } catch (Exception e) {
            }
        }

        return cellid;
    }

    public String getMnc() {
        if (TextUtils.isEmpty(mnc)) {
            try {
                String operator = tm.getNetworkOperator();
                if (!TextUtils.isEmpty(operator) && operator.length() > 2) {
                    mnc = operator.substring(3);
                } else {
                    mnc = "";
                }
            } catch (Exception e) {
                e.printStackTrace();
                mnc = "";
            }
        }
        return mnc;
    }

    public String getMcc() {
        if (TextUtils.isEmpty(mcc)) {
            try {
                String operator = tm.getNetworkOperator();
                if (!TextUtils.isEmpty(operator) && operator.length() > 2) {
                    mcc = operator.substring(0, 3);
                } else {
                    mcc = "";
                }
            } catch (Exception e) {
                e.printStackTrace();
                mcc = "";
            }
        }
        return mcc;
    }

    /**
     * 获取基站信息
     */
    private void getEnodebInfo() {
        try {
            TelephonyManager telephony = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);

            switch (telephony.getPhoneType()) {
                case TelephonyManager.PHONE_TYPE_GSM:
                case TelephonyManager.PHONE_TYPE_CDMA:
                    CellLocation location = telephony.getCellLocation();
                    if (location != null && location instanceof GsmCellLocation) {
                        lac = ((GsmCellLocation)location).getLac() + "";
                        cellid = ((GsmCellLocation)location).getCid() + "";
                    } else if (location != null && location instanceof CdmaCellLocation) {
                        lac = ((CdmaCellLocation)location).getBaseStationId() + "";
                        cellid = ((CdmaCellLocation)location).getBaseStationId() + "";
                    }
                    break;
            }
        } catch (Exception e) {
        }
    }

    public static boolean isSDCardEnable() {
        try {
            String state = android.os.Environment.getExternalStorageState();
            if (android.os.Environment.MEDIA_MOUNTED.equals(state)) {
                if (android.os.Environment.getExternalStorageDirectory().canWrite()) {
                    return true;
                }
            }
        } catch (Exception e) {
        }
        return false;
    }

    public static String getUUID() {
        String uuid = UUID.randomUUID().toString();
        String time = String.valueOf(System.currentTimeMillis());
        return uuid + time;
    }

    /**
     * 获取版名
     *
     * @return 当前应用的版本名
     */
    public String getAppVersionName() {
        if(TextUtils.isEmpty(appVersionName)) {
            try {
                PackageManager manager = mContext.getPackageManager();
                PackageInfo info = manager.getPackageInfo(mContext.getPackageName(), 0);
                appVersionName = info.versionName;
            } catch (Exception e) {
            }
        }
        return appVersionName;
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public String getAppVersionCode() {
        if(TextUtils.isEmpty(appVersionCode)) {
            try {
                PackageManager manager = mContext.getPackageManager();
                PackageInfo info = manager.getPackageInfo(mContext.getPackageName(), 0);
                appVersionCode = String.valueOf(info.versionCode);
            } catch (Exception e) {
            }
        }
        return appVersionCode;
    }

    public String getAppName() {
        if(TextUtils.isEmpty(appName)) {
            try {
                PackageManager manager = mContext.getPackageManager();
                PackageInfo info = manager.getPackageInfo(mContext.getPackageName(), 0);
                ApplicationInfo applicationInfo = manager.getApplicationInfo(info.packageName, 0);
                appName = (String) manager.getApplicationLabel(applicationInfo);
            } catch (Exception e) {
            }
        }
        return appName;
    }

    public float getDeviceDensity() {
        if(screenDesity == 0) {
            try {
                DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
                screenDesity =  displayMetrics.density;
            } catch (Exception e) {
            }
        }
        return screenDesity;
    }

    public int getDeviceDensityDpi() {
        if(screenDesityDpi == 0) {
            try {
                DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
                screenDesityDpi =  displayMetrics.densityDpi;
            } catch (Exception e) {
            }
        }
        return screenDesityDpi;
    }

    /**
     * @return 返回手机宽高尺寸
     */
    public String getDeviceScreenSize() {
        if(TextUtils.isEmpty(screenSize)) {
            try {
                DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
                int widthPixels = displayMetrics.widthPixels;
                int heightPixels = displayMetrics.heightPixels;
                if (mContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    screenSize = heightPixels + "*" + widthPixels;
                } else {
                    screenSize = widthPixels + "*" + heightPixels;
                }
            } catch (Exception e) {
            }
        }
        return screenSize;
    }

    /**
     * 获取运营商
     * @return
     */
    public String getSimOperatorName() {
        TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        return "" + tm.getSimOperatorName();
    }


    /**
     * 是否为虚拟机
     * @return
     */
    public boolean isEmulator() {
        String BOARD = android.os.Build.BOARD;
        String BOOTLOADER = android.os.Build.BOOTLOADER;
        String BRAND = android.os.Build.BRAND;
        String DEVICE = android.os.Build.DEVICE;
        String HARDWARE = android.os.Build.HARDWARE;
        String MODEL = android.os.Build.MODEL;
        String PRODUCT = android.os.Build.PRODUCT;
        if (BOARD == "unknown" || BOOTLOADER == "unknown" || BRAND == "generic"
                || DEVICE == "generic" || MODEL == "sdk" || PRODUCT == "sdk" || HARDWARE == "goldfish") {
            return true;
        }
        return false;
    }

    /**
     * 获取sd卡根路径
     * @return
     */
    private static String getSdCardPath() {
        boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            return Environment.getExternalStorageDirectory().toString();
        }
        return "";
    }



    public static String getChannel(Context context) {
        String channel;
        try {
            ApplicationInfo appInfo = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            /**
             * 内置渠道包，渠道号必须通过getInt方法获取
             * 外部动态替换的渠道包，渠道号必须通过getString方法获取
             * 所以，这不是一段很傻逼的代码！！！
             */
            channel = String.valueOf(appInfo.metaData.getInt("UMENG_CHANNEL"));
            if (channel.equals("0")) {
                channel = appInfo.metaData.getString("UMENG_CHANNEL");
            }
        } catch (Exception e) {
            e.printStackTrace();
            channel = "";
        }
        Log.i("app channel", channel);
        return channel;
    }

    /**
     * 判断系统自动旋转是否开启
     *
     * @param context
     * @return
     */
    public static boolean getSysRotationStatus(Context context) {
        boolean on = false;
        try {
            on = Settings.System.getInt(context.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION) == 1 ? true : false;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return on;
    }

    public int[] getScreenSize(){
        int[] res = new int[2];
        try{
            DisplayMetrics metrics = new DisplayMetrics();
            WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                windowManager.getDefaultDisplay().getRealMetrics(metrics);
            }else{
                windowManager.getDefaultDisplay().getMetrics(metrics);
            }
            if(metrics.widthPixels <=0 || metrics.heightPixels <= 0){
                metrics = mContext.getResources().getDisplayMetrics();
            }
            res[0] = metrics.widthPixels;
            res[1] = metrics.heightPixels;
        }catch (Exception e){

        }

        return res;
    }

}
