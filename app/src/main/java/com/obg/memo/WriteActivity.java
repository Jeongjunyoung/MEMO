package com.obg.memo;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;
import android.support.v7.app.ActionBar;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
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
    SQLiteDatabase db;
    ThemeItems themeItems;
    String writeMode = "write";
    int updateId;
    //ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        AdView mAdview = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                //.addTestDevice("B3EEABB8EE11C2BE770B684D95219ECB")
                .build();
        mAdview.loadAd(adRequest);
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
        Intent getIntentMode = getIntent();
        if (getIntentMode.getStringExtra("mode").equals("modifyMode")) {
            String contentStr = getIntentMode.getStringExtra("contentStr");
            writeMode = "modify";
            updateId = getIntentMode.getIntExtra("updateId", 1);
            int resId = getIntentMode.getIntExtra("resId", 1);
            int item_index = 0;
            memo_EditText.setText(contentStr);
            memo_EditText.setSelection(memo_EditText.length());
            if (resId == R.drawable.important_0) {
                item_index = 2;
            } else if (resId == R.drawable.important_1) {
                item_index = 1;
            } else {
                item_index = 0;
            }
            iSpinner.setSelection(item_index);
        } else if (getIntentMode.getStringExtra("mode").equals("writeMode")) {
            writeMode = "write";
        }
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
        iSpinner.setOnItemSelectedListener(spinnerItemChangeListener);
    }

    private Spinner.OnItemSelectedListener spinnerItemChangeListener = new Spinner.OnItemSelectedListener(){

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            if (i == 2) {
                iSpinner.setBackgroundResource(R.drawable.spinner_style2);
            } else if (i == 1) {
                iSpinner.setBackgroundResource(R.drawable.spinner_style1);
            } else {
                iSpinner.setBackgroundResource(R.drawable.spinner_style0);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };
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
                String contentEditText = String.valueOf(memo_EditText.getText());
                if (contentEditText.trim().equals("")) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(WriteActivity.this);
                    dialog.setTitle("메모를 입력해주세요.");
                    dialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    AlertDialog alertDialog = dialog.create();
                    alertDialog.show();
                } else {
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
                    if (writeMode.equals("write")) {
                        String sql = "insert into oneline_memo(content, date, res_id) values(?,?,?)";
                        Object[] params = {contentEditText, date, resId};
                        db.execSQL(sql, params);
                    } else if (writeMode.equals("modify")) {
                        ((MemoActivity) MemoActivity.mContext).setTextView(contentEditText);
                        dbHelper.modifyData(contentEditText, resId, updateId);
                    }
                    ((MemoActivity) MemoActivity.mContext).changeValues();
                    finish();
                }
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
            memo_EditText.append(""+result_stt);
        }
    }
    @Override
    public void onBackPressed() {
        finish();
    }
}
