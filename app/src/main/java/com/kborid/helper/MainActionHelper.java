package com.kborid.helper;

public class MainActionHelper {

    public enum ActionType {
        ACTION_OPEN_BD,
        ACTION_OPEN_JS,
        ACTION_UNIVERSAL,
        ACTION_PICASSO,
        ACTION_GLIDE,
        ACTION_CHANGE_JUMP,
        ACTION_REFLECT,
        ACTION_SHARE,
        ACTION_CONTEXT_PRINT,
        ACTION_SCAN;

        public static ActionType indexOf(int index) {
            return ActionType.values()[index];
        }
    }
}
