package com.benz.find.apimanager.configsource.model;

import java.util.HashMap;
import java.util.Map;

public class PictureConfigModel {
    private Map<String, Object> map = new HashMap<>();

    public PictureConfigModel() {
        map.put("tabId", "0"); // 风景3 / 壁纸 2 /型男 1/美女 0
        map.put("limit", "50");//请求图片数量限制
        map.put("skip", "0");//跳页
        map.put("Tag", "10000");//   应该是在美女为tabid 0的基础上     Tag=10000  丝袜 10000 美腿 10001 气质 10002 古风 10003 性感 10004 可爱 10005 制服 10006
        map.put("cc", "CN");//国家
        map.put("carrier", "99");
        map.put("did", "e799b81da45d95c30f7ee0021a00eb29");// 不能删  能改
        map.put("mac", "02%3A00%3A00%3A00%3A00%3A00");// 不能删  网络地址？ 3A00是转译字符？
        map.put("cid", "Youloft_Android");//不能删
        map.put("width", "2560");
        map.put("height", "1440");
//        map.put("idfa", "a3f60ba58932022b");
        map.put("bd", "com.youloft.calendar");//不能删
//        map.put("chn2", "20511");
        map.put("av", "4.8.2");//不能删
//        map.put("imei", "357216072056691");
//        map.put("brand", "samsung");
        map.put("lang", "zh");
//        map.put("oudid", "a3f60ba58932022b");
//        map.put("ov", "7.0");
        map.put("chn", "20511-0");//不能删
        map.put("imsi", "unknow");
        map.put("model", "SM-G935T");
        map.put("deviceid", "e799b81da45d95c30f7ee0021a0092be");
        map.put("city", "101120101");
        map.put("nt", "0");
        map.put("t", "1528284019");
        map.put("tkn", "D0513B7CEF494E82AEAFDFF7B2183ECF");
        map.put("citycode", "101120101");
        map.put("strategy", "1002");
        map.put("bsid", "5b16519af4e274740d555b57");//删除有时获取不到图片
        map.put("uid", "");
        map.put("utkn", "An2ydh9NUM8_M8e9PIQm819M3ClUt8zoFjFNoa6W0m5q");
//        map.put("lng", "16.8937573");//
//        map.put("lon", "16.8937573");//经度
//        map.put("lat", "16.6727534");//纬度
        map.put("authtime", "2528284019");//授权时间？
        map.put("authsign", "260a01060a7e26e1d61a0b2c3da57f97");//授权签名？

    }

    public Map<String, Object> getOptions() {
        return map;
    }

    public PictureConfigModel setTag(String tag) {
        if (tag != null) {
            map.put("Tag", tag);
        }
        return this;
    }

    public PictureConfigModel setTabId(String tabId) {
        if (tabId != null) {
            map.put("tabId", tabId);
        }
        return this;
    }

}
