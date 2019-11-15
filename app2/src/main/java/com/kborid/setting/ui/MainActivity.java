package com.kborid.setting.ui;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kborid.setting.R;

import io.flutter.embedding.android.FlutterActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startFlutter(View view) {
//        startActivity(FlutterActivity.createDefaultIntent(this));
//        startActivity(FlutterActivity.withNewEngine().initialRoute("hybrid").build(this));
        startActivity(FlutterActivity.withCachedEngine("flutter_engine").build(this));
    }
}
