package com.example.assignment.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.assignment.R;

import java.util.ArrayList;

public class AvatarAdapter extends BaseAdapter {
    private ArrayList<String> arrayList;
    Context context;

    public AvatarAdapter(ArrayList<String> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            view = inflater.inflate(R.layout.spinner_avatar,viewGroup,false);
        }
        ImageView chon_avatar = view.findViewById(R.id.chon_avatar);
        int id = context.getResources().getIdentifier(arrayList.get(i),"mipmap",context.getPackageName());

        chon_avatar.setImageResource(id);
        return view;
    }
}
