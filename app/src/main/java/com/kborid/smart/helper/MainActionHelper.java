package com.kborid.smart.helper;

public class MainActionHelper {
    public static final String[] actions = {"百度一下", "JS测试", "打开文档", "Handler测试", "SNAP测试", "文本分享", "二维码"};

    public enum ActionType {
        ACTION_OPEN_BD,
        ACTION_OPEN_JS,
        ACTION_OPEN_DOC,
        ACTION_HANDLER,
        ACTION_SNAP,
        ACTION_SHARE,
        ACTION_CODE;

        public static ActionType indexOf(int index) {
            return ActionType.values()[index];
        }
    }
}
