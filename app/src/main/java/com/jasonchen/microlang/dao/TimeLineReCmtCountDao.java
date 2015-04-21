package com.jasonchen.microlang.dao;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.jasonchen.microlang.beans.MessageReCmtCountBean;
import com.jasonchen.microlang.debug.AppLogger;
import com.jasonchen.microlang.utils.http.HttpMethod;
import com.jasonchen.microlang.utils.http.HttpUtility;
import com.jasonchen.microlang.utils.http.URLHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jasonchen.microlang.exception.WeiboException;

/**
 * jasonchen
 * 2015/04/10
 */
public class TimeLineReCmtCountDao {

    private String getJson() throws WeiboException {
        String url = URLHelper.TIMELINE_RE_CMT_COUNT;

        Map<String, String> map = new HashMap<String, String>();
        map.put("access_token", access_token);
        StringBuilder idsBuilder = new StringBuilder();
        for (String id : msgIds) {
            idsBuilder.append(",").append(id);
        }

        map.put("ids", idsBuilder.toString());

        return HttpUtility.getInstance().executeNormalTask(HttpMethod.Get, url, map);
    }

    public List<MessageReCmtCountBean> get() throws WeiboException {
        String json = getJson();

        List<MessageReCmtCountBean> value = null;
        try {
            value = new Gson().fromJson(json, new TypeToken<List<MessageReCmtCountBean>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            AppLogger.e(e.getMessage());
        }

        return value;
    }

    private String access_token;
    private List<String> msgIds;

    public TimeLineReCmtCountDao(String access_token, List<String> msgIds) {
        if (msgIds == null) {
            throw new IllegalArgumentException("msgIds cant be null");
        }
        int size = (msgIds.size() >= 100 ? 99 : msgIds.size());
        this.msgIds = new ArrayList<String>();
        this.access_token = access_token;
        this.msgIds.addAll(msgIds.subList(0, size));
    }
}
