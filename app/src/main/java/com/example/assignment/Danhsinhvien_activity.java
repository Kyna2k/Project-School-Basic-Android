package com.example.assignment;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.assignment.DAO.LopDAO;
import com.example.assignment.DAO.SinhVienDAO;
import com.example.assignment.adapter.SinhVienAdapter;
import com.example.assignment.model.Lop;
import com.example.assignment.model.SinhVien;

import java.util.ArrayList;

public class Danhsinhvien_activity extends AppCompatActivity {
    ArrayList<SinhVien> dssv = new ArrayList<>();
    ListView lvSinhVien;
    SinhVienDAO sinhVienDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_danhsinhvien);
        sinhVienDAO= new SinhVienDAO(Danhsinhvien_activity.this);
        lvSinhVien = findViewById(R.id.list_danhsachssinhvien);
        final ImageView back = findViewById(R.id.btn_back_themlop);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        dodulieu();

    }

    @Override
    protected void onResume() {
        super.onResume();
        dodulieu();
        final EditText edt_tim = findViewById(R.id.timsinhvien);
        edt_tim.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                dssv = tim(sinhVienDAO.getAll(),charSequence);
                SinhVienAdapter adapter = new SinhVienAdapter(dssv,Danhsinhvien_activity.this);
                lvSinhVien.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        lvSinhVien.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SinhVien sv =(SinhVien) adapterView.getAdapter().getItem(i);
                Intent intent = new Intent(Danhsinhvien_activity.this,Thongtinsinhvien_activity.class);
                intent.putExtra("data",sv.getMsv());
                startActivity(intent);
            }
        });
        lvSinhVien.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                SinhVien sv =(SinhVien) adapterView.getAdapter().getItem(i);
                dialogxoa(sv);
                return true;
            }
        });

    }

    private ArrayList<SinhVien> tim(ArrayList<SinhVien> arsinhvien,CharSequence charSequence) {
        ArrayList<SinhVien> newSinhviens = new ArrayList<>();
        if (String.valueOf(charSequence).trim().length() == 0) {
            return arsinhvien;
        } else {
            for (SinhVien sv : arsinhvien) {
                String x = sv.getTensv().toUpperCase();
                if (x.contains(String.valueOf(charSequence).toUpperCase())) {
                    newSinhviens.add(new SinhVien(sv.getMsv(), sv.getTensv(), sv.getNgaysinh(), sv.getHinhsv(), sv.getLop()));
                }
            }
            return newSinhviens;
        }
    }

    private void dodulieu()
    {
        dssv = sinhVienDAO.getAll();
        SinhVienAdapter adapter = new SinhVienAdapter(dssv,Danhsinhvien_activity.this);
        lvSinhVien.setAdapter(adapter);
    }
    private void dialogxoa(SinhVien sv)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(Danhsinhvien_activity.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_comfirm_cancel,null);
        builder.setView(view);
        Button btn_xacnhan = view.findViewById(R.id.btn_xacnhan);
        Button btn_cancel = view.findViewById(R.id.btn_cancel);
        AlertDialog dialog = builder.create();
        dialog.show();
        btn_xacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sinhVienDAO.Datele(sv.getMsv());
                dialog.dismiss();
                onResume();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}
