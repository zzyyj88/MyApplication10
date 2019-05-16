package com.example.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyAdapter extends ArrayAdapter {
    private static final String TAG = "MyAdapter";
    public MyAdapter(Context context, int resource, ArrayList<HashMap<String,String>> list){
        super(context,resource,list);
    }
    @NonNull
    @Override

    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            itemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        Log.i(TAG, "getView: itemVie=" + itemView);

        Map<String,String> map = (Map<String,String>) getItem(position);
        TextView title = (TextView) itemView.findViewById(R.id.itemTitle1);
        TextView detail = (TextView) itemView.findViewById(R.id.itemDetail1);

        Log.i(TAG, "getView: title=" + title);
        title.setText("Title"+map.get("itemTitle"));
        detail.setText("detail"+map.get("itemDetail"));

        return itemView;
    }
}