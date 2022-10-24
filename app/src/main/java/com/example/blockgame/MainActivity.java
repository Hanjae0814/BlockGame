package com.example.blockgame;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {
    MainView m_View;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //타이틀 바 제거
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //상태표시줄(노티바) 제거
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        m_View = new MainView(this);

        setContentView(m_View);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        m_View.m_IsEnd = false;
    }
}