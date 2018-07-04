package com.pbn.org.news.model.bobo;

import android.os.Build;

import com.pbn.org.news.utils.DeviceUtil;
import com.pbn.org.news.utils.Md5Utils;

import java.util.HashMap;
import java.util.Map;

public class BORequestBean {
    public String _aKey = "ANDROIDID";
    public String _umengId	="ai644f12dfd36337a96cb9d7c04f1886e5";

    public Map<String, String> getMapp(){
        Map<String, String> hashMap = new HashMap<>();
        String mac = DeviceUtil.getInstance().getMacAddress();
        String aid = DeviceUtil.getInstance().getAndroidId();
        String imei = DeviceUtil.getInstance().getIMEI();
        hashMap.put("_aKey", "ANDROID");
        hashMap.put("_udid", Md5Utils.md5(aid + mac.toLowerCase() + Build.MODEL + Build.MANUFACTURER));//MD5(AID + MAC(Lower) + Build.MODEL + Build.MANUFACTURER)
        hashMap.put("_androidID", aid);
        hashMap.put("_imei", DeviceUtil.getInstance().getIMEI());
        hashMap.put("_imei2", "");
        hashMap.put("_dpi", DeviceUtil.getInstance().getDeviceDensityDpi()+"");
        hashMap.put("_mac", mac);
        hashMap.put("_devid", Md5Utils.md5(imei + mac));//MD5(IMEI + MAC)
        hashMap.put("_vApp", "9606");
        hashMap.put("_vName", "3.0.8");
        hashMap.put("_pName", "tv.yixia.bobo");
        hashMap.put("_appName", "波波视频");
        hashMap.put("_vOs",  "8.0.0");
        hashMap.put("_lang", "zh_CN");
        hashMap.put("_uId", "0");
        hashMap.put("_dId", "MI+6");
        hashMap.put("_facturer", Build.MANUFACTURER);
        hashMap.put("_pcId", "xiaomi_market");
        hashMap.put("_t", System.currentTimeMillis()/1000 + "");
        hashMap.put("_nId", "1");
        hashMap.put("_cpu", "arm64-v8a");
        hashMap.put("_cpuId", "Qualcomm Technologies, Inc MSM8998");
        hashMap.put("_px", DeviceUtil.getInstance().getScreenSize()[0] + "x" + DeviceUtil.getInstance().getScreenSize()[1]);
        hashMap.put("_rt", "0");
        hashMap.put("_token", "");
//        if (TextUtils.isEmpty(gs.a.m())) {
//            hashMap.put("_abId", d.a().a("KuaiGengAbTestkey", ""));
//        } else {
//        }
        hashMap.put("_abId", "");

        hashMap.put("_lac", "-1");
        hashMap.put("_latitude", "0.0");
        hashMap.put("_longitude", "0.0");
        hashMap.put("_carrier", "联通");
        hashMap.put("_localIp", "");
        hashMap.put("_umengId", "ai644f12dfd36337a96cb9d7c04f1886e5");
        hashMap.put("_sign", "1c5584ba3b1df6aae1aa");
        /**
         * debug	0
         ifMoment	1
         lastUpdateTime
         newinstall	1
         page	1
         size	10
         type	up
         */
        hashMap.put("debug", "0");
        hashMap.put("ifMoment", "1");
        hashMap.put("lastUpdateTime", "");
        hashMap.put("newinstall", "1");
        hashMap.put("page", "1");
        hashMap.put("size", "10");
        hashMap.put("type", "up");
        return hashMap;
    }


}
