package com.example.assignment;

import static com.example.assignment.model.NoichuaMucAnh.dialog_thongbao;
import static com.example.assignment.model.NoichuaMucAnh.getThuvienanh_lop;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.assignment.adapter.AvatarAdapter;
import com.example.assignment.adapter.LopApdater;
import com.example.assignment.model.Lop;

import java.util.ArrayList;

public class Themlop_activity extends AppCompatActivity {

    ListView lvLop;
    LopDAO lopDAO;
    ArrayList<Lop> dslop = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themlop);
        final ImageView back = findViewById(R.id.btn_back_themlop);
        final Spinner anh = findViewById(R.id.layanh);
        final Button btn_themlop = findViewById(R.id.btn_themlop);
        final EditText tenlop = findViewById(R.id.tenlop);
        final EditText malop = findViewById(R.id.malop);
        lvLop = findViewById(R.id.them_lvLop);
        lopDAO = new LopDAO(Themlop_activity.this);
        dodulieu();
        AvatarAdapter adapter2 = new AvatarAdapter(getThuvienanh_lop(),Themlop_activity.this);
        anh.setAdapter(adapter2);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn_themlop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ten = tenlop.getText().toString();
                String ma = malop.getText().toString();
                String layhinh = (String) anh.getSelectedItem();
                Boolean xacnhan = lopDAO.Insert(new Lop(ma,ten,layhinh));
                if(xacnhan)
                {
                    dialog_thongbao("Đã thêm lớp thành công",Themlop_activity.this,xacnhan);
                    tenlop.setText("");
                    malop.setText("");
                }
                else
                {
                    dialog_thongbao("Không thể thêm lớp",Themlop_activity.this,xacnhan);
                }
                dodulieu();
            }
        });
        lvLop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Lop item = (Lop) adapterView.getAdapter().getItem(i);
                Intent intent = new Intent(Themlop_activity.this,thongtinlop_activity.class);
                intent.putExtra("data",item.getMalop());
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        dodulieu();
    }

    private void dodulieu()
    {
        dslop = lopDAO.getAll();
        LopApdater apdater = new LopApdater(dslop,Themlop_activity.this);
        lvLop.setAdapter(apdater);
    }

}