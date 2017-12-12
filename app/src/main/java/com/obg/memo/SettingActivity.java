package com.obg.memo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.obg.memo.singleton.MainSingleton;

public class SettingActivity extends PreferenceActivity implements Preference.OnPreferenceClickListener, Preference.OnPreferenceChangeListener{
    private static final String SET_SORT = "set_memo_sort";
    public static Context mContext;
    private ListPreference sortPre;
    private DBHelper dbHelper;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        dbHelper = new DBHelper(this);
        addPreferencesFromResource(R.xml.preference);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.fabPressed));
        }
        getListView().setBackgroundColor(Color.parseColor("#ede8e9ea"));
        sortPre = (ListPreference) findPreference(SET_SORT);
        Preference setTheme = (Preference) findPreference("key_setTheme");
        setTheme.setOnPreferenceClickListener(this);
        sortPre.setOnPreferenceChangeListener(this);
        sortPre.setSummary(sortPre.getEntries()[sortPre.findIndexOfValue(sortPre.getValue().toString())]);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        LinearLayout root = (LinearLayout)findViewById(android.R.id.list).getParent().getParent().getParent();
        Toolbar bar = (Toolbar) LayoutInflater.from(this).inflate(R.layout.setting_toolbar, root, false);
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
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        if (preference.getKey().equals("key_setTheme")) {
            Intent intent = new Intent(SettingActivity.this, SettingsFragActivity.class);
            intent.putExtra("selected", "setTheme");
            startActivityForResult(intent, 0);
        }
        return false;
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object o) {
        String value = o.toString();
        if (preference == sortPre) {
            int index = sortPre.findIndexOfValue(value);
            sortPre.setSummary(sortPre.getEntries()[index]);
            ((MemoActivity) MemoActivity.mContext).setSort(index);
        }
        return true;
    }

    public void finishActivity() {
        finish();
    }
}
