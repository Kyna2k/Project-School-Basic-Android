package com.example.assignment.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class QuanLySinhVienSQLite extends SQLiteOpenHelper {
    public static final String DB_Name = "2tr6";
    public static final int DB_Version = 2;

    public QuanLySinhVienSQLite(Context context)
    {
        super(context,DB_Name,null,DB_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "Create table Lop(malop text,tenlop text,avatar text, primary key(malop))";
        sqLiteDatabase.execSQL(sql);
        String sql1 = "CREATE TABLE SinhVien(msv text,tensv text,ngaysinh text,hinh text,malop text,primary key(msv),foreign key (malop) references lop(malop))";
        sqLiteDatabase.execSQL(sql1);
        String data = "insert into SinhVien values('PS23156','Phan thanh huy','2000-09-18','avatar2','CP17307'),('PS1235','Nguyễn văn tèo','1555-08-02','avatar3','IT17302')," +
                "('PX1282','Nguyễn văn tí','2002-02-10','avatar3','WEB17306')";
        sqLiteDatabase.execSQL(data);
        String datamau = "Insert into Lop values ('CP17307','Lập trình máy tính','js'),('IT17302','Ứng dụng phần mềm','java'),('WEB17306','Thiết kế web','cplus'),('CP17305','Lập trình máy tính','html')";
        sqLiteDatabase.execSQL(datamau);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = "Drop table if exists Lop";
        String sql1 = "Drop table if exists SinhVien";
        sqLiteDatabase.execSQL(sql1);
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }
}
