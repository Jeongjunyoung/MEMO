package com.obg.memo;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * Created by d1jun on 2017-11-29.
 */

public class MemoProvider extends AppWidgetProvider {
    public static final String REFRESH_BTN = "android.action.REFRESH_CLICK";
    public static final String ADD_BTN = "android.action.ADD_CLICK";
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        //RemoteViews btnRemoteView = new RemoteViews(context.getPackageName(), R.layout.memo_widget);

        //버튼 클릭 처리


        for(int i=0;i<appWidgetIds.length;i++) {
            updateAppWidget(context, appWidgetManager, appWidgetIds[i]);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.memo_widget);
        Intent intent = new Intent(context, MemoWidgetService.class);
        remoteView.setRemoteAdapter(appWidgetId, R.id.list_view, intent);
        remoteView.setEmptyView(R.id.list_view, R.id.empty_view);

        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

        PendingIntent btn_click = PendingIntent.getBroadcast(context, 0, new Intent(REFRESH_BTN), PendingIntent.FLAG_UPDATE_CURRENT);
        remoteView.setOnClickPendingIntent(R.id.widget_item_refresh, btn_click);

        Intent i = new Intent(Intent.ACTION_MAIN);
        i.addCategory(Intent.CATEGORY_LAUNCHER);
        i.setComponent(new ComponentName(context, WriteActivity.class));
        PendingIntent add_click = PendingIntent.getActivity(context, 0, i, 0);
        //PendingIntent  = PendingIntent.getBroadcast(context, 0, new Intent(ADD_BTN), PendingIntent.FLAG_UPDATE_CURRENT);
        remoteView.setOnClickPendingIntent(R.id.widget_item_add, add_click);

        Intent itemIntent = new Intent(Intent.ACTION_VIEW);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, itemIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteView.setPendingIntentTemplate(R.id.list_view, pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId, remoteView);
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        String action = intent.getAction();
        if (action.equals(REFRESH_BTN)) {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            ComponentName name = new ComponentName(context.getPackageName(), MemoProvider.class.getName());
            int[] ids = appWidgetManager.getAppWidgetIds(name);
            appWidgetManager.notifyAppWidgetViewDataChanged(ids, R.id.list_view);
            Toast.makeText(context, "새로고침", Toast.LENGTH_SHORT).show();
        } else if (action.equals(ADD_BTN)) {
        }
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
        int minWidth = newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);
        int maxWidth = newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_WIDTH);
        int minHeight = newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT);
        int maxHeight = newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_HEIGHT);
        RemoteViews rv = null;

        if(minWidth == 152 && maxWidth == 196 && minHeight == 58 && maxHeight == 84){
            rv = new RemoteViews(context.getPackageName(), R.layout.memo_widget);
        } else {
            rv = new RemoteViews(context.getPackageName(), R.layout.memo_widget);
        }
        appWidgetManager.updateAppWidget(appWidgetId, rv);

    }
}
