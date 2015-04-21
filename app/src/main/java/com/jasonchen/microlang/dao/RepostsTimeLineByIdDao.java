package com.jasonchen.microlang.dao;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jasonchen.microlang.beans.MessageBean;
import com.jasonchen.microlang.beans.RepostListBean;
import com.jasonchen.microlang.debug.AppLogger;
import com.jasonchen.microlang.settings.SettingUtility;
import com.jasonchen.microlang.utils.GlobalContext;
import com.jasonchen.microlang.utils.TimeUtility;
import com.jasonchen.microlang.utils.http.HttpMethod;
import com.jasonchen.microlang.utils.http.HttpUtility;
import com.jasonchen.microlang.utils.http.URLHelper;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.jasonchen.microlang.exception.WeiboException;

/**
 * jasonchen
 * 2015/04/10
 */
public class RepostsTimeLineByIdDao {

    public RepostListBean getGSONMsgList() throws WeiboException {

        String url = URLHelper.REPOSTS_TIMELINE_BY_MSGID;

        Map<String, String> map = new HashMap<String, String>();
        map.put("access_token", GlobalContext.getInstance().getSpecialBlackToken());
        map.put("id", id);
        map.put("since_id", since_id);
        map.put("max_id", max_id);
        map.put("count", count);
        map.put("page", page);
        map.put("filter_by_author", filter_by_author);

        String jsonData = HttpUtility.getInstance().executeNormalTask(HttpMethod.Get, url, map);

        Gson gson = new Gson();

        RepostListBean value = null;

        try {
            value = gson.fromJson(jsonData, RepostListBean.class);
        } catch (JsonSyntaxException e) {

            AppLogger.e(e.getMessage());
        }

        if (value != null && value.getItemList().size() > 0) {
            List<MessageBean> msgList = value.getItemList();
            Iterator<MessageBean> iterator = msgList.iterator();
            while (iterator.hasNext()) {
                MessageBean msg = iterator.next();
                if (msg.getUser() == null) {
                    iterator.remove();
                } else {
                    msg.getListViewSpannableString();
                    TimeUtility.dealMills(msg);
                }
            }
        }
        return value;
    }

    public RepostsTimeLineByIdDao(String token, String id) {
        this.access_token = token;
        this.id = id;
        this.count = SettingUtility.getMsgCount();
    }

    public void setSince_id(String since_id) {
        this.since_id = since_id;
    }

    public void setMax_id(String max_id) {
        this.max_id = max_id;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public void setFilter_by_author(String filter_by_author) {
        this.filter_by_author = filter_by_author;
    }

    private String access_token;
    private String id;
    private String since_id;
    private String max_id;
    private String count;
    private String page;
    private String filter_by_author;
}
