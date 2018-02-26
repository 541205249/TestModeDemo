package com.eebbk.testmodetool;

import android.content.Context;

public class TestModeController {
    private final static class HolderClass {
        private static final TestModeController INSTANCE = new TestModeController();
    }

    private TestModeController() {
    }

    public static TestModeController getInstance() {
        return HolderClass.INSTANCE;
    }

    public void collectTime(Context context) {

    }

}
