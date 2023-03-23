package com.example.assignment;

import static com.example.assignment.model.NoichuaMucAnh.dialog_thongbao;
import static com.example.assignment.model.NoichuaMucAnh.getThuvienanh_sv;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.assignment.DAO.LopDAO;
import com.example.assignment.DAO.SinhVienDAO;
import com.example.assignment.adapter.AvatarAdapter;
import com.example.assignment.adapter.LopApdater;
import com.example.assignment.adapter.SinhVienAdapter;
import com.example.assignment.adapter.sLopAdapter;
import com.example.assignment.model.Lop;
import com.example.assignment.model.NoichuaMucAnh;
import com.example.assignment.model.SinhVien;

import java.util.ArrayList;
import java.util.Locale;

@RequiresApi(api = Build.VERSION_CODES.N)
public class Themsinhvien_activity extends AppCompatActivity {
    final Calendar myCalendar= Calendar.getInstance();
    ArrayList<SinhVien> dssv = new ArrayList<>();
    ListView them_lvSinhVien;
    SinhVienDAO sinhVienDAO;
    LopDAO lopDAO;
    Spinner chonlop;
    EditText ngaysinh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themsinhvien);
        chonlop = findViewById(R.id.chonlop);
        final Button btn_themsinhvien = findViewById(R.id.btn_themsinhvien);
        final Spinner hinh = findViewById(R.id.layanh);
        final EditText tensv = findViewById(R.id.tensinhvien);
        final EditText masv = findViewById(R.id.masinhvien);
        ngaysinh = findViewById(R.id.ngaysinh);
        them_lvSinhVien = findViewById(R.id.them_lvSinhVien);
        sinhVienDAO= new SinhVienDAO(Themsinhvien_activity.this);
        lopDAO= new LopDAO(Themsinhvien_activity.this);
        AvatarAdapter adapter3 = new AvatarAdapter(getThuvienanh_sv(),Themsinhvien_activity.this);
        hinh.setAdapter(adapter3);
        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();
            }
        };
        ngaysinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(Themsinhvien_activity.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        btn_themsinhvien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String layhinh =(String) hinh.getSelectedItem();
                String lop = ((Lop)chonlop.getSelectedItem()).getMalop();
                String ten = tensv.getText().toString();
                String ngay_sinh = ngaysinh.getText().toString();
                String masv1 = masv.getText().toString();
                Boolean xacnhan = sinhVienDAO.Insert(new SinhVien(masv1,ten,ngay_sinh,layhinh,lop));
                if(xacnhan)
                {
                    dialog_thongbao("Đã thêm thành công xinh viên",Themsinhvien_activity.this,xacnhan);
                    hinh.setSelection(0);
                    chonlop.setSelection(0);
                    tensv.setText("");
                    ngaysinh.setText("");
                    masv.setText("");
                }
                else
                {
                    dialog_thongbao("Không thể thêm sinh viên",Themsinhvien_activity.this,xacnhan);
                }
                dodulieu();
            }
        });
        final ImageView back = findViewById(R.id.btn_back_themlop);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        dodulieu();
        dodulieulop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        dodulieu();
        them_lvSinhVien.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SinhVien sv =(SinhVien) adapterView.getAdapter().getItem(i);
                Intent intent = new Intent(Themsinhvien_activity.this,Thongtinsinhvien_activity.class);
                intent.putExtra("data",sv.getMsv());
                startActivity(intent);
            }
        });
        them_lvSinhVien.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                SinhVien sv =(SinhVien) adapterView.getAdapter().getItem(i);
                dialogxoa(sv);
                return true;
            }
        });
    }

    private void dodulieu()
    {
        dssv = sinhVienDAO.getAll();
        SinhVienAdapter adapter = new SinhVienAdapter(dssv,Themsinhvien_activity.this);
        them_lvSinhVien.setAdapter(adapter);
    }
    private void dodulieulop()
    {
        sLopAdapter apdater2 = new sLopAdapter (lopDAO.getAll(),Themsinhvien_activity.this);
        chonlop.setAdapter(apdater2);
    }
    private void dialogxoa(SinhVien sv)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(Themsinhvien_activity.this);
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
    private void updateLabel(){
        String myFormat="MM/dd/yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        ngaysinh.setText(dateFormat.format(myCalendar.getTime()));
    }
}