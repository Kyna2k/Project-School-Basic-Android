package com.example.assignment.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.assignment.R;
import com.example.assignment.model.Lop;

import java.util.ArrayList;

public class LopApdater extends BaseAdapter {
    private ArrayList<Lop> dslop;
    Context context;

    public LopApdater(ArrayList<Lop> dslop, Context context) {
        this.dslop = dslop;
        this.context = context;
    }

    @Override
    public int getCount() {
        return dslop.size();
    }

    @Override
    public Object getItem(int i) {
        return dslop.get(i);
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
            view = inflater.inflate(R.layout.item_lop,viewGroup,false);
        }
        TextView sothutu = view.findViewById(R.id.sothutu);
        TextView txt_tenlop = view.findViewById(R.id.txt_themtenlop);
        TextView txt_malop = view.findViewById(R.id.txt_themmalop);
        ImageView hinh = view.findViewById(R.id.hinhlop);
        sothutu.setText(String.valueOf(i+1));
        txt_malop.setText("Mã lớp: "+dslop.get(i).getMalop());
        txt_tenlop.setText(dslop.get(i).getTenlop());
        int id = context.getResources().getIdentifier(dslop.get(i).getAvatar_lop(),"mipmap",context.getPackageName());
        hinh.setImageResource(id);
        return view;
    }
}
