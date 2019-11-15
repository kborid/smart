package com.kborid.smart.helper;

public class MainActionHelper {

    public enum ActionType {
        ACTION_OPEN_BD,
        ACTION_OPEN_JS,
        ACTION_REFLECT,
        ACTION_SHARE,
        ACTION_CONTEXT_PRINT,
        ACTION_SCAN,
        ACTION_SECRET;

        public static ActionType indexOf(int index) {
            return ActionType.values()[index];
        }
    }
}
