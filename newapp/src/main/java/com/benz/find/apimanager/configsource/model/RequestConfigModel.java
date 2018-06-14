package com.benz.find.apimanager.configsource.model;

import java.util.HashMap;
import java.util.Map;

public class RequestConfigModel {
    private Map<String, Object> map = new HashMap<>();

    public RequestConfigModel() {
        map.put("tabId", "0"); // 风景3 / 壁纸 2 /型男 1/美女 0
        map.put("limit", "10");
        map.put("cid", "Youloft_Android");
        map.put("height", "1440");
        map.put("width", "2560");
        map.put("bd", "com.youloft.calendar");
        map.put("av", "4.8.2");
        map.put("chn2", "20511");
        map.put("carrier", "99");
        //   skip=0  跳 页
        //   应该是在美女为tabid 0的基础上     Tag=10000  丝袜 10000 美腿 10001 气质 10002 古风 10003 性感 10004 可爱 10005 制服 10006
    }

    public Map<String, Object> getOptions() {
        return map;
    }

    public Map<String, Object> setTag(String Tag) {
        map.put("Tag", Tag);
        return map;
    }

}
