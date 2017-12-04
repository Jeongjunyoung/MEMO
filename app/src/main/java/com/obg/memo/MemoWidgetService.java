package com.obg.memo;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.List;

public class MemoWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteViewsFactory(getApplicationContext(), intent);
    }
    static class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory{
        private Context mContext;
        private int mCount;
        List<MemoItem> list;
        private static String DATABASE_TABLE = "oneline_memo";
        public DBHelper dbHelper;

        public StackRemoteViewsFactory(Context context, Intent intent) {
            mContext = context;
        }
        @Override
        public void onCreate() {
            setDBListItem();
        }

        @Override
        public void onDataSetChanged() {
            setDBListItem();
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public RemoteViews getViewAt(int i) {
            RemoteViews remoteView = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
            remoteView.setImageViewResource(R.id.widget_importance, list.get(i).getResId());
            remoteView.setTextViewText(R.id.widget_text_item, "" + list.get(i).getContent());

            //Intent fillinIntent = new Intent();
            return remoteView;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        public void setDBListItem() {
            dbHelper = new DBHelper(mContext);
            SQLiteDatabase db = dbHelper.getDataBaseHelper();
            Cursor cursor;
            cursor = db.query(DATABASE_TABLE,null,null,null,null,null,null);
            list = new ArrayList<MemoItem>();
            MemoItem memoItem = null;
            for(int i = 0;i<cursor.getCount(); i++) {
                cursor.moveToNext();
                memoItem = new MemoItem();
                memoItem.set_id(cursor.getInt(0));
                memoItem.setContent(cursor.getString(1));
                memoItem.setDate(cursor.getString(2));
                memoItem.setResId(cursor.getInt(3));
                list.add(memoItem);
            }
        }
    }
}
