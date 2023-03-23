package com.example.assignment.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.assignment.SQLite.QuanLySinhVienSQLite;
import com.example.assignment.model.Lop;
import com.example.assignment.model.SinhVien;

import java.util.ArrayList;

public class SinhVienDAO {
    private QuanLySinhVienSQLite quanLySinhVienSQLite;

    public SinhVienDAO(Context context) {
        quanLySinhVienSQLite = new QuanLySinhVienSQLite(context);
    }
    public ArrayList<SinhVien> getAll()
    {
        ArrayList<SinhVien> dssv = new ArrayList<>();
        SQLiteDatabase database = quanLySinhVienSQLite.getReadableDatabase();
        Cursor cursor = database.rawQuery("Select * from sinhvien",null);
        if(cursor.getCount()!=0)
        {
            cursor.moveToFirst();
            do{
                dssv.add(new SinhVien(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4)));
            }while (cursor.moveToNext());
        }

        return dssv;
    }
    public SinhVien getSinhVien(String msv1)
    {
        SQLiteDatabase database = quanLySinhVienSQLite.getReadableDatabase();
        Cursor cursor = database.rawQuery("Select * from SinhVien where msv = ?", new String[]{msv1});
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            SinhVien sv = new SinhVien(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4));
            return sv;
        } else{return null;}

    }
    public ArrayList<SinhVien> getSinhVienTrongLop(String maloptim)
    {
        ArrayList<SinhVien> dssv = new ArrayList<>();
        SQLiteDatabase database = quanLySinhVienSQLite.getReadableDatabase();
        Cursor cursor = database.rawQuery("Select * from sinhvien where malop = ?",new String[]{maloptim});
        if(cursor.getCount()!=0)
        {
            cursor.moveToFirst();
            do{
                dssv.add(new SinhVien(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4)));
            }while (cursor.moveToNext());
        }

        return dssv;
    }
    public boolean Insert(SinhVien sv)
    {
        try {
            SQLiteDatabase database = quanLySinhVienSQLite.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            if(sv.getLop().equalsIgnoreCase("")||sv.getMsv().equalsIgnoreCase("")||sv.getNgaysinh().equalsIgnoreCase("")||sv.getTensv().equalsIgnoreCase(""))
            {
                throw new Exception();
            }
            else
            {
                contentValues.put("msv",sv.getMsv());
                contentValues.put("tensv",sv.getTensv());
                contentValues.put("ngaysinh",sv.getNgaysinh());
                contentValues.put("hinh",sv.getHinhsv());
                contentValues.put("malop",sv.getLop());
            }

            long id =  database.insert("SinhVien",null,contentValues);
            Log.i("check", id+"");
            if(id == -1)
            {
                throw new Exception();
            }
            else
            {
                return true;
            }

        }catch (Exception e)
        {
            return false;
        }
    }
    public boolean Update(SinhVien sv)
    {
        try{
            SQLiteDatabase database = quanLySinhVienSQLite.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            if(sv.getMsv().equalsIgnoreCase("")||sv.getNgaysinh().equalsIgnoreCase("")||sv.getTensv().equalsIgnoreCase(""))
            {
                throw new Exception();
            }
            else {
                contentValues.put("tensv",sv.getTensv());
                contentValues.put("ngaysinh",sv.getNgaysinh());
                contentValues.put("hinh",sv.getHinhsv());
                contentValues.put("malop",sv.getLop());
            }
            long id = database.update("SinhVien",contentValues,"msv = ?",new String[]{sv.getMsv()});
            Log.i("check", id+"");
            if(id == -1)
            {
                throw new Exception();
            }
            else
            {
                return true;
            }
        }catch (Exception e)
        {
            return false;
        }
    }
    public boolean Datele(String masv)
    {
        try
        {
            SQLiteDatabase database = quanLySinhVienSQLite.getWritableDatabase();
            long id = database.delete("SinhVien","msv = ?",new String[]{masv});
            if(id == -1)
            {
                throw new Exception();
            }else
            {
                return true;
            }
        }catch (Exception e)
        {
            return false;
        }
    }
    public boolean DateleSV_1lop(String malop)
    {
        try
        {
            SQLiteDatabase database = quanLySinhVienSQLite.getWritableDatabase();
            long id = database.delete("SinhVien","malop = ?",new String[]{malop});
            if(id == -1)
            {
                throw new Exception();
            }else
            {
                return true;
            }
        }catch (Exception e)
        {
            return false;
        }
    }

}
