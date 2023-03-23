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
import com.example.assignment.model.SinhVien;

import java.util.ArrayList;

public class SinhVienAdapter extends BaseAdapter {
    private ArrayList<SinhVien> dssv;
    Context context;

    public SinhVienAdapter(ArrayList<SinhVien> dssv, Context context) {
        this.dssv = dssv;
        this.context = context;
    }

    @Override
    public int getCount() {
        return dssv.size();
    }

    @Override
    public Object getItem(int i) {
        return dssv.get(i);
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
            view =inflater.inflate(R.layout.item_sinhvien,viewGroup,false);

        }
        TextView sothutu = view.findViewById(R.id.sothutu);
        TextView tensv = view.findViewById(R.id.txt_tensv);
        TextView masv = view.findViewById(R.id.txt_masv);
        TextView malop = view.findViewById(R.id.txt_malop);
        ImageView hinh = view.findViewById(R.id.hinhsv);
        tensv.setText(dssv.get(i).getTensv());
        sothutu.setText(String.valueOf(i+1));
        masv.setText("MSV: "+dssv.get(i).getMsv());
        malop.setText("Lớp: "+dssv.get(i).getLop());
        int id = context.getResources().getIdentifier(dssv.get(i).getHinhsv(),"mipmap",context.getPackageName());
        hinh.setImageResource(id);

        return view;
    }
}
