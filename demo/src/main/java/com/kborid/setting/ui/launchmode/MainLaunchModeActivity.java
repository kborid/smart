package com.kborid.setting.ui.launchmode;

import android.os.Bundle;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.kborid.setting.R;

public class MainLaunchModeActivity extends AppCompatActivity {

    private static final String TAG = MainLaunchModeActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main_lm);
        Log.e(TAG, "———onCreate(): TaskId: " + getTaskId() + ",  hashCode: " + hashCode());
    }
}