package com.obg.memo.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.obg.memo.CheckableLayout;
import com.obg.memo.DBHelper;
import com.obg.memo.MemoItem;
import com.obg.memo.MemoItemView;
import com.obg.memo.MemoViewHolder;
import com.obg.memo.R;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class MemoListAdapter extends BaseAdapter {
    private Context mContext;
    private List<MemoItem> items = new ArrayList<MemoItem>();
    public MemoListAdapter(Context context) {
        mContext = context;
    }
    boolean cClick = false;
    public List<View> convertViewList = new ArrayList<View>();
    public MemoListAdapter(){}
    private DBHelper dbHelper;

    @Override
    public int getCount() {
        return items.size();
    }

    public void addItem(MemoItem item) {
        MemoItem memoItem = new MemoItem();
        memoItem.set_id(item.get_id());
        memoItem.setContent(item.getContent());
        memoItem.setDate(item.getDate());
        memoItem.setResId(item.getResId());
        items.add(memoItem);
    }
    public void removeItem(Long[] arrLong) {
        //items.remove(position);
        for(int j=0;j<arrLong.length;j++) {
            Long position = arrLong[j];
            for(int i=0;i<items.size();i++) {
                if (items.get(i).get_id() == position) {
                    items.remove(i);
                }
            }
        }

    }
    @Override
    public Object getItem(int i) {
        return items.get(i);
    }
    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        final Context context = viewGroup.getContext();
        MemoViewHolder holder;
        MemoItem memoItem = items.get(position);
        //CheckableLayout checkableLayout = (CheckableLayout) convertView.findViewById(R.id.memo_item_layout);
        //if (convertViewList.size() <= items.size()) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.memo_item, viewGroup, false);
        holder = new MemoViewHolder();
        holder.memoContent = (TextView) convertView.findViewById(R.id.memo_text);
        holder.memoDate = (TextView) convertView.findViewById(R.id.memo_date);
        holder.imageView = (ImageView) convertView.findViewById(R.id.memo_importance);
        holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);
        holder.checkBox.setId(memoItem.get_id());
        holder.list_position = position;
        convertViewList.add(convertView);
        convertView.setTag(holder);
        //Log.d("GETView", holder.memoContent.getText() + " :  " + memoItem.get_id());
        /*} else {
            convertView = convertViewList.get(position);
            holder = (MemoViewHolder) convertView.getTag();
        }*/
        if (cClick) {
            holder.checkBox.setVisibility(View.VISIBLE);
        } else if (!cClick) {
            holder.checkBox.setVisibility(View.GONE);
        }
        holder.memoContent.setText(memoItem.getContent());
        holder.memoDate.setText(memoItem.getDate());
        holder.imageView.setImageResource(memoItem.getResId());
        return convertView;
    }

    public void toggleCheckbox(boolean bClick) {
        cClick = bClick;
        notifyDataSetChanged();
    }

    public Long[] getCheckItems() {
        List<Long> checkIds = new ArrayList<Long>();
        //int index = 0;
        int[] indexArr;
        View view;
        for(int i=0;i<convertViewList.size();i++) {
            view = convertViewList.get(i);
            MemoViewHolder holder = (MemoViewHolder) view.getTag();
            if (holder.checkBox.isChecked()) {
                checkIds.add((long) holder.checkBox.getId());
                //removeItem(holder.checkBox.getId());
                //indexArr[i] = holder.checkBox.getId();
                //Log.d("IN", holder.memoContent.getText() +" :  " + holder.checkBox.getId());
            }
        }
        for(int j=0;j<items.size();j++) {
            //Log.d("OUT", "" + items.get(j).get_id());
        }
        Long[] arrLong = (Long[]) checkIds.toArray(new Long[0]);
        removeItem(arrLong);
        return arrLong;
    }

    public void clearChoices() {
        List<Long> checkIds = new ArrayList<Long>();
        //int index = 0;
        View view;
        for(int i=0;i<convertViewList.size();i++) {
            view = convertViewList.get(i);
            MemoViewHolder holder = (MemoViewHolder) view.getTag();
            if (holder.checkBox.isChecked()) {
                holder.checkBox.setChecked(false);
            }
        }
    }

    public int getUpdateText(int position) {
        int updateStr = 0;
        View view;
        for(int i=0;i<convertViewList.size();i++) {
            view = convertViewList.get(i);
            MemoViewHolder holder = (MemoViewHolder) view.getTag();
            if (holder.list_position == position) {
                updateStr = holder.checkBox.getId();
            }
        }
        return updateStr;
    }

    public int modifyList(String modifyStr, int _id) {
        int position = 0;
        View view;
        for(int i=0;i<convertViewList.size();i++) {
            view = convertViewList.get(i);
            MemoViewHolder holder = (MemoViewHolder) view.getTag();
            if (holder.checkBox.getId() == _id) {
                position = i;
            }
        }
        MemoItem memoItem = items.get(position);
        memoItem.setContent(modifyStr);
        return position;
    }
    public void memoAdapterItemClear() {
        items.clear();
    }
}
