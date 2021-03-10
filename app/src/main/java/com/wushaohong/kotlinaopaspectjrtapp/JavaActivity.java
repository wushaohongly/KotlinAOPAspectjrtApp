package com.wushaohong.kotlinaopaspectjrtapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.wushaohong.kotlinaopaspectjrtapp.click.CheckPoint;

public class JavaActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @CheckPoint("AOP Click")
    public void onClick(View view) {
    }

    public void onCheckPermissions(View view) {
    }
}
