package com.example.assignment;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText username;
    EditText pass;
    CheckBox remember;
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username= findViewById(R.id.username);
        pass = findViewById(R.id.password);
        final Button btn_login = findViewById(R.id.btn_login);
        remember = findViewById(R.id.ghinhopass);
        saveuser();
        preferences = getSharedPreferences("thongtin",MODE_PRIVATE);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String euser = username.getText().toString();
                String epasswork = pass.getText().toString();
                String user = preferences.getString("username","");
                String passwork = preferences.getString("passwork","");
                if(euser.equals(user) && epasswork.equals(passwork))
                {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("isRemember",remember.isChecked());
                    editor.commit();
                    Toast.makeText(MainActivity.this,"Đăng nhập thành công",Toast.LENGTH_SHORT).show();
                    chuyentrang();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Đăng nhập thật bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean checkremember= preferences.getBoolean("isRemember",false);
        remember.setChecked(checkremember);
        if(checkremember)
        {
            username.setText(preferences.getString("username",""));
            pass.setText(preferences.getString("passwork",""));
            chuyentrang();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Intent intent = getIntent();
    }

    private void saveuser()
    {
        SharedPreferences preferences = getSharedPreferences("thongtin",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("username","admin");
        editor.putString("passwork","admin");
        editor.commit();

    }
    private void chuyentrang()
    {
        Intent intent = new Intent(MainActivity.this,Home_activity.class);
        startActivity(intent);
    }
}