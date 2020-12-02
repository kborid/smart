package com.kborid.smart.constant;

import com.kborid.smart.BuildConfig;

/**
 * AppConstant
 *
 * @description: 常量定义
 * @author: duanwei
 * @email: duanwei@thunisoft.com
 * @version: 1.0.0
 * @date: 2020/12/2
 */
public interface AppConstant {

    /*broadcast*/
    String UNLOGIN_ACTION = BuildConfig.APPLICATION_ID + ".action.UNLOGIN"; // 未登录广播

    String HOME_CURRENT_TAB_POSITION = "HOME_CURRENT_TAB_POSITION";
    String MENU_SHOW_HIDE = "MENU_SHOW_HIDE";

    /* 新闻*/
    String NEWS_ID = "news_id";
    String NEWS_TYPE = "news_type";
    String CHANNEL_POSITION = "channel_position";
    String CHANNEL_MINE = "CHANNEL_MINE";
    String CHANNEL_MORE = "CHANNEL_MORE";
    String CHANNEL_SWAP = "CHANNEL_SWAP";
    String NEWS_CHANNEL_CHANGED = "NEWS_CHANNEL_CHANGED";

    /* 视频*/
    String VIDEO_TYPE = "VIDEO_TYPE";

    String NEWS_LIST_TO_TOP = "NEWS_LIST_TO_TOP";//列表返回顶部
    String ZONE_PUBLISH_ADD = "ZONE_PUBLISH_ADD";//发布说说

    String NEWS_POST_ID = "NEWS_POST_ID";//新闻详情id
    String NEWS_LINK = "NEWS_LINK";
    String NEWS_TITLE = "NEWS_TITLE";

    String PHOTO_DETAIL_IMGSRC = "photo_detail_imgsrc";
    String PHOTO_DETAIL = "photo_detail";
    String PHOTO_TAB_CLICK = "PHOTO_TAB_CLICK";

    String NEWS_IMG_RES = "news_img_res";
}
