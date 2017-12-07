package com.obg.memo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.obg.memo.fragment.SetThemeFragment;
import com.obg.memo.singleton.MainSingleton;

import java.util.ArrayList;
import java.util.List;

public class SettingsFragActivity extends AppCompatActivity {
    SetThemeFragment themeFragment;
    LinearLayout root;
    Toolbar bar;
    private DBHelper dbHelper;
    private List<ThemeItems> colorItems = new ArrayList<ThemeItems>();
    ThemeItems themeItems;
    public static Context mContext;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_frag_acitivity);
        mContext = this;
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        dbHelper = new DBHelper(this);
        themeItems = dbHelper.getThemeColor();
        getWindow().setStatusBarColor(Color.parseColor(themeItems.getWindow()));
        getSupportActionBar().setBackgroundDrawable(getDrawable(R.color.fabPrimary));
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

    /*@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        root = (LinearLayout)findViewById(R.id.set_fragment);
        bar = (Toolbar) LayoutInflater.from(this).inflate(R.layout.setting_toolbar, root, false);
        bar.setTitle("Theme Setting");
        bar.setBackgroundDrawable(getDrawable(R.color.fabPrimary));
        root.addView(bar, 0); // insert at top
        ThemeItems themeItems = dbHelper.getThemeColor();
        getWindow().setStatusBarColor(Color.parseColor(themeItems.getWindow()));
        bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }*/

    public void finishActivity() {
        //SettingSingleton singleton = SettingSingleton.getInstance(SettingActivity.mContext);
        ((SettingActivity) SettingActivity.mContext).finishActivity();
        finish();
    }

    public void setColorItems(ThemeItems themeItems) {
        colorItems.clear();
        colorItems.add(themeItems);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void changeActionBar(String color) {
        Drawable drawable = (Drawable) getDrawable(R.color.changeBar);
        drawable.setTint(Color.parseColor(color));
        //bar.setBackgroundDrawable(getDrawable(R.color.changeBar));
        getSupportActionBar().setBackgroundDrawable(getDrawable(R.color.changeBar));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_write,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int curId = item.getItemId();
        switch (curId) {
            case R.id.memo_add:
                ThemeItems changeTheme = colorItems.get(0);
                if (changeTheme != null) {
                    dbHelper.changeThemeColor(changeTheme);
                }
                MainSingleton singleton = MainSingleton.getInstance(MemoActivity.mContext);
                ((MemoActivity) singleton.getmContext()).restart();
                ((SettingActivity) SettingActivity.mContext).finishActivity();
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
