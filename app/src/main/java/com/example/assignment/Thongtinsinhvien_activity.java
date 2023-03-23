package com.example.assignment;

import static com.example.assignment.model.NoichuaMucAnh.dialog_thongbao;
import static com.example.assignment.model.NoichuaMucAnh.getThuvienanh_sv;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.assignment.DAO.LopDAO;
import com.example.assignment.DAO.SinhVienDAO;
import com.example.assignment.adapter.AvatarAdapter;
import com.example.assignment.adapter.sLopAdapter;
import com.example.assignment.model.Lop;
import com.example.assignment.model.SinhVien;

import java.util.ArrayList;
import java.util.Locale;

@RequiresApi(api = Build.VERSION_CODES.N)
public class Thongtinsinhvien_activity extends AppCompatActivity {
    final Calendar myCalendar= Calendar.getInstance();
    EditText ngaysinh;
    String getData;
    SinhVienDAO sinhVienDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtinsinhvien);
        Intent intent = getIntent();
        getData = intent.getExtras().getString("data");
    }

    @Override
    protected void onResume() {
        super.onResume();
        final ImageView avatar = findViewById(R.id.avatar);
        final TextView tensv = findViewById(R.id.tensv);
        final TextView malop = findViewById(R.id.malop);
        final TextView ngaysinh = findViewById(R.id.ngaysinh);
        final Button btn_capnhat = findViewById(R.id.btn_capnhat);
        final TextView masv = findViewById(R.id.masv);
        final Button btn_xoa = findViewById(R.id.btn_xoasinhvien);
        sinhVienDAO = new SinhVienDAO(Thongtinsinhvien_activity.this);
        SinhVien sv = sinhVienDAO.getSinhVien(getData);
        int id = this.getResources().getIdentifier(sv.getHinhsv(),"mipmap",this.getPackageName());
        avatar.setImageResource(id);
        tensv.setText(sv.getTensv());
        masv.setText("Mã sinh viên: "+sv.getMsv());
        malop.setText("Mã lớp: "+sv.getLop());
        ngaysinh.setText("Ngày sinh: "+sv.getNgaysinh());
        malop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Thongtinsinhvien_activity.this,thongtinlop_activity.class);
                intent1.putExtra("data",sv.getLop());
                startActivity(intent1);
            }
        });
        btn_capnhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_capnhat(sv);
            }
        });
        btn_xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogxoa(sv);
            }
        });
        final ImageView back = findViewById(R.id.btn_back_themlop);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        if(sinhVienDAO.getSinhVien(getData) == null)
        {
            finish();
        }
    }

    private void dialog_capnhat(SinhVien sv)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(Thongtinsinhvien_activity.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.diaglog_capnhat,null);
        builder.setView(view);
        Button btn_xacnhan = view.findViewById(R.id.btn_xacnhan);
        Button btn_cancel = view.findViewById(R.id.btn_cancel);
        Spinner hinh = view.findViewById(R.id.layanh);
        EditText tensv = view.findViewById(R.id.tensinhvien);
        ngaysinh = view.findViewById(R.id.ngaysinh);
        Spinner chonlop = view.findViewById(R.id.chonlop);
        EditText masv = view.findViewById(R.id.masinhvien);
        AvatarAdapter adapter3 = new AvatarAdapter(getThuvienanh_sv(),Thongtinsinhvien_activity.this);
        hinh.setAdapter(adapter3);
        LopDAO lopDAO= new LopDAO(Thongtinsinhvien_activity.this);
        sLopAdapter apdater2 = new sLopAdapter (lopDAO.getAll(),Thongtinsinhvien_activity.this);
        chonlop.setAdapter(apdater2);
        hinh.setSelection(getThuvienanh_sv().indexOf(sv.getHinhsv()));
        tensv.setText(sv.getTensv());
        ngaysinh.setText(sv.getNgaysinh());
        masv.setText(sv.getMsv());
        int z = -1;
        for(int i = 0; i<lopDAO.getAll().size();i++)
        {
            if(lopDAO.getAll().get(i).getMalop().equalsIgnoreCase(sv.getLop()))
            {
                z = i;
            }
        }// tại sao không dùng indexOf vì nó chả thèm chạy :(((((
        Log.i("check",z+" ");
        chonlop.setSelection(z);
        AlertDialog dialog = builder.create();
        dialog.show();
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
                new DatePickerDialog(Thongtinsinhvien_activity.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        btn_xacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SinhVienDAO svDao = new SinhVienDAO(Thongtinsinhvien_activity.this);
                String layhinh =(String) hinh.getSelectedItem();
                String lop = ((Lop)chonlop.getSelectedItem()).getMalop();
                String ten = tensv.getText().toString();
                String ngay_sinh = ngaysinh.getText().toString();
                String masv1 = masv.getText().toString();
                SinhVien sv_dapcapnhat = new SinhVien(masv1,ten,ngay_sinh,layhinh,lop);
                boolean ketqua =svDao.Update(sv_dapcapnhat);
                if(ketqua)
                {
                    getData = sv_dapcapnhat.getMsv();
                    dialog_thongbao("Cập nhật thành công!",Thongtinsinhvien_activity.this,ketqua);
                    dialog.dismiss();
                    onResume();
                }
                else
                {
                    dialog_thongbao("Cập nhật thất bại!",Thongtinsinhvien_activity.this,ketqua);

                }
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
    private void dialogxoa(SinhVien sv)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(Thongtinsinhvien_activity.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_comfirm_cancel,null);
        builder.setView(view);
        TextView thongbao = view.findViewById(R.id.noidung);
        Button btn_xacnhan = view.findViewById(R.id.btn_xacnhan);
        Button btn_cancel = view.findViewById(R.id.btn_cancel);
        AlertDialog dialog = builder.create();
        dialog.show();
        btn_xacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sinhVienDAO.Datele(sv.getMsv());
                finish();
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