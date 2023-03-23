package com.example.assignment;

import static com.example.assignment.model.NoichuaMucAnh.dialog_thongbao;
import static com.example.assignment.model.NoichuaMucAnh.getThuvienanh_lop;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.assignment.DAO.LopDAO;
import com.example.assignment.DAO.SinhVienDAO;
import com.example.assignment.adapter.AvatarAdapter;
import com.example.assignment.adapter.SinhVienAdapter;
import com.example.assignment.model.Lop;
import com.example.assignment.model.SinhVien;

public class thongtinlop_activity extends AppCompatActivity {

    String getData;
    Lop lop;
    LopDAO lopDAO;
    SinhVienAdapter adapter;
    ListView lvsv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtinlop);
        Intent intent = getIntent();
        getData= intent.getExtras().getString("data");
        lopDAO= new LopDAO(thongtinlop_activity.this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        final ImageView avatar = findViewById(R.id.hinhlop);
        final TextView ten = findViewById(R.id.tenlop);
        final TextView malop =findViewById(R.id.malop);
        lvsv = findViewById(R.id.lvSinhVien);
        final Button btn_capnhat = findViewById(R.id.btn_capnhatlop);
        final Button btn_xoalop = findViewById(R.id.btn_xoalop);
        final TextView txt_chiso = findViewById(R.id.chiso);
        lop= lopDAO.getLop(getData);
        int id = this.getResources().getIdentifier(lop.getAvatar_lop(),"mipmap",this.getPackageName());
        avatar.setImageResource(id);
        ten.setText(lop.getTenlop());
        malop.setText("Mã lớp: "+lop.getMalop());
        adapter = new SinhVienAdapter(new SinhVienDAO(thongtinlop_activity.this).getSinhVienTrongLop(lop.getMalop()),thongtinlop_activity.this);
        lvsv.setAdapter(adapter);
        txt_chiso.setText("Chỉ số lớp: "+lvsv.getCount());
        lvsv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SinhVien sv =(SinhVien) adapterView.getAdapter().getItem(i);
                Intent intent1 = new Intent(thongtinlop_activity.this,Thongtinsinhvien_activity.class);
                intent1.putExtra("data",sv.getMsv());
                startActivity(intent1);
            }
        });
        btn_capnhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogcapnhat(lop);
            }
        });
        btn_xoalop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogxoa(lop);
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
    }// em không hiểu =]] nhưng nó fix được bug


    private void dialogcapnhat(Lop lop)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(thongtinlop_activity.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_capnhatlop,null);
        final Spinner anh = view.findViewById(R.id.layanh);
        final Button btn_capnhat = view.findViewById(R.id.btn_xacnhan);
        final Button btn_cancel = view.findViewById(R.id.btn_cancel);
        final EditText tenlop = view.findViewById(R.id.tenlop);
        final EditText malop = view.findViewById(R.id.malop);
        AvatarAdapter adapter = new AvatarAdapter(getThuvienanh_lop(),thongtinlop_activity.this);
        anh.setAdapter(adapter);
        anh.setSelection(getThuvienanh_lop().indexOf(lop.getAvatar_lop()));
        tenlop.setText(lop.getTenlop());
        malop.setText(lop.getMalop());
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        btn_capnhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ten = tenlop.getText().toString();
                String ma = malop.getText().toString();
                String layhinh = (String) anh.getSelectedItem();
                Lop newlop = new Lop(ma,ten,layhinh);
                Boolean xacnhan = lopDAO.Update(newlop);
                Log.i("check", xacnhan+"");
                if(xacnhan)
                {
                    getData = newlop.getMalop();
                    dialog_thongbao("Cập nhật thành công!",thongtinlop_activity.this,xacnhan);
                    alertDialog.dismiss();
                    onResume();
                }
                else
                {
                    dialog_thongbao("Cập nhật thất bại!",thongtinlop_activity.this,xacnhan);
                }

            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }
    private void dialogxoa(Lop lop)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(thongtinlop_activity.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_xoa_lop,null);
        builder.setView(view);
        TextView thongbao = view.findViewById(R.id.noidung);
        Button btn_xacnhan = view.findViewById(R.id.btn_xacnhan);
        Button btn_cancel = view.findViewById(R.id.btn_cancel);
        ListView listView = view.findViewById(R.id.lv_xoalop);
        thongbao.setText("Nếu xóa lớp này, chúng tôi sẽ xóa luôn những sinh viên trong lớp theo. Bạn có đồng ý");
        listView.setAdapter(adapter);
        AlertDialog dialog = builder.create();
        dialog.show();
        btn_xacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lopDAO.Datele(lop.getMalop(),thongtinlop_activity.this);
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