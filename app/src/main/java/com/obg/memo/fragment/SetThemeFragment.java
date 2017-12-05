package com.obg.memo.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.obg.memo.DBHelper;
import com.obg.memo.MemoActivity;
import com.obg.memo.R;
import com.obg.memo.SettingsFragActivity;
import com.obg.memo.singleton.MainSingleton;

/**
 * Created by d1jun on 2017-12-01.
 */

public class SetThemeFragment extends Fragment {
    DBHelper db;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.settheme_fragment, container,false);
        db = new DBHelper();
        Button button = (Button) rootView.findViewById(R.id.testBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(rootView.getContext(), "what", Toast.LENGTH_SHORT).show();
                db.changeThemeColor("#c5e6e2");
                MainSingleton singleton = MainSingleton.getInstance(MemoActivity.mContext);
                Context context = singleton.getmContext();
                ((MemoActivity) context).restart();
                ((SettingsFragActivity)getActivity()).finishActivity();

            }
        });
        return rootView;
    }
}
