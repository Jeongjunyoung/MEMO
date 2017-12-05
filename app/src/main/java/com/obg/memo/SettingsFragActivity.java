package com.obg.memo;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.obg.memo.fragment.SetThemeFragment;
import com.obg.memo.singleton.MainSingleton;
import com.obg.memo.singleton.SettingSingleton;

public class SettingsFragActivity extends AppCompatActivity {
    SetThemeFragment themeFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_frag_acitivity);
        Intent getIntent = new Intent(this.getIntent());
        String position = "";
        themeFragment = new SetThemeFragment();
        position = getIntent.getStringExtra("selected");
        Fragment selected = null;
        if (position.equals("setTheme")) {
            selected = themeFragment;
        }
        getSupportFragmentManager().beginTransaction().add(R.id.container, selected).commit();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        LinearLayout root = (LinearLayout)findViewById(R.id.set_fragment);
        Toolbar bar = (Toolbar) LayoutInflater.from(this).inflate(R.layout.setting_toolbar, root, false);
        bar.setTitle("Theme Setting");
        bar.setBackgroundDrawable(getDrawable(R.color.fabPrimary));
        root.addView(bar, 0); // insert at top
        bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void finishActivity() {
        //Singleton singleton = Singleton.getInstance(MemoActivity.mContext);
        //startActivity(new Intent(singleton.getmContext(), SettingsFragActivity.class));
        //MainSingleton singleton = MainSingleton.getInstance(SettingActivity.mContext);
        //Context context = singleton.getmContext();
        //((MemoActivity) context).finish();
        SettingSingleton singleton = SettingSingleton.getInstance(SettingActivity.mContext);
        ((SettingActivity) singleton.getmContext()).finish();
        finish();
    }
}
