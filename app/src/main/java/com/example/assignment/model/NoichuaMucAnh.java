package com.example.assignment.model;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.assignment.DAO.SinhVienDAO;
import com.example.assignment.Danhsinhvien_activity;
import com.example.assignment.R;
import com.example.assignment.Themsinhvien_activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class NoichuaMucAnh {

    public static ArrayList<String> getThuvienanh_sv()
    {
        ArrayList<String> thuvienanh_sv = new ArrayList<>();
        thuvienanh_sv.add("avatar1");
        thuvienanh_sv.add("avatar2");
        thuvienanh_sv.add("avatar3");
        thuvienanh_sv.add("avatar4");
        return thuvienanh_sv;
    }
    public static ArrayList<String> getThuvienanh_lop()
    {
        ArrayList<String> thuvienanh_lop = new ArrayList<>();
        thuvienanh_lop.add("cplus");
        thuvienanh_lop.add("html");
        thuvienanh_lop.add("java");
        thuvienanh_lop.add("js");
        return thuvienanh_lop;
    }
    public static void dialog_thongbao(String noidung,Context context,boolean ketqua)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.comfirmdiaglog,null);
        builder.setView(view);
        TextView thongbao = view.findViewById(R.id.noidung);
        Button btn_xacnhan = view.findViewById(R.id.btn_xacnhan);
        ImageView anh = view.findViewById(R.id.anhcomfirm);
        thongbao.setText(noidung);
        String r_ketqua ="";
        if (ketqua)
        {
            r_ketqua = "checked";
        }
        else
        {
            r_ketqua = "remove";
        }
        int id = context.getResources().getIdentifier(r_ketqua,"mipmap",context.getPackageName());
        anh.setImageResource(id);
        AlertDialog dialog = builder.create();
        dialog.show();
        btn_xacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

}
