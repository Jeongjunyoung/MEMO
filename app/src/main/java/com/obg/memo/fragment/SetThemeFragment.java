package com.obg.memo.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.obg.memo.DBHelper;
import com.obg.memo.R;
import com.obg.memo.SettingsFragActivity;
import com.obg.memo.ThemeItems;

/**
 * Created by d1jun on 2017-12-01.
 */

public class SetThemeFragment extends Fragment implements View.OnClickListener{
    DBHelper db;
    private Button aBtn;
    private Button bBtn;
    private Button cBtn;
    private Button dBtn;
    private Button eBtn;
    private Button fBtn;
    private Button gBtn;
    private Button hBtn;
    ThemeItems themeItems;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.settheme_fragment, container,false);
        db = new DBHelper();
        aBtn = (Button) rootView.findViewById(R.id.aBtn);
        bBtn = (Button) rootView.findViewById(R.id.bBtn);
        cBtn = (Button) rootView.findViewById(R.id.cBtn);
        dBtn = (Button) rootView.findViewById(R.id.dBtn);
        eBtn = (Button) rootView.findViewById(R.id.eBtn);
        fBtn = (Button) rootView.findViewById(R.id.fBtn);
        gBtn = (Button) rootView.findViewById(R.id.gBtn);
        hBtn = (Button) rootView.findViewById(R.id.hBtn);
        aBtn.setOnClickListener(this);
        bBtn.setOnClickListener(this);
        cBtn.setOnClickListener(this);
        dBtn.setOnClickListener(this);
        eBtn.setOnClickListener(this);
        fBtn.setOnClickListener(this);
        gBtn.setOnClickListener(this);
        hBtn.setOnClickListener(this);
        return rootView;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void changeActionBar(String color) {
        ((SettingsFragActivity)getActivity()).changeActionBar(color);
    }

    public ThemeItems setThemeItems(String actionbar, String window) {
        ThemeItems setThemeItems = new ThemeItems();
        setThemeItems.setWindow(window);
        setThemeItems.setActionbar(actionbar);
        return setThemeItems;
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.aBtn:
                themeItems = setThemeItems("#6d7073", "#9ca0a6");
                ((SettingsFragActivity) getActivity()).setColorItems(themeItems);
                changeActionBar("#6d7073");
                break;
            case R.id.bBtn:
                themeItems = setThemeItems("#aca98f", "#cfcdb5");
                ((SettingsFragActivity) getActivity()).setColorItems(themeItems);
                //themeItems.setBackground("#eae9e0");
                changeActionBar("#aca98f");
                break;
            case R.id.cBtn:
                themeItems = setThemeItems("#9bc0ce", "#c5d9e1");
                ((SettingsFragActivity) getActivity()).setColorItems(themeItems);
                //themeItems.setBackground("#e2ebef");
                changeActionBar("#9bc0ce");
                break;
            case R.id.dBtn:
                themeItems = setThemeItems("#a254b1", "#b992c1");
                ((SettingsFragActivity) getActivity()).setColorItems(themeItems);
                //themeItems.setBackground("#e0c8e6");
                changeActionBar("#a254b1");
                break;
            case R.id.eBtn:
                themeItems = setThemeItems("#dc888f", "#f2dfe1");
                ((SettingsFragActivity) getActivity()).setColorItems(themeItems);
                //themeItems.setBackground("#f4d6d9");
                changeActionBar("#dc888f");
                break;
            case R.id.fBtn:
                themeItems = setThemeItems("#f4d9851f", "#f4efba79");
                ((SettingsFragActivity) getActivity()).setColorItems(themeItems);
                //themeItems.setBackground("#edd4b4");
                changeActionBar("#f4d9851f");
                break;
            case R.id.gBtn:
                themeItems = setThemeItems("#3b3a3a", "#a7a5a5");
                ((SettingsFragActivity) getActivity()).setColorItems(themeItems);
                //themeItems.setBackground("#d1cece");
                changeActionBar("#3b3a3a");
                break;
            case R.id.hBtn:
                themeItems = setThemeItems("#1f2a69", "#8f9be4");
                ((SettingsFragActivity) getActivity()).setColorItems(themeItems);
                //themeItems.setBackground("#d5d9f2");
                changeActionBar("#1f2a69");
                break;
        }
    }
}
