package com.obg.memo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.preference.ListPreference;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.app.ActionBar;

import com.melnykov.fab.FloatingActionButton;
import com.obg.memo.adapter.MemoListAdapter;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MemoActivity extends AppCompatActivity implements View.OnClickListener{
    LinearLayout memo;
    EditText memo_editText;
    TextView memo_textView;
    ListView list;
    MemoListAdapter memoAdapter;
    String date;
    Menu mMenu;
    List<MemoItem> listItem;
    Drawable normalDrawable;
    Drawable pressDrawable;
    Drawable rippleDrawable;
    private DBHelper dbHelper;
    Animation viewAnim;
    Animation hideAnim;
    private FloatingActionButton addButton;
    private Button writeCancelBtn;
    private Button writeUpdateBtn;
    private Button writeSaveBtn;
    int updateId;
    long first_backBtn;
    long second_backBtn;
    int sort;
    public static Context mContext;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Singleton s = Singleton.getInstance(this);
        //mContext = s.getmContext();
        mContext = this;
        dbHelper = new DBHelper(this);
        dbHelper.open();
        ThemeItems themeItems = dbHelper.getThemeColor();
        list = (ListView) findViewById(R.id.listView);
        memoAdapter = new MemoListAdapter(this);
        list.setAdapter(memoAdapter);
        list.setClickable(true);
        memo = (LinearLayout) findViewById(R.id.memo);
        memo_editText = (EditText) findViewById(R.id.memo_editText);
        memo_textView = (TextView) findViewById(R.id.memo_textView);
        addButton = (FloatingActionButton) findViewById(R.id.addBtn);
        viewAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.flow);
        hideAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.hide);
        writeCancelBtn = (Button) findViewById(R.id.write_cancel_btn);
        writeUpdateBtn = (Button) findViewById(R.id.write_update_btn);
        writeSaveBtn = (Button) findViewById(R.id.write_save_btn);
        addButton.setOnClickListener(this);
        writeCancelBtn.setOnClickListener(this);
        writeUpdateBtn.setOnClickListener(this);
        writeSaveBtn.setOnClickListener(this);
        normalDrawable = (Drawable) getDrawable(R.color.fabPrimary);
        pressDrawable = (Drawable) getDrawable(R.color.fabPressed);
        rippleDrawable = (Drawable) getDrawable(R.color.fabPressed);
        normalDrawable.setTint(Color.parseColor(themeItems.getActionbar()));
        pressDrawable.setTint(Color.parseColor(themeItems.getWindow()));
        rippleDrawable.setTint(Color.parseColor(themeItems.getWindow()));
        addButton.setColorNormal(Color.parseColor(themeItems.getActionbar()));
        addButton.setColorPressed(R.color.fabPressed);
        addButton.setColorRipple(R.color.fabPressed);
        date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date(System.currentTimeMillis()));
        listItem = new ArrayList<MemoItem>();
        Intent intent = getIntent();
        if (intent.getStringExtra("widgetAdd") != null) {
            Intent writeStartIntent = new Intent(mContext, WriteActivity.class);
            startActivity(writeStartIntent);
        }
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String sort_SetValue = pref.getString("set_memo_sort","");
        if (sort_SetValue.equals("0") || sort_SetValue.equals("1")) {
            sort = Integer.parseInt(sort_SetValue);
        }else{
            sort = 0;
        }
        listItem = dbHelper.selectData(sort);
        for(int i=0;i<listItem.size();i++) {
            memoAdapter.addItem(listItem.get(i));
        }
        memoAdapter.notifyDataSetChanged();
        list.setOnItemClickListener(new ListViewItemClickListener());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor(themeItems.getWindow()));
            getSupportActionBar().setBackgroundDrawable(getDrawable(R.color.fabPrimary));
        }

    }
    //MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mMenu = menu;
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int curId = item.getItemId();
        int count = memoAdapter.getCount();;
        switch (curId) {
            case R.id.delete_list:
                iconChanger(false);
                clickEditBtn(count, true);
                return true;
            case R.id.remove_cancel:
                iconChanger(true);
                clickEditBtn(count, false);
                memoAdapter.clearChoices();
                return true;
            case R.id.remove_list:
                Long[] checkedItems = memoAdapter.getCheckItems();
                for(int i = checkedItems.length-1; i>=0; i--) {
                    long id = checkedItems[i];
                    Log.d("ID", " : "+(int) id);
                    dbHelper.delete((int) id);
                }
                listItem.clear();
                listItem = dbHelper.selectData(sort);
                memoAdapter.clearChoices();
                memoAdapter.notifyDataSetChanged();
                return true;
            case R.id.setting:
                Intent intent = new Intent(this,SettingActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void iconChanger(boolean change) {
        MenuItem removeBtn = mMenu.findItem(R.id.remove_list);
        MenuItem deleteBtn = mMenu.findItem(R.id.delete_list);
        MenuItem removeCancelBtn = mMenu.findItem(R.id.remove_cancel);
        deleteBtn.setVisible(change);
        removeBtn.setVisible(!change);
        removeCancelBtn.setVisible(!change);
    }

    public void clickEditBtn(int count, boolean bClick) {
        for(int i=0;i<count;i++) {
            list.setItemChecked(i,false);
        }
        memoAdapter.toggleCheckbox(bClick);
    }

    /*@Override
        public void colorChanged(int color1) {
        PreferenceManager.getDefaultSharedPreferences(this).edit().putInt("color", color1).commit();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Drawable fabPrimary = getResources().getDrawable(R.color.fabPrimary);
            Drawable fabPressed = getResources().getDrawable(R.color.colorAccent);
            println(String.valueOf(color1));
            fabPrimary.setTint(color1);
            fabPressed.setColorFilter(color1, PorterDuff.Mode.SRC_IN);
            addButton.setColorNormal(color1);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorAccent));
            getSupportActionBar().setBackgroundDrawable(getDrawable(R.color.fabPrimary));
        }
    }*/

    private class ListViewItemClickListener implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            updateId = memoAdapter.getUpdateText(position);
            list.setEnabled(false);
            memo_textView.setText(listItem.get(position).getContent());
            memo_editText.setText(listItem.get(position).getContent());
            memo.setVisibility(View.VISIBLE);
            memo.startAnimation(viewAnim);
            println(String.valueOf(updateId));
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addBtn:
                Intent intent = new Intent(MemoActivity.this, WriteActivity.class);
                startActivity(intent);
                break;
            case R.id.write_cancel_btn:
                list.setEnabled(true);
                setModifyMode(false);
                memo.setVisibility(View.GONE);
                memo.startAnimation(hideAnim);
                break;
            case R.id.write_update_btn:
                setModifyMode(true);
                break;
            case R.id.write_save_btn:
                String modifyStr = memo_editText.getText().toString();
                setModifyMode(false);
                memo_textView.setText(modifyStr);
                int position = memoAdapter.modifyList(modifyStr, updateId);
                listItem.get(position).setContent(modifyStr);
                memoAdapter.notifyDataSetChanged();
                dbHelper.modifyData(modifyStr, updateId);
                Toast.makeText(this, "UPDATE SUCCESS", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onBackPressed() {
        second_backBtn = System.currentTimeMillis();
        if (memo.getVisibility() == 0) {
            list.setEnabled(true);
            memo.setVisibility(View.GONE);
            memo.startAnimation(hideAnim);
            setModifyMode(false);
        }else if(second_backBtn - first_backBtn < 2000){
            super.onBackPressed();
        }else{
            Toast.makeText(this, "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
            first_backBtn = System.currentTimeMillis();
        }
    }

    public void setModifyMode(boolean mode) {
        if (mode) {
            writeSaveBtn.setVisibility(View.VISIBLE);
            writeUpdateBtn.setVisibility(View.GONE);
            memo_textView.setVisibility(View.GONE);
            memo_editText.setVisibility(View.VISIBLE);
        } else {
            writeSaveBtn.setVisibility(View.GONE);
            writeUpdateBtn.setVisibility(View.VISIBLE);
            memo_textView.setVisibility(View.VISIBLE);
            memo_editText.setVisibility(View.GONE);
        }
    }
    public void changeValues() {
        setSort(sort);
    }
    public void setSort(int sort) {
        listItem.clear();
        memoAdapter.memoAdapterItemClear();
        listItem = dbHelper.selectData(sort);
        for(int i=0;i<listItem.size();i++) {
            memoAdapter.addItem(listItem.get(i));
        }
        memoAdapter.notifyDataSetChanged();
    }

    public void restart() {
        recreate();
    }
    public void println(String string) {
        Log.d("OBG", "TEXT : " + string);
    }
}
