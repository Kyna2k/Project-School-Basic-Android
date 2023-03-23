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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.assignment.DAO.LopDAO;
import com.example.assignment.DAO.SinhVienDAO;
import com.example.assignment.adapter.LopApdater;
import com.example.assignment.adapter.SinhVienAdapter;
import com.example.assignment.model.Lop;
import com.example.assignment.model.SinhVien;

import java.util.ArrayList;
import java.util.Locale;

public class Danhsachlop_activity extends AppCompatActivity {
    LopApdater adapter;
    LopDAO lopDAO;
    ArrayList<Lop> lop;
    ListView list_danhsachlop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsachlop);
        list_danhsachlop = findViewById(R.id.list_danhsachlop);
        lopDAO = new LopDAO(Danhsachlop_activity.this);
        dodulieu();
        final ImageView back = findViewById(R.id.btn_back_themlop);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        final EditText edit_timlop = findViewById(R.id.edit_timlop);
        dodulieu();
        edit_timlop.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                lop=tim(lopDAO.getAll(),charSequence);
                LopApdater adapter = new LopApdater(lop,Danhsachlop_activity.this);
                list_danhsachlop.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        list_danhsachlop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Lop item = (Lop) adapterView.getAdapter().getItem(i);
                Intent intent = new Intent(Danhsachlop_activity.this,thongtinlop_activity.class);
                intent.putExtra("data",item.getMalop());
                startActivity(intent);
            }
        });
        list_danhsachlop.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                SinhVienDAO sv = new SinhVienDAO(Danhsachlop_activity.this);
                ArrayList<SinhVien> svs = sv.getSinhVienTrongLop(((Lop)adapterView.getAdapter().getItem(i)).getMalop());
                Lop lop = (Lop) adapterView.getAdapter().getItem(i);
                dialogxoa(lop,svs);
                return true;
            }
        });
        dodulieu();
    }
    private void dodulieu()
    {
        lop = lopDAO.getAll();
        LopApdater adapter = new LopApdater(lop,Danhsachlop_activity.this);
        list_danhsachlop.setAdapter(adapter);
    }
    private ArrayList<Lop> tim(ArrayList<Lop> lops,CharSequence charSequence)
    {
        ArrayList<Lop> newLops = new ArrayList<>();
        if(String.valueOf(charSequence).trim().length() == 0)
        {
            return lops;
        }else
        {
            for(Lop lop : lops)
            {
                String x = lop.getTenlop().toUpperCase();
                if(x.contains(String.valueOf(charSequence).toUpperCase()))
                {
                    newLops.add(new Lop(lop.getMalop(),lop.getTenlop(),lop.getAvatar_lop()));
                }
            }
            return newLops;
        }
    }
    private void dialogxoa(Lop lop,ArrayList<?> svs)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(Danhsachlop_activity.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_xoa_lop,null);
        builder.setView(view);
        TextView thongbao = view.findViewById(R.id.noidung);
        Button btn_xacnhan = view.findViewById(R.id.btn_xacnhan);
        Button btn_cancel = view.findViewById(R.id.btn_cancel);
        ListView listView = view.findViewById(R.id.lv_xoalop);
        thongbao.setText("Nếu xóa lớp này, chúng tôi sẽ xóa luôn những sinh viên trong lớp theo. Bạn có đồng ý");
        SinhVienAdapter adapter1 = new SinhVienAdapter((ArrayList<SinhVien>) svs,Danhsachlop_activity.this);
        listView.setAdapter(adapter1);
        AlertDialog dialog = builder.create();
        dialog.show();
        btn_xacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lopDAO.Datele(lop.getMalop(),Danhsachlop_activity.this);
                dodulieu();
                dialog.dismiss();
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