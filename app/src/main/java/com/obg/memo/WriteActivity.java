package com.obg.memo;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;
import android.support.v7.app.ActionBar;


import com.melnykov.fab.FloatingActionButton;
import com.obg.memo.adapter.MemoListAdapter;
import com.obg.memo.singleton.MainSingleton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class WriteActivity extends AppCompatActivity {
    private static final int RESULT_SPEECH = 1;
    Spinner iSpinner;
    EditText memo_EditText;
    String date;
    MemoListAdapter memoAdapter;
    private DBHelper dbHelper;
    Intent micIntent;
    SpeechRecognizer mRecognizer;
    SQLiteDatabase db;
    ThemeItems themeItems;
    //ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        iSpinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.importance,android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        iSpinner.setAdapter(spinnerAdapter);
        memo_EditText = (EditText) findViewById(R.id.write_editText);
        date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date(System.currentTimeMillis()));
        memoAdapter = new MemoListAdapter(this);
        dbHelper = new DBHelper(this);
        db = dbHelper.getDataBaseHelper();
        themeItems = dbHelper.getThemeColor();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        FloatingActionButton micBtn = (FloatingActionButton) findViewById(R.id.micBtn);
        micBtn.setColorNormal(Color.parseColor(themeItems.getActionbar()));
        micBtn.setColorPressed(Color.parseColor(themeItems.getWindow()));
        micBtn.setColorRipple(Color.parseColor(themeItems.getWindow()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor(themeItems.getWindow()));
            getSupportActionBar().setBackgroundDrawable(getDrawable(R.color.fabPrimary));
        }
        micBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                micIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                micIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
                micIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"ko-KR");
                micIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "speak");
                try {
                    startActivityForResult(micIntent, RESULT_SPEECH);
                } catch (ActivityNotFoundException e) {
                    e.getStackTrace();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_write,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int curId = item.getItemId();
        switch(curId){
            case R.id.memo_add:
                int item_index = iSpinner.getSelectedItemPosition();
                Intent intent = new Intent(WriteActivity.this, MemoActivity.class);
                int resId = 0;
                if (item_index == 0) {
                    resId = R.drawable.important_2;
                } else if (item_index == 1) {
                    resId = R.drawable.important_1;
                } else if (item_index == 2) {
                    resId = R.drawable.important_0;
                }
                String contentEditText = String.valueOf(memo_EditText.getText());
                //dbHelper.insert(contentEditText, date, resId);
                String sql = "insert into oneline_memo(content, date, res_id) values(?,?,?)";
                Object[] params = {contentEditText, date, resId};
                db.execSQL(sql, params);
                MainSingleton singleton = MainSingleton.getInstance(MemoActivity.mContext);
                ((MemoActivity) singleton.getmContext()).changeValues();
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && (requestCode == RESULT_SPEECH)) {
            ArrayList<String> sttResult = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String result_stt = sttResult.get(0);
            memo_EditText.setText(""+result_stt);
        }
    }
    @Override
    public void onBackPressed() {
        //Intent intent = new Intent(WriteActivity.this, MemoActivity.class);
        //startActivity(intent);
        finish();
    }
    //@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    /*@Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        LinearLayout root = (LinearLayout)findViewById(android.R.id.list).getParent().getParent().getParent();
        Toolbar bar = (Toolbar) LayoutInflater.from(this).inflate(R.layout.setting_toolbar, root, false);
        bar.setBackgroundDrawable(getDrawable(R.color.fabPrimary));
        root.addView(bar, 0); // insert at top
        bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }*/
}
