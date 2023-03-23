package com.example.assignment;

import android.content.Intent;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.assignment.model.SinhVien;

public class Home_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        final ImageView images_danhsachlop = findViewById(R.id.images_danhsachlop);
        final ImageView images_danhsachsinhvien = findViewById(R.id.images_danhsachsinhvien);
        final ImageView images_themlop = findViewById(R.id.images_themlop);
        final ImageView images_themsinhvien = findViewById(R.id.images_themsinhvien);
        final TextView text_danhsachlop = findViewById(R.id.text_danhsachlop);
        final TextView text_danhsachsinhvien = findViewById(R.id.text_danhsinhvien);
        final TextView text_themlop = findViewById(R.id.text_themlop);
        final TextView text_themsinhvien = findViewById(R.id.text_themsinhvien);
        final ImageView user = findViewById(R.id.userten);
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_logout();
            }
        });
        //images event
        images_danhsachlop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home_activity.this,Danhsachlop_activity.class);
                startActivity(intent);
            }
        });
        images_danhsachsinhvien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home_activity.this,Danhsinhvien_activity.class);
                startActivity(intent);
            }
        });
        images_themlop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home_activity.this,Themlop_activity.class);
                startActivity(intent);
            }
        });
        images_themsinhvien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home_activity.this,Themsinhvien_activity.class);
                startActivity(intent);
            }
        });
        //text event ( cho nó đồng bộ thầy à )
        text_danhsachlop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home_activity.this,Danhsachlop_activity.class);
                startActivity(intent);
            }
        });
        text_danhsachsinhvien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home_activity.this,Danhsinhvien_activity.class);
                startActivity(intent);
            }
        });
        text_themlop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home_activity.this,Themlop_activity.class);
                startActivity(intent);
            }
        });
        text_themsinhvien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home_activity.this,Themsinhvien_activity.class);
                startActivity(intent);
            }
        });
    }
    private void dialog_logout()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(Home_activity.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_comfirm_cancel,null);
        builder.setView(view);
        TextView thongbao = view.findViewById(R.id.noidung);
        Button btn_xacnhan = view.findViewById(R.id.btn_xacnhan);
        thongbao.setText("Thoát tài khoảng");
        btn_xacnhan.setText("Logout");
        Button btn_cancel = view.findViewById(R.id.btn_cancel);
        AlertDialog dialog = builder.create();
        dialog.show();
        btn_xacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = getSharedPreferences("thongtin",MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("isRemember",false);
                editor.commit();
                startActivity(new Intent(Home_activity.this,MainActivity.class));
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
}