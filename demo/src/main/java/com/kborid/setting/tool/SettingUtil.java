package com.kborid.setting.tool;

import android.database.Cursor;
import android.net.Uri;

import com.kborid.setting.PRJApplication;

import java.util.HashMap;
import java.util.Map;

/**
 * SettingUtil
 *
 * @description:
 * @author: duanwei
 * @email: duanwei@thunisoft.com
 * @version: 1.0.0
 * @date: 2020/12/10
 */
public class SettingUtil {

    /**
     * 跳转apn设置界面
     *
     * @return
     */
    public static Map<String, String> checkAPN() {
        Map<String, String> map = new HashMap<String, String>();
        Cursor cr = PRJApplication.getInstance().getContentResolver().query(Uri.parse("content://telephony/carriers"), null, null, null, null);
        int i = 0;
        while (cr != null && cr.moveToNext()) {
            String id = cr.getString(cr.getColumnIndex("_id"));
            map.put("id" + i, id);
            String apn = cr.getString(cr.getColumnIndex("apn"));
            map.put("apn" + i, apn);
        }
        return map;
    }
}
